package com.rc.cloud.app.system.service.oauthclient;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rc.cloud.app.system.convert.client.OAuthClientDetailsConvert;
import com.rc.cloud.app.system.mapper.oauthclient.OauthClientDetailsMapper;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.rc.cloud.app.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_EXISTS;
import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 客户端信息服务类
 */
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public PageResult<SysOauthClientDetailsPO> getPage(OauthClientDetailsPageReqVO reqVO) {
        return oauthClientDetailsMapper.selectPage(reqVO);
    }

    @Override
    public SysOauthClientDetailsPO getOne(LambdaQueryWrapper<SysOauthClientDetailsPO> queryWrapper) {
        return oauthClientDetailsMapper.selectOne(queryWrapper);
    }

    @Override
    public void removeClientDetailsById(String id) {
        oauthClientDetailsMapper.deleteById(id);
    }

    @Override
    public SysOauthClientDetailsPO getById(String clientId) {
        return oauthClientDetailsMapper.selectById(clientId);
    }

    @Override
    public String create(OauthClientDetailsCreateReqVO reqVO) {
        SysOauthClientDetailsPO sysOauthClientDetailsPO = oauthClientDetailsMapper.selectByClientId(reqVO.getClientId());
        if (BeanUtil.isNotEmpty(sysOauthClientDetailsPO)) {
            throw exception(OAUTH2_CLIENT_EXISTS);
        }
        SysOauthClientDetailsPO po = OAuthClientDetailsConvert.INSTANCE.convert(reqVO);
        int insertRes = oauthClientDetailsMapper.insert(po);
        if (insertRes > 0) {
            return po.getId();
        }
        return "";
    }

    @Override
    public void update(OauthClientDetailsUpdateReqVO reqVO) {
        vaildateCanUpdate(reqVO);
        SysOauthClientDetailsPO po = OAuthClientDetailsConvert.INSTANCE.convert(reqVO);
        oauthClientDetailsMapper.updateById(po);
    }

    private void vaildateCanUpdate(OauthClientDetailsUpdateReqVO reqVO) {
        SysOauthClientDetailsPO sysOauthClientDetailsPO = oauthClientDetailsMapper.selectByClientId(reqVO.getClientId());
        if (BeanUtil.isNotEmpty(sysOauthClientDetailsPO) && !sysOauthClientDetailsPO.getId().equals(reqVO.getId())) {
            throw exception(OAUTH2_CLIENT_EXISTS);
        }
    }


//  /**
//   * 清除客户端缓存
//   */
//  @Override
//  @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
//  public void clearClientCache() {
//
//  }

}
