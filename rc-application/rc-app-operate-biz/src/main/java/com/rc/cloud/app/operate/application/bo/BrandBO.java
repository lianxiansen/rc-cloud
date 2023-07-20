package com.rc.cloud.app.operate.application.bo;

import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.common.core.pojo.PageResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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
    private String id;
    private String name;
    private String type;
    private int sort;
    private boolean enable;
    private LocalDateTime createTime;
    public static PageResult<BrandBO> convertBatch(PageResult<Brand> brandPageResult){
        List<BrandBO> brandVOList=new ArrayList<>();
        brandPageResult.getList().forEach(item->{
            brandVOList.add(convert(item));
        });
        PageResult<BrandBO> brandVOPageResult=new PageResult<>();
        brandVOPageResult.setTotal(brandPageResult.getTotal());
        brandVOPageResult.setList(brandVOList);
        return brandVOPageResult;
    }

    public static BrandBO convert(Brand brand){
        BrandBO brandBO=new BrandBO();
        brandBO.setId(brand.getId().id())
                .setEnable(brand.isEnabled())
                .setName(brand.getName())
                .setSort(brand.getSort())
                .setType(brand.getType())
                .setCreateTime(brand.getCreateTime().getTime());
        return brandBO;
    }
}
