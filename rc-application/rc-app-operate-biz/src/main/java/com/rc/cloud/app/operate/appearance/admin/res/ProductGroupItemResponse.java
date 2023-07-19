package com.rc.cloud.app.operate.appearance.admin.res;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductGroupItemResponse {

    private String Id;

    private String productId;

    private String productName;

    private String createTime;
}
