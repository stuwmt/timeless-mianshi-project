package com.timeless.mianshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timeless.mianshi.model.entity.QuestionBankQuestion;

/**
 * 题库题目表服务
 */
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add                  对创建的数据进行校验
     */
    void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add);
}
