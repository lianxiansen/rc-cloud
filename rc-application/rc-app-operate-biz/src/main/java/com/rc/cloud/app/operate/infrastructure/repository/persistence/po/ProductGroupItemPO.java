package com.rc.cloud.app.operate.infrastructure.repository.persistence.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.app.operate.domain.model.productgroup.ProductGroupItem;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

@TableName("product_group_item")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ProductGroupItemPO extends BaseDO {

    private static final long serialVersionUID = 2343242L;
    @TableField("id")
    private String Id;

    @TableField("product_group_id")
    private String productGroupId;

    @TableField("product_id")
    private String productId;


    public boolean sameValueAs(ProductGroupItem productGroupItem) {
        boolean sameValue=true;
        if(!Objects.isNull(productGroupId)){
            sameValue=sameValue&&productGroupId.equals(productGroupItem.getProductGroupId().id());
        }
        if (StringUtils.isNotEmpty(productId)) {
            sameValue=sameValue&&productId.equals(productGroupItem.getProductId().id());
        }
        return sameValue;
    }
}
