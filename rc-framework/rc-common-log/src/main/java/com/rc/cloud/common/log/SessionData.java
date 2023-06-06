package com.rc.cloud.common.log;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
@Data
@Accessors(chain = true)
public class SessionData {
    // sessionId
    private String sessionId;
    // 开始行
    private int startLine;
    // 是否为新连接
    private boolean isNew;
}
