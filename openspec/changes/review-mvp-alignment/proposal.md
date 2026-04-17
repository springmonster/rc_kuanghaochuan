## Why

审视当前 OpenSpec 文档与 README 中的需求是否匹配，验证是否满足硬性要求，确认 MVP 方案可行性。

## What Changes

**Review Findings（审查发现）:**

### 1. 需求与实现匹配性 ✅

| 需求（来自 README） | 当前实现 | 状态 |
|---------------------|----------|------|
| 业务系统通过 HTTP POST 发送通知 | notification-api: POST /api/v1/notifications | ✅ 匹配 |
| 支持 API Key 认证 | notification-sender: X-API-Key header | ✅ 匹配 |
| 固定间隔重试 3 次 | notification-sender: retry up to 3 times | ✅ 匹配 |
| Destination 配置管理 | notification-config: CRUD | ✅ 匹配 |
| 状态记录 | NotificationLog Entity (tasks.md) | ✅ 匹配 |
| 占位符替换 {{field}} | notification-sender: placeholder replacement | ✅ 匹配 |

### 2. 硬性要求验证 ✅

**来自原始需求的核心要求：**

| 硬性要求 | 满足情况 |
|----------|----------|
| 不同供应商 API 地址不同 | ✅ 通过 Destination.url 配置 |
| 不同供应商 Header/Body 格式不同 | ✅ 通过 Destination.headers + body 占位符配置 |
| 业务系统不需要关心外部 API 返回值 | ✅ 202 Accepted 即返回，不等待外部响应 |
| 需要确保通知请求稳定可靠送达 | ✅ 3 次重试 + 状态记录 |

### 3. 发现的问题 ⚠️

#### 问题 1: 缺少重试间隔明确说明
- **现状**: tasks.md 提到 5s 间隔，但 spec.md 和 design.md 未明确
- **影响**: 实现者可能使用不同间隔
- **建议**: 在 spec.md 中补充

#### 问题 2: 缺少通知状态查询接口
- **现状**: 设计中说"提供基础的状态查询"，但 spec 中无此接口
- **影响**: 业务系统无法查询通知是否送达
- **建议**: MVP 阶段暂不实现，前端展示放 P3

#### 问题 3: 异步投递未明确
- **现状**: notification-api 返回 202，但未说明是同步还是异步投递
- **影响**: 如果同步投递，HTTP 客户端可能超时
- **建议**: 明确为同步投递，投递完成后返回

#### 问题 4: 前端 MVP 状态
- **现状**: tasks.md 标注"可选 MVP 先不做"
- **影响**: MVP 完成后无 UI 管理 Destination
- **建议**: 保持现状，通过 API 手动管理 H2 数据库

### 4. MVP 可行性结论 ✅

**结论: 当前方案是可行的 MVP**

理由：
1. 覆盖核心场景：接收通知 → 查找配置 → 发送请求 → 重试失败
2. 满足原始需求的所有硬性要求
3. 复杂度适中，单个开发者可实现
4. 拆分后的 tasks.md 可操作性强

## Capabilities

### Review Output

**保持不变:**
- Phase 1 后端核心（1.1-1.4）
- Phase 2 通知投递（2.1-2.6）
- Phase 3 前端（标注为 P3）

**建议补充到 design.md:**
- 重试间隔明确为 5s
- 同步投递说明
- 状态查询接口移至 P3

## 技术栈

无变更:
- 前端：Vue 3 + Vite
- 后端：SpringBoot
- 数据库：H2

## 优先级

无调整，当前优先级合理。
