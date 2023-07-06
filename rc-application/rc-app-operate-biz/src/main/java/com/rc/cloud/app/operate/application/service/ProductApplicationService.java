package com.rc.cloud.app.operate.application.service;

import com.bowen.idgenerator.service.RemoteIdGeneratorService;
import com.rc.cloud.app.operate.application.dto.*;
import com.rc.cloud.app.operate.domain.model.brand.valobj.BrandId;
import com.rc.cloud.app.operate.domain.model.product.*;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
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


    @Resource
    private RemoteIdGeneratorService remoteIdGeneratorService;

    private void validateTenantId(TenantId tenantId){
        if(!tenantService.exists(tenantId)){
            throw new IllegalArgumentException("所属租户错误");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void createProduct(ProductSaveDTO productSaveDTO){

        ProductId productId=new ProductId(remoteIdGeneratorService.getUidByRedis());
        TenantId tenantId = new TenantId(productSaveDTO.getTenantId() + "");
        Name name = new Name(productSaveDTO.getName());
        //定义商品
        Product product= null;
        product =new Product(productId,tenantId,name);
        Remark remark = new Remark(productSaveDTO.getRemark());
        product.setRemark(remark);
        Tag tag = new Tag(productSaveDTO.getTag());
        product.setTag(tag);
        BrandId brandId = new BrandId(productSaveDTO.getBrandId());
        product.setBrandId(brandId);
        CategoryName firstCategory = new CategoryName(productSaveDTO.getFirstCategory());
        CategoryName secondCategory = new CategoryName(productSaveDTO.getSecondCategory());
        CategoryName thirdCategory = new CategoryName(productSaveDTO.getThirdCategory());
        product.setCategory(firstCategory,secondCategory,thirdCategory);
        CustomClassificationId customClassificationId = new CustomClassificationId(productSaveDTO.getCustomClassificationId());
        product.setCustomClassificationId(customClassificationId);
        if(productSaveDTO.getNewFlag()!=null){
            Newest newest = new Newest(productSaveDTO.getNewFlag());
            product.setNewest(newest);
        }
        //是否有爆品图片
        if(productSaveDTO.getExplosivesFlag()!=null){
            Explosives explosives = null;
            if(productSaveDTO.getExplosivesFlag()){
                explosives= new Explosives(productSaveDTO.getExplosivesFlag(),productSaveDTO.getExplosivesImage());
            }else{
                explosives= new Explosives(productSaveDTO.getExplosivesFlag(),null);
            }
            product.setExplosives(explosives);
        }
        //是否推荐
        if(productSaveDTO.getRecommendFlag()!=null){
            Recommend recommend = new Recommend(productSaveDTO.getRecommendFlag());
            product.setRecommend(recommend);
        }
        //是否公开
        if(productSaveDTO.getPublicFlag()!=null){
            Open open = new Open(productSaveDTO.getPublicFlag());
            product.setOpen(open);
        }
        //上架状态
        if(productSaveDTO.getOnShelfStatus()!=null){
            OnshelfStatus onshelfStatus = new OnshelfStatus(productSaveDTO.getOnShelfStatus());
            product.setOnshelfStatus(onshelfStatus);
        }
        //启用状态
        if(productSaveDTO.getEnabledFlag()!=null){
            Enable enable = new Enable(productSaveDTO.getEnabledFlag());
            product.setEnable(enable);
        }
        //视频
        Video video = new Video(productSaveDTO.getVideoUrl(),productSaveDTO.getVideoImg()
                ,productSaveDTO.getInstallVideoUrl(),productSaveDTO.getInstallVideoImg());
        product.setVideo(video);
        //设置相册
        List<ProductImageEntity> productImages = new ArrayList<>();
        productSaveDTO.getAlbums().forEach(item -> {
            ProductImageEntity productImageEntity=new ProductImageEntity();
            productImageEntity.setUrl(item.getUrl());
            productImageEntity.setSort(item.getSort());
            productImages.add(productImageEntity);
        });
        product.setProductImages(productImages);
        //设置字典
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
        productRepository.insertProductEntry(product);
        //保存sku
        List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();
        if(skus==null || skus.size()<=0){
            throw  new IllegalArgumentException("sku不能为空");
        }
        for (ProductSkuSaveDTO productSkuSaveDTO : skus){
            ProductSkuId productSkuId=new ProductSkuId(remoteIdGeneratorService.getUidByRedis());
            ProductSku productSku=new ProductSku(productSkuId,productId,tenantId,new Price(
                    BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getPrice()))
            ));
            if(productSkuSaveDTO.getEnabledFlag()!=null){
                productSku.setEnabledFlag(productSkuSaveDTO.getEnabledFlag());
            }
            if(productSkuSaveDTO.getInventory()!=null){
                productSku.setInventory(new Inventory(productSkuSaveDTO.getInventory()));
            }
            if(productSkuSaveDTO.getSort()!=null){
                productSku.setSort(new Sort(productSkuSaveDTO.getSort()));
            }
            if(productSkuSaveDTO.getWeight()!=null){
                productSku.setWeight(new Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))));
            }
            if(productSkuSaveDTO.getSupplyPrice()!=null){
                productSku.setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice()))));
            }
            //sku图片
            List<ProductSkuImageEntity> productSkuImageEntityList=new ArrayList<>();
            int pos=1;
            for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                ProductSkuImageEntity productSkuImageEntity=new ProductSkuImageEntity();
                productSkuImageEntity.setSort(album.getSort());
                productSkuImageEntity.setUrl(album.getUrl());
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
            productSkuRepository.insertProductSku(productSku);
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductSaveDTO productSaveDTO){

        ProductId productId=new ProductId(productSaveDTO.getId());
        //修改
        Product product = productRepository.findById(productId);;
        if (null==product) {
            throw new IllegalArgumentException("未找到当前商品");
        }
        if(productSaveDTO.getName()!=null){
            Name name = new Name(productSaveDTO.getName());
            product.setName(name);
        }
        if(productSaveDTO.getRemark()!=null){
            Remark remark = new Remark(productSaveDTO.getRemark());
            product.setRemark(remark);
        }
        if(productSaveDTO.getTag()!=null){
            Tag tag = new Tag(productSaveDTO.getTag());
            product.setTag(tag);
        }
        if(productSaveDTO.getBrandId()!=null){
            BrandId brandId = new BrandId(productSaveDTO.getBrandId());
            product.setBrandId(brandId);
        }
        if(productSaveDTO.getFirstCategory()!=null || productSaveDTO.getSecondCategory()!=null
                || productSaveDTO.getThirdCategory()!=null
        ){
            CategoryName firstCategory = new CategoryName(productSaveDTO.getFirstCategory());
            CategoryName secondCategory = new CategoryName(productSaveDTO.getSecondCategory());
            CategoryName thirdCategory = new CategoryName(productSaveDTO.getThirdCategory());
            product.setCategory(firstCategory,secondCategory,thirdCategory);
        }
        if(productSaveDTO.getCustomClassificationId()!=null){
            CustomClassificationId customClassificationId = new CustomClassificationId(productSaveDTO.getCustomClassificationId());
            product.setCustomClassificationId(customClassificationId);
        }
        if(productSaveDTO.getNewFlag()!=null){
            Newest newest = new Newest(productSaveDTO.getNewFlag());
            product.setNewest(newest);
        }
        //是否有爆品图片
        if(productSaveDTO.getExplosivesFlag()!=null){
            Explosives explosives = null;
            if(productSaveDTO.getExplosivesFlag()){
                explosives= new Explosives(productSaveDTO.getExplosivesFlag(),productSaveDTO.getExplosivesImage());
            }else{
                explosives= new Explosives(productSaveDTO.getExplosivesFlag(),null);
            }
            product.setExplosives(explosives);
        }
        //是否推荐
        if(productSaveDTO.getRecommendFlag()!=null){
            Recommend recommend = new Recommend(productSaveDTO.getRecommendFlag());
            product.setRecommend(recommend);
        }
        //是否公开
        if(productSaveDTO.getPublicFlag()!=null){
            Open open = new Open(productSaveDTO.getPublicFlag());
            product.setOpen(open);
        }
        //上架状态
        if(productSaveDTO.getOnShelfStatus()!=null){
            OnshelfStatus onshelfStatus = new OnshelfStatus(productSaveDTO.getOnShelfStatus());
            product.setOnshelfStatus(onshelfStatus);
        }
        //启用状态
        if(productSaveDTO.getEnabledFlag()!=null){
            Enable enable = new Enable(productSaveDTO.getEnabledFlag());
            product.setEnable(enable);
        }
        if(productSaveDTO.getVideoUrl()!=null || productSaveDTO.getVideoImg()!=null
                || productSaveDTO.getInstallVideoUrl()!=null ||productSaveDTO.getInstallVideoImg()!=null){
            Video video = new Video(productSaveDTO.getVideoUrl(),productSaveDTO.getVideoImg()
                    ,productSaveDTO.getInstallVideoUrl(),productSaveDTO.getInstallVideoImg());
            product.setVideo(video);
        }
        if(productSaveDTO.getAlbums()!=null){
            List<ProductImageEntity> productImages = new ArrayList<>();
            productSaveDTO.getAlbums().forEach(item -> {
                ProductImageEntity productImageEntity=new ProductImageEntity();
                productImageEntity.setUrl(item.getUrl());
                productImageEntity.setSort(item.getSort());
                productImages.add(productImageEntity);
            });
            product.setProductImages(productImages);
        }
        if(productSaveDTO.getDicts()!=null){
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
        }
        //设置属性
        if(productSaveDTO.getAttributes()!=null){
            product.clearAttribute();
            for (ProductAttributeSaveDTO attribute : productSaveDTO.getAttributes()) {
                product.addAttribute(attribute.getName(),attribute.getValue(),attribute.getSort());
            }
        }
        //保存spu
        productRepository.updateProductEntry(product);

        if(productSaveDTO.getSkus()!=null){

            //保存sku
            List<ProductSkuSaveDTO> skus = productSaveDTO.getSkus();

            for (ProductSkuSaveDTO productSkuSaveDTO : skus) {
                boolean exist = productSkuRepository.exist(new ProductSkuId(productSkuSaveDTO.getId()));
                ProductSku productSku=null;
                if (!exist) {
                    throw new IllegalArgumentException("skuid有误");
                } else {
                    productSku = productSkuRepository.findById(new ProductSkuId(productSkuSaveDTO.getId()));
                }
                if(productSkuSaveDTO.getPrice()!=null){
                    productSku.setPrice(new Price(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getPrice())) ));
                }
                if(productSkuSaveDTO.getEnabledFlag()!=null){
                    productSku.setEnabledFlag(productSkuSaveDTO.getEnabledFlag());
                }
                if(productSkuSaveDTO.getInventory()!=null){
                    productSku.setInventory(new Inventory(productSkuSaveDTO.getInventory()));
                }
                if(productSkuSaveDTO.getSort()!=null){
                    productSku.setSort(new Sort(productSkuSaveDTO.getSort()));
                }
                if(productSkuSaveDTO.getWeight()!=null){
                    productSku.setWeight(new Weight(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getWeight()))));
                }
                if(productSkuSaveDTO.getSupplyPrice()!=null){
                    productSku.setSupplyPrice(new SupplyPrice(BigDecimal.valueOf(Double.valueOf(productSkuSaveDTO.getSupplyPrice()))));
                }
                //sku图片
                if( productSkuSaveDTO.getAlbums()!=null){
                    List<ProductSkuImageEntity> productSkuImageEntityList=new ArrayList<>();
                    int pos=1;
                    for (ProductSkuImageSaveDTO album : productSkuSaveDTO.getAlbums()) {
                        ProductSkuImageEntity productSkuImageEntity=new ProductSkuImageEntity();
                        productSkuImageEntity.setSort(album.getSort());
                        productSkuImageEntity.setUrl(album.getUrl());
                        pos++;
                        productSkuImageEntityList.add(productSkuImageEntity);
                    }
                    productSku.skuImageList(productSkuImageEntityList);
                }
                //sku属性
                if( productSkuSaveDTO.getAttributes()!=null){
                    for (ProductSkuAttributeSaveDTO attribute : productSkuSaveDTO.getAttributes()) {
                        productSku.addSkuAttribute(attribute.getName(),attribute.getValue(),attribute.getSort());
                    }
                    productSkuRepository.updateProductSku(productSku);
                }

            }
        }



    }
}
