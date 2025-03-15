package com.timeless.mianshi.job.cycle;

import cn.hutool.core.collection.CollUtil;
import com.timeless.mianshi.esdao.QuestionEsDao;
import com.timeless.mianshi.mapper.QuestionMapper;
import com.timeless.mianshi.model.dto.question.QuestionEsDTO;
import com.timeless.mianshi.model.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步题目到 es
 */
@Component
@Slf4j
public class IncSyncQuestionToEs {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionEsDao questionEsDao;


    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void incSyncQuestionToEs() {
        //  1. 查询近 5 分钟内的数据
        long FIVE_MINUTES = 5 * 60 * 1000L;
        Date minUpdateTime = new Date(new Date().getTime() - FIVE_MINUTES);
        //  2. 查询题目
        List<Question> questionList = questionMapper.listQuestionWithDelete(minUpdateTime);
        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc question");
            return;
        }
        // 3. 转为es实体
        List<QuestionEsDTO> questionEsDTOS =
                questionList.stream().map(QuestionEsDTO::objToDto).collect(Collectors.toList());
        // 4. 分页批量插入
        final int pageSize = 500;
        int total = questionEsDTOS.size();
        log.info("IncSyncQuestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            questionEsDao.saveAll(questionEsDTOS.subList(i, end));
        }
        log.info("IncSyncQuestionToEs end, total {}", total);
    }
}
