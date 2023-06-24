package com.rc.cloud.app.product.application.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author chenjianxiang
 * @Date 2021/2/22
 * @Description:
 */
public interface IItemService extends IService<Item> {

    Item getItem(int id);

    List<Item> getItemList(int productId);

    List<Item> getItemList(List<Integer> itemIds);

    Integer saveItem(Item item);

    void updateInventory(int itemId, int inventoryPlus);


    void setItemsRedis(int productId, List<Item> items, Boolean isFreshOther);

    List<Item> getItemsRedis(int productId);

    List<Item> getItemsRedis(int productId, Boolean isSyncFromDbWhenNull);

    void setItemRedis(Item item);

    Item getItemRedis(int productId, int itemId);

    Item getItemRedis(int productId, int itemId, Boolean isSyncFromDbWhenNull);

    Item GetTheItem(List<Integer> selectedSpevValueIds, List<Item> items);

    int GetTheItemSpecTotalInventory(int thisSpecValueId, List<Integer> selectedSpevValueIds, List<Item> items);

    int GetTheItemSpecTotalInventory(int thisSpecValueId, List<Item> items);
}
