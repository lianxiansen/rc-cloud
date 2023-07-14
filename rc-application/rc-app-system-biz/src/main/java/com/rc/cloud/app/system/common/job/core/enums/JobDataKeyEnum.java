package com.rc.cloud.app.system.common.job.core.enums;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description Quartz Job Data 的 key 枚举
 */
public enum JobDataKeyEnum {

    /**
     * 任务ID
     */
    JOB_ID,
    /**
     * 任务执行器名称
     */
    JOB_HANDLER_NAME,
    /**
     * 任务执行器参数
     */
    JOB_HANDLER_PARAM,
    /**
     * 任务最大重试次数
     */
    JOB_RETRY_COUNT,
    /**
     * 任务每次重试间隔
     */
    JOB_RETRY_INTERVAL,

}
