package com.sp.project.service;

import com.sp.project.model.vo.SysMenuVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysMenuServiceTests {
    @Autowired
    private SysMenuService sysMenuService;

    @Test
    void queryMenuTree() {
        List<SysMenuVO> sysMenuVOS = sysMenuService.queryMenuTree(1L);
        System.out.println(sysMenuVOS);
    }

    @Test
    void queryRoleMenuTree() {
        List<SysMenuVO> sysMenuVOS = sysMenuService.queryRoleMenuTree(1L);
        List<SysMenuVO> sysMenuVOS1 = sysMenuService.queryRoleMenuTree(2L);
        System.out.println(sysMenuVOS1);
    }
}
