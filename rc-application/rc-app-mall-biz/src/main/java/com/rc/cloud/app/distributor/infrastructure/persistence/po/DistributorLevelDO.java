package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;


/**
 * 经销商客户等级 DO
 *
 * @author wjf
 */
@TableName("distributor_level")
@KeySequence("distributor_level_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorLevelDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 客户等级
     */
    private String name;
    /**
     * 说明
     */
    private String explain;
    /**
     * 创建时间
     */
    private String createtime;

}
