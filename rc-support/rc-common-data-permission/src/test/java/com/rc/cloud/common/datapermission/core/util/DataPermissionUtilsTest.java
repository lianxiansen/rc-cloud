package com.rc.cloud.common.datapermission.core.util;

import com.rc.cloud.common.datapermission.core.aop.DataPermissionContextHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataPermissionUtilsTest {

    @Test
    public void testExecuteIgnore() {
        DataPermissionUtils.executeIgnore(() -> Assertions.assertFalse(DataPermissionContextHolder.get().enable()));
    }

}
