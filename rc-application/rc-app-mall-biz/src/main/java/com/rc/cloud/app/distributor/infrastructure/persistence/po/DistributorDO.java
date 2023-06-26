package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import lombok.experimental.Accessors;

/**
 * 经销商 DO
 *
 * @author wjf
 */
@TableName("distributor")
@KeySequence("id") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDO extends BaseDO {

    /**
     * id
     */
    @TableId(type=IdType.ASSIGN_ID)
    private Integer id;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系手机
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
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
    private String county;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 合作开始
     */
    private String start;
    /**
     * 合作结束
     */
    private String end;
    /**
     * 对接状态1未对接2已对接
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 管理员id
     */
    private Integer adminId;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 客户渠道id
     */
    private Integer channel;
    /**
     * 获客方式id
     */
    private Integer source;
    /**
     * 客户等级id
     */
    private Integer level;
    /**
     * 信誉等级
     */
    private Integer reputation;
    /**
     * 成立时间
     */
    private String establishedTime;

    /**
     * 是否锁定
     */
    private Integer locking;

}
