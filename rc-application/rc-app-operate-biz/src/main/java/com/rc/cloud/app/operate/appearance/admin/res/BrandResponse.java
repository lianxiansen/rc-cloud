package com.rc.cloud.app.operate.appearance.admin.res;

import com.rc.cloud.app.operate.appearance.admin.convert.BrandConvert;
import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.common.core.pojo.PageResult;
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
public class BrandResponse {
    private String Id;
    private String name;
    private String type;
    private int sort;
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
