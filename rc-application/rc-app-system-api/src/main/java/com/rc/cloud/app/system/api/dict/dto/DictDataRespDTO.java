package com.rc.cloud.app.system.api.dict.dto;

import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description: 字典数据 Response DTO
 */
@Data
public class DictDataRespDTO {

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态
     */
    private Integer status;
}
