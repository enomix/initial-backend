package com.sp.project.mapper;

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
}
