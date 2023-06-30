package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rc.cloud.app.operate.appearance.request.ProductListVO;
import com.rc.cloud.app.operate.appearance.request.ProductRequestVO;
import com.rc.cloud.app.operate.application.data.*;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
@Mapper
public interface ProductMapper extends BaseMapperX<ProductDO> {

    default PageResult<ProductDO> selectPage(ProductListQueryDTO queryDTO) {
        return selectPage(queryDTO, new LambdaQueryWrapperX<ProductDO>()
                .likeIfPresent(ProductDO::getName, queryDTO.getName())
                .eqIfPresent(ProductDO::getFirstCategory, queryDTO.getFirstCategory())
                .eqIfPresent(ProductDO::getSecondCategory, queryDTO.getSecondCategory())
                .eqIfPresent(ProductDO::getThirdCategory, queryDTO.getThirdCategory())
                .eqIfPresent(ProductDO::getTenantId,queryDTO.getTenantId())
        );
    }

    default List<ProductDO> selectList(ProductListQueryDTO queryDTO) {
        return selectList(new LambdaQueryWrapperX<ProductDO>()
                .likeIfPresent(ProductDO::getName, queryDTO.getName())
                .eqIfPresent(ProductDO::getFirstCategory, queryDTO.getFirstCategory())
                .eqIfPresent(ProductDO::getSecondCategory, queryDTO.getSecondCategory())
                .eqIfPresent(ProductDO::getThirdCategory, queryDTO.getThirdCategory())
                .orderByDesc(ProductDO::getId));
    }

}
