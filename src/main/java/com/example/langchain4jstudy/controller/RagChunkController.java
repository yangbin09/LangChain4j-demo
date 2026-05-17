package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.model.request.RagAskRequest;
import com.example.langchain4jstudy.model.response.RagChunkAskResponse;
import com.example.langchain4jstudy.service.RagChunkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag/chunk")
@RequiredArgsConstructor
@Slf4j
public class RagChunkController {

    private final RagChunkService ragChunkService;

    @PostMapping("/ask")
    public RagChunkAskResponse ask(@RequestBody RagAskRequest request) {
        log.info("收到 RAG 切片问答请求：{}", request);
        return ragChunkService.ask(request.getQuestion());
    }
}