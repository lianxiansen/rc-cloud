package com.rc.cloud.baseconfig.service;

/**
 * @author WJF
 * @create 2023-06-26 11:03
 * @description TODO
 */

public interface DistrictService {

    Object getAllProvince();

    Object getByParentCode(String adcode);
}
