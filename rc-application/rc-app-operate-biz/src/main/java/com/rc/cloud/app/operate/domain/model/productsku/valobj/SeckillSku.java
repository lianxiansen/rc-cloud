package com.rc.cloud.app.operate.domain.model.productsku.valobj;

import com.rc.cloud.common.core.domain.AbstractId;
import com.rc.cloud.common.core.domain.Entity;
import com.rc.cloud.common.core.domain.ValueObject;

import java.text.DecimalFormat;

/**
 * @Author: chenjianxiang
 * @Date: 2023/8/5
 * @Description:
 */
public class SeckillSku extends Entity {

    private LimitBuy seckillLimitBuy;

    private SeckillPrice seckillPrice;

    private Inventory seckillInventory;

    private TotalInventory seckillTotalInventory;


    public LimitBuy getSeckillLimitBuy() {
        return seckillLimitBuy;
    }

    public void setSeckillLimitBuy(LimitBuy seckillLimitBuy) {
        this.seckillLimitBuy = seckillLimitBuy;
    }

    public SeckillPrice getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(SeckillPrice seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Inventory getSeckillInventory() {
        return seckillInventory;
    }

    public void setSeckillInventory(Inventory seckillInventory) {
        this.seckillInventory = seckillInventory;
    }

    public TotalInventory getSeckillTotalInventory() {
        return seckillTotalInventory;
    }

    public void setSeckillTotalInventory(TotalInventory seckillTotalInventory) {
        this.seckillTotalInventory = seckillTotalInventory;
    }

    public String getSeckillBuyRate(){
        if(seckillInventory.getValue()>0 && seckillTotalInventory.getValue()>0){
            String rate = percent(seckillInventory.getValue(), seckillTotalInventory.getValue());
            return rate;
        }else{
            return "0";
        }
    }

    //计算百分比
    public String percent(int x, int y) {
        String percent = "";
        double xx = x * 100.0;
        double yy = y * 100.0;
        double zz = xx / yy;
        DecimalFormat df = new DecimalFormat("##.00");
        if(Math.abs(zz)<0.000000000001){
            percent = "0.00";
        } else {
            percent = df.format(zz);
        }
        return percent;
    }


    @Override
    public AbstractId getId() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && this.getClass() == other.getClass()) {
            SeckillSku typedObject = (SeckillSku) other;
        }
        return false;
    }
}
