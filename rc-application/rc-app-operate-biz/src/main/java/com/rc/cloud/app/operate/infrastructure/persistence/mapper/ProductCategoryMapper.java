package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductCategoryDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangzhihao
 * @Date 2021-03-28
 * @Description:
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapperX<ProductCategoryDO> {

}
