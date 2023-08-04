package com.rc.cloud.app.marketing.infrastructure.repository;

import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.marketing.domain.entity.common.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressRepository;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName DeliveryAddressUnitTest
 * @Author liandy
 * @Date 2023/8/3 15:33
 * @Description 收货地址资源库单元测试
 * @Version 1.0
 */
@DisplayName("收货地址资源库单元测试")
@Import({LocalIdRepositoryImpl.class, DeliveryAddressRepositoryImpl.class})
public class DeliveryAddressRepositoryUnitTest extends BaseDbUnitTest {
    @Resource
    private DeliveryAddressRepository deliveryAddressRepository;
    @Resource
    private IdRepository idRepository;
    private DeliveryAddress entity;
    private String customerId = "5b6b70eafeaa9938cff8e430245090c7";
    @BeforeEach
    public void beforeEach() {
        String userName = RandomUtil.randomString(10);
        String mobile = RandomUtil.randomNumbers(13);
        String zipcode = RandomUtil.randomString(8);
        Area area = new Area("浙江省", "台州市", "黄岩区", "");
        entity = new DeliveryAddress(idRepository.nextId(), customerId, userName, mobile, zipcode, area);
        deliveryAddressRepository.save(entity);
    }
    @Test
    public void save() {
        boolean saved = deliveryAddressRepository.save(entity);
        Assertions.assertTrue(saved);
    }

    @Test
    public void findByCustomerId() {
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findByCustomerId(customerId);
        Assertions.assertTrue(!CollectionUtils.isEmpty(deliveryAddresses));
    }

    @Test
    public void findById() {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(entity.getId());
        Assertions.assertTrue(
                deliveryAddress.getCustomerId().equals(customerId) &&
                        deliveryAddress.getUserName().equals("童文") &&
                        deliveryAddress.getMobile().equals("13984276639") &&
                        deliveryAddress.getZipcode().equals("350000") &&
                        deliveryAddress.getArea().getProvince().equals("贵州省") &&
                        deliveryAddress.getArea().getCity().equals("遵义市") &&
                        deliveryAddress.getArea().getDistrict().equals("红花岗区") &&
                        deliveryAddress.getArea().getDetail().equals("贵州省遵义市") &&
                        deliveryAddress.isDefaulted()
        );
    }
}
