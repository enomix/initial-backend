package com.sp.project.controller;

import com.sp.project.common.BaseResponse;
import com.sp.project.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色权限控制器
 */
@RestController
@RequestMapping("/role/menu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

}
