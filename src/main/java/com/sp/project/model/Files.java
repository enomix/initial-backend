package com.sp.project.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;


/**
* 
* @TableName sys_file
*/
@TableName(value = "sys_file")
@Data
public class Files implements Serializable {

    /**
    * id
    */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
    * 文件名称
    */
    private String fileName;


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
    * 是否删除
    */
    @TableLogic
    private Integer isDelete;


    /**
    * 是否禁用链接
    */
    private Integer isEnable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
