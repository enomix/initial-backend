package com.sp.project.model.dto.sysMenu;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除菜单请求
 *
 * @author yupi
 */
@Data
public class SysMenuDeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}