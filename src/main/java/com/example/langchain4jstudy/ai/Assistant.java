package com.example.langchain4jstudy.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

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