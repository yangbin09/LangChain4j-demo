package com.example.langchain4jstudy.service;

import com.example.langchain4jstudy.ai.RagAssistant;
import com.example.langchain4jstudy.model.dto.LocalKnowledgeChunk;
import com.example.langchain4jstudy.model.dto.RagRetrievedChunk;
import com.example.langchain4jstudy.model.response.RagChunkAskResponse;
import com.example.langchain4jstudy.model.response.RagChunkReferenceItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RagChunkService {

    private final MarkdownChunkService markdownChunkService;
    private final ChunkRagRetrievalService chunkRagRetrievalService;
    private final RagAssistant ragAssistant;

    public RagChunkAskResponse ask(String question) {
        log.info("开始 RAG 切片问答，问题：{}", question);

        List<LocalKnowledgeChunk> chunks = markdownChunkService.loadAllChunks();

        List<RagRetrievedChunk> retrievedChunks =
                chunkRagRetrievalService.retrieve(question, chunks, 3);

        if (CollectionUtils.isEmpty(retrievedChunks)) {
            log.info("当前知识库切片没有找到足够依据。");
            return RagChunkAskResponse.builder()
                    .answer("当前知识库没有找到足够依据，请先补充相关 Markdown 文档。")
                    .references(List.of())
                    .hitCount(0)
                    .build();
        }

        log.info("检索到 {} 个命中切片。", retrievedChunks.size());

        String context = buildChunkContext(retrievedChunks);

        log.info("切片上下文组装完成，上下文长度：{}", context.length());
        log.info("开始调用 RAG AI 助手回答问题。");

        String answer = ragAssistant.answer(question, context);

        return RagChunkAskResponse.builder()
                .answer(answer)
                .references(buildReferences(retrievedChunks))
                .hitCount(retrievedChunks.size())
                .build();
    }

    private String buildChunkContext(List<RagRetrievedChunk> retrievedChunks) {
        StringBuilder contextBuilder = new StringBuilder();

        for (int i = 0; i < retrievedChunks.size(); i++) {
            RagRetrievedChunk chunk = retrievedChunks.get(i);

            contextBuilder.append("【切片")
                    .append(i + 1)
                    .append("】\n");

            contextBuilder.append("来源文件：")
                    .append(chunk.getSourceFile())
                    .append("\n");

            contextBuilder.append("文档标题：")
                    .append(chunk.getDocumentTitle())
                    .append("\n");

            contextBuilder.append("片段序号：")
                    .append(chunk.getChunkIndex())
                    .append("\n");

            contextBuilder.append("片段标题：")
                    .append(chunk.getChunkTitle())
                    .append("\n");

            contextBuilder.append("片段内容：\n")
                    .append(chunk.getContent())
                    .append("\n\n");
        }

        return contextBuilder.toString();
    }

    private List<RagChunkReferenceItem> buildReferences(List<RagRetrievedChunk> retrievedChunks) {
        return retrievedChunks.stream()
                .map(chunk -> RagChunkReferenceItem.builder()
                        .sourceFile(chunk.getSourceFile())
                        .documentTitle(chunk.getDocumentTitle())
                        .chunkIndex(chunk.getChunkIndex())
                        .chunkTitle(chunk.getChunkTitle())
                        .score(chunk.getScore())
                        .snippet(chunk.getSnippet())
                        .build())
                .toList();
    }
}