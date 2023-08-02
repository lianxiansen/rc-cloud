package com.rc.cloud.app.marketing.domain.entity.order;

import lombok.Data;

/**
 * @ClassName Buyer
 * @Author liandy
 * @Date 2023/7/28 17:16
 * @Description TODO
 * @Version 1.0
 */
@Data
public class Buyer {
    /**
     * 下单人ID
     */
    private String buyerId;
    /**
     * 买家名称
     */
    private String buyername;
    /**
     * 下单人
     */
    private String buyerOrder;


    /**
     * 会员账号
     */
    private String buyerAccount;

    public Buyer(String buyerId,String buyername, String buyerOrder, String buyerAccount) {
        this.buyerId = buyerId;
        this.buyername = buyername;
        this.buyerOrder = buyerOrder;
        this.buyerAccount = buyerAccount;
    }
}
