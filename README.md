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
│   └── Assistant.java                  # AI 助手接口（带系统提示词）
├── config/
│   └── LangChain4jConfig.java          # LangChain4j 配置
└── controller/
    ├── ChatController.java             # 基础对话接口 /chat
    └── AssistantController.java        # AI 助手接口 /assistant/chat

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

## 分支规范

See [CONTRIBUTING.md](CONTRIBUTING.md)

## 学习章节

| 章节 | 分支 | 说明 |
|------|------|------|
| 第一章 | `tutorial/2026-05-15-chapter1` | Spring Boot 接入 DeepSeek，跑通聊天接口 |
| 第二章 | `tutorial/2026-05-15-chapter2` | 添加 AI 助手接口，支持系统提示词 |

## 项目地址

https://github.com/yangbin09/LangChain4j-demo
