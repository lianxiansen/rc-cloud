package com.rc.cloud.app.mall.application.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rc.cloud.app.mall.appearance.request.ItemSpecificationListQuery;
import com.rc.cloud.app.mall.application.data.ItemSpecKeyValuesRedisDTO;
import com.rc.cloud.app.mall.application.data.ItemSpecValueDTO;
import com.rc.cloud.app.mall.application.service.IItemSpecificationService;
import com.rc.cloud.app.mall.infrastructure.config.RedisKey;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ItemSpecificationMapper;
import com.rc.cloud.app.mall.infrastructure.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*
* @Author taotianhong
* @Date 2021-03-26
* @Description:
*/
@Service
public class ItemSpecificationServiceImpl extends ServiceImpl<ItemSpecificationMapper, ItemSpecification> implements IItemSpecificationService {

    @Autowired
    private ItemSpecificationMapper itemSpecificationMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取
     * @param productId 商品ID
     * @return
     */
    @Override
    public List<ItemSpecification> getItemSpecifications(int productId) {
        QueryWrapper<ItemSpecification> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return itemSpecificationMapper.selectList(wrapper);
    }

    /**
     * 通过多个SKUID获取实例列表
     * @param itemIds SKUID们
     * @return 实例列表
     */
    @Override
    public List<ItemSpecification> getItemSpecificationListByItemIds(List<Integer> itemIds) {
        ItemSpecificationListQuery query = new ItemSpecificationListQuery();
        query.setItemIds(itemIds);
        return itemSpecificationMapper.getItemSpecificationList(query);
    }

    /**
     * 获取商品SKU规格缓存实例列表
     * @param productId 商品ID
     * @return 商品SKU规格缓存实例列表
     */
    @Override
    public List<ItemSpecKeyValuesRedisDTO> getItemSpecKeyValuesRedisDTOList(int productId) {
        List<ItemSpecKeyValuesRedisDTO> result = new ArrayList<>();
        QueryWrapper<ItemSpecification> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        List<ItemSpecification> itemSpecs = itemSpecificationMapper.selectList(wrapper);
        if (itemSpecs != null && !itemSpecs.isEmpty() && itemSpecs.size() > 0) {
            Map<Integer, List<ItemSpecification>> map = itemSpecs.stream().collect(Collectors.groupingBy(ItemSpecification::getSpecificationID));
            result = map.keySet().stream().map(key -> {
                List<ItemSpecification> values = map.get(key);
                ItemSpecKeyValuesRedisDTO dto = new ItemSpecKeyValuesRedisDTO();
                dto.setSpecId(values.get(0).getSpecificationID());
                dto.setSpecName(values.get(0).getSpecificationTitle());
                dto.setSortId(values.get(0).getSortIDForTitle());

                Map<Integer, List<ItemSpecification>> valueMap = values.stream().collect(Collectors.groupingBy(ItemSpecification::getSpecificationValueID));
                List<ItemSpecValueDTO> vdtos = valueMap.keySet().stream().map(vkey -> {
                    List<ItemSpecification> vValues = valueMap.get(vkey);
                    ItemSpecValueDTO vdto = new ItemSpecValueDTO();
                    vdto.setSpecValueId(vValues.get(0).getSpecificationValueID());
                    vdto.setSpecValueName(vValues.get(0).getSpecificationContent());
                    vdto.setSortId(vValues.get(0).getSortID());
                    return vdto;
                }).collect(Collectors.toList());
                dto.setValues(vdtos);
                return dto;
            }).collect(Collectors.toList());
        }
        return  result;
    }

    /**
     * 批量保存
     * @param itemSpecifications
     * @return 保存数量
     */
    @Override
    public Integer saveItemSpecifications(List<ItemSpecification> itemSpecifications) {
        Integer rows = 0;
        for (ItemSpecification itemSpecification : itemSpecifications) {
            if (itemSpecification.getId() > 0) {
                QueryWrapper<ItemSpecification> wrapper = new QueryWrapper<>();
                wrapper.eq("ID", itemSpecification.getId());
                rows += itemSpecificationMapper.update(itemSpecification, wrapper);
            } else {
                rows += itemSpecificationMapper.insert(itemSpecification);
            }
        }
        return rows;
    }

    /**
     * 删除商品的所有SKU规格
     * @param productId 商品ID
     * @return 删除数量
     */
    @Override
    public Integer removeItemSpecifications(int productId) {
        QueryWrapper<ItemSpecification> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return itemSpecificationMapper.delete(wrapper);
    }

    /**
     * 设置商品SKU规格缓存
     * @param productId 商品ID
     * @param itemSpecifications 商品SKU规格缓存
     */
    @Override
    public void setItemSpecificationsRedis(int productId, List<ItemSpecKeyValuesRedisDTO> itemSpecifications) {
        redisUtil.putHash(RedisKey.getItemSpecificationKeyValusGroupsHashKey(), productId, JSONUtil.toJsonStr(itemSpecifications));
    }

    /**
     * 获取商品SKU规格缓存
     * @param productId
     * @return
     */
    @Override
    public List<ItemSpecKeyValuesRedisDTO> getItemSpecificationsRedis(int productId) {

        List<ItemSpecKeyValuesRedisDTO> res = redisUtil.getListFromHash(RedisKey.getItemSpecificationKeyValusGroupsHashKey(), productId,ItemSpecKeyValuesRedisDTO.class);
        return  res;
    }

}
