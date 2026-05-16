package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.ToolAssistant;
import com.example.langchain4jstudy.model.request.ToolChatRequest;
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
        String answer = toolAssistant.chat(request.getMessage());
        return Map.of("answer", answer);
    }
}