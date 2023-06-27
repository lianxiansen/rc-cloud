package com.rc.cloud.app.distributor.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.cloud.app.distributor.appearance.req.AppDistributorContactUpdatePasswordReqVO;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorContactMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactDO;
import com.rc.cloud.app.distributor.infrastructure.util.CommonUtil;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.mybatis.core.query.LambdaQueryWrapperX;
import com.rc.cloud.common.mybatis.core.query.QueryWrapperX;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author WJF
 * @create 2023-06-27 8:41
 * @description TODO
 */
@Service
public class DistributorContactServiceImpl extends ServiceImpl<DistributorContactMapper,DistributorContactDO> implements DistributorContactService {

    @Autowired
    private DistributorContactMapper contactMapper;

    @Autowired
    private PasswordEncoder webPasswordEncoder;

    //批量更新经销商联系人
    // 执行新增和删除。对于已经绑定的联系人，不做任何处理
    @Transactional
    public void updateContacts(Long distributorId, List<DistributorContactDO> contactDOS) {
        //需要更新的数据
        List<String> newMobiles = contactDOS.stream().map(x -> x.getMobile()).collect(Collectors.toList());
        //如果联系方式重复，则抛出异常
        if (newMobiles.stream().count() != newMobiles.stream().distinct().count()) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_CONTACT_PHONE_DUPLICATE);
        }
        //如果联系方式已被绑定，抛出异常
        DistributorContactDO contactDO = contactMapper.selectOne(new QueryWrapper<DistributorContactDO>().lambda()
                .in(DistributorContactDO::getMobile, newMobiles));
        if (contactDO != null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_CONTACT_PHONE_EXIST);
        }

        //已存在的数据
        List<DistributorContactDO> allDOList = contactMapper.selectList(DistributorContactDO::getDistributorId, distributorId);
        List<String> oldMobiles = allDOList.stream().map(x -> x.getMobile()).collect(Collectors.toList());

        //删除旧联系人的数据
        List<String> olddata = ListUtils.subtract(oldMobiles, newMobiles);
        contactMapper.delete(new QueryWrapper<DistributorContactDO>().lambda()
                .in(DistributorContactDO::getMobile, olddata)
                .and(wq -> wq.eq(DistributorContactDO::getDistributorId, distributorId))
        );

        //新插入的数据，手机号设置成后6位
        List<String> newdata = ListUtils.subtract(newMobiles, oldMobiles);
        List<DistributorContactDO> newlist = contactDOS.stream().filter(x -> newdata.contains(x.getMobile())).collect(Collectors.toList());
        newlist.forEach(x->{
            x.setPassword(encodePassword(CommonUtil.getFinalMobile(x.getMobile())));
        });
        contactMapper.insertBatch(newlist);
    }

    @Override
    public List<DistributorContactDO> getByDistributorId(Long distributorId) {
        return contactMapper.selectList(new LambdaQueryWrapperX<DistributorContactDO>().eq(DistributorContactDO::getDistributorId,distributorId));
    }

    @Override
    public void updatePassword(AppDistributorContactUpdatePasswordReqVO updatePasswordReqVO) {
        DistributorContactDO contactDO= contactMapper.selectOne(new QueryWrapperX<DistributorContactDO>().lambda().eq(DistributorContactDO::getId,updatePasswordReqVO.getId()));
        contactDO.setPassword(encodePassword(updatePasswordReqVO.getPassword()));
        contactMapper.updateById(contactDO);
    }

    /*
     * 重置密码，手机号后6位
     */
    @Override
    public void resetPassword(Long id) {
        DistributorContactDO contactDO= contactMapper.selectOne(new QueryWrapperX<DistributorContactDO>().lambda().eq(DistributorContactDO::getId,id));
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
