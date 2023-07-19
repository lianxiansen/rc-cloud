package com.rc.cloud.app.system.service.logger;


import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.model.logger.LoginLogPO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogExportReqVO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

import javax.validation.Valid;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/19
 * @description 登录日志 Service 接口
 */
public interface LoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param reqVO 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLogPO> getLoginLogPage(LoginLogPageReqVO reqVO);

    /**
     * 获得登录日志列表
     *
     * @param reqVO 列表条件
     * @return 登录日志列表
     */
    List<LoginLogPO> getLoginLogList(LoginLogExportReqVO reqVO);

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
