package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.StudyPlanAssistant;
import com.example.langchain4jstudy.model.StudyPlanDayItem;
import com.example.langchain4jstudy.model.StudyPlanRequest;
import com.example.langchain4jstudy.model.StudyPlanResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study-plan")
public class StudyPlanController {

    private final StudyPlanAssistant studyPlanAssistant;

    public StudyPlanController(StudyPlanAssistant studyPlanAssistant) {
        this.studyPlanAssistant = studyPlanAssistant;
    }

    @PostMapping("/generate")
    public StudyPlanResponse generate(@RequestBody StudyPlanRequest request) {
        return studyPlanAssistant.generate(request.getGoal());
    }
}