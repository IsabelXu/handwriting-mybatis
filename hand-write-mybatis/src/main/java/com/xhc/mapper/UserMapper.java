package com.xhc.mapper;

import com.xhc.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> list();

    User findById(User user);

    Integer add(User user);

    Integer updateUser(User user);
    //Integer updateUser(User user);

    Integer delete(User user);

    Integer deleteUserById(Integer id);
}
