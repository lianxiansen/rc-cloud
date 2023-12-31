package com.rc.cloud.app.distributor.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author WJF
 * @create 2023-06-26 14:45
 * @description TODO
 */
@TableName("distributor_detail")
@KeySequence("distributor_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributorDetailPO implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String distributorId;

    private String distributorDetail;
}
