# API 通知系统设计与实现

## 快速开始

### 前置条件

| 工具 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | 后端运行 |
| Maven | 3.8+ | 后端构建 |
| Node.js | 18+ | 前端运行 |
| npm | 9+ | 前端依赖管理 |

### 后端启动（3步）

```bash
cd backend
mvn spring-boot:run
```

验证启动成功：
```bash
curl http://localhost:8080/api/v1/destinations
# 响应: []
```

### 前端启动（3步）

```bash
cd frontend
npm install
npm run dev
```

访问应用：`http://localhost:5173`

### 功能验证

1. **创建 Destination 配置**
```bash
curl -X POST http://localhost:8080/api/v1/destinations \
  -H "Content-Type: application/json" \
  -d '{
    "name": "测试API",
    "url": "https://httpbin.org/post",
    "apiKey": "test-key-123",
    "body": "{\"user_id\": \"{{user_id}}\"}",
    "retryCount": 3
  }'
```

2. **发送通知**
```bash
curl -X POST http://localhost:8080/api/v1/notifications \
  -H "Content-Type: application/json" \
  -d '{
    "destinationId": 1,
    "payload": {"user_id": "12345"}
  }'
# 响应: HTTP 202 Accepted
```

---

## 系统架构

### 架构图

```
┌─────────────┐     ┌─────────────┐     ┌─────────────────┐
│   前端 Vue   │────▶│  SpringBoot │────▶│   外部 API      │
│  localhost   │     │   :8080    │     │  (httpbin.org)  │
│   :5173      │◀────│   H2 DB    │     └─────────────────┘
└─────────────┘     └─────────────┘
                          ▲
                          │
                   ┌──────┴──────┐
                   │ Notification│
                   │   Service   │
                   │  (async)    │
                   └─────────────┘
```

### 核心组件

| 组件 | 技术 | 说明 |
|------|------|------|
| 前端 | Vue 3 + Vite | Destination 配置管理界面 |
| 后端 | Spring Boot 3.2 | REST API 和业务逻辑 |
| 数据库 | H2 | 内存数据库，存储配置和日志 |
| HTTP Client | RestTemplate | 发送通知到外部 API |

### 数据流

1. 前端创建 Destination 配置（API 地址、认证、Body 模板）
2. 业务系统调用 POST /api/v1/notifications 发送通知
3. 后端异步处理通知投递
4. 支持 3 次固定间隔（5秒）重试
5. 记录投递状态到数据库

---

## 配置说明

### application.yml 关键配置

```yaml
server:
  port: 8080

spring:
  h2:
    console:
      enabled: true  # 访问 http://localhost:8080/h2-console
      path: /h2-console
  datasource:
    url: jdbc:h2:mem:notificationdb
    driver-class-name: org.h2.Driver
    username: sa
    password:

notification:
  retry:
    max-attempts: 3       # 最大重试次数
    interval: 5000        # 重试间隔（毫秒）
  http:
    connect-timeout: 5000  # 连接超时（毫秒）
    read-timeout: 10000    # 读取超时（毫秒）
```

### 重试机制

- **投递语义**：至少一次（At-Least-Once）
- **重试间隔**：5 秒（固定）
- **最大重试**：3 次
- **重试条件**：HTTP 状态码非 2xx 或网络异常
- **重试耗尽后**：记录 FAILED 状态到数据库

---

## API 参考

### Destination API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/v1/destinations | 获取所有配置 |
| GET | /api/v1/destinations/{id} | 获取单个配置 |
| POST | /api/v1/destinations | 创建配置 |
| PUT | /api/v1/destinations/{id} | 更新配置 |
| DELETE | /api/v1/destinations/{id} | 删除配置 |

### Notification API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/v1/notifications | 发送通知（异步） |

### 请求示例

**创建 Destination：**
```json
{
  "name": "广告系统",
  "url": "https://api.ad.com/notify",
  "apiKey": "key-xxx",
  "headers": "Content-Type: application/json",
  "body": "{\"user_id\": \"{{user_id}}\"}",
  "retryCount": 3
}
```

**发送通知：**
```json
{
  "destinationId": 1,
  "payload": {
    "user_id": "U12345"
  }
}
```

### 错误响应格式

```json
{
  "success": false,
  "error": "DESTINATION_NOT_FOUND",
  "message": "Destination with id 999 not found"
}
```

**错误码：**
| 错误码 | 说明 |
|--------|------|
| DESTINATION_NOT_FOUND | Destination 不存在 |
| VALIDATION_ERROR | 请求参数校验失败 |
| NOTIFICATION_FAILED | 通知投递失败（已重试） |

---

## FAQ

### Q1: 端口被占用怎么办？

```bash
# 查找占用端口的进程
lsof -i :8080

# 杀死进程
kill -9 <PID>
```

### Q2: CORS 跨域错误如何解决？

确保后端已配置 CORS。本系统使用 `allowedOriginPatterns("http://localhost:*")` 允许所有本地端口。

如果仍有问题，检查浏览器控制台是否有具体的 CORS 错误信息。

### Q3: 重试机制是怎样的？

1. 通知发送后，立即返回 HTTP 202
2. 后端异步投递通知
3. 如果投递失败，5 秒后重试
4. 最多重试 3 次
5. 所有重试失败后，状态记录为 FAILED

### Q4: 如何访问 H2 Console？

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:notificationdb`
- Username: `sa`
- Password: (空)

### Q5: 如何查看通知投递状态？

通过 H2 Console 执行：
```sql
SELECT * FROM notification_log ORDER BY created_at DESC;
```

### Q6: Body 模板如何使用？

Body 模板使用 `{{field}}` 占位符：

**模板：**
```json
{"user_id": "{{user_id}}", "action": "{{action}}"}
```

**Payload：**
```json
{"user_id": "U123", "action": "purchase"}
```

**最终发送：**
```json
{"user_id": "U123", "action": "purchase"}
```

### Q7: Maven/npm 安装失败怎么办？

**Maven 使用国内镜像：**
在 `pom.xml` 或 `~/.m2/settings.xml` 中配置阿里云镜像。

**npm 清理缓存：**
```bash
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

---

## 问题理解

### 需求描述

```
企业内部多个业务系统在关键事件发⽣时，需要调⽤外部系统供应商提供的 HTTP(S) API进⾏通知。例如：

- ⽤户通过第三⽅⼴告系统引流并成功注册后，通知对应的⼴告系统
- ⽤户订阅付款成功后，通知 CRM 系统更改 Contact 状态
- ⽤户购买商品后，通知库存系统进⾏库存变更

不同供应商的 API：
- 请求地址不同
- Header / Body 格式不同

业务系统本身：
- 不需要关⼼外部 API 的返回值
- 只需确保通知请求能够被稳定、可靠地送达
```

### 我的理解

- **API 系统**：这是一个调用三方服务时，可靠消息的投递问题
  - 不需要关心外部 API 的返回值，应该是特指 response body
  - 需要 response status code 来确定是否送达
  - 要保证可靠性
- **发送方**：企业内部多个业务系统（可以认为是微服务，完全可控）
  - 发送方可能使用的技术栈不统一
  - 接收方的认证方式有很多
- **接收方**：外部系统供应商提供的服务（不可控）
  - 接收方可能没有多套环境匹配

---

## 系统边界

### 哪些问题你选择在这个系统中解决？

- 统一接收业务系统的通知请求
- 统一管理外部 API 的配置（地址、认证、超时，重试策略）
- 统一处理投递失败的重试逻辑
- 记录投递状态（成功/失败），支持后续排查

### 哪些问题你明确选择不解决？为什么？

- **外部 API 返回值处理**：业务系统明确不需要关心返回值
- **外部 API 的回调**：当前需求为单向通知，暂不处理回调
- **复杂消息路由**：当前只有 1:1 的通知映射，暂不需要复杂路由

---

## MVP 设计

### 核心目标

构建最小可用产品（MVP)，业务系统能发送通知到外部 API。

### MVP 功能

- 业务系统通过 HTTP POST 发送通知
- 支持 API Key 认证方式
- 支持 3 次固定间隔重试
- Destination 配置管理（CRUD）
- 通知状态记录

### MVP 技术栈

| 组件 | 技术选型 | 原因 |
|------|----------|------|
| 前端 | Vue 3 + Vite | 主流框架，生态成熟 |
| 后端 | SpringBoot | 企业级成熟框架，团队熟悉 |
| 数据库 | H2 | 零配置嵌入式数据库 |

---

## 可靠性与失败处理

### 通知投递语义

**至少一次（At-Least-Once）**

- 外部 API 通知通常为幂等操作
- 优先保证不丢消息，接受少量重复

### 重试策略

**固定间隔重试 3 次**

- 重试间隔：5s（固定）
- 最大重试：3次

**重试耗尽后：**
- 记录失败状态和原因到数据库
- 支持通过数据库查询失败记录

---

## 取舍与演进

### MVP 选择简化而非复杂的原因

| 原AI方案 | MVP简化后 | 原因 |
|----------|-----------|------|
| 4种认证（API Key/Bearer/Basic/OAuth2） | 仅API Key | 最常用，实现最简 |
| 指数退避重试（5次） | 固定间隔重试3次 | 足够应对临时故障 |
| Mustache模板引擎 | 简单占位符`{{field}}` | 满足大多数场景 |
| 动态Header替换 | 仅静态Header | 大多数API仅需静态Header |
| 敏感信息加密 | MVP明文存储 | 第一版快速验证 |
| 前端登录认证 | 不需要（内部工具） | 减少复杂度 |

### 过度设计（已不采纳）

| 过度设计 | 不采纳原因 |
|----------|------------|
| 消息队列（RabbitMQ/RocketMQ） | 增加运维复杂度，第一版优先简单 |
| 死信队列 | 3次重试足够应对大多数场景 |
| 限流机制 | 第一版量级有限，聚焦核心功能 |
| 监控面板 | 先记录状态到数据库，后续按需添加 |
| 前端登录 | MVP作为内部工具，暂不需要认证 |

### 后续演进规划

| 优先级 | 功能 | 触发条件 |
|--------|------|----------|
| P1 | 模板引擎（变量替换） | 需要动态body |
| P1 | 指数退避重试 | 需要更智能的重试策略 |
| P2 | Bearer Token认证 | 特定API需求 |
| P2 | 动态Header | 特定API需求 |
| P2 | Basic Auth | 特定API需求 |
| P3 | 监控面板 | 运营需求 |
| P3 | 限流 | 防止外部API被刷 |
| P3 | 前端登录 | 安全要求 |
| P3 | 死信队列 | 需要人工处理失败通知 |

---

## AI 使用说明

**Date:** 2026-04-17
**Author:** kuanghc

### AI 辅助的关键环节

#### 1.1 需求分析与系统设计

**AI 帮助的地方：**
- 提供了详细的系统架构设计建议
- 生成了 Spring Boot + Vue 3 的技术栈方案
- 输出了完整的 OpenSpec 变更管理流程

**人工决策：**
- 选择了 H2 内存数据库而非 MySQL（MVP 阶段零配置优先）
- 选择了固定间隔重试而非指数退避（简化优先）

#### 1.2 技术选型

**AI 帮助的地方：**
- 建议使用 Lombok 减少样板代码
- 建议使用 Vite 作为 Vue 3 的构建工具
- 输出了详细的目录结构建议

**人工决策：**
- 拒绝了 AI 建议的消息队列方案（RabbitMQ/RocketMQ），认为运维复杂度过高
- 拒绝了 AI 建议的死信队列设计，认为 3 次重试足够

#### 1.3 代码实现

**AI 帮助的地方：**
- 生成了 Spring Boot 基础代码结构
- 生成了 Vue 3 前端组件代码
- 提供了占位符替换的初始实现

**人工修正：**
- 发现占位符替换逻辑错误：AI 原来是用 payload JSON 替换 body，需要修正为用 payload 值替换 body 模板中的占位符
- 发现缺少 `body` 字段，补充了 Destination 实体的 body 字段
- 添加了 CORS 配置解决跨域问题

#### 1.4 问题排查

**AI 帮助的地方：**
- 提供了 Lombok annotation processor 配置问题的解决方案
- 给出了 schema.sql 初始化问题的排查思路

**人工决策：**
- 设置 `spring.sql.init.mode: never` 解决 schema.sql 导致的启动失败

### 未采纳的 AI 建议

#### 过度设计建议

| AI 建议 | 未采纳原因 |
|---------|------------|
| 使用消息队列（RabbitMQ/RocketMQ） | MVP 阶段运维复杂度过高，聚焦核心功能 |
| 死信队列（Dead Letter Queue） | 3 次重试足够应对大多数临时故障 |
| 限流机制 | 第一版量级有限，聚焦核心功能 |
| 多种认证方式（Bearer/Basic/OAuth2） | MVP 只实现 API Key 最常用场景 |
| 监控面板 | 先记录状态到数据库，后续按需添加 |
| 前端登录认证 | MVP 作为内部工具，暂不需要认证 |

#### 不适用的方案

| AI 建议 | 未采纳原因 |
|---------|------------|
| 指数退避重试策略 | 固定 5 秒间隔更简单，满足大多数场景 |
| Mustache 模板引擎 | 简单占位符 `{{field}}` 满足大多数场景 |
| 动态 Header 替换 | 大多数 API 仅需静态 Header |
| 敏感信息加密 | MVP 明文存储，第一版快速验证 |

### 人工关键决策

#### 系统边界决策

**决策：只解决通知投递问题，不解决以下问题：**

| 问题 | 不解决原因 |
|------|------------|
| 外部 API 返回值处理 | 业务系统明确不需要关心返回值 |
| 外部 API 的回调 | 当前需求为单向通知，暂不处理 |
| 复杂消息路由 | 当前只有 1:1 的通知映射 |

**判断依据：** 需求明确说明"业务系统本身不需要关心外部 API 的返回值"，聚焦可靠投递。

#### 可靠性策略

**决策：选择 At-Least-Once（至少一次）投递语义**

**判断依据：**
- 外部 API 通知通常为幂等操作
- 优先保证不丢消息，接受少量重复
- 符合"只需确保通知请求能够被稳定、可靠地送达"的需求

**重试策略：**
- 固定间隔 5 秒重试
- 最多重试 3 次
- 重试耗尽后记录失败状态到数据库

**判断依据：** 简单可靠的策略，足够应对临时故障。

### AI 使用统计

| 环节 | AI 贡献度 | 人工修正 |
|------|-----------|----------|
| 需求分析 | 60% | 40% |
| 技术选型 | 40% | 60% |
| 代码实现 | 80% | 20% |
| 问题排查 | 50% | 50% |

### 关键教训

1. **AI 生成代码需要仔细验证**：占位符替换逻辑 AI 最初实现有误，需要人工修正
2. **MVP 阶段要抵制过度设计**：AI 倾向提供完整方案，需要人工判断简化
3. **技术选型要结合实际情况**：H2 内存数据库适合 MVP，但不适合生产环境
