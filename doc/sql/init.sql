CREATE TABLE `sys_user` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`created_by` bigint(20) NOT NULL COMMENT '创建人',
`created_time` datetime NOT NULL COMMENT '创建时间',
`updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
`updated_time` datetime DEFAULT NULL COMMENT '更新时间',
`status` int(4) NOT NULL COMMENT '状态（1/正常 2/停用）',
`account` varchar(128) NOT NULL COMMENT '账号',
`password` varchar(128) NOT NULL COMMENT '密码',
`email` varchar(128) NOT NULL COMMENT '邮箱',
`phone` varchar(128) DEFAULT NULL COMMENT '手机号',
`nick_name` varchar(128) NOT NULL COMMENT '昵称',

`last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
`type` int(1) NOT NULL COMMENT '类型1/普通 2/内置',
`del_flag` int(1) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `idx_pa_account` (`account`),
KEY `idx_pa_email` (`email`),
KEY `idx_pa_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=5514908876246703846 DEFAULT CHARSET=utf8mb4 COMMENT='账号表'