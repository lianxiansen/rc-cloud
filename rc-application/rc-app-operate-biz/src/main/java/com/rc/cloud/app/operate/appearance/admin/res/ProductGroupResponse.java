package com.rc.cloud.app.operate.appearance.admin.res;


import com.rc.cloud.app.operate.appearance.admin.convert.ProductGroupConvert;
import com.rc.cloud.app.operate.appearance.admin.convert.ProductGroupItemConvert;
import com.rc.cloud.app.operate.application.bo.ProductGroupBO;
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
    @Schema(description = "产品组合名称")
    private String name;
    @Schema(description = "创建时间")
    private String createTime;
    @Schema(description = "产品组合项列表")
    private Collection<ProductGroupItemResponse> itemList;

    public static List<ProductGroupResponse> from(List<ProductGroupBO> boList) {
        List<ProductGroupResponse> vos = new ArrayList<>();
        boList.stream().sorted(new Comparator<ProductGroupBO>() {
            @Override
            public int compare(ProductGroupBO o1, ProductGroupBO o2) {
                return o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        }).forEach(item -> {
            ProductGroupResponse vo = ProductGroupConvert.INSTANCE.convert2ProductGroupVO(item);
            List<ProductGroupItemResponse> itemVOs=new ArrayList<>();
            item.getItemList().forEach(itemBO->{
                ProductGroupItemResponse itemVO= ProductGroupItemConvert.INSTANCE.convert2ProductGroupItemVO(itemBO);
                itemVOs.add(itemVO);
            });
            vo.setItemList(itemVOs);
            vos.add(vo);
        });
        return vos;
    }

}
