package com.rc.cloud.app.system.api.user.dto;

import com.rc.cloud.common.core.enums.CommonStatusEnum;
import lombok.Data;

import java.util.Set;

/**
 * Admin 用户 Response DTO
 *
 * @author 芋道源码
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
     *
     * 枚举 {@link CommonStatusEnum}
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
