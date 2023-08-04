package com.rc.cloud.app.operate.appearance.admin.resp;

import com.rc.cloud.app.operate.appearance.admin.resp.convert.BrandConvert;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.date.LocalDateTimeUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BrandVO
 * @Author: liandy
 * @Date: 2023/7/7 14:56
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
@Schema(description = "品牌response")
public class BrandResponse {
    @Schema(description = "品牌唯一标识")
    private String Id;
    @Schema(description = "品牌名称")
    private String name;
    @Schema(description = "logo")
    private String logo;
    @Schema(description = "品牌类型")
    private String type;
    @Schema(description = "排序")
    private int sort;
    @Schema(description = "状态，是否启用")
    private boolean enabled;
    @Schema(description = "创建时间")
    private String createTime;

    public static PageResult<BrandResponse> from(PageResult<BrandBO> brandPageResult) {
        List<BrandResponse> brandVOList =from(brandPageResult.getList());
        PageResult<BrandResponse> brandVOPageResult = new PageResult<>();
        brandVOPageResult.setTotal(brandPageResult.getTotal());
        brandVOPageResult.setList(brandVOList);
        return brandVOPageResult;
    }

    public static BrandResponse from(BrandBO brand) {
        BrandResponse response = BrandConvert.INSTANCE.convert2BrandVO(brand);
        response.setCreateTime(LocalDateTimeUtils.format(brand.getCreateTime()));
        return response;
    }

    public static List<BrandResponse> from(List<BrandBO> brands) {
        List<BrandResponse> brandVOList = new ArrayList<>();
        brands.forEach(item -> {
            brandVOList.add(from(item));
        });
        return brandVOList;
    }
}
