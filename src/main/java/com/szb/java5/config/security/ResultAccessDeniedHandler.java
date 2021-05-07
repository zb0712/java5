package com.szb.java5.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szb.java5.common.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 石致彬
 * @since 2021-03-30 22:39
 */
@Component
public class ResultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        Result rest = Result.error("权限不足");
        writer.write(new ObjectMapper().writeValueAsString(rest));
        writer.flush();
        writer.close();
    }
}
