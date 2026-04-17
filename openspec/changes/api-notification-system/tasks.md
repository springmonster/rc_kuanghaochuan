## MVP 实现任务

### Phase 1: 后端核心

- [ ] 1.1 搭建 SpringBoot 项目
- [ ] 1.2 集成 H2 数据库
- [ ] 1.3 设计 Destination 配置表（name, url, api_key, headers, retry_count）
- [ ] 1.4 实现 Destination CRUD API

### Phase 2: 通知投递

- [ ] 2.1 实现 POST /api/v1/notifications 接口
- [ ] 2.2 实现 HTTP 客户端发送请求
- [ ] 2.3 实现 API Key 认证
- [ ] 2.4 实现占位符替换 `{{field}}`
- [ ] 2.5 实现固定间隔重试（3次）
- [ ] 2.6 实现状态记录（pending/success/failed）

### Phase 3: 前端（可选 MVP 先不做）

- [ ] 3.1 搭建 Vue 3 项目
- [ ] 3.2 实现 Destination 列表页面
- [ ] 3.3 实现 Destination 创建/编辑页面
- [ ] 3.4 对接后端 API

## 后续功能（按优先级）

| 优先级 | 功能 | 说明 |
|---------|------|------|
| P1 | 模板引擎 | 支持 Mustache 变量替换 |
| P1 | 指数退避 | 更智能的重试策略 |
| P2 | Bearer Token | 另一种认证方式 |
| P2 | 动态 Header | 支持 {{variable}} 占位符 |
| P3 | 监控面板 | Grafana 看板 |
| P3 | 限流 | 保护外部 API |
| P3 | 前端登录 | 安全认证 |
