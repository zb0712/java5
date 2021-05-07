package com.szb.java5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szb.java5.bean.Admin;
import com.szb.java5.bean.Department;
import com.szb.java5.bean.Questionnaire;
import com.szb.java5.bean.Questions;
import com.szb.java5.common.Result;
import com.szb.java5.mapper.DepartmentMapper;
import com.szb.java5.mapper.QuestionnaireMapper;
import com.szb.java5.mapper.QuestionsMapper;
import com.szb.java5.service.QuestionnaireService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-04
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {

    @Autowired
    QuestionnaireMapper questionnaireMapper;
    @Autowired
    QuestionsMapper questionsMapper;
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public int creatQuestionnaire(Questionnaire questionnaire) {
        return questionnaireMapper.insert(questionnaire);
    }

    @Override
    public Questionnaire getQuestionnaireByNameAndDepartId(String questionnaireName, Long departmentId) {
        QueryWrapper<Questionnaire> wrapper = new QueryWrapper<>();
        wrapper.eq("questionnaire_name",questionnaireName);
        wrapper.eq("department_id",departmentId);
        return questionnaireMapper.selectOne(wrapper);
    }

    @Override
    public Result getQuestionnairesByDepartmentId(Long departmentId) {
        List<Questionnaire> questionnaires = questionnaireMapper.selectList(new QueryWrapper<Questionnaire>().eq("department_id", departmentId));
        for (Questionnaire questionnaire : questionnaires) {
            List<Questions> questions = questionsMapper.selectList(new QueryWrapper<Questions>().eq("questionnaire_id", questionnaire.getQuestionnaireId()));
            questionnaire.setQuestions(questions);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("questionnaires",questionnaires);
        return Result.ok(map);
    }

    @Override
    public Result getQuestionnaireById(Long questionnaireId, Admin admin) {
        Questionnaire questionnaire = questionnaireMapper.selectById(questionnaireId);
        if (questionnaire == null) {
            return Result.error("无该问卷");
        }
        if ("manager".equals(admin.getRole()) && !admin.getDepartmentId().equals(questionnaire.getDepartmentId())) {
            return Result.error("您的部门无该问卷");
        }
        if (questionnaire.getOverTime().getTime()<System.currentTimeMillis()) {
            return Result.error("该问卷已过期");
        }
        List<Questions> questions = questionsMapper.selectList(new QueryWrapper<Questions>().eq("questionnaire_id", questionnaire.getQuestionnaireId()));
        questionnaire.setQuestions(questions);
        Map<String,Object> map = new HashMap<>();
        map.put("questionnaire",questionnaire);
        return Result.ok(map);
    }

    @Override
    public Result getQuestionnaireByDepartmentId(Long departmentId) {
        Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_id", departmentId));
        if (department.getQuestionnaireId() == null) {
            return Result.error("部门当前暂未设置问卷");
        }
        Questionnaire questionnaire = questionnaireMapper.selectOne(new QueryWrapper<Questionnaire>().eq("questionnaire_id", department.getQuestionnaireId()));
        List<Questions> questions = questionsMapper.selectList(new QueryWrapper<Questions>().eq("questionnaire_id", questionnaire.getQuestionnaireId()));
        questionnaire.setQuestions(questions);
        Map<String,Object> map = new HashMap<>();
        map.put("questionnaire",questionnaire);
        return Result.ok(map);
    }
}
