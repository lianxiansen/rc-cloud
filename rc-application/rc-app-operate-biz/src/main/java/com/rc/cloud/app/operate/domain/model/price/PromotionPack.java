package com.rc.cloud.app.operate.domain.model.price;

import java.util.List;

public class PromotionPack extends PromotionInfo {

    /**
     * 这个优惠活动关联的商品包
     */
    private List<ProductPack> relatedProductPackList;

    public List<ProductPack> getRelatedProductPackList() {
        return relatedProductPackList;
    }

    public void setRelatedProductPackList(List<ProductPack> relatedProductPackList) {
        this.relatedProductPackList = relatedProductPackList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }else{
            if(getClass() != obj.getClass()){
                return false;
            }else{
                if(((PromotionPack)obj).getId().equals(this.getId())){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }

}
