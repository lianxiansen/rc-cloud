package com.rc.cloud.app.operate.domain.model.price;

import com.rc.cloud.app.operate.domain.model.price.PriceCalParam;
import com.rc.cloud.app.operate.domain.model.price.PriceContext;

/**
 * @author WJF
 * @create 2023-07-26 9:23
 * @description TODO
 */

public interface PriceService {
    PriceContext calPrice(PriceCalParam req);
}

