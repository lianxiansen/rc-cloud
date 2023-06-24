package com.rc.cloud.app.product.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.product.infrastructure.persistence.po.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

}
