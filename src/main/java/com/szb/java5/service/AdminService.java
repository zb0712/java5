package com.szb.java5.service;

import com.szb.java5.bean.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szb.java5.common.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-30
 */
public interface AdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    Result login(String username, String password, HttpServletRequest request);

    /**
     * 根据name获取admin对象
     * @param username
     * @return
     */
    Admin getAdminByName(String username);

    Result creatAdmin(String name, String password, String departmentName);

    Result getAllStudentInfoByDepartmentId(Long departmentId);

    Result updateStudentName(Long stuId, String name);

    Result updateStudentNum(Long stuId, String stuNum);

    Result updateStudentEmail(Long stuId, String stuEmail);

    Result creatExam(String examName, Long departmentId);

    Result finishExam(Long examId, Long departmentId);

    Result pass(Long stuId, Long examId, Long departmentId);

    Result fail(Long stuId, Long examId, Long departmentId);

    Result getAllExamInfo(Long departmentId);

    Result getExamMsg(Long examId, Integer current, Integer pageSize, Long departmentId);
}
