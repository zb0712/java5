package com.szb.java5.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szb.java5.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义返回结果
 * @author 石致彬
 * @since 2021-03-30 22:36
 */
@Component
public class ResultAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        Result rest = Result.error("尚未登录");
        writer.write(new ObjectMapper().writeValueAsString(rest));
        writer.flush();
        writer.close();
    }
}
