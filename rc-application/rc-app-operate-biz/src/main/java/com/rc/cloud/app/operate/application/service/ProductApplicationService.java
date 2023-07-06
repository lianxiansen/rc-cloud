package com.rc.cloud.app.operate.application.service;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.domain.common.OperateActionEnum;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.*;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImageEntity;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuRepository;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Inventory;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Price;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.SupplyPrice;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Weight;
import com.rc.cloud.app.operate.domain.model.tenant.service.TenantService;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RemoteIdGeneratorService remoteIdGeneratorService;

    private void validateTenantId(TenantId tenantId){
        if(!tenantService.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void createProduct(ProductSaveDTO productSaveDTO){

        TenantId tenantId = new TenantId(productSaveDTO.getTenantId() + "");
        Name name = new Name(productSaveDTO.getName());
        Remark remark = new Remark(productSaveDTO.getRemark());
        Tag tag = new Tag(productSaveDTO.getTag());
        BrandId brandId = new BrandId(productSaveDTO.getBrandId());
        CategoryName firstCategory = new CategoryName(productSaveDTO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productSaveDTO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productSaveDTO.getThirdCategory());
        CustomClassification customClassification = new CustomClassification(productSaveDTO.getCustomClassificationId());
        Newest newest = new Newest(productSaveDTO.getNewFlag());
        Explosives explosives = null;
        if(productSaveDTO.isExplosivesFlag()){
            explosives= new Explosives(productSaveDTO.isExplosivesFlag(),productSaveDTO.getExplosivesImage());
        }

        Recommend recommend = new Recommend(productSaveDTO.isRecommendFlag());
        Open open = new Open(productSaveDTO.isPublicFlag());
        OnshelfStatus onshelfStatus = new OnshelfStatus(productSaveDTO.getOnShelfStatus());
        Enable enable = new Enable(productSaveDTO.isEnabledFlag());
        Video video = new Video(productSaveDTO.getVideoUrl(),productSaveDTO.getVideoImg()
                ,productSaveDTO.getInstallVideoUrl(),productSaveDTO.getInstallVideoImg());
        //设置相册
        List<ProductImageEntity> productImages = new ArrayList<>();
        productSaveDTO.getAlbums().forEach(item -> {
            ProductImageEntity productImageEntity=new ProductImageEntity();
            productImageEntity.setUrl(item.getUrl());
            productImageEntity.setSort(item.getSort());
            productImages.add(productImageEntity);
        });



        product.setRemark(remark);
        product.setTag(tag);
        product.setBrandId(brandId);
        product.setCategory(firstCategory,secondCategory,thirdCategory);
        product.setCustomClassification(customClassification);
        product.setNewest(newest);
        product.setExplosives(explosives);
        product.setRecommend(recommend);
        product.setOpen(open);
        product.setOnshelfStatus(onshelfStatus);
        product.setEnable(enable);
        product.setVideo(video);

        List<ProductDictEntity> productDictEntities = new ArrayList<>();
        if(productSaveDTO.getDicts()!=null){
            for (ProductDictSaveDTO dict : productSaveDTO.getDicts()) {
                ProductDictEntity entity=new ProductDictEntity();
                entity.setKey(dict.getKey());
                entity.setValue(dict.getValue());
                entity.setSortId(dict.getSort());
                productDictEntities.add(entity);
            }
        }
        //设置字典
        product.setProductDict(productDictEntities);

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
        for (ProductAttributeSaveDTO attribute : productSaveDTO.getAttributes()) {
            product.addAttribute(attribute.getName(),attribute.getValue(),attribute.getSort());
        }
        //保存spu
        productRepository.saveProductEntry(product);
        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();

        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            boolean exist2 = productSkuRepository.exist(new ProductSkuId(productSkuSaveDTO.getId()));
            ProductSku productSku=null;
            if (!exist2) {
                long id= remoteIdGeneratorService.getUidByLocal().longValue();
                productSku=new ProductSku(new ProductSkuId(String.valueOf(id)),productId,tenantId,new Price(BigDecimal.ZERO));
            } else {
                productSku = productSkuRepository.findById(new ProductSkuId(productSkuSaveDTO.getId()));
            }
            productSku.enabledFlag(productSkuSaveDTO.isEnabledFlag())
                    .inventory(new Inventory(productSkuSaveDTO.getInventory()))
                    .sort(new Sort(productSkuSaveDTO.getSort()))
                    .weight(new Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))))
                    .setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice())))

                    );
            //sku图片
            List<ProductSkuImageEntity> productSkuImageEntityList=new ArrayList<>();
            int pos=1;
            for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                ProductSkuImageEntity productSkuImageEntity=new ProductSkuImageEntity();
                productSkuImageEntity.setSort(album.getSort());
                productSkuImageEntity.setUrl(album.getUrl());
                productSkuImageEntity.setDefaultFlag(pos==1?true:false);
                pos++;
                productSkuImageEntityList.add(productSkuImageEntity);
            }
            productSku.skuImageList(productSkuImageEntityList);

            //设置sku 属性
            /**
             * "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}]
             */
            for (ProductSkuAttributeSaveDTO attribute : productSkuSaveDTO.getAttributes()) {
                productSku.addSkuAttribute(attribute.getName(),attribute.getValue(),attribute.getSort());
            }
            productSkuRepository.saveProductSku(productSku);
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductSaveDTO productSaveDTO){

        ProductId productId=new ProductId(productSaveDTO.getId());
        //修改
        boolean exist = productRepository.exist(new ProductId(productSaveDTO.getId()));
        Product product= null;
        if (!exist) {
            throw new IllegalArgumentException("未找到当前商品");
        } else {
            product = productRepository.findById(productId);
        }
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId() + "");
        Name name = new Name(productSaveDTO.getName());
        Remark remark = new Remark(productSaveDTO.getRemark());
        Tag tag = new Tag(productSaveDTO.getTag());
        BrandId brandId = new BrandId(productSaveDTO.getBrandId());
        CategoryName firstCategory = new CategoryName(productSaveDTO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productSaveDTO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productSaveDTO.getThirdCategory());
        CustomClassification customClassification = new CustomClassification(productSaveDTO.getCustomClassificationId());
        Newest newest = new Newest(productSaveDTO.isNewFlag());
        Explosives explosives = null;
        if(productSaveDTO.isExplosivesFlag()){
            explosives= new Explosives(productSaveDTO.isExplosivesFlag(),productSaveDTO.getExplosivesImage());
        }

        Recommend recommend = new Recommend(productSaveDTO.isRecommendFlag());
        Open open = new Open(productSaveDTO.isPublicFlag());
        OnshelfStatus onshelfStatus = new OnshelfStatus(productSaveDTO.getOnShelfStatus());
        Enable enable = new Enable(productSaveDTO.isEnabledFlag());
        Video video = new Video(productSaveDTO.getVideoUrl(),productSaveDTO.getVideoImg()
                ,productSaveDTO.getInstallVideoUrl(),productSaveDTO.getInstallVideoImg());
        //设置相册
        List<ProductImageEntity> productImages = new ArrayList<>();
        productSaveDTO.getAlbums().forEach(item -> {
            ProductImageEntity productImageEntity=new ProductImageEntity();
            productImageEntity.setUrl(item.getUrl());
            productImageEntity.setSort(item.getSort());
            productImages.add(productImageEntity);
        });



        product.setRemark(remark);
        product.setTag(tag);
        product.setBrandId(brandId);
        product.setCategory(firstCategory,secondCategory,thirdCategory);
        product.setCustomClassification(customClassification);
        product.setNewest(newest);
        product.setExplosives(explosives);
        product.setRecommend(recommend);
        product.setOpen(open);
        product.setOnshelfStatus(onshelfStatus);
        product.setEnable(enable);
        product.setVideo(video);

        List<ProductDictEntity> productDictEntities = new ArrayList<>();
        if(productSaveDTO.getDicts()!=null){
            for (ProductDictSaveDTO dict : productSaveDTO.getDicts()) {
                ProductDictEntity entity=new ProductDictEntity();
                entity.setKey(dict.getKey());
                entity.setValue(dict.getValue());
                entity.setSortId(dict.getSort());
                productDictEntities.add(entity);
            }
        }
        //设置字典
        product.setProductDict(productDictEntities);

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
        for (ProductAttributeSaveDTO attribute : productSaveDTO.getAttributes()) {
            product.addAttribute(attribute.getName(),attribute.getValue(),attribute.getSort());
        }
        //保存spu
        productRepository.saveProductEntry(product);
        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();

        for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
            boolean exist2 = productSkuRepository.exist(new ProductSkuId(productSkuSaveDTO.getId()));
            ProductSku productSku=null;
            if (!exist2) {
                long id= remoteIdGeneratorService.getUidByLocal().longValue();
                productSku=new ProductSku(new ProductSkuId(String.valueOf(id)),productId,tenantId,new Price(BigDecimal.ZERO));
            } else {
                productSku = productSkuRepository.findById(new ProductSkuId(productSkuSaveDTO.getId()));
            }
            productSku.enabledFlag(productSkuSaveDTO.isEnabledFlag())
                    .inventory(new Inventory(productSkuSaveDTO.getInventory()))
                    .sort(new Sort(productSkuSaveDTO.getSort()))
                    .weight(new Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))))
                    .setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice())))

                    );
            //sku图片
            List<ProductSkuImageEntity> productSkuImageEntityList=new ArrayList<>();
            int pos=1;
            for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                ProductSkuImageEntity productSkuImageEntity=new ProductSkuImageEntity();
                productSkuImageEntity.setSort(album.getSort());
                productSkuImageEntity.setUrl(album.getUrl());
                productSkuImageEntity.setDefaultFlag(pos==1?true:false);
                pos++;
                productSkuImageEntityList.add(productSkuImageEntity);
            }
            productSku.skuImageList(productSkuImageEntityList);

            //设置sku 属性
            /**
             * "attributes":[{"name":"颜色","value":"红","sort":9},{"name":"尺寸","value":"X","sort":9}]
             */
            for (ProductSkuAttributeSaveDTO attribute : productSkuSaveDTO.getAttributes()) {
                productSku.addSkuAttribute(attribute.getName(),attribute.getValue(),attribute.getSort());
            }
            productSkuRepository.saveProductSku(productSku);
        }
    }
}
