package com.rc.cloud.app.system.convert.logger;

import com.rc.cloud.app.system.api.logger.dto.LoginLogCreateReqDTO;
import com.rc.cloud.app.system.model.logger.LoginLogPO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogRespVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author rc@hqf
 * @date 2023/07/19
 * @description 登陆日志 Convert
 */
@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogPO> page);

    LoginLogPO convert(LoginLogCreateReqDTO bean);

}
