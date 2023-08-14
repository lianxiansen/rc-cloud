package com.rc.cloud.app.operate.application;

import com.rc.cloud.app.operate.application.bo.*;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.BrandApplicationService;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.application.service.impl.ProductApplicationServiceImpl;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.BrandService;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryRebuildFactory;
import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategoryService;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailService;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictService;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImageService;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuService;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.*;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.convert.ProductCategoryConvert;
import com.rc.cloud.app.operate.infrastructure.util.RandomUtils;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.test.core.ut.BaseDbUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: ProductApplicationServiceUnitTest
 * @Author: liandy
 * @Date: 2023/7/7 11:01
 * 1.创建商品
 */

@Import({  LocalIdRepositoryImpl.class, ProductApplicationServiceImpl.class,ProductService.class
        , ProductSkuService.class,
        ProductImageService.class,
        ProductCategoryService.class,
        ProductDictService.class, ProductDetailService.class
        , ProductRepositoryImpl.class
        , ProductSkuRepositoryImpl.class
        , ProductDictRepositoryImpl.class
        , ProductCategoryConvert.class
        , BrandService.class
        , BrandRepositoryImpl.class
        , ProductCategoryRepositoryImpl.class
        , ProductCategoryRebuildFactory.class
        , ProductImageRepositoryImpl.class
        , ProductDetailRepositoryImpl.class})
public class ProductApplicationServiceIntegratedTest extends BaseDbUnitTest {

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
        Assertions.assertEquals(productBO.getId(),newProductBO.getId());
        Assertions.assertEquals(productBO.getProductOrigin(),newProductBO.getProductOrigin());
        for (AttributeBO attribute : productBO.getAttributes()) {
            AttributeBO newAttribute = productBO.getAttributes().stream().filter(u -> u.getAttribute().equals(attribute.getAttribute())).findFirst().get();
            Assertions.assertArrayEquals(attribute.getValues().toArray(),
                    newAttribute.getValues().toArray()   );
        }
        Assertions.assertEquals(productBO.getDetail(),newProductBO.getDetail());
        Assertions.assertEquals(productBO.getBrandId(),newProductBO.getBrandId());
        Assertions.assertEquals(productBO.getBrandName(),newProductBO.getBrandName());

        Assertions.assertEquals(productBO.getCustomClassificationId(),newProductBO.getCustomClassificationId());
        Assertions.assertEquals(productBO.getExplosivesImage(),newProductBO.getExplosivesImage());

        Assertions.assertArrayEquals(productBO.getDicts().entrySet().toArray(),
                newProductBO.getDicts().entrySet().toArray());
        Assertions.assertEquals(
                productBO.getInstallDetail()
                ,newProductBO.getInstallDetail());
        Assertions.assertEquals(productBO.getInstallVideoImg(),newProductBO.getInstallVideoImg());
        Assertions.assertEquals(productBO.getOnshelfStatus(),newProductBO.getOnshelfStatus());
        Assertions.assertEquals(productBO.getName(),newProductBO.getName());
        Assertions.assertEquals(productBO.getRemark(),newProductBO.getRemark());
        Assertions.assertEquals(productBO.getProductListImage(),newProductBO.getProductListImage());
        Assertions.assertArrayEquals(
                productBO.getMasterAlbums().toArray()
                ,newProductBO.getMasterAlbums().toArray());
        Assertions.assertArrayEquals(
                productBO.getSizeAlbums().toArray()
                ,newProductBO.getSizeAlbums().toArray());

        Assertions.assertEquals(productBO.getVideoImg(),newProductBO.getVideoImg());

        Assertions.assertEquals(productBO.getTag(),newProductBO.getTag());
        Assertions.assertEquals(productBO.getVideoUrl(),newProductBO.getVideoUrl());
        Assertions.assertEquals(productBO.getSpuCode(),newProductBO.getSpuCode());
        Assertions.assertEquals(productBO.getSort(),newProductBO.getSort());
        Assertions.assertEquals(productBO.getOutId(),newProductBO.getOutId());

        for (ProductSkuBO sku : productBO.getSkus()) {
            ProductSkuBO newSku = productBO.getSkus().stream().filter(u->u.getId().equals(sku.getId())).findFirst().get();
            Assertions.assertEquals(sku.getInventory(),newSku.getInventory());
            Assertions.assertEquals(sku.getSort(),newSku.getSort());
            Assertions.assertEquals(sku.getLimitBuy(),newSku.getLimitBuy());
            Assertions.assertEquals(sku.getCartonSizeWidth(),newSku.getCartonSizeWidth());
            Assertions.assertEquals(sku.getCartonSizeHeight(),newSku.getCartonSizeHeight());
            Assertions.assertEquals(sku.getCartonSizeLength(),newSku.getCartonSizeLength());
            Assertions.assertEquals(sku.getPrice(),newSku.getPrice());
            Assertions.assertEquals(sku.getSkuCode(),newSku.getSkuCode());
            Assertions.assertEquals(sku.getPackingNumber(),newSku.getPackingNumber());
            Assertions.assertEquals(sku.getSeckillLimitBuy(),newSku.getSeckillLimitBuy());
            Assertions.assertEquals(sku.getSeckillPrice(),newSku.getSeckillPrice());
            Assertions.assertEquals(sku.getSeckillTotalInventory(),newSku.getSeckillTotalInventory());
            Assertions.assertEquals(sku.getSeckillInventory(),newSku.getSeckillInventory());
            Assertions.assertEquals(sku.getWeight(),newSku.getWeight());
            Assertions.assertEquals(sku.getSupplyPrice(),newSku.getSupplyPrice());
            Assertions.assertArrayEquals(sku.getSkuImages().toArray(),newSku.getSkuImages().toArray());
            Assertions.assertArrayEquals(sku.getSkuAttributes().toArray(),
                 newSku.getSkuAttributes().toArray());
        }
        Assertions.assertEquals(productBO.getFirstCategory(),newProductBO.getFirstCategory());
        Assertions.assertEquals(productBO.getSecondCategory(),newProductBO.getSecondCategory());
        Assertions.assertEquals(productBO.getThirdCategory(),newProductBO.getThirdCategory());

    }



    @Test
    @DisplayName("修改商品")
    public void updateProduct() {

        ProductSaveDTO productSaveDTO=createProductSaveDTO();
        productSaveDTO.setId("eae9d95a-3b69-43bb-9038-3309560");
        ProductBO newProductBO = productApplicationService.updateProduct(productSaveDTO);

        //校验DTO里面的值是否与BO里面的值相等
        Assertions.assertEquals(productSaveDTO.getId(),newProductBO.getId());
        Assertions.assertEquals(productSaveDTO.getProductOrigin(),newProductBO.getProductOrigin());
        Set<String> attribute=new HashSet<>();
        for (ProductAttributeSaveDTO productSaveDTOAttribute : productSaveDTO.getAttributes()) {
            attribute.add(productSaveDTOAttribute.getName());
        }
        Assertions.assertEquals(attribute.size(),newProductBO.getAttributes().size());
        for (String s : attribute) {
            AttributeBO newAttribute = newProductBO.getAttributes().stream().filter(u -> u.getAttribute().equals(s)).findFirst().get();
            Assertions.assertEquals(s,newAttribute.getAttribute());
        }
        Assertions.assertEquals(productSaveDTO.getDetail(),newProductBO.getDetail());
        Assertions.assertEquals(productSaveDTO.getBrandId(),newProductBO.getBrandId());
        Assertions.assertEquals(productSaveDTO.getCustomClassificationId(),newProductBO.getCustomClassificationId());

        Assertions.assertEquals(productSaveDTO.getExplosivesImage(),newProductBO.getExplosivesImage());

        Assertions.assertEquals(productSaveDTO.getDicts().size(),
                newProductBO.getDicts().size());

        Assertions.assertEquals(
                productSaveDTO.getInstallDetail()
                ,newProductBO.getInstallDetail());

        Assertions.assertEquals(productSaveDTO.getInstallVideoImg(),newProductBO.getInstallVideoImg());
        Assertions.assertEquals(productSaveDTO.getOnshelfStatus(),newProductBO.getOnshelfStatus());
        Assertions.assertEquals(productSaveDTO.getName(),newProductBO.getName());
        Assertions.assertEquals(productSaveDTO.getRemark(),newProductBO.getRemark());
        Assertions.assertEquals(productSaveDTO.getListImage(),newProductBO.getProductListImage());


        if(productSaveDTO.getMasterAlbums()!=null){
            for (ProductImageSaveDTO masterAlbum : productSaveDTO.getMasterAlbums()) {
                ProductImageBO productImageBO = newProductBO.getMasterAlbums().stream().filter(u -> u.getId().equals(masterAlbum.getId())).findFirst().get();
                Assertions.assertEquals(masterAlbum.getId(),productImageBO.getId());
                Assertions.assertEquals(masterAlbum.getUrl(),productImageBO.getUrl());
                Assertions.assertEquals(masterAlbum.getSort(),productImageBO.getSort());

            }
        }

        if(productSaveDTO.getSizeAlbums()!=null){
            for (ProductImageSaveDTO sizeAlbum : productSaveDTO.getSizeAlbums()) {
                ProductImageBO productImageBO = newProductBO.getSizeAlbums().stream().filter(u -> u.getId().equals(sizeAlbum.getId())).findFirst().get();
                Assertions.assertEquals(sizeAlbum.getId(),productImageBO.getId());
                Assertions.assertEquals(sizeAlbum.getUrl(),productImageBO.getUrl());
                Assertions.assertEquals(sizeAlbum.getSort(),productImageBO.getSort());

            }
        }

        Assertions.assertEquals(productSaveDTO.getVideoImg(),newProductBO.getVideoImg());
        Assertions.assertEquals(productSaveDTO.getTag(),newProductBO.getTag());
        Assertions.assertEquals(productSaveDTO.getVideoUrl(),newProductBO.getVideoUrl());
        Assertions.assertEquals(productSaveDTO.getSpuCode(),newProductBO.getSpuCode());
        Assertions.assertEquals(productSaveDTO.getSort(),newProductBO.getSort());
        Assertions.assertEquals(productSaveDTO.getOutId(),newProductBO.getOutId());
        Assertions.assertEquals(productSaveDTO.getSkus().size(),newProductBO.getSkus().size());
        for (ProductSkuSaveDTO sku : productSaveDTO.getSkus()) {

            ProductSkuBO newSku = newProductBO.getSkus().stream().filter(u->u.getId().equals(sku.getId())).findFirst().get();
            Assertions.assertEquals(sku.getInventory().intValue(),newSku.getInventory());
            Assertions.assertEquals(sku.getSort(),newSku.getSort());
            Assertions.assertEquals(sku.getCartonSizeWidth(),newSku.getCartonSizeWidth());
            Assertions.assertEquals(sku.getCartonSizeHeight(),newSku.getCartonSizeHeight());
            Assertions.assertEquals(sku.getCartonSizeLength(),newSku.getCartonSizeLength());
            Assertions.assertEquals(sku.getPrice(),newSku.getPrice().toString());
            Assertions.assertEquals(sku.getSkuCode(),newSku.getSkuCode());
            Assertions.assertEquals(sku.getPackingNumber(),newSku.getPackingNumber());
            Assertions.assertEquals(sku.getWeight(),newSku.getWeight().toString());
            Assertions.assertEquals(sku.getSupplyPrice(),newSku.getSupplyPrice().toString());

            for (ProductSkuImageSaveDTO album : sku.getAlbums()) {
                ProductSkuImageBO productSkuImageBO = newSku.getSkuImages().stream().filter(u -> u.getUrl().equals(album.getUrl())).findFirst().get();
                Assertions.assertEquals(album.getUrl(),productSkuImageBO.getUrl());
                Assertions.assertEquals(album.getSort(),productSkuImageBO.getSort());
            }

            Assertions.assertEquals(sku.getAttributes().size(),
                    newSku.getSkuAttributes().size());

            for (ProductSkuAttributeSaveDTO skuAttribute : sku.getAttributes()) {
                AttributeValueCombinationBO attributeValueCombinationBO = newSku.getSkuAttributes().stream().filter(u -> u.getAttribute().equals(skuAttribute.getName())).findFirst().get();
                Assertions.assertEquals(skuAttribute.getName(),
                        attributeValueCombinationBO.getAttribute());
                Assertions.assertEquals(skuAttribute.getValue(),
                        attributeValueCombinationBO.getAttributeValue());
            }

        }
        Assertions.assertEquals(productSaveDTO.getFirstCategory(),newProductBO.getFirstCategory());
        Assertions.assertEquals(productSaveDTO.getSecondCategory(),newProductBO.getSecondCategory());
        Assertions.assertEquals(productSaveDTO.getThirdCategory(),newProductBO.getThirdCategory());

    }



    @Test
    @DisplayName("删除商品")
    public void removeProduct(){
        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        int k=RandomUtils.randomInteger()%5;

        ProductRemoveDTO removeDTO=new ProductRemoveDTO();
        List<String> productIds=new ArrayList<>();
        for (int i = 0; i < k; i++) {
            ProductBO productBO = productApplicationService.createProduct(productSaveDTO);
            productIds.add(productBO.getId());
        }
        removeDTO.setProductIds(productIds);
        Assertions.assertEquals(k,productIds.size());
        ProductRemoveBO productRemoveBO = productApplicationService.removeProductBatch(removeDTO);
        for (String productId : productIds) {
            ProductBO product = getProduct(productId);
            Assertions.assertEquals(product,null);
        }
    }


    @Test
    @DisplayName("验证商品")
    public void validateProductList(){
        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        int k=RandomUtils.randomInteger()%5;

        ProductRemoveDTO removeDTO=new ProductRemoveDTO();
        List<String> productIds=new ArrayList<>();
        List<String> removeProductIds=new ArrayList<>();
        List<ProductBO> boList =new ArrayList<>();

        List<ProductValidateDTO> productValidateDTOs =new ArrayList<>();
        for (int i = 0; i < k; i++) {
            ProductBO productBO = productApplicationService.createProduct(productSaveDTO);

            ProductValidateDTO productValidateDTO=new ProductValidateDTO();
            productValidateDTO.setProductId(productBO.getId());
            productValidateDTO.setProductSkuId(productBO.getSkus().get(0).getId());
            productValidateDTOs.add(productValidateDTO);

            productIds.add(productBO.getId());
            if(i==0){
                removeProductIds.add(productBO.getId());
            }else{
                boList.add(productBO);
            }
        }
        removeDTO.setProductIds(removeProductIds);
        ProductRemoveBO productRemoveBO = productApplicationService.removeProductBatch(removeDTO);


        List<ProductValidateBO> productValidateBOList = productApplicationService.validateProductList(productValidateDTOs);
        for (ProductValidateBO productValidateBO : productValidateBOList) {
            if(productValidateBO.isEnabled()==false){
                Assertions.assertEquals(productValidateBO.getProductId(),removeProductIds.get(0));
            }else{
                ProductBO productBO = boList.stream().filter(u -> u.getId().equals(productValidateBO.getProductId())).findFirst().get();
                Assertions.assertNotNull(productBO);
                Assertions.assertEquals(productValidateBO.getProductSku().getProductName(),productBO.getName());
                Assertions.assertEquals(productValidateBO.getProductSku().getProductImage(),productBO.getName());

            }
        }


    }

    @Test
    @DisplayName("获取商品列表")
    public void getProductList(){
        //移除初始化数据
        ProductRemoveDTO removeDTO=new ProductRemoveDTO();
        List<String> productIds=new ArrayList<>();
        productIds.add("eae9d95a-3b69-43bb-9038-3309560");
        removeDTO.setProductIds(productIds);
        ProductRemoveBO productRemoveBO = productApplicationService.removeProductBatch(removeDTO);
        //模拟数据
        ProductSaveDTO productSaveDTO = createProductSaveDTO();
        int k=RandomUtils.randomInteger()%1000;
        List<ProductBO> boList =new ArrayList<>();
        for (int i = 0; i < k; i++) {
            productSaveDTO.setSort(i+1);
            ProductBO productBO = productApplicationService.createProduct(productSaveDTO);
            boList.add(productBO);
        }
        ProductListQueryDTO query =new ProductListQueryDTO();
        query.setPageNo(2);
        query.setPageSize(10);
        query.setOrder("sort");
        query.setAsc(true);
        PageResult<ProductBO> productList = productApplicationService.getProductList(query);
        Assertions.assertEquals(productList.getTotal(),k);
        int pos=11;
        for (ProductBO productBO : productList.getList()) {
            Assertions.assertEquals(productBO.getSort(),pos);
            pos++;
        }

    }

    @Test
    @DisplayName("获取商品列表")
    public void changeNewStatus(){

    }

    @Test
    @DisplayName("获取商品列表")
    public void changeOnshelfStatus(){

    }

    @Test
    @DisplayName("获取商品列表")
    public void changePublicStatus(){

    }

    @Test
    @DisplayName("获取商品列表")
    public void changeRecommendStatus(){

    }

    @Test
    @DisplayName("获取商品列表")
    public void changeExplosivesStatus(){

    }

    public ProductBO getProduct(String id){
        ProductQueryDTO productQueryDTO=new ProductQueryDTO();
        productQueryDTO.setProductId(id);
        productQueryDTO.setNeedProductDetail(true);
        productQueryDTO.setNeedProductSku(true);
        productQueryDTO.setNeedProductSizeImage(true);
        productQueryDTO.setNeedProductMasterImage(true);
        ProductBO product = productApplicationService.getProduct(productQueryDTO);
        return product;
    }



    private String attrbute[]= new String[]{
            "颜色","规格"
    };
    private String attrbuteValue[][]= new String[][]{
            new String[]{
                    "红","黄","蓝"
            },
            new String[]{
                    "X","XL"
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
                productSkuSaveDTO.setInventory(99);
                productSkuSaveDTO.setSkuCode(RandomUtils.randomString());
                productSkuSaveDTO.setSort(50);
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
                    productSkuSaveDTO.setInventory(99);
                    productSkuSaveDTO.setSkuCode(RandomUtils.randomString());
                    productSkuSaveDTO.setSort(50);
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
        productSaveDTO.setBrandId("f7570440-f052-462c-b6a8-984b799");
        productSaveDTO.setCustomClassificationId(RandomUtils.randomString());
        productSaveDTO.setSkus(createProductSku(randomNum));

        productSaveDTO.setExplosivesFlag(true);
        productSaveDTO.setExplosivesImage("https://"+RandomUtils.randomString());
        productSaveDTO.setFirstCategory("w5x0nzyvbd");
        productSaveDTO.setSecondCategory("w5x0nzyvbd");
        productSaveDTO.setThirdCategory("w5x0nzyvbd");
        productSaveDTO.setVideoImg("https://"+RandomUtils.randomString());
        productSaveDTO.setVideoUrl("https://"+RandomUtils.randomString());
        productSaveDTO.setInstallVideoImg("https://"+RandomUtils.randomString());
        productSaveDTO.setInstallVideoUrl("https://"+RandomUtils.randomString());
        productSaveDTO.setInstallDetail(RandomUtils.randomString());
        productSaveDTO.setOnshelfStatus(ProductShelfStatusEnum.Onshelf.value);
        productSaveDTO.setSort(99);
        productSaveDTO.setPublicFlag(true);
        productSaveDTO.setRecommendFlag(true);
        productSaveDTO.setNewFlag(true);
        productSaveDTO.setTag(RandomUtils.randomString()+","+RandomUtils.randomString());
        productSaveDTO.setDetail(RandomUtils.randomString());
        productSaveDTO.setPackingLowestBuyFlag(true);
        productSaveDTO.setSpuCode(RandomUtils.randomString());
        return productSaveDTO;
    }




}
