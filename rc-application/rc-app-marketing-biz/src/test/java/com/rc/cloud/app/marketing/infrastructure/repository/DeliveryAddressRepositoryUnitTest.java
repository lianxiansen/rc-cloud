package com.rc.cloud.app.marketing.infrastructure.repository;

import cn.hutool.core.util.RandomUtil;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressRepository;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import com.rc.cloud.common.test.core.util.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private DeliveryAddress deliveryAddress;
    private String customerId = "5b6b70eafeaa9938cff8e430245090c7";
    private String userName;
    private String mobile;
    private String zipcode;

    @BeforeEach
    public void beforeEach() {
        userName = RandomUtil.randomString(10);
        mobile = RandomUtil.randomNumbers(13);
        zipcode = RandomUtil.randomString(8);
        Area area = new Area(RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString());
        deliveryAddress = new DeliveryAddress(idRepository.nextId(), customerId, userName, mobile, zipcode, area);
        deliveryAddress.setDefaulted(true);
        deliveryAddressRepository.save(deliveryAddress);
    }

    @Test
    public void save() {
        boolean saved = deliveryAddressRepository.save(deliveryAddress);
        Assertions.assertTrue(saved);
    }

    @Test
    public void findByCustomerId() {
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findByCustomerId(customerId);
        Assertions.assertTrue(!CollectionUtils.isEmpty(deliveryAddresses));
    }

    @Test
    public void findById() {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(this.deliveryAddress.getId());
        Assertions.assertTrue(
                deliveryAddress.getCustomerId().equals(customerId) &&
                        deliveryAddress.getName().equals(userName) &&
                        deliveryAddress.getMobile().equals(mobile) &&
                        deliveryAddress.getZipcode().equals(zipcode) &&
                        deliveryAddress.getArea().getProvince().equals(this.deliveryAddress.getArea().getProvince()) &&
                        deliveryAddress.getArea().getProvinceCode().equals(this.deliveryAddress.getArea().getProvinceCode()) &&
                        deliveryAddress.getArea().getCity().equals(this.deliveryAddress.getArea().getCity()) &&
                        deliveryAddress.getArea().getCityCode().equals(this.deliveryAddress.getArea().getCityCode()) &&
                        deliveryAddress.getArea().getDistrict().equals(this.deliveryAddress.getArea().getDistrict()) &&
                        deliveryAddress.getArea().getDistrictCode().equals(this.deliveryAddress.getArea().getDistrictCode()) &&
                        deliveryAddress.getArea().getDetail().equals(this.deliveryAddress.getArea().getDetail()) &&
                        deliveryAddress.isDefaulted()
        );
    }
    @Test
    public void removeById(){
        Assertions.assertTrue(deliveryAddressRepository.removeById(deliveryAddress.getId()));
    }

    @Test
    public void updateBatch(){
        List<DeliveryAddress> deliveryAddresses =new ArrayList<>();
        Area area = new Area(RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString());
        deliveryAddress = new DeliveryAddress(idRepository.nextId(), customerId, userName, mobile, zipcode, area);
        deliveryAddresses.add(deliveryAddress);
        deliveryAddressRepository.updateBatch(deliveryAddresses);
    }

}
