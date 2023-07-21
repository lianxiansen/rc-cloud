package com.rc.cloud.app.shop.application.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author WJF
 * @create 2023-07-21 14:57
 * @description TODO
 */
@Data
public class ApplicationPageConfigCreateDTO {

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 页面标题
     */
    private String title;

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

}
