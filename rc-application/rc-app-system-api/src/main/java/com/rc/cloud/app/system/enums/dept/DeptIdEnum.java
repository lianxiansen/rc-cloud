package com.rc.cloud.app.system.enums.dept;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 部门编号枚举
 */
@Getter
@AllArgsConstructor
public enum DeptIdEnum {

    /**
     * 根节点
     */
    ROOT("0");

    /**
     * 部门编号
     */
    private final String id;
}
