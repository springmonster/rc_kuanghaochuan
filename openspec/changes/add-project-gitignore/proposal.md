## Why

项目根目录缺少 `.gitignore` 文件，导致编译产物（`target/`、`dist/`）、IDE配置（`.idea/`）和其他可能包含敏感信息的文件可能被提交到 git 仓库。

## What Changes

- 创建项目根目录 `.gitignore` 文件
- 配置合理的忽略规则

## Capabilities

### New Capabilities
- `project-gitignore`: 项目级 gitignore 配置

## Impact

- Git 仓库：保持干净，避免提交编译产物
