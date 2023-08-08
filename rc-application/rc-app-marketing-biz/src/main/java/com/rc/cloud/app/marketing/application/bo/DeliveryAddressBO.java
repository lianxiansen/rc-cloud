package com.rc.cloud.app.marketing.application.bo;

import lombok.Data;

/**
 * @ClassName DeliveryAddressBO
 * @Author liandy
 * @Date 2023/8/3 14:17
 * @Description 收货地址业务对象
 * @Version 1.0
 */
@Data
public class DeliveryAddressBO{
    /**
     * 收货地址唯一标识
     */
    private String id;
    /**
     * 用户id
     */
    private String customerId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮政编号
     */
    private String zipcode;
    /**
     * 省
     */
    private String province;

    private String provinceCode;

    /**
     * 市
     */
    private String city;

    private String cityCode;
    /**
     * 区
     */
    private String district;

    private String districtCode;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 是否为默认 0：不是 1：是
     */
    private boolean defaulted;
}
