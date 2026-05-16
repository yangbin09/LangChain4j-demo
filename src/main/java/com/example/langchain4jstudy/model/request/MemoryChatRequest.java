package com.example.langchain4jstudy.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带记忆的对话请求。
 *
 * <p>用于接收用户消息和会话 ID。</p>
 *
 * <p>memoryId 用来区分不同用户或不同会话。
 * 同一个 memoryId 会共享同一段对话记忆，不同 memoryId 之间互不影响。</p>
 *
 * @author yang xiao
 */
@Data
@NoArgsConstructor
public class MemoryChatRequest {

    /**
     * 记忆 ID。
     *
     * <p>可以理解为会话 ID，例如 user-001、session-001。</p>
     */
    private String memoryId;

    /**
     * 用户消息。
     */
    private String message;
}