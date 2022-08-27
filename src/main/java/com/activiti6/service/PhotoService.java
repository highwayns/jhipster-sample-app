package com.activiti6.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    /**
     * 根据bpmn文件导入增加模型
     *
     * @param file 上传的文件
     * @return 增加的模型信息
     */
    Boolean importPhoto(MultipartFile file, int userId);

    InputStream exportPhoto(int userId);

    Boolean importIDCardPhoto(MultipartFile file, int userId);

    InputStream exportIDCardPhoto(int userId);
}