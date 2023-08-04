package com.rc.cloud.app.operate.application.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @ClassName: BrandBO
 * @Author: liandy
 * @Date: 2023/7/7 14:56
 * @Description: 品牌业务对象
 */
@Data
@Accessors(chain = true)
public class BrandBO {
    private String id;
    private String name;
    private String logo;
    private String type;
    private int sort;
    private boolean enabled;
    private LocalDateTime createTime;
}
