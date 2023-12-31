package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WJF
 * @create 2023-06-27 8:27
 * @description 经销商联系人账号
 */
@TableName("distributor_contact")
@Data
public class DistributorContactPO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 账号名称
     */
    private String name;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 经销商id
     */
    private String distributorId;
}
