CREATE DATABASE sadness;

CREATE TABLE `sadness.transaction_message` (
    `message_id` bigint unsigned NOT NULL COMMENT '消息ID',
    `business_tag` varchar(36) NOT NULL COMMENT '业务唯一标识',
    `message_body` longtext NOT NULL COMMENT '消息体',
    `message_data_type` varchar(50) DEFAULT NULL COMMENT '消息数据类型',
    `consumer_queue` varchar(100) NOT NULL COMMENT '消费队列',
    `message_send_times` smallint NOT NULL DEFAULT '0' COMMENT '消息重发次数',
    `message_confirm_times` smallint NOT NULL DEFAULT '0' COMMENT '消息确认次数',
    `dead` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否死亡',
    `status` smallint NOT NULL COMMENT '状态',
    `confirm_info` json NOT NULL COMMENT '确认信息',
    `remark` varchar(200) DEFAULT NULL COMMENT '备注',
    `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
    `create_time` bigint NOT NULL COMMENT '创建时间',
    `update_time` bigint NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`message_id`)
) ENGINE=InnoDB;


CREATE TABLE `sadness.confirm_log` (
   `id` varchar(36) NOT NULL,
   `message_id` varchar(36) NOT NULL COMMENT '事务消息ID',
   `error_info` text COMMENT '异常消息',
   `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
   `create_time` bigint NOT NULL COMMENT '创建时间',
   `update_time` bigint NOT NULL COMMENT '修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB;


create database bank1;
create database bank2;

# 下面bank1和bank2各自键相同的表

CREATE TABLE `account` (
    `id` bigint NOT NULL,
    `user_id` bigint NOT NULL COMMENT '账户所有人',
    `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
    `status` tinyint NOT NULL DEFAULT '0' COMMENT '账户状态：0→正常，-1冻结',
    `level` tinyint NOT NULL DEFAULT '0' COMMENT '账户等级：0→普通账户，1→白金账户，2→黄金账户',
    `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `account_log` (
   `id` bigint NOT NULL,
   `business_tag` varchar(36) NOT NULL COMMENT '业务唯一标识',
   `user_id` bigint NOT NULL COMMENT '账户所有人',
   `account_id` bigint NOT NULL COMMENT '账户ID',
   `action` tinyint NOT NULL COMMENT '动作：0→扣款，1→入账',
   `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
   `create_time` bigint NOT NULL COMMENT '创建时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `index_business_tag` (`business_tag`)
) ENGINE=InnoDB;

INSERT INTO bank1.account VALUES (1,1,1000,0,0,0);
INSERT INTO bank2.account VALUES (1,1,1000,0,0,0);