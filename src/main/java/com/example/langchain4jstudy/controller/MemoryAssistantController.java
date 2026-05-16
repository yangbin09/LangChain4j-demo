package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.MemoryAssistant;
import com.example.langchain4jstudy.model.request.MemoryChatRequest;
import com.example.langchain4jstudy.model.response.MemoryChatResponse;
import com.example.langchain4jstudy.service.ChatMemorySessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 带记忆能力的 AI 助手控制器。
 *
 * <p>提供多轮对话和清空记忆接口。</p>
 *
 * @author yang xiao
 */
@RestController
@RequestMapping("/memory-assistant")
@Slf4j
public class MemoryAssistantController {

    private final MemoryAssistant memoryAssistant;
    private final ChatMemorySessionService chatMemorySessionService;

    public MemoryAssistantController(MemoryAssistant memoryAssistant,
                                     ChatMemorySessionService chatMemorySessionService) {
        this.memoryAssistant = memoryAssistant;
        this.chatMemorySessionService = chatMemorySessionService;
    }

    /**
     * 带记忆的多轮对话。
     *
     * @param request 对话请求
     * @return AI 回答
     */
    @PostMapping("/chat")
    public MemoryChatResponse chat(@RequestBody MemoryChatRequest request) {
        log.info("收到带记忆对话请求，memoryId：{}，消息：{}", request.getMemoryId(), request.getMessage());
        String answer = memoryAssistant.chat(request.getMemoryId(), request.getMessage());
        log.info("返回回答，长度：{} 字符", answer.length());
        return new MemoryChatResponse(request.getMemoryId(), answer);
    }

    /**
     * 清空指定会话记忆。
     *
     * @param memoryId 记忆 ID
     * @return 清空结果
     */
    @DeleteMapping("/{memoryId}")
    public Map<String, Object> clear(@PathVariable String memoryId) {
        log.info("收到清空记忆请求，memoryId：{}", memoryId);
        chatMemorySessionService.clear(memoryId);
        int activeCount = chatMemorySessionService.count();
        log.info("记忆已清空，当前活跃会话数：{}", activeCount);
        return Map.of(
                "memoryId", memoryId,
                "cleared", true,
                "activeMemoryCount", activeCount
        );
    }
}