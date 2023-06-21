package com.rc.cloud.app.mall.appearance.response;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/9
 * @Description:
 */
public enum ApiCodeEnum {


    /**
     * 常规接口异常
     */
    SUCCESS(200, "操作成功"),
    Error(500, "操作失败"),
    BUSY(600, "系统繁忙"),
    INVALID_PARAMETERS(10001, "请求参数错误"),
    BAD_REQUEST(10002, "请求失败"),
    SQL_ERROR(10003, "数据库错误"),
    INTERNAL_SERVER_ERROR(10004, "服务器内部错误"),
    FORBIDDEN(10005, "权限不足"),
    INTERNAL_CONTROLLER_ERROR(10006, "服务控制器内部错误"),
    NO_SUCH_ANNOTATION_ERROR(10007, "service annotation handle exception"),
    UNKNOWN(10008, "未知错误"),
    REMARKS(100010,"请先备注,备注后可开启拒签按钮"),
    ALREADY_FINISHED(100011,"已兑完"),
    REFUND_SUCCESSFULLY(100012,"您已成功退款"),
    RATE_LIMIT(100013,"当前操作人数过多，请稍后再试!"),
    VOTE_DEFEATED(100014,"投票失败"),
    VOTE_REPETITION(100015,"投票重复"),
    VOTE_INSUFFICIENT(100016,"投票数不足"),
    VOTE_TIMEOUT(100017,"投票时间已过"),

    /**
     * oss 文件操作
     */
    EMPTY_FILE(11000, "文件不存在"),
    /**
     * curd操作
     */
    CURD_ERROR(12000, "数据操作失败"),
    CURD_CREATE_ERROR(12001, "数据保存失败"),
    CURD_UPDATE_ERROR(12002, "数据修改失败"),
    CURD_DELETE_ERROR(12003, "数据删除失败"),
    CURD_NULL_ERROR(12004, "数据不存在"),
    /**
     * 推送操作
     */
    PUSH_ERROR(20000, "推送失败"),
    ALREADY_INVOICE_ERROR(30000, "发票已存在"),
    MONEY_ERR(10000,"请输入正确的退款金额"),
    /**
     * account
     */
    ACCOUNT_UNAUTHORIZED(41000, "unauthorized 未授权"),
    ACCOUNT_SIGNIN_ERROR(41001, "用户登录失败"),
    ACCOUNT_NOT_EXIST(41002, "账号不存在"),
    ACCOUNT_ERROR_CERTIFICATE(41003, "用户名或密码不正确"),
    ACCOUNT_LOCKED(41004, "账户已锁定"),
    ACCOUNT_MAX_ERROR_TIMES(41005, "错误次数过多"),
    ACCOUNT_MAX_ERROR_TIMES_NEED_IMG_CODE(41006, "错误次数过多，请输入图片验证码"),
    ACCOUNT_AUTHENTICATION_FAILED(41007, "认证失败"),
    ACCOUNT_UNAUTHENTICATED(41008, "未认证"),
    ACCOUNT_PHONE_OCCUPY(41009, "该手机号已注册"),
    ACCOUNT_PHONE_EMPTY(41010, "该手机号未注册"),
    ACCOUNT_INVITATECODE_ERROR(41011, "邀请码错误"),
    ACCOUNT_FAST_LOGIN_EMPTY(41012, "手机号或验证码不能为空"),
    ACCOUNT_LOGIN_EMPTY(41013, "手机号、密码、验证码不能为空"),
    ACCOUNT_CHANGE_PWD_ERROR(41014, "修改用户密码失败"),
    ACCOUNT_CHECK_PWD_ERROR(41015, "原密码不正确"),
    ACCOUNT_CHECK_OLDPWD_ERROR(41016, "旧密码不正确"),
    ACCOUNT_CHECK_PWDTWO_ERROR(41017, "两次密码不一致"),
    ACCOUNT_SIGNUP_ERROR(41018, "注册失败"),
    ACCOUNT_NOT_SIGNIN(41019, "用户未登录"),
    ACCOUNT_NOT_LEVELLOW(41020, "用户等级过低"),
    ACCOUNT_BINDING_ERROR(41021, "您不是普通用户,或者您已经被他人绑定"),
    ACCOUNT_BINDING_FAILED(41022,"您已经被他人绑定"),
    ACCOUNT_NO_LOGIN(41023, "该用户暂时无法登录"),
    ACCOUNT_BINDING_REPETIION(41024,"重复绑定"),
    ACCOUNT_LOGIN_ATTENTION_ERROR(41025, "未关注公众号"),
    TB_ACCOUNT_UNAUTHORIZED(41026, "淘宝未授权"),
    USER_EQUIPMENT_REPEAT(41027, "请更新APP版本后再参与活动，如需帮助请联系客服！"),
    /**
     *  jwt token验证
     */
    TOKEN_NOT_LAWFUL(52000, "token登录信息无法解析，请重新登录"),
    TOKEN_TIME_OUT(52001, "token 已过期"),
    TOKEN_FIELD_LOST(52002, "token 必要字段丢失"),
    TOKEN_REFRESH_FAILED(52003, "token refresh failed"),
    TOKEN_UNSIGN_FAILED(52004, "token 解析失败"),
    /**
     * sms
     */
    SMS_SEND_ERROR(50000, "短信发送失败"),
    SMS_CODE_DISABLE(50001, "验证码无效"),
    SMS_CODE_ERROR(50002, "短信验证码错误"),
    SMS_CODE_SENDSMSCOUNT(50003, "发送次数过多"),
    SMS_CODE_SENDSMSOFTEN(50004, "发送过于频繁"),

    /**
     * 阿里云实名认证
     */
    ALIYUN_VERUFY_ALREADY(60000, "用户已实人认证"),

    ALIYUN_VERUFY_EXCEED(60001, "用户实人认证已超过最大次数"),

    ALIYUN_VERUFY_UPDATE_FAIL(60002, "用户实人认证信息修改失败"),

    ALIYUN_VERUFY_INFO_FAIL(60003, "获取用户实人认证失败"),
    /**
     * 物流信息
     */
    EXPRESS_NAME_FAIL(70001, "查询物流公司名称不正确"),
    EXPRESS_FAIL(70002, "查询物流失败"),
    EXPRESS_ADDRESS_FAIL(70003, "不允许修改收货地址"),

    /**
     * 接口签名校验
     */
    SIGNATURE_INVALID(90001, "签名验证失败"),
    SIGNATURE_MATCH_FAILED(90002, "接口签名匹配失败"),
    SIGNATURE_TIMEOUT(90003, "接口签名过期，或者非法时间戳"),
    SIGNATURE_NONCE_REPEAT(90004, "重复的请求"),
    SIGNATURE_PARSE_FAILED(90005, "无法解析接口签名"),
    SIGNATURE_APPKEY_INVALID(90006, "无效的appKey"),
    INVALID_TICKET(90007, "无效的ticket"),

    /**
     * 下单
     */
    ORDER_VERIFICATION(91001,"请绑定邀请码后下单"),

    /**
     * 抽奖
     */
    STAMP_DIFFER(80001, "版本戳不一致"),
    LACK_CREDIT(80002, "积分不足"),

    /**
     * 支付
     */
    VALIDATION_FAILED(80010, "签名失败"),
    PAY_PARAMETER(80011, "支付参数错误"),

    /**
     * app提现
     */
    ORDINARY_USER_NOT(80020, "普通用户暂无领取收益权限"),
    NO_REACH_TIME(80021, "当前暂不支持发起领取,请于每月16日至18日期间发起"),
    NO_ALIPAY(80022, "您当前未绑定支付宝账号!请先绑定后再领取收益!"),
    NO_BANK(80023, "您的银行卡信息尚未完善!请完善后再领取收益!"),

    CheckFailure(900003, "已绑定");

    public final Integer value;
    public final String name;

    ApiCodeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{\"code\":" + this.value + ",\"message\":\"" + this.name + "\"}";
    }
}
