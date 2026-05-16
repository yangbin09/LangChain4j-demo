package com.example.langchain4jstudy.model.request;


import lombok.Data;

/**
 * 学习计划请求对象。
 *
 * <p>用于接收用户输入的学习目标。</p>
 *
 * @author yang xiao
 */
@Data
public class StudyPlanRequest {

    /**
     * 用户的学习目标。
     *
     * <p>描述用户想要达成的学习目标。</p>
     * <p>例如：我想用7天入门LangChain4j</p>
     */
    private String goal;

    public StudyPlanRequest() {
    }
}
