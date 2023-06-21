package com.rc.cloud.app.mall.application.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rc.cloud.app.mall.appearance.request.ItemUpdateInventoryQuery;
import com.rc.cloud.app.mall.application.service.IItemService;
import com.rc.cloud.app.mall.application.service.IItemSpecificationService;
import com.rc.cloud.app.mall.infrastructure.config.RedisKey;
import com.rc.cloud.app.mall.infrastructure.persistence.mapper.ItemMapper;
import com.rc.cloud.app.mall.infrastructure.persistence.po.Item;
import com.rc.cloud.app.mall.infrastructure.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chenjianxiang
 * @Date 2021/2/22
 * @Description:
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IItemSpecificationService iItemSpecificationService;

    @Override
    public Item getItem(int id) {
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", id);
        return itemMapper.selectOne(wrapper);
    }

    /**
     * 获取商品SKU列表
     * @param productId 商品ID
     * @return SKU列表
     */
    @Override
    public List<Item> getItemList(int productId) {
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.eq("ProductID", productId);
        return itemMapper.selectList(wrapper);
    }

    /**
     * 获取商品SKU列表
     * @param itemIds 商品SKUID们
     * @return SKU列表
     */
    @Override
    public List<Item> getItemList(List<Integer> itemIds) {
        return itemMapper.getItemList(itemIds);
    }

    /**
     * 保存商品SKU
     * @param item 商品SKU
     * @return
     */
    @Override
    public Integer saveItem(Item item) {
        Integer result = 0;
        if (item.getId() > 0) {
            QueryWrapper<Item> wrapper = new QueryWrapper<>();
            wrapper.eq("ID", item.getId());
            result = itemMapper.update(item, wrapper);
        } else {
            result = itemMapper.insert(item);
        }
        return result;
    }

    /**
     * 更新库存
     * @param itemId SKUID
     * @param inventoryPlus 库存增量
     */
    @Override
    @Transactional(rollbackFor = {Exception.class} )
    public void updateInventory(int itemId, int inventoryPlus) {
        try {
            ItemUpdateInventoryQuery query = new ItemUpdateInventoryQuery();
            query.setId(itemId);
            query.setInv_plus(inventoryPlus);
            itemMapper.updateInventory(query);
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
    }

    /**
     * 设置商品SKU列表缓存
     * @param productId 商品ID
     * @param items 商品SKU列表
     * @param isFreshOther 是否同时刷新其他缓存
     */
    @Override
    public void setItemsRedis(int productId, List<Item> items, Boolean isFreshOther) {
        String redisKey = RedisKey.getItemHashKey();
        String pRedisKey = RedisKey.getItemsProductIdHashKey(String.valueOf(productId));
        redisUtil.multiPutHash(redisKey, items);
        redisUtil.deleteHash(pRedisKey);
        redisUtil.multiPutHash(pRedisKey, items);
        if (!isFreshOther) {
            return;
        }
        iItemSpecificationService.setItemSpecificationsRedis(productId, iItemSpecificationService.getItemSpecKeyValuesRedisDTOList(productId));
    }

    /**
     * 获取商品SKU列表缓存
     * @param productId 商品ID
     * @return 商品SKU列表
     */
    @Override
    public List<Item> getItemsRedis(int productId) {
        return getItemsRedis(productId, false);
    }

    /**
     * 获取商品SKU列表缓存
     * @param productId 商品ID
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return 商品SKU列表
     */
    @Override
    public List<Item> getItemsRedis(int productId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getItemsProductIdHashKey(String.valueOf(productId));
        List<Item> items = redisUtil.getListFromHashList(redisKey, Item.class);
        if ((CollUtil.isEmpty(items) || items.size() < 1) && isSyncFromDbWhenNull.equals(true)) {
            items = getItemList(productId);
            if (CollUtil.isNotEmpty(items) && items.size() > 0) {
                setItemsRedis(productId, items, false);
            }
        }
        return items;
    }

    /**
     * 设置商品SKU缓存
     * @param item 商品SKU
     */
    @Override
    public void setItemRedis(Item item) {
        for (String s : Arrays.asList(RedisKey.getItemHashKey(), RedisKey.getItemsProductIdHashKey(String.valueOf(item.getProductID())))) {
            redisUtil.putHash(s, item.getId(), item);
        }
    }

    /**
     * 获取商品SKU缓存
     * @param productId 商品ID
     * @param itemId SKU ID
     * @return 商品SKU
     */
    @Override
    public Item getItemRedis(int productId, int itemId) {
        return getItemRedis(productId, itemId, false);
    }

    /**
     * 获取商品SKU缓存
     * @param productId 商品ID
     * @param itemId SKU ID
     * @param isSyncFromDbWhenNull 当没有缓存，是否同步数据库
     * @return 商品SKU
     */
    @Override
    public Item getItemRedis(int productId, int itemId, Boolean isSyncFromDbWhenNull) {
        String redisKey = RedisKey.getItemsProductIdHashKey(String.valueOf(productId));
        Item item = redisUtil.getObjectFromHash(redisKey, itemId, Item.class);
        if (ObjectUtil.isEmpty(item) && isSyncFromDbWhenNull.equals(true)) {
            item = getItem(itemId);
            if (ObjectUtil.isNotEmpty(item)) {
                setItemRedis(item);
            }
        }
        return item;
    }

    @Override
    public Item GetTheItem(List<Integer> selectedSpevValueIds, List<Item> items)
    {
        List<Item> itemWhere = items.stream().filter(i->i.getId()>0).collect(Collectors.toList());
        for(Integer valueId : selectedSpevValueIds)
        {
            itemWhere = itemWhere.stream().filter(i->("," + i.getSpecificationCombinationID() + ",").contains("," + valueId + ",")).collect(Collectors.toList());
        }
        return itemWhere.stream().findFirst().orElse(null);
    }


    @Override
    public int GetTheItemSpecTotalInventory(int thisSpecValueId, List<Integer> selectedSpevValueIds, List<Item> items)
    {
        List<Integer> thisSpecValueIds = new ArrayList<>();
        thisSpecValueIds.add(thisSpecValueId);
        thisSpecValueIds.addAll(selectedSpevValueIds);

        List<Item> itemWhere = items.stream().filter(i->i.getId()>0).collect(Collectors.toList());

        thisSpecValueIds.forEach(u->{ });
        for (int j=0;j<thisSpecValueIds.size();j++) {
            Integer u=thisSpecValueIds.get(j);
            itemWhere = itemWhere.stream().filter(i -> ("," + i.getSpecificationCombinationID() + ",").contains("," + u.intValue() + ",")).collect(Collectors.toList());
        }

        if (itemWhere.size() > 0)
        {
            return itemWhere.stream().map(Item::getInventory).reduce(0, Integer::sum);

        }
        else
        {
            return 0;
        }
    }

    @Override
    public int GetTheItemSpecTotalInventory(int thisSpecValueId, List<Item> items)
    {

        List<Item> itemWhere = items.stream().filter(i -> ("," + i.getSpecificationCombinationID() + ",").contains("," + thisSpecValueId + ",")).collect(Collectors.toList());

        if (itemWhere.size() > 0) {
            return itemWhere.stream().map(Item::getInventory).reduce(0, Integer::sum);
        } else {
            return -1;
        }
    }


}
