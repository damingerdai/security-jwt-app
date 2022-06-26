package com.damingerdai.securityjwtapp.service.impl;

import com.damingerdai.securityjwtapp.domain.User;
import com.damingerdai.securityjwtapp.repository.mapper.UserMapper;
import com.damingerdai.securityjwtapp.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author gming001
 * @version 2022-06-25 23:14
 */
@Service
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;

    @Override
    public User get(String id) {
        return this.userMapper.get(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userMapper.getByUsername(username);
    }

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
