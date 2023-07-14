package com.rc.cloud.app.system.api.tenant;

import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/13
 * @description 多租户的 API 接口
 */
public interface TenantApi {

    /**
     * 获得所有租户
     *
     * @return 租户编号数组
     */
    List<String> getTenantIdList();

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validateTenant(String id);

}
