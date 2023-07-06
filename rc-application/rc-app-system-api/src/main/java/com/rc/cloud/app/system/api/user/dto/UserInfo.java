package com.rc.cloud.app.system.api.user.dto;

import com.rc.cloud.app.system.api.dept.entity.SysPostDO;
import com.rc.cloud.app.system.api.permission.entity.SysRoleDO;
import com.rc.cloud.app.system.api.user.entity.SysUserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author oliveoil
 * @date 2023-06-16
 */
@Data
public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	private SysUserVO sysUser;

	/**
	 * 权限标识集合
	 */
	private String[] permissions;

	/**
	 * 角色集合
	 */
	private Long[] roles;

	/**
	 * 角色集合
	 */
	private List<SysRoleDO> roleList;

	/**
	 * 岗位集合
	 */
	private Long[] posts;

	/**
	 * 岗位集合
	 */
	private List<SysPostDO> postList;

}
