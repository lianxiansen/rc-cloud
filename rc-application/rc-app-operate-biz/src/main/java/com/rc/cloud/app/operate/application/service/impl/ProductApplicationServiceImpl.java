package com.rc.cloud.app.operate.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductRemoveBO;
import com.rc.cloud.app.operate.application.bo.convert.*;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.domain.common.ProductImageTypeEnum;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailService;
import com.rc.cloud.app.operate.domain.model.productdetail.identifier.ProductDetailId;
import com.rc.cloud.app.operate.domain.model.productdetail.valobj.Detail;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictService;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImage;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImageService;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuService;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.app.operate.infrastructure.constants.ProductErrorCodeConstants;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.exception.ServiceException;
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
    private ProductService productService;

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductDictService productDictService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private BrandService brandService;

    @Resource
    private IdRepository idRepository;

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductBO createProduct(ProductSaveDTO productSaveDTO) {


        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();
        if (skus == null || skus.size() <= 0) {
            throw new IllegalArgumentException("sku不能为空");
        }

        ProductId productId =new ProductId(idRepository.nextId());
        productSaveDTO.setId(productId.getId());

        Product product= ProductConvert.convertDomain(productId.id()
                ,productSaveDTO,true,null);

        productService.createProduct(product);
        List<ProductImage> productSizeImages=null;
        List<ProductImage> productMasterImages=null;

        //设置商品图片
        if(productSaveDTO.getSizeAlbums()!=null && productSaveDTO.getSizeAlbums().size()>0){
            for (ProductImageSaveDTO sizeAlbum : productSaveDTO.getSizeAlbums()) {
                sizeAlbum.setId(idRepository.nextId());
            }
            productSizeImages = ProductImageConvert
                    .convertDomainList(productSaveDTO.getSizeAlbums(), productId,ProductImageTypeEnum.SizeImage);
            productImageService.insertProductSizeImageList( productSizeImages);
        }
        if(productSaveDTO.getMasterAlbums()!=null && productSaveDTO.getMasterAlbums().size()>0){
            for (ProductImageSaveDTO  masterImages: productSaveDTO.getMasterAlbums()) {
                masterImages.setId(idRepository.nextId());
            }
            productMasterImages = ProductImageConvert
                    .convertDomainList(productSaveDTO.getMasterAlbums(),productId,ProductImageTypeEnum.MasterImage);
            productImageService.insertProductMasterImageList(productMasterImages);
        }

        //设置字典
        Set<ProductDict> productDicts = ProductDictConvert
                .convertProductDictSet(productId.id(), productSaveDTO.getDicts());
        productDictService.insertProductDict(productDicts);
        //设置详情
        ProductDetail productDetail = ProductDetailConvert.convertDomain(productId,true,
                productSaveDTO.getDetail(),
                productSaveDTO.getInstallVideoUrl(),
                productSaveDTO.getInstallVideoImg(),
                productSaveDTO.getInstallDetail(),null
                );
        productDetailService.createProductDetail(productDetail);

        List<ProductSku> productSkuList=new ArrayList<>();
        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            ProductSkuId productSkuId = new ProductSkuId(idRepository.nextId());
            productSkuSaveDTO.setId(productSkuId.getId());
            ProductSku productSku = ProductSkuConvert.convertDomain(productSkuId, productId
                    , productSkuSaveDTO, true, null);
            productSkuList.add(productSku);
        }
        productSkuService.batchSaveProductSku(productId,productSkuList);
        return ProductConvert.convertProductBO(product,productMasterImages ,productSizeImages,productDicts,productDetail,productSkuList);
    }


    /**
     * 修改商品
     *
     * @param productSaveDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductBO updateProduct(ProductSaveDTO productSaveDTO) {

        ProductId productId = new ProductId(productSaveDTO.getId());
        //修改
        Product product = productService.findProductById(productId);
        if (null == product) {
            throw new ServiceException(ProductErrorCodeConstants.PRODUCT_NOT_EXIST_ERROR);
        }
        product= ProductConvert.convertDomain(productId.id()
                ,productSaveDTO,false,product);

        productService.updateProduct(product);

        List<ProductImage> productSizeImages=null;
        List<ProductImage> productMasterImages=null;
        Set<ProductDict> productDicts=null;
        ProductDetail productDetail=null;
        if(productSaveDTO.getSizeAlbums()!=null){
            for (ProductImageSaveDTO sizeAlbum : productSaveDTO.getSizeAlbums()) {
                if(StringUtils.isEmpty(sizeAlbum.getId())){
                    sizeAlbum.setId(idRepository.nextId());
                }
            }
            productSizeImages = ProductImageConvert
                    .convertDomainList(productSaveDTO.getSizeAlbums(), productId, ProductImageTypeEnum.SizeImage);
            productImageService.updateProductSizeImageList(productId, productSizeImages);
        }
        if(productSaveDTO.getMasterAlbums()!=null){
            for (ProductImageSaveDTO masterImages : productSaveDTO.getMasterAlbums()) {
                if(StringUtils.isEmpty(masterImages.getId())){
                    masterImages.setId(idRepository.nextId());
                }
            }
            productMasterImages = ProductImageConvert
                    .convertDomainList(productSaveDTO.getMasterAlbums(),productId, ProductImageTypeEnum.MasterImage);
            productImageService.updateProductMasterImageList(productId,productMasterImages);
        }
        if(productSaveDTO.getDicts()!=null){
            productDicts = ProductDictConvert.convertProductDictSet(productId.id(), productSaveDTO.getDicts());
            productDictService.updateProductDict(productId,productDicts);
        }
        //设置详情
        productDetail = productDetailService.findProductDetail(new ProductDetailId(productId));
        productDetail = ProductDetailConvert.convertDomain(
                productId, false,
                productSaveDTO.getDetail(),
                productSaveDTO.getInstallVideoUrl(),
                productSaveDTO.getInstallVideoImg(),
                productSaveDTO.getInstallDetail(),productDetail
        );
        productDetailService.updateProductDetail(productDetail);
        List<ProductSku> productSkuList=new ArrayList<>();
        if(productSaveDTO.getSkus()!=null && productSaveDTO.getSkus().size()>0){
            for (ProductSkuSaveDTO productSkuSaveDTO :  productSaveDTO.getSkus()) {
                ProductSku productSku=null;
                if(StringUtils.isNotEmpty(productSkuSaveDTO.getId())){
                    productSku =productSkuService.findProductSkuById(new ProductSkuId(productSkuSaveDTO.getId()));
                }
                if(StringUtils.isNotEmpty(productSkuSaveDTO.getId()) && productSku==null){
                    throw  new ServiceException(ProductErrorCodeConstants.PRODUCT_SKU_NOT_EXIST_ERROR);
                }
                if(productSku==null){
                    productSkuSaveDTO.setId(idRepository.nextId());
                    productSku = ProductSkuConvert.convertDomain(new ProductSkuId(productSkuSaveDTO.getId()), productId
                            , productSkuSaveDTO, true, productSku);
                }else{
                    productSku = ProductSkuConvert.convertDomain(productSku.getId(), productId
                            , productSkuSaveDTO, false, productSku);
                }
                productSkuList.add(productSku);
            }
            productSkuService.batchSaveProductSku(productId,productSkuList);
        }
        ProductQueryDTO query=new ProductQueryDTO();
        query.setProductId(productId.id());
        query.setNeedProductDetail(true);
        query.setNeedProductDict(true);
        query.setNeedProductSku(true);
        query.setNeedProductSizeImage(true);
        query.setNeedProductMasterImage(true);
        return getProduct(query);
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
        productService.deleteProduct(new ProductId(productId));
        productImageService.deleteProductImageByProductId(new ProductId(productId));
        productDictService.deleteProductDictByProductId(new ProductId(productId));
        productSkuService.deleteProductSkuByProductId(new ProductId(productId));
        productDetailService.deleteProductDetail(new ProductDetailId(new ProductId(productId)));
        return true;
    }

    /**
     * 批量移除商品
     * @param productRemoveDTO 传入所需要删除的商品id以及删除的类别，默认类别为软删除
     * @return
     */
    @Override
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
    @Override
    public ProductBO getProduct(ProductQueryDTO productQueryDTO) {
        Product product = productService.findProductById(new ProductId(productQueryDTO.getProductId()));
        ProductDetail productDetail=null;
        Set<ProductDict> productDicts=null;
        List<ProductSku> productSkuList=null;
        List<ProductImage> productSizeImages=null;
        List<ProductImage> productMasterImages=null;
        if(productQueryDTO.isNeedProductMasterImage()){
            productMasterImages = productImageService.getProductMasterImageByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductSizeImage()){
            productSizeImages = productImageService.getProductSizeImageByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductDetail()){
            productDetail = productDetailService.findProductDetail(new ProductDetailId(new ProductId(productQueryDTO.getProductId())));
        }
        if(productQueryDTO.isNeedProductDict()){
            productDicts = productDictService.getProductDictSetByProductId(new ProductId(productQueryDTO.getProductId())).stream().collect(Collectors.toSet());
        }
        if(productQueryDTO.isNeedProductSku()){
            productSkuList = productSkuService.getProductSkuListByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        return ProductConvert.convertProductBO(product,productMasterImages ,productSizeImages,productDicts,productDetail,productSkuList);
    }


    /**
     * 获取商品列表
     *
     * @return
     */
    @Override
    public PageResult<ProductBO> getProductList(ProductListQueryDTO query) {
        PageResult<Product> resultList = productService.getProductPageList(query);
        List<ProductBO> productBOS = new ArrayList<>();
        for (Product product : resultList.getList()) {
            ProductBO productBO = ProductConvert.convertProductBO(product);
            if(query.isNeedBrandName()){
                //查询品牌名
                Brand brand = brandService.findById(product.getBrandId());
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


    @Override
    public int changeNewStatus(String productId, boolean newFlag){
        if(newFlag){
            productService.setNews(new ProductId(productId));
        }else{
            productService.cancelNews(new ProductId(productId));
        }
        return 1;
    }


    @Override
    public int changeOnShelfStatus(String productId, int onShelfStatus){
        if(onShelfStatus== ProductShelfStatusEnum.OnShelf.value){
            productService.onShelf(new ProductId(productId));
        }else  if(onShelfStatus== ProductShelfStatusEnum.OffShelf.value){
            productService.offShelf(new ProductId(productId));
        }
        return 1;
    }

    @Override
    public int changePublicStatus(String productId, boolean publicFlag){
        if(publicFlag){
            productService.setPublic(new ProductId(productId));
        }else{
            productService.cancelPublic(new ProductId(productId));
        }
        return 1;
    }

    @Override
    public int changeRecommendStatus(String productId, boolean recommendFlag){
        if(recommendFlag){
            productService.setRecommend(new ProductId(productId));
        }else{
            productService.cancelRecommend(new ProductId(productId));
        }
        return 1;
    }


}
