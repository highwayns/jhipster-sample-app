package com.activiti6.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.activiti6.bpmn.builder.ImageBpmnModelUtils;
import com.activiti6.bpmn.builder.ImageGenerator;
import com.activiti6.constant.ProcessConstants;
import com.activiti6.exception.ProcessException;
import com.activiti6.mapper.UserPhotoMapper;
import com.activiti6.service.PhotoService;
import com.activiti6.po.UserPhoto;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流程定义管理Service组件
 * 
 * @author lhj
 */
@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    UserPhotoMapper userPhotoMapper;

    /**
     * 根据bpmn文件导入增加模型
     *
     * @param file 上传的文件
     * @return 增加的模型信息
     */
    @Override
    public Boolean importPhoto(MultipartFile file, int userId) {
        try {
            // 新增模型数据并增加模型的xml和图片
            // 获取图片原始文件名
            String filename = file.getOriginalFilename();
            // 文件名使用当前时间
            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            // 获取上传图片的扩展名(jpg/png/...)
            String extension = FilenameUtils.getExtension(filename);
            // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
            String path = "/upload/" + name + "." + extension;
            InputStream is = file.getInputStream();
            File file_ = new File(path);
            FileUtils.copyInputStreamToFile(is, file_);
            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setId(userId);
            userPhoto.setPhoto(path);
            if (userPhotoMapper.getUserPhoto(userId) != null) {
                userPhotoMapper.updateByPrimaryKey(userPhoto);
            } else {
                userPhotoMapper.save(userPhoto);
            }
            return true;
        } catch (Exception e) {
            log.error("文件解释出错，请检查文件是否为bpmn2.0标准格式", e);
            throw new ProcessException("文件解释出错，请检查文件是否为bpmn2.0标准格式");
        }

    }

    @Override
    public InputStream exportPhoto(int userId) {
        UserPhoto userPhoto = userPhotoMapper.getUserPhoto(userId);
        if (userPhoto != null) {
            String path = userPhoto.getPhoto();
            File file_ = new File(path);
            try {
                InputStream is = new FileInputStream(file_);
                return is;
            } catch (IOException ex) {
            }
        }
        return null;
    }

    /**
     * 根据bpmn文件导入增加模型
     *
     * @param file 上传的文件
     * @return 增加的模型信息
     */
    @Override
    public Boolean importIDCardPhoto(MultipartFile file, int userId) {
        try {
            // 新增模型数据并增加模型的xml和图片
            // 获取图片原始文件名
            String filename = file.getOriginalFilename();
            // 文件名使用当前时间
            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            // 获取上传图片的扩展名(jpg/png/...)
            String extension = FilenameUtils.getExtension(filename);
            // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
            String path = "/upload/" + name + "." + extension;
            InputStream is = file.getInputStream();
            File file_ = new File(path);
            FileUtils.copyInputStreamToFile(is, file_);
            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setId(userId);
            userPhoto.setIDCardphoto(path);
            if (userPhotoMapper.getUserPhoto(userId) != null) {
                userPhotoMapper.updateByPrimaryKey(userPhoto);
            } else {
                userPhotoMapper.save(userPhoto);
            }
            return true;
        } catch (Exception e) {
            log.error("文件解释出错，请检查文件是否为bpmn2.0标准格式", e);
            throw new ProcessException("文件解释出错，请检查文件是否为bpmn2.0标准格式");
        }

    }

    @Override
    public InputStream exportIDCardPhoto(int userId) {
        UserPhoto userPhoto = userPhotoMapper.getUserPhoto(userId);
        if (userPhoto != null) {
            String path = userPhoto.getIDCardphoto();
            File file_ = new File(path);
            try {
                InputStream is = new FileInputStream(file_);
                return is;
            } catch (IOException ex) {
            }
        }
        return null;
    }

}
