package com.szb.java5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szb.java5.bean.*;
import com.szb.java5.common.Result;
import com.szb.java5.config.security.JwtTokenUtil;
import com.szb.java5.mapper.*;
import com.szb.java5.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szb.java5.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-30
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StuDepartmentMapper stuDepartmentMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private StuExamMapper stuExamMapper;
    @Autowired
    private MailUtils mailSender;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Result login(String username, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null==userDetails || !passwordEncoder.matches(password,userDetails.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        //更新登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return Result.ok(map);
    }

    @Override
    public Admin getAdminByName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("admin_name",username));
    }

    @Override
    public Result creatAdmin(String name, String password, String departmentName) {
        String pwd = passwordEncoder.encode(password);
        Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_name", departmentName));
        Long departmentId = department.getDepartmentId();
        Admin admin = new Admin(null,name,pwd,departmentId,null);
        adminMapper.insert(admin);
        return Result.ok(null);
    }

    @Override
    public Result getAllStudentInfoByDepartmentId(Long departmentId) {
        List<StuDepartment> list = stuDepartmentMapper.selectList(new QueryWrapper<StuDepartment>().eq("department_id", departmentId));
        if (list.size() == 0) {
            return Result.error("无报名人员信息");
        }
        ArrayList<Long> arrayList = new ArrayList<>();
        for (StuDepartment stuDepartment : list) {
            arrayList.add(stuDepartment.getStuId());
        }
        List<Student> students = studentMapper.selectBatchIds(arrayList);
        Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_id", departmentId));
        Long questionnaireId = department.getQuestionnaireId();
        for (Student student : students) {
            List<Answer> answers = answerMapper.selectList(new QueryWrapper<Answer>().eq("questionnaire_id", questionnaireId).eq("stu_id", student.getStuId()));
            student.setAnswers(answers);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("info",students);
        return Result.ok(map);
    }

    @Override
    public Result updateStudentName(Long stuId, String name) {
        Student student = new Student();
        student.setStuId(stuId);
        student.setStuName(name);
        studentMapper.updateById(student);
        return Result.ok(null);
    }

    @Override
    public Result updateStudentNum(Long stuId, String stuNum) {
        Student student = new Student();
        student.setStuId(stuId);
        student.setStuNum(stuNum);
        studentMapper.updateById(student);
        return Result.ok(null);
    }

    @Override
    public Result updateStudentEmail(Long stuId, String stuEmail) {
        Student student = new Student();
        student.setStuId(stuId);
        student.setStuEmail(stuEmail);
        studentMapper.updateById(student);
        return Result.ok(null);
    }

    @Override
    public Result creatExam(String examName, Long departmentId) {
        Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_name", examName).eq("department_id", departmentId));
        if (exam != null) {
            return Result.error("该考核已存在");
        }
        Exam exam1 = new Exam();
        exam1.setExamName(examName);
        exam1.setDepartmentId(departmentId);
        exam1.setExamStatus("testing");
        examMapper.insert(exam1);
        Exam examBySelect = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_name", examName).eq("department_id", departmentId));
        List<StuDepartment> stuDepartments = stuDepartmentMapper.selectList(new QueryWrapper<StuDepartment>().eq("department_id", departmentId).eq("status", "examing"));
        if (stuDepartments.size() == 0) {
            return Result.error("无符合考核条件的成员，无法发布考核");
        }
        StuExam stuExam = new StuExam();
        stuExam.setExamId(examBySelect.getExamId());
        stuExam.setStatus("testing");
        for (StuDepartment stuDepartment : stuDepartments) {
            stuExam.setStuId(stuDepartment.getStuId());
            stuExamMapper.insert(stuExam);
        }
        return Result.ok(null);
    }

    @Override
    public Result finishExam(Long examId, Long departmentId) {
        Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_id", examId));
        if (!exam.getDepartmentId().equals(departmentId)){
            return Result.error("无该权限");
        }
        List<StuExam> stuExams = stuExamMapper.selectList(new QueryWrapper<StuExam>().eq("exam_id", examId).eq("status", "testing"));
        if (stuExams.size()!= 0) {
            return Result.error("还有学生的成绩未评判，无法结束考核");
        }
        exam.setExamStatus("overed");
        examMapper.updateById(exam);
        QueryWrapper<StuExam> wrapper = new QueryWrapper<>();
        wrapper.select("stu_id").eq("exam_id",examId).eq("status","passed");
        List<StuExam> stuExams1 = stuExamMapper.selectList(wrapper);
        //如果没有通过的，就不用发消息了
        if (stuExams1.size() == 0) {
            return Result.ok(null);
        }
        List<Long> stuIds = stuExams1.stream().map(StuExam::getStuId).collect(Collectors.toList());
        List<Student> students = studentMapper.selectBatchIds(stuIds);
        String[] strings = students.stream().map(Student::getStuEmail).toArray(String[]::new);
        mailSender.sendSimpleMessage("XXXX工作室","恭喜通过本轮考核",strings);
        return Result.ok(null);
    }

    @Override
    public Result pass(Long stuId, Long examId, Long departmentId) {
        Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_id",examId));
        if (!exam.getDepartmentId().equals(departmentId)) {
            return Result.error("无该权限");
        }
        StuExam stuExam = stuExamMapper.selectOne(new QueryWrapper<StuExam>().eq("stu_id", stuId).eq("exam_id", examId));
        stuExam.setStatus("passed");
        stuExamMapper.updateById(stuExam);
        return Result.ok(null);
    }

    @Override
    public Result fail(Long stuId, Long examId, Long departmentId) {
        Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_id",examId));
        if (!departmentId.equals(exam.getDepartmentId())) {
            return Result.error("无该权限");
        }
        StuExam stuExam = stuExamMapper.selectOne(new QueryWrapper<StuExam>().eq("stu_id", stuId).eq("exam_id", examId));
        stuExam.setStatus("failed");
        stuExamMapper.updateById(stuExam);
        StuDepartment stuDepartment = stuDepartmentMapper.selectOne(new QueryWrapper<StuDepartment>().eq("stu_id", stuId).eq("department_id", departmentId));
        stuDepartment.setStatus("failed");
        stuDepartmentMapper.updateById(stuDepartment);
        return Result.ok(null);
    }

    @Override
    public Result getAllExamInfo(Long departmentId) {
        List<Exam> exams = examMapper.selectList(new QueryWrapper<Exam>().eq("department_id", departmentId));
        if (exams.size()==0) {
            return Result.error("暂无考核信息");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("examInfo",exams);
        return Result.ok(map);
    }

    @Override
    public Result getExamMsg(Long examId, Integer current, Integer pageSize, Long departmentId) {
        Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_id",examId));
        if (!departmentId.equals(exam.getDepartmentId())) {
            return Result.error("无该权限");
        }
        Page<StuExam> page = new Page<>(current,pageSize);
        Page<StuExam> pageData = stuExamMapper.selectPage(page, new QueryWrapper<StuExam>().eq("exam_id", examId));
        List<StuExam> records = pageData.getRecords();
        List<Map> items = new ArrayList<>();
        for (StuExam record : records) {
            Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("stu_id", record.getStuId()));
            Map<String,Object> item = new HashMap<>();
            item.put("info",student);
            item.put("status",record.getStatus());
            items.add(item);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("items",items);
        map.put("current",pageData.getCurrent());
        map.put("total",pageData.getTotal());
        map.put("pages",pageData.getPages());
        map.put("size",pageData.getSize());
        return Result.ok(map);
    }
}
