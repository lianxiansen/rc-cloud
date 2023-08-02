package com.rc.cloud.app.marketing.domain;

import com.rc.cloud.api.product.bo.AttributeValueCombinationBO;
import com.rc.cloud.api.product.bo.ProductBO;
import com.rc.cloud.api.product.bo.ProductSkuBO;
import com.rc.cloud.api.product.dto.ProductListQueryDTO;
import com.rc.cloud.api.product.service.ProductApplicationService;
import com.rc.cloud.app.marketing.application.bo.CartListBO;
import com.rc.cloud.app.marketing.application.dto.CartDTO;
import com.rc.cloud.app.marketing.application.service.impl.CartApplicationServiceImpl;
import com.rc.cloud.app.marketing.domain.entity.cart.CartService;
import com.rc.cloud.app.marketing.domain.entity.price.PriceService;
import com.rc.cloud.app.marketing.infrastructure.repository.CartRepositoryImpl;
import com.rc.cloud.app.marketing.infrastructure.repository.mapper.CartMapper;
import com.rc.cloud.app.marketing.infrastructure.repository.po.CartPO;
import com.rc.cloud.common.core.pojo.PageResult;
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
        CartRepositoryImpl.class})
class CartApplicationServiceImplTest extends BaseDbUnitTest {

    @MockBean
    private ProductApplicationService productApplicationService;

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
        CartPO po = randomPojo(CartPO.class, o -> {
            o.setUserid("admin");
            o.setSkuAttributes("绿色,40H");
            o.setNum(10);
            o.setProductid("2");
            o.setProductuniqueid("200");
            o.setPayed(0);
            o.setNewstate(0);
        });
        cartMapper.insert(po);
        po = randomPojo(CartPO.class, o -> {
            o.setUserid("admin");
            o.setSkuAttributes("白色,40H");
            o.setNum(10);
            o.setProductid("2");
            o.setProductuniqueid("300");
            o.setPayed(0);
            o.setNewstate(0);
        });
        cartMapper.insert(po);
        //模拟服务返回
        mockProductService();

        CartListBO cartList = cartApplicationServiceImpl.getCartList(Arrays.asList("200", "300"));
        assertEquals(cartList.getCartList().size(), 2);
        assertEquals(cartList.getCartList().get(0).getState(), 1);
        assertEquals(cartList.getCartList().get(1).getState(), 0);
    }

    @Test
    void saveCart() {
        List<CartDTO> cartDTOList = new ArrayList<>();
        //模拟请求
        CartDTO dto = randomPojo(CartDTO.class, o -> {
            o.setProductid("1");
            o.setProductuniqueid("100");
            o.setNum(5);
            o.setShopid("1");
        });
        cartDTOList.add(dto);
        dto = randomPojo(CartDTO.class, o -> {
            o.setProductid("2");
            o.setProductuniqueid("200");
            o.setNum(10);
            o.setShopid("1");
        });
        cartDTOList.add(dto);

        //模拟服务返回
        mockProductService();
        //调用保存
        cartApplicationServiceImpl.saveCart(cartDTOList);

        CartPO cartPO = cartMapper.selectOne(new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getProductid, "1")
                .eq(CartPO::getProductuniqueid, "100")
                .eq(CartPO::getShopid, "1")
        );
        assertNull(cartPO);
        cartPO = cartMapper.selectOne(new LambdaQueryWrapperX<CartPO>()
                .eq(CartPO::getProductid, "2")
                .eq(CartPO::getProductuniqueid, "200")
                .eq(CartPO::getShopid, "1")
        );
        assertNotNull(cartPO);
        assertEquals(cartPO.getNum(), 10);
    }

    @Test
    void deleteCartByProductuniqueid() {
    }

    void mockProductService() {
        ProductListQueryDTO productListQueryDTO = new ProductListQueryDTO();
        PageResult<ProductBO> productBOPageResult = new PageResult<>();
        List<ProductSkuBO> skuBOList = new ArrayList<>();
        ProductSkuBO skuBO = randomPojo(ProductSkuBO.class, o -> {
            o.setSkuCode("100");
            o.setSkuAttributes(Arrays.asList(
                    new AttributeValueCombinationBO().setAttribute("大小").setAttributeValue("40H"),
                    new AttributeValueCombinationBO().setAttribute("颜色").setAttributeValue("白色")
            ));
        });
        skuBOList.add(skuBO);
        skuBO = randomPojo(ProductSkuBO.class, o -> {
            o.setSkuCode("200");
            o.setSkuAttributes(Arrays.asList(
                    new AttributeValueCombinationBO().setAttribute("大小").setAttributeValue("40H"),
                    new AttributeValueCombinationBO().setAttribute("颜色").setAttributeValue("绿色")
            ));
        });
        skuBOList.add(skuBO);
        ProductBO productBO = randomPojo(ProductBO.class, o -> {
            o.setId("2");
            o.setSkus(skuBOList);
        });
        productBOPageResult.setList(Arrays.asList(productBO));
        //模拟调用
        when(productApplicationService.getProductList(productListQueryDTO)).thenReturn(productBOPageResult);
    }
}