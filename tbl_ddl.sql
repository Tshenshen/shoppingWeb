
#创建表脚本

DROP TABLE if EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `sex` char(1) DEFAULT NULL COMMENT '0:女  1:男',
  `is_seller` char(1) DEFAULT NULL COMMENT '1为卖家',
  `is_delete` char(1) DEFAULT NULL COMMENT '1为删除',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE if EXISTS `tbl_goods`;
CREATE TABLE `tbl_goods` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '商品 id',
  `name` varchar(100) NOT NULL COMMENT '商品名字',
  `price` double NOT NULL COMMENT '商品 价格',
  `stock` int NOT NULL COMMENT '库存 数量',
  `describe` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品描述',
  `pic_paths` varchar(2000) DEFAULT NULL COMMENT '商品 图片路径',
  `create_date` datetime DEFAULT NULL COMMENT '商品 上架时间',
  `update_date` datetime DEFAULT NULL COMMENT '商品 更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE if EXISTS `tbl_order`;
CREATE TABLE `tbl_order` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int NOT NULL COMMENT '订购者id',
  `receive_info_id` int DEFAULT NULL COMMENT '收件信息id',
  `create_date` datetime DEFAULT NULL COMMENT '创建订单时间',
  `state` char(1) DEFAULT NULL COMMENT '订单状态，0为未完成，1为完成，2为取消',
  `update_date` datetime DEFAULT NULL COMMENT '订单状态更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE if EXISTS `tbl_receive_info`;
CREATE TABLE `tbl_receive_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '收件信息id',
  `user_id` int DEFAULT NULL COMMENT '绑定的用户id',
  `receiver` varchar(100) DEFAULT NULL COMMENT '收货人名字',
  `phone_number` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(500) DEFAULT NULL COMMENT '配送地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE if EXISTS `tbl_order_cell`;
CREATE TABLE `tbl_order_cell` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单单元ID',
  `order_id` int NOT NULL COMMENT '总订单ID',
  `goods_id` int DEFAULT NULL COMMENT '商品id',
  `goods_num` int DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE if EXISTS `tbl_cert`;
CREATE TABLE `tbl_cert` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '购物车编号',
  `user_id` int DEFAULT NULL COMMENT '储存者id',
  `goods_id` int DEFAULT NULL COMMENT '商品id',
  `goods_num` int DEFAULT NULL COMMENT '商品数量',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;