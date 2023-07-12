package com.rc.cloud.app.operate.infrastructure.persistence.repository;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.domain.common.IdRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: RemoteIdRepository
 * @Author: liandy
 * @Date: 2023/7/12 10:21
 * @Description: TODO
 */
@Service
@Primary
public class RemoteIdRepositoryImpl implements IdRepository {
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }
}
