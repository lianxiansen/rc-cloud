package com.rc.cloud.app.distributor.application.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.distributor.appearance.facade.admin.req.DistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorContactMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.app.distributor.infrastructure.util.CommonUtil;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author WJF
 * @create 2023-06-27 8:41
 * @description TODO
 */
@Service
public class DistributorContactServiceImpl extends ServiceImpl<DistributorContactMapper, DistributorContactPO>
        implements DistributorContactService {

    @Resource
    private DistributorContactMapper contactMapper;

    @Autowired
    private PasswordEncoder webPasswordEncoder;

    /**
     * 批量更新经销商联系人
     * 执行新增和删除。对于已经绑定的联系人，不做任何处理
     * @param distributorId 经销商id
     * @param contactDOS 经销商联系人列表
     * @return
     * */
    @Transactional
    public void updateContacts(String distributorId, List<DistributorContactPO> contactDOS) {
        //需要更新的数据
        List<String> newMobiles = contactDOS.stream().map(DistributorContactPO::getMobile).collect(Collectors.toList());
        //如果联系方式重复，则抛出异常
        if (newMobiles.size() != newMobiles.stream().distinct().count()) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_CONTACT_PHONE_DUPLICATE);
        }
        //如果联系方式已被绑定，抛出异常
        if (contactMapper.exists(new LambdaQueryWrapperX<DistributorContactPO>()
                .inIfPresent(DistributorContactPO::getMobile, newMobiles)
                .and(wq -> wq.ne(DistributorContactPO::getDistributorId, distributorId)))) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_CONTACT_PHONE_EXIST);
        }

        //已存在的数据
        List<DistributorContactPO> allDOList = contactMapper.selectList(DistributorContactPO::getDistributorId, distributorId);
        List<String> oldMobiles = allDOList.stream().map(DistributorContactPO::getMobile).collect(Collectors.toList());

        //删除旧联系人的数据
        List<String> olddata = ListUtils.subtract(oldMobiles, newMobiles);
        if (olddata.size() > 0) {
            contactMapper.delete(new LambdaQueryWrapperX<DistributorContactPO>()
                    .in(DistributorContactPO::getMobile, olddata)
                    .and(wq -> wq.eq(DistributorContactPO::getDistributorId, distributorId))
            );
        }

        //新插入的数据，手机号设置成后6位
        List<String> newdata = ListUtils.subtract(newMobiles, oldMobiles);
        List<DistributorContactPO> newlist = contactDOS.stream().filter(x -> newdata.contains(x.getMobile())).collect(Collectors.toList());
        newlist.forEach(x -> {
            x.setPassword(encodePassword(CommonUtil.getFinalMobile(x.getMobile())));
            x.setDistributorId(distributorId);
        });
        contactMapper.insertBatch(newlist);
    }

    @Override
    public List<DistributorContactPO> getByDistributorId(String distributorId) {
        return contactMapper.selectList(new LambdaQueryWrapperX<DistributorContactPO>()
                .eq(DistributorContactPO::getDistributorId, distributorId));
    }

    @Override
    public DistributorContactPO getByMobile(String mobile) {
        return contactMapper.selectOne(new LambdaQueryWrapperX<DistributorContactPO>().eq(DistributorContactPO::getMobile, mobile));
    }

    @Override
    public void updatePassword(DistributorContactUpdatePasswordReqVO updatePasswordReqVO) {
        DistributorContactPO contactDO = getBaseMapper().selectById(updatePasswordReqVO.getId());
        contactDO.setPassword(encodePassword(updatePasswordReqVO.getPassword()));
        getBaseMapper().updateById(contactDO);
    }

    /*
     * description 重置密码，手机号后6位
     */
    @Override
    public void resetPassword(String id) {
        DistributorContactPO contactDO = contactMapper.selectById(id);
        contactDO.setPassword(encodePassword(CommonUtil.getFinalMobile(contactDO.getMobile())));
        contactMapper.updateById(contactDO);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return webPasswordEncoder.encode(password);
    }
}
