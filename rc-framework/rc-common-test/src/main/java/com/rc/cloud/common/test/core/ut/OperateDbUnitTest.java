package com.rc.cloud.common.test.core.ut;

import org.springframework.test.context.jdbc.Sql;

/**
 * 依赖内存 DB 的单元测试
 *
 * 注意，Service 层同样适用。对于 Service 层的单元测试，我们针对自己模块的 Mapper 走的是 H2 内存数据库，针对别的模块的 Service 走的是 Mock 方法
 *
 * @author 芋道源码
 */
@Sql(scripts = {"/sql/clean.sql","/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // 每个单元测试结束后，清理 DB
public class OperateDbUnitTest extends BaseDbUnitTest{
    public static class Application {
    }

}
