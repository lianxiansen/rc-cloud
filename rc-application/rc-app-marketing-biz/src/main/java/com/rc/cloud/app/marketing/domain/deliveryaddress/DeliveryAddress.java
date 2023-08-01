package com.rc.cloud.app.marketing.domain.deliveryaddress;

/**
 * @ClassName DeliveryAddress
 * @Author liandy
 * @Date 2023/7/28 14:42
 * @Description 收货地址
 * @Version 1.0
 */
public class DeliveryAddress {
    private String id;
    private String username;
    private String mobile;
    private String zipcode;
    private Area area;
    private boolean defaulted;
    public DeliveryAddress(String id,String username, String mobile, String zipcode, Area area) {
        this.id = id;
        this.username = username;
        this.mobile = mobile;
        this.zipcode = zipcode;
        this.area = area;
    }



    public String getId() {
        return id;
    }
}
