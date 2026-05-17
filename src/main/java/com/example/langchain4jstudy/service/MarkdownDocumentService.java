package com.example.langchain4jstudy.service;

import com.example.langchain4jstudy.model.dto.LocalKnowledgeDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Markdown 文档加载服务。
 *
 * <p>负责从项目根目录的 docs 文件夹中读取 Markdown 文档。</p>
 *
 * <p>当前实现适合学习和本地演示。生产环境可以替换成数据库、对象存储、知识库系统或文档管理平台。</p>
 *
 * @author yang xiao
 */
@Service
@Slf4j
public class MarkdownDocumentService {

    /**
     * 本地文档目录。
     */
    private static final String DOCS_DIR = "docs";

    /**
     * 加载所有 Markdown 文档。
     *
     * @return 本地知识文档列表
     */
    public List<LocalKnowledgeDocument> loadAllDocuments() {
        Path docsPath = Paths.get(DOCS_DIR);

        if (!Files.exists(docsPath) || !Files.isDirectory(docsPath)) {
            log.error("本地 docs 目录不存在，请先在项目根目录创建 docs 文件夹。");
            throw new IllegalStateException("本地 docs 目录不存在，请先在项目根目录创建 docs 文件夹。");
        }

        try (Stream<Path> pathStream = Files.list(docsPath)) {
            log.info("开始加载 docs 目录下的所有 Markdown 文档。");
            return pathStream
                    .filter(path -> path.toString().endsWith(".md"))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .map(this::readMarkdownFile)
                    .toList();
        } catch (IOException e) {
            log.error("读取 docs 目录失败：{}", e.getMessage());
            throw new IllegalStateException("读取 docs 目录失败：" + e.getMessage(), e);
        }
    }

    /**
     * 读取单个 Markdown 文件。
     *
     * @param path Markdown 文件路径
     * @return 本地知识文档
     */
    private LocalKnowledgeDocument readMarkdownFile(Path path) {
        log.info("开始读取 Markdown 文件：{}", path);
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            String sourceFile = path.getFileName().toString();

            return LocalKnowledgeDocument.builder()
                    .sourceFile(sourceFile)
                    .title(extractTitle(content, sourceFile))
                    .content(content)
                    .build();
        } catch (IOException e) {
            log.error("读取 Markdown {}文件失败：{}", path, e.getMessage());
            throw new IllegalStateException("读取 Markdown 文件失败：" + path, e);
        }
    }

    /**
     * 从 Markdown 内容中提取一级标题。
     *
     * @param content Markdown 内容
     * @param defaultTitle 默认标题
     * @return 文档标题
     */
    private String extractTitle(String content, String defaultTitle) {
        log.info("开始从 Markdown 内容中提取一级标题。内容长度：{}", content.length());
        if (!StringUtils.hasText(content)) {
            return defaultTitle;
        }

        return content.lines()
                .filter(line -> line.startsWith("# "))
                .map(line -> line.replaceFirst("# ", "").trim())
                .findFirst()
                .orElse(defaultTitle);
    }
}