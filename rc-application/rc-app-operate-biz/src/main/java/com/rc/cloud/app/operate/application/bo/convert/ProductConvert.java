package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.application.dto.ProductAttributeSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductImageSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.product.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductAttributeId;
import com.rc.cloud.app.operate.domain.model.product.valobj.OnshelfStatus;
import com.rc.cloud.app.operate.domain.model.product.valobj.Enable;
import com.rc.cloud.app.operate.domain.model.product.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.app.operate.domain.model.tenant.valobj.TenantId;
import com.rc.cloud.common.core.exception.ApplicationException;
import com.rc.cloud.common.core.util.StringUtils;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert
{



    public static ProductBO convert(Product product){

        return null;

    }

    /**
     * 填充product参数
     * 如果product为空，表示新增dto传入数据为空就要判断是否合法
     * 如果product不为空，可能是部分属性修改，dto传入数据可以为空
     * @param productId
     * @param tenantId
     * @param productSaveDTO
     * @param isCreate
     * @param product
     * @return
     */
    public static Product convert(String productId, String tenantId,
                                  ProductSaveDTO productSaveDTO,boolean isCreate, Product product){
        if(isCreate && product!=null){
            throw new IllegalArgumentException("参数错误，内部错误");
        }
        if(isCreate){
            product = new Product(
                    new ProductId(productId)
                    , new TenantId(tenantId)
                    , new Name(productSaveDTO.getName()));
        }
        //设置商品名
        product = setName(productSaveDTO.getName(),isCreate,product);
        //商品标签
        product = setRemark(productSaveDTO.getRemark(),isCreate,product);
        //商品tag
        product = setTag(productSaveDTO.getTag(),isCreate,product);
        //商品品牌id
        product = setBrandId(productSaveDTO.getBrandId(),isCreate,product);
        //商品分类
        product = setCategory(productSaveDTO.getFirstCategory()
                ,productSaveDTO.getSecondCategory()
                ,productSaveDTO.getThirdCategory()
                ,isCreate,product);
        //自定义分类
        product = setCustomClassificationId(productSaveDTO.getCustomClassificationId(),isCreate,product);
        //设置Newest
        product = setNewest(productSaveDTO.getNewFlag(),isCreate,product);
        //设置爆品
        product = setExplosives(productSaveDTO.getExplosivesFlag(),productSaveDTO.getExplosivesImage(),isCreate,product);
        //设置公开
        product = setPublic(productSaveDTO.getPublicFlag(),isCreate,product);
        //设置Enabled
        product = setEnable(productSaveDTO.getEnabledFlag(),isCreate,product);
        //设置OnShelfStatus
        product = setOnShelfStatus(productSaveDTO.getOnShelfStatus(),isCreate,product);
        //设置Recommend
        product = setRecommend(productSaveDTO.getRecommendFlag(),isCreate,product);
        //设置Video
        product = setVideo(productSaveDTO.getVideoUrl()
                ,productSaveDTO.getVideoImg()
                ,productSaveDTO.getInstallVideoUrl()
                ,productSaveDTO.getInstallVideoImg()
                ,isCreate,product);
        //设置图片
        product= setProductImage(productSaveDTO,isCreate,product);
        //设置属性
        product =setProductAttibute(productSaveDTO,isCreate,product);
        return product;
    }




    private static Product setName(String name, boolean isCreate, Product product){
        //商品名字
        if(isCreate){
            product.setName(new Name(name));
        }else{
            if (name != null) {
                product.setName(new Name(name));
            }
        }
        return product;
    }

    private static Product setRemark(String remark, boolean isCreate, Product product){
        //商品描述
        if(isCreate){
            product.setRemark(new Remark(remark));
        }else{
            if (remark != null) {
                product.setRemark(new Remark(remark));
            }
        }
        return product;
    }


    private static Product setTag(String tag, boolean isCreate, Product product){
        //商品tag
        if(isCreate){
            product.setTag(new Tag(tag));
        }else{
            if (tag != null) {
                product.setTag(new Tag(tag));
            }
        }
        return product;
    }

    private static Product setBrandId(String brandId, boolean isCreate, Product product){
        //商品描述
        if(isCreate){
            product.setBrandId(new BrandId(brandId));
        }else{
            if (brandId != null) {
                product.setBrandId(new BrandId(brandId));
            }
        }
        return product;
    }

    private static Product setCategory(String firstCategory, String secondCategory,String thirdCategory, boolean isCreate, Product product){
        CategoryName firstCategory2 = new CategoryName(firstCategory);
        CategoryName secondCategory2 = new CategoryName(secondCategory);
        CategoryName thirdCategory2 = new CategoryName(thirdCategory);
        //商品描述
        if(isCreate){
            product.setCategory(firstCategory2, secondCategory2, thirdCategory2);
        }else{
            if (firstCategory != null || secondCategory != null
                    || thirdCategory != null
            ) {
                product.setCategory(firstCategory2, secondCategory2, thirdCategory2);
            }
        }
        return product;
    }

    private static Product setCustomClassificationId(String customClassificationId, boolean isCreate, Product product){
        if(isCreate){
            product.setCustomClassificationId(new CustomClassificationId(customClassificationId));
        }else{
            if (customClassificationId != null) {
                product.setCustomClassificationId(new CustomClassificationId(customClassificationId));
            }
        }
        return product;
    }

    private static Product setNewest(Boolean newFlag, boolean isCreate, Product product){
        if(isCreate){
            product.setNewest(new Newest(newFlag));
        }else{
            if (newFlag != null) {
                Newest newest = new Newest(newFlag);
                product.setNewest(newest);
            }

        }
        return product;
    }

    private static Product setExplosives(Boolean explosivesFlag,String explosivesImage
            , boolean isCreate, Product product){
        if(isCreate){
            product.setExplosives(new Explosives(explosivesFlag,explosivesImage));
        }else{
            //是否有爆品图片
            if (explosivesFlag != null) {
                Explosives explosives = null;
                if (explosivesFlag) {
                    explosives = new Explosives(explosivesFlag, explosivesImage);
                } else {
                    explosives = new Explosives(explosivesFlag, null);
                }
                product.setExplosives(explosives);
            }
        }
        return product;
    }


    private static Product setRecommend(Boolean recommendFlag, boolean isCreate, Product product){
        if(isCreate){
            product.setRecommend(new Recommend(recommendFlag));
        }else{
            if (recommendFlag != null) {
                Recommend recommend = new Recommend(recommendFlag);
                product.setRecommend(recommend);
            }
        }
        return product;
    }


    private static Product setPublic(Boolean publicFlag, boolean isCreate, Product product){
        if(isCreate){
            product.setOpen(new Open(publicFlag));
        }else{
            if (publicFlag != null) {
                Open open = new Open(publicFlag);
                product.setOpen(open);
            }

        }
        return product;
    }


    private static Product setOnShelfStatus(Integer onshelfStatus, boolean isCreate, Product product){
        if(isCreate){
            product.setOnshelfStatus(new OnshelfStatus(onshelfStatus));
        }else{
            if (onshelfStatus != null) {
                product.setOnshelfStatus(new OnshelfStatus(onshelfStatus));
            }
        }
        return product;
    }

    private static Product setEnable(Boolean enableFlag, boolean isCreate, Product product){
        if(isCreate){
            product.setEnable(new Enable(enableFlag));
        }else{
            if (enableFlag != null) {
                Enable enable = new Enable(enableFlag);
                product.setEnable(enable);
            }
        }
        return product;
    }


    private static Product setVideo(String videoUrl, String videoImg, String installVideoUrl, String installVideoImg
            , boolean isCreate, Product product){
        Video video2 = new Video(videoUrl, videoImg
                , installVideoUrl,installVideoImg);
        if(isCreate){
            product.setVideo(video2);
        }else{
            if (videoUrl != null || videoImg != null
                    || installVideoUrl != null || installVideoImg != null) {
                product.setVideo(video2);
            }
        }
        return product;
    }

    private static Product setProductImage(ProductSaveDTO productSaveDTO,boolean isCreate, Product product){
        List<ProductImage> productImages = ProductImageConvert.convertList(productSaveDTO.getAlbums());
        if(isCreate){
            product.setProductImages(productImages);
        }
        if (productSaveDTO.getAlbums() != null) {
            product.setProductImages(productImages);
        }
        return product;
    }


    private static Product setProductAttibute(ProductSaveDTO productSaveDTO, boolean isCreate,Product product){
        List<ProductAttributeSaveDTO> attributes = productSaveDTO.getAttributes();
        ProductAttribute productAttribute = new ProductAttribute(new ProductAttributeId(productSaveDTO.getAttributeId()));
        if(isCreate){
            if(attributes==null){
                throw  new IllegalArgumentException("attributes must be not null");
            }
            for (ProductAttributeSaveDTO attribute : attributes) {
                productAttribute.addAttribute(attribute.getName(), attribute.getValue(), attribute.getSort());
            }
            product.setProductAttribute(productAttribute);
        }else{
            if(attributes!=null){
                for (ProductAttributeSaveDTO attribute : attributes) {
                    productAttribute.addAttribute(attribute.getName(), attribute.getValue(), attribute.getSort());
                }
            }
        }
        return product;
    }



    public static List<ProductBO> convertList(List<Product> productList){
        return null;
    }


    public static ProductBO  convert(Product product, List<ProductDict> productDicts, ProductDetail productDetail, List<ProductSku> productSkuList) {

        return null;
    }

}
