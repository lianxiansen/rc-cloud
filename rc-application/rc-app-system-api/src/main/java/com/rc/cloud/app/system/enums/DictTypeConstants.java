package com.rc.cloud.app.system.enums;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description System 字典类型的枚举类
 */
public interface DictTypeConstants {

    /**
     * 用户类型
     */
    String USER_TYPE = "user_type";

    /**
     * 系统状态
     */
    String COMMON_STATUS = "common_status";

    // ========== SYSTEM 模块 ==========
    /**
     * 用户性别
     */
    String USER_SEX = "system_user_sex";

    /**
     * 操作类型
     */
    String OPERATE_TYPE = "system_operate_type";

    /**
     * 登录日志的类型
     */
    String LOGIN_TYPE = "system_login_type";

    /**
     * 登录结果
     */
    String LOGIN_RESULT = "system_login_result";

    /**
     * 错误码的类型枚举
     */
    String ERROR_CODE_TYPE = "system_error_code_type";

    /**
     * 短信渠道编码
     */
    String SMS_CHANNEL_CODE = "system_sms_channel_code";

    /**
     * 短信模板类型
     */
    String SMS_TEMPLATE_TYPE = "system_sms_template_type";

    /**
     * 短信发送状态
     */
    String SMS_SEND_STATUS = "system_sms_send_status";

    /**
     * 短信接收状态
     */
    String SMS_RECEIVE_STATUS = "system_sms_receive_status";


}
