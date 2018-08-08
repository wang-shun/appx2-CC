DROP TABLE IF EXISTS  `mem_hier`;
CREATE TABLE `mem_hier` (
  `id` char(32) NOT NULL COMMENT '会员等级ID',
  `sto_id` char(32) NOT NULL COMMENT '店铺ID',
  `name` char(64) NOT NULL COMMENT '会员等级名称',
  `sequence` int(2) DEFAULT NULL COMMENT '会员等级顺序',
  `gro_val` int(8) DEFAULT '0' COMMENT '到达该等级所需成长值',
  `fre_ship` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否包邮',
  `discnt` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启会员折扣',
  `dis_amo` double DEFAULT '10' COMMENT '折扣率',
  `expiration` char(32) NOT NULL,
  `period` int(3) DEFAULT NULL,
  `exp_deduction` int(8) DEFAULT NULL,
  `status` char(8) DEFAULT NULL,
  `cre_tim` timestamp NULL DEFAULT NULL,
  `upd_tim` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_sto_id` (`sto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员等级表';

DROP TABLE IF EXISTS  `mem_poi_rec`;
CREATE TABLE `mem_poi_rec` (
  `id` char(32) NOT NULL COMMENT '主键',
  `type` char(32) NOT NULL COMMENT '类型',
  `value` char(8) NOT NULL COMMENT '成长值变更类型',
  `source` char(10) DEFAULT NULL COMMENT '成长值来源',
  `custid` char(32) NOT NULL COMMENT '客户ID',
  `cre_tim` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `sto_id` char(32) DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`),
  KEY `mem_poi_rec_id_index` (`id`),
  KEY `mem_poi_rec_custid_index` (`custid`),
  KEY `mem_poi_rec_type_custid_index` (`type`,`custid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `mem_trd_usr`;
CREATE TABLE `mem_trd_usr` (
  `id` char(32) NOT NULL COMMENT '主键',
  `pet_nam` char(32) NOT NULL COMMENT '昵称',
  `mug_shot` varchar(255) NOT NULL COMMENT '头像',
  `tppid` char(32) NOT NULL COMMENT '第三方平台标识',
  `tpp_open_id` char(32) NOT NULL COMMENT '第三方平台标识ID',
  `appType` varchar(10) NOT NULL COMMENT '应用类型',
  `sto_id` char(32) NOT NULL COMMENT '店铺ID',
  `appid` char(32) NOT NULL COMMENT '应用ID',
  `cre_tim` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mem_id` char(32) DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mem_trd_usr_tpp_open_id_sto_id_appid_index` (`tpp_open_id`,`sto_id`,`appid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台用户表';

DROP TABLE IF EXISTS  `mem_user`;
CREATE TABLE `mem_user` (
  `id` char(32) NOT NULL COMMENT 'ID序列号',
  `sto_id` char(32) NOT NULL COMMENT '店铺ID',
  `ph_num` char(20) DEFAULT NULL COMMENT '手机号',
  `mug_shot` varchar(255) DEFAULT NULL COMMENT '头像',
  `nick_nam` varchar(100) DEFAULT NULL COMMENT '昵称',
  `usr_nam` varchar(255) DEFAULT NULL COMMENT '用户名',
  `sex` int(1) DEFAULT NULL COMMENT '性别',
  `hier_id` char(32) DEFAULT NULL,
  `upd_tim` timestamp NULL DEFAULT NULL,
  `cre_tim` timestamp NULL DEFAULT NULL,
  `due_date` timestamp NULL DEFAULT NULL,
  `gro_val` int(8) DEFAULT '0',
  `birthday` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mem_user_id_sto_id_uindex` (`id`,`sto_id`),
  KEY `mem_user_sto_id_hier_id_index` (`sto_id`,`hier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';
