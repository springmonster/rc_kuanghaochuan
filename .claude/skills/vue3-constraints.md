# Vue 3 前端约束

## 技术栈

- **Vue 3** + Composition API
- **Vite** 构建工具
- **Vue Router** 路由
- **Pinia** 状态管理（可选）
- **TypeScript**（可选，MVP阶段可用JavaScript）

## 项目结构

```
frontend/
├── src/
│   ├── api/              # API 调用
│   ├── components/        # 公共组件
│   ├── views/             # 页面组件
│   ├── router/            # 路由配置
│   ├── App.vue
│   └── main.js
├── index.html
├── vite.config.js
└── package.json
```

## 命名规范

### 组件命名

- **PascalCase**：`DestinationList.vue`
- 组件文件与组件名一致

### 目录/文件

| 类型 | 命名 | 示例 |
|------|------|------|
| 页面 | PascalCase + Page | DestinationPage.vue |
| 组件 | PascalCase | DestinationForm.vue |
| API 模块 | 小写+下划线 | destination.js |

### 目录结构

```
views/
├── destination/
│   ├── DestinationList.vue
│   └── DestinationForm.vue
└── notification/
    └── NotificationPage.vue

api/
├── destination.js
└── notification.js
```

## 代码约束

### 组件结构

```vue
<template>
  <!-- 模板 -->
</template>

<script setup>
// 逻辑
</script>

<style scoped>
/* 样式 */
</style>
```

### API 调用

- 封装 fetch/axios
- 统一错误处理
- 返回 Promise

```javascript
// api/destination.js
export const getDestinations = () => {
  return fetch('/api/v1/destinations')
}
```

### 状态管理

- MVP 阶段可先用 props/emit
- 复杂状态使用 Pinia

## 页面约束

### 基础页面

| 页面 | 路由 | 说明 |
|------|------|------|
| 配置列表 | /destinations | Destination 列表页 |
| 配置表单 | /destinations/new 或 /destinations/:id | 新建/编辑页 |

### 页面元素

- 列表页：表格 + 分页 + 操作按钮
- 表单页：表单 + 提交按钮

## 样式约束

- 使用 **scoped** 样式
- 优先使用 **Flexbox** 布局
- 颜色/间距使用 CSS 变量
- 组件样式独立，不污染全局
