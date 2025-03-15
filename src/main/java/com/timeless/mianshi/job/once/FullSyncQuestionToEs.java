package com.timeless.mianshi.job.once;

import cn.hutool.core.collection.CollUtil;
import com.timeless.mianshi.esdao.QuestionEsDao;
import com.timeless.mianshi.model.dto.question.QuestionEsDTO;
import com.timeless.mianshi.model.entity.Question;
import com.timeless.mianshi.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步题目到 es
 */
@Slf4j
@Component
public class FullSyncQuestionToEs implements CommandLineRunner {
    @Resource
    private QuestionEsDao questionEsDao;
    @Resource
    private QuestionService questionService;

    @Override
    public void run(String... args) throws Exception {
        // 全量获取题目-数据量不大的情况使用
        List<Question> questionList = questionService.list();
        if (CollUtil.isEmpty(questionList)) {
            return;
        }
        // 转为es实体
        List<QuestionEsDTO> questionEsDTOS = questionList.stream().map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        // 分页批量插入
        final int pageSize = 500;
        for (int i = 0; i < questionEsDTOS.size(); i += pageSize) {
            // toIndex 不能超过 list 长度
            int toIndex = Math.min(i + pageSize, questionEsDTOS.size());
            List<QuestionEsDTO> subList = questionEsDTOS.subList(i, toIndex);
            questionEsDao.saveAll(subList);
        }
        log.info("全量同步题目到 es 成功");
    }
}
