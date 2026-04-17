## Context

本次设计基于 review-mvp-alignment 审查，补充以下明确说明，确保实现一致性。

## 明确说明

### 1. 重试策略

**固定间隔重试 3 次，间隔 5 秒**

```
try 1 → 等待 5s → try 2 → 等待 5s → try 3 → 记录失败
```

- 重试条件：HTTP 状态码非 2xx 或超时
- 重试完成：记录最终状态（success/failed）

### 2. 投递语义

**同步投递，HTTP 202 即返回**

```
业务系统 → POST /api/v1/notifications → 202 Accepted
                                              ↓
                                         同步投递
                                              ↓
                                    记录状态 → 返回
```

- API 收到请求后，同步执行投递
- 投递完成（包括重试）后才返回响应
- 返回 202 表示"请求已接收，投递完成"

### 3. 状态查询

**MVP 阶段不实现状态查询 API**

- H2 数据库可直查 `notification_log` 表
- 前端状态查询移至 P3 监控面板

### 4. API 响应码

| 场景 | 状态码 |
|------|--------|
| 通知投递成功 | 202 Accepted |
| Destination 不存在 | 404 Not Found |
| 请求参数缺失 | 400 Bad Request |
| 投递失败（重试耗尽）| 202 Accepted（记录失败状态）|

## Goals / Non-Goals

### Goals (MVP)
- 业务系统可通过 HTTP POST 发送通知
- 支持 API Key 认证方式
- 支持 3 次固定间隔（5s）重试
- Destination 配置管理（CRUD）
- 同步投递，HTTP 202 返回

### Non-Goals (P3)
- 通知状态查询 API
- 前端监控面板
- 多种认证方式
- 模板引擎
- 动态 Header
