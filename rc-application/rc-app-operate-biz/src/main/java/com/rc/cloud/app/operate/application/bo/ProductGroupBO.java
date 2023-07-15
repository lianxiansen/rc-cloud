package com.rc.cloud.app.operate.application.bo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductGroupBO {

    private String Id;
    private String productId;
    private String name;

    private LocalDateTime createTime;

    private List<ProductGroupItemBO> itemList;







}
