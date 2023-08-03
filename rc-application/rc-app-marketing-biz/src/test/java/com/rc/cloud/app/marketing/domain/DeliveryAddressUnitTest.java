package com.rc.cloud.app.marketing.domain;

import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.DeliveryAddressRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.LocalIdRepositoryImpl;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @ClassName DeliveryAddressUnitTest
 * @Author liandy
 * @Date 2023/8/3 15:33
 * @Description 收货地址资源库单元测试
 * @Version 1.0
 */
@Import({LocalIdRepositoryImpl.class, DeliveryAddressRepositoryImpl.class})
@DisplayName("收货地址资源库单元测试")
public class DeliveryAddressUnitTest extends BaseDbUnitTest {
    @Resource
    private DeliveryAddressRepository deliveryAddressRepository;
    @Resource
    private IdRepository idRepository;

    @Test
    public void save() {
        String customerId = "1";
        String userName = RandomUtil.randomString(10);
        String mobile = RandomUtil.randomNumbers(13);
        String zipcode = RandomUtil.randomString(8);
        Area area = new Area("浙江省", "台州市", "黄岩区", "");
        DeliveryAddress entity = new DeliveryAddress(idRepository.nextId(), customerId, userName, mobile, zipcode, area);
        boolean saved = deliveryAddressRepository.save(entity);
        Assertions.assertTrue(saved);
    }
}
