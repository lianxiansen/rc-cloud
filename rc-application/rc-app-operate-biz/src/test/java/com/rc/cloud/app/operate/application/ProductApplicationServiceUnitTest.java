package com.rc.cloud.app.operate.application;

import cn.hutool.json.JSONUtil;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Name;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailDomainService;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailRepository;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictDomainService;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroup;
import com.rc.cloud.app.operate.domain.model.productgroup.identifier.ProductGroupId;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuDomainService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.*;
import com.rc.cloud.app.operate.infrastructure.repository.remote.TenantServiceImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.util.TenantContext;
import com.rc.cloud.common.core.util.object.ObjectUtils;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

/**
 * @ClassName: ProductApplicationServiceUnitTest
 * @Author: liandy
 * @Date: 2023/7/7 11:01
 * 1.创建商品
 */

@Import({  LocalIdRepositoryImpl.class, ProductApplicationService.class,ProductDomainService.class
        , ProductSkuDomainService.class,
        ProductDictDomainService.class, ProductDetailDomainService.class
        , ProductRepositoryImpl.class
        , ProductSkuRepositoryImpl.class
        , TenantServiceImpl.class
        , ProductDictRepositoryImpl.class
        , ProductDetailRepositoryImpl.class})
public class ProductApplicationServiceUnitTest extends BaseDbUnitTest {

    @Autowired
    ProductApplicationService productApplicationService;



    @Autowired
    private IdRepository idRepository;


    @BeforeEach
    public void beforeEach() {
        initStub();
        initFixture();
    }


    @Test
    @DisplayName("创建商品")
    public void createProduct() {

        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        ProductBO productBO = productApplicationService.createProduct(createProductSaveDTO());
        int random = RandomUtils.randomInteger();
        Assertions.assertTrue(ObjectUtils.isNotNull(
                productBO.getId()) , "创建失败");
        Assertions.assertTrue(ObjectUtils.isNotNull(
                productBO.getDetail()!=null) , "创建失败");
        Assertions.assertTrue(ObjectUtils.isNotNull(
                productBO.getDicts().size()==productSaveDTO.getDicts().size()) , "创建失败");
        Assertions.assertTrue(ObjectUtils.isNotNull(
                productBO.getSkus().size()==productSaveDTO.getSkus().size()) , "创建失败");

    }

    @Test
    @DisplayName("创建商品-相册为空")
    public void createProductWhenAlbumsIsNull() {
        //Product product = new Product(new ProductId(idRepository.nextId()),new TenantId("test"),
        //        new Name("aa"));
        //when(productDomainServiceStub.createProduct(product)).thenReturn();
        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        productSaveDTO.setAlbums(null);
        ProductBO productBO = productApplicationService.createProduct(createProductSaveDTO());
        Assertions.assertTrue(ObjectUtils.isNull(
                productBO.getImages()) , "创建失败");

    }


    @Test
    @DisplayName("创建商品-字典为空")
    public void createProductWhenDictIsNull() {
        //Product product = new Product(new ProductId(idRepository.nextId()),new TenantId("test"),
        //        new Name("aa"));
        //when(productDomainServiceStub.createProduct(product)).thenReturn();
        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        productSaveDTO.setDicts(null);
        ProductBO productBO = productApplicationService.createProduct(productSaveDTO);
        Assertions.assertTrue(
                productBO.getDicts()==null ||
                        productBO.getDicts().size()==0
                 , "创建失败");

    }

    @Test
    @DisplayName("创建商品-详情为空")
    public void createProductWhenDetailIsNull() {
        //Product product = new Product(new ProductId(idRepository.nextId()),new TenantId("test"),
        //        new Name("aa"));
        //when(productDomainServiceStub.createProduct(product)).thenReturn();
        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        productSaveDTO.setDetail(null);
        ProductBO productBO = productApplicationService.createProduct(productSaveDTO);
        Assertions.assertTrue(
                productBO.getDetail()==null
                , "创建失败");

    }


    @Test
    @DisplayName("创建商品-规格相册为空")
    public void createProductWhenSkuAlbumsIsNull() {

        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        productSaveDTO.getSkus().forEach(
                x-> x.setAlbums(null)
        );
        ProductBO productBO = productApplicationService.createProduct(productSaveDTO);
        for (ProductSkuBO skus : productBO.getSkus()) {
            Assertions.assertTrue(
                    skus.getSkuImages()==null || skus.getSkuImages().size()==0
                     , "创建失败");
        }

    }



    public void createSku(String productId){

//        ProductSkuSaveDTO productSkuSaveDTO=new ProductSkuSaveDTO();
//        productSkuSaveDTO.setProductId(productId);
    }

    private void initStub() {

    }
    private void initFixture() {
        TenantContext.setTenantId("test");


    }


    private ProductSaveDTO createProductSaveDTO(){
        ProductSaveDTO productSaveDTO=new ProductSaveDTO();
        productSaveDTO.setName("优生活香水洗衣液持久留香柔顺护色机洗手洗天然家庭装");
        String v="[\n" +
                "      {\"name\":\"颜色\",\"value\":\"红\",\"sort\":1},\n" +
                "      {\"name\":\"颜色\",\"value\":\"蓝\",\"sort\":3},\n" +
                "      {\"name\":\"尺寸\",\"value\":\"X\",\"sort\":4},\n" +
                "      {\"name\":\"尺寸\",\"value\":\"XL\",\"sort\":5}\n" +
                "  ]";
        java.util.List<ProductAttributeSaveDTO> productAttributeSaveDTOS = JSONUtil.toList(v, ProductAttributeSaveDTO.class);
        productSaveDTO.setAttributes(productAttributeSaveDTOS);

        String images="[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}]";

        java.util.List<ProductImageSaveDTO> productImageSaveDTOS = JSONUtil.toList(images, ProductImageSaveDTO.class);
        productSaveDTO.setAlbums(productImageSaveDTOS);

        productSaveDTO.setBrandId("1234567");
        productSaveDTO.setEnableFlag(true);
        productSaveDTO.setCustomClassificationId("1234");

        String dics="[{\"key\":\"材质\",\"value\":\"塑料\",\"sort\":1}," +
                "{\"key\":\"包装\",\"value\":\"纸箱\",\"sort\":2}]";
        java.util.List<ProductDictSaveDTO> productDictSaveDTOS = JSONUtil.toList(dics, ProductDictSaveDTO.class);
        productSaveDTO.setDicts(productDictSaveDTOS);

        java.util.List<ProductSkuSaveDTO> skus = new ArrayList<>();
        String sku1="{\n" +
                "     \"skuCode\":\"001\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"红\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"X\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO1 = JSONUtil.toBean(sku1, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO1);

        String sku2="{\n" +
                "     \"skuCode\":\"002\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"蓝\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"X\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO2 = JSONUtil.toBean(sku2, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO2);

        String sku3="{\n" +
                "     \"skuCode\":\"003\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"红\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"XL\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO3 = JSONUtil.toBean(sku3, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO3);

        String sku4="{\n" +
                "     \"skuCode\":\"004\",\n" +
                "     \"price\":\"12\",\n" +
                "     \"supplyPrice\":\"12\",\n" +
                "     \"weight\":\"1\",\n" +
                "     \"enabledFlag\":\"true\",\n" +
                "     \"albums\":[{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":1}\n" +
                "     ,{\"url\":\"https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg\",\"sort\":2}],\n" +
                "     \"attributes\":[{\"name\":\"颜色\",\"value\":\"蓝\",\"sort\":9},{\"name\":\"尺寸\",\"value\":\"XL\",\"sort\":9}],\n" +
                "     \"inventory\":99,\n" +
                "     \"sort\":99,\n" +
                "\n" +
                "     }";
        ProductSkuSaveDTO productSkuSaveDTO4 = JSONUtil.toBean(sku4, ProductSkuSaveDTO.class);
        skus.add(productSkuSaveDTO4);

        productSaveDTO.setSkus(skus);

        productSaveDTO.setExplosivesFlag(true);
        productSaveDTO.setExplosivesImage("https://cbu01.alicdn.com/img/ibank/2019/004/218/10888812400_1788414178.jpg");

        productSaveDTO.setFirstCategory("家居用品");
        productSaveDTO.setSecondCategory("日用百货");
        productSaveDTO.setThirdCategory("清洁洗剂");
        productSaveDTO.setVideoImg("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");
        productSaveDTO.setVideoUrl("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");
        productSaveDTO.setInstallVideoImg("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");
        productSaveDTO.setInstallVideoUrl("https://cbu01.alicdn.com/img/ibank/2019/423/040/10889040324_1788414178.jpg");

        productSaveDTO.setPublicFlag(true);
        productSaveDTO.setRecommendFlag(true);
        productSaveDTO.setNewFlag(true);
        productSaveDTO.setTag("优生活,香水,洗衣液");

        productSaveDTO.setTenantId("001");
        productSaveDTO.setDetail("sadfdsafasdfasdfasdfsdf");
        return productSaveDTO;
    }




}
