package com.rc.cloud.app.operate.infrastructure.repository.persistence.mapper;

import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductPO;
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
public interface ProductMapper extends BaseMapperX<ProductPO> {

    default PageResult<ProductPO> selectPage(ProductListQueryDTO queryDTO) {
        return selectPage(queryDTO,getWrapper(queryDTO));
    }

    default List<ProductPO> selectList(ProductListQueryDTO queryDTO) {
        return selectList(getWrapper(queryDTO));
    }

    default  LambdaQueryWrapperX<ProductPO> getWrapper(ProductListQueryDTO queryDTO){
        LambdaQueryWrapperX<ProductPO> wrapper=  new LambdaQueryWrapperX<ProductPO>()
                .likeIfPresent(ProductPO::getName, queryDTO.getName())
                .eqIfPresent(ProductPO::getSpuCode,queryDTO.getSpuCode())
                .eqIfPresent(ProductPO::getFirstCategory, queryDTO.getFirstCategory())
                .eqIfPresent(ProductPO::getSecondCategory, queryDTO.getSecondCategory())
                .eqIfPresent(ProductPO::getThirdCategory, queryDTO.getThirdCategory())
                .eqIfPresent(ProductPO::getTenantId,queryDTO.getTenantId())
                .eqIfPresent(ProductPO::getBrandId,queryDTO.getBrandId())
                .eqIfPresent(ProductPO::getOnshelfStatus,queryDTO.getOnshelfStatus())
                .betweenIfPresent(ProductPO::getCreateTime,queryDTO.getStartTime(),
                        queryDTO.getEndTime()
                ).orderByDesc(ProductPO::getCreateTime)
                ;
        //通过商品id查询
        if(queryDTO.getProductIds()!=null){
            String[] ids = queryDTO.getProductIds().split(",");
            wrapper.in(ProductPO::getId,ids);
        }
        //排序
        if(ProductListQueryDTO.CREATE_TIME.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.DESC.equals(queryDTO.getOrderByType())){
            wrapper.orderByDesc(ProductPO::getCreateTime);

        }else if(ProductListQueryDTO.SORT_ID.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.DESC.equals(queryDTO.getOrderByType())){
            wrapper.orderByDesc(ProductPO::getSortId);
        }
        else if(ProductListQueryDTO.CREATE_TIME.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.ASC.equals(queryDTO.getOrderByType())){
            wrapper.orderByAsc(ProductPO::getCreateTime);
        }else if(ProductListQueryDTO.SORT_ID.equals(queryDTO.getOrderByCondition())
                && ProductListQueryDTO.ASC.equals(queryDTO.getOrderByType())){
            wrapper.orderByAsc(ProductPO::getSortId);
        }
        return wrapper;
    }

}
