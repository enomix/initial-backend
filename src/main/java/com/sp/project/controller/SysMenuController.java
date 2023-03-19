package com.sp.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.sp.project.common.BaseResponse;
import com.sp.project.common.ErrorCode;
import com.sp.project.common.ResultUtils;
import com.sp.project.exception.BusinessException;
import com.sp.project.model.SysMenu;
import com.sp.project.model.User;
import com.sp.project.model.dto.sysMenu.SysMenuAddRequest;
import com.sp.project.model.dto.sysMenu.SysMenuDeleteRequest;
import com.sp.project.model.dto.sysMenu.SysMenuQueryRequest;
import com.sp.project.model.dto.sysMenu.SysMenuUpdateRequest;
import com.sp.project.model.vo.SysMenuVO;
import com.sp.project.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单接口
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 获取目录树
     * @param request
     * @return
     */
    @GetMapping("/list/tree")
    public BaseResponse<List<SysMenuVO>> queryMenuTree(Long userId,HttpServletRequest request) {
        if (userId == null) {
            throw new BusinessException(40000, "用户id不能为空");
        }
        List<SysMenuVO> sysMenuVOList = sysMenuService.queryMenuTree(userId);
        return ResultUtils.success(sysMenuVOList);
    }


    /**
     * 创建菜单
     * @param sysMenuAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addSysMenu(@RequestBody SysMenuAddRequest sysMenuAddRequest, HttpServletRequest request) {
        if (sysMenuAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuAddRequest, sysMenu);
        boolean result = sysMenuService.save(sysMenu);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(sysMenu.getId());
    }

    /**
     * 删除菜单
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSysMenu(@RequestBody SysMenuDeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = sysMenuService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 批量删除菜单
     * @param ids
     * @return
     */
    @PostMapping("/delete/batch")
    public BaseResponse<Boolean> deleteBatchSysMenus(@RequestBody List<Integer> ids) {
        boolean b = sysMenuService.removeByIds(ids);
        return ResultUtils.success(b);
    }

    /**
     * 更新菜单
     * @param sysMenuUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateSysMenu(@RequestBody SysMenuUpdateRequest sysMenuUpdateRequest, HttpServletRequest request) {
        if (sysMenuUpdateRequest == null || sysMenuUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuUpdateRequest, sysMenu);
        boolean b = sysMenuService.updateById(sysMenu);
        return ResultUtils.success(b);
    }

    /**
     * 根据id获取菜单
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<SysMenuVO> getSysMenuById(int id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysMenu sysMenu = sysMenuService.getById(id);
        SysMenuVO sysMenuVO = new SysMenuVO();
        BeanUtils.copyProperties(sysMenu, sysMenuVO);
        return ResultUtils.success(sysMenuVO);
    }

    /**
     * 获取菜单列表
     * @param sysMenuQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<SysMenuVO>> listSysMenu(SysMenuQueryRequest sysMenuQueryRequest, HttpServletRequest request) {
        SysMenu sysMenuQuery = new SysMenu();
        if (sysMenuQueryRequest != null) {
            BeanUtils.copyProperties(sysMenuQueryRequest, sysMenuQuery);
        }
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>(sysMenuQuery);
        List<SysMenu> sysMenuList = sysMenuService.list(queryWrapper);
        List<SysMenuVO> sysMenuVOList = sysMenuList.stream().map(sysMenu -> {
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            return sysMenuVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(sysMenuVOList);

    }

    /**
     * 分页获取菜单列表
     * @param sysMenuQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
//    @CrossOrigin //允许跨域
    public BaseResponse<Page<SysMenuVO>> listSysMenuByPage(SysMenuQueryRequest sysMenuQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        SysMenu sysMenuQuery = new SysMenu();
        if (sysMenuQueryRequest != null) {
            BeanUtils.copyProperties(sysMenuQueryRequest, sysMenuQuery);
            current = sysMenuQueryRequest.getCurrent();
            size = sysMenuQueryRequest.getPageSize();
        }
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>(sysMenuQuery);
        Page<SysMenu> sysMenuPage = sysMenuService.page(new Page<>(current, size), queryWrapper);
        Page<SysMenuVO> sysMenuVOPage = new PageDTO<>(sysMenuPage.getCurrent(), sysMenuPage.getSize(), sysMenuPage.getTotal());
        List<SysMenuVO> sysMenuVOList = sysMenuPage.getRecords().stream().map(sysMenu -> {
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            return sysMenuVO;
        }).collect(Collectors.toList());
        sysMenuVOPage.setRecords(sysMenuVOList);
        return ResultUtils.success(sysMenuVOPage);
    }


}
