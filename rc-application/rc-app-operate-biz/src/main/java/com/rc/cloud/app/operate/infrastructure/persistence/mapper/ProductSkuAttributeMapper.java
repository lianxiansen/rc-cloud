package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductImageDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductSkuAttributeDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSkuAttributeMapper extends BaseMapperX<ProductSkuAttributeDO> {
}
