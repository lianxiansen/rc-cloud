package com.rc.cloud.app.operate.application.service;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.bo.convert.ProductConvert;
import com.rc.cloud.app.operate.application.bo.convert.ProductDictConvert;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.ProductRepository;
import com.rc.cloud.app.operate.domain.model.product.identifier.*;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
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


        ProductId productId =new ProductId(productRepository.nextId().id());
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId());
        Product product= ProductConvert.convert(productId.id()
                ,tenantId.id(),productSaveDTO,true,null);
        //设置字典
        List<ProductDict> productDicts = ProductDictConvert.convertList(productId.id(), tenantId.id(), productSaveDTO.getDicts());
        product.setProductDict(productDicts);

        //设置属性
        /**
         * "attributes":[
         *     {"name":"颜色","value":"红","sort":9},
         *     {"name":"颜色","value":"黄","sort":9},
         *     {"name":"颜色","value":"蓝","sort":9},
         *     {"name":"尺寸","value":"X","sort":9},
         *     {"name":"尺寸","value":"XL","sort":9}
         * ]
         */
        ProductAttribute productAttribute = null;//TODO
        for (ProductAttributeSaveDTO attribute : productSaveDTO.getAttributes()) {
            //productAttribute.addAttribute(null), attribute.getValue(), attribute.getSort());

            product.setProductAttributeEntity(productAttribute);
        }
        if (StringUtils.isNotEmpty(productSaveDTO.getDetail())) {
            Detail detail = new Detail(productDetailRepository.nextId(), productSaveDTO.getDetail());
            product.setDetail(detail);
        }
        //保存spu
        productRepository.insertProductEntity(product);
        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();
        if (skus == null || skus.size() <= 0) {
            throw new IllegalArgumentException("sku不能为空");
        }
        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            ProductSkuId productSkuId = productSkuRepository.nextId();
            ProductSku productSku = new ProductSku(productSkuId, productId, tenantId, new Price(
                    productSkuSaveDTO.getPrice()
            ));
            if (productSkuSaveDTO.getEnabledFlag() != null) {
                productSku.setEnabledFlag(productSkuSaveDTO.getEnabledFlag());
            }
            if (productSkuSaveDTO.getInventory() != null) {
                productSku.setInventory(new Inventory(productSkuSaveDTO.getInventory()));
            }
            if (productSkuSaveDTO.getSort() != null) {
                productSku.setSort(new Sort(productSkuSaveDTO.getSort()));
            }
            if (productSkuSaveDTO.getWeight() != null) {
                productSku.setWeight(new Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))));
            }
            if (productSkuSaveDTO.getSupplyPrice() != null) {
                productSku.setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice()))));
            }
            //sku图片
            List<ProductSkuImage> productSkuImageList = new ArrayList<>();
            int pos = 1;
            for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                ProductSkuImage productSkuImage = new ProductSkuImage(new ProductSkuImageId(idRepository.nextId()));
                productSkuImage.setSort(album.getSort());
                productSkuImage.setUrl(album.getUrl());
                pos++;
                productSkuImageList.add(productSkuImage);
            }
            productSku.skuImageList(productSkuImageList);

            //设置sku 属性
            /**
             * "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}]
             */
//            ProductSkuAttribute productSkuAttribute = new ProductSkuAttribute(
//                    new ProductSkuAttributeId(idRepository.nextId()),
//                    productSkuId, tenantId
//            );
            for (ProductSkuAttributeSaveDTO attr : productSkuSaveDTO.getAttributes()) {
               // productSkuAttribute.addSkuAttribute(attr.getName(), attr.getValue(), attr.getSort());
            }
           // productSku.setProductSkuAttributeEntity(productSkuAttribute);
            productSkuRepository.insertProductSku(productSku);
        }
        return null;

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


        if (productSaveDTO.getAlbums() != null) {
            List<ProductImage> productImages = new ArrayList<>();
            productSaveDTO.getAlbums().forEach(item -> {
                ProductImage ProductImage = new ProductImage(idRepository.nextId());
                ProductImage.setUrl(item.getUrl());
                ProductImage.setSort(item.getSort());
                productImages.add(ProductImage);
            });
            product.setProductImages(productImages);
        }
        if (productSaveDTO.getDicts() != null) {
            List<ProductDict> productDictEntities = new ArrayList<>();
            if (productSaveDTO.getDicts() != null) {
                for (ProductDictSaveDTO dict : productSaveDTO.getDicts()) {
                    ProductDict entity = new ProductDict(idRepository.nextId());
                    entity.setKey(dict.getKey());
                    entity.setValue(dict.getValue());
                    entity.setSort(dict.getSort());
                    productDictEntities.add(entity);
                }
            }
            //设置字典
            product.setProductDict(productDictEntities);
        }
        //设置属性
        if (productSaveDTO.getAttributes() != null) {
            ProductAttribute productAttribute = null;//TODO
            for (ProductAttributeSaveDTO attribute : productSaveDTO.getAttributes()) {
            //    productAttribute.addAttribute(new ProductAttributeId(attribute.getName()), attribute.getValue(), attribute.getSort());

                product.setProductAttributeEntity(productAttribute);
            }

            if (StringUtils.isNotEmpty(productSaveDTO.getDetail())) {
                Detail detail = new Detail(productDetailRepository.nextId(), productSaveDTO.getDetail());
                product.setDetail(detail);
            }

            //保存spu
            productRepository.updateProductEntity(product);

            if (productSaveDTO.getSkus() != null) {

                //保存sku
                List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();

                for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
                    boolean exist = productSkuRepository.exist(new ProductSkuId(productSkuSaveDTO.getId()));
                    ProductSku productSku = null;
                    if (!exist) {
                        throw new IllegalArgumentException("skuid有误");
                    } else {
                        productSku = productSkuRepository.findById(new ProductSkuId(productSkuSaveDTO.getId()));
                    }
                    if (productSkuSaveDTO.getPrice() != null) {
                        productSku.setPrice(new Price(productSkuSaveDTO.getPrice()));
                    }
                    if (productSkuSaveDTO.getEnabledFlag() != null) {
                        productSku.setEnabledFlag(productSkuSaveDTO.getEnabledFlag());
                    }
                    if (productSkuSaveDTO.getInventory() != null) {
                        productSku.setInventory(new Inventory(productSkuSaveDTO.getInventory()));
                    }
                    if (productSkuSaveDTO.getSort() != null) {
                        productSku.setSort(new Sort(productSkuSaveDTO.getSort()));
                    }
                    if (productSkuSaveDTO.getWeight() != null) {
                        productSku.setWeight(new Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))));
                    }
                    if (productSkuSaveDTO.getSupplyPrice() != null) {
                        productSku.setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice()))));
                    }
                    //sku图片
                    if (productSkuSaveDTO.getAlbums() != null) {
                        List<ProductSkuImage> productSkuImageList = new ArrayList<>();
                        int pos = 1;
                        for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                            ProductSkuImage productSkuImage = new ProductSkuImage(new ProductSkuImageId(idRepository.nextId()));
                            productSkuImage.setSort(album.getSort());
                            productSkuImage.setUrl(album.getUrl());
                            pos++;
                            productSkuImageList.add(productSkuImage);
                        }
                        productSku.skuImageList(productSkuImageList);
                    }
                    //sku属性
                    if (productSkuSaveDTO.getAttributes() != null) {
                        ProductSkuAttribute productSkuAttribute = null;//TODO
                        for (ProductSkuAttributeSaveDTO attribute : productSkuSaveDTO.getAttributes()) {
                            productSkuAttribute.addSkuAttribute(attribute.getName(), attribute.getValue(), attribute.getSort());
                        }
                        productSku.setProductSkuAttributeEntity(productSkuAttribute);
                        productSkuRepository.updateProductSku(productSku);
                    }

                }
            }


        }
        return null;
    }

    /**
     * 获取商品
     *
     * @param productId
     * @return
     */
    public ProductBO getProduct(String productId) {
        Product product = productRepository.findById(new ProductId(productId));
        return ProductConvert.convert(product);
    }


    /**
     * 获取商品列表
     *
     * @return
     */
    public PageResult<ProductBO> getProductList(ProductListQueryDTO productListQueryDTO) {
        PageResult<Product> productPageList = productRepository.getProductPageList(productListQueryDTO);
        List<ProductBO> productBOS = ProductConvert.convertList(productPageList.getList());
        PageResult<ProductBO> pageResult = new PageResult<>();
        pageResult.setTotal(productPageList.getTotal());
        pageResult.setList(productBOS);
        return pageResult;
    }


    public ProductBO modifyProductField(ProductModifyDTO productModifyDTO) {

        if (productModifyDTO.getModifyValue() == null) {
            throw new IllegalArgumentException("修改属性不能为空");
        }
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setId(productModifyDTO.getProductId());
        String modifyValue = productModifyDTO.getModifyValue();
        if (modifyValue.equals(ProductModifyDTO.NEW)) {

            productSaveDTO.setNewFlag(productModifyDTO.getModifyValue() == "1" ? true : false);
        } else if (modifyValue.equals(ProductModifyDTO.ENABLED)) {

            productSaveDTO.setEnabledFlag(productModifyDTO.getModifyValue() == "1" ? true : false);
        } else if (modifyValue.equals(ProductModifyDTO.ONSHELF)) {

            productSaveDTO.setOnShelfStatus(Integer.valueOf(productModifyDTO.getModifyValue()));
        } else if (modifyValue.equals(ProductModifyDTO.PUBLIC)) {

            productSaveDTO.setPublicFlag(productModifyDTO.getModifyValue() == "1" ? true : false);
        } else if (modifyValue.equals(ProductModifyDTO.RECOMMEND)) {

            productSaveDTO.setRecommendFlag(productModifyDTO.getModifyValue() == "1" ? true : false);
        } else {
            throw new IllegalArgumentException("修改属性不存在");
        }

        return updateProduct(productSaveDTO);


    }

}
