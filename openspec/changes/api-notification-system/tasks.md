## 1. 项目基础搭建

- [ ] 1.1 创建项目基础结构（目录、依赖）
- [ ] 1.2 配置日志基础设施
- [ ] 1.3 配置监控埋点基础设施

## 2. 配置管理模块 (notification-config)

- [ ] 2.1 设计并创建 Destination 配置数据模型
- [ ] 2.2 实现 Destination 配置 CRUD API
- [ ] 2.3 实现 Mustache 模板引擎集成
- [ ] 2.4 实现 Header 静态/动态值替换逻辑
- [ ] 2.5 配置加密存储（敏感信息）

## 3. 通知 API 模块 (notification-api)

- [ ] 3.1 实现 HTTP POST /api/v1/notifications 接口
- [ ] 3.2 实现请求 Payload 验证逻辑
- [ ] 3.3 实现基于 Destination ID 的路由逻辑
- [ ] 3.4 实现限流逻辑（per source system）
- [ ] 3.5 实现 202 Accepted 快速响应

## 4. 通知投递模块 (notification-sender)

- [ ] 4.1 实现 HTTP 客户端（支持 HEADERS/BODY）
- [ ] 4.2 实现多种认证方式支持（API Key, Bearer, Basic Auth, OAuth2）
- [ ] 4.3 实现指数退避重试策略
- [ ] 4.4 实现超时处理逻辑
- [ ] 4.5 对接 Config 模块获取 Destination 配置
- [ ] 4.6 记录投递状态（成功/失败/重试中）

## 5. 监控模块 (notification-monitor)

- [ ] 5.1 实现 Prometheus metrics 端点 (/metrics)
- [ ] 5.2 实现核心指标埋点（sent/delivered/failed/duration）
- [ ] 5.3 实现基于 destination/source_system 的多维度标签
- [ ] 5.4 配置 Grafana 看板
- [ ] 5.5 配置告警规则（成功率低于阈值）

## 6. 联调与测试

- [ ] 6.1 单元测试覆盖（各模块核心逻辑）
- [ ] 6.2 集成测试（API → Sender → External API）
- [ ] 6.3 模拟外部 API 的测试环境
- [ ] 6.4 重试机制验证

## 7. 生产就绪

- [ ] 7.1 Config 加密方案生产验证
- [ ] 7.2 监控告警生产验证
- [ ] 7.3 业务系统接入示例
