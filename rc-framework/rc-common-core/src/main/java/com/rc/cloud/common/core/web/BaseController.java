package com.rc.cloud.common.core.web;

import com.rc.cloud.common.core.web.CodeResult;

/**
 * web层通用数据处理
 *
 * @author hqf@rc
 */
public class BaseController {

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected CodeResult<Void> toAjax(int rows) {
        return rows > 0 ? CodeResult.ok() : CodeResult.fail();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected CodeResult<Void> toAjax(boolean result) {
        return result ? CodeResult.ok() : CodeResult.fail();
    }

}
