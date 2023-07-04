

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerName', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerName', 'TRIGGER_ACCESS');


-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerName', 'PC-20211219OMLM1688366319097', 1688368516934, 15000);

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, '柔川信息', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-06-12 17:03:49', '0', 1);
INSERT INTO `sys_dept` VALUES (101, '黄岩总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', NULL, '2023-06-27 13:32:23', '0', 1);
INSERT INTO `sys_dept` VALUES (102, '杭州分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2023-06-12 17:04:07', '0', 1);
INSERT INTO `sys_dept` VALUES (103, '研发部门', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', NULL, '2023-06-27 13:32:18', '0', 1);
INSERT INTO `sys_dept` VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', '0', 1);
INSERT INTO `sys_dept` VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-06-27 13:32:56', '0', 1);
INSERT INTO `sys_dept` VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', '0', 1);
INSERT INTO `sys_dept` VALUES (107, '运维部门', 101, 5, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:33', '0', 1);
INSERT INTO `sys_dept` VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', '0', 1);
INSERT INTO `sys_dept` VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', '0', 1);
INSERT INTO `sys_dept` VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', '0', 121);
INSERT INTO `sys_dept` VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', '0', 122);
INSERT INTO `sys_dept` VALUES (112, '测试部门001', 0, 0, NULL, NULL, NULL, 0, 'admin', '2023-06-30 17:14:29', 'admin', '2023-06-30 17:17:48', '1', 1);


-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1236, 0, '未知', '0', 'user_sex', 0, 'warning', '', '未知', NULL, '2023-06-28 14:01:14', NULL, '2023-06-28 15:16:37', '0');
INSERT INTO `sys_dict_data` VALUES (1237, 1, '男', '1', 'user_sex', 0, 'primary', '', '', NULL, '2023-06-28 14:01:34', NULL, '2023-06-28 15:16:44', '0');
INSERT INTO `sys_dict_data` VALUES (1238, 2, '女', '2', 'user_sex', 0, 'danger', '', '女', NULL, '2023-06-28 14:01:53', NULL, '2023-06-28 15:16:53', '0');
INSERT INTO `sys_dict_data` VALUES (1239, 0, '正常', '0', 'common_status', 0, 'success', '', '', NULL, '2023-06-28 15:19:30', NULL, '2023-06-28 15:19:30', '0');
INSERT INTO `sys_dict_data` VALUES (1240, 1, '停用', '1', 'common_status', 0, 'danger', '', '', NULL, '2023-06-28 15:19:43', NULL, '2023-06-28 15:19:43', '0');


-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (170, '性别', 'user_sex', 0, '用户管理', NULL, '2023-06-28 14:00:25', NULL, '2023-06-28 14:00:25', '0');
INSERT INTO `sys_dict_type` VALUES (171, '通用状态', 'common_status', 0, '通用状态', NULL, '2023-06-28 15:18:51', NULL, '2023-06-28 15:18:51', '0');


-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (2162, '产品', '', 1, 0, 0, NULL, '', 'product', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:29:01', NULL, '2023-06-27 14:06:53', '0', 1);
INSERT INTO `sys_menu` VALUES (2163, '品牌', '', 1, 1, 0, NULL, NULL, 'brand', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:36:38', NULL, '2023-06-27 14:06:54', '0', 1);
INSERT INTO `sys_menu` VALUES (2164, '经销商', '', 1, 2, 0, NULL, NULL, 'dealer', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:37:26', NULL, '2023-06-27 14:06:55', '0', 1);
INSERT INTO `sys_menu` VALUES (2165, '经营', '', 1, 3, 0, NULL, NULL, 'operate', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:38:05', NULL, '2023-06-27 14:06:55', '0', 1);
INSERT INTO `sys_menu` VALUES (2166, '数据', '', 1, 4, 0, NULL, NULL, 'data', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:38:32', NULL, '2023-06-27 14:06:56', '0', 1);
INSERT INTO `sys_menu` VALUES (2167, '内容', '', 1, 5, 0, NULL, NULL, 'content', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:38:57', NULL, '2023-06-27 14:06:56', '0', 1);
INSERT INTO `sys_menu` VALUES (2168, '系统', '', 1, 6, 0, NULL, NULL, NULL, NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:39:12', NULL, '2023-06-27 14:06:57', '0', 1);
INSERT INTO `sys_menu` VALUES (2169, '权限管理', '', 2, 0, 2168, NULL, 'icon-safetycertificate', '', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:42:10', NULL, '2023-06-27 14:06:58', '0', 1);
INSERT INTO `sys_menu` VALUES (2170, '系统设置', '', 2, 1, 2168, NULL, 'icon-setting', NULL, NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:43:22', NULL, '2023-06-27 14:06:59', '0', 1);
INSERT INTO `sys_menu` VALUES (2171, '日志管理', '', 2, 2, 2168, NULL, 'icon-filedone', NULL, NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:44:25', NULL, '2023-06-27 14:48:42', '0', 1);
INSERT INTO `sys_menu` VALUES (2172, '在线开发', '', 2, 3, 2168, NULL, 'icon-cloud', NULL, NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:45:17', NULL, '2023-06-27 14:07:00', '0', 1);
INSERT INTO `sys_menu` VALUES (2173, '用户管理', '', 2, 0, 2169, NULL, 'icon-user', 'sys/user/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 16:58:30', NULL, '2023-06-27 14:07:00', '0', 1);
INSERT INTO `sys_menu` VALUES (2174, '部门管理', '', 2, 1, 2169, NULL, 'icon-cluster', 'sys/dept/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 17:07:26', NULL, '2023-06-30 17:26:44', '0', 1);
INSERT INTO `sys_menu` VALUES (2175, '岗位管理', '', 2, 2, 2169, NULL, 'icon-solution', 'sys/post/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 17:08:11', NULL, '2023-06-27 14:07:02', '0', 1);
INSERT INTO `sys_menu` VALUES (2176, '角色管理', '', 2, 3, 2169, NULL, 'icon-team', 'sys/role/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-26 17:09:04', NULL, '2023-06-27 14:07:03', '0', 1);
INSERT INTO `sys_menu` VALUES (2177, '查询', 'sys:user:query', 3, 0, 2173, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-26 17:13:58', NULL, '2023-06-28 10:11:49', '0', 1);
INSERT INTO `sys_menu` VALUES (2178, '新增', 'sys:user:create', 3, 1, 2173, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-26 17:15:34', NULL, '2023-06-27 17:06:28', '0', 1);
INSERT INTO `sys_menu` VALUES (2179, '修改', 'sys:user:update', 3, 2, 2173, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-26 17:16:07', NULL, '2023-06-27 17:06:53', '0', 1);
INSERT INTO `sys_menu` VALUES (2180, '删除', 'sys:user:delete', 3, 3, 2173, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-26 17:16:23', NULL, '2023-06-27 16:52:14', '0', 1);
INSERT INTO `sys_menu` VALUES (2181, '菜单管理', '', 2, 0, 2170, NULL, 'icon-menu', 'sys/menu/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-27 13:49:20', NULL, '2023-06-27 14:07:06', '0', 1);
INSERT INTO `sys_menu` VALUES (2182, '数据字典', '', 2, 1, 2170, NULL, 'icon-insertrowabove', 'sys/dict/type', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-27 13:50:05', NULL, '2023-06-27 14:07:07', '0', 1);
INSERT INTO `sys_menu` VALUES (2183, '附件管理', '', 2, 3, 2170, NULL, 'icon-folder-fill', 'sys/attachment/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-27 13:50:43', NULL, '2023-06-27 14:07:07', '0', 1);
INSERT INTO `sys_menu` VALUES (2184, '参数管理', '', 2, 2, 2170, NULL, 'icon-filedone', 'sys/params/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-27 13:51:41', NULL, '2023-06-27 14:07:08', '0', 1);
INSERT INTO `sys_menu` VALUES (2185, '接口文档', '', 2, 10, 2170, NULL, 'icon-file-text-fill', '{{apiUrl}}/doc.html', NULL, 0, '1', '1', '1', 1, NULL, '2023-06-27 13:52:12', NULL, '2023-06-27 14:07:11', '0', 1);
INSERT INTO `sys_menu` VALUES (2186, '日志列表', '', 2, 1, 2171, NULL, '', '', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-27 14:48:00', NULL, '2023-06-27 14:50:35', '1', 1);
INSERT INTO `sys_menu` VALUES (2187, '日志列表', '', 0, 1, 2171, NULL, 'icon-rotate-left', 'product', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-27 16:15:41', NULL, '2023-06-27 16:15:52', '1', 1);
INSERT INTO `sys_menu` VALUES (2188, '重置密码', 'sys:user:update-password', 3, 4, 2173, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-27 16:51:37', NULL, '2023-06-27 16:51:37', '0', 1);
INSERT INTO `sys_menu` VALUES (2189, '查询', 'sys:dept:query', 3, 0, 2174, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-27 17:07:48', NULL, '2023-06-27 17:07:48', '0', 1);
INSERT INTO `sys_menu` VALUES (2190, '新增', 'sys:dept:create', 3, 1, 2174, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-27 17:08:49', NULL, '2023-06-27 17:08:49', '0', 1);
INSERT INTO `sys_menu` VALUES (2191, '修改', 'sys:dept:update', 3, 2, 2174, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-27 17:09:44', NULL, '2023-06-27 17:09:44', '0', 1);
INSERT INTO `sys_menu` VALUES (2192, '删除', 'sys:dept:delete', 3, 3, 2174, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-27 17:14:50', NULL, '2023-06-27 17:14:50', '0', 1);
INSERT INTO `sys_menu` VALUES (2193, '查询', 'sys:post:query', 3, 0, 2175, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 08:58:37', NULL, '2023-06-28 10:49:03', '0', 1);
INSERT INTO `sys_menu` VALUES (2194, '新增', 'sys:post:create', 3, 1, 2175, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 09:00:03', NULL, '2023-06-28 09:00:03', '0', 1);
INSERT INTO `sys_menu` VALUES (2195, '修改', 'sys:post:update', 3, 2, 2175, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 09:00:39', NULL, '2023-06-28 09:00:39', '0', 1);
INSERT INTO `sys_menu` VALUES (2196, '删除', 'sys:post:delete', 3, 3, 2175, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 09:01:19', NULL, '2023-06-28 09:01:19', '0', 1);
INSERT INTO `sys_menu` VALUES (2197, '查询', 'sys:role:query', 3, 0, 2176, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:50:35', NULL, '2023-06-28 10:50:35', '0', 1);
INSERT INTO `sys_menu` VALUES (2198, '新增', 'sys:role:create', 3, 1, 2176, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:51:12', NULL, '2023-06-28 10:51:12', '0', 1);
INSERT INTO `sys_menu` VALUES (2199, '修改', 'sys:role:update', 3, 2, 2176, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:51:54', NULL, '2023-06-28 10:51:54', '0', 1);
INSERT INTO `sys_menu` VALUES (2200, '删除', 'sys:role:delete', 3, 3, 2176, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:52:23', NULL, '2023-06-28 10:52:23', '0', 1);
INSERT INTO `sys_menu` VALUES (2201, '查询', 'sys:menu:query', 3, 0, 2181, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:54:13', NULL, '2023-06-28 10:54:13', '0', 1);
INSERT INTO `sys_menu` VALUES (2202, '新增', 'sys:menu:create', 3, 1, 2181, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:54:43', NULL, '2023-06-28 10:54:43', '0', 1);
INSERT INTO `sys_menu` VALUES (2203, '修改', 'sys:menu:update', 3, 2, 2181, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:55:16', NULL, '2023-06-28 10:55:16', '0', 1);
INSERT INTO `sys_menu` VALUES (2204, '删除', 'sys:menu:delete', 3, 3, 2181, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:55:41', NULL, '2023-06-28 10:55:41', '0', 1);
INSERT INTO `sys_menu` VALUES (2205, '查询', 'sys:dict:query', 3, 0, 2182, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:58:18', NULL, '2023-06-28 10:58:18', '0', 1);
INSERT INTO `sys_menu` VALUES (2206, '新增', 'sys:dict:create', 3, 0, 2182, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:58:51', NULL, '2023-06-28 10:58:51', '0', 1);
INSERT INTO `sys_menu` VALUES (2207, '修改', 'sys:dict:update', 3, 2, 2182, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:59:22', NULL, '2023-06-28 11:00:17', '0', 1);
INSERT INTO `sys_menu` VALUES (2208, '删除', 'sys:dict:delete', 3, 3, 2182, '', '', '', '', 0, '1', '1', '1', 0, NULL, '2023-06-28 10:59:46', NULL, '2023-06-28 10:59:46', '0', 1);
INSERT INTO `sys_menu` VALUES (2209, '经销商管理', '', 2, 0, 2164, NULL, '', '', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:20:31', NULL, '2023-06-29 16:20:31', '0', 1);
INSERT INTO `sys_menu` VALUES (2210, '参数配置', '', 2, 1, 2164, NULL, '', '', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:20:48', NULL, '2023-06-29 16:20:48', '0', 1);
INSERT INTO `sys_menu` VALUES (2211, '未对接经销商', '', 2, 0, 2209, NULL, '', 'distributor/admin/un-abutmented/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:21:28', NULL, '2023-06-30 14:01:25', '0', 1);
INSERT INTO `sys_menu` VALUES (2212, '经销商回收站', '', 2, 1, 2209, NULL, '', 'distributor/admin/recycle/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:21:48', NULL, '2023-06-30 14:01:30', '0', 1);
INSERT INTO `sys_menu` VALUES (2213, '客户渠道', '', 2, 0, 2210, NULL, '', 'distributor/config/customer-channel/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:22:10', NULL, '2023-06-30 14:01:34', '0', 1);
INSERT INTO `sys_menu` VALUES (2214, '获客方式', '', 2, 1, 2210, NULL, '', 'distributor/config/customer-acquisition-method/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:22:29', NULL, '2023-06-30 14:01:39', '0', 1);
INSERT INTO `sys_menu` VALUES (2215, '客户等级', '', 2, 2, 2210, NULL, '', 'distributor/config/customer-level/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:22:45', NULL, '2023-06-30 14:01:44', '0', 1);
INSERT INTO `sys_menu` VALUES (2216, '信誉等级', '', 2, 3, 2210, NULL, '', 'distributor/config/reputation-level/index', NULL, 0, '1', '1', '1', 0, NULL, '2023-06-29 16:22:58', NULL, '2023-06-30 14:01:48', '0', 1);


-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_oauth_client_details` VALUES ('client', NULL, 'client', 'server', 'client_credentials', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_oauth_client_details` VALUES ('gen', NULL, 'gen', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_oauth_client_details` VALUES ('rc', NULL, 'rc', 'server', 'password,app,refresh_token,authorization_code,client_credentials', 'https://rc.com', NULL, NULL, NULL, NULL, 'true', NULL, '2023-06-24 16:32:05', NULL, NULL, '0');
INSERT INTO `sys_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,app,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true', NULL, NULL, NULL, NULL, '0');


-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2023-02-11 15:19:04', '0', 1);
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2021-12-12 10:47:47', '0', 1);
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工7770', 4, 0, '111', 'admin', '2021-01-05 17:03:48', 'admin', '2023-06-30 16:53:53', '0', 1);
INSERT INTO `sys_post` VALUES (5, 'test001', '测试001', 0, 0, NULL, 'admin', '2023-06-30 17:06:09', 'admin', '2023-06-30 17:10:43', '1', 1);


-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', '0', 1);
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', '0', 1);
INSERT INTO `sys_role` VALUES (101, '测试账号', 'test', 0, 1, '[]', 0, 2, '132', '', '2021-01-06 13:49:35', NULL, '2023-06-29 14:21:56', '0', 1);
INSERT INTO `sys_role` VALUES (140, '测试账号2', 'test2', 0, 1, '', 0, 2, '', NULL, '2023-06-29 08:08:23', NULL, '2023-06-29 08:15:47', '1', 1);
INSERT INTO `sys_role` VALUES (141, '测试账号1333', 'test15', 3, 1, NULL, 0, 2, '', NULL, '2023-06-29 08:16:49', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role` VALUES (142, '产品经理', 'product', 0, 1, NULL, 0, 2, '', NULL, '2023-06-29 13:30:47', NULL, '2023-06-29 14:21:45', '1', 1);


-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2901, 1, 2162, '', '2023-06-26 16:51:04', '', '2023-06-26 16:51:24', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2902, 1, 2163, '', '2023-06-26 16:51:41', '', '2023-06-26 16:51:45', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2903, 1, 2164, '', '2023-06-26 16:51:54', '', '2023-06-26 16:52:14', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2904, 1, 2165, '', '2023-06-26 16:52:13', '', '2023-06-26 16:52:13', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2905, 1, 2166, '', '2023-06-26 16:52:25', '', '2023-06-26 16:52:25', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2906, 1, 2167, '', '2023-06-26 16:52:43', '', '2023-06-26 16:52:43', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2907, 1, 2168, '', '2023-06-26 16:52:51', '', '2023-06-26 16:52:51', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2908, 1, 2169, '', '2023-06-26 16:52:59', '', '2023-06-26 16:52:59', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2909, 1, 2170, '', '2023-06-26 16:53:07', '', '2023-06-26 16:53:07', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2910, 1, 2171, '', '2023-06-26 16:53:14', '', '2023-06-26 16:53:14', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2911, 1, 2172, '', '2023-06-26 16:53:23', '', '2023-06-26 16:53:23', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2912, 1, 2173, '', '2023-06-26 16:58:50', '', '2023-06-26 16:58:50', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2913, 1, 2174, '', '2023-06-26 17:09:23', '', '2023-06-26 17:09:23', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2914, 1, 2175, '', '2023-06-26 17:09:33', '', '2023-06-26 17:09:33', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2915, 1, 2176, '', '2023-06-26 17:09:43', '', '2023-06-26 17:09:43', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2916, 1, 2177, '', '2023-06-26 17:14:24', '', '2023-06-26 17:14:24', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2917, 1, 2178, '', '2023-06-26 17:16:51', '', '2023-06-26 17:16:51', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2918, 1, 2179, '', '2023-06-26 17:16:58', '', '2023-06-26 17:16:58', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2919, 1, 2180, '', '2023-06-26 17:17:06', '', '2023-06-26 17:17:06', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2920, 1, 2181, '', '2023-06-27 13:57:46', '', '2023-06-27 13:57:46', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2921, 1, 2182, '', '2023-06-27 13:57:54', '', '2023-06-27 13:57:54', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2922, 1, 2183, '', '2023-06-27 13:58:02', '', '2023-06-27 13:58:02', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2923, 1, 2184, '', '2023-06-27 13:58:09', '', '2023-06-27 13:58:09', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2924, 1, 2185, '', '2023-06-27 13:58:16', '', '2023-06-27 13:58:16', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2925, 1, 2186, '', '2023-06-27 14:49:17', '', '2023-06-27 14:50:35', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2926, 1, 2187, '', '2023-06-28 09:58:28', '', '2023-06-28 09:58:28', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2927, 1, 2188, '', '2023-06-28 09:58:36', '', '2023-06-28 09:58:36', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2928, 1, 2189, '', '2023-06-28 09:58:43', '', '2023-06-28 09:58:43', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2929, 1, 2190, '', '2023-06-28 09:58:50', '', '2023-06-28 09:58:50', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2930, 1, 2191, '', '2023-06-28 09:58:57', '', '2023-06-28 09:58:57', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2931, 1, 2192, '', '2023-06-28 09:59:03', '', '2023-06-28 09:59:03', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2932, 1, 2193, '', '2023-06-28 09:59:07', '', '2023-06-28 09:59:09', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2933, 1, 2194, '', '2023-06-28 09:59:16', '', '2023-06-28 09:59:16', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2934, 1, 2195, '', '2023-06-28 09:59:24', '', '2023-06-28 09:59:24', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2935, 1, 2196, '', '2023-06-28 09:59:32', '', '2023-06-28 09:59:32', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2936, 1, 2197, '', '2023-06-28 11:07:12', '', '2023-06-28 11:08:05', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2937, 1, 2198, '', '2023-06-28 11:07:18', '', '2023-06-28 11:08:08', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2938, 1, 2199, '', '2023-06-28 11:07:31', '', '2023-06-28 11:08:12', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2939, 1, 2200, '', '2023-06-28 11:08:22', '', '2023-06-28 11:08:22', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2940, 1, 2201, '', '2023-06-28 11:08:31', '', '2023-06-28 11:08:31', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2941, 1, 2202, '', '2023-06-28 11:08:38', '', '2023-06-28 11:08:38', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2942, 1, 2203, '', '2023-06-28 11:08:44', '', '2023-06-28 11:08:44', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2943, 1, 2204, '', '2023-06-28 11:08:52', '', '2023-06-28 11:08:52', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2944, 1, 2205, '', '2023-06-28 11:08:59', '', '2023-06-28 11:08:59', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2945, 1, 2206, '', '2023-06-28 11:09:07', '', '2023-06-28 11:09:07', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2946, 1, 2207, '', '2023-06-28 11:09:13', '', '2023-06-28 11:09:13', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2947, 1, 2208, '', '2023-06-28 11:09:19', '', '2023-06-28 11:09:19', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2948, 141, 2162, NULL, '2023-06-29 08:37:22', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2949, 141, 2163, NULL, '2023-06-29 08:37:22', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2950, 141, 2164, NULL, '2023-06-29 08:37:22', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2951, 141, 2165, NULL, '2023-06-29 13:29:06', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2952, 141, 2167, NULL, '2023-06-29 13:29:19', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2953, 141, 2176, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2954, 141, 2177, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2955, 141, 2178, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2956, 141, 2179, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2957, 141, 2180, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2958, 141, 2181, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2959, 141, 2182, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2960, 141, 2183, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2961, 141, 2184, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2962, 141, 2185, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2963, 141, 2188, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2964, 141, 2189, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2965, 141, 2190, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2966, 141, 2191, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2967, 141, 2192, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2968, 141, 2193, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2969, 141, 2194, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2970, 141, 2195, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2971, 141, 2196, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2972, 141, 2197, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2973, 141, 2198, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2974, 141, 2199, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2975, 141, 2200, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2976, 141, 2201, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2977, 141, 2202, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2978, 141, 2203, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2979, 141, 2204, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2980, 141, 2205, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2981, 141, 2206, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2982, 141, 2207, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2983, 141, 2208, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2984, 141, 2168, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2985, 141, 2169, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2986, 141, 2170, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2987, 141, 2171, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2988, 141, 2172, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2989, 141, 2173, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2990, 141, 2174, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2991, 141, 2175, NULL, '2023-06-29 13:29:30', NULL, '2023-06-29 13:30:01', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2992, 142, 2162, NULL, '2023-06-29 13:30:47', NULL, '2023-06-29 14:21:45', '1', 1);
INSERT INTO `sys_role_menu` VALUES (2993, 101, 2162, NULL, '2023-06-29 14:21:56', NULL, '2023-06-29 14:21:56', '0', 1);
INSERT INTO `sys_role_menu` VALUES (2994, 101, 2163, NULL, '2023-06-29 14:21:56', NULL, '2023-06-29 14:21:56', '0', 1);


-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, '柔川信息', 1, '张三', '13777777777', 0, 'https://www.iocoder.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2023-06-13 14:23:35', '0');
INSERT INTO `sys_tenant` VALUES (121, '小租户', 110, '小王2', '15601691300', 0, 'http://www.iocoder.cn', 111, '2024-03-11 00:00:00', 20, '1', '2022-02-22 00:56:14', '1', '2022-05-17 10:03:59', '0');
INSERT INTO `sys_tenant` VALUES (122, '测试租户', 113, '李四', '15601691300', 0, 'https://www.iocoder.cn', 111, '2022-04-30 00:00:00', 50, '1', '2022-03-07 21:37:58', '1', '2023-06-12 17:05:10', '0');
INSERT INTO `sys_tenant` VALUES (150, 'zuhu1', 126, 'huang', NULL, 0, 'www.baidu.com', 111, '2024-05-31 00:00:00', 1000, '1', '2023-05-31 13:52:15', '1', '2023-05-31 13:52:15', '0');


-- ----------------------------
-- Records of sys_tenant_package
-- ----------------------------
INSERT INTO `sys_tenant_package` VALUES (111, '普通套餐', 0, '小功能', '[1,1036,1037,1038,1039,100,101,1063,103,1064,1001,1065,1002,1003,107,1004,1005,1006,1007,1008,1009,1010,1011,1012,1017,1018,1019,1020]', '1', '2022-02-22 00:54:00', '1', '2022-09-21 22:48:12', '0');


-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '{bcrypt}$2a$10$3EqFTg5lyyBkNTXqTpe5xeGwU6QSgBxzxSBc16fZARDZ.NYIk7w0W', 'rc', '管理员', 100, '[1]', 'aoteman@126.com', '15612345678', 1, 'https://cdn.maku.net/images/avatar.png', 0, '127.0.0.1', '2023-05-31 11:26:15', 'admin', '2021-01-05 17:03:47', NULL, '2023-06-28 16:43:56', '0', 1);
INSERT INTO `sys_user` VALUES (128, 'hqftest123', '{bcrypt}$2a$10$6tM5ihqRHZloc96TTZ0wGegAoPY7sR1F5KHhAwOnpfUn1q0dWidNW', 'hqf', '管理员', 105, '[4]', '', '13777777777', 1, 'https://cdn.maku.net/images/avatar.png', 0, '', NULL, NULL, '2023-06-28 17:01:24', NULL, '2023-06-28 17:05:39', '0', 1);
INSERT INTO `sys_user` VALUES (129, 'hqf12345', '{bcrypt}$2a$10$UxzKHyZuBu/.J77RStGYou2s2wdAH4doOFHPK5AE2478QbMxkZe16', 'hqf', '管理员', 105, '[4]', '', '13777766664', 1, 'https://cdn.maku.net/images/avatar.png', 0, '', NULL, NULL, '2023-06-28 17:06:14', NULL, '2023-06-28 17:06:24', '1', 1);


-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', '0', 1);
INSERT INTO `sys_user_post` VALUES (118, 1, 2, NULL, '2023-06-28 16:06:03', NULL, '2023-06-28 16:06:34', '1', 1);
INSERT INTO `sys_user_post` VALUES (119, 128, 4, NULL, '2023-06-28 17:01:24', NULL, '2023-06-28 17:01:24', '0', 1);
INSERT INTO `sys_user_post` VALUES (120, 129, 4, NULL, '2023-06-28 17:06:14', NULL, '2023-06-28 17:06:14', '0', 1);


-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', '0', 1);
INSERT INTO `sys_user_role` VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2023-06-28 16:40:08', '1', 1);
INSERT INTO `sys_user_role` VALUES (32, 128, 101, NULL, '2023-06-28 17:05:39', NULL, '2023-06-28 17:05:39', '0', 1);
INSERT INTO `sys_user_role` VALUES (33, 129, 101, NULL, '2023-06-28 17:06:14', NULL, '2023-06-28 17:06:14', '0', 1);
INSERT INTO `sys_user_role` VALUES (34, 128, 142, NULL, '2023-06-29 14:19:12', NULL, '2023-06-29 14:21:28', '1', 1);
INSERT INTO `sys_user_role` VALUES (35, 128, 142, NULL, '2023-06-29 14:21:34', NULL, '2023-06-29 14:21:40', '1', 1);
INSERT INTO `sys_user_role` VALUES (36, 1, 142, NULL, '2023-06-29 14:21:34', NULL, '2023-06-29 14:21:40', '1', 1);

