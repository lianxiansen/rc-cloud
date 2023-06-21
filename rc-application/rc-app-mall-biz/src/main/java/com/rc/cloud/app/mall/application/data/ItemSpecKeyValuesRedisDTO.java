package com.rc.cloud.app.mall.application.data;

import lombok.Data;

import java.util.List;

@Data
public class ItemSpecKeyValuesRedisDTO {

    private String specName;

    private Integer sortId;

    private Integer specId;

    private List<ItemSpecValueDTO> values;
}
