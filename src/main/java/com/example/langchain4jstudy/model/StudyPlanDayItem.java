package com.example.langchain4jstudy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 学习计划中的每日学习安排。
 *
 * <p>用于描述某一天具体要学什么、做什么、产出什么、如何验收。</p>
 *
 * <p>该对象属于 StudyPlanResponse 的子结构，通常不会单独作为接口返回。</p>
 *
 * @author yang xiao
 */
@Data
@NoArgsConstructor
public class StudyPlanDayItem {

    /**
     * 第几天。
     *
     * <p>从 1 开始，例如 1、2、3。</p>
     */
    private Integer dayIndex;

    /**
     * 当天学习主题。
     *
     * <p>例如：跑通 LangChain4j 对话接口。</p>
     */
    private String topic;

    /**
     * 当天任务清单。
     *
     * <p>要求任务尽量具体，避免出现“学习相关知识”这类空泛表达。</p>
     */
    private List<String> tasks;

    /**
     * 当天产出物。
     *
     * <p>例如：一个可以正常调用 DeepSeek 的 Spring Boot 接口。</p>
     */
    private String deliverable;

    /**
     * 当天验收标准。
     *
     * <p>用于判断当天学习是否真正完成。</p>
     */
    private String acceptanceCriteria;
}