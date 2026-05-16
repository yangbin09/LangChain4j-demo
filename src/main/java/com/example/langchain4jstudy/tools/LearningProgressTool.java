package com.example.langchain4jstudy.tools;

import com.example.langchain4jstudy.model.response.LearningProgressResponse;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 学习进度工具。
 *
 * <p>用于查询当前 LangChain4j 学习进度。</p>
 * <p>这里先使用模拟数据，后续可以替换成数据库查询。</p>
 *
 * @author yang xiao
 */
@Component
@Slf4j
public class LearningProgressTool {

    /**
     * 查询当前学习进度。
     *
     * <p>返回当前已完成章节、下一章内容、已具备能力列表和下一步建议。</p>
     *
     * @return 当前学习进度信息
     */
    @Tool("查询用户当前 LangChain4j 学习进度。当用户询问学到哪、下一章学什么、当前进度时，必须调用该工具。")
    public LearningProgressResponse queryCurrentProgress() {
        log.info("查询当前学习进度");
        return LearningProgressResponse.builder()
                .completedChapterIndex(3)
                .completedChapterName("结构化输出")
                .nextChapterIndex(4)
                .nextChapterName("Tool Calling 工具调用")
                .completedAbilities(List.of(
                        "能使用 Spring Boot 调用 DeepSeek 对话接口",
                        "能使用 AiService 封装 AI 能力",
                        "能让大模型返回 Java 结构化对象"
                ))
                .nextSuggestion("建议继续学习 Tool Calling，让大模型调用 Java 方法，完成确定性查询和业务动作。")
                .build();
    }
}
