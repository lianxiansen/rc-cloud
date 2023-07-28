package com.rc.cloud.app.operate.appearance.admin.resp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.rc.cloud.app.operate.application.bo.ProductRemoveBO;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品移除response")
public class ProductRemoveResponse {

    @Schema(description = "移除成功id列表")
    private List<String> successList;

    @Schema(description = "移除失败id列表")
    private List<ProductIdAndErrorResult> failList;

    @Schema(description = "当前操作是否成功，如果successList不为空就算操作成功")
    private boolean success;

    public static ProductRemoveResponse from(ProductRemoveBO bo) {
        ProductRemoveResponse response=new ProductRemoveResponse();
        if(CollectionUtil.isNotEmpty(bo.getFailList())){
            List<ProductIdAndErrorResult> failList =new ArrayList<>();
            bo.getFailList().forEach(
                    x-> {
                        failList.add(new ProductIdAndErrorResult(x.getKey(),x.getValue()));
                    }
            );
            response.setFailList(failList);
        }
        response.setSuccessList(bo.getSuccessList());
        if(CollectionUtil.isNotEmpty(bo.getSuccessList())){
            response.setSuccess(true);
        }
        return response;
    }

}
