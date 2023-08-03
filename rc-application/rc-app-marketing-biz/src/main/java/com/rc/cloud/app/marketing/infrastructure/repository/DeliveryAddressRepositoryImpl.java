package com.rc.cloud.app.marketing.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressRepository;
import com.rc.cloud.app.marketing.infrastructure.repository.convert.DeliveryAddressConvert;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.DeliveryAddressMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.DeliveryAddressPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName DeliveryAddressRepositoryImpl
 * @Author liandy
 * @Date 2023/8/3 14:44
 * @Description TODO
 * @Version 1.0
 */
@Repository
public class DeliveryAddressRepositoryImpl implements DeliveryAddressRepository {
    @Resource
    private DeliveryAddressMapper deliveryAddressMapper;

    @Override
    public boolean save(DeliveryAddress deliveryAddress) {
        DeliveryAddressPO po=DeliveryAddressConvert.toDeliveryAddressPO(deliveryAddress);
        String idVal = po.getId();
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(deliveryAddressMapper.selectById((Serializable) idVal))) {
            return deliveryAddressMapper.updateById(po) > 0;
        }
        return deliveryAddressMapper.insert(po) > 0;
    }

    @Override
    public DeliveryAddress findById(String id) {
        DeliveryAddressPO po = deliveryAddressMapper.selectById((Serializable) id);
        return DeliveryAddressConvert.toDeliveryAddress(po);
    }

    @Override
    public List<DeliveryAddress> findByCustomerId(String customerId) {
        List<DeliveryAddress> deliveryAddresses = new ArrayList<>();
        List<DeliveryAddressPO> pos = deliveryAddressMapper.selectList(DeliveryAddressPO::getCustomerId, customerId);
        pos.forEach(po -> {
            deliveryAddresses.add(DeliveryAddressConvert.toDeliveryAddress(po));
        });
        return deliveryAddresses;
    }
}
