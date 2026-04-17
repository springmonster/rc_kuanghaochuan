## Context

企业内部多个业务系统（微服务）需要在关键业务事件发生时通知外部第三方系统。当前各业务系统直接调用外部 API 存在高度耦合、可靠性无保障、监控分散等问题。

**当前痛点：**
- 业务系统需直接维护每个外部 API 的调用代码，包括认证、请求格式等
- 网络抖动或外部服务不可用时，通知直接丢失
- 各系统独立实现，缺乏统一的投递状态追踪

**关键约束：**
- 发送方（内部微服务）：技术栈不统一，直接访问外部 URL 能力不一致
- 接收方（外部 API）：不可控，可能无多环境，HTTP 存在中间人攻击风险
- 业务系统不关心外部 API 返回值，只关心通知是否送达
- **优先保证简单**，避免过度设计
- **采用单体架构 + 前后端分离**，简化部署和运维

## 系统架构图

### 整体架构

```mermaid
graph TB
    subgraph 业务系统
        SYS1[业务系统 A]
        SYS2[业务系统 B]
        SYS3[业务系统 C]
    end

    subgraph NotificationService[通知服务 - 单体架构]
        subgraph Frontend[前端 Vue 3]
            WEB[Web UI<br/>配置管理]
        end

        subgraph Backend[后端 SpringBoot]
            API[Notification API<br/>接收通知请求]
            CONFIG[Config Service<br/>配置管理]
            SENDER[Sender Service<br/>投递服务]
        end

        subgraph Data[数据层 H2]
            DB[(数据库<br/>配置 + 投递记录)]
        end
    end

    subgraph 外部系统
        EXT1[广告系统 API]
        EXT2[CRM 系统 API]
        EXT3[库存系统 API]
    end

    SYS1 -->|HTTP POST| API
    SYS2 -->|HTTP POST| API
    SYS3 -->|HTTP POST| API

    WEB -->|HTTP/REST| API
    WEB -->|HTTP/REST| CONFIG

    API --> CONFIG
    API --> SENDER
    SENDER --> DB
    CONFIG --> DB

    SENDER -->|HTTP/S| EXT1
    SENDER -->|HTTP/S| EXT2
    SENDER -->|HTTP/S| EXT3
```

### 前端后端交互流程

```mermaid
sequenceDiagram
    participant U as 用户
    participant F as 前端 Vue 3
    participant A as 后端 SpringBoot
    participant S as Sender
    participant E as 外部 API

    Note over U,E: 管理员操作流程
    U->>F: 访问配置管理页面
    F->>A: 获取 Destination 列表
    A->>F: 返回配置列表
    U->>F: 创建/编辑 Destination
    F->>A: 提交配置
    A->>A: 验证并保存到数据库
    A->>F: 返回保存结果

    Note over U,E: 业务系统投递通知流程
    SYS->>A: POST /api/v1/notifications
    A->>A: 验证请求
    A->>S: 触发投递
    A->>SYS: 返回 202 Accepted
    S->>E: HTTP POST
    E-->>S: 响应结果
    S->>S: 记录结果到数据库
```

### 投递流程

```mermaid
flowchart LR
    A([业务系统]) -->|POST /notifications| B[API]
    B -->|验证| C{验证通过?}
    C -->|否| D[返回 400]
    C -->|是| E[保存到数据库]
    E --> F[返回 202 Accepted]
    F --> G[Sender 同步/异步投递]
    G --> H{外部 API 响应}
    H -->|2xx| I[标记成功]
    H -->|4xx/5xx| J[重试?]
    H -->|超时| J
    J -->|是| K[指数退避等待]
    K --> G
    J -->|否| L[标记失败]
    I --> M[(数据库)]
    L --> M
```

## Goals / Non-Goals

**Goals:**
- 提供统一的 API 通知接入层，业务系统只需按标准格式投递
- 保证通知至少投递一次（at-least-once）
- 支持同步投递（等待外部 API 响应）和异步投递（后台重试）
- 集中管理外部 API 配置（地址、认证、超时，重试策略）
- 提供 Web UI 用于配置管理
- 记录投递状态（成功/失败）

**Non-Goals:**
- 不处理外部 API 的返回值（业务系统不关心 response body）
- 不实现复杂的消息路由（固定 1:1 映射，通知 → 目标 API）
- 不处理外部 API 的回调（单向通知）
- **暂不引入消息队列**（RabbitMQ/RocketMQ）- 未来流量激增时再引入
- **暂不引入死信队列** - 失败重试足够应对时暂不需要
- **暂不实现限流** - 作为后续优化项
- **暂不实现监控面板** - 作为后续优化项，优先保证核心投递功能

## Decisions

### Decision 1: 架构模式 - 单体架构 + 前后端分离

**选择：单体架构，前端后端分离部署**

**理由：**
- 第一版量级未知，微服务架构增加复杂度
- 单体架构开发、部署、运维更简单
- 前后端分离便于独立迭代，前端专注 UI，后端专注业务
- 如果未来复杂度显著增加，可以拆分为独立服务

**模块划分：**
- **前端**：Vue 3 + Vite，提供配置管理界面
- **后端**：SpringBoot，提供 RESTful API
- **数据库**：H2（开发/测试），存储配置和投递记录

### Decision 2: 前端技术选型

**选择：Vue 3 + Vite**

**理由：**
- 主流前端框架，生态成熟
- 组件化开发，效率高
- Vite 提供极速开发体验

### Decision 3: 后端技术选型

**选择：SpringBoot (Java)**

**理由：**
- 企业级应用成熟框架
- 生态完善，文档丰富
- 团队熟悉度高
- 内置 Web 服务器，部署简单

### Decision 4: 数据库选型

**选择：H2 数据库**

**理由：**
- 零配置，嵌入式数据库
- 开发测试方便，无需安装外部数据库
- SQL 标准兼容性好
- 未来可轻松迁移到 PostgreSQL/MySQL

### Decision 5: 消息投递语义 - At-Least-Once

**选择：At-Least-Once（至少一次）**

**理由：**
- 外部 API 通知通常为幂等操作（如更新 CRM 状态）
- 业务优先保证不丢消息，接受少量重复
- 实现复杂度适中，重试机制成熟

### Decision 6: 重试策略 - 指数退避 + 最大次数

**选择：指数退避，最大 5 次重试**

**参数：**
- 基础延迟：1s
- 退避倍数：2
- 最大延迟：60s
- 最大重试：5次

### Decision 7: 认证方式 - 配置化支持多种认证

**选择：配置中心统一管理认证信息**

- API Key、Bearer Token、Basic Auth、OAuth2
- 敏感信息加密存储

## Risks / Trade-offs

**[Risk] 外部 API 响应慢导致请求积压**
→ **Mitigation**: 设置合理的超时时间，使用异步处理避免阻塞

**[Risk] 重试次数耗尽后通知丢失**
→ **Mitigation**: 记录所有失败通知的状态和原因，支持人工排查和重试

**[Risk] 单体架构扩展性差**
→ **Mitigation**: 合理设计模块边界，未来可平滑拆分为微服务

## Migration Plan

**Phase 1: 项目基础**
1. 搭建前端项目（Vue 3 + Vite）
2. 搭建后端项目（SpringBoot）
3. 集成 H2 数据库

**Phase 2: 核心功能**
4. 实现配置管理 API + 前端页面
5. 实现通知接收 API
6. 实现 Sender 投递功能
7. 实现重试机制

**Phase 3: 生产就绪**
8. 完善加密存储
9. 编写文档
10. 业务系统接入

## Open Questions

1. **外部 API 超时设置**：统一默认值 vs 可配置？
2. **多环境支持**：是否需要区分 dev/staging/prod？
3. **幂等 ID 生成**：如何生成全局唯一 ID？

## 演进规划

| 触发条件 | 引入方案 |
|----------|----------|
| 通知量 > 1000/秒 | 引入 RabbitMQ 消息队列 |
| 失败通知需人工处理 | 引入死信队列 |
| 需要限流保护 | 引入限流机制（令牌桶/滑动窗口） |
| 需要可视化监控 | 引入 Prometheus + Grafana 监控面板 |
| 需要更高可用性 | 水平扩展 + 负载均衡 |
