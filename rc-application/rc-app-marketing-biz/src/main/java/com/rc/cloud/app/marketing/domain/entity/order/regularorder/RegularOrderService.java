package com.rc.cloud.app.marketing.domain.entity.order.regularorder;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @ClassName OrderService
 * @Author liandy
 * @Date 2023/7/29 08:54
 * @Description 订单聚合服务
 * @Version 1.0
 */
@Service
public class RegularOrderService {
    @Resource
    private RegularOrderRepository regularOrderRepository;


    public List<RegularOrder> findOrdersByTradeNo(String tradeNo) {
        return null;
    }
    /**
     * 生成订单编号
     * 订单编号规则：(10位)：(年末尾*月，取后2位)+（日取整后2位）+(timestamp*10000以内随机数，取后6位)
     * @return
     */
    public String generateOrderSn(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        year = year % 10;
        if(year == 0) year = 10;
        int month = calendar.get(Calendar.MONTH)+1;
        int yearMonth  =  year * month;
        String yearMonthPart = "0"+yearMonth;
        yearMonthPart = yearMonthPart.substring(yearMonthPart.length() - 2 );
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String timestampPart = ""+(Math.random() * 10000) * (System.currentTimeMillis()/10000);
        timestampPart = timestampPart.replace(".", "").replace("E", "");
        timestampPart = timestampPart.substring(0,6);
        return yearMonthPart+day+timestampPart;
    }

    public void insertBatch(List<RegularOrder> orders) {
        regularOrderRepository.saveBatch(orders);
    }
}
