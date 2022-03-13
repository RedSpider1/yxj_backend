SET MODE MYSQL;

DROP TABLE IF EXISTS pss_group_team;
CREATE TABLE `pss_group_team` (
  `id` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  `title` varchar(90) DEFAULT '' COMMENT '标题', -- CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `picture_url` text COMMENT '图片URl 以逗号分割', -- CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `introduce` text COMMENT '介绍', -- CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `examine_time` datetime DEFAULT NULL COMMENT '开始时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `min_team_size` int DEFAULT '0' COMMENT '最小组队人数',
  `max_team_size` int DEFAULT '0' COMMENT '最大组队人数',
  `province` varchar(100) DEFAULT '' COMMENT '省',
  `city` varchar(100) DEFAULT '' COMMENT '市',
  `area` varchar(100) DEFAULT '' COMMENT '区',
  `place` varchar(255) DEFAULT '' COMMENT '地点', --CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `lon` varchar(100) DEFAULT '' COMMENT '经度', --CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `lat` varchar(100) DEFAULT '' COMMENT '纬度', --CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `contain_me` int DEFAULT '0' COMMENT '是否包含我自己 0不包含 1 包含',
  `release_status` int DEFAULT '0' COMMENT '发布状态 0草稿 1创建成功',
  `team_status` int DEFAULT '0' COMMENT '0未开始 1  组队中 2组队成功 3组队失败 4撤销组队',
  `confirm_status` int DEFAULT '0' COMMENT '确认类型 0 不需要确认 1 需要确认',
  `examine_type` int DEFAULT '0' COMMENT '审核类型 0 自动审核 1 手动审核',
  `examine_user` varchar(32) DEFAULT '' COMMENT '审核人', --CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `examine_status` int DEFAULT '0' COMMENT '审核状态 0未审核 1审核成功 2 审核失败',
  `start_time` datetime DEFAULT NULL COMMENT '审核时间',
  `examine_msg` varchar(255) DEFAULT '' COMMENT '审核结论', --CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `creator_id` bigint DEFAULT '0' COMMENT '添加人',
  `updater_id` bigint DEFAULT '0' COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '删除状态 0正常'
);
insert into pss_group_team (`id`,`title`,`deleted`) values (1,'there is title',101);

DROP TABLE IF EXISTS pss_group_team_lable;
CREATE TABLE `pss_group_team_lable` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_team_id` bigint DEFAULT '0' COMMENT '团队ID',
  `label_id` bigint DEFAULT '0' COMMENT '标签ID',
  `creator_id` bigint DEFAULT '0' COMMENT '添加人',
  `updater_id` bigint DEFAULT '0' COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint DEFAULT '0' COMMENT '0删除标志',
  PRIMARY KEY (`id`)
);
insert into pss_group_team_lable values (1,1,1,1,1,now(),now(),0);

DROP TABLE IF EXISTS pss_group_team_search_history;
CREATE TABLE `pss_group_team_search_history` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator_id` int NOT NULL,
  `updater_id` int NOT NULL,
  `deleted` tinyint unsigned NOT NULL DEFAULT '0',
  `keyword` varchar(255) NOT NULL COMMENT '搜索关键字',
  PRIMARY KEY (`id`)
--   KEY `idx_keyword_normal` (`keyword`) USING BTREE COMMENT '关键字索引' --不支持普通索引，使用UNIQUE 或 PRIMARY KEY
);
insert into pss_group_team_search_history values (1,now(),now(),1,1,0,'key');

DROP TABLE IF EXISTS pss_group_team_user;
CREATE TABLE `pss_group_team_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_team_id` bigint DEFAULT '0' COMMENT '团队ID',
  `user_id` bigint DEFAULT '0' COMMENT '参与团队用户ID',
  `status` int DEFAULT '1' COMMENT '0提交组队申请，1 组队成功 2 退出组队',
  `creator_id` bigint DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint DEFAULT '0' COMMENT '修改人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint DEFAULT '0' COMMENT '删除标志',
  `contact_information` varchar(32) DEFAULT NULL COMMENT '联系方式', -- CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `type` tinyint DEFAULT '0' COMMENT '联系方式类型 0电话，1 微信',
  `remarks` varchar(255) DEFAULT '""' COMMENT '备注', --CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  PRIMARY KEY (`id`)
);
insert into pss_group_team_user values (1,1,1,0,1,1,now(),now(),0,'phone_number',0,'this is remark');

DROP TABLE IF EXISTS pss_group_team_viewed;
CREATE TABLE `pss_group_team_viewed` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint DEFAULT '0' COMMENT '用户ID',
  `group_team_id` bigint DEFAULT '0' COMMENT '组队单ID',
  `creator_id` bigint DEFAULT '0' COMMENT '添加人',
  `updater_id` bigint DEFAULT '0' COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '删除状态 0正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id,group_team_id` (`user_id`,`group_team_id`)
);
insert into pss_group_team_viewed values (1,1,1,1,1,now(),now(),0);

DROP TABLE IF EXISTS pss_label;
CREATE TABLE `pss_label` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `label_name` varchar(32) NOT NULL DEFAULT '' COMMENT '标签名字',
  `creator_id` bigint unsigned NOT NULL COMMENT '添加人',
  `updater_id` bigint unsigned NOT NULL COMMENT '更新操作者 id',
  `create_time` timestamp NOT NULL DEFAULT (now()),
  `update_time` timestamp NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '0删除标志',
  PRIMARY KEY (`id`)
);
insert into pss_label values (1,'label_name',1,1,now(),now(),0);

DROP TABLE IF EXISTS user_basic;
CREATE TABLE `user_basic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `wechat_num` varchar(50) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `slogan` varchar(64) DEFAULT NULL COMMENT '个性签名',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` tinyint DEFAULT NULL COMMENT '性别，0: male，1:female',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `updater_id` bigint unsigned DEFAULT NULL COMMENT '更新操作者 id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_basic_wechat_num_uindex` (`wechat_num`)
);
insert into user_basic values (1,'administrator','admin','phone_num','wechat_number','email','man is not made for defeat',now(),'0','link_url',1,now(),now());

DROP TABLE IF EXISTS user_expand;
CREATE TABLE `user_expand` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL COMMENT '用户id',
  `open_id` varchar(64) NOT NULL COMMENT '用户唯一标识',
  `union_id` varchar(64) DEFAULT NULL COMMENT '微信开发平台唯一标识',
  `session_key` varchar(64) NOT NULL COMMENT '会话秘钥',
  `updater_id` bigint unsigned DEFAULT NULL COMMENT '更新操作者 id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_open_id_uindex` (`open_id`),
  UNIQUE KEY `user_id_uindex` (`user_id`)
);
insert into user_expand values (1,1,'open_id','union_id','session_key',1,now(),now());
