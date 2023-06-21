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
@KeySequence("distributor_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
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
    @TableId
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
     * 创建时间
     */
    private String createtime;
    /**
     * 数据添加人员
     */
    private String by;
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
    private Integer customers;
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
    private String chengli;
    /**
     * 展厅地址
     */
    private String ztAddress;
    /**
     * 展厅面积
     */
    private String ztMianji;
    /**
     * 展厅图片
     */
    private String ztImage;
    /**
     * 门店地址
     */
    private String mdAddress;
    /**
     * 门店面积
     */
    private String mdMianji;
    /**
     * 门店图片
     */
    private String mdImage;
    /**
     * 仓库地址
     */
    private String ckAddress;
    /**
     * 仓库面积
     */
    private String ckMianji;
    /**
     * 仓库图片
     */
    private String ckImage;
    /**
     * 是否回收站, 1否, 2是
     */
    private Integer isdelete;
    /**
     * 是否锁定
     */
    private Integer locking;

}
