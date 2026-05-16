package com.example.langchain4jstudy.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 学习进度响应对象。
 *
 * <p>用于描述当前 LangChain4j 学习进度。</p>
 *
 * @author yang xiao
 */
@Data
@Builder
public class LearningProgressResponse {

    /**
     * 当前已完成章节序号。
     */
    private Integer completedChapterIndex;

    /**
     * 当前已完成章节名称。
     */
    private String completedChapterName;

    /**
     * 下一章序号。
     */
    private Integer nextChapterIndex;

    /**
     * 下一章名称。
     */
    private String nextChapterName;

    /**
     * 已完成能力列表。
     */
    private List<String> completedAbilities;

    /**
     * 下一步建议。
     */
    private String nextSuggestion;
}