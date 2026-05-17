package com.example.langchain4jstudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalKnowledgeChunk {

    /**
     * 来源文件名。
     */
    private String sourceFile;

    /**
     * 原始文档标题。
     */
    private String documentTitle;

    /**
     * 当前片段序号。
     */
    private Integer chunkIndex;

    /**
     * 当前片段标题。
     */
    private String chunkTitle;

    /**
     * 当前片段内容。
     */
    private String content;
}