package com.rc.cloud.app.operate.application.dto;

import com.rc.cloud.common.core.pojo.PageParam;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: CreateBrandDTO
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class BrandQueryPageDTO extends PageParam {
    private String name;
}
