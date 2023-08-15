package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import java.util.Objects;

/**
 * @ClassName Receiver
 * @Author liandy
 * @Date 2023/7/28 17:18
 * @Description 收货信息
 * @Version 1.0
 */
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

    public Receiver(String receiverContact, String receiverProvince, String receiverCity, String receiverDistrict, String receiverAddressDetail, String receiverMobile) {
        this.receiverContact = receiverContact;
        this.receiverProvince = receiverProvince;
        this.receiverCity = receiverCity;
        this.receiverDistrict = receiverDistrict;
        this.receiverAddressDetail = receiverAddressDetail;
        this.receiverMobile = receiverMobile;
    }


    public String getReceiverProvince() {
        return receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public String getReceiverContact() {
        return receiverContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Receiver receiver = (Receiver) o;
        return getReceiverContact().equals(receiver.getReceiverContact()) && getReceiverProvince().equals(receiver.getReceiverProvince()) && getReceiverCity().equals(receiver.getReceiverCity()) && getReceiverDistrict().equals(receiver.getReceiverDistrict()) && getReceiverAddressDetail().equals(receiver.getReceiverAddressDetail()) && getReceiverMobile().equals(receiver.getReceiverMobile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReceiverContact(), getReceiverProvince(), getReceiverCity(), getReceiverDistrict(), getReceiverAddressDetail(), getReceiverMobile());
    }

    public static Receiver mockReceiver() {
        return new Receiver("某某某", "浙江省", "台州市", "黄岩区", "浙江省台州市黄岩区王西路41号", "13812345678");
    }
}
