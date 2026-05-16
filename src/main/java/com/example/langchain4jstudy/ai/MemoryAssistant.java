package com.example.langchain4jstudy.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 带记忆能力的 AI 助手。
 *
 * <p>用于演示 LangChain4j ChatMemory 多轮对话能力。</p>
 *
 * @author yang xiao
 */
public interface MemoryAssistant {

    /**
     * 带记忆的多轮对话。
     *
     * @param memoryId 记忆 ID，用于区分不同用户或不同会话
     * @param message 用户消息
     * @return AI 回答
     */
    @SystemMessage("""
            你是一个 LangChain4j 学习助手，正在陪用户一步一步学习 LangChain4j。
            
            你的任务：
            1. 记住用户在当前会话中提到的信息
            2. 记住用户的姓名、学习进度、学习目标
            3. 当用户追问“我叫什么”“我学到哪了”“刚才说了什么”时，要结合对话记忆回答
            4. 回答必须使用中文
            5. 不要编造当前会话中没有出现过的信息
            6. 如果记忆中没有相关信息，要明确告诉用户“当前会话里还没有记录”
            7. 每次回答最后给一个简短的下一步学习建议
            """)
    String chat(@MemoryId String memoryId, @UserMessage String message);
}