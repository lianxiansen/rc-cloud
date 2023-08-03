package com.rc.cloud.app.marketing.domain.entity.order;

import lombok.Data;

/**
 * @ClassName Receiver
 * @Author liandy
 * @Date 2023/7/28 17:18
 * @Description 收货信息
 * @Version 1.0
 */
@Data
public class Receiver {
    /**
     * 收货人
     */
    private String receiverContact;
    /**
     * 收货地址-省
     */
    private String receiverProvince;
    /**
     * 收货地址-市
     */
    private String receiverCity;
    /**
     * 收货地址-区
     */
    private String receiverDistrict;
    /**
     * 收货地址
     */
    private String receiverAddressDetail;
    /**
     * 收货人手机号
     */
    private String receiverMobile;

    public Receiver(String receiverContact, String receiverAddress, String receiverMobile) {
        this.receiverContact = receiverContact;
        this.receiverAddressDetail = receiverAddress;
        this.receiverMobile = receiverMobile;
    }
}
