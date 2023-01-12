package com.sp.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.project.mapper.FileMapper;
import com.sp.project.model.Files;
import com.sp.project.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {
    @Autowired
    private FileMapper fileMapper;
}
