package com.rc.cloud.app.distributor.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rc.cloud.app.distributor.appearance.req.DistributorCreateReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorExportReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorPageReqVO;
import com.rc.cloud.app.distributor.appearance.req.DistributorUpdateReqVO;
import com.rc.cloud.app.distributor.application.convert.DistributorContactConvert;
import com.rc.cloud.app.distributor.application.convert.DistributorConvert;
import com.rc.cloud.app.distributor.application.convert.DistributorDetailConvert;
import com.rc.cloud.app.distributor.application.event.DistributorDeleteEvent;
import com.rc.cloud.app.distributor.application.service.DistributorContactService;
import com.rc.cloud.app.distributor.infrastructure.config.DistributorErrorCodeConstants;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorDetailMapper;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorContactPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorPO;
import com.rc.cloud.app.distributor.infrastructure.persistence.mapper.DistributorMapper;
import com.rc.cloud.app.distributor.application.service.DistributorService;
import com.rc.cloud.app.distributor.infrastructure.persistence.po.DistributorDetailPO;
import com.rc.cloud.app.distributor.infrastructure.util.CommonUtil;
import com.rc.cloud.app.distributor.infrastructure.util.SpringContextHolder;
import com.rc.cloud.common.core.pojo.PageResult;
import com.rc.cloud.common.core.util.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import static com.rc.cloud.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 经销商 Service 实现类
 *
 * @author wjf
 */
@Service
@Validated
public class DistributorServiceImpl implements DistributorService {

    @Resource
    private DistributorMapper mapper;

    @Resource
    private DistributorDetailMapper detailMapper;

    @Resource
    private DistributorContactService contactService;

    @Resource
    private PasswordEncoder webPasswordEncoder;

    @Override
    @Transactional
    public Long create(DistributorCreateReqVO createReqVO) {
        // 插入
        DistributorPO distributorPO = DistributorConvert.INSTANCE.convert(createReqVO);

        DistributorDetailPO distributorDetailPO = DistributorDetailConvert.INSTANCE.convert(createReqVO);
        List<DistributorContactPO> contactDOList = DistributorContactConvert.INSTANCE.convertList(createReqVO.getContacts());

        if (contactDOList.size()>0){
            List<String> names = contactDOList.stream().map(x -> x.getName()).distinct().collect(Collectors.toList());
            List<String> mobiles = contactDOList.stream().map(x -> x.getMobile()).distinct().collect(Collectors.toList());
            distributorPO.setContact(StringUtils.join(names,","));
            distributorPO.setMobile(StringUtils.join(mobiles,","));
        }
        mapper.insert(distributorPO);
        distributorDetailPO.setDistributorId(distributorPO.getId());
        //插入明细表
        detailMapper.insert(distributorDetailPO);
        contactDOList.forEach(x -> {
            x.setDistributorId(distributorPO.getId());
            x.setPassword(webPasswordEncoder.encode(CommonUtil.getFinalMobile(x.getMobile())));
        });
        contactService.updateContacts(distributorPO.getId(), contactDOList);
        // 返回
        return distributorPO.getId();
    }

    @Override
    @Transactional
    public void update(DistributorUpdateReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());

        DistributorPO updateObj = DistributorConvert.INSTANCE.convert(updateReqVO);
        DistributorDetailPO detailObj = DistributorDetailConvert.INSTANCE.convert(updateReqVO);
        List<DistributorContactPO> contacts = DistributorContactConvert.INSTANCE.convertList2(updateReqVO.getContacts());

        if (contacts.size()>0){
            List<String> names = contacts.stream().map(x -> x.getName()).distinct().collect(Collectors.toList());
            List<String> mobiles = contacts.stream().map(x -> x.getMobile()).distinct().collect(Collectors.toList());
            updateObj.setContact(StringUtils.join(names,","));
            updateObj.setMobile(StringUtils.join(mobiles,","));
        }
        // 更新主表
        mapper.updateById(updateObj);

        Wrapper<DistributorDetailPO> detailDOQueryWrapper = new QueryWrapper<DistributorDetailPO>().lambda()
                .eq(DistributorDetailPO::getDistributorId, updateReqVO.getId());
        DistributorDetailPO detailDO = detailMapper.selectOne(detailDOQueryWrapper);
        if (detailDO == null) {//如果存在原有记录
            //插入明细表
            detailObj = new DistributorDetailPO();
            detailObj.setDistributorId(updateReqVO.getId());
            detailObj.setDistributorDetail(updateReqVO.getDistributorDetail());
            detailMapper.insert(detailObj);
        } else {
            detailObj.setId(detailDO.getId());
            //更新明细表
            detailMapper.updateById(detailObj);
        }
        //更新联系人表
        contactService.updateContacts(updateReqVO.getId(), contacts);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        mapper.deleteById(id);
        DistributorDeleteEvent deleteEvent=new DistributorDeleteEvent(id);
        SpringContextHolder.publishEvent(deleteEvent);
    }

    private void validateExists(Long id) {
        if (mapper.selectById(id) == null) {
            throw exception(DistributorErrorCodeConstants.DISTRIBUTOR_NOT_EXISTS);
        }
    }

    @Override
    public DistributorPO get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<DistributorPO> getAll() {
        return mapper.selectList();
    }

    @Override
    public DistributorDetailPO getDetail(Long id) {
        // 校验存在
        validateExists(id);
        return detailMapper.selectByDistributorId(id);
    }

    @Override
    public List<DistributorPO> getList(Collection<Long> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DistributorPO> getPage(DistributorPageReqVO pageReqVO) {
        return mapper.selectPage(pageReqVO);
    }

    @Override
    public List<DistributorPO> getList(DistributorExportReqVO exportReqVO) {
        return mapper.selectList(exportReqVO);
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
