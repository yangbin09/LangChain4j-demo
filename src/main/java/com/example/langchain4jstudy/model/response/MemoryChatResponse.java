package com.example.langchain4jstudy.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带记忆的对话响应。
 *
 * <p>用于返回 AI 回答以及当前使用的记忆 ID。</p>
 *
 * @author yang xiao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoryChatResponse {

    /**
     * 当前记忆 ID。
     */
    private String memoryId;

    /**
     * AI 回答内容。
     */
    private String answer;
}