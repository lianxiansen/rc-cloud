package com.rc.cloud.app.marketing.appearance.api.vo;

import com.rc.cloud.app.marketing.application.bo.ShopBO;
import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-07-26 10:30
 * @description TODO
 */
@Data
public class ShopCartVO {
    ShopBO shopInfo;

    List<CartProductVO> cartProductList;
}
