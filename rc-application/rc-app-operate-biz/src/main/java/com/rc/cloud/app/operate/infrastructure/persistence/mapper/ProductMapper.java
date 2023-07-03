package com.rc.cloud.app.operate.infrastructure.persistence.mapper;

import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductDO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/8
 * @Description:
 */
@Mapper
public interface ProductMapper extends BaseMapperX<ProductDO> {

    default PageResult<ProductDO> selectPage(ProductListQueryDTO queryDTO) {
        return selectPage(queryDTO,getWrapper(queryDTO));
    }

    default List<ProductDO> selectList(ProductListQueryDTO queryDTO) {
        return selectList(getWrapper(queryDTO));
    }

    default  LambdaQueryWrapperX<ProductDO> getWrapper(ProductListQueryDTO queryDTO){
        LambdaQueryWrapperX<ProductDO> wrapper=  new LambdaQueryWrapperX<ProductDO>()
                .likeIfPresent(ProductDO::getName, queryDTO.getName())
                .eqIfPresent(ProductDO::getSpuCode,queryDTO.getSpuCode())
                .eqIfPresent(ProductDO::getFirstCategory, queryDTO.getFirstCategory())
                .eqIfPresent(ProductDO::getSecondCategory, queryDTO.getSecondCategory())
                .eqIfPresent(ProductDO::getThirdCategory, queryDTO.getThirdCategory())
                .eqIfPresent(ProductDO::getTenantId,queryDTO.getTenantId())
                .eqIfPresent(ProductDO::getBrandId,queryDTO.getBrandId())
                .eqIfPresent(ProductDO::getOnshelfStatus,queryDTO.getOnshelfStatus())
                .betweenIfPresent(ProductDO::getCreateTime,queryDTO.getStartTime(),
                        queryDTO.getEndTime()
                ).orderByDesc(ProductDO::getCreateTime)
                ;
        //通过商品id查询
        if(queryDTO.getProductIds()!=null){
            String[] ids = queryDTO.getProductIds().split(",");
            wrapper.in(ProductDO::getId,ids);
        }
        //排序
        if(ProductListQueryDTO.CREATE_TIME.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.DESC.equals(queryDTO.getOrderByType())){
            wrapper.orderByDesc(ProductDO::getCreateTime);

        }else if(ProductListQueryDTO.SORT_ID.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.DESC.equals(queryDTO.getOrderByType())){
            wrapper.orderByDesc(ProductDO::getSortId);
        }
        else if(ProductListQueryDTO.CREATE_TIME.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.ASC.equals(queryDTO.getOrderByType())){
            wrapper.orderByAsc(ProductDO::getCreateTime);
        }else if(ProductListQueryDTO.SORT_ID.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.ASC.equals(queryDTO.getOrderByType())){
            wrapper.orderByAsc(ProductDO::getSortId);
        }
        return wrapper;
    }

}
