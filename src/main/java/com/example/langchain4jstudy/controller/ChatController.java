package com.example.langchain4jstudy.controller;

import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 基础对话控制器。
 *
 * <p>提供最基础的 AI 对话接口，直接使用 ChatModel 进行单轮对话。</p>
 * <p>不经过 AiServices 封装，适合最原始的调用场景。</p *
 * @author yang xiao
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatModel chatModel;

    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 基础对话接口。
     *
     * <p>接收用户消息，返回 AI 的回答。适合简单的一问一答场景。</p>
     *
     * @param request 包含 message 字段的请求体
     * @return AI 回答内容
     */
    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        log.info("收到对话请求：{}", message);
        String answer = chatModel.chat(message);
        log.info("返回回答，长度：{} 字符", answer.length());
        return Map.of("answer", answer);
    }
}
