## Why

前端应用运行在 `http://localhost:5173`，而后端 API 运行在 `http://localhost:8080`。跨域请求被浏览器 CORS 策略阻止，导致前端无法调用后端 API。

## What Changes

- 在 Spring Boot 后端配置全局 CORS 策略
- 允许 `http://localhost:5173` 跨域访问所有 API 端点
- 支持跨域请求所需的 HTTP 方法（GET, POST, PUT, DELETE, OPTIONS）

## Capabilities

### New Capabilities
- `cors-configuration`: 跨域配置能力，支持前端 origin 访问后端 API

## Impact

- 后端：`NotificationApplication.java` 或新增 CORS 配置类
- 前端：无需修改，现有 API 调用保持不变
