package com.timeless.mianshi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timeless.mianshi.model.entity.Question;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author ethereal
 * @description 针对表【question(题目)】的数据库操作Mapper
 * @createDate 2025-03-09 21:55:53
 * @Entity com.timeless.mianshi.model.entity.Question
 */
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 根据更新时间获取题目,包括已删除的
     *
     * @param minUpdateTime 最小更新时间
     * @return 题目列表
     */
    @Select("select * from question where updateTime > #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);

}




