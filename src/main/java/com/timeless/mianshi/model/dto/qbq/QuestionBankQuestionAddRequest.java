package com.timeless.mianshi.model.dto.qbq;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建题库题目表请求
 */
@Data
public class QuestionBankQuestionAddRequest implements Serializable {
    /**
     * 题目id
     */
    private Long questionId;
    /**
     * 题库id
     */
    private Long questionBankId;
    private static final long serialVersionUID = 1L;
}