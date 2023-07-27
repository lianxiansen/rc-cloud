package com.rc.cloud.app.operate.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.ProductRemoveBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDetailConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDictConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.application.service.ProductApplicationService;
import com.rc.cloud.app.operate.domain.common.ProductRemoveTypeEnum;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.Brand;
import com.rc.cloud.app.operate.domain.model.brand.BrandDomainService;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductDomainService;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailDomainService;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictDomainService;
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
     * 创建商品字典
     * 创建商品详情
     * 创建商品sku
     *
     * @param productSaveDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductBO createProduct(ProductSaveDTO productSaveDTO) {


        ProductId productId =new ProductId(idRepository.nextId());
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId());

        if(CollectionUtil.isNotEmpty(productSaveDTO.getMasterAlbums())){
            productSaveDTO.getMasterAlbums().forEach(x->
                    x.setId(idRepository.nextId())
            );
        }
        if(CollectionUtil.isNotEmpty(productSaveDTO.getSizeAlbums())){
            productSaveDTO.getSizeAlbums().forEach(x->
                    x.setId(idRepository.nextId())
            );
        }
        if(CollectionUtil.isNotEmpty(productSaveDTO.getAttributes())){
            productSaveDTO.setAttributeId(idRepository.nextId());
        }
        Product product= ProductConvert.convert(productId.id()
                ,tenantId.id(),productSaveDTO,true,null);

        productDomainService.createProduct(product);
        Set<ProductDict> productDicts=null;
        ProductDetail productDetail =null;
        //设置字典
        if(CollectionUtil.isNotEmpty(productSaveDTO.getDicts())){
            productSaveDTO.getDicts().forEach(
                    x->x.setId(idRepository.nextId())
            );
            productDicts = ProductDictConvert.convertProductDictSet(productId.id(), tenantId.id(), productSaveDTO.getDicts());
            productDictDomainService.saveProductDict(productDicts);
        }
        if(StringUtils.isNotEmpty(productSaveDTO.getDetail())){
            productSaveDTO.setDetailId(idRepository.nextId());
            productDetail = ProductDetailConvert.convert(
                    productSaveDTO.getDetailId(),
                    tenantId.id(),
                    productId.id(),
                    productSaveDTO.getDetail());
            productDetailDomainService.saveProductDetail(productDetail);
        }
        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();
        if (skus == null || skus.size() <= 0) {
            throw new IllegalArgumentException("sku不能为空");
        }
        List<ProductSku> productSkuList=new ArrayList<>();
        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            if(CollectionUtil.isNotEmpty(productSkuSaveDTO.getAlbums())){
                productSkuSaveDTO.getAlbums().forEach(
                        x->x.setId(idRepository.nextId())
                );
            }
            productSkuSaveDTO.setAttributeId(idRepository.nextId());
            ProductSkuId productSkuId = new ProductSkuId(idRepository.nextId());
            ProductSku productSku = ProductSkuConvert.convert(productSkuId, productId
                    , tenantId, productSkuSaveDTO, true, null);
            productSkuList.add(productSku);
        }
        productSkuDomainService.batchSaveProductSku(productSkuList);
        return ProductConvert.convert(product,productDicts,productDetail,productSkuList);

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
        product= ProductConvert.convert(productId.id()
                ,tenantId.id(),productSaveDTO,false,product);

        productDomainService.updateProduct(product);

        Set<ProductDict> productDicts=null;
        ProductDetail productDetail=null;
        List<ProductSku> productSkuList=null;

        if(productSaveDTO.getDicts()!=null){
            productDicts = ProductDictConvert.convertProductDictSet(productId.id(), tenantId.id(), productSaveDTO.getDicts());
            productDictDomainService.saveProductDict(productDicts);
        }

        if (StringUtils.isNotEmpty(productSaveDTO.getDetail())) {
             productDetail = ProductDetailConvert.convert(
                    productSaveDTO.getDetailId(),
                    tenantId.id(),
                    productId.id(),
                    productSaveDTO.getDetail());
            productDetailDomainService.saveProductDetail(productDetail);
        }
        if(productSaveDTO.getSkus()!=null && productSaveDTO.getSkus().size()>0){
            productSkuList=new ArrayList<>();
            //这里的规格有可能是原有的基础上新增
            //比如：1 2 3 新增 4 5 6 结果是 1 2 3 4 5 6
            //也有可能是减少 1 2 3 4 减少 3 4 结果是 1 2
            //也可能是重新洗牌 1 2 3 4 结果是 5 6 7 8
            //但是在这一层不需要做这事情，但是需要记录sku_id
            for (ProductSkuSaveDTO productSkuSaveDTO :  productSaveDTO.getSkus()) {
                ProductSku productSku=null;
                if(productSkuSaveDTO.getId()!=null){
                    productSkuDomainService.findProductSkuById(new ProductSkuId(productSkuSaveDTO.getId()));
                }
                if(productSku==null){
                    productSku = ProductSkuConvert.convert(new ProductSkuId(idRepository.nextId()), productId
                            , tenantId, productSkuSaveDTO, true, productSku);
                }else{
                    productSku = ProductSkuConvert.convert(productSku.getId(), productId
                            , tenantId, productSkuSaveDTO, false, productSku);
                }
                productSkuList.add(productSku);
            }
            productSkuDomainService.batchSaveProductSku(productSkuList);
        }

        return ProductConvert.convert(product,productDicts,productDetail,productSkuList);
    }

    /**
     * 移除商品（若是软删除只需要修改产品的是否删除属性即可）
     * 移除商品字典
     * 移除商品详情
     * 移除商品sku
     * @param productId
     * @param removeType
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeProduct(String productId, ProductRemoveTypeEnum removeType){
        if(removeType==ProductRemoveTypeEnum.DELETE){
            productDomainService.deleteProduct(new ProductId(productId));
            productDictDomainService.deleteProductDict(new ProductId(productId));
            productSkuDomainService.deleteProductSku(new ProductId(productId));
            productDetailDomainService.deleteProductDetail(new ProductId(productId));
        }else if(removeType==ProductRemoveTypeEnum.SOFT_DELETE){

            productDomainService.softDeleteProduct(new ProductId(productId));
        }else{
            throw new IllegalArgumentException("removeType is not null");
        }
        return true;
    }

    /**
     * 批量移除商品
     * @param productRemoveDTO 传入所需要删除的商品id以及删除的类别，默认类别为软删除
     * @return
     */
    public ProductRemoveBO removeProductBatch(ProductRemoveDTO productRemoveDTO){
        ProductRemoveBO bo=new ProductRemoveBO();
        List<String> needRemoveList=new ArrayList<>();
        List<String> successList=new ArrayList<>();
        List<Pair<String,String>> failList= new ArrayList<>();
        if(CollectionUtil.isNotEmpty(productRemoveDTO.getProductIds())){
            for (String id : productRemoveDTO.getProductIds()) {
                needRemoveList.add(id);
                try{
                    boolean success= removeProduct(id,productRemoveDTO.getRemoveType());
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
        Set<ProductDict> productDictList=null;
        List<ProductSku> productSkuList=null;
        if(productQueryDTO.isNeedProductDetail()){
            productDetail = productDetailDomainService.findProductDetailById(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductDict()){
            productDictList = productDictDomainService.getProductDictSetByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductSku()){
            productSkuList = productSkuDomainService.getProductSkuListByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        return ProductConvert.convert(product,productDictList,productDetail,productSkuList);
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
            ProductBO productBO = ProductConvert.convert(product);
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
