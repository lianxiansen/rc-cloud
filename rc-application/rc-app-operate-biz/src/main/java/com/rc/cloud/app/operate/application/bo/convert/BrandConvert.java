package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.BrandBO;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.common.core.pojo.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BrandConvert
 * @Author liandy
 * @Date 2023/8/4 13:39
 * @Description 品牌转换器 将领域实体转化为业务对象BO
 * @Version 1.0
 */
public class BrandConvert {
    public static PageResult<BrandBO> convert2PageResult(PageResult<Brand> brandPageResult){
        List<BrandBO> brandVOList= convertBatch(brandPageResult.getList());
        PageResult<BrandBO> brandVOPageResult=new PageResult<>();
        brandVOPageResult.setTotal(brandPageResult.getTotal());
        brandVOPageResult.setList(brandVOList);
        return brandVOPageResult;
    }
    public static List<BrandBO> convertBatch(List<Brand> brands){
        List<BrandBO> brandVOList=new ArrayList<>();
        brands.forEach(item->{
            brandVOList.add(convert(item));
        });
        return brandVOList;
    }
    public static BrandBO convert(Brand brand){
        BrandBO brandBO=new BrandBO();
        brandBO.setId(brand.getId().id())
                .setEnabled(brand.isEnabled())
                .setName(brand.getName())
                .setSort(brand.getSort())
                .setType(brand.getType())
                .setCreateTime(brand.getCreateTime().getTime())
                .setLogo(brand.getLogo());
        return brandBO;
    }
}
