package com.paranoid.dao;

import com.paranoid.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> getUserList();

    User getOneUser(int id);
    // 此处定义返回类型为 int 则可以接收 mapper执行后返回的影响的行数
    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(int id);


}
