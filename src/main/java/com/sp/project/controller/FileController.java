package com.sp.project.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.sp.project.common.BaseResponse;
import com.sp.project.common.ErrorCode;
import com.sp.project.common.ResultUtils;
import com.sp.project.exception.BusinessException;
import com.sp.project.mapper.FileMapper;
import com.sp.project.model.Files;
import com.sp.project.model.User;
import com.sp.project.model.dto.file.FileDeleteRequest;
import com.sp.project.model.dto.file.FileUpdateEnableRequest;
import com.sp.project.model.dto.file.FileUpdateRequest;
import com.sp.project.model.dto.user.UserDeleteRequest;
import com.sp.project.model.dto.user.UserQueryRequest;
import com.sp.project.model.vo.FileVO;
import com.sp.project.model.vo.UserVO;
import com.sp.project.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public BaseResponse<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        //定义文件的唯一标识符
        String fileUUID =date + IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath, fileUUID);
        //判断配置的文件目录是否存在, 若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        //存储文件到目录
        file.transferTo(uploadFile);
        String url = "http://localhost:9090/file/" + fileUUID;


        //存储文件信息到数据库
        Files saveFile = new Files();
        saveFile.setFileName(fileUUID);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        fileMapper.insert(saveFile);

        return ResultUtils.success(url);
    }


    /**
     * 文件下载接口
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }


    /**
     * 分页获取文件列表
     * @param current
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<Files>> listFileByPage(@RequestParam Integer current,
                                                     @RequestParam Integer pageSize,
                                                     @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> filesQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            filesQueryWrapper.like("fileName", name);
        }
        Page<Files> filesPage = fileMapper.selectPage(new Page<>(current, pageSize), filesQueryWrapper);

        return ResultUtils.success(filesPage);
    }

    /**
     * 更新文件信息
     * @param fileUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateFile(@RequestBody FileUpdateRequest fileUpdateRequest) {
        if (fileUpdateRequest == null || fileUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Files files = new Files();
        BeanUtils.copyProperties(fileUpdateRequest, files);
        fileMapper.updateById(files);
        return ResultUtils.success(true);
    }

    /**
     * 文件是否启用
     * @param request
     * @return
     */
    @PostMapping("/update/enable")
    public BaseResponse<Boolean> updateEnable(@RequestBody FileUpdateEnableRequest request) {
        if (request.getId() == null || request.getIsEnable() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<Files> filesUpdateWrapper = new UpdateWrapper<>();
        filesUpdateWrapper.eq("id", request.getId());
        Files files = new Files();
        files.setIsEnable(request.getIsEnable());
        fileMapper.update(files, filesUpdateWrapper);
        return ResultUtils.success(true);
    }


    /**
     * 删除文件
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody FileDeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        fileMapper.deleteById(deleteRequest.getId());
        return ResultUtils.success(true);
    }


    @PostMapping("/delete/batch")
    public BaseResponse<Boolean> deleteBatchFiles(@RequestBody List<Integer> ids) {
        boolean b = fileService.removeByIds(ids);
        return ResultUtils.success(b);
    }

}
