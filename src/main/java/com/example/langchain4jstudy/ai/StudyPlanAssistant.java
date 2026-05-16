package com.example.langchain4jstudy.ai;

import com.example.langchain4jstudy.model.StudyPlanDayItem;
import com.example.langchain4jstudy.model.StudyPlanResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface StudyPlanAssistant {

    @SystemMessage("""
            你是一个非常严格的技术学习教练，擅长给 Java 工程师制定实战学习计划。
            
            你的任务：
            根据用户目标，生成一个结构化学习计划。
            
            规则：
            1. 必须偏实战，不要堆理论
            2. 每一天都必须有明确任务
            3. 每一天都必须有明确产出物
            4. 每一天都必须有验收标准
            5. 不要编造不存在的软件版本
            6. 如果用户目标过大，要在 warnings 中提醒风险
            7. 内容必须适合 Java / Spring Boot 工程师
            8. 回答必须使用中文
            """)
    StudyPlanResponse generate(@UserMessage String userGoal);
}