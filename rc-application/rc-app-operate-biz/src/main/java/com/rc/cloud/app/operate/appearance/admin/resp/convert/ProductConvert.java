package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductAttributeResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductDetailResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductImageResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductListResponse;
import com.rc.cloud.app.operate.application.bo.AttributeBO;
import com.rc.cloud.app.operate.application.bo.AttributeValueBO;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.domain.common.ProductDictKeyEnum;
import com.rc.cloud.app.operate.domain.common.ProductShelfStatusEnum;
import com.rc.cloud.common.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConvert {

    public static ProductListResponse convert2ProductList(ProductBO bo) {
        ProductListResponse response=new ProductListResponse();
        response.setId(bo.getId());
        response.setSpuCode(bo.getSpuCode());
        response.setName(bo.getName());
        response.setListImage(bo.getProductListImage());
        response.setNewFlag(bo.isNewFlag());
        response.setBrandName(bo.getName());
        response.setExplosivesFlag(bo.isExplosivesFlag());
        if(bo.isExplosivesFlag()){
            response.setExplosivesImage(bo.getExplosivesImage());
        }
        response.setPublicFlag(bo.isPublicFlag());
        response.setRecommendFlag(bo.isRecommendFlag());
        response.setCategoryName(bo.getCategory());
        if(bo.getOnshelfStatus()== ProductShelfStatusEnum.Initshelf.value
                || bo.getOnshelfStatus()==ProductShelfStatusEnum.Offshelf.value){
            response.setOnshelfStatus(ProductShelfStatusEnum.Offshelf.value);
        }else{

            response.setOnshelfStatus(ProductShelfStatusEnum.Onshelf.value);
        }
        response.setCreateTime(LocalDateTimeUtil.formatNormal(bo.getCreateTime()));
        return response;
    }


    public static ProductDetailResponse convert2ProductDetail(ProductBO bo) {
        ProductDetailResponse response=new ProductDetailResponse();
        response.setId(bo.getId());
        response.setName(bo.getName());
        response.setTag(bo.getTag());
        response.setRemark(bo.getRemark());
        response.setListImage(bo.getProductListImage());
        response.setNewFlag(bo.isNewFlag());
        response.setBrandId(bo.getBrandId());
        response.setExplosivesFlag(bo.isExplosivesFlag());
        response.setPublicFlag(bo.isPublicFlag());
        response.setRecommendFlag(bo.isRecommendFlag());
        response.setFirstCategory(bo.getFirstCategory());
        response.setSecondCategory(bo.getSecondCategory());
        response.setThirdCategory(bo.getThirdCategory());
        response.setCustomClassificationId(bo.getCustomClassificationId());
        response.setNewFlag(bo.isNewFlag());
        response.setExplosivesFlag(bo.isExplosivesFlag());
        response.setExplosivesImage(bo.getExplosivesImage());
        response.setRecommendFlag(bo.isRecommendFlag());
        response.setPublicFlag(bo.isPublicFlag());
        if(bo.getOnshelfStatus()== ProductShelfStatusEnum.Initshelf.value
                || bo.getOnshelfStatus()==ProductShelfStatusEnum.Offshelf.value){
            response.setOnshelfStatus(ProductShelfStatusEnum.Offshelf.value);
        }else{

            response.setOnshelfStatus(ProductShelfStatusEnum.Onshelf.value);
        }
        response.setVideoImg(bo.getVideoImg());
        response.setVideoUrl(bo.getVideoUrl());
        response.setProductListImage(bo.getProductListImage());
        if(bo.getMasterAlbums()!=null){
            List<ProductImageResponse> masterImages = bo.getMasterAlbums().stream().map(item ->
                            new ProductImageResponse(item.getId(), item.getUrl(), item.getSort()))
                    .collect(Collectors.toList());
            response.setMasterAlbums(masterImages);
        }
        if(bo.getSizeAlbums()!=null){
            List<ProductImageResponse> sizeImages = bo.getSizeAlbums().stream().map(item ->
                            new ProductImageResponse(item.getId(),item.getUrl(), item.getSort()))
                    .collect(Collectors.toList());
            response.setSizeAlbums(sizeImages);
        }
        response.setDetail(bo.getDetail());
        response.setInstallVideoImg(bo.getInstallVideoImg());
        response.setInstallVideoUrl(bo.getInstallVideoUrl());
        response.setInstallDetail(bo.getInstallDetail());
        response.setSpuCode(bo.getSpuCode());
        if(bo.getSkus()!=null){
            response.setSkus(ProductSkuConvert.convert2ProductSkuDetailList(bo.getSkus()));
        }
        response.setCaiZhi(bo.getDicts().get(ProductDictKeyEnum.CaiZhi.name));
        response.setTiaoMa(bo.getDicts().get(ProductDictKeyEnum.TiaoMa.name));
        response.setChengZhong(bo.getDicts().get(ProductDictKeyEnum.ChengZhong.name));
        response.setChiCun(bo.getDicts().get(ProductDictKeyEnum.ChiCun.name));

        List<ProductAttributeResponse> attributes =new ArrayList<>();
        if(bo.getAttributes()!=null){
            for (AttributeBO attribute : bo.getAttributes()) {
                for (AttributeValueBO value : attribute.getValues()) {
                    ProductAttributeResponse attributeResponse=new ProductAttributeResponse();
                    attributeResponse.setName(attribute.getAttribute());
                    attributeResponse.setValue(value.getAttributeValue());
                    attributeResponse.setSort(attribute.getSort()+value.getSort());
                    attributes.add(attributeResponse);
                }
            }
        }
        response.setAttributes(attributes);

        return response;
    }



    public static String format(String firstCategory,String secondCategory, String thirdCategory){
        String s=firstCategory+"-"+secondCategory;
        if(StringUtils.isNotEmpty(thirdCategory)){
            s+= "-"+thirdCategory;
        }
        return s;
    }


}
