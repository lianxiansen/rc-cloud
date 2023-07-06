package com.rc.cloud.app.system.api.dept.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rc.cloud.app.system.api.user.entity.SysUserVO;
import com.rc.cloud.common.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和岗位关联
 *
 * @author ruoyi
 */
@TableName("sys_user_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPostDO extends BaseDO {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户 ID
     *
     * 关联 {@link SysUserVO#getId()}
     */
    private Long userId;
    /**
     * 角色 ID
     *
     * 关联 {@link SysPostDO#getId()}
     */
    private Long postId;

}
