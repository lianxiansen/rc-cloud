package com.rc.cloud.app.marketing.domain;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.infrastructure.repository.DeliveryAddressRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.LocalIdRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.convert.DeliveryAddressConvert;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.DeliveryAddressMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.DeliveryAddressPO;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbAndRedisUnitTest;
import com.rc.cloud.common.test.core.util.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName DeliveryAddressServiceTest
 * @Author liandy
 * @Date 2023/8/5 11:15
 * @Description 收货地址单元测试
 * @Version 1.0
 * <p>
 * 1.新建收货地址
 * 1.1新进默认收货地址
 * 2.修改收货地址
 * 2.1修改为默认收货地址
 * 2.2取消默认
 * 3.获取顾客收货地址列表
 * 4.根据唯一标识获取收货地址列表
 */
@Import({
        LocalIdRepositoryImpl.class, DeliveryAddressService.class, DeliveryAddressRepositoryImpl.class
})
@DisplayName("收货地址单元测试")
public class DeliveryAddressServiceTest extends BaseDbAndRedisUnitTest {
    @Resource
    private DeliveryAddressService deliveryAddressService;
    @Resource
    private IdRepository idRepository;

    private DeliveryAddress deliveryAddress;
    @Resource
    private DeliveryAddressMapper deliveryAddressMapper;
    boolean created;
    private String customerId;

    @BeforeEach
    public void beforeEach() {
        customerId = idRepository.nextId();
        Area area = new Area(RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString());
        deliveryAddress = new DeliveryAddress(idRepository.nextId(),
                customerId, RandomUtils.randomString(), RandomUtils.randomString(),
                RandomUtils.randomString(), area);
        deliveryAddress.setDefaulted(true);
        created = deliveryAddressService.create(deliveryAddress);
    }

    @Test
    public void createDeliveryAddress() {
        Assertions.assertTrue(created);
    }

    @Test
    public void createDefaultDeliveryAddress() {
        //数据准备
        Wrapper<DeliveryAddressPO> queryWrapper = new LambdaQueryWrapperX<DeliveryAddressPO>().eq(DeliveryAddressPO::getCustomerId, customerId);
        deliveryAddressMapper.delete(queryWrapper);
        List<DeliveryAddressPO> pos = new ArrayList<>();
        int datasize = 10;
        for (int i = 0; i < datasize; i++) {
            Area area = new Area(RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString());
            deliveryAddress = new DeliveryAddress(idRepository.nextId(),
                    customerId, RandomUtils.randomString(), RandomUtils.randomString(),
                    RandomUtils.randomString(), area);
            deliveryAddress.setDefaulted(true);
            pos.add(DeliveryAddressConvert.toDeliveryAddressPO(deliveryAddress));
        }
        this.deliveryAddressMapper.insertBatch(pos);
        deliveryAddress.setDefaulted(true);
        //执行
        deliveryAddressService.create(deliveryAddress);
        //验证
        queryWrapper = new LambdaQueryWrapperX<DeliveryAddressPO>()
                .eq(DeliveryAddressPO::getCustomerId, customerId)
                .ne(DeliveryAddressPO::getId,deliveryAddress.getId());
        Optional optional=this.deliveryAddressMapper.selectList(queryWrapper).stream().filter(item->item.isDefaulted()).findFirst();
        Assertions.assertTrue(!optional.isPresent());
    }

    @Test
    public void findListByCustomerId() {
        Wrapper<DeliveryAddressPO> queryWrapper = new LambdaQueryWrapperX<DeliveryAddressPO>().eq(DeliveryAddressPO::getCustomerId, customerId);
        deliveryAddressMapper.delete(queryWrapper);
        List<DeliveryAddressPO> pos = new ArrayList<>();
        //数据条数
        int datasize = 10;
        for (int i = 0; i < datasize; i++) {
            Area area = new Area(RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString());
            deliveryAddress = new DeliveryAddress(idRepository.nextId(),
                    customerId, RandomUtils.randomString(), RandomUtils.randomString(),
                    RandomUtils.randomString(), area);
            pos.add(DeliveryAddressConvert.toDeliveryAddressPO(deliveryAddress));
        }
        this.deliveryAddressMapper.insertBatch(pos);
        int actualDataSize = deliveryAddressService.findList(customerId).size();
        Assertions.assertTrue(datasize == actualDataSize);
    }

    @Test
    public void findById() {
        DeliveryAddress deliveryAddressExpected = deliveryAddressService.findById(deliveryAddress.getId());
        Assertions.assertEquals(deliveryAddressExpected, deliveryAddress);
    }
    @Test
    public void cancelDefault(){
        deliveryAddressService.cancelDefault(deliveryAddress);
        Assertions.assertFalse(deliveryAddress.isDefaulted());
    }

    @Test
    public void modify(){
        Area area = new Area(RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString(), RandomUtils.randomString());
        deliveryAddress.modifyName(RandomUtils.randomString());
        deliveryAddress.modifyMobile(RandomUtils.randomString());
        deliveryAddress.modifyZipcode(RandomUtils.randomString());
        boolean modified=deliveryAddressService.modify(deliveryAddress);
        Assertions.assertTrue(modified);
    }
}
