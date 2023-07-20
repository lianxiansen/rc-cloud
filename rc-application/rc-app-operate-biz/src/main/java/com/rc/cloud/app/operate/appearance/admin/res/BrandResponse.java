package com.rc.cloud.app.operate.appearance.admin.res;

import com.rc.cloud.app.operate.appearance.admin.res.convert.BrandConvert;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.common.core.pojo.PageResult;
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
    @Schema(description = "品牌类型")
    private String type;
    @Schema(description = "排序")
    private int sort;
    @Schema(description = "状态，是否启用")
    private boolean enable;

    public static PageResult<BrandResponse> from(PageResult<BrandBO> brandPageResult){
        List<BrandResponse> brandVOList=new ArrayList<>();
        brandPageResult.getList().forEach(item->{
            brandVOList.add(from(item));
        });
        PageResult<BrandResponse> brandVOPageResult=new PageResult<>();
        brandVOPageResult.setTotal(brandPageResult.getTotal());
        brandVOPageResult.setList(brandVOList);
        return brandVOPageResult;
    }

    public static BrandResponse from (BrandBO brand){
        return BrandConvert.INSTANCE.convert2BrandVO(brand);
    }
}
