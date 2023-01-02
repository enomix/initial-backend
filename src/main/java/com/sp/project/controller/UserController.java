package com.sp.project.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.sp.project.common.BaseResponse;
import com.sp.project.common.ErrorCode;
import com.sp.project.common.ResultUtils;
import com.sp.project.exception.BusinessException;
import com.sp.project.model.User;
import com.sp.project.model.dto.user.*;
import com.sp.project.model.vo.UserVO;
import com.sp.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
             throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录的用户
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 创建用户
     * @param userAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(user.getId());
    }

    /**
     * 删除用户
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    @PostMapping("/delete/batch")
    public BaseResponse<Boolean> deleteBatchUsers(@RequestBody List<Integer> ids) {
        boolean b = userService.removeByIds(ids);
        return ResultUtils.success(b);
    }

    /**
     * 更新用户
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean b = userService.updateById(user);
        return ResultUtils.success(b);
    }

    /**
     * 根据id获取用户
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserVO> getUserById(int id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 获取用户列表
     * @param userQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<UserVO>> listUser(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(queryWrapper);
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVOList);

    }

    /**
     * 分页获取用户列表
     * @param userQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
//    @CrossOrigin //允许跨域
    public BaseResponse<Page<UserVO>> listUserByPage(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
            current = userQueryRequest.getCurrent();
            size = userQueryRequest.getPageSize();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        Page<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

    /**
     * 用户模糊分页查询
     * @param current 当前页
     * @param pageSize 页面大小
     * @param username 用户名
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<UserVO>> listUserByPageAndSearching (@RequestParam Integer current,
                                                                  @RequestParam Integer pageSize,
                                                                  @RequestParam(defaultValue = "") String username) {
        Page<User> userPage = userService.listUserByPageAndSearching(new Page<>(current, pageSize), username);
        PageDTO<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

    /**
     * excel导出接口
     * @param response
     * @throws Exception
     */
    @GetMapping("export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库中查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存中操作, 写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义表格的标题别名(数据库英文字段名转化为想要的格式)
        writer.addHeaderAlias("userName", "用户名");
        writer.addHeaderAlias("userAccount", "账号");
        writer.addHeaderAlias("userAvatar", "头像");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("userRole", "角色权限");
        writer.addHeaderAlias("userPassword", "密码");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "修改时间");

        //写出list内的对象到excel, 使用默认样式, 强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * 导入Excel到数据库
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public BaseResponse<Boolean> importExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2: 忽略表头的中文, 直接读取表的内容
        List<List<Object>> list = reader.read(1);
        ArrayList<User> users = CollUtil.newArrayList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(list);
        for (List<Object> row : list) {
            User user = new User();
            user.setUserName(row.get(1).toString());
            user.setUserAccount(row.get(2).toString());
            user.setUserAvatar(row.get(3).toString());
            user.setGender(row.get(4).toString() == null ? null: Integer.valueOf(row.get(4).toString()));

            user.setUserRole(row.get(5).toString());
            user.setUserPassword(row.get(6).toString());
            user.setCreateTime(simpleDateFormat.parse(row.get(7).toString()));
            user.setUpdateTime(simpleDateFormat.parse(row.get(8).toString()));
            user.setIsDelete(0);
            users.add(user);
        }

        userService.saveBatch(users);

        return ResultUtils.success(true);
    }
}
