package com.rc.cloud.uid.generator.service;

import com.rc.cloud.uid.generator.factory.RemoteIdGeneratorFallbackFactory;
import com.rc.cloud.common.core.constant.ServiceNameConstants;
import com.rc.cloud.common.feign.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * id生成服务接口
 * @author hqf@rc
 * @date 2022-04-26
 **/
@FeignClient(
        contextId = "remoteIdGeneratorService",
        value = ServiceNameConstants.ID_GENERATOR_SERVICE,   // 服务名称
        configuration = FeignRequestInterceptor.class,   // 请求拦截器 （关键代码）
        fallbackFactory = RemoteIdGeneratorFallbackFactory.class     // 服务降级处理
)
public interface RemoteIdGeneratorService {

    /**
     * 实时生成
     */
    @GetMapping("/id-gen/uidGenerator")
    String uidGenerator();

    /**
     * 基于redis
     */
    @GetMapping("/id-gen/getUidByRedis")
    String getUidByRedis();

    /**
     * 基于本地缓存生成
     */
    @GetMapping("/id-gen/getUidByLocal")
    Long getUidByLocal();
}
