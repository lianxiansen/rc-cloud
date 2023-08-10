package com.rc.cloud.app.operate.appearance.admin.resp;


import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.appearance.admin.resp.convert.ProductGroupItemConvert;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
import com.rc.cloud.common.core.util.date.LocalDateTimeUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "产品组合response")
public class ProductGroupResponse {
    @Schema(description = "产品组合唯一标识")
    private String Id;
    @Schema(description = "商品唯一标识")
    private String productId;
    @Schema(description = "产品组合名称")
    private String name;
    @Schema(description = "创建时间")
    private String createTime;
    @Schema(description = "产品组合描述",example = "共7款产品")
    private String description;
    @Schema(description = "产品组合项列表")
    private Collection<ProductGroupItemResponse> itemList;


    public static ProductGroupResponse from(ProductGroupBO bo) {
        ProductGroupResponse response = ProductGroupConvert.INSTANCE.convert2ProductGroupVO(bo);
        response.setCreateTime(LocalDateTimeUtils.format(bo.getCreateTime()));
        response.setDescription("共"+response.getItemList().size()+"款产品");
        return response;
    }

    public static List<ProductGroupResponse> from(List<ProductGroupBO> boList) {
        List<ProductGroupResponse> vos = new ArrayList<>();
        boList.stream().sorted(new Comparator<ProductGroupBO>() {
            @Override
            public int compare(ProductGroupBO o1, ProductGroupBO o2) {
                return o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        }).forEach(item -> {
            ProductGroupResponse vo = from(item);
            List<ProductGroupItemResponse> itemVOs=new ArrayList<>();
            item.getItemList().forEach(itemBO->{
                ProductGroupItemResponse itemVO= ProductGroupItemConvert.INSTANCE.convert2ProductGroupItemVO(itemBO);
                itemVOs.add(itemVO);
            });
            vo.setItemList(itemVOs);
            vo.setDescription("共"+item.getItemList().size()+"款产品");
            vos.add(vo);
        });
        return vos;
    }

}
