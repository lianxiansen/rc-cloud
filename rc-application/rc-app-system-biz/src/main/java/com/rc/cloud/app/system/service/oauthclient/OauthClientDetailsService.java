package com.rc.cloud.app.system.service.oauthclient;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rc.cloud.app.system.model.oauthclient.SysOauthClientDetailsPO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsCreateReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsPageReqVO;
import com.rc.cloud.app.system.vo.client.OauthClientDetailsUpdateReqVO;
import com.rc.cloud.common.core.pojo.PageResult;

/**
 * @author rc@hqf
 * @date 2023/07/14
 * @description 客户端信息服务类
 */
public interface OauthClientDetailsService {

    /**
     * 获得授权客户端分页列表
     *
     * @param reqVO 分页条件
     * @return 授权客户端分页列表
     */
    PageResult<SysOauthClientDetailsPO> getPage(OauthClientDetailsPageReqVO reqVO);

    /**
     * 通过条件查询单个客户端信息
     *
     * @param queryWrapper 查询条件
     * @return 客户端信息
     */
    SysOauthClientDetailsPO getOne(LambdaQueryWrapper<SysOauthClientDetailsPO> queryWrapper);

    /**
     * 通过id删除
     *
     * @param id id
     */
    void removeClientDetailsById(String id);

    /**
     * 通过id查询
     *
     * @param clientId id
     * @return 客户端信息
     */
    SysOauthClientDetailsPO getById(String clientId);

    /**
     * 创建客户端
     *
     * @param reqVO 客户端信息
     * @return 客户端id
     */
    String create(OauthClientDetailsCreateReqVO reqVO);

    /**
     * 更新客户端
     *
     * @param reqVO 客户端信息
     */
    void update(OauthClientDetailsUpdateReqVO reqVO);
//  /**
//   * 清除客户端缓存
//   */
//  void clearClientCache();
}
