package com.example.langchain4jstudy.service;

import com.example.langchain4jstudy.model.dto.LocalKnowledgeDocument;
import com.example.langchain4jstudy.model.dto.RagRetrievedDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 简单 RAG 检索服务。
 *
 * <p>当前使用关键词匹配和简单打分实现文档检索。</p>
 *
 * <p>这不是最终企业级方案，只用于第六章理解 RAG 主流程。
 * 后续章节会升级为文档切分、Embedding 向量化和向量库检索。</p>
 *
 * @author yang xiao
 */
@Service
@Slf4j
public class SimpleRagRetrievalService {

    /**
     * 常见技术关键词。
     *
     * <p>用于提升中文问题下的检索稳定性。</p>
     */
    private static final List<String> TECH_TERMS = List.of(
            "LangChain4j",
            "AiService",
            "Tool Calling",
            "ChatMemory",
            "RAG",
            "Embedding",
            "DeepSeek",
            "Hudi",
            "Flink",
            "Spark",
            "Hive",
            "结构化输出",
            "工具调用",
            "多轮对话",
            "检索增强生成",
            "向量",
            "知识库",
            "Checkpoint",
            "Bucket Index",
            "duplicate fileId",
            "张晨岚",
            "李牧遥",
            "周安禾"
    );

    /**
     * 检索相关文档。
     *
     * @param question 用户问题
     * @param documents 本地文档列表
     * @param topK 返回前几个结果
     * @return 命中文档列表
     */
    public List<RagRetrievedDocument> retrieve(String question,
                                               List<LocalKnowledgeDocument> documents,
                                               int topK) {
        if (!StringUtils.hasText(question)) {
            log.info("用户问题为空，无法检索文档。");
            return List.of();
        }

        Set<String> keywords = extractKeywords(question);

        return documents.stream()
                .map(document -> scoreDocument(question, keywords, document))
                .filter(result -> result.getScore() > 0)
                .sorted(Comparator.comparing(RagRetrievedDocument::getScore).reversed())
                .limit(topK)
                .collect(Collectors.toList());
    }

    /**
     * 提取问题关键词。
     *
     * @param question 用户问题
     * @return 关键词集合
     */
    private Set<String> extractKeywords(String question) {
        Set<String> keywords = new LinkedHashSet<>();

        for (String term : TECH_TERMS) {
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

        return keywords;
    }

    /**
     * 给单篇文档打分。
     *
     * @param question 用户问题
     * @param keywords 关键词集合
     * @param document 本地文档
     * @return 检索结果
     */
    private RagRetrievedDocument scoreDocument(String question,
                                               Set<String> keywords,
                                               LocalKnowledgeDocument document) {
        String content = document.getContent();
        String lowerContent = content.toLowerCase();

        int score = 0;

        for (String keyword : keywords) {
            String lowerKeyword = keyword.toLowerCase();
            if (lowerContent.contains(lowerKeyword)) {
                score += 3;
            }
            if (document.getTitle().toLowerCase().contains(lowerKeyword)) {
                score += 5;
            }
            if (document.getSourceFile().toLowerCase().contains(lowerKeyword)) {
                score += 5;
            }
        }

        if (lowerContent.contains(question.toLowerCase())) {
            score += 10;
        }

        return RagRetrievedDocument.builder()
                .sourceFile(document.getSourceFile())
                .title(document.getTitle())
                .content(content)
                .score(score)
                .snippet(extractSnippet(content, keywords))
                .build();
    }

    /**
     * 提取命中文档片段。
     *
     * @param content 文档内容
     * @param keywords 关键词集合
     * @return 文档片段
     */
    private String extractSnippet(String content, Set<String> keywords) {
        List<String> lines = content.lines()
                .filter(StringUtils::hasText)
                .toList();

        for (String line : lines) {
            for (String keyword : keywords) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    return limitLength(line.trim(), 120);
                }
            }
        }

        return limitLength(content.replace("\n", " ").trim(), 120);
    }

    /**
     * 限制文本长度。
     *
     * @param text 原文本
     * @param maxLength 最大长度
     * @return 截断后的文本
     */
    private String limitLength(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }
}