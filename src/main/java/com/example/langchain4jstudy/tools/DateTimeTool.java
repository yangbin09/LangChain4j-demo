package com.example.langchain4jstudy.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具。
 *
 * <p>用于给大模型提供当前系统时间。</p>
 * <p>注意：当前时间这类信息不应该让大模型猜，而应该通过工具方法获取。</p>
 *
 * @author yang xiao
 */
@Component
@Slf4j
public class DateTimeTool {

    /**
     * 获取当前系统时间。
     *
     * <p>返回当前日期时间，格式为 yyyy-MM-dd HH:mm:ss。</p>
     *
     * @return 当前系统时间
     */
    @Tool("获取当前系统时间。当用户询问现在几点、当前日期、今天是什么时候时，必须调用该工具。")
    public String getCurrentDateTime() {
        String result = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("获取当前系统时间：{}", result);
        return result;
    }
}
