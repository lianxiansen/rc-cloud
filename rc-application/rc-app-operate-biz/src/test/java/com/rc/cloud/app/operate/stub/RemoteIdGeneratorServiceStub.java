package com.rc.cloud.app.operate.stub;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @ClassName: RemoteIdGeneratorServiceStub
 * @Author: liandy
 * @Date: 2023/6/28 10:37
 * @Description: TODO
 */
@Repository
public class RemoteIdGeneratorServiceStub implements RemoteIdGeneratorService {
    @Override
    public String uidGenerator() {
        return UUID.randomUUID().toString().substring(0,32);
    }

    @Override
    public String getUidByRedis() {
        return null;
    }

    @Override
    public Long getUidByLocal() {
        return null;
    }
}
