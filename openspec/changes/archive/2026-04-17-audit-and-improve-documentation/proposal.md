# Proposal: 审核并完善工程文档

## 1. Problem Statement

通过审核整个工程，发现以下可以完善的方面：

### 1.1 文档方面

- **README.md** 缺少快速开始（Quick Start）部分，新人难以快速上手
- **README.md** 缺少项目架构图，难以直观理解系统结构
- **README.md** 缺少配置说明文档（API 地址、认证方式）
- RUN_GUIDE.md 与 README.md 有部分内容重叠
- 缺少 FAQ / 故障排查文档
- 缺少 API 错误响应格式说明

### 1.2 代码方面

- RestTemplate 在 Spring Boot 3.x 中已废弃，建议使用 WebClient
- DTO 缺少 Bean Validation 注解（@NotBlank, @NotNull 等）
- 缺少全局异常处理器（GlobalExceptionHandler）
- 通知服务缺少请求超时配置说明

### 1.3 项目完整性

- frontend/.gitignore 和 frontend/README.md 存在但未提交
- 缺少后端配置说明文档（application.yml 配置项说明）

## 2. Goals

1. 完善 README.md，添加快速开始、架构图、配置说明
2. 整合 RUN_GUIDE.md 内容到 README.md，减少重复
3. 添加 FAQ / 故障排查部分
4. 补充 API 错误响应格式说明
5. 提交 frontend 目录下的待提交文件

## 3. Non-Goals

- 不进行代码重构（保留当前 MVP 范围）
- 不添加新的功能点
- 不修改现有的测试用例

## 4. Deliverables

1. 更新后的 README.md，包含：
   - 快速开始（Quick Start）部分
   - 系统架构图
   - 配置说明
   - API 错误响应格式
   - FAQ / 故障排查
2. 删除重复的 RUN_GUIDE.md（内容整合到 README.md）
3. 提交 frontend 下的待提交文件

## 5. Success Metrics

- README.md 包含所有关键章节
- 新人能通过 README.md 在 10 分钟内启动项目
- 无重复文档
