package com.rc.cloud.app.mall.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.cloud.app.mall.appearance.request.ItemUpdateInventoryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjianxiang
 * @since 2021-02-22
 */
public interface ItemMapper extends BaseMapper<Item> {

    List<Item> getItemList(@Param("list") List<Integer> list);

    void updateInventory(@Param("query") ItemUpdateInventoryQuery query);
}
