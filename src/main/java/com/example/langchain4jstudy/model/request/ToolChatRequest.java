package com.example.langchain4jstudy.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具增强对话请求。
 *
 * <p>用于接收用户输入的对话内容。</p>
 *
 * @author yang xiao
 */
@Data
@NoArgsConstructor
public class ToolChatRequest {

    /**
     * 用户消息。
     */
    private String message;
}