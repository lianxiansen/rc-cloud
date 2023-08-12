package com.rc.cloud.app.system.api.permission.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 部门的数据权限 Response DTO
 */
@Data
@Accessors(chain = true)
public class DeptDataPermissionRespDTO {

    /**
     * 是否可查看全部数据
     */
    private Boolean all;

    /**
     * 是否可查看自己的数据
     */
    private Boolean self;

    /**
     * 可查看的部门编号数组
     */
    private Set<String> deptIds;

    /**
     * 构造函数
     */
    public DeptDataPermissionRespDTO() {
        this.all = false;
        this.self = false;
        this.deptIds = new HashSet<>();
    }
}
