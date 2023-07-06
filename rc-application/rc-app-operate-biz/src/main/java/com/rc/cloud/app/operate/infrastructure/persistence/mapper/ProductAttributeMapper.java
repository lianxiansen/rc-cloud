package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributeDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductAttributeMapper extends BaseMapperX<ProductAttributeDO> {
}
