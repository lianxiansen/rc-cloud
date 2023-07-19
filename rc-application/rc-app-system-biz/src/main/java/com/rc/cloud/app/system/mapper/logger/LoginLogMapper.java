package com.rc.cloud.app.system.mapper.logger;

import com.rc.cloud.app.system.enums.login.LoginResultEnum;
import com.rc.cloud.app.system.model.logger.LoginLogPO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogExportReqVO;
import com.rc.cloud.app.system.vo.logger.loginlog.LoginLogPageReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.mybatis.core.mapper.BaseMapperX;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogPO> {

    default PageResult<LoginLogPO> selectPage(LoginLogPageReqVO reqVO) {
        LambdaQueryWrapperX<LoginLogPO> query = new LambdaQueryWrapperX<LoginLogPO>()
                .likeIfPresent(LoginLogPO::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLogPO::getUsername, reqVO.getUsername())
                .betweenIfPresent(LoginLogPO::getCreateTime, reqVO.getCreateTime());
        if (Boolean.TRUE.equals(reqVO.getStatus())) {
            query.eq(LoginLogPO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
            query.gt(LoginLogPO::getResult, LoginResultEnum.SUCCESS.getResult());
        }
        query.orderByDesc(LoginLogPO::getId); // 降序
        return selectPage(reqVO, query);
    }

    default List<LoginLogPO> selectList(LoginLogExportReqVO reqVO) {
        LambdaQueryWrapperX<LoginLogPO> query = new LambdaQueryWrapperX<LoginLogPO>()
                .likeIfPresent(LoginLogPO::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLogPO::getUsername, reqVO.getUsername())
                .betweenIfPresent(LoginLogPO::getCreateTime, reqVO.getCreateTime());
        if (Boolean.TRUE.equals(reqVO.getStatus())) {
            query.eq(LoginLogPO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
            query.gt(LoginLogPO::getResult, LoginResultEnum.SUCCESS.getResult());
        }
        query.orderByDesc(LoginLogPO::getId); // 降序
        return selectList(query);
    }
}
