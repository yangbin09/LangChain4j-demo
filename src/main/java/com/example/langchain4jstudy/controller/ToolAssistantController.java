package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.ToolAssistant;
import com.example.langchain4jstudy.model.request.ToolChatRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工具增强助手控制器。
 *
 * <p>提供 Tool Calling 演示接口。</p>
 *
 * @author yang xiao
 */
@RestController
@RequestMapping("/tool-assistant")
@Slf4j
public class ToolAssistantController {

    private final ToolAssistant toolAssistant;

    public ToolAssistantController(ToolAssistant toolAssistant) {
        this.toolAssistant = toolAssistant;
    }

    /**
     * 工具增强对话。
     *
     * @param request 对话请求
     * @return AI 回答
     */
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ToolChatRequest request) {
        log.info("收到工具增强对话请求：{}", request.getMessage());
        String answer = toolAssistant.chat(request.getMessage());
        log.info("返回回答，长度：{} 字符", answer.length());
        return Map.of("answer", answer);
    }
}