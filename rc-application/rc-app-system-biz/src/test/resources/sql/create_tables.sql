CREATE TABLE IF NOT EXISTS "sys_dept" (
    "id" varchar NOT NULL,
    "name" varchar NOT NULL DEFAULT '',
    "parent_id" varchar NOT NULL DEFAULT '0',
    "sort" int NOT NULL DEFAULT '0',
    "leader_user_id" varchar DEFAULT NULL,
    "phone" varchar(11) DEFAULT NULL,
    "email" varchar(50) DEFAULT NULL,
    "status" tinyint NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '部门表';

CREATE TABLE IF NOT EXISTS "sys_dict_data" (
    "id" varchar NOT NULL,
    "sort" int NOT NULL,
    "label" varchar(100) NOT NULL DEFAULT '',
    "value" varchar(100) NOT NULL DEFAULT '',
    "dict_type" varchar(100) NOT NULL DEFAULT '',
    "status" tinyint NOT NULL,
    "color_type" varchar(100) NOT NULL DEFAULT '',
    "css_class" varchar(100) NOT NULL DEFAULT '',
    "remark" varchar(500) DEFAULT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`id`)
) COMMENT '字典数据表';

CREATE TABLE IF NOT EXISTS "sys_role" (
    "id" varchar NOT NULL,
    "name" varchar NOT NULL,
    "code" varchar(100) NOT NULL,
    "sort" int NOT NULL,
    "data_scope" tinyint NOT NULL DEFAULT '1',
    "data_scope_dept_ids" varchar(500) NOT NULL DEFAULT '',
    "status" tinyint NOT NULL,
    "type" tinyint NOT NULL,
    "remark" varchar(500) DEFAULT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '角色信息表';

CREATE TABLE IF NOT EXISTS "sys_role_menu" (
    "id" varchar NOT NULL,
    "role_id" varchar NOT NULL,
    "menu_id" varchar NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '角色和菜单关联表';

CREATE TABLE IF NOT EXISTS "sys_menu" (
    "id" varchar NOT NULL,
    "name" varchar(50) NOT NULL,
    "permission" varchar(100) NOT NULL DEFAULT '',
    "type" tinyint NOT NULL,
    "sort" int NOT NULL DEFAULT '0',
    "parent_id" varchar NOT NULL DEFAULT '0',
    "path" varchar(200) DEFAULT '',
    "icon" varchar(100) DEFAULT '#',
    "component" varchar(255) DEFAULT NULL,
    "component_name" varchar(255) DEFAULT NULL,
    "status" tinyint NOT NULL DEFAULT '0',
    "visible" bit NOT NULL DEFAULT TRUE,
    "keep_alive" bit NOT NULL DEFAULT TRUE,
    "always_show" bit NOT NULL DEFAULT TRUE,
    "open_style" int(4) NOT NULL DEFAULT 0,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '菜单权限表';

CREATE TABLE IF NOT EXISTS "sys_user_role" (
     "id" varchar NOT NULL,
     "user_id" varchar NOT NULL,
     "role_id" varchar NOT NULL,
     "creator" varchar(64) DEFAULT '',
     "create_time" timestamp DEFAULT NULL,
     "updater" varchar(64) DEFAULT '',
     "update_time" timestamp DEFAULT NULL,
     "deleted" bit DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '用户和角色关联表';

CREATE TABLE IF NOT EXISTS "sys_dict_type" (
    "id" varchar NOT NULL,
    "name" varchar(100) NOT NULL DEFAULT '',
    "type" varchar(100) NOT NULL DEFAULT '',
    "status" tinyint NOT NULL DEFAULT '0',
    "remark" varchar(500) DEFAULT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '字典类型表';

CREATE TABLE IF NOT EXISTS `sys_user_session` (
    "id" varchar NOT NULL,
    `token` varchar(32) NOT NULL,
    `user_id` varchar DEFAULT NULL,
    "user_type" tinyint NOT NULL,
    `username` varchar(50) NOT NULL DEFAULT '',
    `user_ip` varchar(50) DEFAULT NULL,
    `user_agent` varchar(512) DEFAULT NULL,
    `session_timeout` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updater` varchar(64) DEFAULT '' ,
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY (`id`)
) COMMENT '用户在线 Session';

CREATE TABLE IF NOT EXISTS "sys_post" (
    "id"          varchar NOT NULL,
    "code"        varchar(64) NOT NULL,
    "name"        varchar(50) NOT NULL,
    "sort"        integer     NOT NULL,
    "status"      tinyint     NOT NULL,
    "remark"      varchar(500)         DEFAULT NULL,
    "creator"     varchar(64)          DEFAULT '',
    "create_time" timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater"     varchar(64)          DEFAULT '',
    "update_time" timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted"     bit         NOT NULL DEFAULT FALSE,
    "tenant_id" varchar not null default  '0',
    PRIMARY KEY ("id")
) COMMENT '岗位信息表';

CREATE TABLE IF NOT EXISTS `sys_user_post`(
    "id"          varchar NOT NULL,
    "user_id"     varchar             DEFAULT NULL,
    "post_id"     varchar             DEFAULT NULL,
    "creator"     varchar(64)        DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater"     varchar(64)        DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted"     bit       NOT NULL DEFAULT FALSE,
    "tenant_id"   varchar    not null default '0',
    PRIMARY KEY (`id`)
) COMMENT ='用户岗位表';

CREATE TABLE IF NOT EXISTS "sys_user" (
    "id" varchar not null,
    "username" varchar not null,
    "password" varchar(100) not null default '',
    "nickname" varchar not null,
    "remark" varchar(500) default null,
    "dept_id" varchar default null,
    "post_ids" varchar(255) default null,
    "email" varchar(50) default '',
    "mobile" varchar(11) default '',
    "sex" tinyint default '0',
    "avatar" varchar(100) default '',
    "status" tinyint not null default '0',
    "login_ip" varchar(50) default '',
    "login_date" timestamp default null,
    "creator" varchar(64) default '',
    "create_time" timestamp not null default current_timestamp,
    "updater" varchar(64) default '',
    "update_time" timestamp not null default current_timestamp,
    "deleted" bit not null default false,
    "tenant_id" varchar not null default  '0',
    primary key ("id")
) comment '用户信息表';

CREATE TABLE IF NOT EXISTS "sys_tenant" (
    "id" varchar(50) NOT NULL,
    "name" varchar(63) NOT NULL,
    "contact_user_id" varchar NOT NULL DEFAULT '0',
    "contact_name" varchar(255) NOT NULL,
    "contact_mobile" varchar(255),
    "status" tinyint NOT NULL,
    "domain" varchar(63) DEFAULT '',
    "package_id"  varchar NOT NULL,
    "expire_time" timestamp NOT NULL,
    "account_count" int NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '租户';

CREATE TABLE IF NOT EXISTS "sys_tenant_package" (
    "id" varchar NOT NULL,
    "name" varchar NOT NULL,
    "status" tinyint NOT NULL,
    "remark" varchar(256),
    "menu_ids" varchar(2048) NOT NULL,
    "creator" varchar(64) DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar(64) DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT '租户套餐表';

CREATE TABLE IF NOT EXISTS "sys_oauth2_client" (
  "id" varchar NOT NULL,
  "client_id" varchar NOT NULL,
  "secret" varchar NOT NULL,
  "name" varchar NOT NULL,
  "logo" varchar NOT NULL,
  "description" varchar,
  "status" int NOT NULL,
  "access_token_validity_seconds" int NOT NULL,
  "refresh_token_validity_seconds" int NOT NULL,
  "redirect_uris" varchar NOT NULL,
  "authorized_grant_types" varchar NOT NULL,
  "scopes" varchar NOT NULL DEFAULT '',
  "auto_approve_scopes" varchar NOT NULL DEFAULT '',
  "authorities" varchar NOT NULL DEFAULT '',
  "resource_ids" varchar NOT NULL DEFAULT '',
  "additional_information" varchar NOT NULL DEFAULT '',
  "creator" varchar DEFAULT '',
  "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updater" varchar DEFAULT '',
  "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "deleted" bit NOT NULL DEFAULT FALSE,
  PRIMARY KEY ("id")
) COMMENT 'OAuth2 客户端表';

CREATE TABLE IF NOT EXISTS "sys_oauth2_approve" (
  "id" varchar NOT NULL,
  "user_id" varchar NOT NULL,
  "user_type" tinyint NOT NULL,
  "client_id" varchar NOT NULL,
  "scope" varchar NOT NULL,
  "approved" bit NOT NULL DEFAULT FALSE,
  "expires_time" datetime NOT NULL,
  "creator" varchar DEFAULT '',
  "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updater" varchar DEFAULT '',
  "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "deleted" bit NOT NULL DEFAULT FALSE,
  PRIMARY KEY ("id")
) COMMENT 'OAuth2 批准表';

CREATE TABLE IF NOT EXISTS "sys_oauth2_access_token" (
   "id" varchar NOT NULL,
   "user_id" varchar NOT NULL,
   "user_type" tinyint NOT NULL,
   "access_token" varchar NOT NULL,
   "refresh_token" varchar NOT NULL,
   "client_id" varchar NOT NULL,
   "scopes" varchar NOT NULL,
   "approved" bit NOT NULL DEFAULT FALSE,
   "expires_time" datetime NOT NULL,
   "creator" varchar DEFAULT '',
   "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "updater" varchar DEFAULT '',
   "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   "deleted" bit NOT NULL DEFAULT FALSE,
   "tenant_id" varchar NOT NULL,
   PRIMARY KEY ("id")
) COMMENT 'OAuth2 访问令牌';

CREATE TABLE IF NOT EXISTS "sys_oauth2_refresh_token" (
    "id" varchar NOT NULL,
    "user_id" varchar NOT NULL,
    "user_type" tinyint NOT NULL,
    "refresh_token" varchar NOT NULL,
    "client_id" varchar NOT NULL,
    "scopes" varchar NOT NULL,
    "approved" bit NOT NULL DEFAULT FALSE,
    "expires_time" datetime NOT NULL,
    "creator" varchar DEFAULT '',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updater" varchar DEFAULT '',
    "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "deleted" bit NOT NULL DEFAULT FALSE,
    PRIMARY KEY ("id")
) COMMENT 'OAuth2 刷新令牌';

CREATE TABLE IF NOT EXISTS "sys_oauth2_code" (
     "id" varchar NOT NULL,
     "user_id" varchar NOT NULL,
     "user_type" tinyint NOT NULL,
     "code" varchar NOT NULL,
     "client_id" varchar NOT NULL,
     "scopes" varchar NOT NULL,
     "expires_time" datetime NOT NULL,
     "redirect_uri" varchar NOT NULL,
     "state" varchar NOT NULL,
     "creator" varchar DEFAULT '',
     "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     "updater" varchar DEFAULT '',
     "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     "deleted" bit NOT NULL DEFAULT FALSE,
     PRIMARY KEY ("id")
) COMMENT 'OAuth2 刷新令牌';
