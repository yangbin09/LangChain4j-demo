package com.example.langchain4jstudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RAG 检索命中文档。
 *
 * <p>用于表示一次问题检索后命中的文档及其相关性分数。</p>
 *
 * @author yang xiao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagRetrievedDocument {

    /**
     * 来源文件名。
     */
    private String sourceFile;

    /**
     * 文档标题。
     */
    private String title;

    /**
     * 文档内容。
     */
    private String content;

    /**
     * 检索分数。
     */
    private Integer score;

    /**
     * 命中片段。
     */
    private String snippet;
}