package com.rc.cloud.app.marketing.domain;

import com.rc.cloud.api.product.service.ProductApplicationService;
import com.rc.cloud.app.marketing.application.bo.CartBO;
import com.rc.cloud.app.marketing.application.dto.CartDTO;
import com.rc.cloud.app.marketing.application.service.impl.CartApplicationServiceImpl;
import com.rc.cloud.app.marketing.domain.entity.cart.*;
import com.rc.cloud.app.marketing.domain.entity.cart.identifier.ProductId;
import com.rc.cloud.app.marketing.domain.entity.price.PriceService;
import com.rc.cloud.app.marketing.infrastructure.repository.CartRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.CartMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.CartPO;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rc.cloud.common.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author WJF
 * @create 2023-07-28 14:54
 * @description TODO
 */
@Import({CartApplicationServiceImpl.class,
        CartService.class,
        CartRepositoryImpl.class,
        CartFactory.class
})
class CartApplicationServiceImplTest extends BaseDbUnitTest {

    @MockBean
    private ProductApplicationService productApplicationService;

    @MockBean
    private CartProductRepository cartProductRepository;

    @MockBean
    private PriceService priceService;

    @Resource
    private CartApplicationServiceImpl cartApplicationServiceImpl;

    @Resource
    private CartService cartService;

    @Resource
    private CartMapper cartMapper;

    @BeforeEach
    void insert() {
    }

    @Test
    void getCartListByShopIds() {
    }

    @Test
    void calPrice() {
    }

    @Test
    void getCartList() {
        String user = "admin";
        CartPO po = randomPojo(CartPO.class, o -> {
            o.setUserId("admin");
            o.setSkuAttributes("绿色,40H");
            o.setNum(10);
            o.setProductId("2");
            o.setProductUniqueid("200");
            o.setPayed(0);
            o.setNewState(0);
        });
        cartMapper.insert(po);
        po = randomPojo(CartPO.class, o -> {
            o.setUserId("admin");
            o.setSkuAttributes("白色,40H");
            o.setNum(10);
            o.setProductId("2");
            o.setProductUniqueid("300");
            o.setPayed(0);
            o.setNewState(0);
        });
        cartMapper.insert(po);
        //模拟服务返回
        mockProductService();

        List<CartBO> cartList = cartApplicationServiceImpl.getCartList(user, Arrays.asList("200", "300"));
        assertEquals(cartList.size(), 2);
        assertEquals(cartList.get(0).getState(), ExpireState.NOT_EXPIRE.getCode());
        assertEquals(cartList.get(1).getState(), ExpireState.PRODUCT_EXPIRE.getCode());
    }

    @Test
    void saveCart() {
        String user = "admin";
        List<CartDTO> cartDTOList = new ArrayList<>();
        //模拟请求
        CartDTO dto = randomPojo(CartDTO.class, o -> {
            o.setProductId("1");
            o.setProductUniqueid("100");
            o.setNum(5);
            o.setShopId("1");
        });
        cartDTOList.add(dto);
        dto = randomPojo(CartDTO.class, o -> {
            o.setProductId("2");
            o.setProductUniqueid("200");
            o.setNum(10);
            o.setShopId("1");
        });
        cartDTOList.add(dto);

        //模拟服务返回
        mockProductService();
        //调用保存
        cartApplicationServiceImpl.saveCart(user, cartDTOList);

        CartPO cartPO = cartMapper.selectOne(new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getProductId, "1")
                .eq(CartPO::getProductUniqueid, "100")
                .eq(CartPO::getShopId, "1")
        );
        assertNull(cartPO);
        cartPO = cartMapper.selectOne(new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getProductId, "2")
                .eq(CartPO::getProductUniqueid, "200")
                .eq(CartPO::getShopId, "1")
        );
        assertNotNull(cartPO);
        assertEquals(cartPO.getNum(), 10);


        cartDTOList = new ArrayList<>();
        dto = randomPojo(CartDTO.class, o -> {
            o.setProductId("2");
            o.setProductUniqueid("200");
            o.setNum(15);
            o.setShopId("1");
        });
        cartDTOList.add(dto);
        //调用保存
        cartApplicationServiceImpl.saveCart(user, cartDTOList);

        cartPO = cartMapper.selectOne(new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getProductId, "2")
                .eq(CartPO::getProductUniqueid, "200")
                .eq(CartPO::getShopId, "1")
        );
        assertNotNull(cartPO);
        assertEquals(cartPO.getNum(), 15);
    }

    @Test
    void deleteCartByProductuniqueid() {
    }

    void mockProductService() {
        List<CartProductSkuInfo> list = new ArrayList<>();
        CartProductSkuInfo cartProductInfo = new CartProductSkuInfo();

        cartProductInfo = randomPojo(cartProductInfo.getClass(), o -> {
            o.setProductName("皮带");
            o.setProductId("2");
            o.setSkuId("100");
            o.setAttributes(Arrays.asList("40H", "白色"));
        });

        list.add(cartProductInfo);
        cartProductInfo = randomPojo(cartProductInfo.getClass(), o -> {
            o.setProductName("皮带");
            o.setProductId("2");
            o.setSkuId("100");
            o.setAttributes(Arrays.asList("40H", "白色"));
        });

        list.add(cartProductInfo);

        //模拟调用
        when(cartProductRepository.getProductList(new ArrayList<>())).thenReturn(list);

    }
}
