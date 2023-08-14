package com.rc.cloud.app.operate.infrastructure.repository.persistence;

import com.rc.cloud.uid.generator.service.RemoteIdGeneratorService;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: RemoteIdRepository
 * @Author: liandy
 * @Date: 2023/7/12 10:21
 * @Description: TODO
 */
@Component
public class RemoteIdRepositoryImpl implements IdRepository {
    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;
    @Override
    public String nextId() {
        return remoteIdGeneratorService.uidGenerator();
    }
}
