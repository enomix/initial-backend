package com.sp.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.project.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelect() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    void testFindPage() {
        Page<User> userPage = new Page<>();
        Page<User> page = userMapper.findPage(userPage, "张");
        System.out.println(page);
    }
}
