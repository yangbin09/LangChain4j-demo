package com.example.langchain4jstudy.service;

import com.example.langchain4jstudy.model.dto.LocalKnowledgeChunk;
import com.example.langchain4jstudy.model.dto.LocalKnowledgeDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkdownChunkService {

    private final MarkdownDocumentService markdownDocumentService;

    public List<LocalKnowledgeChunk> loadAllChunks() {
        log.info("开始加载所有 Markdown 文档并进行切片。");

        List<LocalKnowledgeDocument> documents = markdownDocumentService.loadAllDocuments();

        List<LocalKnowledgeChunk> chunks = documents.stream()
                .flatMap(document -> splitToChunks(document).stream())
                .toList();

        log.info("Markdown 文档切片完成，文档数量：{}，切片数量：{}", documents.size(), chunks.size());

        return chunks;
    }

    private List<LocalKnowledgeChunk> splitToChunks(LocalKnowledgeDocument document) {
        List<LocalKnowledgeChunk> chunks = new ArrayList<>();

        if (document == null || !StringUtils.hasText(document.getContent())) {
            log.warn("文档内容为空，跳过切片。document：{}", document);
            return chunks;
        }

        String[] lines = document.getContent().split("\\R");

        String currentChunkTitle = "文档说明";
        StringBuilder currentContent = new StringBuilder();
        int chunkIndex = 1;

        for (String line : lines) {
            if (line.startsWith("# ")) {
                continue;
            }

            if (line.startsWith("## ")) {
                if (currentContent.length() > 0) {
                    chunks.add(buildChunk(document, chunkIndex++, currentChunkTitle, currentContent.toString()));
                    currentContent.setLength(0);
                }

                currentChunkTitle = line.replaceFirst("## ", "").trim();
                continue;
            }

            if (StringUtils.hasText(line)) {
                currentContent.append(line).append("\n");
            }
        }

        if (currentContent.length() > 0) {
            chunks.add(buildChunk(document, chunkIndex, currentChunkTitle, currentContent.toString()));
        }

        log.info("单个文档切片完成，sourceFile：{}，chunkCount：{}", document.getSourceFile(), chunks.size());

        return chunks;
    }

    private LocalKnowledgeChunk buildChunk(LocalKnowledgeDocument document,
                                           Integer chunkIndex,
                                           String chunkTitle,
                                           String content) {
        return LocalKnowledgeChunk.builder()
                .sourceFile(document.getSourceFile())
                .documentTitle(document.getTitle())
                .chunkIndex(chunkIndex)
                .chunkTitle(chunkTitle)
                .content(content.trim())
                .build();
    }
}