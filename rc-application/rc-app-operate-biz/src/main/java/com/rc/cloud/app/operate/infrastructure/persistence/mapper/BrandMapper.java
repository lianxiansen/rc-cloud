package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper extends BaseMapperX<BrandDO> {

//    default PageResult<ProductDO> selectPage(ProductListQueryDTO queryDTO) {
//        return selectPage(queryDTO, new LambdaQueryWrapperX<ProductDO>()
//                .likeIfPresent(ProductDO::getName, queryDTO.getName())
//                .eqIfPresent(ProductDO::getFirstCategory, queryDTO.getFirstCategory())
//                .eqIfPresent(ProductDO::getSecondCategory, queryDTO.getSecondCategory())
//                .eqIfPresent(ProductDO::getThirdCategory, queryDTO.getThirdCategory())
//                .eqIfPresent(ProductDO::getTenantId,queryDTO.getTenantId())
//        );
//    }
}
