package com.rc.cloud.app.operate.appearance.response;

import com.alibaba.druid.util.StringUtils;
import com.rc.cloud.app.operate.infrastructure.util.InetAddressUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.MDC;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:chenjianxiang
 * @Date 2021/2/9
 * @Description:
 */
public class DataPack {

    /**
     * 状态码
     */
    public static final String CODE = "error_code";

    /**
     * 请求成功返回数据
     */
    public static final String DATA = "data";

    /**
     * 请求成功,返回列表数据类型
     */
    public static final String DATA_LIST = "list";

    /**
     * 返回列表类型时,表示数据总量
     */
    public static final String DATA_SIZE = "total";

    /**
     * 请求返回信息提示
     */
    public static final String MSG = "error_msg";
//    public static final String MSG = "error_message";

    /**
     * 请求id
     */
    private static final String REQUEST_ID = "request_id";
    private static final String REQUEST_ID_PREFIX = InetAddressUtils.getLocalHostName().toLowerCase();

    private DataPack() {
        throw new UnsupportedOperationException("DataPack.class can not be construct to a instance");
    }

    /**
     * 请求成功结果包装
     *
     * @return
     */
    public static ModelMap packOk() {
        return packResult(ApiCodeEnum.SUCCESS);
    }

    /**
     * 请求结果失败包装
     *
     * @return
     */
    public static ModelMap packError() {
        return packResult(ApiCodeEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 请求成功结果包装
     *
     * @return
     */
    public static ModelMap packOkMsg(String msg) {
        return packResult(ApiCodeEnum.SUCCESS, msg);
    }

    /**
     * 请求成功返回结果包装，并且返回数据是一个List
     *
     * @param list
     * @return list
     */
    public static ModelMap packOkList(List<?> list) {
        return packOkList(list, CollectionUtils.size(list));
    }

    /**
     * 请求成功结果包装，并且返回数据是一个List
     *
     * @param list  返回数据，List
     * @param total 数据总数
     * @return
     */
    public static ModelMap packOkList(List<?> list, int total) {
        Map<String, Object> result = new HashMap<>();
        if (list != null) {
            result.put(DATA_LIST, list);
        }
        result.put(DATA_SIZE, total);
        return packOk(result);
    }

    /**
     * 请求成功结果包装
     *
     * @param result 返回数据结果，为{@code null}时忽略
     * @return
     */
    public static ModelMap packOk(Object result) {
        return packResult(ApiCodeEnum.SUCCESS, ApiCodeEnum.SUCCESS.name, result);
    }

    public static ModelMap packResult(ApiCodeEnum code) {
        return packResult(code, code.name);
    }

    public static ModelMap packResult(ApiCodeEnum code, String msg) {
        return packResult(code, msg, null);
    }

    public static ModelMap packResult(ApiCodeEnum code, Object result) {
        return packResult(code, code.name, result);
    }

    public static ModelMap packResult(ApiCodeEnum code, String msg, Object result) {
        return packResult(code.value,msg,result);
    }

    public static ModelMap packResult(Integer code, String msg, Object result)
    {
        ModelMap model = new ModelMap();
        model.put(CODE, code);
        if (!StringUtils.isEmpty(msg)) {
            model.put(MSG, msg);
        }
        if (null != result) {
            model.put(DATA,result);
        }
        if (StringUtils.isEmpty(MDC.get(REQUEST_ID))) {
            MDC.put(REQUEST_ID, REQUEST_ID_PREFIX + UUID.randomUUID());
        }
        model.put(REQUEST_ID, MDC.get(REQUEST_ID));
        return model;
    }


    /**
     * 请求参数错误返回包装
     *
     * @param errorMsg 错误信息
     * @return
     */
    public static ModelMap packBadRequest(String errorMsg) {
        return packResult(ApiCodeEnum.INVALID_PARAMETERS, errorMsg, null);
    }

    /**
     * 内部错误返回包装
     *
     * @param errorMsg 错误信息
     * @return
     */
    public static ModelMap packInternalError(String errorMsg) {
        return packResult(ApiCodeEnum.INTERNAL_SERVER_ERROR, errorMsg, null);
    }

    /**
     * 权限不足返回包装
     *
     * @param errorMsg 错误信息
     * @return
     */
    public static ModelMap packForbidden(String errorMsg) {
        return packResult(ApiCodeEnum.FORBIDDEN, errorMsg, null);
    }

    /**
     * 权限不足返回包装
     *
     * @return
     */
    public static ModelMap packForbidden() {
        return packResult(ApiCodeEnum.FORBIDDEN);
    }
}
