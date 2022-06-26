package com.damingerdai.securityjwtapp.service;

import com.damingerdai.securityjwtapp.domain.User;

/**
 * @author gming001
 * @version 2022-06-25 23:09
 */
public interface IUserService {

    User get(String id);

    User getUserByUsername(String username);
}
