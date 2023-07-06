package com.rc.cloud.baseconfig.amap;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author WJF
 * @create 2023-07-06 9:55
 * @description TODO
 */
@Data
public class DistrictDTO {

        //包含建议关键字列表和建议城市列表
        private HashMap<String,Object> suggestion;

        //数量
        private Integer count;

        //状态码
        private String infocode;

        //返回结果状态值
        private String status;

        //返回状态说明
        private String info;

        //行政区列表
        private List<DistrictEntity> districts;
}
