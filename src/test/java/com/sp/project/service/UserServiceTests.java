package com.sp.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    void testRegister() {
        userService.userRegister("test1", "123456789", "123456789");
    }
}
