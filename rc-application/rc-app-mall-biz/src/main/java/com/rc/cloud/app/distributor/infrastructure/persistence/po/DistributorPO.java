package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

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
public class DistributorPO extends BaseDO {

    /**
     * id
     */
    @TableId(type=IdType.ASSIGN_ID)
    private String id;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 联系方式
     */
    private String mobile;
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
    private String startTime;
    /**
     * 合作结束
     */
    private String endTime;
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
    private String adminId;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 客户渠道id
     */
    private String channel;
    /**
     * 获客方式id
     */
    private String source;
    /**
     * 客户等级id
     */
    private String level;
    /**
     * 信誉等级
     */
    private String reputation;
    /**
     * 成立时间
     */
    private String establishedTime;

    /**
     * 是否锁定
     */
    private Integer locking;

}
