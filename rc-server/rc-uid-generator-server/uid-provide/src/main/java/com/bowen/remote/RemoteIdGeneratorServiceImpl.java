package com.bowen.remote;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.bowen.service.UidGenService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * create hqf@rc 2022-04-26
 */
@RestController
public class RemoteIdGeneratorServiceImpl implements RemoteIdGeneratorService {

    @Resource
    private UidGenService uidGenService;

    /**
     * 实时生成
     * @return
     */
    public String uidGenerator() {
        return String.valueOf(uidGenService.getUid());
    }

    /**
     * 基于redis
     * @return
     */
    public String getUidByRedis() {
        return  uidGenService.getUidByRedis();
    }


    /**
     * 基于本地缓存生成
     * @return
     */
    public Long getUidByLocal() {
        return  uidGenService.getUidByLocal();
    }
}
