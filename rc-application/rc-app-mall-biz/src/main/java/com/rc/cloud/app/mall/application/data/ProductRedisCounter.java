package com.rc.cloud.app.mall.application.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductRedisCounter {

    public int ProductId;
    public int CostHigherCount;
    public Timestamp LastTime;
    public int AskForReplenish;
    public Timestamp AskTime;
}
