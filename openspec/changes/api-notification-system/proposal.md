## Why

企业内部多个业务系统需要调用外部供应商的 HTTP(S) API 进行通知。当前直接调用存在耦合、可靠性差等问题。

**MVP 目标：** 构建最小可用产品，业务系统能发送通知到外部 API。

## What Changes

1. **通知 API**：业务系统通过 HTTP POST 发送通知
2. **配置管理**：Destination 配置（URL、API Key、Header）
3. **简单重试**：固定间隔重试 3 次
4. **状态记录**：记录投递状态

## Capabilities

### MVP (当前实现)

- `notification-api`: 接收通知请求
- `notification-sender`: HTTP 投递 + API Key 认证 + 重试
- `notification-config`: 简单配置管理

### 后续添加

- `template-engine`: Mustache 变量替换
- `multiple-auth`: Bearer/Basic/OAuth2
- `monitoring`: 监控面板
- `rate-limit`: 限流

## 技术栈

- 前端：Vue 3 + Vite
- 后端：SpringBoot
- 数据库：H2

## 优先级

| 阶段 | 功能 | 优先级 |
|------|------|--------|
| MVP | 基础通知发送 | P0 |
| P1 | 模板引擎 | P1 |
| P1 | 指数退避重试 | P1 |
| P2 | 多种认证 | P2 |
| P2 | 动态 Header | P2 |
| P3 | 监控面板 | P3 |
| P3 | 限流 | P3 |
