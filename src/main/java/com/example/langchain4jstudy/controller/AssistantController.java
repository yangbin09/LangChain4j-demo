package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.ai.Assistant;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private final Assistant assistant;

    public AssistantController(Assistant assistant) {
        this.assistant = assistant;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String answer = assistant.chat(message);
        return Map.of("answer", answer);
    }
}