package com.example.langchain4jstudy.service;

import com.example.langchain4jstudy.model.dto.LocalKnowledgeChunk;
import com.example.langchain4jstudy.model.dto.RagRetrievedChunk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class ChunkRagRetrievalService {

    private static final List<String> TERMS = List.of(
            "张晨岚", "李牧遥", "周安禾",
            "项目代号", "工号", "特殊备注", "基本信息", "技术方向", "当前项目", "工作习惯",
            "Aurora-17", "Maple-Score", "RiverLake-09",
            "Tool Calling", "ChatMemory", "RAG",
            "爆款文章评分器", "duplicate fileId", "AI 写作", "前端页面",
            "Flink", "Hudi", "Checkpoint", "Bucket Index",
            "手机号", "邮箱", "住址", "年龄"
    );

    public List<RagRetrievedChunk> retrieve(String question,
                                            List<LocalKnowledgeChunk> chunks,
                                            int topK) {
        if (!StringUtils.hasText(question)) {
            log.info("用户问题为空，无法进行切片检索。");
            return List.of();
        }

        if (chunks == null || chunks.isEmpty()) {
            log.info("当前没有可检索的文档切片。");
            return List.of();
        }

        Set<String> keywords = extractKeywords(question);

        log.info("开始切片检索，question：{}，keywords：{}，chunkCount：{}，topK：{}",
                question, keywords, chunks.size(), topK);

        List<RagRetrievedChunk> retrievedChunks = chunks.stream()
                .map(chunk -> scoreChunk(question, keywords, chunk))
                .filter(result -> result.getScore() > 0)
                .sorted(Comparator.comparing(RagRetrievedChunk::getScore).reversed())
                .limit(topK)
                .toList();

        log.info("切片检索完成，命中数量：{}", retrievedChunks.size());

        return retrievedChunks;
    }

    private Set<String> extractKeywords(String question) {
        Set<String> keywords = new LinkedHashSet<>();

        for (String term : TERMS) {
            if (question.toLowerCase().contains(term.toLowerCase())) {
                keywords.add(term);
            }
        }

        String[] parts = question.split("[\\s，。！？、,.!?：:；;（）()《》<>\"'“”]+");
        for (String part : parts) {
            if (StringUtils.hasText(part) && part.trim().length() >= 2) {
                keywords.add(part.trim());
            }
        }

        log.info("问题关键词提取完成，question：{}，keywords：{}", question, keywords);

        return keywords;
    }

    private RagRetrievedChunk scoreChunk(String question,
                                         Set<String> keywords,
                                         LocalKnowledgeChunk chunk) {
        String content = safeLower(chunk.getContent());
        String chunkTitle = safeLower(chunk.getChunkTitle());
        String documentTitle = safeLower(chunk.getDocumentTitle());
        String sourceFile = safeLower(chunk.getSourceFile());

        int score = 0;

        for (String keyword : keywords) {
            String lowerKeyword = keyword.toLowerCase();

            if (content.contains(lowerKeyword)) {
                score += 5;
            }

            if (chunkTitle.contains(lowerKeyword)) {
                score += 8;
            }

            if (documentTitle.contains(lowerKeyword)) {
                score += 5;
            }

            if (sourceFile.contains(lowerKeyword)) {
                score += 3;
            }
        }

        if (content.contains(question.toLowerCase())) {
            score += 15;
        }

        return RagRetrievedChunk.builder()
                .sourceFile(chunk.getSourceFile())
                .documentTitle(chunk.getDocumentTitle())
                .chunkIndex(chunk.getChunkIndex())
                .chunkTitle(chunk.getChunkTitle())
                .content(chunk.getContent())
                .score(score)
                .snippet(extractSnippet(chunk.getContent(), keywords))
                .build();
    }

    private String extractSnippet(String content, Set<String> keywords) {
        if (!StringUtils.hasText(content)) {
            return "";
        }

        List<String> lines = content.lines()
                .filter(StringUtils::hasText)
                .toList();

        for (String line : lines) {
            for (String keyword : keywords) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    return limitLength(line.trim(), 160);
                }
            }
        }

        return limitLength(content.replace("\n", " ").trim(), 160);
    }

    private String limitLength(String text, int maxLength) {
        if (text == null) {
            return "";
        }

        if (text.length() <= maxLength) {
            return text;
        }

        return text.substring(0, maxLength) + "...";
    }

    private String safeLower(String value) {
        return value == null ? "" : value.toLowerCase();
    }
}