package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

/**
 * @ClassName Address
 * @Author liandy
 * @Date 2023/7/28 14:43
 * @Description 地区
 * @Version 1.0
 */
public class Area {
    private String province;
    private String city;
    private String district;
    private String detail;

    public Area(String province, String city, String district, String detail) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.detail = detail;
    }
}
