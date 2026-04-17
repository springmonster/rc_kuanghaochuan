## 1. 基础设施搭建

- [ ] 1.1 搭建 RabbitMQ 集群（开发环境）
- [ ] 1.2 创建项目基础结构（目录、依赖）
- [ ] 1.3 配置日志和监控埋点基础设施

## 2. 配置管理模块 (notification-config)

- [ ] 2.1 设计并创建 Destination 配置数据模型
- [ ] 2.2 实现 Destination 配置 CRUD API
- [ ] 2.3 实现 Mustache 模板引擎集成
- [ ] 2.4 实现 Header 静态/动态值替换逻辑
- [ ] 2.5 配置加密存储（敏感信息）

## 3. 通知路由模块 (notification-router)

- [ ] 3.1 实现 HTTP POST /api/v1/notifications 接口
- [ ] 3.2 实现请求 Payload 验证逻辑
- [ ] 3.3 实现基于 Destination ID 的消息队列投递
- [ ] 3.4 实现限流逻辑（per source system）
- [ ] 3.5 实现 202 Accepted 快速响应

## 4. 通知投递模块 (notification-dispatcher)

- [ ] 4.1 实现消息队列消费者
- [ ] 4.2 实现 HTTP 客户端（支持 HEADERS/BODY）
- [ ] 4.3 实现多种认证方式支持（API Key, Bearer, Basic Auth, OAuth2）
- [ ] 4.4 实现指数退避重试策略
- [ ] 4.5 实现超时处理逻辑
- [ ] 4.6 对接 Config 模块获取 Destination 配置

## 5. 监控模块 (notification-monitor)

- [ ] 5.1 实现 Prometheus metrics 端点 (/metrics)
- [ ] 5.2 实现核心指标埋点（sent/delivered/failed/duration）
- [ ] 5.3 实现基于 destination/source_system 的多维度标签
- [ ] 5.4 配置 Grafana 看板
- [ ] 5.5 配置告警规则（成功率低于阈值）

## 6. 死信队列模块 (dead-letter-queue)

- [ ] 6.1 实现 DLQ 消息存储
- [ ] 6.2 实现超过最大重试次数后转入 DLQ 逻辑
- [ ] 6.3 实现 DLQ 管理界面 API（查看、重试、删除）
- [ ] 6.4 实现 DLQ 指标埋点
- [ ] 6.5 实现 DLQ 保留期自动清理

## 7. 联调与测试

- [ ] 7.1 单元测试覆盖（各模块核心逻辑）
- [ ] 7.2 集成测试（Router → Queue → Dispatcher → External API）
- [ ] 7.3 模拟外部 API 的测试环境
- [ ] 7.4 重试机制验证
- [ ] 7.5 DLQ 流转验证

## 8. 生产就绪

- [ ] 8.1 RabbitMQ 生产集群部署
- [ ] 8.2 Config 加密方案生产验证
- [ ] 8.3 监控告警生产验证
- [ ] 8.4 业务系统接入示例
