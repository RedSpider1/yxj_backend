SET MODE MYSQL;

CREATE TABLE `user_record` (
  `id` bigint NOT NULL,
  `biz_id` bigint DEFAULT NULL,
  `biz_type` varchar(64) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `delete` int NOT NULL,
  `record_id` bigint DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL
);

DROP TABLE IF EXISTS group;
create table `group`
(
    id           bigint auto_increment
        primary key,
    biz_type     varchar(10)                           not null comment 'group',
    title        varchar(255)                          not null comment '标题',
    introduction varchar(255)                          not null comment '介绍',
    `condition`  varchar(255)                          null comment '条件',
    status       varchar(16) default 'draft'           not null comment '草稿 draft 审核 auditing 寻找 finding 成功 success 失败 fail 撤回 revert',
    start_time   datetime                              not null comment '开始时间',
    end_time     datetime                              not null comment '结束时间',
    anonymous    tinyint     default 0                 not null comment '是否匿名 0 否 1 是',
    resource     varchar(255)                          null comment '上传资源路径',
    owner_id     bigint                                not null comment '创建人',
    audit_id     bigint                                not null comment '审核单',
    address_id   bigint                                null comment '联系地址',
    label_ids    varchar(255)                          null comment '标签数组',
    create_time  datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint index_audit_id
        unique (audit_id)
);

DROP TABLE IF EXISTS address;
CREATE TABLE `address` (
  `id` bigint NOT NULL,
  `province` varchar(255) DEFAULT NULL, -- CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
  `city` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL
);

DROP TABLE IF EXISTS audit;
create table audit
(
    id          bigint auto_increment comment '自增id'
        primary key,
    status      varchar(16) default 'pending'         not null comment '未审核 pending 审核中 auditing  成功 success 失败 fail',
    type        tinyint     default 0                 not null comment '0 automatic 1 manual',
    biz_type    varchar(16) default 'group'           not null comment '组队单 group',
    operator_id bigint                                not null comment '操作人',
    remark      varchar(255)                          null comment '评论',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '审核单';

DROP TABLE IF EXISTS user_group;
create table user_group
(
    id            bigint auto_increment
        primary key,
    group_id      bigint                             not null comment '组队单id',
    user_id       bigint                             not null comment '参与用户id',
    `delete`      tinyint  default 0                 not null comment '0 正常 1 退出',
    contract_type tinyint                            not null comment '0 we_chat 1 phone',
    contract_info varchar(255)                       not null comment '联系方式',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint index_group_id_user_id
        unique (group_id, user_id),
    constraint index_user_id_group_id
        unique (user_id, group_id)
);

INSERT INTO address VALUES (0,'四川','成都','金牛区','荷花街道',0);