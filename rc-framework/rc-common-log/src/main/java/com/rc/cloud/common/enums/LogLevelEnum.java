package com.rc.cloud.common.enums;

/**
 * @author WJF
 * @create 2022-08-06 14:27
 * @description TODO
 */
public enum LogLevelEnum {
    ALL, DEBUG, INFO, WARN, ERROR;

    public static LogLevelEnum get(String level) {
        for (LogLevelEnum value : LogLevelEnum.values()) {
            if (value.name().equals(level)) {
                return value;
            }
        }
        return ALL;
    }
}
