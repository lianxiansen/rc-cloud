package com.rc.cloud.app.operate.appearance.vo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductGroupItemVO {

    private String Id;

    private String productId;

    private String productName;

    private String createTime;
}
