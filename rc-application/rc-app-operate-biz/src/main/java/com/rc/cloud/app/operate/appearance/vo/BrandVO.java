package com.rc.cloud.app.operate.appearance.vo;

import com.rc.cloud.app.operate.appearance.assemble.BrandAssemble;
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
public class BrandVO {
    private String Id;
    private String name;
    private String type;
    private int sort;
    private boolean enable;

    public static PageResult<BrandVO> from(PageResult<BrandBO> brandPageResult){
        List<BrandVO> brandVOList=new ArrayList<>();
        brandPageResult.getList().forEach(item->{
            brandVOList.add(from(item));
        });
        PageResult<BrandVO> brandVOPageResult=new PageResult<>();
        brandVOPageResult.setTotal(brandPageResult.getTotal());
        brandVOPageResult.setList(brandVOList);
        return brandVOPageResult;
    }

    public static BrandVO from (BrandBO brand){
        return BrandAssemble.INSTANCE.convert2BrandVO(brand);
    }
}
