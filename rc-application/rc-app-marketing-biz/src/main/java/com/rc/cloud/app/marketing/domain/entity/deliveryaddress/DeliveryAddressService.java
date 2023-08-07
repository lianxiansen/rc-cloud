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

    public DeliveryAddress findById(String id) {
        return deliveryAddressRepository.findById(id);
    }

    /**
     * 获取顾客默认收货地址
     *
     * @param customerId
     * @return
     */
    public DeliveryAddress findDefault(String customerId) {
        List<DeliveryAddress> deliveryAddresses = findList(customerId);
        if (CollectionUtils.isEmpty(deliveryAddresses)) {
            return null;
        }
        return deliveryAddresses.stream().filter(item -> item.isDefaulted()).findFirst().orElse(deliveryAddresses.get(0));
    }

    /**
     * 获取顾客收货地址列表
     *
     * @param customerId
     * @return
     */
    public List<DeliveryAddress> findList(String customerId) {
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findByCustomerId(customerId);
        return deliveryAddresses;
    }

    /**
     * 新建收货地址
     *
     * @param deliveryAddress
     * @return
     */
    public boolean create(DeliveryAddress deliveryAddress) {
        if (deliveryAddress.isDefaulted()) {
            cancelDefaultByCustomerId(deliveryAddress.getCustomerId());
        }
        return this.deliveryAddressRepository.save(deliveryAddress);
    }
    /**
     * 修改收货地址
     *
     * @param deliveryAddress
     * @return
     */
    public boolean modify(DeliveryAddress deliveryAddress){
        if (deliveryAddress.isDefaulted()) {
            cancelDefaultByCustomerId(deliveryAddress.getCustomerId());
        }
        return this.deliveryAddressRepository.save(deliveryAddress);
    }

    /**
     * 设置默认收货地址
     *
     * @param deliveryAddress
     * @return
     */
    public boolean setDefault(DeliveryAddress deliveryAddress) {
        cancelDefaultByCustomerId(deliveryAddress.getCustomerId());
        deliveryAddress.setDefaulted(true);
        return this.deliveryAddressRepository.save(deliveryAddress);
    }

    /**
     * 取消默认
     *
     * @param deliveryAddress
     * @return
     */
    public boolean cancelDefault(DeliveryAddress deliveryAddress) {
        deliveryAddress.setDefaulted(false);
        return this.deliveryAddressRepository.save(deliveryAddress);
    }


    private void cancelDefaultByCustomerId(String customerId) {
        List<DeliveryAddress> deliveryAddresses = this.deliveryAddressRepository.findByCustomerId(customerId);
        deliveryAddresses.forEach(item -> {
            item.setDefaulted(false);
        });
        if (CollectionUtils.isEmpty(deliveryAddresses)) {
            return;
        }
        this.deliveryAddressRepository.updateBatch(deliveryAddresses);
    }


}
