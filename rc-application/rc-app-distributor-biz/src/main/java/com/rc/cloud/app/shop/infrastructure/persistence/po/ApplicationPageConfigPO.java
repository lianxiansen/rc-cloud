package com.rc.cloud.app.shop.infrastructure.persistence.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author WJF
 * @create 2023-07-21 13:45
 * @description 应用页面配置
 */
@Data
@TableName("shop_pageconfig")
public class ApplicationPageConfigPO extends BaseDO {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 页面标题
     */
    private String title;
    /**
     * 是否默认
     */
    private Integer defaulted;

    /**
     * 类型 1-系统画布 2-自定义页面 3-商家店铺装修
     */
    private Integer type;

    /**
     * 店铺Id
     */
    private String shopid;

    /**
     * 终端 1-小程序 2-H5 3-APP 4-PC
     */
    @NotNull
    private Integer terminal;

    /**
     * 模板
     */
    private String template;
}
