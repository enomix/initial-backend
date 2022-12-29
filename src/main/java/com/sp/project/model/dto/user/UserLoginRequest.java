package com.sp.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -3539463635574084602L;

    private String userAccount;

    private String userPassword;
}
