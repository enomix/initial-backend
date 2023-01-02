package com.sp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.project.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名模糊与分页查询
     * @param page
     * @param username
     * @return
     */
    @Select("select * from user where username like concat('%',#{username},'%') and isDelete = 0")
    Page<User> findPage(Page<User> page, @Param("username") String username);
}
