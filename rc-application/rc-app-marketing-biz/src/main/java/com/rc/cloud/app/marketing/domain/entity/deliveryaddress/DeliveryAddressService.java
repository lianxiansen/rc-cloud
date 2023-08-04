package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName DeliveryAddressDomainService
 * @Author liandy
 * @Date 2023/7/29 15:49
 * @Description TODO
 * @Version 1.0
 */
@Service
public class DeliveryAddressService {
    @Resource
    private DeliveryAddressRepository deliveryAddressRepository;
    public DeliveryAddress findById(String id){
        return deliveryAddressRepository.findById(id);
    }

    /**
     * 获取顾客默认收货地址
     * @param customerId
     * @return
     */
    public DeliveryAddress findDefaultDeliveryAddress(String customerId){
        List<DeliveryAddress> deliveryAddresses= deliveryAddressRepository.findByCustomerId(customerId);
        if(CollectionUtils.isEmpty(deliveryAddresses)){
            return null;
        }
        return deliveryAddresses.stream().filter(item->item.isDefaulted()).findFirst().orElse(deliveryAddresses.get(0));
    }
}
