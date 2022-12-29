package com.sp.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除用户请求
 *
 * @author yupi
 */
@Data
public class UserDeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}