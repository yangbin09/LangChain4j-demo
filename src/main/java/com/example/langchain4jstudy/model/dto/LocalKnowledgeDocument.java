package com.example.langchain4jstudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本地知识文档。
 *
 * <p>用于表示从 docs 目录读取出来的一篇 Markdown 文档。</p>
 *
 * @author yang xiao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalKnowledgeDocument {

    /**
     * 文件名。
     */
    private String sourceFile;

    /**
     * 文档标题。
     */
    private String title;

    /**
     * 文档完整内容。
     */
    private String content;
}