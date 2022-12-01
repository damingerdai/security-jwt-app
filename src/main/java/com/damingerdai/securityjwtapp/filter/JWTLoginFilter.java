package com.damingerdai.securityjwtapp.filter;

import com.damingerdai.securityjwtapp.common.Result;
import com.damingerdai.securityjwtapp.domain.SecurityUser;
import com.damingerdai.securityjwtapp.util.JwtUtils;
import com.damingerdai.securityjwtapp.util.ServletUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author gming001
 * @version 2022-06-26 12:04
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 父类的构造方法
     * @param defaultFilterProcessesUrl 默认需要过滤的 url
     * @param authenticationManager 权限管理器
     */
    public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    /**
     /**
     * 自定义处理 登录认证，这里使用的json body 登录
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * 登录成功返回 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
        SecurityUser principal = (SecurityUser)auth.getPrincipal();
        var securityUser = new SecurityUser();
        securityUser.setUsername(principal.getUsername());
        securityUser.setAuthorities(new HashSet<>(principal.getAuthorities()));
        String token = JwtUtils.generateKey(securityUser);
        try {
            //登录成功時，返回json格式进行提示
            ServletUtils.render(request,response, Result.ok(token));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 失败返回
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String result="";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            result="账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            result="密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            result="密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            result="账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            result="账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            result="用户不存在";
        }
        // 其他错误
        else{
            result="未知异常";
        }
        ServletUtils.render(request,response, Result.error(result));
    }

}
