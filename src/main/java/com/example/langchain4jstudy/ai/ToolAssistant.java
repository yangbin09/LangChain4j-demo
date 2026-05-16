package com.example.langchain4jstudy.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 工具调用 AI 助手。
 *
 * <p>用于演示 LangChain4j Tool Calling 能力。</p>
 *
 * @author yang xiao
 */
public interface ToolAssistant {

    /**
     * 与工具增强助手对话。
     *
     * @param message 用户消息
     * @return AI 最终回答
     */
    @SystemMessage("""
            你是一个 LangChain4j 学习助手。
            
            你的任务：
            帮助用户学习 LangChain4j，并在需要时调用工具获取真实信息。
            
            工具使用规则：
            1. 用户询问当前时间、日期时，必须调用日期时间工具
            2. 用户询问当前学习进度、学到哪一章、下一章学什么时，必须调用学习进度工具
            3. 用户询问 AiService、Tool Calling、RAG、ChatMemory、Embedding 等术语时，优先调用技术术语工具
            4. 工具返回结果后，你要用中文整理成自然、清晰的回答
            5. 不要暴露工具调用细节，不要说“我调用了某某工具”
            6. 回答要偏实战，每次给一个下一步操作建议
            """)
    String chat(@UserMessage String message);
}