package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.model.brand.Brand;
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
public class BrandBO {
    private String Id;
    private String name;
    private String type;
    private int sort;
    private boolean enable;

    public static PageResult<BrandBO> from(PageResult<Brand> brandPageResult){
        List<BrandBO> brandVOList=new ArrayList<>();
        brandPageResult.getList().forEach(item->{
            brandVOList.add(from(item));
        });
        PageResult<BrandBO> brandVOPageResult=new PageResult<>();
        brandVOPageResult.setTotal(brandPageResult.getTotal());
        brandVOPageResult.setList(brandVOList);
        return brandVOPageResult;
    }

    public static BrandBO from (Brand brand){
        BrandBO brandBO=new BrandBO();
        brandBO.setId(brand.getId().id())
                .setEnable(brand.isEnable())
                .setName(brand.getName())
                .setSort(brand.getSort())
                .setType(brand.getType());
        return brandBO;
    }
}
