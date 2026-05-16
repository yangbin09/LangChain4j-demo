package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.StudyPlanAssistant;
import com.example.langchain4jstudy.model.request.StudyPlanRequest;
import com.example.langchain4jstudy.model.response.StudyPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 学习计划控制器。
 *
 * <p>提供学习计划生成接口，根据用户目标生成结构化的学习计划。</p>
 * <p>返回内容包含每日主题、任务、产出物和验收标准。</p>
 *
 * @author yang xiao
 */
@RestController
@RequestMapping("/study-plan")
@Slf4j
public class StudyPlanController {

    private final StudyPlanAssistant studyPlanAssistant;

    public StudyPlanController(StudyPlanAssistant studyPlanAssistant) {
        this.studyPlanAssistant = studyPlanAssistant;
    }

    /**
     * 生成学习计划。
     *
     * <p>接收用户的学习目标，返回结构化的学习计划。</p>
     *
     * @param request 学习计划请求，包含目标描述
     * @return 结构化的学习计划
     */
    @PostMapping("/generate")
    public StudyPlanResponse generate(@RequestBody StudyPlanRequest request) {
        log.info("收到学习计划生成请求，目标：{}", request.getGoal());
        StudyPlanResponse response = studyPlanAssistant.generate(request.getGoal());
        log.info("生成学习计划完成，标题：{}", response.getTitle());
        return response;
    }
}
