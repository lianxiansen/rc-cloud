package com.rc.cloud.app.operate.infrastructure.persistence.convert;

import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductAttributePO;
import com.rc.cloud.app.operate.infrastructure.persistence.po.ProductPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductConvert {


    ProductConvert INSTANCE = Mappers.getMapper(ProductConvert.class);
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")



    Product convert(ProductPO a);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name.value")

//        ProductId productId=new ProductId(productPO.getId());
//        TenantId tenantId = new TenantId(productPO.getTenantId());
//        Product product=new Product(productId,tenantId,new Name(productPO.getName()));
//        //设置相册
//        List<ProductImage> urls = getProductImageByProductId(productId);
//        product.setProductImages(urls);
//
//        product.setId(productId);
//
//        Remark remark = new Remark(productPO.getRemark());
//        Tag tag = new Tag(productPO.getTag());
//        BrandId brandId = new BrandId(productPO.getBrandId());
//        CategoryName firstCategory = new CategoryName(productPO.getFirstCategory());
//        CategoryName secondCategory = new CategoryName(productPO.getSecondCategory());
//        CategoryName thirdCategory = new CategoryName(productPO.getThirdCategory());
//        CustomClassificationId customClassificationId = new CustomClassificationId(productPO.getCustomClassificationId());
//        Newest newest = new Newest(productPO.getNewFlag());
//        Explosives explosives = null;
//        if(productPO.getExplosivesFlag()){
//            explosives= new Explosives(productPO.getExplosivesFlag(), productPO.getExplosivesImage());
//        }
//
//        Recommend recommend = new Recommend(productPO.getRecommendFlag());
//        Open open = new Open(productPO.getPublicFlag());
//        OnshelfStatus onshelfStatus = new OnshelfStatus(productPO.getOnshelfStatus());
//        Enable enable = new Enable(productPO.getEnabledFlag());
//        Video video = new Video(productPO.getVideoUrl(), productPO.getVideoImg()
//                , productPO.getInstallVideoUrl(), productPO.getInstallVideoImg());
//
//        product.setRemark(remark);
//        product.setTag(tag);
//        product.setBrandId(brandId);
//        product.setCategory(firstCategory,secondCategory,thirdCategory);
//        product.setCustomClassificationId(customClassificationId);
//        product.setNewest(newest);
//        product.setExplosives(explosives);
//        product.setRecommend(recommend);
//        product.setOpen(open);
//        product.setOnshelfStatus(onshelfStatus);
//        product.setEnable(enable);
//        product.setVideo(video);
//
//        //设置字典
//        product.setProductDict(getProductDictByProductId(productId));

    ProductPO convert(Product a);

    List<Product> convertList(List<ProductPO> list);

//    public static ProductPO convert2ProductDO(Product product) {
//        ProductPO target=new ProductPO();
//
//        target.setId(product.getId().id());
//        target.setTenantId(product.getTenantId().id());
//        if(product.getEnable()!=null){
//            target.setEnabledFlag(product.getEnable().result());
//        }
//        if(product.getName()!=null){
//            target.setName(product.getName().getValue());
//        }
//        if(product.getRemark()!=null){
//            target.setRemark(product.getRemark().getValue());
//        }
//        if(product.getTag()!=null){
//            target.setTag(product.getTag().getValue());
//        }
//        if(product.getBrandId()!=null){
//            target.setBrandId(product.getBrandId().id());
//        }
//        //类别
//        if(product.getFirstCategory()!=null){
//            target.setFirstCategory(product.getFirstCategory().getValue());
//        }
//        if(product.getSecondCategory()!=null){
//            target.setSecondCategory(product.getSecondCategory().getValue());
//        }
//        if(product.getThirdCategory()!=null){
//            target.setThirdCategory(product.getThirdCategory().getValue());
//        }
//        //自定义分类
//        if(product.getCustomClassificationId()!=null){
//            target.setCustomClassificationId(product.getCustomClassificationId().id());
//        }
//        if(product.getNewest()!=null){
//            target.setNewFlag(product.getNewest().getValue());
//        }
//        if(product.getRecommend()!=null){
//            target.setRecommendFlag(product.getRecommend().getValue());
//        }
//        if(product.getExplosives()!=null){
//            target.setExplosivesFlag(product.getExplosives().isFlag());
//            target.setExplosivesImage(product.getExplosives().getImage());
//        }
//        if(product.getOpen()!=null){
//            target.setPublicFlag(product.getOpen().getValue());
//        }
//        //上架状态
//        if(product.getOnshelfStatus()!=null){
//            target.setOnshelfStatus(product.getOnshelfStatus().getValue());
//        }
//        //启用状态
//        if(product.getEnable()!=null){
//            target.setEnabledFlag(product.getEnable().result());
//        }
//        if(product.getVideo()!=null){
//            target.setVideoImg(product.getVideo().getVideoImg());
//            target.setVideoUrl(product.getVideo().getVideoUrl());
//            target.setInstallVideoImg(product.getVideo().getInstallVideoImg());
//            target.setInstallVideoUrl(product.getVideo().getInstallVideoUrl());
//        }
//        return target;
//    }
//
//    public static ProductDetailPO convert2ProductDetailDO(String detailId, String productId, String tenantId, String detail){
//        ProductDetailPO productDetailPO =new ProductDetailPO();
//        productDetailPO.setId(detailId);
//        productDetailPO.setDetail(detail);
//        productDetailPO.setProductId(productId);
//        productDetailPO.setTenantId(tenantId);
//        return productDetailPO;
//    }
//
//    public static ProductAttributePO convert2ProductAttributeDO(String productId, String tenantId, ProductAttribute productAttribute){
//        ProductAttributePO productAttributePO =new ProductAttributePO();
//        String attr = JSON.toJSONString(productAttribute.getAttributes());
//        productAttributePO.setId(productAttribute.getId());
//        productAttributePO.setProductId(productId);
//        productAttributePO.setTenantId(tenantId);
//        productAttributePO.setContent(attr);
//        return productAttributePO;
//    }
//
//
//    public static List<ProductImagePO> convert2ProductImageDO(String productId, String tenantId, List<ProductImage> productImageList){
//        List<ProductImagePO> resList =new ArrayList<>();
//        if(productImageList !=null){
//            for (ProductImage productImage : productImageList) {
//                ProductImagePO productImagePO =new ProductImagePO();
//                productImagePO.setId(productImage.getId());
//                productImagePO.setProductId(productId);
//                productImagePO.setUrl(productImage.getUrl());
//                productImagePO.setTenantId(tenantId);
//                productImagePO.setSortId(productImage.getSort());
//                resList.add(productImagePO);
//            }
//        }
//
//        return resList;
//    }
//
//
//    public static List<ProductDictPO> convert2ProductDictDO(String productId, String tenantId, List<ProductDict> productDictList){
//        List<ProductDictPO> resList =new ArrayList<>();
//        if(productDictList !=null){
//            for (ProductDict productDict : productDictList) {
//                ProductDictPO productDictPO =new ProductDictPO();
//                productDictPO.setId(productDict.getId());
//                productDictPO.setProductId(productId);
//                productDictPO.setValue(productDict.getValue());
//                productDictPO.setTenantId(tenantId);
//                productDictPO.setKey(productDict.getKey());
//                productDictPO.setSortId(productDict.getSort());
//                resList.add(productDictPO);
//            }
//        }
//        return resList;
//    }
}
