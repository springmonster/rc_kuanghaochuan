## Context

设计 TDD 开发约束的技术实现方案，落地为 `.claude/skills/tdd-constraints.md`。

## 设计目标

1. **可执行**：约束应能在日常开发中落地
2. **可检查**：通过工具验证覆盖率
3. **可持续**：不过于严格，能长期坚持

## 技术方案

### 1. 测试框架选择

**后端 SpringBoot：**
- JUnit 5 + Mockito
- JaCoCo 覆盖率插件
- Maven surefire 插件运行测试

**前端 Vue 3：**
- Vitest（Vite 原生支持）
- Vue Test Utils
- V8 覆盖率或 Istanbul

### 2. 覆盖率检查配置

**Maven (pom.xml 片段)：**
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <configuration>
        <rules>
            <rule>
                <element>BUNDLE</element>
                <limits>
                    <limit>
                        <counter>LINE</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.60</minimum>
                    </limit>
                </limits>
            </rule>
        </rules>
    </configuration>
</plugin>
```

**前端 (vite.config.js 片段)：**
```javascript
test: {
  coverage: {
    provider: 'v8',
    thresholds: {
      lines: 60,
      functions: 60,
      branches: 50
    }
  }
}
```

### 3. TDD 流程落地

**每日开发流程：**
```
1. 需求分析 → 写测试（RED）
2. 运行测试 → 确认失败
3. 写实现代码（GREEN）
4. 运行测试 → 确认通过
5. 重构（REFACTOR）
6. 提交前运行覆盖率检查
```

### 4. CI 集成（可选）

**提交前检查：**
- `mvn test` 运行测试
- `mvn jacoco:check` 检查覆盖率
- 前端：`vitest run --coverage`

**覆盖率门禁：**
- 低于 60% 阻止合并（CI 检查）
- 可按模块设置不同阈值

## 测试命名规范

### 推荐格式

| 测试类型 | 命名格式 | 示例 |
|----------|----------|------|
| 单方法测试 | `should_<expected>_when_<condition>` | `should_return_user_when_id_exists` |
| 边界测试 | `should_<expected>_when_<edge_case>` | `should_throw_when_input_is_null` |
| 集成测试 | `<method>_integration` | `create_user_integration` |

### 示例

```java
// Bad
@Test
void test1() { ... }

// Good
@Test
void shouldReturnDestinationWhenIdExists() { ... }
@Test
void shouldThrowWhenUrlIsMissing() { ... }
```

## AAA 模式

```java
@Test
void shouldReplacePlaceholderWithValue() {
    // Arrange
    String template = "Hello {{name}}";
    Map<String, String> payload = Map.of("name", "World");

    // Act
    String result = PlaceholderReplacer.replace(template, payload);

    // Assert
    assertEquals("Hello World", result);
}
```

## 约束文件结构

```
.claude/skills/
├── tdd-constraints.md      # TDD 约束文档
├── springboot-constraints.md  # 已存在
└── vue3-constraints.md        # 已存在
```

## 工具链总结

| 层级 | 工具 | 用途 |
|------|------|------|
| 后端测试 | JUnit 5 + Mockito | 单元测试 |
| 后端覆盖率 | JaCoCo | 覆盖率报告 |
| 前端测试 | Vitest | 单元测试 |
| 前端覆盖率 | V8/Istanbul | 覆盖率报告 |
| CI | GitHub Actions | 自动化检查 |
