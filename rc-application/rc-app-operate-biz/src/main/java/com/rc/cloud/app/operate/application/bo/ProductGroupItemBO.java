package com.rc.cloud.app.operate.application.bo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ProductGroupItemBO {

    private String id;

    private String productId;

    private String productName;

    private LocalDateTime createTime;
}
