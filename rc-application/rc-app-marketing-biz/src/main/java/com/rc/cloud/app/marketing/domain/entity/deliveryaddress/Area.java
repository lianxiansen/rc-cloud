package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

import java.util.Objects;

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
    private String provinceCode;
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

    public Area(String provinceCode, String province, String cityCode, String city, String districtCode, String district, String detail) {
        this.provinceCode = provinceCode;
        this.province = province;
        this.cityCode = cityCode;
        this.city = city;
        this.districtCode = districtCode;
        this.district = district;
        this.detail = detail;
    }

    public String getProvince() {
        return province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Area area = (Area) o;
        return getProvince().equals(area.getProvince()) && getProvinceCode().equals(area.getProvinceCode()) && getCity().equals(area.getCity()) && getCityCode().equals(area.getCityCode()) && getDistrict().equals(area.getDistrict()) && getDistrictCode().equals(area.getDistrictCode()) && getDetail().equals(area.getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProvince(), getProvinceCode(), getCity(), getCityCode(), getDistrict(), getDistrictCode(), getDetail());
    }

    @Override
    public String toString() {
        return "Area{" +
                "province='" + province + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    Area setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
        return this;
    }

    Area setProvince(String province) {
        this.province = province;
        return this;
    }

    Area setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    Area setCity(String city) {
        this.city = city;
        return this;
    }

    Area setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
        return this;
    }

    Area setDistrict(String district) {
        this.district = district;
        return this;
    }

    Area setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
