# SpringBoot 后端约束

## 技术栈

- **SpringBoot 3.x**
- **Java 17+**
- **H2 数据库**（开发/测试）
- **Maven** 构建工具

## 命名规范

### 包命名

```
com.notification
├── config          # 配置类
├── controller      # 控制器
├── service         # 服务层
├── repository      # 数据访问层
├── model           # 实体类
├── dto             # 数据传输对象
└── exception       # 异常定义
```

### 类命名

| 类型 | 后缀 | 示例 |
|------|------|------|
| 控制器 | Controller | NotificationController |
| 服务接口 | Service | NotificationService |
| 服务实现 | ServiceImpl | NotificationServiceImpl |
| 数据访问 | Repository | DestinationRepository |
| 实体 | Entity | Destination |
| DTO | DTO | NotificationRequestDTO |

### 方法命名

| 操作 | 前缀 | 示例 |
|------|------|------|
| 查询 | get/find | getById, findByDestinationId |
| 新增 | create/save | createNotification |
| 更新 | update | updateDestination |
| 删除 | delete | deleteById |

## API 设计约束

### 路径规范

```
/api/v1/{resource}
/api/v1/destinations
/api/v1/notifications
```

### 请求方法

| 方法 | 用途 | 示例 |
|------|------|------|
| GET | 查询 | GET /api/v1/destinations |
| GET | 单个查询 | GET /api/v1/destinations/{id} |
| POST | 创建 | POST /api/v1/destinations |
| PUT | 更新 | PUT /api/v1/destinations/{id} |
| DELETE | 删除 | DELETE /api/v1/destinations/{id} |

### 响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 代码约束

### Controller 层

- 使用 `@RestController`
- 使用 `@RequestMapping` 定义版本前缀
- 参数使用 `@Valid` 校验
- 统一异常处理

### Service 层

- 使用 `@Service` 注解
- 面向接口编程
- 事务管理使用 `@Transactional`

### Repository 层

- 使用 Spring Data JPA
- 自定义查询使用 `@Query`

## 项目结构

```
backend/
├── src/main/java/com/notification/
│   ├── NotificationApplication.java
│   ├── config/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── dto/
│   └── exception/
├── src/main/resources/
│   ├── application.yml
│   └── data.sql
└── pom.xml
```
