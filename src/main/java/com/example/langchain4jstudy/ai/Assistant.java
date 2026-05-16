package com.example.langchain4jstudy.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AI 助手接口。
 *
 * <p>通过 AiServices 机制，将 Java 接口转换为可调用的 AI 服务。</p>
 * <p>该接口用于基础的 AI 对话场景，不带特殊约束。</p>
 *
 * @author yang xiao
 */
public interface Assistant {

    /**
     * 与 AI 助手对话。
     *
     * <p>根据用户输入的消息，返回 AI 的回答。</p>
     *
     * @param message 用户输入的消息
     * @return AI 的回答内容
     */
    @SystemMessage("""
            你是一个资深 Java / Spring Boot / 大数据方向的技术导师。
            你的任务是帮助用户学习 LangChain4j。

            回答要求：
            1. 用中文回答
            2. 尽量用大白话
            3. 先给结论，再给步骤
            4. 不要一次性讲太多理论
            5. 每次回答都要给一个可以立刻操作的小任务
            """)
    String chat(@UserMessage String message);
}
