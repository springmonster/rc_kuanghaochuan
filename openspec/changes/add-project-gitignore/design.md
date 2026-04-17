## Context

项目包含 backend (Maven/Spring Boot) 和 frontend (Vite/Vue) 两个子项目。需要统一管理 git 忽略规则。

## Goals / Non-Goals

**Goals:**
- 忽略编译产物和临时文件
- 忽略 IDE 配置
- 忽略依赖目录

**Non-Goals:**
- 不包含敏感配置（如 .env）

## Decisions

### Structure

```
# Backend
backend/target/

# Frontend
frontend/node_modules/
frontend/dist/
frontend/dist-ssr/

# IDE
.idea/
.vscode/
.DS_Store

# Logs
*.log
```

## Risks / Trade-offs

无显著风险
