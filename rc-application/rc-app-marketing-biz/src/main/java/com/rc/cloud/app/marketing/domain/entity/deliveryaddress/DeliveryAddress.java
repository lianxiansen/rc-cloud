package com.rc.cloud.app.marketing.domain.entity.deliveryaddress;

import com.rc.cloud.common.core.util.AssertUtils;

import java.util.Objects;

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
     * 地区
     */
    private Area area;
    /**
     * 是否为默认 0：不是 1：是
     */
    private boolean defaulted;

    public DeliveryAddress(String id, String customerId, String name, String mobile, String zipcode, Area area) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
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

    public String getName() {
        return name;
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



    public void modifyName(String name) {
        this.name = name;
    }

    public void modifyMobile(String mobile) {
        this.mobile = mobile;
    }

    public void modifyZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    private void setArea(Area area) {
        this.area = area;
    }


    public boolean isDefaulted() {
        return defaulted;
    }

    private void modifyArea(Area area) {
        AssertUtils.assertArgumentNotNull(area, "area must not be empty");
        this.area = area;
    }
    public void setDefaulted(boolean defaulted){
        this.defaulted=defaulted;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeliveryAddress that = (DeliveryAddress) o;
        return isDefaulted() == that.isDefaulted() && getId().equals(that.getId()) && getCustomerId().equals(that.getCustomerId()) && getName().equals(that.getName()) && getMobile().equals(that.getMobile()) && getZipcode().equals(that.getZipcode()) && getArea().equals(that.getArea());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getName(), getMobile(), getZipcode(), getArea(), isDefaulted());
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", area=" + area +
                ", defaulted=" + defaulted +
                '}';
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    public void setProvinceCode(String provinceCode) {
        this.area.setProvinceCode(provinceCode);
    }
    public void setProvince(String province) {
        this.area.setProvince(province);
    }

    public void setCityCode(String cityCode) {
        this.area.setCityCode(cityCode);
    }
    public void setCity(String City) {
        this.area.setCity(City);
    }

    public void setDistrictCode(String districtCode) {
        this.area.setDistrictCode(districtCode);
    }
    public void setDistrict(String district) {
        this.area.setDistrict(district);
    }
    public void setDetail(String detail) {
        this.area.setDetail(detail);
    }

}
