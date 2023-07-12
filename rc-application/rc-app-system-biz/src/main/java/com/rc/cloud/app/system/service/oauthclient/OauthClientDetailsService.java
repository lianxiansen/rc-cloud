package com.rc.cloud.app.system.service.oauthclient;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
public interface OauthClientDetailsService {

	/**
	 * 获得授权客户端分页列表
	 *
	 * @param reqVO 分页条件
	 * @return 授权客户端分页列表
	 */
	PageResult<SysOauthClientDetailsPO> getPage(OauthClientDetailsPageReqVO reqVO);

	SysOauthClientDetailsPO getOne(LambdaQueryWrapper<SysOauthClientDetailsPO> queryWrapper);

	/**
	 * 通过id删除
	 * @param id id
	 */
    void removeClientDetailsById(String id);

	SysOauthClientDetailsPO getById(String clientId);

	String create(OauthClientDetailsCreateReqVO reqVO);

	void update(OauthClientDetailsUpdateReqVO reqVO);
//	/**
//	 * 清除客户端缓存
//	 */
//	void clearClientCache();

}
