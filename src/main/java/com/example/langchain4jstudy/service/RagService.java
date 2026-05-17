package com.example.langchain4jstudy.service;

import com.example.langchain4jstudy.ai.RagAssistant;
import com.example.langchain4jstudy.model.dto.LocalKnowledgeDocument;
import com.example.langchain4jstudy.model.dto.RagRetrievedDocument;
import com.example.langchain4jstudy.model.response.RagAskResponse;
import com.example.langchain4jstudy.model.response.RagReferenceItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * RAG 问答服务。
 *
 * <p>负责串联文档加载、文档检索、上下文组装和 AI 回答生成。</p>
 *
 * @author yang xiao
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RagService {

    /**
     * Markdown 文档加载服务。
     */
    private final MarkdownDocumentService markdownDocumentService;

    /**
     * 简单 RAG 检索服务。
     */
    private final SimpleRagRetrievalService simpleRagRetrievalService;

    /**
     * RAG AI 助手。
     */
    private final RagAssistant ragAssistant;

    /**
     * 基于本地 Markdown 文档回答问题。
     *
     * @param question 用户问题
     * @return RAG 问答结果
     */
    public RagAskResponse ask(String question) {
        log.info("开始 RAG 问答，问题：{}", question);
        List<LocalKnowledgeDocument> documents = markdownDocumentService.loadAllDocuments();

        List<RagRetrievedDocument> retrievedDocuments =
                simpleRagRetrievalService.retrieve(question, documents, 3);

        if (CollectionUtils.isEmpty(retrievedDocuments)) {
            log.info("当前知识库没有找到足够依据，请先补充相关 Markdown 文档。");
            return RagAskResponse.builder()
                    .answer("当前知识库没有找到足够依据，请先补充相关 Markdown 文档。")
                    .references(List.of())
                    .hitCount(0)
                    .build();
        }

        log.info("检索到 {} 个命中文档。", retrievedDocuments.size());
        String context = buildContext(retrievedDocuments);
        log.info("上下文组装完成，上下文长度：{}", context.length());
        log.info("开始调用 RAG AI 助手回答问题。");
        String answer = ragAssistant.answer(question, context);

        return RagAskResponse.builder()
                .answer(answer)
                .references(buildReferences(retrievedDocuments))
                .hitCount(retrievedDocuments.size())
                .build();
    }

    /**
     * 构造给大模型使用的知识库上下文。
     *
     * @param retrievedDocuments 检索命中文档
     * @return 上下文文本
     */
    private String buildContext(List<RagRetrievedDocument> retrievedDocuments) {
        StringBuilder contextBuilder = new StringBuilder();

        for (int i = 0; i < retrievedDocuments.size(); i++) {
            RagRetrievedDocument document = retrievedDocuments.get(i);

            contextBuilder.append("【文档")
                    .append(i + 1)
                    .append("】\n");

            contextBuilder.append("来源文件：")
                    .append(document.getSourceFile())
                    .append("\n");

            contextBuilder.append("标题：")
                    .append(document.getTitle())
                    .append("\n");

            contextBuilder.append("内容：\n")
                    .append(document.getContent())
                    .append("\n\n");
        }

        return contextBuilder.toString();
    }

    /**
     * 构造引用来源。
     *
     * @param retrievedDocuments 检索命中文档
     * @return 引用来源列表
     */
    private List<RagReferenceItem> buildReferences(List<RagRetrievedDocument> retrievedDocuments) {
        return retrievedDocuments.stream()
                .map(document -> RagReferenceItem.builder()
                        .sourceFile(document.getSourceFile())
                        .title(document.getTitle())
                        .score(document.getScore())
                        .snippet(document.getSnippet())
                        .build())
                .toList();
    }
}