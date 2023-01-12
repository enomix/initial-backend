package com.sp.project.model.vo;

import lombok.Data;

@Data
public class FileVO {

    /**
     * id
     */
    private Integer id;


    /**
     * 文件名称
     */
    private String filename;


    /**
     * 文件类型
     */
    private String type;


    /**
     * 文件大小(kb)
     */
    private Long size;


    /**
     * 下载链接
     */
    private String url;

    /**
     * 是否禁用链接
     */
    private Integer isEnable;

    private static final long serialVersionUID = 1L;
}
