package com.example.langchain4jstudy.model.enums;

/**
 * 学习计划难度等级枚举。
 *
 * <p>用于标识学习计划的整体难度。</p>
 *
 * <p>注意：枚举值需要保持英文大写，方便大模型稳定输出并被 Java 正确解析。</p>
 *
 * @author yang xiao
 */
public enum StudyPlanLevelEnum {

    /**
     * 入门级。
     *
     * <p>适合刚开始接触某项技术，目标是能跑通基础案例。</p>
     */
    BEGINNER,

    /**
     * 进阶级。
     *
     * <p>适合已经掌握基础用法，目标是能做出完整小项目。</p>
     */
    INTERMEDIATE,

    /**
     * 高级。
     *
     * <p>适合已经具备实战经验，目标是理解工程化、架构设计和复杂场景落地。</p>
     */
    ADVANCED
}