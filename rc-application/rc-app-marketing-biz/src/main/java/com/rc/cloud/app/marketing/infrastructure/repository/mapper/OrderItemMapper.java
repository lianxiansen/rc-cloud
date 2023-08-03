package com.rc.cloud.app.marketing.infrastructure.repository.mapper;

import com.rc.cloud.app.marketing.infrastructure.repository.po.OrderItemPO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderItemMapper
 * @Author liandy
 * @Date 2023/8/3 14:24
 * @Description  订单项Mapper
 * @Version 1.0
 */
@Mapper
public interface OrderItemMapper extends BaseMapperX<OrderItemPO> {
}
