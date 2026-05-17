package com.example.langchain4jstudy.controller;

import com.example.langchain4jstudy.model.request.RagAskRequest;
import com.example.langchain4jstudy.model.response.RagAskResponse;
import com.example.langchain4jstudy.service.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * RAG 问答控制器。
 *
 * <p>提供基于本地 Markdown 文档的知识库问答接口。</p>
 *
 * @author yang xiao
 */
@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
@Slf4j
public class RagController {

    /**
     * RAG 问答服务。
     */
    private final RagService ragService;

    /**
     * 基于本地 Markdown 文档提问。
     *
     * @param request RAG 问答请求
     * @return RAG 问答结果
     */
    @PostMapping("/ask")
    public RagAskResponse ask(@RequestBody RagAskRequest request) {
        log.info("收到 RAG 问答请求：{}", request);
        return ragService.ask(request.getQuestion());
    }
}