package com.szb.java5.controller;


import com.szb.java5.bean.Admin;
import com.szb.java5.bean.LoginParam;
import com.szb.java5.common.Result;
import com.szb.java5.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-30
 */
@RestController
public class LoginController {
    @Autowired
    AdminService adminService;


    @ApiOperation("登录之后返回token")
    @PostMapping("/login")
    public Result login(LoginParam loginParam, HttpServletRequest request) {
        return adminService.login(loginParam.getUsername(),loginParam.getPassword(),request);
    }

    @ApiOperation(value = "获取登录信息")
    @GetMapping("/info")
    public Result getInfo(Principal principal) {
        if (null==principal) {
            return Result.error("未登录");
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByName(username);
        admin.setAdminPassword(null);
        Map<String,Object> map = new HashMap<>();
        map.put("admin",admin);
        return Result.ok(map);
    }


    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout() {
        return Result.ok(null);
    }
}
