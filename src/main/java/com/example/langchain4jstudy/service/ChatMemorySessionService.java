package com.example.langchain4jstudy.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对话记忆会话管理服务。
 *
 * <p>用于按 memoryId 管理不同用户或不同会话的 ChatMemory。</p>
 *
 * <p>当前实现使用内存 Map 保存记忆，适合学习和演示。
 * 如果应用重启，记忆会丢失。生产环境建议改成数据库、Redis 或其他持久化存储。</p>
 *
 * @author yang xiao
 */
@Service
@Slf4j
public class ChatMemorySessionService {

    /**
     * 会话记忆缓存。
     *
     * <p>key 是 memoryId，value 是对应的 ChatMemory。</p>
     */
    private final Map<String, ChatMemory> memoryMap = new ConcurrentHashMap<>();

    /**
     * 获取或创建对话记忆。
     *
     * @param memoryId 记忆 ID
     * @return 对话记忆对象
     */
    public ChatMemory getOrCreate(String memoryId) {
        log.info("获取或创建对话记忆，memoryId：{}", memoryId);
        return memoryMap.computeIfAbsent(memoryId, id -> {
            log.info("创建新的 ChatMemory 实例，memoryId：{}，最大消息数：20", id);
            return MessageWindowChatMemory.builder()
                    .id(id)
                    .maxMessages(20)
                    .build();
        });
    }

    /**
     * 清空指定会话记忆。
     *
     * @param memoryId 记忆 ID
     */
    public void clear(String memoryId) {
        log.info("清空对话记忆，memoryId：{}", memoryId);
        ChatMemory chatMemory = memoryMap.remove(memoryId);
        if (chatMemory != null) {
            chatMemory.clear();
            log.info("对话记忆已清空，memoryId：{}", memoryId);
        } else {
            log.warn("未找到对应的对话记忆，memoryId：{}", memoryId);
        }
    }

    /**
     * 获取当前内存中的会话数量。
     *
     * @return 会话数量
     */
    public int count() {
        int size = memoryMap.size();
        log.info("查询当前会话数量：{}", size);
        return size;
    }
}