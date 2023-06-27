package com.rc.cloud.app.operate.domain.category.valobj;

import com.rc.cloud.app.operate.domain.common.AssertionConcern;

/**
 * @ClassName: TenantId
 * @Author: liandy
 * @Date: 2023/6/23 13:26
 * @Description: 分类名
 */
public class ProductCategoryName extends AssertionConcern {
    /**
     * 分类名（中文名）
     */
    private String ChName;
    /**
     * 分类名（英文名）
     */
    private String EnglishName;
}
