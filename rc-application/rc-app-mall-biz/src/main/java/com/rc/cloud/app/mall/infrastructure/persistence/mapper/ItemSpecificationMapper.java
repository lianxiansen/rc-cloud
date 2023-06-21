package com.rc.cloud.app.mall.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rc.cloud.app.mall.appearance.request.ItemSpecificationListQuery;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ItemSpecification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
public interface ItemSpecificationMapper extends BaseMapper<ItemSpecification> {

    List<ItemSpecification> getItemSpecificationList(@Param("query") ItemSpecificationListQuery query);
}
