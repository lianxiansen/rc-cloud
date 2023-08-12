package com.rc.cloud.resource.application.remote;

import com.rc.cloud.common.core.web.CodeResult;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.application.service.OssUploadApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件请求处理
 * 这个类是若依中没有的，若依中直接调用RemoteFileService接口，没有其实现来，
 * 而本RemoteFileServiceImpl类，是用来对文件信息作保存用的，保存到数据库
 *
 * @author hqf
 */
@Slf4j
@RestController
public class RemoteFileServiceImpl {

    @Autowired
    private OssUploadApplicationService ossUploadApplicationService;

    /**
     * 文件上传请求
     */
    @Transactional(rollbackFor = Exception.class)
//    @DSTransactional //注意这里开启事务，用了动态数据源的事务注解
    public CodeResult<Map<String, String>> upload(MultipartFile file) {
        OssDTO ossDTO = ossUploadApplicationService.upload(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", ossDTO.getUrl());
        map.put("fileName", ossDTO.getFileName());
        return CodeResult.ok(map);
    }
}
