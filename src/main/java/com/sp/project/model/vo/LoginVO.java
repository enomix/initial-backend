package com.sp.project.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 登录时返回用户信息和菜单信息给前端
 */
@Data
public class LoginVO {

    private UserVO userVO;

    private List<SysMenuVO> sysMenuVOList;
}
