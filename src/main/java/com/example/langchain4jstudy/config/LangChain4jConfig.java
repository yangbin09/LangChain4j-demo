package com.example.langchain4jstudy.config;

import com.example.langchain4jstudy.ai.Assistant;
import com.example.langchain4jstudy.ai.MemoryAssistant;
import com.example.langchain4jstudy.ai.StudyPlanAssistant;
import com.example.langchain4jstudy.ai.ToolAssistant;
import com.example.langchain4jstudy.service.ChatMemorySessionService;
import com.example.langchain4jstudy.tools.DateTimeTool;
import com.example.langchain4jstudy.tools.LearningProgressTool;
import com.example.langchain4jstudy.tools.TechTermTool;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * LangChain4j 配置类。
 *
 * <p>负责配置对话模型和注册各类 AI 服务 Bean。</p>
 * <p>所有 AI 能力都通过这里注册为 Spring Bean，供 Controller 注入使用。</p>
 *
 * @author yang xiao
 */
@Configuration
@Slf4j
public class LangChain4jConfig {

    /**
     * 配置对话模型。
     *
     * <p>使用 OpenAI 接口风格配置 DeepSeek 或其他兼容的大模型。</p>
     * <p>配置项从 application.yml 中读取，包括 API Key、接口地址和模型名称。</p>
     *
     * @param apiKey AI 模型的 API 密钥
     * @param baseUrl API 接口地址
     * @param modelName 模型名称
     * @return 对话模型实例
     */
    @Bean
    public ChatModel chatModel(
            @Value("${ai.deepseek.api-key}") String apiKey,
            @Value("${ai.deepseek.base-url}") String baseUrl,
            @Value("${ai.deepseek.model-name}") String modelName
    ) {
        log.info("初始化对话模型，地址：{}，模型：{}", baseUrl, modelName);
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(1.0)
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    /**
     * 注册基础 AI 助手。
     *
     * <p>通过 AiServices 将 Assistant 接口转换为可用的 AI 服务。</p>
     *
     * @param chatModel 对话模型
     * @return AI 助手实例
     */
    @Bean
    public Assistant assistant(ChatModel chatModel) {
        log.info("注册基础 AI 助手");
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .build();
    }

    /**
     * 注册学习计划助手。
     *
     * <p>用于生成结构化学习计划的 AI 服务。</p>
     *
     * @param chatModel 对话模型
     * @return 学习计划助手实例
     */
    @Bean
    public StudyPlanAssistant studyPlanAssistant(ChatModel chatModel) {
        log.info("注册学习计划助手");
        return AiServices.builder(StudyPlanAssistant.class)
                .chatModel(chatModel)
                .build();
    }

    /**
     * 注册工具增强助手。
     *
     * <p>支持 Tool Calling 功能的 AI 助手，可以调用外部工具获取真实信息。</p>
     * <p>当前注册了三个工具：日期时间工具、学习进度工具、技术术语工具。</p>
     *
     * @param chatModel 对话模型
     * @param dateTimeTool 日期时间工具
     * @param learningProgressTool 学习进度工具
     * @param techTermTool 技术术语工具
     * @return 工具增强助手实例
     */
    @Bean
    public ToolAssistant toolAssistant(ChatModel chatModel,
                                       DateTimeTool dateTimeTool,
                                       LearningProgressTool learningProgressTool,
                                       TechTermTool techTermTool) {
        log.info("注册工具增强助手，注册工具数量：{}", 3);
        return AiServices.builder(ToolAssistant.class)
                .chatModel(chatModel)
                .tools(dateTimeTool, learningProgressTool, techTermTool)
                .build();
    }

    /**
     * 注册带记忆能力的助手。
     *
     * <p>通过 chatMemoryProvider 按 memoryId 获取不同会话的 ChatMemory。</p>
     */
    @Bean
    public MemoryAssistant memoryAssistant(ChatModel chatModel,
                                           ChatMemorySessionService chatMemorySessionService) {
        log.info("注册带记忆能力的助手");
        return AiServices.builder(MemoryAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId ->
                        chatMemorySessionService.getOrCreate(String.valueOf(memoryId))
                )
                .build();
    }
}
