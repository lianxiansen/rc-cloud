package com.rc.cloud.app.operate.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther Ushop
 * @date 2021/4/20 10:21
 * @Description Whitelist
 * @PROJECT_NAME qyy-live
 */
@Component
public class WhiteListProp {
    private static String whiteListAdmin;

//    @Value("${whiteList.admin}")
    public void setWhiteListAdmin(String whiteListAdmin) {
        this.whiteListAdmin = whiteListAdmin;
    }

    public static String getWhiteListAdmin() {
        return whiteListAdmin;
    }
}
