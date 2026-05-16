package com.example.langchain4jstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LangChain4j 学习项目启动类。
 *
 * <p>本项目用于学习 Spring Boot 3.x 集成 LangChain4j，对接国产大模型（DeepSeek、MiniMax 等）。</p>
 *
 * @author yang xiao
 */
@SpringBootApplication
public class LangChain4jStudyApplication {

    /**
     * 项目启动入口。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(LangChain4jStudyApplication.class, args);
    }
}
