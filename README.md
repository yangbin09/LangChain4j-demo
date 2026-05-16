# LangChain4j 学习项目

Spring Boot 3.x 集成 LangChain4j，对接国产大模型（DeepSeek、MiniMax 等）的学习项目。

## 项目背景

学习 LangChain4j 过程中，从最小 Demo 出发，先跑通核心链路，再逐步扩展功能。

## 技术栈

| 技术 | 版本 |
|------|------|
| Spring Boot | 3.3.0 |
| Java | 17+ |
| LangChain4j | 0.12.0 |
| langchain4j-open-ai | 0.12.0 |

## 项目结构

```
src/main/java/com/example/langchain4jstudy
├── LangChain4jStudyApplication.java    # 启动类
├── ai/
│   ├── Assistant.java                  # AI 助手（带系统提示词）
│   ├── StudyPlanAssistant.java         # 学习计划助手
│   ├── ToolAssistant.java              # 工具增强助手（Tool Calling）
│   └── MemoryAssistant.java            # 记忆增强助手（多轮对话）
├── config/
│   └── LangChain4jConfig.java          # LangChain4j 配置
├── controller/
│   ├── ChatController.java             # 基础对话接口 /chat
│   ├── AssistantController.java        # AI 助手接口 /assistant/chat
│   ├── StudyPlanController.java        # 学习计划接口 /study-plan
│   ├── ToolAssistantController.java    # 工具增强助手接口 /tool-assistant/chat
│   └── MemoryAssistantController.java  # 记忆增强助手接口 /memory-assistant
├── model/
│   ├── request/                        # 请求模型
│   │   ├── StudyPlanRequest.java       # 学习计划请求
│   │   ├── ToolChatRequest.java         # 工具对话请求
│   │   └── MemoryChatRequest.java      # 记忆对话请求
│   ├── response/                      # 响应模型
│   │   ├── StudyPlanResponse.java      # 学习计划响应
│   │   ├── LearningProgressResponse.java # 学习进度响应
│   │   └── MemoryChatResponse.java     # 记忆对话响应
│   ├── StudyPlanDayItem.java           # 每日学习项
│   └── enums/
│       └── StudyPlanLevelEnum.java     # 学习难度枚举
├── service/
│   └── ChatMemorySessionService.java   # 对话记忆会话管理
└── tools/                              # 工具类
    ├── DateTimeTool.java               # 日期时间工具
    ├── LearningProgressTool.java       # 学习进度工具
    └── TechTermTool.java               # 技术术语工具

src/main/resources/
└── application.yml                     # 配置文件
```

## 快速开始

### 1. 配置环境变量

```bash
# DeepSeek
export DEEPSEEK_API_KEY="your_api_key"

# 或 MiniMax
export MINIMAX_API_KEY="your_api_key"
export MINIMAX_API_KEY_ID="your_api_key_id"
```

### 2. 启动项目

```bash
mvn spring-boot:run
```

### 3. 测试接口

```bash
# 基础对话
curl -X POST http://localhost:8080/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"你好"}'

# AI 助手对话
curl -X POST http://localhost:8080/assistant/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"帮我解释一下什么是 Spring Boot"}'
```

## 接口说明

### POST /chat

基础对话接口，单轮对话。

**请求：**
```json
{"message": "你好"}
```

**响应：**
```json
{"answer": "你好！有什么可以帮助你的吗？"}
```

### POST /assistant/chat

AI 助手接口，带系统提示词约束，适合技术问答场景。

**请求：**
```json
{"message": "帮我解释一下什么是 Spring Boot"}
```

**响应：**
```json
{"answer": "Spring Boot 是一个用于快速构建 Spring 应用的框架..."}
```

### POST /study-plan

学习计划助手接口，根据主题、难度和天数生成个性化学习计划。

**请求：**
```json
{
  "subject": "Java",
  "level": "BEGINNER",
  "days": 7
}
```

**响应：**
```json
{
  "subject": "Java",
  "level": "BEGINNER",
  "days": 7,
  "plan": [
    {
      "day": 1,
      "content": "Java 基础语法和环境搭建",
      "description": "学习 Java 基本语法、数据类型、变量声明"
    }
  ]
}
```

**难度等级：**
- `BEGINNER` - 初级
- `INTERMEDIATE` - 中级
- `ADVANCED` - 高级

### POST /tool-assistant/chat

工具增强助手接口，支持 Tool Calling 调用工具获取真实信息。

**请求：**
```json
{"message": "现在几点了？"}
```

**响应：**
```json
{"answer": "现在是 2026年5月16日 12:30:45"}
```

**支持的工具：**
- `DateTimeTool` - 获取当前日期时间
- `LearningProgressTool` - 获取学习进度信息
- `TechTermTool` - 查询技术术语解释

### POST /memory-assistant/chat

记忆增强助手接口，支持多轮对话，AI 会记住当前会话的上下文。

**请求：**
```json
{
  "memoryId": "user-001",
  "message": "我叫张三"
}
```

**响应：**
```json
{
  "memoryId": "user-001",
  "answer": "好的，张三，我记住了。有什么可以帮助你的吗？"
}
```

### DELETE /memory-assistant/{memoryId}

清空指定会话的记忆。

**响应：**
```json
{
  "memoryId": "user-001",
  "cleared": true,
  "activeMemoryCount": 5
}
```

## 分支规范

See [CONTRIBUTING.md](CONTRIBUTING.md)

## 学习章节

| 章节 | 分支 | 说明 |
|------|------|------|
| 第一章 | `tutorial/2026-05-15-chapter1` | Spring Boot 接入 DeepSeek，跑通聊天接口 |
| 第二章 | `tutorial/2026-05-15-chapter2` | 添加 AI 助手接口，支持系统提示词 |
| 第三章 | `tutorial/2026-05-16-chapter3` | 添加学习计划助手接口，生成个性化学习计划 |
| 第四章 | `tutorial/2026-05-16-chapter4` | 添加 Tool Calling 工具调用功能 |
| 第五章 | `tutorial/2026-05-16-chapter5` | 添加 Chat Memory 多轮对话功能 |

## 项目地址

https://github.com/yangbin09/LangChain4j-demo
