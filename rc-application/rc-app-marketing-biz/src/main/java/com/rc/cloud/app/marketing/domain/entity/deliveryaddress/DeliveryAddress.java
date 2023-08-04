package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

import com.rc.cloud.app.marketing.domain.entity.common.Area;
import com.rc.cloud.common.core.util.AssertUtils;

/**
 * @ClassName DeliveryAddress
 * @Author liandy
 * @Date 2023/7/28 14:42
 * @Description 收货地址
 * @Version 1.0
 */
public class DeliveryAddress {
    /**
     * 收货地址唯一标识
     */
    private String id;
    /**
     * 用户id
     */
    private String customerId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮政编号
     */
    private String zipcode;
    /**
     * 地区
     */
    private Area area;
    /**
     * 是否为默认 0：不是 1：是
     */
    private boolean defaulted;

    public DeliveryAddress(String id, String customerId, String username, String mobile, String zipcode, Area area) {
        this.id = id;
        this.customerId = customerId;
        this.userName = username;
        this.mobile = mobile;
        this.zipcode = zipcode;
        setArea(area);
    }


    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Area getArea() {
        return area;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    private void setArea(Area area) {
        AssertUtils.assertArgumentNotNull(area, "area must not be empty");
        this.area = area;
    }

    public void setDefault(boolean defaulted){
        this.defaulted=defaulted;
    }
}
