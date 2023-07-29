package com.rc.cloud.app.operate.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductRemoveBO;
import com.rc.cloud.app.operate.application.bo.ProductSkuBO;
import com.rc.cloud.app.operate.application.bo.convert.*;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.common.ProductRemoveTypeEnum;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandDomainService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailDomainService;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictDomainService;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImage;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImageDomainService;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuDomainService;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ErrorCode;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: chenjianxiang
 * @Date: 2023/7/1
 * @Description:
 */
@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {

    @Autowired
    private ProductDomainService productDomainService;

    @Autowired
    private ProductSkuDomainService productSkuDomainService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ProductImageDomainService productImageDomainService;

    @Autowired
    private ProductDictDomainService productDictDomainService;

    @Autowired
    private ProductDetailDomainService productDetailDomainService;

    @Autowired
    private BrandDomainService brandDomainService;

    @Resource
    private IdRepository idRepository;

    public void validateTenantId(TenantId tenantId) {
        if (!tenantService.exists(tenantId)) {
            throw new IllegalArgumentException("所属租户错误");
        }
    }

    /**
     * 创建商品
     * 创建商品图片
     * 创建商品字典
     * 创建商品详情
     * 创建商品sku
     *
     * @param productSaveDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductBO createProduct(ProductSaveDTO productSaveDTO) {


        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();
        if (skus == null || skus.size() <= 0) {
            throw new IllegalArgumentException("sku不能为空");
        }

        ProductId productId =new ProductId(idRepository.nextId());
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId());
        productSaveDTO.setId(productId.getId());

        Product product= ProductConvert.convertDomain(productId.id()
                ,tenantId.id(),productSaveDTO,true,null);

        productDomainService.createProduct(product);

        List<ProductImage> productSizeImages=null;
        List<ProductImage> productMasterImages=null;
        Set<ProductDict> productDicts=null;
        ProductDetail productDetail=null;
        //设置商品图片

        productSizeImages = ProductImageConvert
                .convertDomainList(productSaveDTO.getSizeAlbums(), productId, tenantId, ProductImageTypeEnum.SizeImage);

        productMasterImages = ProductImageConvert
                .convertDomainList(productSaveDTO.getMasterAlbums(),productId,tenantId, ProductImageTypeEnum.MasterImage);

        productImageDomainService.insertProductSizeImageList( productSizeImages);
        productImageDomainService.insertProductMasterImageList(productMasterImages);
        //设置字典
       productDicts = ProductDictConvert
                .convertProductDictSet(productId.id(), tenantId.id(), productSaveDTO.getDicts());
        productDictDomainService.insertProductDict(productDicts);
        //设置详情
        productDetail = ProductDetailConvert.convertDomain(
                new ProductId(productId.id()),
                new TenantId(tenantId.id()),
                new Detail( productSaveDTO.getDetail()),
                new Url(productSaveDTO.getInstallVideoUrl()),
                new Url(productSaveDTO.getInstallVideoImg()),
                new Detail(productSaveDTO.getInstallDetail())
                );
        productDetailDomainService.saveProductDetail(productDetail);

        List<ProductSku> productSkuList=new ArrayList<>();
        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            ProductSkuId productSkuId = new ProductSkuId(idRepository.nextId());
            productSkuSaveDTO.setId(productSkuId.getId());
            ProductSku productSku = ProductSkuConvert.convertDomain(productSkuId, productId
                    , tenantId, productSkuSaveDTO, true, null);
            productSkuList.add(productSku);
        }
        productSkuDomainService.batchSaveProductSku(productSkuList);
        return ProductConvert.convertProductBO(product,productMasterImages ,productSizeImages,productDicts,productDetail,productSkuList);

    }


    /**
     * 修改商品
     *
     * @param productSaveDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductBO updateProduct(ProductSaveDTO productSaveDTO) {

        ProductId productId = new ProductId(productSaveDTO.getId());
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId());
        //修改
        Product product = productDomainService.findProductById(productId);
        if (null == product) {
            throw new IllegalArgumentException("未找到当前商品");
        }
        product= ProductConvert.convertDomain(productId.id()
                ,tenantId.id(),productSaveDTO,false,product);

        productDomainService.updateProduct(product);

        List<ProductImage> productSizeImages=null;
        List<ProductImage> productMasterImages=null;
        Set<ProductDict> productDicts=null;
        ProductDetail productDetail=null;
        if(productSaveDTO.getSizeAlbums()!=null){
            productSizeImages = ProductImageConvert
                    .convertDomainList(productSaveDTO.getSizeAlbums(), productId, tenantId, ProductImageTypeEnum.SizeImage);
            productImageDomainService.updateProductSizeImageList(productId, productSizeImages);
        }
        if(productSaveDTO.getMasterAlbums()!=null){
            productMasterImages = ProductImageConvert
                    .convertDomainList(productSaveDTO.getMasterAlbums(),productId,tenantId, ProductImageTypeEnum.MasterImage);
            productImageDomainService.updateProductMasterImageList(productId,productMasterImages);
        }
        if(productSaveDTO.getDicts()!=null){
            productDicts = ProductDictConvert.convertProductDictSet(productId.id(), tenantId.id(), productSaveDTO.getDicts());
            productDictDomainService.updateProductDict(productId,productDicts);
        }
        productDetail = ProductDetailConvert.convertDomain(
                new ProductId(productId.id()),
                new TenantId(tenantId.id()),
                new Detail( productSaveDTO.getDetail()),
                new Url(productSaveDTO.getInstallVideoUrl()),
                new Url(productSaveDTO.getInstallVideoImg()),
                new Detail(productSaveDTO.getInstallDetail())
        );
        productDetailDomainService.saveProductDetail(productDetail);
        List<ProductSku> productSkuList=new ArrayList<>();
        if(productSaveDTO.getSkus()!=null && productSaveDTO.getSkus().size()>0){
            for (ProductSkuSaveDTO productSkuSaveDTO :  productSaveDTO.getSkus()) {
                ProductSku productSku=null;
                if(StringUtils.isNotEmpty(productSkuSaveDTO.getId())){
                    productSkuDomainService.findProductSkuById(new ProductSkuId(productSkuSaveDTO.getId()));
                }
                if(productSku==null){
                    productSku = ProductSkuConvert.convertDomain(new ProductSkuId(idRepository.nextId()), productId
                            , tenantId, productSkuSaveDTO, true, productSku);
                }else{
                    productSku = ProductSkuConvert.convertDomain(productSku.getId(), productId
                            , tenantId, productSkuSaveDTO, false, productSku);
                }
                productSkuList.add(productSku);
            }
            productSkuDomainService.batchSaveProductSku(productSkuList);
        }

        return ProductConvert.convertProductBO(product,productMasterImages ,productSizeImages,productDicts,productDetail,productSkuList);
    }

    /**
     * 移除商品
     * 移除商品字典
     * 移除商品详情
     * 移除商品sku
     * @param productId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeProduct(String productId){
        productDomainService.deleteProduct(new ProductId(productId));
        productImageDomainService.deleteProductImageByProductId(new ProductId(productId));
        productDictDomainService.deleteProductDictByProductId(new ProductId(productId));
        productSkuDomainService.deleteProductSkuByProductId(new ProductId(productId));
        productDetailDomainService.deleteProductDetailByProductId(new ProductId(productId));
        return true;
    }

    /**
     * 批量移除商品
     * @param productRemoveDTO 传入所需要删除的商品id以及删除的类别，默认类别为软删除
     * @return
     */
    public ProductRemoveBO removeProductBatch(ProductRemoveDTO productRemoveDTO){
        if(CollectionUtil.isEmpty(productRemoveDTO.getProductIds())){
            throw new IllegalArgumentException("请选择移除的商品");//TODO
        }
        ProductRemoveBO bo=new ProductRemoveBO();
        List<String> needRemoveList=new ArrayList<>();
        List<String> successList=new ArrayList<>();
        List<Pair<String,String>> failList= new ArrayList<>();
        if(CollectionUtil.isNotEmpty(productRemoveDTO.getProductIds())){
            for (String id : productRemoveDTO.getProductIds()) {
                needRemoveList.add(id);
                try{
                    boolean success= removeProduct(id);
                    if(success){
                        successList.add(id);
                    }else{
                        Pair<String,String> errInfo =new Pair<>(id,"unknown error");
                        failList.add(errInfo);
                    }

                }catch (Exception ex){
                    Pair<String,String> errInfo =new Pair<>(id,ex.getMessage());
                    failList.add(errInfo);
                }
            }
        }
        bo.setNeedRemoveList(needRemoveList);
        bo.setSuccessList(successList);
        bo.setFailList(failList);
        return bo;
    }



    /**
     * 获取商品
     * TODO
     * @param productQueryDTO
     * @return
     */
    public ProductBO getProduct(ProductQueryDTO productQueryDTO) {
        Product product = productDomainService.findProductById(new ProductId(productQueryDTO.getProductId()));
        ProductDetail productDetail=null;
        Set<ProductDict> productDicts=null;
        List<ProductSku> productSkuList=null;
        List<ProductImage> productSizeImages=null;
        List<ProductImage> productMasterImages=null;
        if(productQueryDTO.isNeedProductMasterImage()){
            productMasterImages = productImageDomainService.getProductMasterImageByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductSizeImage()){
            productSizeImages = productImageDomainService.getProductSizeImageByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductDetail()){
            productDetail = productDetailDomainService.findProductDetailByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductDict()){
            productDicts = productDictDomainService.getProductDictSetByProductId(new ProductId(productQueryDTO.getProductId())).stream().collect(Collectors.toSet());
        }
        if(productQueryDTO.isNeedProductSku()){
            productSkuList = productSkuDomainService.getProductSkuListByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        return ProductConvert.convertProductBO(product,productMasterImages ,productSizeImages,productDicts,productDetail,productSkuList);
    }


    /**
     * 获取商品列表
     *
     * @return
     */
    public PageResult<ProductBO> getProductList(ProductListQueryDTO query) {
        PageResult<Product> resultList = productDomainService.getProductPageList(query);
        List<ProductBO> productBOS = new ArrayList<>();
        for (Product product : resultList.getList()) {
            ProductBO productBO = ProductConvert.convertProductBO(product);
            if(query.isNeedBrandName()){
                //查询品牌名
                Brand brand = brandDomainService.findById(product.getBrandId());
                if(brand!=null){
                    productBO.setBrandName(brand.getName());
                }
            }
            productBOS.add(productBO);
        }
        PageResult<ProductBO> pageResult = new PageResult<>();
        pageResult.setTotal(resultList.getTotal());
        pageResult.setList(productBOS);
        return pageResult;
    }


    public int changeNewStatus(String productId, boolean newFlag){
        if(newFlag){
            productDomainService.setNews(new ProductId(productId));
        }else{
            productDomainService.cancelNews(new ProductId(productId));
        }
        return 1;
    }


    public int changeOnShelfStatus(String productId, int onShelfStatus){
        if(onShelfStatus== ProductShelfStatusEnum.OnShelf.value){
            productDomainService.onShelf(new ProductId(productId));
        }else  if(onShelfStatus== ProductShelfStatusEnum.OffShelf.value){
            productDomainService.offShelf(new ProductId(productId));
        }
        return 1;
    }

    public int changePublicStatus(String productId, boolean publicFlag){
        if(publicFlag){
            productDomainService.setPublic(new ProductId(productId));
        }else{
            productDomainService.cancelPublic(new ProductId(productId));
        }
        return 1;
    }

    public int changeRecommendStatus(String productId, boolean recommendFlag){
        if(recommendFlag){
            productDomainService.setRecommend(new ProductId(productId));
        }else{
            productDomainService.cancelRecommend(new ProductId(productId));
        }
        return 1;
    }


}
