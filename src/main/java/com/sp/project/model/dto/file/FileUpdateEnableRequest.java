package com.sp.project.model.dto.file;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改文件是否启用
 */
@Data
public class FileUpdateEnableRequest implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 是否启用
     */
    private Integer isEnable;

    private static final long serialVersionUID = 1L;
}
