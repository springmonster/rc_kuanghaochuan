## MVP 实现任务

> 每个任务都是一个可单独完成的原子操作，预计耗时 1-2 小时

### Phase 1: 后端核心

#### 1.1 搭建 SpringBoot 项目

- [ ] 1.1.1 创建 Maven 项目结构，配置 pom.xml（spring-boot-starter-web, h2, spring-boot-starter-data-jpa）
- [ ] 1.1.2 创建主应用类 `NotificationApplication.java`
- [ ] 1.1.3 配置 application.yml（端口、数据库连接）
- [ ] 1.1.4 验证项目可运行

#### 1.2 集成 H2 数据库

- [ ] 1.2.1 在 application.yml 中配置 H2 数据源
- [ ] 1.2.2 启用 H2 Console（开发调试用）
- [ ] 1.2.3 创建 data.sql 初始化脚本（后续任务依赖）

#### 1.3 设计 Destination 配置表

- [ ] 1.3.1 创建 Destination Entity 类（id, name, url, api_key, headers, retry_count, created_at, updated_at）
- [ ] 1.3.2 创建 DestinationRepository 接口（继承 JpaRepository）
- [ ] 1.3.3 验证表结构自动创建

#### 1.4 实现 Destination CRUD API

- [ ] 1.4.1 创建 DestinationRequestDTO（接收创建/更新请求）
- [ ] 1.4.2 创建 DestinationResponseDTO（返回给前端）
- [ ] 1.4.3 创建 DestinationService 接口和实现类
- [ ] 1.4.4 创建 DestinationController（GET list, GET one, POST create, PUT update, DELETE）
- [ ] 1.4.5 实现统一响应格式 ApiResponse
- [ ] 1.4.6 编写 CRUD 接口测试

### Phase 2: 通知投递

#### 2.1 实现 POST /api/v1/notifications 接口

- [ ] 2.1.1 创建 NotificationRequestDTO（接收通知请求）
- [ ] 2.1.2 创建 NotificationController，定义 POST 接口
- [ ] 2.1.3 创建 NotificationService 处理通知逻辑
- [ ] 2.1.4 实现基本的请求转发（暂不发送）

#### 2.2 实现 HTTP 客户端发送请求

- [ ] 2.2.1 集成 RestTemplate 或 WebClient
- [ ] 2.2.2 实现请求发送逻辑（动态 URL、Headers、Body）
- [ ] 2.2.3 处理外部 API 响应（记录状态码）

#### 2.3 实现 API Key 认证

- [ ] 2.3.1 根据 Destination 配置选择认证方式
- [ ] 2.3.2 在请求发送时注入 API Key 到 Header
- [ ] 2.3.3 测试认证是否生效

#### 2.4 实现占位符替换 `{{field}}`

- [ ] 2.4.1 定义占位符替换工具类
- [ ] 2.4.2 支持 Body 和 Header 中的 `{{field}}` 替换
- [ ] 2.4.3 编写单元测试验证替换逻辑

#### 2.5 实现固定间隔重试（3次）

- [ ] 2.5.1 定义重试策略（5秒间隔，最大3次）
- [ ] 2.5.2 在 NotificationService 中实现重试逻辑
- [ ] 2.5.3 记录重试次数和原因
- [ ] 2.5.4 编写重试测试用例

#### 2.6 实现状态记录

- [ ] 2.6.1 创建 NotificationLog Entity（保存投递记录）
- [ ] 2.6.2 实现 NotificationLogRepository
- [ ] 2.6.3 在通知流程中记录状态（pending/success/failed）
- [ ] 2.6.4 提供查询接口（可选，MVP 可暂不实现前端展示）

### Phase 3: 前端（P3，后续添加）

#### 3.1 搭建 Vue 3 项目

- [ ] 3.1.1 使用 Vite 创建 Vue 3 项目
- [ ] 3.1.2 配置项目结构和依赖（vue-router, axios）
- [ ] 3.1.3 验证项目可运行

#### 3.2 实现 Destination 列表页面

- [ ] 3.2.1 创建 DestinationList.vue 页面
- [ ] 3.2.2 调用后端 API 获取列表
- [ ] 3.2.3 实现表格展示和分页

#### 3.3 实现 Destination 创建/编辑页面

- [ ] 3.3.1 创建 DestinationForm.vue 组件
- [ ] 3.3.2 实现表单验证
- [ ] 3.3.3 对接后端 POST/PUT API

#### 3.4 对接后端 API

- [ ] 3.4.1 封装 API 调用模块（api/destination.js）
- [ ] 3.4.2 实现删除功能
- [ ] 3.4.3 测试完整 CRUD 流程

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
