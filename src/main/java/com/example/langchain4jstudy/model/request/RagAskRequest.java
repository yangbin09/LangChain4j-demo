package com.example.langchain4jstudy.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RAG 问答请求对象。
 *
 * <p>用于接收用户提出的问题。</p>
 *
 * @author yang xiao
 */
@Data
@NoArgsConstructor
public class RagAskRequest {

    /**
     * 用户问题。
     *
     * <p>例如：LangChain4j 的 Tool Calling 是什么？</p>
     */
    private String question;
}