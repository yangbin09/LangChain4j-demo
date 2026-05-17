package com.example.langchain4jstudy.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagChunkReferenceItem {

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
     * 检索得分。
     */
    private Integer score;

    /**
     * 命中的简短片段。
     */
    private String snippet;
}