package com.rc.cloud.app.system.api.permission.dto;

import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-14 9:39
 * @description TODO
 */
@Data
public class PermissionGetReqDTO {

    String userId;

    List<String> permissions;
}
