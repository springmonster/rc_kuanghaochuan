## Context

前端 Vue 3 应用运行在 Vite 开发服务器 (localhost:5173)，后端 Spring Boot 运行在 port 8080。当前后端未配置 CORS，导致所有跨域请求被浏览器阻止。

## Goals / Non-Goals

**Goals:**
- 允许前端应用跨域访问后端 API
- 支持所有标准 HTTP 方法和自定义头

**Non-Goals:**
- 不实现生产环境 CORS 配置（仅开发环境 localhost）
- 不实现 CORS 细粒度控制（按路径配置不同 origin）

## Decisions

### Decision 1: CORS 配置位置

**Choice:** 在 `NotificationApplication.java` 中通过 `WebMvcConfigurer` 配置全局 CORS

**Rationale:**
- Spring Boot 提供便捷的 CORS 配置方式
- 代码集中，便于维护
- 支持细粒度控制（按路径、按方法）

### Decision 2: 允许的 Origin

**Choice:** 只允许 `http://localhost:5173`

**Rationale:**
- 开发环境专用
- 生产环境应使用更严格的 CORS 策略

## Risks / Trade-offs

- [Risk] 生产环境 CORS 配置过于宽松 → **Mitigation**: 当前仅开发环境使用，生产部署前需审查
