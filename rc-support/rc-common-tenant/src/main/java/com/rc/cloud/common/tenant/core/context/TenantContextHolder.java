package com.rc.cloud.common.tenant.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.rc.cloud.app.system.enums.ErrorCodeConstants;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 多租户上下文 Holder
 *
 * @author 芋道源码
 */
public class TenantContextHolder {

    /**
     * 当前租户编号
     */
    private static final ThreadLocal<String> TENANT_ID = new TransmittableThreadLocal<>();

    /**
     * 是否忽略租户
     */
    private static final ThreadLocal<Boolean> IGNORE = new TransmittableThreadLocal<>();

    /**
     * 获得租户编号。
     *
     * @return 租户编号
     */
    public static String getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 获得租户编号。如果不存在，则抛出 ServiceException 异常
     *
     * @return 租户编号
     */
    public static String getRequiredTenantId() {
        String tenantId = getTenantId();
        if (tenantId == null) {
            throw exception(ErrorCodeConstants.TENANT_ID_INVALID);
        }
        return tenantId;
    }

    public static void setTenantId(String tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static void setIgnore(Boolean ignore) {
        IGNORE.set(ignore);
    }

    /**
     * 当前是否忽略租户
     *
     * @return 是否忽略
     */
    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE.get());
    }

    public static void clear() {
        TENANT_ID.remove();
        IGNORE.remove();
    }

}
