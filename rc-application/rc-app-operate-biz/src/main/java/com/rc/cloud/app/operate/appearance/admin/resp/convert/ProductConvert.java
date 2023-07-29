package com.rc.cloud.app.operate.appearance.admin.resp.convert;

import com.rc.cloud.app.operate.appearance.admin.resp.ProductDetailResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductImageResponse;
import com.rc.cloud.app.operate.appearance.admin.resp.ProductListResponse;
import com.rc.cloud.app.operate.application.bo.ProductBO;
import com.rc.cloud.app.operate.domain.common.ProductDictKeyEnum;
import com.rc.cloud.common.core.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConvert {

    public static ProductListResponse convert2ProductList(ProductBO bo) {
        ProductListResponse response=new ProductListResponse();
        response.setId(bo.getId());
        response.setName(bo.getName());
        response.setListImage(bo.getProductListImage());
        response.setNewFlag(bo.isNewFlag());
        response.setBrandName(bo.getName());
        response.setExplosivesFlag(bo.isExplosivesFlag());
        response.setPublicFlag(bo.isPublicFlag());
        response.setRecommendFlag(bo.isRecommendFlag());
        response.setCategoryName(format(bo.getFirstCategory(), bo.getSecondCategory(), bo.getThirdCategory()));
        response.setOnShelfStatus(bo.getOnshelfStatus());
        response.setCreateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(bo.getCreateTime()));
        return response;
    }


    public static ProductDetailResponse convert2ProductDetail(ProductBO bo) {
        ProductDetailResponse response=new ProductDetailResponse();
        response.setId(bo.getId());
        response.setName(bo.getName());
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
        response.setOnshelfStatus(bo.getOnshelfStatus());
        response.setVideoImg(bo.getVideoImg());
        response.setVideoUrl(bo.getVideoUrl());
        response.setProductListImage(bo.getProductListImage());
        if(bo.getMasterImages()!=null){
            List<ProductImageResponse> masterImages = bo.getMasterImages().stream().map(item ->
                            new ProductImageResponse(item.getUrl(), item.getSort()))
                    .collect(Collectors.toList());
            response.setImageList(masterImages);
        }
        if(bo.getSizeImages()!=null){
            List<ProductImageResponse> sizeImages = bo.getSizeImages().stream().map(item ->
                            new ProductImageResponse(item.getUrl(), item.getSort()))
                    .collect(Collectors.toList());
            response.setSizeImageList(sizeImages);
        }
        if(bo.getDetail()!=null){
            response.setDetail(bo.getDetail().getDetail());
            response.setInstallVideoImg(bo.getDetail().getInstallVideoImg());
            response.setInstallVideoUrl(bo.getDetail().getInstallVideoUrl());
            response.setInstallDetail(bo.getDetail().getInstallDetail());
        }

        response.setSpuCode(bo.getSpuCode());
        response.setSkus(ProductSkuConvert.convert2ProductSkuDetailList(bo.getSkus()));
        response.setCaiZhi(bo.getDicts().get(ProductDictKeyEnum.CaiZhi.name));
        response.setTiaoMa(bo.getDicts().get(ProductDictKeyEnum.TiaoMa.name));
        response.setChengZhong(bo.getDicts().get(ProductDictKeyEnum.ChengZhong.name));
        response.setChiCun(bo.getDicts().get(ProductDictKeyEnum.ChiCun.name));



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
