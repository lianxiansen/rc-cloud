package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.operate.application.data.ProductListQueryDTO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.BrandDO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
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
