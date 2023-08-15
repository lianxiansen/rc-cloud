package com.rc.cloud.app.marketing.domain.entity.order.valobj;

import java.util.Objects;

/**
 * @ClassName Buyer
 * @Author liandy
 * @Date 2023/7/28 17:16
 * @Description 买家
 * @Version 1.0
 */
public class Buyer {
    /**
     * 下单人ID
     */
    private String buyerId;
    /**
     * 买家名称
     */
    private String buyerName;
    /**
     * 下单人
     */
    private String buyerOrder;


    /**
     * 会员账号
     */
    private String buyerAccount;

    public Buyer(String buyerId, String buyername, String buyerOrder, String buyerAccount) {
        this.buyerId = buyerId;
        this.buyerName = buyername;
        this.buyerOrder = buyerOrder;
        this.buyerAccount = buyerAccount;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerOrder() {
        return buyerOrder;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Buyer buyer = (Buyer) o;
        return getBuyerId().equals(buyer.getBuyerId()) && getBuyerName().equals(buyer.getBuyerName()) && getBuyerOrder().equals(buyer.getBuyerOrder()) && getBuyerAccount().equals(buyer.getBuyerAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuyerId(), getBuyerName(), getBuyerOrder(), getBuyerAccount());
    }

    public static Buyer mockBuyer() {
        Buyer buyer = new Buyer("123", "陈激扬", "去11", "18258687039");
        return buyer;
    }
}
