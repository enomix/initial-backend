package com.sp.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册登录dto
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3890560336727616810L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
