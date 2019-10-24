package com.mjoys.auth.shiro;

import com.alibaba.fastjson.JSON;
import com.mjoys.auth.jwt.JwtToken;
import com.mjoys.auth.jwt.JwtUtil;
import com.mjoys.constant.StatusCode;
import com.mjoys.dto.CommomResponseDto;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;


public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        try {
            CommomResponseDto<String> resDto = new CommomResponseDto<>(StatusCode.ERROR, "非法的请求");
            String res = JSON.toJSONString(resDto);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setContentLength(res.getBytes("UTF-8").length);
            PrintWriter writer = response.getWriter();
            writer.write(res);
        } catch (IOException e) {
        }
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken().setRaw(token);
        //提交给realm进行登入，如果错误他会抛出异常并被捕获
        //依次提交给多个realm，判断是不是支持，不支持跳过
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
}