/**
 * @author oliveoil
 * date 2023-04-21 16:10
 */
package com.rc.cloud.app.system.enums.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum TokenTypeEnum {
/**
     * 访问令牌
     */
    ACCESS_TOKEN("access", "访问令牌"),
    /**
     * 刷新令牌
     */
    REFRESH_TOKEN("refresh", "刷新令牌");

    private final String value;
    private final String name;

    public static String getNameByValue(String value) {
        for (TokenTypeEnum s : TokenTypeEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s.getName();
            }
        }
        return "";
    }

    public static String getValueByName(String name) {
        for (TokenTypeEnum s : TokenTypeEnum.values()) {
            if (Objects.equals(s.getName(), name)) {
                return s.getValue();
            }
        }
        return null;
    }
}
