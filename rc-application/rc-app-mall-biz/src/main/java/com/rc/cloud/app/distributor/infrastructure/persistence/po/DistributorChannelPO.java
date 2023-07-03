package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 经销商渠道 DO
 *
 * @author wjf
 */
@TableName("distributor_channel")
@KeySequence("distributor_channel_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorChannelPO extends BaseDO{

    /**
     * id
     */
    @TableId(type=IdType.ASSIGN_ID)
    private Long id;
    /**
     * 渠道名称
     */
    private String name;
    /**
     * 说明
     */
    private String description;
}
