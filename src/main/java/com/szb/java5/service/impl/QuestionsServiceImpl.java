package com.szb.java5.service.impl;

import com.szb.java5.bean.Questions;
import com.szb.java5.mapper.QuestionsMapper;
import com.szb.java5.service.QuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-08
 */
@Service
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Questions> implements QuestionsService {

    @Autowired
    QuestionsMapper questionsMapper;
    @Override
    public void creatQuestion(Questions question) {
        questionsMapper.insert(question);
    }
}
