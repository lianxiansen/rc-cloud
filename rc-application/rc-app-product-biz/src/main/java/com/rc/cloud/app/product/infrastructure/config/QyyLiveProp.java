package com.rc.cloud.app.product.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/19
 * @Description:
 */
@Component
@Data
public class QyyLiveProp {


    @Value("${qyy-live.domain.id}")
    private  String domainId;

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    @Value("${qyy-live.gateway.h5}")
    private String gateWay;

    @Value("${qyy-live.gateway.api}")
    private String apiGateWay;

}
