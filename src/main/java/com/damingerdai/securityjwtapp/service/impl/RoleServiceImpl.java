package com.damingerdai.securityjwtapp.service.impl;

import com.damingerdai.securityjwtapp.domain.Role;
import com.damingerdai.securityjwtapp.repository.mapper.RoleMapper;
import com.damingerdai.securityjwtapp.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gming001
 * @version 2022-06-25 23:11
 */
@Service
public class RoleServiceImpl implements IRoleService {

    private RoleMapper roleMapper;

    @Override
    public Role get(String id) {
        return this.roleMapper.get(id);
    }

    @Override
    public List<Role> list() {
        return this.roleMapper.list();
    }

    @Override
    public List<Role> listByUserId(String userId) {
        return this.roleMapper.listByUserId(userId);
    }

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
}
