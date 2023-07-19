package com.rc.cloud.app.system.service.logger;

import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.convert.logger.LoginLogConvert;
import com.rc.cloud.app.system.mapper.logger.LoginLogMapper;
import com.rc.cloud.app.system.model.logger.LoginLogPO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogExportReqVO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rc@hqf
 * @date 2023/07/19
 * @description 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public PageResult<LoginLogPO> getLoginLogPage(LoginLogPageReqVO reqVO) {
        return loginLogMapper.selectPage(reqVO);
    }

    @Override
    public List<LoginLogPO> getLoginLogList(LoginLogExportReqVO reqVO) {
        return loginLogMapper.selectList(reqVO);
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogPO loginLog = LoginLogConvert.INSTANCE.convert(reqDTO);
        loginLogMapper.insert(loginLog);
    }

}
