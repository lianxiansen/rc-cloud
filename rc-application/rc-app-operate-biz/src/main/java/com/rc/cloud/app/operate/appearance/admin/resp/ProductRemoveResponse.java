package com.rc.cloud.app.operate.appearance.admin.resp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.rc.cloud.app.operate.application.bo.ProductRemoveBO;
import com.rc.cloud.common.core.util.collection.CollectionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品移除response")
public class ProductRemoveResponse {

    @Schema(description = "需要操作的id列表")
    private List<String> needRemoveList;

    @Schema(description = "移除成功id列表")
    private List<String> successList;

    @Schema(description = "移除失败id列表")
    private List<Pair<String,String>> failList;

    @Schema(description = "当前操作是否成功，如果successList不为空就算操作成功")
    private boolean success;

    public static ProductRemoveResponse from(ProductRemoveBO bo) {
        ProductRemoveResponse response=new ProductRemoveResponse();
        response.setNeedRemoveList(bo.getNeedRemoveList());
        response.setFailList(bo.getFailList());
        response.setSuccessList(bo.getSuccessList());
        if(CollectionUtil.isNotEmpty(bo.getSuccessList())){
            response.setSuccess(true);
        }
        return response;
    }
}
