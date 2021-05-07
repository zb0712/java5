package com.szb.java5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szb.java5.bean.*;
import com.szb.java5.common.Result;
import com.szb.java5.mapper.*;
import com.szb.java5.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-08
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StuDepartmentMapper stuDepartmentMapper;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Override
    public Result submit(Student student) {
        Student student1 = getStudentByNameAndNum(student.getStuName(), student.getStuNum());
        Long stuId;
        Long departmentId = student.getDepartmentId();
        if (student.equals(student1)) { //如果已经报名过
            if (isSignedUp(student1.getStuId(),departmentId)) {
                return Result.error("您已报名过该部门,请勿重复报名");
            }
            if (!student.getStuEmail().equals(student1.getStuEmail())) {
                return Result.error("该邮箱与之前报名邮箱不一致,无法报名,请联系管理员修改");
            }
            stuId = student.getStuId();
        } else {
            studentMapper.insert(student);
            student1 = getStudentByNameAndNum(student.getStuName(), student.getStuNum());
            stuId = student1.getStuId();
        }

        StuDepartment stuDepartment = new StuDepartment();
        stuDepartment.setStuId(stuId);
        stuDepartment.setDepartmentId(departmentId);
        stuDepartment.setStatus("examing");
        stuDepartmentMapper.insert(stuDepartment);

        List<Answer> answers = student.getAnswers();
        for (Answer answer : answers) {
            Long questionId = answer.getQuestionId();
            Questions question = questionsMapper.selectOne(new QueryWrapper<Questions>().eq("question_id", questionId));
            answer.setStuId(stuId);
            answer.setQuestionnaireId(question.getQuestionnaireId());
            answerMapper.insert(answer);
        }
        return Result.ok(null);
    }



    public Student getStudentByNameAndNum(String name,String num) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_name",name);
        wrapper.eq("stu_num",num);
        return studentMapper.selectOne(wrapper);
    }

    public boolean isSignedUp(Long stuId,Long departmentId) {
        QueryWrapper<StuDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id",stuId);
        wrapper.eq("department_id",departmentId);
        StuDepartment stuDepartment = stuDepartmentMapper.selectOne(wrapper);
        return stuDepartment != null;
    }
}
