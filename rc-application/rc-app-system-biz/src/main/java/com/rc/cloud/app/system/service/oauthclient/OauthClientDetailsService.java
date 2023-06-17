package com.rc.cloud.app.system.service.oauthclient;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.cloud.app.system.api.oauthclient.entity.SysOauthClientDetailsDO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
public interface OauthClientDetailsService extends IService<SysOauthClientDetailsDO> {

	/**
	 * 通过ID删除客户端
	 * @param id
	 * @return
	 */
	Boolean removeClientDetailsById(String id);

	/**
	 * 修改客户端信息
	 * @param sysOauthClientDetails
	 * @return
	 */
	Boolean updateClientDetailsById(SysOauthClientDetailsDO sysOauthClientDetails);

	/**
	 * 清除客户端缓存
	 */
	void clearClientCache();

}
