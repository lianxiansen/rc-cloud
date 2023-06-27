package com.rc.cloud.app.operate.appearance.request;

import com.rc.cloud.app.operate.application.data.BaseQuery;
import lombok.Data;

import java.util.List;

@Data
public class ItemSpecificationListQuery extends BaseQuery {

    private List<Integer> itemIds;
}
