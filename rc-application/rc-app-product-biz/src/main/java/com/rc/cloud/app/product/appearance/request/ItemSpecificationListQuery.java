package com.rc.cloud.app.product.appearance.request;

import com.rc.cloud.app.product.application.data.BaseQuery;
import lombok.Data;

import java.util.List;

@Data
public class ItemSpecificationListQuery extends BaseQuery {

    private List<Integer> itemIds;
}
