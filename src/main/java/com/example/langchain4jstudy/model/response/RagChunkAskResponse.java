package com.example.langchain4jstudy.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagChunkAskResponse {

    /**
     * AI 回答。
     */
    private String answer;

    /**
     * 命中的切片引用。
     */
    private List<RagChunkReferenceItem> references;

    /**
     * 命中的切片数量。
     */
    private Integer hitCount;
}