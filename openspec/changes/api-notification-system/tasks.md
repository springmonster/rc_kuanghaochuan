## 1. 项目基础搭建

- [ ] 1.1 搭建前端项目（Vue 3 + Vite）
- [ ] 1.2 搭建后端项目（SpringBoot）
- [ ] 1.3 集成 H2 数据库
- [ ] 1.4 配置日志基础设施

## 2. 后端 - 配置管理模块 (Config Service)

- [ ] 2.1 设计 Destination 配置数据模型
- [ ] 2.2 实现 Destination CRUD API
- [ ] 2.3 实现 Mustache 模板引擎
- [ ] 2.4 实现 Header 静态/动态值替换
- [ ] 2.5 配置加密存储（敏感信息）
- [ ] 2.6 API 单元测试

## 3. 后端 - 通知 API 模块 (Notification API)

- [ ] 3.1 实现 POST /api/v1/notifications 接口
- [ ] 3.2 实现请求 Payload 验证
- [ ] 3.3 实现基于 Destination ID 的路由
- [ ] 3.4 实现 202 Accepted 快速响应
- [ ] 3.5 API 单元测试

## 4. 后端 - 通知投递模块 (Sender Service)

- [ ] 4.1 实现 HTTP 客户端
- [ ] 4.2 实现多种认证方式（API Key, Bearer, Basic Auth, OAuth2）
- [ ] 4.3 实现指数退避重试策略
- [ ] 4.4 实现超时处理
- [ ] 4.5 对接 Config 获取 Destination 配置
- [ ] 4.6 记录投递状态（成功/失败/重试中）
- [ ] 4.7 单元测试

## 5. 前端 - 配置管理页面

- [ ] 5.1 搭建前端项目结构
- [ ] 5.2 实现 Destination 列表页面
- [ ] 5.3 实现 Destination 创建/编辑页面
- [ ] 5.4 实现表单验证
- [ ] 5.5 对接后端 Config API

## 6. 联调与测试

- [ ] 6.1 后端 API 集成测试
- [ ] 6.2 前后端联调测试
- [ ] 6.3 模拟外部 API 测试环境
- [ ] 6.4 重试机制验证

## 7. 生产就绪

- [ ] 7.1 Config 加密方案生产验证
- [ ] 7.2 编写部署文档
- [ ] 7.3 业务系统接入示例

## 后续优化项（暂不实现）

- [ ] 8.1 限流机制（令牌桶/滑动窗口）
- [ ] 8.2 Prometheus metrics 端点
- [ ] 8.3 Grafana 监控看板
- [ ] 8.4 告警规则配置
