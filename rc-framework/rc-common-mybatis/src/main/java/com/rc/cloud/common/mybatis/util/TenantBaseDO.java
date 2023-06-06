/*
 * @Descripttion: your project
 * @Author: chenjianxiang
 * @Date: 2022-04-21 14:43:03
 */
package com.rc.cloud.common.mybatis.util;

import lombok.Data;

@Data
public class TenantBaseDO  extends BaseDO{

     /**
     * 租户ID
     */
    private String tenantId;
    
}
