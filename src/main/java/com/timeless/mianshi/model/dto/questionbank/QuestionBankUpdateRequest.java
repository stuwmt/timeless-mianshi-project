package com.timeless.mianshi.model.dto.questionbank;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新题库请求
 */
@Data
public class QuestionBankUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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
     * 图片
     */
    private String picture;

    private static final long serialVersionUID = 1L;
}