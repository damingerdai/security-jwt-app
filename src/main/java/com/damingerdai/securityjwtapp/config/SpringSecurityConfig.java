package com.damingerdai.securityjwtapp.config;

import com.damingerdai.securityjwtapp.filter.JWTLoginFilter;
import com.damingerdai.securityjwtapp.filter.SecurityAuthTokenFilter;
import com.damingerdai.securityjwtapp.service.impl.SecurityUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author gming001
 * @version 2022-06-26 20:15
 */
@EnableWebSecurity
//开启权限注解,默认是关闭的
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 放行 swagger 相关路径
        String[] authWhiteList = {
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/**",
                "/csrf",

                // other
                "/css/**",
                "/js/**",
                "/html/**",
                "/instances",
                "/favicon.ico"
        };
        //对于在header里面增加token等类似情况，放行所有OPTIONS请求。
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                // 可以直接访问的静态数据或接口
                .requestMatchers(authWhiteList);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, SecurityUserService securityUserService, PasswordEncoder passwordEncoder) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(securityUserService).passwordEncoder(passwordEncoder);
        var authenticationManager = authenticationManagerBuilder.build();
        http
                .authorizeHttpRequests()// 授权
                .requestMatchers("/index/**").anonymous()// 匿名用户权限
                .requestMatchers("/api/**").hasRole("USER")//普通用户权限
                .requestMatchers("/login").permitAll()
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()// 异常
                .exceptionHandling()
//                .accessDeniedHandler(accessDeny)//授权异常处理
//                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)// 认证异常处理
                .and()
                .logout()
                //.logoutSuccessHandler(authenticationLogout)
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SecurityAuthTokenFilter(authenticationManager),UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security不创建HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationManager(authenticationManager)
                .csrf().disable();// 关闭 csrf

        return http.build();
    }

}
