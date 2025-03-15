package com.timeless.mianshi.manager;

import com.timeless.mianshi.esdao.QuestionEsDao;
import com.timeless.mianshi.model.dto.question.QuestionEsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class EsDaoTest {
    @Resource
    private QuestionEsDao questionEsDao;

    @Test
    public void test() {
        List<QuestionEsDTO> byUserId = questionEsDao.findByUserId(1L);
    }
}
