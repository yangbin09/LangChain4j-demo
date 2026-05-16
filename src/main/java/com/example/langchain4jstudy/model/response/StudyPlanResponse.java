package com.example.langchain4jstudy.model.response;

import com.example.langchain4jstudy.model.StudyPlanDayItem;
import com.example.langchain4jstudy.model.enums.StudyPlanLevelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 学习计划生成结果。
 *
 * <p>用于承接大模型生成的结构化学习计划结果。</p>
 * <p>该对象会作为接口响应直接返回给前端或调用方。</p>
 *
 * <p>注意：</p>
 * <ul>
 *     <li>字段命名要保持清晰，避免使用 plan、data、info 这类过于模糊的名称。</li>
 *     <li>枚举字段要求大模型返回固定英文枚举值，避免返回中文导致解析失败。</li>
 *     <li>集合字段建议在提示词中明确要求不能为空，否则模型可能漏字段。</li>
 * </ul>
 *
 * @author yang xiao
 */
@Data
@NoArgsConstructor
public class StudyPlanResponse {

    /**
     * 学习计划标题。
     *
     * <p>例如：7 天 LangChain4j 实战学习计划。</p>
     */
    private String title;

    /**
     * 学习难度等级。
     *
     * <p>只能返回 BEGINNER、INTERMEDIATE、ADVANCED 中的一个。</p>
     */
    private StudyPlanLevelEnum level;

    /**
     * 学习计划摘要。
     *
     * <p>用于说明该学习计划适合什么人、核心目标是什么。</p>
     */
    private String summary;

    /**
     * 每日学习安排列表。
     *
     * <p>每个元素代表一天的学习主题、任务、产出物和验收标准。</p>
     */
    private List<StudyPlanDayItem> days;

    /**
     * 风险提醒列表。
     *
     * <p>例如学习周期过短、目标过大、前置基础不足等提醒。</p>
     */
    private List<String> warnings;
}