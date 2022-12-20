package com.sp.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.project.model.User;

public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
