package com.rc.cloud.app.mall.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.mall.application.data.ItemSpecKeyValuesRedisDTO;
import com.rc.cloud.app.mall.infrastructure.persistence.po.ItemSpecification;

import java.util.List;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
public interface IItemSpecificationService extends IService<ItemSpecification> {

    List<ItemSpecification> getItemSpecifications(int productId);

    List<ItemSpecification> getItemSpecificationListByItemIds(List<Integer> itemIds);

    List<ItemSpecKeyValuesRedisDTO> getItemSpecKeyValuesRedisDTOList(int productId);

    Integer saveItemSpecifications(List<ItemSpecification> itemSpecifications);

    Integer removeItemSpecifications(int productId);


    void setItemSpecificationsRedis(int productId, List<ItemSpecKeyValuesRedisDTO> itemSpecifications);

    List<ItemSpecKeyValuesRedisDTO> getItemSpecificationsRedis(int productId);
}