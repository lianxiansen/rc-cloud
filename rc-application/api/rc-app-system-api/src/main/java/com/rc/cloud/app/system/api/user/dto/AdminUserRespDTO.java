package com.rc.cloud.app.system.api.user.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description Admin 用户 Response DTO
 */
@Data
public class AdminUserRespDTO {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 帐号状态
     * 枚举 CommonStatusEnum
     */
    private Integer status;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 岗位编号数组
     */
    private Set<String> postIds;

    /**
     * 手机号码
     */
    private String mobile;
}
