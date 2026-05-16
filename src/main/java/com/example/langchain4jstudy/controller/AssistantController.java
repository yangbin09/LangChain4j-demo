package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.Assistant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AI 助手控制器。
 *
 * <p>提供带系统提示词的 AI 助手对话接口。</p>
 * <p>通过 Assistant 接口，支持更丰富的对话场景，如技术问答、学习指导等。</p>
 *
 * @author yang xiao
 */
@RestController
@RequestMapping("/assistant")
@Slf4j
public class AssistantController {

    private final Assistant assistant;

    public AssistantController(Assistant assistant) {
        this.assistant = assistant;
    }

    /**
     * AI 助手对话接口。
     *
     * <p>通过 Assistant 接口与 AI 对话，支持系统提示词约束。</p>
     *
     * @param request 包含 message 字段的请求体
     * @return AI 回答内容
     */
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        log.info("收到助手对话请求：{}", message);
        String answer = assistant.chat(message);
        log.info("返回回答，长度：{} 字符", answer.length());
        return Map.of("answer", answer);
    }
}
