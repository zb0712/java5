package com.szb.java5.service;

import com.szb.java5.bean.Admin;
import com.szb.java5.bean.Questionnaire;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szb.java5.common.Result;

import java.security.Principal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-04
 */
public interface QuestionnaireService extends IService<Questionnaire> {

    int creatQuestionnaire(Questionnaire questionnaire);

    Questionnaire getQuestionnaireByNameAndDepartId(String questionnaireName, Long departmentId);

    Result getQuestionnairesByDepartmentId(Long departmentId);

    Result getQuestionnaireById(Long questionnaireId, Admin admin);

    Result getQuestionnaireByDepartmentId(Long departmentId);
}
