package com.rc.cloud.app.marketing.infrastructure.repository.mapper;

import com.rc.cloud.app.marketing.infrastructure.repository.po.OrderPO;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderMapper
 * @Author liandy
 * @Date 2023/8/3 14:24
 * @Description  订单Mapper
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapperX<OrderPO> {
}
