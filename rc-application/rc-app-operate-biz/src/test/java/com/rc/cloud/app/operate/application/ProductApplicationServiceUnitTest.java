package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.BrandServiceImpl;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailDomainService;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictDomainService;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuDomainService;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.*;
import com.rc.cloud.app.operate.infrastructure.repository.remote.TenantServiceImpl;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

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
        , BrandServiceImpl.class
        , BrandRepositoryImpl.class
        , ProductDetailRepositoryImpl.class})
public class ProductApplicationServiceUnitTest extends BaseDbUnitTest {

    @Autowired
    ProductApplicationService productApplicationService;


    @Autowired
    private IdRepository idRepository;

    @BeforeEach
    public void beforeEach() {
    }


    @Test
    @DisplayName("创建商品")
    public void createProduct() {

        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        ProductBO productBO = productApplicationService.createProduct(productSaveDTO);

        String id = productBO.getId();

        ProductBO newProductBO = getProduct(id);
        //校验是否相等
        Assertions.assertEquals(productBO,newProductBO);


    }

    public ProductBO getProduct(String id){
        ProductQueryDTO productQueryDTO=new ProductQueryDTO();
        productQueryDTO.setProductId(id);
        productQueryDTO.setNeedProductDetail(true);
        productQueryDTO.setNeedProductDict(true);
        productQueryDTO.setNeedProductSku(true);
        ProductBO product = productApplicationService.getProduct(productQueryDTO);
        return product;
    }



    private String attrbute[]= new String[]{
            "颜色","尺寸"
    };
    private String attrbuteValue[][]= new String[][]{
            new String[]{
                    "红","黄","蓝"
            },
            new String[]{
                    "X","XL","XLL"
            }
    };

    private java.util.List<ProductAttributeSaveDTO> createProductAttribute(int randomNum){
      //  int randomNum= RandomUtils.randomInteger()%3;
        java.util.List<ProductAttributeSaveDTO> productAttributeSaveDTOS=new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            for (int j = 0; j < attrbuteValue[i].length; j++) {
                ProductAttributeSaveDTO productAttributeSaveDTO=new ProductAttributeSaveDTO();
                productAttributeSaveDTO.setName(attrbute[i]);
                productAttributeSaveDTO.setValue(attrbuteValue[i][j]);
                productAttributeSaveDTO.setSort((i+1)*j+1);
                productAttributeSaveDTOS.add(productAttributeSaveDTO);
            }

        }
        return productAttributeSaveDTOS;
    }

    private java.util.List<ProductSkuSaveDTO> createProductSku(int randomNum){

        java.util.List<ProductSkuSaveDTO> skus = new ArrayList<>();

        if(randomNum==1){
            for (int j = 0; j < attrbuteValue[0].length; j++) {
                ProductSkuSaveDTO productSkuSaveDTO=new ProductSkuSaveDTO();
                productSkuSaveDTO.setAlbums(createProductSkuImage(1));
                List<ProductSkuAttributeSaveDTO>
                        arrs= new ArrayList<>();
                ProductSkuAttributeSaveDTO att=new ProductSkuAttributeSaveDTO();
                att.setName(attrbute[0]);
                att.setValue(attrbuteValue[0][j]);
                att.setSort(j+1);
                arrs.add(att);
                productSkuSaveDTO.setAttributes(arrs);
                productSkuSaveDTO.setInventory(RandomUtils.randomInteger());
                productSkuSaveDTO.setSkuCode(RandomUtils.randomString());
                productSkuSaveDTO.setSort(RandomUtils.randomInteger());
                productSkuSaveDTO.setCartonSizeHeight(RandomUtils.randomInteger());
                productSkuSaveDTO.setCartonSizeLength(RandomUtils.randomInteger());
                productSkuSaveDTO.setCartonSizeWidth(RandomUtils.randomInteger());
                productSkuSaveDTO.setPackingNumber(RandomUtils.randomInteger());
                productSkuSaveDTO.setPrice("0.01");
                productSkuSaveDTO.setSupplyPrice("0.01");
                productSkuSaveDTO.setWeight("0.01");
                productSkuSaveDTO.setEnabledFlag(true);
                skus.add(productSkuSaveDTO);
            }
        }
        if(randomNum==2){
            for (int i = 0; i < attrbuteValue[0].length; i++) {
                for (int j = 0; j < attrbuteValue[1].length; j++) {
                    ProductSkuSaveDTO productSkuSaveDTO=new ProductSkuSaveDTO();
                    productSkuSaveDTO.setAlbums(createProductSkuImage(1));
                    List<ProductSkuAttributeSaveDTO>
                            arrs= new ArrayList<>();
                    ProductSkuAttributeSaveDTO att1=new ProductSkuAttributeSaveDTO();
                    att1.setName(attrbute[0]);
                    att1.setValue(attrbuteValue[0][i]);
                    att1.setSort((i+1)*j+1);
                    arrs.add(att1);
                    ProductSkuAttributeSaveDTO att2=new ProductSkuAttributeSaveDTO();
                    att2.setName(attrbute[1]);
                    att2.setValue(attrbuteValue[1][j]);
                    att2.setSort((i+1)*j+1);
                    arrs.add(att2);
                    productSkuSaveDTO.setAttributes(arrs);
                    productSkuSaveDTO.setInventory(RandomUtils.randomInteger());
                    productSkuSaveDTO.setSkuCode(RandomUtils.randomString());
                    productSkuSaveDTO.setSort(RandomUtils.randomInteger());
                    productSkuSaveDTO.setCartonSizeHeight(RandomUtils.randomInteger());
                    productSkuSaveDTO.setCartonSizeLength(RandomUtils.randomInteger());
                    productSkuSaveDTO.setCartonSizeWidth(RandomUtils.randomInteger());
                    productSkuSaveDTO.setPackingNumber(RandomUtils.randomInteger());
                    productSkuSaveDTO.setPrice("0.01");
                    productSkuSaveDTO.setSupplyPrice("0.01");
                    productSkuSaveDTO.setWeight("0.01");
                    productSkuSaveDTO.setEnabledFlag(true);
                    skus.add(productSkuSaveDTO);
                }
            }
        }
        return skus;
    }

    private List<ProductImageSaveDTO>  createProductImage(int randomNum){
        List<ProductImageSaveDTO> productImageSaveDTOList =new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            ProductImageSaveDTO productImageSaveDTO=new ProductImageSaveDTO();
            productImageSaveDTO.setUrl("http://"+ RandomUtils.randomString());
            productImageSaveDTO.setSort(i+1);
            productImageSaveDTOList.add(productImageSaveDTO);
        }
        return productImageSaveDTOList;
    }

    private List<ProductSkuImageSaveDTO>  createProductSkuImage(int randomNum){
        List<ProductSkuImageSaveDTO> productSkuImageSaveDTOList =new ArrayList<>();
        for (int i = 0; i < randomNum; i++) {
            ProductSkuImageSaveDTO productSkuImageSaveDTO=new ProductSkuImageSaveDTO();
            productSkuImageSaveDTO.setUrl("http://"+ RandomUtils.randomString());
            productSkuImageSaveDTO.setSort(i+1);
            productSkuImageSaveDTOList.add(productSkuImageSaveDTO);
        }
        return productSkuImageSaveDTOList;
    }


    private ProductSaveDTO createProductSaveDTO(){
        ProductSaveDTO productSaveDTO=new ProductSaveDTO();
        productSaveDTO.setName(RandomUtils.randomString());
        productSaveDTO.setProductType(1);
        productSaveDTO.setProductOrigin(1);
        productSaveDTO.setOutId(RandomUtils.randomString());
        productSaveDTO.setListImage("https://"+RandomUtils.randomString());
        productSaveDTO.setRemark(RandomUtils.randomString());

        int randomNum= RandomUtils.randomInteger()%2+1;

        ProductDictSaveDTO productDictSaveDTO=new ProductDictSaveDTO();
        productDictSaveDTO.setKey("材质");
        productDictSaveDTO.setValue("塑料");
        productDictSaveDTO.setSort(1);
        List<ProductDictSaveDTO> productDictSaveDTOList=new ArrayList<>();
        productDictSaveDTOList.add(productDictSaveDTO);
        productSaveDTO.setDicts(productDictSaveDTOList);

        java.util.List<ProductAttributeSaveDTO> productAttributeSaveDTOS =
                createProductAttribute(randomNum);
        productSaveDTO.setAttributes(productAttributeSaveDTOS);

        List<ProductImageSaveDTO> productImage = createProductImage(randomNum);
        productSaveDTO.setMasterAlbums(productImage);
        List<ProductImageSaveDTO> productImage1 = createProductImage(randomNum);
        productSaveDTO.setSizeAlbums(productImage1);
        productSaveDTO.setBrandId(RandomUtils.randomString());
        productSaveDTO.setCustomClassificationId(RandomUtils.randomString());
        productSaveDTO.setSkus(createProductSku(randomNum));

        productSaveDTO.setExplosivesFlag(true);
        productSaveDTO.setExplosivesImage("https://"+RandomUtils.randomString());
        productSaveDTO.setFirstCategory("家居用品");
        productSaveDTO.setSecondCategory("日用百货");
        productSaveDTO.setThirdCategory("清洁洗剂");
        productSaveDTO.setVideoImg("https://"+RandomUtils.randomString());
        productSaveDTO.setVideoUrl("https://"+RandomUtils.randomString());
        productSaveDTO.setInstallVideoImg("https://"+RandomUtils.randomString());
        productSaveDTO.setInstallVideoUrl("https://"+RandomUtils.randomString());
        productSaveDTO.setInstallDetail(RandomUtils.randomString());
        productSaveDTO.setOnShelfStatus(ProductShelfStatusEnum.OnShelf.value);
        productSaveDTO.setSort(99);
        productSaveDTO.setPublicFlag(true);
        productSaveDTO.setRecommendFlag(true);
        productSaveDTO.setNewFlag(true);
        productSaveDTO.setTag(RandomUtils.randomString()+","+RandomUtils.randomString());
        productSaveDTO.setTenantId("001");
        productSaveDTO.setDetail(RandomUtils.randomString());
        productSaveDTO.setPackingLowestBuyFlag(true);
        productSaveDTO.setSpuCode(RandomUtils.randomString());
        return productSaveDTO;
    }




}
