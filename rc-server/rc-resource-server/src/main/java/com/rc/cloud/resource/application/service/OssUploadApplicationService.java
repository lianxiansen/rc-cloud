package com.rc.cloud.resource.application.service;


import com.rc.cloud.resource.application.dto.OssDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author oliveoil
 * date 2022-04-25 10:34
 * 文件上传服务
 */
public interface OssUploadApplicationService {

    OssDTO upload(MultipartFile file);
}
