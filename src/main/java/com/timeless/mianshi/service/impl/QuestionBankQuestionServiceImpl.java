package com.timeless.mianshi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timeless.mianshi.common.ErrorCode;
import com.timeless.mianshi.exception.ThrowUtils;
import com.timeless.mianshi.mapper.QuestionBankQuestionMapper;
import com.timeless.mianshi.model.entity.Question;
import com.timeless.mianshi.model.entity.QuestionBankQuestion;
import com.timeless.mianshi.service.QuestionBankQuestionService;
import com.timeless.mianshi.service.QuestionBankService;
import com.timeless.mianshi.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 题库题目表服务实现
 */
@Service
@Slf4j
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion> implements QuestionBankQuestionService {
    @Resource
    @Lazy
    private QuestionService questionService;
    @Resource
    private QuestionBankService questionBankService;

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add                  对创建的数据进行校验
     */
    @Override
    public void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add) {
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.PARAMS_ERROR);
        Long questionId = questionBankQuestion.getQuestionId();
        if (questionId != null) {
            Question question = questionService.getById(questionId);
            ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        }
        Long questionBankId = questionBankQuestion.getQuestionBankId();
        if (questionBankId != null) {
            ThrowUtils.throwIf(questionBankService.getById(questionBankId) == null, ErrorCode.NOT_FOUND_ERROR);
        }
    }
}
