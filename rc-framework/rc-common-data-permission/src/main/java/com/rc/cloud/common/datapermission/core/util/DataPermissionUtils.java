package com.rc.cloud.common.datapermission.core.util;

import com.rc.cloud.common.datapermission.core.annotation.DataPermission;
import com.rc.cloud.common.datapermission.core.aop.DataPermissionContextHolder;
import lombok.SneakyThrows;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 数据权限 Util
 */
public class DataPermissionUtils {

    private static DataPermission dataPermissionDisable;

    @DataPermission(enable = false)
    @SneakyThrows
    private static DataPermission getDisableDataPermissionDisable() {
        if (dataPermissionDisable == null) {
            dataPermissionDisable = DataPermissionUtils.class
                    .getDeclaredMethod("getDisableDataPermissionDisable")
                    .getAnnotation(DataPermission.class);
        }
        return dataPermissionDisable;
    }

    /**
     * 忽略数据权限，执行对应的逻辑
     *
     * @param runnable 逻辑
     */
    public static void executeIgnore(Runnable runnable) {
        DataPermission dataPermission = getDisableDataPermissionDisable();
        DataPermissionContextHolder.add(dataPermission);
        try {
            // 执行 runnable
            runnable.run();
        } finally {
            DataPermissionContextHolder.remove();
        }
    }

}
