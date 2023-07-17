package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDetailConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDictConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductSkuConvert;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.*;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetailRepository;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDictRepository;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuAttribute;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuAttributeId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Inventory;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Price;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SupplyPrice;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Weight;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.domain.IdRepository;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProductService
 * @Author: liandy
 * @Date: 2023/6/23 15:51
 * @Description: TODO
 */
@Service
public class ProductApplicationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ProductDictRepository productDictRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Resource
    private IdRepository idRepository;

    private void validateTenantId(TenantId tenantId) {
        if (!tenantService.exists(tenantId)) {
            throw new IllegalArgumentException("所属租户错误");
        }
    }

    /**
     * 创建商品
     *
     * @param productSaveDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductBO createProduct(ProductSaveDTO productSaveDTO) {


        ProductId productId =new ProductId(idRepository.nextId());
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId());
        Product product= ProductConvert.convert(productId.id()
                ,tenantId.id(),productSaveDTO,true,null);

        if(productSaveDTO.getAlbums()!=null){
            productSaveDTO.getAlbums().forEach(x->
                    x.setId(idRepository.nextId())
                    );
        }
        productRepository.insertProduct(product);
        //设置字典
        List<ProductDict> productDicts = ProductDictConvert.convertList(productId.id(), tenantId.id(), productSaveDTO.getDicts());
        productDictRepository.saveProductDict(productDicts);

        ProductDetail productDetail = ProductDetailConvert.convert(
                productSaveDTO.getDetailId(),
                tenantId.id(),
                productId.id(),
                productSaveDTO.getDetail());
        productDetailRepository.saveProductDetail(productDetail);
        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();
        if (skus == null || skus.size() <= 0) {
            throw new IllegalArgumentException("sku不能为空");
        }
        List<ProductSku> productSkuList=new ArrayList<>();
        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            ProductSkuId productSkuId = new ProductSkuId(idRepository.nextId());
            ProductSku productSku = ProductSkuConvert.convert(productSkuId, productId
                    , tenantId, productSkuSaveDTO, true, null);
            productSkuList.add(productSku);
        }
        productSkuRepository.batchSaveProductSku(productSkuList);
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
        Product product = productRepository.findById(productId);
        if (null == product) {
            throw new IllegalArgumentException("未找到当前商品");
        }
        product= ProductConvert.convert(productId.id()
                ,tenantId.id(),productSaveDTO,false,product);

        productRepository.insertProduct(product);

        List<ProductDict> productDicts=null;
        ProductDetail productDetail=null;
        List<ProductSku> productSkuList=null;

        if(productSaveDTO.getDicts()!=null){
            productDicts = ProductDictConvert.convertList(productId.id(), tenantId.id(), productSaveDTO.getDicts());
            productDictRepository.saveProductDict(productDicts);
        }

        if (StringUtils.isNotEmpty(productSaveDTO.getDetail())) {
             productDetail = ProductDetailConvert.convert(
                    productSaveDTO.getDetailId(),
                    tenantId.id(),
                    productId.id(),
                    productSaveDTO.getDetail());
            productDetailRepository.saveProductDetail(productDetail);
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
                    productSkuRepository.findById(new ProductSkuId(productSkuSaveDTO.getId()));
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
            productSkuRepository.batchSaveProductSku(productSkuList);
        }

        return ProductConvert.convert(product,productDicts,productDetail,productSkuList);
    }

    /**
     * 获取商品
     * TODO
     * @param productQueryDTO
     * @return
     */
    public ProductBO getProduct(ProductQueryDTO productQueryDTO) {
        Product product = productRepository.findById(new ProductId(productQueryDTO.getProductId()));
        ProductDetail productDetail=null;
        List<ProductDict> productDictList=null;
        List<ProductSku> productSkuList=null;
        if(productQueryDTO.isNeedProductDetail()){
            productDetail = productDetailRepository.findById(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductDict()){
            productDictList = productDictRepository.getProductDictByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        if(productQueryDTO.isNeedProductSku()){
            productSkuList = productSkuRepository.getProductSkuListByProductId(new ProductId(productQueryDTO.getProductId()));
        }
        return ProductConvert.convert(product,productDictList,productDetail,productSkuList);
    }


    /**
     * 获取商品列表
     *
     * @return
     */
    public PageResult<ProductBO> getProductList(ProductListQueryDTO productListQueryDTO) {
        PageResult<Product> productPageList = productRepository.getProductPageList(productListQueryDTO);
        List<ProductBO> productBOS = new ArrayList<>();
        for (Product product : productPageList.getList()) {
            ProductBO productBO = ProductConvert.convert(product);
            productBOS.add(productBO);
        }
        PageResult<ProductBO> pageResult = new PageResult<>();
        pageResult.setTotal(productPageList.getTotal());
        pageResult.setList(productBOS);
        return pageResult;
    }


    public ProductBO changeNewStatus(String productId, boolean newFlag){
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setId(productId);
        productSaveDTO.setNewFlag(newFlag);
        return updateProduct(productSaveDTO);
    }

    public ProductBO changeEnableStatus(String productId, boolean enableFlag){
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setId(productId);
        productSaveDTO.setEnableFlag(enableFlag);
        return updateProduct(productSaveDTO);
    }

    public ProductBO changeOnShelfStatus(String productId, int onShelfStatus){
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setId(productId);
        productSaveDTO.setOnShelfStatus(onShelfStatus);
        return updateProduct(productSaveDTO);
    }

    public ProductBO changePublicStatus(String productId, boolean publicFlag){
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setId(productId);
        productSaveDTO.setPublicFlag(publicFlag);
        return updateProduct(productSaveDTO);
    }

    public ProductBO changeRecommendStatus(String productId, boolean recommendFlag){
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setId(productId);
        productSaveDTO.setRecommendFlag(recommendFlag);
        return updateProduct(productSaveDTO);
    }


}
