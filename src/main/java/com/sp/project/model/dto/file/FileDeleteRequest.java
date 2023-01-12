package com.sp.project.model.dto.file;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除文件请求
 */
@Data
public class FileDeleteRequest implements Serializable {
    /**
     * id
     */
    private Integer id;

    private static final long serialVersionUID = 1L;
}
