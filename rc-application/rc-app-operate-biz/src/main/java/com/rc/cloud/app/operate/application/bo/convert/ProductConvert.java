package com.rc.cloud.app.operate.application.bo.convert;

import com.rc.cloud.app.operate.application.bo.*;
import com.rc.cloud.app.operate.application.dto.ProductAttributeSaveDTO;
import com.rc.cloud.app.operate.application.dto.ProductSaveDTO;
import com.rc.cloud.app.operate.domain.common.ProductOriginEnum;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.app.operate.domain.model.brand.identifier.BrandId;
import com.rc.cloud.app.operate.domain.model.product.Product;
import com.rc.cloud.app.operate.domain.model.product.ProductAttribute;
import com.rc.cloud.app.operate.domain.model.productimage.ProductImage;
import com.rc.cloud.app.operate.domain.model.product.valobj.OnshelfStatus;
import com.rc.cloud.app.operate.domain.model.customclassification.identifier.CustomClassificationId;
import com.rc.cloud.app.operate.domain.model.product.identifier.ProductId;
import com.rc.cloud.app.operate.domain.model.product.valobj.*;
import com.rc.cloud.app.operate.domain.model.productdetail.ProductDetail;
import com.rc.cloud.app.operate.domain.model.productdict.ProductDict;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSku;
import com.rc.cloud.common.core.util.StringUtils;

import java.util.*;

public class ProductConvert
{

    /**
     * 填充product参数
     * 如果product为空，表示新增dto传入数据为空就要判断是否合法
     * 如果product不为空，可能是部分属性修改，dto传入数据可以为空
     * @param productId
     * @param productSaveDTO
     * @param isCreate
     * @param product
     * @return
     */
    public static Product convertDomain(String productId,
                                  ProductSaveDTO productSaveDTO,boolean isCreate, Product product){
        if(isCreate && product!=null){
            throw new IllegalArgumentException("参数错误，内部错误");
        }
        if(isCreate){
            product = new Product(
                    new ProductId(productId)
                    , new Name(productSaveDTO.getName()));
        }
        //设置商品名
        product = setName(productSaveDTO.getName(),isCreate,product);
        //设置商品type
        product = setType(productSaveDTO.getProductType(),isCreate,product);
        //设置商品origin
        product = setOrigin(productSaveDTO.getProductOrigin(),isCreate,product);
        //设置商品sort
        product = setSort(productSaveDTO.getSort(),isCreate,product);
        //设置商品outid
        product = setOutId(productSaveDTO.getOutId(),isCreate,product);
        //设置商品spu
        product = setSpuCode(productSaveDTO.getSpuCode(),isCreate,product);
        //商品图片
        product = setProductListImage(productSaveDTO.getListImage(),isCreate,product);
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
        //设置OnShelfStatus
        product = setOnShelfStatus(productSaveDTO.getOnShelfStatus(),isCreate,product);
        //设置Recommend
        product = setRecommend(productSaveDTO.getRecommendFlag(),isCreate,product);
        //设置Recycle
        product = setRecycle(productSaveDTO.getRecycleFlag(),isCreate,product);
        //设置PackingLowestBuy
        product = setPackingLowestBuy(productSaveDTO.getPackingLowestBuyFlag(),isCreate,product);
        //设置Video
        product = setVideo(productSaveDTO.getVideoUrl()
                ,productSaveDTO.getVideoImg()
                ,isCreate,product);
        //设置属性
        product =setProductAttibute(productSaveDTO,isCreate,product);

        return product;
    }


    /**
     * 设置商品名
     * @param name
     * @param isCreate
     * @param product
     * @return
     */
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

    /**
     * 设置类别
     * @param type
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setType(Integer type, boolean isCreate, Product product){
        if(isCreate){
            if(type!=null){
                product.setType(new Type(type));
            }else{
                product.setType(new Type(0));
            }
        }else{
            if (type != null) {
                product.setType(new Type(type));
            }
        }
        return product;
    }

    /**
     * 设置商品来源
     * @param origin
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setOrigin(Integer origin, boolean isCreate, Product product){
        if(isCreate){
            if(origin!=null){
                product.setOrigin(new Origin(origin));
            }else{
                product.setOrigin(new Origin(ProductOriginEnum.Self.value));
            }

        }else{
            if (origin != null) {
                product.setOrigin(new Origin(origin));
            }
        }
        return product;
    }

    /**
     * 设置outid
     * @param outid
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setOutId(String outid, boolean isCreate, Product product){
        if(isCreate){
            product.setOutid(new OutId(outid));
        }else{
            if (outid != null) {
                product.setOutid(new OutId(outid));
            }
        }
        return product;
    }

    /**
     * 设置排序
     * @param sort
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setSort(Integer sort, boolean isCreate, Product product){
        if(isCreate){
            product.setSort(new Sort(sort));
        }else{
            if (sort != null) {
                product.setSort(new Sort(sort));
            }
        }
        return product;
    }

    /**
     * 设置商品spucode
     * @param spuCode
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setSpuCode(String spuCode, boolean isCreate, Product product){
        //商品SPU
        if(isCreate){
            product.setSpuCode(new SpuCode(spuCode));
        }else{
            if (spuCode != null) {
                product.setSpuCode(new SpuCode(spuCode));
            }
        }
        return product;
    }

    /**
     * 设置商品remark
     * @param remark
     * @param isCreate
     * @param product
     * @return
     */
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


    /**
     * 设置商品tag
     * @param tag
     * @param isCreate
     * @param product
     * @return
     */
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

    /**
     * 设置商品brandId
     * @param brandId
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setBrandId(String brandId, boolean isCreate, Product product){
        //商品描述
        if(isCreate){
            if(!StringUtils.isEmpty(brandId)){
                product.setBrandId(new BrandId(brandId));
            }
        }else{
            if (brandId != null) {
                product.setBrandId(new BrandId(brandId));
            }
        }
        return product;
    }

    /**
     * 设置商品类别
     * @param firstCategory
     * @param secondCategory
     * @param thirdCategory
     * @param isCreate
     * @param product
     * @return
     */
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

    /**
     * 设置自定义id
     * @param customClassificationId
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setCustomClassificationId(String customClassificationId, boolean isCreate, Product product){
        if(isCreate){
            if(!StringUtils.isEmpty(customClassificationId)){
                product.setCustomClassificationId(new CustomClassificationId(customClassificationId));
            }
        }else{
            if (customClassificationId != null) {
                product.setCustomClassificationId(new CustomClassificationId(customClassificationId));
            }
        }
        return product;
    }

    /**
     * 设置newFlag
     * @param newFlag
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setNewest(Boolean newFlag, boolean isCreate, Product product){
        if(isCreate){
            if(newFlag==null){
                product.setNewFlag(false);
            }else{
                product.setNewFlag(newFlag);
            }
        }else{
            if (newFlag != null) {
                product.setNewFlag(newFlag);
            }
        }
        return product;
    }

    /**
     * 设置explosives
     * @param explosivesFlag
     * @param explosivesImage
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setExplosives(Boolean explosivesFlag,String explosivesImage
            , boolean isCreate, Product product){
        if(isCreate){
            if(explosivesFlag!=null && explosivesFlag){
                product.setExplosives(new Explosives(explosivesFlag,new Url(explosivesImage)));
            }else{
                product.setExplosives(new Explosives(false,new Url("")));
            }
        }else{
            //是否有爆品图片
            if (explosivesFlag != null) {
                Explosives explosives = null;
                if (explosivesFlag) {

                    explosives = new Explosives(explosivesFlag, new Url(explosivesImage));
                } else {
                    explosives = new Explosives(explosivesFlag, new Url(""));
                }
                product.setExplosives(explosives);
            }
        }
        return product;
    }

    /**
     * 设置首页推荐
     * @param recommendFlag
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setRecommend(Boolean recommendFlag, boolean isCreate, Product product){
        if(isCreate){
            if(recommendFlag==null){
                product.setRecommendFlag(new Recommend(false));
            }else{
                product.setRecommendFlag(new Recommend(recommendFlag));
            }

        }else{
            if (recommendFlag != null) {
                Recommend recommend = new Recommend(recommendFlag);
                product.setRecommendFlag(recommend);
            }
        }
        return product;
    }

    /**
     * 设置Recycle
     * @param recycleFlag
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setRecycle(Boolean recycleFlag, boolean isCreate, Product product){
        if(isCreate){
            if(recycleFlag==null){
                product.setRecycleFlag(new Recycle(false));
            }else{
                product.setRecycleFlag(new Recycle(recycleFlag));
            }

        }else{
            if (recycleFlag != null) {
                Recycle recycle = new Recycle(recycleFlag);
                product.setRecycleFlag(recycle);
            }
        }
        return product;
    }

    /**
     * 设置packingLowestBuyFlag
     * @param packingLowestBuyFlag
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setPackingLowestBuy(Boolean packingLowestBuyFlag, boolean isCreate, Product product){
        if(isCreate){
            if(packingLowestBuyFlag==null){
                product.setPackingLowestBuy(new PackingLowestBuy(false));
            }else{
                product.setPackingLowestBuy(new PackingLowestBuy(packingLowestBuyFlag));
            }
        }else{
            if (packingLowestBuyFlag != null) {
                PackingLowestBuy packingLowestBuy = new PackingLowestBuy(packingLowestBuyFlag);
                product.setPackingLowestBuy(packingLowestBuy);
            }
        }
        return product;
    }


    /**
     * 设置公开
     * @param publicFlag
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setPublic(Boolean publicFlag, boolean isCreate, Product product){
        if(isCreate){
            if(publicFlag==null){
                product.setPublicFlag(false);
            }else{
                product.setPublicFlag(publicFlag);
            }
        }else{
            if (publicFlag != null) {
                product.setPublicFlag(publicFlag);
            }
        }
        return product;
    }

    /**
     * 设置上架状态
     * @param onshelfStatus
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setOnShelfStatus(Integer onshelfStatus, boolean isCreate, Product product){
        if(isCreate){
            if(onshelfStatus!=null){
                product.setOnshelfStatus(new OnshelfStatus(onshelfStatus));
            }else{
                product.setOnshelfStatus(new OnshelfStatus(ProductShelfStatusEnum.InitShelf.value));
            }
        }else{
            if (onshelfStatus != null) {
                product.setOnshelfStatus(new OnshelfStatus(onshelfStatus));
            }
        }
        return product;
    }

    /**
     * 设置视频
     * @param videoUrl
     * @param videoImg
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setVideo(String videoUrl, String videoImg
            , boolean isCreate, Product product){
        Video video=null;
        if(isCreate){
            if(videoUrl!=null){
                video = new Video(new Url(videoUrl));
                product.setVideo(video);
                if(videoImg!=null){
                    video.setVideoImg(new Url(videoImg));
                }
            }
        }else{
            if (videoUrl != null || videoImg != null) {
                video = new Video(new Url(videoUrl));
                product.setVideo(video);
                if(videoImg!=null){
                    video.setVideoImg(new Url(videoImg));
                }
            }
        }
        return product;
    }

    /**
     * 设置productListImage
     * @param productListImage
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setProductListImage(String productListImage, boolean isCreate, Product product){
        Url url=new Url(productListImage);
        if(isCreate){
            product.setProductListImage(url);
        }else{
            if (productListImage != null) {
                product.setProductListImage(url);
            }
        }
        return product;
    }



    /**
     * 设置属性
     * @param productSaveDTO
     * @param isCreate
     * @param product
     * @return
     */
    private static Product setProductAttibute(ProductSaveDTO productSaveDTO, boolean isCreate,Product product){
        List<ProductAttributeSaveDTO> attributes = productSaveDTO.getAttributes();
        ProductAttribute productAttribute = new ProductAttribute(new ProductId(productSaveDTO.getId()));
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

    /**
     * 转化为ProductBO
     * @param product
     * @param productSizeImages
     * @param productMasterImages
     * @param productDicts
     * @param productDetail
     * @param skuList
     * @return
     */
    public static ProductBO  convertProductBO(Product product,
                                            List<ProductImage> productSizeImages,
                                            List<ProductImage> productMasterImages,
                                              Set<ProductDict> productDicts, ProductDetail productDetail

            , List<ProductSku> skuList) {
        ProductBO bo=convertProductBO(product);
        bo.setSizeImages( ProductImageConvert.convertProductImageBOList(productSizeImages));
        bo.setMasterImages(ProductImageConvert.convertProductImageBOList(productMasterImages));
        if(productDicts!=null){
            bo.setDicts(ProductDictConvert.convertProductDictMap(productDicts));
        }
        if(productDetail!=null){

            bo.setDetail(productDetail.getDetail().getValue());
            bo.setInstallDetail(productDetail.getInstallDetail().getValue());
            bo.setInstallVideoImg(productDetail.getInstallVideoImg().getValue());
            bo.setInstallVideoUrl(productDetail.getInstallVideoUrl().getValue());

        }
        if(skuList!=null){
            bo.setSkus(ProductSkuConvert.convertProductSkuBOList(skuList));
        }
        return bo;
    }

    /**
     * 领域转化ProductBO，仅基础数据
     * @param product
     * @return
     */
    public static ProductBO convertProductBO(Product product){

        ProductBO bo=new ProductBO();
        bo.setId(product.getId().id());
        bo.setSpuCode(product.getSpuCode().getValue());
        bo.setProductType(product.getType().getValue());
        bo.setProductOrigin(product.getOrigin().getValue());
        bo.setOutId(product.getOutid().getValue());
        bo.setName(product.getName().getValue());
        bo.setProductListImage(product.getProductListImage().getValue());
        bo.setRemark(product.getRemark().getValue());
        bo.setTag(product.getTag().getValue());
        bo.setSort(product.getSort().getValue());
        if(product.getBrandId()!=null){
            bo.setBrandId(product.getBrandId().id());
        }
        //类别
        bo.setFirstCategory(product.getFirstCategory().getValue());
        bo.setSecondCategory(product.getSecondCategory().getValue());
        bo.setThirdCategory(product.getThirdCategory().getValue());
        //自定义分类
        if(product.getCustomClassificationId()!=null){
            bo.setCustomClassificationId(product.getCustomClassificationId().id());
        }
        if(product.getNewFlag()!=null){
            bo.setNewFlag(product.getNewFlag());
        }else{
            bo.setNewFlag(false);
        }
        if(product.getRecommendFlag()!=null){
            bo.setRecommendFlag(product.getRecommendFlag().getValue());
        }
        if(product.getRecycleFlag()!=null){
            bo.setRecycleFlag(product.getRecycleFlag().getValue());
        }else{
            bo.setRecycleFlag(false);
        }
        if(product.getExplosives()!=null){
            bo.setExplosivesFlag(product.getExplosives().isFlag());
            bo.setExplosivesImage(product.getExplosives().getImage().getValue());
        }
        if(product.getPublicFlag()!=null){
            bo.setPublicFlag(product.getPublicFlag());
        }else{
            bo.setPublicFlag(false);
        }
        //上架状态
        if(product.getOnshelfStatus()!=null){
            bo.setOnshelfStatus(product.getOnshelfStatus().getValue());
        }
        //视频
        if(product.getVideo()!=null){
            if(product.getVideo().getVideoImg()!=null){
                bo.setVideoImg(product.getVideo().getVideoImg().getValue());
            }
            if(product.getVideo().getVideoUrl()!=null){
                bo.setVideoUrl(product.getVideo().getVideoUrl().getValue());
            }
        }
        if(product.getPackingLowestBuy()!=null){
            bo.setPackingLowestBuyFlag(product.getPackingLowestBuy().result());
        }
        //转换属性
        if(product.getProductAttribute()!=null){
            ProductAttribute productAttribute = product.getProductAttribute();
            SortedSet<Attribute> attributes = productAttribute.getAttributes();
            bo.setAttributes(convertAttributeBOList(attributes));
        }
        if(product.getCreateTime()!=null){
            bo.setCreateTime(product.getCreateTime().getTime());
        }
        return bo;

    }

    private  static List<AttributeBO> convertAttributeBOList(SortedSet<Attribute> attributes){

        List<AttributeBO> resList =new ArrayList<>();
        for (Attribute attribute : attributes) {
            AttributeBO attributeBO=new AttributeBO();
            attributeBO.setAttribute(attribute.getAttribute());
            attributeBO.setSort(attribute.getSort());
            attributeBO.setValues(convertAttributeValueBOList(attribute.getValues()));
            resList.add(attributeBO);
        }
        return resList;
    }

    private static List<AttributeValueBO> convertAttributeValueBOList(SortedSet<AttributeValue> attributeValues){
        List<AttributeValueBO> resList =new ArrayList<>();
        for (AttributeValue attributeValue : attributeValues) {
            AttributeValueBO attributeValueBO=new AttributeValueBO();
            attributeValueBO.setAttributeValue(attributeValue.getAttributeValue());
            attributeValueBO.setSort(attributeValue.getSort());
            resList.add(attributeValueBO);
        }
        return resList;
    }


}
