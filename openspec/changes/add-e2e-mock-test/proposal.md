## Why

为了验证整个通知系统的端到端流程，需要一个 Mock 发送方模拟业务系统发送通知请求，同时需要一个真实的接收方（httpbin.org）来接收和响应请求。

## What Changes

- 创建 Shell 脚本作为 Mock 发送方
- 脚本发送 HTTP POST 请求到后端 API
- 使用 httpbin.org/post 作为接收方
- 验证完整流程：发送方 → 通知系统 → 接收方

## Capabilities

### New Capabilities
- `e2e-mock-test`: 端到端测试工具，包含 Mock 发送方脚本

## Impact

- 后端：无需修改
- 前端：无需修改
- 新增测试脚本：`scripts/send-test-notification.sh`
