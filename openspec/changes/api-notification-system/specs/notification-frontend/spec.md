## MVP Requirements (当前实现)

### Requirement: Configuration Management Page
The frontend SHALL provide a web interface for managing destinations.

#### Scenario: List destinations
- **WHEN** admin opens page
- **THEN** all destinations are displayed

#### Scenario: Create destination
- **WHEN** admin fills form and submits
- **THEN** destination is created

#### Scenario: Edit destination
- **WHEN** admin clicks edit
- **THEN** form is populated for editing

#### Scenario: Delete destination
- **WHEN** admin clicks delete
- **THEN** destination is removed

## Future Requirements (后续添加)

| 功能 | 说明 | 优先级 |
|------|------|--------|
| 监控面板 | 投递统计看板 | P3 |
| 手动重试 | 失败通知重试 | P3 |
| 登录认证 | 管理员登录 | P3 |
