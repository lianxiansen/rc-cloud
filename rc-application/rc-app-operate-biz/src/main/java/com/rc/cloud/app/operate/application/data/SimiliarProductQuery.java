package com.rc.cloud.app.operate.application.data;

import lombok.Data;

import java.util.List;

@Data
public class SimiliarProductQuery extends BaseQuery {

    private List<Integer> ids;

    public SimiliarProductQuery() {}

    public SimiliarProductQuery(List<Integer> _ids) { ids = _ids; }
}
