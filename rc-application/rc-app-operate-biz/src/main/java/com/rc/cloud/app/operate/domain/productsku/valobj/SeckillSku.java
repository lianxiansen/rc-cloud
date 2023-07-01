package com.rc.cloud.app.operate.domain.productsku.valobj;

import com.rc.cloud.app.operate.domain.productsku.ProductSkuEntity;
import com.rc.cloud.app.operate.domain.productsku.valobj.Inventory;
import com.rc.cloud.app.operate.domain.productsku.valobj.LimitBuy;
import com.rc.cloud.app.operate.domain.productsku.valobj.Price;
import com.rc.cloud.app.operate.domain.productsku.valobj.TotalInventory;

import java.text.DecimalFormat;

public class SeckillSku {


    private ProductSkuEntity context;

    public  SeckillSku(ProductSkuEntity context){
        this.context=context;
    }

    private LimitBuy seckillLimitBuy;

    private Price seckillPrice;

    private Inventory seckillInventory;

    private TotalInventory seckillTotalInventory;

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

}
