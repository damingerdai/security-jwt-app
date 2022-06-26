package com.damingerdai.securityjwtapp.repository.mapper;

import com.damingerdai.securityjwtapp.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gming001
 * @version 2022-06-25 22:35
 */
@Mapper
public interface RoleMapper {

    Role get(String id);

    List<Role> list();

    List<Role> listByUserId(String userId);
}
