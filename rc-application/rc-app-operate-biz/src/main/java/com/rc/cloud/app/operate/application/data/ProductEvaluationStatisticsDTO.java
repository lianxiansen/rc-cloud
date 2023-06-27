package com.rc.cloud.app.operate.application.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author:chenjianxiang
 * @Date 2021/3/15
 * @Description:
 */
@Data
@EqualsAndHashCode
public class ProductEvaluationStatisticsDTO {

    private int favourable_comment_amount;

    private int neutral_comment_amount;

    private int negative_comment_amount;

    private String favourable_comment_rate;

    private Boolean is_show_favourable_comment_rate;

    /**
     * 好评率
     * @return
     */
    public String getFavourable_comment_rate() {
        BigDecimal fav= new BigDecimal(favourable_comment_amount);
        BigDecimal all =new BigDecimal(favourable_comment_amount+ neutral_comment_amount+ negative_comment_amount);
        if(all.compareTo(BigDecimal.ZERO)==0){
            favourable_comment_rate=null;
        }else{
           String rate= fav.divide(all).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_UP).toString();
           favourable_comment_rate=rate;
        }
        return favourable_comment_rate;
    }

    public void setFavourable_comment_rate(String favourable_comment_rate) {
        this.favourable_comment_rate = favourable_comment_rate;
    }

    /**
     * 是否显示好评率
     * @return
     */
    public Boolean getIs_show_favourable_comment_rate() {
        if(favourable_comment_amount+ neutral_comment_amount+ negative_comment_amount ==0){
           return false;
        }else{
            return true;
        }
    }

    public void setIs_show_favourable_comment_rate(Boolean is_show_favourable_comment_rate) {
    }
}
