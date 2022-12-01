package com.damingerdai.securityjwtapp.filter;

import com.damingerdai.securityjwtapp.common.Result;
import com.damingerdai.securityjwtapp.domain.SecurityUser;
import com.damingerdai.securityjwtapp.util.JwtUtils;
import com.damingerdai.securityjwtapp.util.ServletUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 这个进行token的认证拦截
 * @author gming001
 * @version 2022-06-26 20:08
 */
public class SecurityAuthTokenFilter extends BasicAuthenticationFilter {

    public SecurityAuthTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        System.out.println(token);
        if(StringUtils.hasLength(token)){
            SecurityUser userInfo = JwtUtils.getUserInfoByToken(token);
            if(userInfo == null){
                ServletUtils.render(request,response, Result.error("Token过期或无效"));
                return;
            }
            if (StringUtils.hasLength(userInfo.getUsername()) &&  SecurityContextHolder.getContext().getAuthentication() == null){
                // 如果没过期，保持登录状态
                if (!JwtUtils.isExpiration(token)){
                    // 将用户信息存入 authentication，方便后续校验
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null,userInfo.getAuthorities());
                    // SecurityContextHolder 权限验证上下文
                    SecurityContext context = SecurityContextHolder.getContext();
                    // 指示用户已通过身份验证
                    context.setAuthentication(authentication);
                    System.out.println("authorite="+authentication.getAuthorities().toString());
                }
            }
        }
        // 继续下一个过滤器
        filterChain.doFilter(request, response);
    }


    /**
     * 从header或者参数中获取token
     * @return token
     */
    public String getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer ")) {
            token = request.getParameter("Authorization");
        } else {
            token = token.substring(7);
        }
        return token;
    }

}
