package com.atguigu.oss.service;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface OssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile multipartFile);
}
