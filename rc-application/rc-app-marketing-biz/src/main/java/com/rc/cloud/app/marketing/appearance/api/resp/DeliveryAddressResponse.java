package com.rc.cloud.app.marketing.appearance.api.resp;

import lombok.Data;

/**
 * @ClassName DeliveryAddressBO
 * @Author liandy
 * @Date 2023/8/3 14:17
 * @Description 收货地址响应对象
 * @Version 1.0
 */
@Data
public class DeliveryAddressResponse {
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

    private String provinceCode;
    /**
     * 省
     */
    private String province;


    /**
     * 市
     */
    private String cityCode;
    private String city;


    /**
     * 区
     */
    private String districtCode;

    private String district;

    /**
     * 详细地址
     */
    private String detail;
    /**
     * 是否为默认 0：不是 1：是
     */
    private boolean defaulted;
}
