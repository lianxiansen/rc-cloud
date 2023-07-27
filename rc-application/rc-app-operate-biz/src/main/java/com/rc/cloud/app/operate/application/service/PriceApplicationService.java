package com.rc.cloud.app.operate.application.service;

import com.alibaba.fastjson.JSON;
import com.rc.cloud.app.operate.domain.model.price.PriceCalParam;
import com.rc.cloud.app.operate.domain.model.price.PriceContext;
import com.yomahub.liteflow.flow.LiteflowResponse;

/**
 * @author WJF
 * @create 2023-07-26 9:23
 * @description TODO
 */

public interface PriceApplicationService {
    PriceContext calPrice(PriceCalParam req);
}

