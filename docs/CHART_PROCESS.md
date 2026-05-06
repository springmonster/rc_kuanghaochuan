# 架构图生成过程文档

**Date:** 2026-05-06
**Tool:** Claude Code + draw.io Mermaid MCP

## 目标

为 API 通知系统项目生成一套完整的软件架构文档图集，覆盖 4+1 架构视图。

## 技术方案

### 工具选择过程

1. **初始方案: Excalidraw MCP** — 配置了 `excalidraw-app-demo` MCP (HTTP 类型)，但连接失败（`Failed to reconnect`），生成的 `.excalidraw` 文件被 JetBrains Excalidraw 插件覆盖为空文件。

2. **最终方案: draw.io Mermaid MCP** — 重新配置了 draw.io MCP Server，通过 Mermaid 语法生成图表，在 draw.io 编辑器中打开。

### 分析过程

1. 阅读 `README.md` 了解系统整体架构和设计决策
2. 遍历 `backend/src/main/java/` 目录结构，理解分层架构
3. 精读关键文件：
   - Controller 层: `DestinationController`、`NotificationController`
   - Service 层: `DestinationServiceImpl`、`NotificationServiceImpl`、接口定义
   - Repository 层: `DestinationRepository`、`NotificationLogRepository`
   - Model 层: `Destination`、`NotificationLog`
   - DTO 层: `ApiResponse`、`DestinationRequestDTO/ResponseDTO`、`NotificationRequestDTO`
   - 工具类: `PlaceholderReplacer`
   - 配置类: `WebConfig`
4. 阅读前端代码: `App.vue`、`router/index.js`、`DestinationList.vue`、`DestinationForm.vue`、`api/destination.js`

## 生成的图表

### 1. 系统架构总览图

- **类型**: Flowchart (TB)
- **覆盖**: 逻辑视图 + 物理视图
- **内容**: 前端 → Controller → Service → Repository → H2 → 外部 API 的全链路
- **颜色标识**: 蓝色(前端)、绿色(后端)、橙色(数据库)、紫色(业务系统)、红色(外部API)

### 2. ER 实体关系图

- **类型**: erDiagram
- **覆盖**: 数据视图
- **内容**: `Destination` 1:N `NotificationLog`，包含所有字段和约束

### 3. 通知发送时序图

- **类型**: sequenceDiagram
- **覆盖**: 进程视图 + 场景视图
- **内容**: 从业务系统发起请求到外部 API 响应的完整调用链，包含 3 次重试循环的 alt 分支

### 4. 通知状态机图

- **类型**: stateDiagram-v2
- **覆盖**: 逻辑视图
- **内容**: PENDING → PENDING(重试) / SUCCESS / FAILED 的状态转换及条件

### 5. 后端类图

- **类型**: classDiagram
- **覆盖**: 开发视图
- **内容**: Controller/Service(接口+实现)/Repository/Model/DTO/Config/Util 的继承、实现、依赖关系

### 6. 重试机制流程图

- **类型**: flowchart TD
- **覆盖**: 进程视图
- **内容**: 参数校验 → 异步处理 → 占位符替换 → HTTP 发送 → 状态码判断 → 重试/结束 的完整决策树

### 7. 前端组件架构图

- **类型**: flowchart TD
- **覆盖**: 开发视图
- **内容**: index.html → main.js → App.vue → Vue Router → Views → axios → 后端 API

## 生成方式

每张图通过 `mcp__drawio__open_drawio_mermaid` 工具调用，传入 Mermaid 语法定义，工具自动：

1. 将 Mermaid 代码编译为 draw.io 内部格式
2. 在浏览器中打开 draw.io 编辑器
3. 用户可在浏览器中直接编辑、调整布局、导出

## 文件清单

```
docs/
├── 架构图.drawio              # 系统架构总览
├── ER 实体关系图.drawio        # 数据模型
├── 通知发送时序图.drawio       # 核心时序
├── 通知状态图.drawio          # 状态机
├── 后端类图.drawio            # 类关系
├── 重试机制流程图.drawio       # 重试逻辑
├── 前端组件架构图.drawio       # 前端结构
└── CHART_PROCESS.md           # 本文档
```
