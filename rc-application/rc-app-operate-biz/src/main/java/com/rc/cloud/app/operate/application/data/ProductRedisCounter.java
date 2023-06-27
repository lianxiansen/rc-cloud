package com.rc.cloud.app.operate.application.data;

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
