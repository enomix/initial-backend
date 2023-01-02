package com.sp.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.project.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    @Test
    void testDate() throws ParseException {
        String str = "2022-12-17 23:36:31";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.parse(str));
    }
}
