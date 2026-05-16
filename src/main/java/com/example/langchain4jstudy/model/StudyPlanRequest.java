package com.example.langchain4jstudy.model;


import lombok.Data;

/**
 * 学习计划请求
 */
@Data
public class StudyPlanRequest {
    /**
     * 学习目标
     */
    private String goal;

    public StudyPlanRequest() {
    }
}