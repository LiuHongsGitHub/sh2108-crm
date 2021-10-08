package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.bean.User;

import java.util.List;

public interface UserService {
    List<User> queryAllUser();

    User login(User user, String code, String correctCode, String ip);

    void verify(User loginPwd, String oldPwd);

    void updateUser(User user);

    User selectUserById(String id);
}
