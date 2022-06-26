package com.damingerdai.securityjwtapp.service.impl;

import com.damingerdai.securityjwtapp.domain.SecurityUser;
import com.damingerdai.securityjwtapp.service.IRoleService;
import com.damingerdai.securityjwtapp.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gming001
 * @version 2022-06-25 23:16
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private IUserService userService;

    private IRoleService roleService;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userService.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误！！");
        }
        System.out.println(user);
        var roles = this.roleService.listByUserId(user.getId());
        Set<GrantedAuthority> grantedAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().trim())).collect(Collectors.toSet());
        var securityUser = new SecurityUser();
        securityUser.setId(user.getId());
        securityUser.setUsername(username);
        securityUser.setNickName(user.getNickName());
        securityUser.setPassword(user.getPassword());
        securityUser.setAuthorities(grantedAuthorities);
        return securityUser;
    }

    public SecurityUserService(IUserService userService, IRoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
}
