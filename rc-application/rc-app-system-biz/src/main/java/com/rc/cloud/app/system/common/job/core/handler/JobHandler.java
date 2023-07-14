package com.rc.cloud.app.system.common.job.core.handler;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 任务处理器
 */
public interface JobHandler {

    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    String execute(String param) throws Exception;
}
