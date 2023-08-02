package com.rc.cloud.app.marketing.domain.service.impl;

import com.rc.cloud.app.marketing.domain.entity.cart.Cart;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrder;
import com.rc.cloud.app.marketing.domain.entity.comfirmorder.ComfirmOrderItem;
import com.rc.cloud.app.marketing.domain.entity.common.Product;
import com.rc.cloud.app.marketing.domain.entity.common.ProductItem;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.Area;
import com.rc.cloud.app.marketing.domain.entity.deliveryaddress.DeliveryAddress;
import com.rc.cloud.app.marketing.domain.service.ComfirmOrderDomainService;
import com.rc.cloud.common.core.domain.IdRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ComfirmOrderDomainService
 * @Author liandy
 * @Date 2023/7/28 13:45
 * @Description TODO
 * @Version 1.0
 */
@Service
public class ComfirmOrderDomainServiceImpl implements ComfirmOrderDomainService {
    @Resource
    private IdRepository idRepository;
    @Override
    public ComfirmOrder placeOrder(List<Cart> carts) {
        ComfirmOrder comfirmOrder = new ComfirmOrder(idRepository.nextId());
        Area area = new Area("浙江省", "台州市", "黄岩区", "");
        DeliveryAddress deliveryAddress = new DeliveryAddress("445be69b-11df-4cf8-80a3-2b7beb5", "test", "13800001234", "10068", area);
        comfirmOrder.setDeliveryAddressId(deliveryAddress.getId());

        //确认订单-商品信息
        carts.forEach(cartItem -> {
            String productId = "7747a149-5a79-4e37-8e42-061e434";
            String productName = "单杯调味罐";
            String productImage = "http://www.zjffcat.com/storage/uploads/20230720/3fc7b3d2f37c7d9e4dc39ac74c38080b.png";
            String productArticleNo = "56226";
            Product product = new Product(productId,productName,productImage,productArticleNo);
            ProductItem productItem = new ProductItem().setProductId(productId).setAttribute("48个/箱").setSkuId("").setNum(2).setSpuId("").setImage("").setPrice(new BigDecimal(7.80));
            ComfirmOrderItem item = new ComfirmOrderItem(idRepository.nextId(), comfirmOrder.getId(), cartItem.getId(),product, productItem);
            comfirmOrder.addItem(item);
        });
        return comfirmOrder;
    }





}
