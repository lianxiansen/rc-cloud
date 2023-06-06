/**
 * @author oliveoil
 * date 2022-04-25 10:37
 */
package com.rc.cloud.resource.application.service.impl;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.common.core.exception.ServiceException2;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.oss.entity.UploadResult;
import com.rc.cloud.common.oss.factory.OssFactory;
import com.rc.cloud.common.oss.service.IOssStrategy;
import com.rc.cloud.resource.application.assembler.OssDTOAssembler;
import com.rc.cloud.resource.application.dto.OssDTO;
import com.rc.cloud.resource.application.service.OssUploadApplicationService;
import com.rc.cloud.resource.domain.model.oss.Oss;
import com.rc.cloud.resource.domain.model.oss.OssId;
import com.rc.cloud.resource.domain.model.oss.OssRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class OssUploadApplicationServiceImpl implements OssUploadApplicationService {

    @Autowired
    private OssRepository ossRepository;

    @Autowired
    private RemoteIdGeneratorService remoteIdGeneratorService;
//
//    public OssUploadApplicationServiceImpl(RemoteIdGeneratorService remoteIdGeneratorService) {
//        this.remoteIdGeneratorService = remoteIdGeneratorService;
//    }

//    @Autowired
//    private UidGenService uidGenService;



    public OssDTO upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        IOssStrategy storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, contentType);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new ServiceException2("上传文件失败");
        }
        // 保存文件信息
        // 生成分布式id
        String id = remoteIdGeneratorService.uidGenerator();
        OssId ossId = new OssId(id); // 应该是自动生成
        Oss oss = new Oss(ossId, uploadResult.getFilename(), originalfileName, suffix, uploadResult.getUrl(), storage.getServiceType().getValue());
        ossRepository.store(oss);
        OssDTO ossDTO = OssDTOAssembler.fromOss(oss);
        return ossDTO;
    }
}
