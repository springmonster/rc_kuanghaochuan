## Context

为 API Notification System MVP 创建运行和验证指南文档。

## 设计目标

1. **简洁清晰** - 开发者能在 5 分钟内启动系统
2. **步骤完整** - 覆盖启动、验证、常见问题
3. **可执行** - 每一步都可验证

## 文档结构

```
RUN_GUIDE.md
├── 1. 前置条件
├── 2. 后端启动
├── 3. 前端启动
├── 4. 功能验证
├── 5. 测试覆盖率
└── 6. 常见问题
```

## 关键端口

| 服务 | 端口 | 说明 |
|------|------|------|
| 后端 API | 8080 | SpringBoot |
| H2 Console | 8080/h2-console | 数据库管理 |
| 前端 | 5173 | Vite 开发服务器 |

## 验证检查点

### 后端
- [ ] Spring Boot 启动成功
- [ ] GET /api/v1/destinations 返回 200
- [ ] H2 Console 可访问

### 前端
- [ ] Vite 启动成功
- [ ] 列表页加载正常
- [ ] 新建/编辑表单可用

### 通知功能
- [ ] POST /api/v1/notifications 返回 202
- [ ] 重试逻辑执行
- [ ] NotificationLog 记录正确
