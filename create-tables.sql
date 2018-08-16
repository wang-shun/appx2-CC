-- ==================================================
-- 组织信息表
-- ==================================================
DROP TABLE IF EXISTS aci_organize;
CREATE TABLE aci_organize (
	id 				CHAR(32) NOT NULL COMMENT 'ID序列号',
	name 			VARCHAR(20) COMMENT '名称',
	app_id 			CHAR(32) NOT NULL COMMENT '应用id',
	app_ctg 		CHAR(20) COMMENT '应用分类',
	status 			CHAR(20) NOT NULL DEFAULT 'ACTIVATED' COMMENT '组织状态',
	cre_tim 		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (id)
) COMMENT='组织信息表' ENGINE=InnoDB;
CREATE UNIQUE INDEX uniq_idx_org_app_id ON aci_organize (app_id);

-- ==================================================
-- 用户信息表
-- ==================================================
DROP TABLE IF EXISTS aci_user;
CREATE TABLE aci_user (
	id 				CHAR(32) NOT NULL COMMENT 'ID序列号',
	username 		VARCHAR(20) COMMENT '用户名，由3~20位英文大小写字母、阿拉伯数字及下划线组成，以标明用户的客户。如：jone_2008',
	password 		CHAR(32) COMMENT '登录密码',
	email 			VARCHAR(255) COMMENT '注册邮箱',
	phoneNumber		VARCHAR(20) COMMENT '手机号码',
	org_id			CHAR(32) NOT NULL COMMENT '应用id',
	status 			CHAR(32) NOT NULL DEFAULT 'ACTIVATED' COMMENT '用户状态（SIGNUPED-已签约、ACTIVATED-已激活、DORMANCY-已休眠、BLOCKED-已冻结、BLACKED-黑名单、DELETED-已删除）',
	cre_tim 		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
	updr_id 		CHAR(32) COMMENT '更新者用户ID号',
	upd_tim 		DATETIME COMMENT '更新时间',
	remark 			VARCHAR(128) COMMENT '备注',
	PRIMARY KEY (id)
) COMMENT='用户信息表' ENGINE=InnoDB;
CREATE UNIQUE INDEX idx_user_email ON aci_user (email, org_id);
CREATE UNIQUE INDEX idx_user_phoneNumber ON aci_user (phoneNumber, org_id);

-- ==================================================
-- 客户信息表
-- ==================================================
DROP TABLE IF EXISTS aci_customer;
CREATE TABLE aci_customer (
	id 				CHAR(32) NOT NULL COMMENT 'ID序列号',
	org_id			CHAR(32) NOT NULL COMMENT '应用id',
	name 			VARCHAR(60) COMMENT '名字（个人的真实姓名或企业、团队、产品的全名）',
	pet_nam 		VARCHAR(20) NOT NULL COMMENT '昵称（个人的昵称或企业、团队、产品的简称）',
	alias 			VARCHAR(35) NOT NULL COMMENT '别名（它可以是昵称的拼音+编号、昵称的拼音+英文字母+编号两种形式）',
	slogan 			VARCHAR(50) COMMENT '品牌宣传口号（它可以是：个人品牌宣传口号，或企业、团队、产品品牌的宣传口号）',
	intro 			VARCHAR(2000) COMMENT '简介',
	homepage 		VARCHAR(255) COMMENT '官方（个人）主页',
	email 			VARCHAR(255) COMMENT '常用邮箱',
	phoneNumber		VARCHAR(20) COMMENT '手机号码',
	mugshot 		VARCHAR(255) COMMENT '头像',
	category 		VARCHAR(64) NOT NULL COMMENT '主体类型（enterprise-企业、team-团队、product-产品等、person-个人）', 
	status 			CHAR(32) NOT NULL DEFAULT 'ACTIVATED' COMMENT '状态（SIGNUPED-已签约、ACTIVATED-已激活、LOCKED-已锁定、CLOSED-已关闭。默认为：DEFAULT-默认）',
	crer_id 		CHAR(32) NOT NULL COMMENT '创建者用户ID号',
	cre_tim 		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	updr_id 		CHAR(32) COMMENT '更新者用户ID号',
	upd_tim 		DATETIME COMMENT '更新时间',
	remark 			VARCHAR(128) COMMENT '备注',
	PRIMARY KEY (id)
) COMMENT='客户信息表' ENGINE=InnoDB;

-- ==================================================
-- 序列号信息表
-- ==================================================
DROP TABLE IF EXISTS aci_sequence;
CREATE TABLE aci_sequence (
	id CHAR(32) NOT NULL COMMENT 'ID序列号',
	name CHAR(20) NOT NULL COMMENT '名称',
	value INT NOT NULL DEFAULT 1 COMMENT '值',
	status CHAR(32) NOT NULL DEFAULT 'DEFAULT' COMMENT '状态（DEFAULT-正常、DEPRECATED-已弃用。默认为：DEFAULT-正常）',
	cre_tim TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	upd_tim DATETIME COMMENT '更新时间',
	remark VARCHAR(128) COMMENT '备注',
	PRIMARY KEY (id)
) COMMENT='序列号信息表' ENGINE=InnoDB;
CREATE UNIQUE INDEX uniq_idx_sequence_name ON aci_sequence (name);

-- ==================================================
-- 自动登录秘钥信息表
-- ==================================================
DROP TABLE IF EXISTS aci_rmbrkey;
CREATE TABLE aci_rmbrkey (
	id CHAR(32) NOT NULL COMMENT 'ID序列号',
	usr_id CHAR(32) NOT NULL COMMENT '用户ID号',
	value CHAR(32) NOT NULL COMMENT '秘钥值',
	cre_tim TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
	lst_usetim DATETIME COMMENT '最近使用时间',
	remark VARCHAR(128) COMMENT '备注',
	PRIMARY KEY (id)
) COMMENT='自动登录秘钥信息表' ENGINE=InnoDB;
CREATE UNIQUE INDEX uniq_idx_rmbrkey_value ON aci_rmbrkey (value);

-- ==================================================
-- 用户登录日志表
-- ==================================================
DROP TABLE IF EXISTS acl_signin;
CREATE TABLE acl_signin (
	id 			CHAR(32) NOT NULL COMMENT 'ID序列号',
	usr_id 		CHAR(32) NOT NULL COMMENT '登录用户id',
	ip 			CHAR(15) NOT NULL COMMENT '登录 IP',
	type 		CHAR(32) NOT NULL DEFAULT 0 COMMENT '登录方式（PAGE-页面登录）',
	time 		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
	status 		CHAR(32) NOT NULL COMMENT '登录状态（SUCCESS-成功，FAIL-失败）',
	cause 		CHAR(32) COMMENT '登录失败的原因（ERROR_USERNAME-用户名未注册，ERROR_PASSWORD-密码错误）',
	remark 		VARCHAR(128) COMMENT '备注',
	PRIMARY KEY (id)
) COMMENT='用户登录日志表' ENGINE=InnoDB;
CREATE INDEX idx_signin_ip ON acl_signin (ip);

