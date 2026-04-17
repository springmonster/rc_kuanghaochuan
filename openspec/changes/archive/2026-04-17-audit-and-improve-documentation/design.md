# Design: 审核并完善工程文档

## 1. Document Restructuring

### 1.1 README.md 新结构

```
README.md
├── 项目概述（保留现有）
├── 快速开始（新增）
│   ├── 前置条件
│   ├── 后端启动（3步）
│   ├── 前端启动（3步）
│   └── 功能验证（2步）
├── 系统架构（新增，整合现有内容）
│   ├── 架构图（ASCII）
│   ├── 核心组件
│   └── 数据流
├── 配置说明（新增）
│   ├── 后端配置（application.yml）
│   └── API 配置管理
├── API 参考（整合现有）
│   ├── Destination API
│   ├── Notification API
│   └── 错误响应格式（新增）
├── 使用指南（整合 RUN_GUIDE.md）
│   ├── 创建 Destination
│   ├── 发送通知
│   └── 查看状态
├── FAQ（新增）
│   ├── 端口占用
│   ├── CORS 问题
│   ├── 重试机制
│   └── 其他问题
└── AI 使用说明（保留现有）
```

### 1.2 删除 RUN_GUIDE.md

将 RUN_GUIDE.md 的内容整合到 README.md 的对应章节后删除。

## 2. 新增内容说明

### 2.1 快速开始部分

提供最简启动步骤，目标让新人 10 分钟内启动项目。

### 2.2 架构图

使用 ASCII 图展示系统组件和数据流：

```
┌─────────────┐     ┌─────────────┐     ┌─────────────────┐
│   前端 Vue   │────▶│  SpringBoot │────▶│   外部 API      │
│  localhost   │     │   :8080    │     │  (httpbin.org)  │
│   :5173      │◀────│   H2 DB    │     └─────────────────┘
└─────────────┘     └─────────────┘
                          ▲
                          │
                   ┌──────┴──────┐
                   │ Notification│
                   │   Service   │
                   │  (async)    │
                   └─────────────┘
```

### 2.3 错误响应格式

```json
{
  "success": false,
  "error": "DESTINATION_NOT_FOUND",
  "message": "Destination with id 999 not found"
}
```

### 2.4 FAQ 章节

常见问题：
1. 端口被占用怎么办
2. CORS 跨域错误如何解决
3. 重试机制说明
4. H2 Console 访问方法

## 3. 待提交文件处理

### 3.1 frontend/.gitignore

添加 gitignore 文件到 frontend 目录。

### 3.2 frontend/README.md

合并到根目录 README.md。

### 3.3 frontend/openspec/

这是前端相关的设计文档，如果存在则保留或删除取决于其内容。

### 3.4 frontend/package-lock.json

这是 npm 依赖锁定文件，应该被 gitignore。

## 4. 实施步骤

1. 更新 README.md，添加快速开始章节
2. 更新 README.md，添加系统架构章节
3. 更新 README.md，添加配置说明章节
4. 更新 README.md，整合 API 参考章节
5. 更新 README.md，添加 FAQ 章节
6. 更新 README.md，整合 AI 使用说明
7. 提交 frontend 下的待提交文件
8. 删除 RUN_GUIDE.md
9. 更新 tasks.md 标记完成

## 5. Risks

无重大风险，仅文档整理工作。
