package com.rc.cloud.app.operate.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: CreateBrandDTO
 * @Author: liandy
 * @Date: 2023/7/7 11:09
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class BrandCreateDTO {
    @NotNull(message = "品牌名称不能为空")
    private String name;

    private String type;

    private Boolean enabled;

    private Integer sortId;
}
