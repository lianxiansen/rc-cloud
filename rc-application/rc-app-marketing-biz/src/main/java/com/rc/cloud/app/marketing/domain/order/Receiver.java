package com.rc.cloud.app.marketing.domain.order;

import lombok.Data;

/**
 * @ClassName Receiver
 * @Author liandy
 * @Date 2023/7/28 17:18
 * @Description 收货信息
 * @Version 1.0
 */
@Data
public class Receiver {
    private String receiverContact;
    private String receiverAddress;
    private String receiverMobile;

    public Receiver(String receiverContact, String receiverAddress, String receiverMobile) {
        this.receiverContact = receiverContact;
        this.receiverAddress = receiverAddress;
        this.receiverMobile = receiverMobile;
    }
}
