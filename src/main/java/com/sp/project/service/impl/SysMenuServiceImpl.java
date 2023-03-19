package com.sp.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.project.exception.BusinessException;
import com.sp.project.mapper.SysMenuMapper;
import com.sp.project.mapper.UserMapper;
import com.sp.project.model.SysMenu;
import com.sp.project.model.User;
import com.sp.project.model.vo.SysMenuVO;
import com.sp.project.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询菜单的树状列表
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuVO> queryMenuTree(Long userId) {
        User user = userMapper.selectById(userId);
        String userRole = user.getUserRole();
        if (StringUtils.isEmpty(userRole)) {
            throw new BusinessException(40101,"用户没有设置权限");
        }

//        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(null);
        List<SysMenuVO> sysMenuVOList = sysMenuList.stream().map(sysMenu -> {
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            return sysMenuVO;
        }).collect(Collectors.toList());
        List<SysMenuVO> parentList = sysMenuVOList.stream().filter(p -> p.getParentId() == null).collect(Collectors.toList());
        for (SysMenuVO sysMenuVO : parentList) {
            List<SysMenuVO> level2List = sysMenuVOList.stream().filter(p -> p.getParentId() == sysMenuVO.getId()).collect(Collectors.toList());
            sysMenuVO.setChildren(level2List);
            for (SysMenuVO sysMenuVO1: level2List) {
                List<SysMenuVO> level3List = sysMenuVOList.stream().filter(p -> p.getParentId() == sysMenuVO1.getId()).collect(Collectors.toList());
                sysMenuVO1.setChildren(level3List);
            }
        }


        return parentList;
    }

    /**
     * 根据用户角色权限查询菜单的树状列表
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuVO> queryRoleMenuTree(Long userId) {
        User user = userMapper.selectById(userId);
        String userRole = user.getUserRole();
        if (StringUtils.isEmpty(userRole)) {
            throw new BusinessException(40101,"用户没有设置权限");
        }


        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();

        if (userRole.equals("admin")) {
            sysMenuQueryWrapper = null;
        }
        if (userRole.equals("user")) {
            sysMenuQueryWrapper.eq("auth",userRole);
        }
//        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(sysMenuQueryWrapper);
        List<SysMenuVO> sysMenuVOList = sysMenuList.stream().map(sysMenu -> {
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            return sysMenuVO;
        }).collect(Collectors.toList());
        List<SysMenuVO> parentList = sysMenuVOList.stream().filter(p -> p.getParentId() == null).collect(Collectors.toList());
        for (SysMenuVO sysMenuVO : parentList) {
            List<SysMenuVO> level2List = sysMenuVOList.stream().filter(p -> p.getParentId() == sysMenuVO.getId()).collect(Collectors.toList());
            sysMenuVO.setChildren(level2List);
            for (SysMenuVO sysMenuVO1: level2List) {
                List<SysMenuVO> level3List = sysMenuVOList.stream().filter(p -> p.getParentId() == sysMenuVO1.getId()).collect(Collectors.toList());
                sysMenuVO1.setChildren(level3List);
            }
        }


        return parentList;
    }



    /**
     * 递归生成嵌套树状目录
     * @param parentId
     * @param parentList
     * @return
     */
    public List<SysMenuVO> childrenThree(Long parentId, List<SysMenuVO> parentList) {
        List<SysMenuVO> list = new ArrayList<>();
        for (SysMenuVO sysMenuVO : parentList) {
            if (Objects.equals(sysMenuVO.getParentId(), parentId)) {
                list.add(sysMenuVO);
                List<SysMenuVO> childrenTree = childrenThree(sysMenuVO.getParentId(), parentList);
                sysMenuVO.setChildren(childrenTree);
            }
        }
        return parentList;
    }
}
