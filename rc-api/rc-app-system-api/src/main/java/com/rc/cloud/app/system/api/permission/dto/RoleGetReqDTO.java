package com.rc.cloud.app.system.api.permission.dto;

import lombok.Data;

import java.util.List;

/**
 * @author WJF
 * @create 2023-08-14 9:48
 * @description TODO
 */
@Data
public class RoleGetReqDTO {

    String userId;

    List<String> roles;
}
