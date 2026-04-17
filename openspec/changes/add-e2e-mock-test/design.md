## Context

端到端测试需要模拟真实场景：
1. Mock 发送方（业务系统）发送通知请求
2. 通知系统接收请求，投递到 httpbin.org
3. httpbin.org 返回响应
4. 通知系统记录状态

## Goals / Non-Goals

**Goals:**
- 创建可执行的 Shell 脚本
- 验证完整通知流程
- 提供清晰的测试输出

**Non-Goals:**
- 不实现复杂的测试框架
- 不实现自动化 CI/CD 测试

## Mock 发送方设计

### 脚本位置
```
scripts/send-test-notification.sh
```

### 脚本功能
1. 创建测试用的 Destination 配置
2. 发送测试通知请求
3. 输出响应结果

### 测试流程
```bash
# 1. 创建 Destination
curl -X POST http://localhost:8080/api/v1/destinations \
  -H "Content-Type: application/json" \
  -d '{
    "name": "百度测试",
    "url": "https://httpbin.org/post",
    "apiKey": "test-key",
    "body": "{\"query\": \"{{query}}\"}",
    "retryCount": 3
  }'

# 2. 发送通知
curl -X POST http://localhost:8080/api/v1/notifications \
  -H "Content-Type: application/json" \
  -d '{
    "destinationId": 1,
    "payload": {"query": "测试搜索"}
  }'
```

## 接收方配置

- URL: `https://httpbin.org/post`
- 方法: POST
- 返回: JSON 响应，包含接收到的数据

## Risks / Trade-offs

- [Risk] httpbin.org 服务不可用 → 使用 httpbin.org/anything 作为备选
- [Risk] 网络问题导致测试失败 → 提供重试机制
