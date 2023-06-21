package com.rc.cloud.app.mall.infrastructure.util;

import cn.hutool.core.net.NetUtil;
import com.qxun.qlive.common.config.WhiteListProp;

/**
 * @Author:chenjianxiang
 * @Date 2021/6/30
 * @Description:
 */
public class IpUtil {

     public static boolean requestIpIsNotInWhitelist(){
         String ip= NetUtil.getLocalhostStr();
         if (!WhiteListProp.getWhiteListAdmin().contains(ip)) {
            return true;
         }
         return  false;
     }
}
