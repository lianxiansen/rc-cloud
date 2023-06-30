package com.rc.cloud.common.mybatis.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.rc.cloud.common.core.constant.SecurityConstants;
import com.rc.cloud.common.core.util.RequestUtils;
import com.rc.cloud.common.core.util.StringUtils;
import com.rc.cloud.common.core.web.util.WebFrameworkUtils;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通用参数填充实现类
 *
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author hexiaowu
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

            LocalDateTime current = LocalDateTime.now();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(current);
            }
            String username = StringUtils.EMPTY;
            Object attribute = RequestUtils.getRequest().getAttribute(SecurityConstants.LOGIN_USERNAME);
            if (attribute != null) {
                username = attribute.toString();
            }
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(username) && Objects.isNull(baseDO.getCreator())) {
                baseDO.setCreator(username);
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(username) && Objects.isNull(baseDO.getUpdater())) {
                baseDO.setUpdater(username);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object modifier = getFieldValByName("updater", metaObject);
        String username = StringUtils.EMPTY;
        Object attribute = RequestUtils.getRequest().getAttribute(SecurityConstants.LOGIN_USERNAME);
        if (attribute != null) {
            username = attribute.toString();
        }
        if (Objects.nonNull(username) && Objects.isNull(modifier)) {
            setFieldValByName("updater", username, metaObject);
        }
    }
}
