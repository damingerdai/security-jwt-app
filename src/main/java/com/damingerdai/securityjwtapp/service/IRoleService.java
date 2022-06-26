package com.damingerdai.securityjwtapp.service;

import com.damingerdai.securityjwtapp.domain.Role;

import java.util.List;

/**
 * @author gming001
 * @version 2022-06-25 23:10
 */
public interface IRoleService {

    Role get(String id);

    List<Role> list();

    List<Role> listByUserId(String userId);
}
