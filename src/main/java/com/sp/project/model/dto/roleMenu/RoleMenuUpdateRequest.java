package com.sp.project.model.dto.roleMenu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单更新请求
 *
 * @author yupi
 */
@Data
public class RoleMenuUpdateRequest implements Serializable {


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户角色
     */
    private String roleid;

    /**
     * 菜单ID
     */
    private Long menuid;


    private static final long serialVersionUID = 1L;
}