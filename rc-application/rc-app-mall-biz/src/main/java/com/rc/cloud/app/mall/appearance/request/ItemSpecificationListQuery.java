package com.rc.cloud.app.mall.appearance.request;

import com.rc.cloud.app.mall.application.data.BaseQuery;
import lombok.Data;

import java.util.List;

@Data
public class ItemSpecificationListQuery extends BaseQuery {

    private List<Integer> itemIds;
}
