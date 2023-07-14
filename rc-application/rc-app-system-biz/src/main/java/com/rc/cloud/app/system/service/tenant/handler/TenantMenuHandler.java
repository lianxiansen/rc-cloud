package com.rc.cloud.app.system.service.tenant.handler;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 租户菜单处理
 * 目的：尽量减少租户逻辑耦合到系统中
 */
public interface TenantMenuHandler {

    /**
     * 基于传入的租户菜单【全】列表，进行相关逻辑的执行
     * 例如说，返回可分配菜单的时候，可以移除多余的
     *
     * @param menuIds 菜单列表
     */
    void handle(Set<String> menuIds);

}
