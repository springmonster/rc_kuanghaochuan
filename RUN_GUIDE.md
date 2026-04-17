# API Notification System 运行指南

## 1. 前置条件

### 必需环境

| 工具 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | 后端运行 |
| Maven | 3.8+ | 后端构建 |
| Node.js | 18+ | 前端运行 |
| npm | 9+ | 前端依赖管理 |

### 检查版本

```bash
java -version
mvn -version
node -v
npm -v
```

---

## 2. 后端启动

### 2.1 进入后端目录

```bash
cd backend
```

### 2.2 启动 Spring Boot

```bash
mvn spring-boot:run
```

### 2.3 验证启动成功

访问 `http://localhost:8080`，应看到 Spring Boot 启动日志。

### 2.4 API 验证

```bash
# 获取 Destination 列表
curl http://localhost:8080/api/v1/destinations

# 响应: [] (空数组)
```

### 2.5 H2 Console 访问

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:notificationdb`
- Username: `sa`
- Password: (空)

---

## 3. 前端启动

### 3.1 进入前端目录

```bash
cd frontend
```

### 3.2 安装依赖

```bash
npm install
```

### 3.3 启动开发服务器

```bash
npm run dev
```

### 3.4 访问应用

打开浏览器访问: `http://localhost:5173`

---

## 4. 功能验证

### 4.1 创建 Destination

```bash
curl -X POST http://localhost:8080/api/v1/destinations \
  -H "Content-Type: application/json" \
  -d '{
    "name": "测试API",
    "url": "https://httpbin.org/post",
    "apiKey": "test-key-123",
    "body": "{\"user_id\": \"{{user_id}}\"}",
    "retryCount": 3
  }'
```

### 4.2 验证列表

```bash
curl http://localhost:8080/api/v1/destinations
```

### 4.3 发送通知

```bash
curl -X POST http://localhost:8080/api/v1/notifications \
  -H "Content-Type: application/json" \
  -d '{
    "destinationId": 1,
    "payload": {"user_id": "12345"}
  }'
```

### 4.4 验证状态记录

```bash
# 查看日志输出或 H2 Console
# status 应为 SUCCESS 或 FAILED
```

---

## 5. 测试覆盖率

### 5.1 运行单元测试

```bash
cd backend
mvn test
```

### 5.2 生成覆盖率报告

```bash
mvn test jacoco:report
```

报告位置: `target/site/jacoco/index.html`

### 5.3 前端构建

```bash
cd frontend
npm run build
```

---

## 6. 常见问题

### Q1: 端口被占用

```bash
# 查找占用端口的进程
lsof -i :8080

# 杀死进程
kill -9 <PID>
```

### Q2: Maven 依赖下载慢

使用国内镜像，在 `pom.xml` 或 `~/.m2/settings.xml` 中配置:

```xml
<mirror>
  <id>aliyun</id>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

### Q3: npm 安装失败

```bash
# 清理缓存
npm cache clean --force

# 重新安装
rm -rf node_modules package-lock.json
npm install
```

### Q4: 前端无法连接后端 API

开发环境下前端运行在 5173 端口，后端在 8080 端口。

确保后端已启动，然后在前端页面操作。

---

## 7. 项目结构

```
.
├── backend/                 # Spring Boot 后端
│   ├── src/main/java/
│   │   └── com/notification/
│   │       ├── controller/    # REST API
│   │       ├── service/      # 业务逻辑
│   │       ├── repository/    # 数据访问
│   │       ├── model/         # 实体
│   │       └── dto/           # 数据传输对象
│   └── pom.xml
├── frontend/               # Vue 3 前端
│   ├── src/
│   │   ├── api/           # API 调用
│   │   ├── views/          # 页面组件
│   │   └── router/         # 路由配置
│   └── package.json
├── openspec/              # 设计文档
└── RUN_GUIDE.md           # 本文档
```

---

## 8. API 参考

### Destination API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/v1/destinations | 获取所有配置 |
| GET | /api/v1/destinations/{id} | 获取单个配置 |
| POST | /api/v1/destinations | 创建配置 |
| PUT | /api/v1/destinations/{id} | 更新配置 |
| DELETE | /api/v1/destinations/{id} | 删除配置 |

### Notification API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/v1/notifications | 发送通知 |

### 请求示例

**创建 Destination:**
```json
{
  "name": "广告系统",
  "url": "https://api.ad.com/notify",
  "apiKey": "key-xxx",
  "headers": "Content-Type: application/json",
  "body": "{\"user_id\": \"{{user_id}}\"}",
  "retryCount": 3
}
```

**发送通知:**
```json
{
  "destinationId": 1,
  "payload": {
    "user_id": "U12345"
  }
}
```
