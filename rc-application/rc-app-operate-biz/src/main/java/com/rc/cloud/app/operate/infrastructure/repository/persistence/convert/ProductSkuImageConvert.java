package com.rc.cloud.app.operate.infrastructure.repository.persistence.convert;

import com.rc.cloud.app.operate.domain.model.productsku.valobj.Sort;
import com.rc.cloud.app.operate.domain.model.productsku.valobj.Url;
import com.rc.cloud.app.operate.domain.model.productsku.ProductSkuImage;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuId;
import com.rc.cloud.app.operate.domain.model.productsku.identifier.ProductSkuImageId;
import com.rc.cloud.app.operate.infrastructure.repository.persistence.po.ProductSkuImagePO;

import java.util.ArrayList;
import java.util.List;


public class ProductSkuImageConvert {


    public static ProductSkuImage convertDomain(ProductSkuImagePO po) {
        if (po != null) {
            ProductSkuImage productSkuImage = new ProductSkuImage(
                    new Url(po.getUrl()), new Sort(po.getSort()
            ));
            return productSkuImage;
        }
        return null;
    }

    public static ProductSkuImagePO convertProductSkuImagePO(ProductSkuImage productSkuImage) {
        ProductSkuImagePO po = new ProductSkuImagePO();
        po.setUrl(productSkuImage.getUrl().getValue());
        po.setSort(productSkuImage.getSort().getValue());
        return po;
    }

    public static List<ProductSkuImage> convertDomainList(List<ProductSkuImagePO> list) {
        List<ProductSkuImage> resList = new ArrayList<>();
        if (list != null) {
            list.forEach(x -> resList.add(convertDomain(x)));
        }
        return resList;
    }
}
