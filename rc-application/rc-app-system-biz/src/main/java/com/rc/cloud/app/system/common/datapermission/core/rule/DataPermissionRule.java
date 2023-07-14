package com.rc.cloud.app.system.common.datapermission.core.rule;

import java.util.Set;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 数据权限规则接口.通过实现接口，自定义数据规则。
 */
public interface DataPermissionRule {

    /**
     * 返回需要生效的表名数组
     * 为什么需要该方法？Data Permission 数组基于 SQL 重写，通过 Where 返回只有权限的数据
     * <p>
     * 如果需要基于实体名获得表名，可调用 TableInfoHelper#getTableInfo(Class) 获得
     *
     * @return 表名数组
     */
    Set<String> getTableNames();

//    /**
//     * 根据表名和别名，生成对应的 WHERE / OR 过滤条件
//     *
//     * @param tableName 表名
//     * @param tableAlias 别名，可能为空
//     * @return 过滤条件 Expression 表达式
//     */
//    Expression getExpression(String tableName, Alias tableAlias);

}
