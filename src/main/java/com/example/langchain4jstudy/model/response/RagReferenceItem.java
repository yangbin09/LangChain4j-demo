package com.example.langchain4jstudy.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RAG 引用来源。
 *
 * <p>用于告诉调用方，本次回答参考了哪些本地文档。</p>
 *
 * @author yang xiao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagReferenceItem {

    /**
     * 来源文件名。
     *
     * <p>例如：langchain4j.md。</p>
     */
    private String sourceFile;

    /**
     * 文档标题。
     */
    private String title;

    /**
     * 简单检索分数。
     *
     * <p>分数越高，说明该文档和问题越相关。</p>
     */
    private Integer score;

    /**
     * 命中文档片段。
     */
    private String snippet;
}