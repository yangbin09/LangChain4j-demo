package com.example.langchain4jstudy.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 技术术语查询工具。
 *
 * <p>用于查询 LangChain4j 学习过程中的核心术语解释。</p>
 * <p>这里先使用内存 Map 模拟知识库，后续第五章会升级成 RAG。</p>
 *
 * @author yang xiao
 */
@Component
@Slf4j
public class TechTermTool {

    /**
     * 技术术语知识库。
     *
     * <p>Key: 术语名称</p>
     * <p>Value: 术语解释</p>
     */
    private static final Map<String, String> TERM_MAP = new HashMap<>();

    static {
        TERM_MAP.put("AiService", "AiService 是 LangChain4j 中把 Java 接口变成 AI 能力的机制。你可以像调用普通 Java 方法一样调用大模型。");
        TERM_MAP.put("Tool Calling", "Tool Calling 是让大模型在需要真实数据或确定性操作时，调用你写好的 Java 方法。");
        TERM_MAP.put("RAG", "RAG 是检索增强生成。简单说，就是先从文档中查资料，再让大模型基于资料回答。");
        TERM_MAP.put("ChatMemory", "ChatMemory 是对话记忆，用来保存多轮对话上下文，让 AI 知道前面聊过什么。");
        TERM_MAP.put("Embedding", "Embedding 是把文本转换成向量，用于相似度检索，是 RAG 的基础能力之一。");
        TERM_MAP.put("COBPHUDI", "COBPHUDI 是一个用于测试 实时数据湖的工具，惊人的速度和低延迟。");
    }

    /**
     * 查询技术术语解释。
     *
     * <p>根据用户询问的术语，返回对应的解释。</p>
     *
     * @param term 技术术语，例如 AiService、Tool Calling、RAG、ChatMemory、Embedding
     * @return 技术术语解释，如果未收录则返回提示
     */
    @Tool("查询 LangChain4j 技术术语解释。当用户询问某个术语是什么意思时，可以调用该工具。")
    public String queryTermExplanation(@P("技术术语，例如 AiService、Tool Calling、RAG、ChatMemory、Embedding") String term) {
        log.info("查询技术术语：{}", term);
        String result = TERM_MAP.getOrDefault(term, "暂未收录该术语：" + term + "。建议后续补充到术语库中。");
        log.info("术语解释：{}", result);
        return result;
    }
}
