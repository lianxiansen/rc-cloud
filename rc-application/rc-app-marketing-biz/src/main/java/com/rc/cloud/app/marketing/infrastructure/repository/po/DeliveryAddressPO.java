package com.rc.cloud.app.marketing.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName DeliveryAddressPO
 * @Author liandy
 * @Date 2023/8/3 14:17
 * @Description 收货地址持久化对象
 * @Version 1.0
 */
@TableName("delivery_address")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryAddressPO extends BaseDO {
    /**
     * 收货地址唯一标识
     */
    private String id;
    /**
     * 用户id
     */
    @TableField("customer_id")
    private String customerId;
    /**
     * 姓名
     */
    @TableField("name")
    private String name;
    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 邮政编号
     */
    @TableField("zipcode")
    private String zipcode;
    /**
     * 省
     */
    @TableField("province_code")
    private String provinceCode;

    @TableField("province")
    private String province;


    /**
     * 市
     */
    @TableField("city_code")
    private String cityCode;
    @TableField("city")
    private String city;


    /**
     * 区
     */
    @TableField("district_code")
    private String districtCode;

    @TableField("district")
    private String district;


    /**
     * 详细地址
     */
    @TableField("detail")
    private String detail;
    /**
     * 是否为默认 0：不是 1：是
     */
    @TableField("defaulted")
    private boolean defaulted;
}
