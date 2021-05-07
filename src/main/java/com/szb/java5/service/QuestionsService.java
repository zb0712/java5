package com.szb.java5.service;

import com.szb.java5.bean.Questions;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-08
 */
public interface QuestionsService extends IService<Questions> {

    void creatQuestion(Questions question);
}
