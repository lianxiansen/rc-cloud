package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 经销商来源 DO
 *
 * @author wjf
 */
@TableName("distributor_source")
@KeySequence("distributor_source_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorSourcePO extends BaseDO {

    /**
     * id
     */
    @TableId(type=IdType.ASSIGN_ID)
    private String id;
    /**
     * 获客方式
     */
    private String name;
    /**
     * 说明
     */
    private String description;
}
