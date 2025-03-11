package com.timeless.mianshi.model.dto.questionbank;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建题库请求
 */
@Data
public class QuestionBankAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
    /**
     * 描述
     */
    private String description;
    /**
     * 封面图片
     */
    private String picture;

    private static final long serialVersionUID = 1L;
}