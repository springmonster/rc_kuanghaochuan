## MVP Requirements (当前实现)

### Requirement: Configuration Management Page
The frontend SHALL provide a web interface for managing destinations.

#### Scenario: List destinations
- **WHEN** admin opens page
- **THEN** all destinations are displayed in a table

#### Scenario: Create destination
- **WHEN** admin fills form and submits
- **THEN** destination is created via POST /api/v1/destinations

#### Scenario: Edit destination
- **WHEN** admin clicks edit
- **THEN** form is populated for editing
- **AND** PUT /api/v1/destinations/{id} is called on submit

#### Scenario: Delete destination
- **WHEN** admin clicks delete
- **THEN** DELETE /api/v1/destinations/{id} is called

## Implementation Details

### Frontend Structure

```
frontend/
├── src/
│   ├── api/
│   │   └── destination.js      # API 调用封装
│   ├── views/
│   │   └── destination/
│   │       ├── DestinationList.vue   # 列表页
│   │       └── DestinationForm.vue   # 创建/编辑表单
│   ├── router/
│   │   └── index.js            # Vue Router 配置
│   ├── App.vue
│   └── main.js
└── package.json
```

### Routes

| Path | Component | Description |
|------|-----------|------------|
| / | redirect to /destinations | |
| /destinations | DestinationList | 配置列表 |
| /destinations/new | DestinationForm | 新建配置 |
| /destinations/:id/edit | DestinationForm | 编辑配置 |

## Future Requirements (后续添加)

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 监控面板 | 投递统计看板 | P3 |
| 手动重试 | 失败通知重试 | P3 |
| 登录认证 | 管理员登录 | P3 |
