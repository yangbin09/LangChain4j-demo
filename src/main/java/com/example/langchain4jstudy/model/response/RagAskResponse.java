package com.example.langchain4jstudy.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * RAG 问答响应对象。
 *
 * <p>用于返回 AI 答案、引用来源和命中文档数量。</p>
 *
 * @author yang xiao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagAskResponse {

    /**
     * AI 基于本地文档生成的答案。
     */
    private String answer;

    /**
     * 引用来源列表。
     */
    private List<RagReferenceItem> references;

    /**
     * 命中文档数量。
     */
    private Integer hitCount;
}