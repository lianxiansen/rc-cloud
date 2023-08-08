package com.rc.cloud.app.marketing.application.service.impl;

import com.rc.cloud.app.marketing.application.bo.DeliveryAddressBO;
import com.rc.cloud.app.marketing.application.bo.convert.DeliveryAddressConvert;
import com.rc.cloud.app.marketing.application.dto.DeliveryAddressCreateDTO;
import com.rc.cloud.app.marketing.application.dto.DeliveryAddressUpdateDTO;
import com.rc.cloud.app.marketing.application.service.DeliveryAddressApplicationService;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.customer.Customer;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddressService;
import com.rc.cloud.app.marketing.infrastructure.config.constant.DeliveryAddressErrorCodeConstant;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName DeliveryAddressApplicationServiceImpl
 * @Author liandy
 * @Date 2023/8/5 16:09
 * @Description 收货地址应用服务实现
 * @Version 1.0
 */
@Service
public class DeliveryAddressApplicationServiceImpl implements DeliveryAddressApplicationService {

    @Resource
    private DeliveryAddressService deliveryAddressService;

    @Resource
    private IdRepository idRepository;

    @Override
    public DeliveryAddressBO createDeliveryAddress(DeliveryAddressCreateDTO dto) {
        Customer customer=Customer.mock();
        dto.validate();
        Area area = new Area(dto.getProvinceCode(),dto.getProvince(),dto.getCityCode(),dto.getCity(),dto.getDistrictCode(),dto.getDistrict(),dto.getDetail());
        DeliveryAddress deliveryAddress=new DeliveryAddress(idRepository.nextId(),customer.getId(),dto.getName(),dto.getMobile(),dto.getZipcode(),area);
        if(Optional.ofNullable(dto.getDefaulted()).isPresent()){
            deliveryAddress.setDefaulted(dto.getDefaulted());
        }
        deliveryAddressService.create(deliveryAddress);
        return DeliveryAddressConvert.INSTANCE.convert(deliveryAddress);
    }

    @Override
    public DeliveryAddressBO updateDeliveryAddress(DeliveryAddressUpdateDTO dto) {
        if(StringUtils.isEmpty(dto.getId())){
            throw new ServiceException(DeliveryAddressErrorCodeConstant.ID_NOT_EMPTY);
        }
        DeliveryAddress deliveryAddress = deliveryAddressService.findById(dto.getId());
        if (Objects.isNull(deliveryAddress)) {
            throw new ServiceException(DeliveryAddressErrorCodeConstant.ID_INVALID);
        }
        changeDeliveryAddress(dto, deliveryAddress);
        deliveryAddressService.modify(deliveryAddress);
        return DeliveryAddressConvert.INSTANCE.convert(deliveryAddress);
    }

    @Override
    public List<DeliveryAddressBO> findCustomerDeliveryAddresses() {
        Customer customer=Customer.mock();
        List<DeliveryAddress> deliveryAddresses=deliveryAddressService.findList(customer.getId());
        return DeliveryAddressConvert.INSTANCE.convert(deliveryAddresses);
    }

    @Override
    public boolean setDeliveryAddressDefault(String id) {
        return false;
    }
    /**
     * 根据dto变更领域实体DeliveryAddress
     * @param dto
     * @param deliveryAddress
     */
    private void changeDeliveryAddress(DeliveryAddressUpdateDTO dto, DeliveryAddress deliveryAddress) {
        if(Objects.nonNull(dto.getName())){
            deliveryAddress.setName(dto.getName());
        }
        if(Objects.nonNull(dto.getMobile())){
            deliveryAddress.setMobile(dto.getMobile());
        }
        if(Objects.nonNull(dto.getZipcode())){
            deliveryAddress.setZipcode(dto.getZipcode());
        }
        if(Objects.nonNull(dto.getProvinceCode())){
            deliveryAddress.setProvinceCode(dto.getProvinceCode());
        }
        if(Objects.nonNull(dto.getProvince())){
            deliveryAddress.setProvince(dto.getProvince());
        }
        if(Objects.nonNull(dto.getCityCode())){
            deliveryAddress.setCityCode(dto.getCityCode());
        }
        if(Objects.nonNull(dto.getCity())){
            deliveryAddress.setCity(dto.getCity());
        }
        if(Objects.nonNull(dto.getDistrictCode())){
            deliveryAddress.setDistrictCode(dto.getDistrictCode());
        }
        if(Objects.nonNull(dto.getDistrict())){
            deliveryAddress.setDistrict(dto.getDistrict());
        }
        if(Objects.nonNull(dto.getDetail())){
            deliveryAddress.setDetail(dto.getDetail());
        }
        if(Objects.nonNull(dto.getDefaulted())){
            deliveryAddress.setDefaulted(dto.getDefaulted());
        }
    }

}
