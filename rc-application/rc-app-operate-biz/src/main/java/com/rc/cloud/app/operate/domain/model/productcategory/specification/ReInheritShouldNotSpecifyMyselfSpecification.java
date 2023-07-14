package com.rc.cloud.app.operate.domain.model.productcategory.specification;

import com.rc.cloud.app.operate.domain.model.productcategory.ProductCategory;
import com.rc.cloud.app.operate.domain.model.productcategory.identifier.ProductCategoryId;
import com.rc.cloud.common.core.domain.AbstractSpecification;
import lombok.Data;

/**
 * @ClassName: ReInheritShouldNotSpecifyMyself
 * @Author: liandy
 * @Date: 2023/7/14 13:44
 * @Description: 重新指定上级分类不能是自己
 */
@Data
public class ReInheritShouldNotSpecifyMyselfSpecification extends AbstractSpecification {
    private ProductCategory sourceProductCategory;
    public ReInheritShouldNotSpecifyMyselfSpecification(ProductCategory sourceProductCategory){
        this.sourceProductCategory=sourceProductCategory;
    }
    @Override
    public boolean isSatisfiedBy(Object o) {
        ProductCategoryId targetParentProductCategoryId= (ProductCategoryId) o;
        return !targetParentProductCategoryId.equals(sourceProductCategory.getId());
    }
}
