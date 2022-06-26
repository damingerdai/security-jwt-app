package com.damingerdai.securityjwtapp.repository.mapper;

import com.damingerdai.securityjwtapp.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gming001
 * @version 2022-06-25 22:17
 */
@Mapper
public interface UserMapper {

    User get(String id);

    User getByUsername(String username);

    List<User> list();

    String create(User user);
}
