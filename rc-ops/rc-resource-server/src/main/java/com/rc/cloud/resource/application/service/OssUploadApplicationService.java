/**
 * @author oliveoil
 * date 2022-04-25 10:34
 */
/**
 * 文件上传服务
 */
package com.rc.cloud.resource.application.service;


import com.rc.cloud.resource.application.dto.OssDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface OssUploadApplicationService {

    public OssDTO upload(MultipartFile file);
}
