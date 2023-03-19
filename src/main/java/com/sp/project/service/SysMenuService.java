package com.sp.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.project.model.SysMenu;
import com.sp.project.model.vo.SysMenuVO;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    /**
     * 查询用户菜单
     * @param userId
     * @return
     */
    List<SysMenuVO> queryMenuTree(Long userId);

    /**
     * 根据用户权限查询用户菜单
     * @param userId
     * @return
     */
    List<SysMenuVO> queryRoleMenuTree(Long userId);
}
