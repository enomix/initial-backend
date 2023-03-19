package com.sp.project.model.dto.roleMenu;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除角色菜单请求
 *
 * @author yupi
 */
@Data
public class RoleMenuDeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}