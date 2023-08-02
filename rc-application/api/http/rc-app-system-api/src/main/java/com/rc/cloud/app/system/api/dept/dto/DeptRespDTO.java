package com.rc.cloud.app.system.api.dept.dto;

import lombok.Data;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 部门 Response DTO
 */
@Data
public class DeptRespDTO {

    /**
     * 部门编号
     */
    private String id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门编号
     */
    private String parentId;

    /**
     * 负责人的用户编号
     */
    private String leaderUserId;

    /**
     * 部门状态
     */
    private Integer status;
}
