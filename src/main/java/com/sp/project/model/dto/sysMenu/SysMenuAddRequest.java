package com.sp.project.model.dto.sysMenu;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 */
@Data
public class SysMenuAddRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 父节点
     */
    private Long parentId;
    /**
     * 节点类型，1文件夹，2页面，3按钮
     */
    private Integer nodeType;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 页面对应的地址
     */
    private String linkUrl;

    /**
     * 层次
     */
    private Integer level;
    /**
     * 前端vue文件保存的地址
     */
    private String path;

    /**
     * 访问访问菜单所需的权限
     */
    private String auth;

    private static final long serialVersionUID = 1L;
}