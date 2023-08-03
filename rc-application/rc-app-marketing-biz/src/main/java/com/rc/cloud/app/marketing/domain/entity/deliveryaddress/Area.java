package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

/**
 * @ClassName Address
 * @Author liandy
 * @Date 2023/7/28 14:43
 * @Description 地区
 * @Version 1.0
 */
public class Area {
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 详细地址
     */
    private String detail;

    public Area(String province, String city, String district, String detail) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.detail = detail;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getDetail() {
        return detail;
    }
}
