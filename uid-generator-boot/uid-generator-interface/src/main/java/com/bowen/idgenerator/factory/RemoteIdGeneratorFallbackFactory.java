/**
 * @author oliveoil
 * date 2022-04-20 13:48
 */
package com.bowen.idgenerator.factory;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.common.core.exception.FailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteIdGeneratorFallbackFactory implements FallbackFactory<RemoteIdGeneratorService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteIdGeneratorFallbackFactory.class);

    @Override
    public RemoteIdGeneratorService create(Throwable throwable)
    {
        log.error("生成ID服务调用失败:{}", throwable.getMessage());
        return new RemoteIdGeneratorService()
        {

            @Override
            public String uidGenerator() {
                throw new FailedException("生成UidGenerator失败:" + throwable.getMessage());
            }

            @Override
            public String getUidByRedis() {
                throw new FailedException("生成getUidByRedis失败:" + throwable.getMessage());

            }

            @Override
            public Long getUidByLocal() {
                throw new FailedException("生成getUidByLocal失败:" + throwable.getMessage());
            }
        };
    }
}
