# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.33)
# Database: chunxiao_mall
# Generation Time: 2017-07-12 06:26:13 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(16) NOT NULL COMMENT '登录账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `role` smallint(6) NOT NULL COMMENT '用户类型：1',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '1, 有效 2 逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accountIdx` (`account`),
  KEY `INX_MOBILE` (`account`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_activity`;

CREATE TABLE `t_activity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `detail` longtext,
  `is_default` int(1) NOT NULL DEFAULT '2' COMMENT '是否默认',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动首页表';



# Dump of table t_admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(45) NOT NULL COMMENT '账号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '角色:1平台管理员 2七贷管理员 3云贷管理员',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `white_page` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `accountIdx` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_app`;

CREATE TABLE `t_app` (
  `id` int(11) unsigned NOT NULL,
  `account_id` int(11) unsigned NOT NULL COMMENT '帐户id',
  `app_name` char(20) DEFAULT NULL COMMENT '接入方名称',
  `app_key` char(32) DEFAULT NULL COMMENT '接入方id',
  `app_secret` char(32) DEFAULT NULL COMMENT '接入方secret',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_attribute
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_attribute`;

CREATE TABLE `t_attribute` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `attr_group_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性组id',
  `attr_value` varchar(60) NOT NULL DEFAULT '' COMMENT '具体的属性值',
  `sort_order` int(11) NOT NULL DEFAULT '0' COMMENT '排序权重',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `cat_id` (`attr_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性取值表';



# Dump of table t_attribute_cat
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_attribute_cat`;

CREATE TABLE `t_attribute_cat` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '类目id',
  `attr_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性id',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `cat_id` (`cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性与三级类目关系表';



# Dump of table t_attribute_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_attribute_group`;

CREATE TABLE `t_attribute_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `attr_name` varchar(60) NOT NULL DEFAULT '' COMMENT '属性名',
  `attr_values` text COMMENT '该属性所有值集合',
  `sort_order` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序权重',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `attr_name_UNIQUE` (`attr_name`),
  KEY `attr_name` (`attr_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性表';



# Dump of table t_brand
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_brand`;

CREATE TABLE `t_brand` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(60) NOT NULL DEFAULT '' COMMENT '品牌名称',
  `brand_logo` varchar(80) DEFAULT '',
  `brand_desc` varchar(255) DEFAULT '' COMMENT '品牌描述',
  `site_url` varchar(255) DEFAULT '',
  `sort_order` int(11) NOT NULL DEFAULT '0',
  `is_show` tinyint(1) NOT NULL COMMENT '是否展示',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `brand_name_UNIQUE` (`brand_name`),
  KEY `is_show` (`is_show`),
  KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌表';



# Dump of table t_brand_cat
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_brand_cat`;

CREATE TABLE `t_brand_cat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brand_id` int(11) NOT NULL COMMENT '品牌id',
  `cat_id` int(11) NOT NULL COMMENT '三级类目id',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌与三级类目关系表';



# Dump of table t_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父类目',
  `cat_name` varchar(90) NOT NULL DEFAULT '' COMMENT '类目名称',
  `cat_desc` varchar(255) DEFAULT '' COMMENT '描述',
  `sort_order` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '类目排序',
  `measure_unit` varchar(15) DEFAULT '' COMMENT '单位',
  `is_show_in_nav` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否展示在侧边栏',
  `is_show` tinyint(1) unsigned DEFAULT '1' COMMENT '是否要展示',
  `grade` smallint(4) NOT NULL DEFAULT '0' COMMENT '类目层级',
  `image` varchar(255) DEFAULT NULL COMMENT '类目图片',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `invoice_goods_code` varchar(45) DEFAULT NULL COMMENT '发票局商品编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cat_name_UNIQUE` (`cat_name`),
  KEY `parent_id` (`parent_id`),
  KEY `cat_name` (`cat_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='类目表';



# Dump of table t_consignee_address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_consignee_address`;

CREATE TABLE `t_consignee_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `customer_id` int(11) NOT NULL COMMENT '用户id 对应订单表的customer_id',
  `consignee_name` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人电话',
  `province` varchar(32) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(32) NOT NULL DEFAULT '' COMMENT '市',
  `district` varchar(128) NOT NULL DEFAULT '' COMMENT '区',
  `detail` varchar(128) NOT NULL DEFAULT '' COMMENT '详情',
  `address` varchar(512) NOT NULL DEFAULT '' COMMENT '完整地址',
  `is_default` int(1) NOT NULL DEFAULT '2' COMMENT '是否默认地址',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `consignee_name` (`consignee_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_down_pay
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_down_pay`;

CREATE TABLE `t_down_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `mount` int(10) unsigned NOT NULL COMMENT '金额',
  `card_no` varchar(128) DEFAULT NULL COMMENT '银行卡号',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `bank_phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `acct_name` varchar(30) DEFAULT NULL COMMENT '开户名',
  `bank_code` varchar(60) DEFAULT '' COMMENT '开户行编码',
  `id_no` varchar(128) DEFAULT NULL COMMENT '证件号',
  `id_type` varchar(20) DEFAULT '1' COMMENT '证件类型 1-身份证，2-护照',
  `ip` varchar(128) DEFAULT NULL,
  `pay_result_desc` varchar(128) DEFAULT '' COMMENT '支付结果描述',
  `pay_result` int(11) DEFAULT NULL COMMENT '支付结果',
  `withhold_id` varchar(64) DEFAULT '' COMMENT '代扣id',
  `status` int(11) NOT NULL COMMENT '1待提交（绑卡）2待确认（验证码) 3待支付 4支付中 5支付失败 6支付成功 7已退款',
  `close_time` timestamp NULL DEFAULT NULL COMMENT '成功或者失败时间',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首付';



# Dump of table t_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `brand_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '品牌id',
  `third_cat_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '三级类目id',
  `supplier_id` int(11) unsigned NOT NULL COMMENT '供应商id',
  `goods_sn` varchar(60) DEFAULT '' COMMENT '商品编码',
  `goods_name` varchar(120) NOT NULL DEFAULT '' COMMENT '商品名称',
  `cat_name` varchar(100) NOT NULL DEFAULT '' COMMENT '类目名称',
  `brand_name` varchar(20) NOT NULL DEFAULT '' COMMENT '品牌名称',
  `goods_number` int(11) DEFAULT '0' COMMENT '商品库存',
  `goods_img` varchar(2048) NOT NULL DEFAULT '' COMMENT '商品图片 多图用,分隔',
  `second_cat_id` int(10) NOT NULL DEFAULT '0' COMMENT '2级类目id',
  `first_cat_id` int(10) NOT NULL DEFAULT '0' COMMENT '1级类目id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供应商名',
  `unit` varchar(45) NOT NULL COMMENT '计量单位',
  `edit_time` timestamp NULL DEFAULT NULL COMMENT '编辑时间',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `goods_sn_UNIQUE` (`goods_sn`),
  KEY `goods_sn` (`goods_sn`),
  KEY `cat_id` (`third_cat_id`),
  KEY `brand_id` (`brand_id`),
  KEY `goods_number` (`goods_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';



# Dump of table t_goods_app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods_app`;

CREATE TABLE `t_goods_app` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `goods_title` varchar(255) DEFAULT '+' COMMENT '商品id',
  `sort_order` int(11) DEFAULT '100' COMMENT '排序权重',
  `is_promote` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐商品:1是,2否',
  `promote_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '推荐时间',
  `buy_limit` int(11) DEFAULT '0' COMMENT '限购数量',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签id',
  `tag_name` varchar(128) DEFAULT NULL COMMENT '标签名',
  `status` int(2) NOT NULL COMMENT '商品状态[1:未上架  2:已上架 3:已下架 ]',
  `off_sell_time` timestamp NULL DEFAULT NULL COMMENT '下架时间',
  `on_sell_time` timestamp NULL DEFAULT NULL COMMENT '上架时间',
  `default_sku_id` int(11) NOT NULL COMMENT '商品列表中显示的默认sku',
  `show_way` int(11) DEFAULT NULL COMMENT '展示方式：1正常 2轮播 3爆品',
  `hot_img` varchar(256) DEFAULT NULL COMMENT '爆品图片',
  `state` int(1) NOT NULL DEFAULT '1',
  `hide` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏',
  `edit_time` timestamp NULL DEFAULT NULL COMMENT '编辑时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `min_down_pay` int(11) DEFAULT '0' COMMENT '最低首付款',
  `permit_instalment` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可分期',
  PRIMARY KEY (`id`),
  KEY `goodsIdIndex` (`goods_id`),
  KEY `onSellTimeIndex` (`on_sell_time`),
  KEY `promoteTimeIndex` (`promote_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品与渠道关系表';



# Dump of table t_goods_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods_detail`;

CREATE TABLE `t_goods_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `detail` longtext COMMENT 'html 图文详情',
  `specification` longtext COMMENT '规格详情 images 七牛地址',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情表';



# Dump of table t_goods_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods_order`;

CREATE TABLE `t_goods_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `customer_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `customer_name` varchar(10) NOT NULL DEFAULT '' COMMENT '用户名',
  `customer_mobile` varchar(20) NOT NULL COMMENT '用户手机号',
  `goods_id` int(11) unsigned zerofill NOT NULL COMMENT '商品id',
  `sku_id` int(11) unsigned NOT NULL COMMENT 'sku id',
  `addr_id` int(11) unsigned NOT NULL COMMENT '收货地址id',
  `app_id` int(11) unsigned NOT NULL COMMENT '接入方id',
  `promote_id` int(11) unsigned zerofill DEFAULT NULL COMMENT '促销活动 id',
  `app_name` varchar(20) NOT NULL COMMENT '渠道(接入方)名称',
  `goods_detail_id` int(10) unsigned NOT NULL COMMENT '商品详情id',
  `delivery_order_id` int(11) unsigned DEFAULT NULL COMMENT '发货单id',
  `goods_name` varchar(120) NOT NULL DEFAULT '' COMMENT '商品名称',
  `invoice_goods_code` varchar(64) DEFAULT NULL COMMENT '发票局商品编码',
  `goods_img` varchar(2048) NOT NULL DEFAULT '' COMMENT '商品图片',
  `attr_value_json` varchar(255) NOT NULL COMMENT '属性值',
  `order_end_desc` char(20) DEFAULT NULL COMMENT '订单关闭原因',
  `order_end_time` timestamp NULL DEFAULT NULL COMMENT '订单关闭时间',
  `has_invoice` tinyint(1) DEFAULT NULL COMMENT '是否开发票[1:是,2:否]',
  `invoice_status` int(2) DEFAULT '1' COMMENT '发票状态[1.未开票,2.开票中,3.已开票,4.已拒绝,5.已取消,6.红票]',
  `logistics_name` char(20) DEFAULT '' COMMENT '物流公司',
  `logistics_no` varchar(32) NOT NULL DEFAULT '' COMMENT '物流单号',
  `erp_order_no` varchar(45) NOT NULL DEFAULT '' COMMENT '传给顺丰的订单号',
  `delivery_way` varchar(20) DEFAULT NULL COMMENT '发货方式',
  `delivery_count` int(11) NOT NULL DEFAULT '0' COMMENT '发货次数',
  `logistics_id` varchar(20) NOT NULL DEFAULT '' COMMENT '发货方式对应的物流公司id',
  `consignee_mobile` varchar(255) NOT NULL DEFAULT '' COMMENT '收货人电话',
  `consignee_name` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人',
  `consignee_address` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址',
  `total_price` int(11) NOT NULL COMMENT '订单总价',
  `service_price` int(11) NOT NULL COMMENT '服务费',
  `sku_price` int(11) unsigned DEFAULT NULL COMMENT 'sku实际成交单价',
  `sku_origin_price` int(11) unsigned DEFAULT NULL COMMENT 'sku原价',
  `free_rate_count` int(11) DEFAULT NULL COMMENT '免息抵扣',
  `instalment_principal` int(11) DEFAULT NULL COMMENT '分期本金',
  `installment_periods` int(11) unsigned DEFAULT NULL COMMENT '分期数',
  `installment_rate` varchar(32) DEFAULT NULL COMMENT '分期利率',
  `monthly_pay` int(11) DEFAULT NULL COMMENT '月供',
  `down_pay` int(11) DEFAULT NULL COMMENT '首付款',
  `pay_result_desc` varchar(128) NOT NULL DEFAULT '' COMMENT '支付结果描述',
  `remark` varchar(128) DEFAULT NULL COMMENT '订单备注',
  `pay_result` int(11) DEFAULT NULL COMMENT '支付结果',
  `sku_count` int(11) unsigned DEFAULT NULL COMMENT '商品数量',
  `pay_way` int(11) DEFAULT NULL COMMENT '支付方式 [1.额度分期（旧版本） 2.分期付款 3.全额付款 4.最低首付 5.固定比例首付】',
  `post_cost` int(11) DEFAULT NULL COMMENT '邮费',
  `actually_pay` int(11) DEFAULT NULL COMMENT '订单实付金额',
  `status` int(2) unsigned NOT NULL DEFAULT '1' COMMENT '[1:待付款 2:付款中 3:已付款 4:已审核 5:备货中 6:已发货 7:已完成 8:已关闭] 9退款中 10  退款失败',
  `close_status` int(2) unsigned DEFAULT NULL COMMENT '订单关闭状态[1:客户未付款取消 2:后台未付款取消  3:未付款超时取消 4:客户付款后取消 5:后台付款后取消 6:审核拒绝 7:备货中退货 8:已发货退货  9:已完成 10:已完成退货（订单已完成后发起退货）11：支付失败(订单付款中状态时付款失败) 12：付款中退款',
  `state` int(1) NOT NULL DEFAULT '1',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `signed_time` timestamp NULL DEFAULT NULL COMMENT '签收时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `supplier_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `audit_remark` varchar(255) DEFAULT NULL,
  `ip` varchar(128) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_no`),
  KEY `customer_id` (`customer_id`),
  KEY `goods_id` (`goods_id`),
  KEY `app_id` (`app_id`),
  KEY `sku_id` (`sku_id`),
  KEY `order_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';



# Dump of table t_instalment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_instalment`;

CREATE TABLE `t_instalment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `principal` int(11) NOT NULL COMMENT '分期本金',
  `period` int(11) unsigned NOT NULL COMMENT '分期数',
  `rate` decimal(15,12) NOT NULL COMMENT '分期利率',
  `monthly_pay` int(11) DEFAULT NULL COMMENT '月供',
  `service_price` int(11) DEFAULT NULL COMMENT '服务费',
  `coupon` int(11) DEFAULT NULL COMMENT '免息抵扣',
  `pay_result_desc` varchar(128) DEFAULT '' COMMENT '支付结果描述',
  `pay_result` int(11) DEFAULT NULL COMMENT '支付结果',
  `status` int(11) NOT NULL COMMENT '1待提交（绑卡）2待确认（验证码）3待支付 4支付中 5支付失败 6支付成功 7已退款 8退款失败',
  `ip` varchar(128) DEFAULT NULL,
  `close_time` timestamp NULL DEFAULT NULL COMMENT '成功或者失败时间',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `action` int(11) DEFAULT NULL COMMENT '退款动作 1：取消 2:审核拒绝 3:退货',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_UNIQUE` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分期表';



# Dump of table t_invoice_application
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_invoice_application`;

CREATE TABLE `t_invoice_application` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `application_no` varchar(32) NOT NULL DEFAULT '' COMMENT '申请号',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `order_price` int(11) NOT NULL COMMENT '订单金额',
  `order_create_time` timestamp NULL DEFAULT NULL COMMENT '下单时间',
  `invoice_no` varchar(32) DEFAULT NULL COMMENT '发票号',
  `invoice_type` int(2) NOT NULL COMMENT '发票类型:[1.电子发票，2.纸质普票，3.纸质专票]',
  `invoice_title` varchar(64) NOT NULL COMMENT '发票抬头',
  `invoice_value` int(11) NOT NULL COMMENT '发票金额',
  `invoice_content` varchar(100) NOT NULL COMMENT '发票内容',
  `invoice_reason` int(2) NOT NULL DEFAULT '1' COMMENT '开票原因:[1.保修,2.其他]',
  `invoice_remark` varchar(64) DEFAULT NULL COMMENT '发票备注',
  `tax_id` varchar(32) DEFAULT NULL COMMENT '企业税号',
  `status` int(2) unsigned NOT NULL DEFAULT '1' COMMENT '开票状态:[1.未开票，2.开票中，3.已开票，4.已拒绝，5.已取消，6.红票]',
  `status_remark` varchar(64) DEFAULT NULL COMMENT '开票状态备注',
  `taker_email` varchar(64) DEFAULT NULL COMMENT '收票人邮箱',
  `taker_name` varchar(64) DEFAULT NULL COMMENT '收票人姓名',
  `taker_mobile` varchar(64) DEFAULT NULL COMMENT '收票人电话',
  `taker_address` varchar(64) DEFAULT NULL COMMENT '收票人地址',
  `app_id` int(11) NOT NULL DEFAULT '1' COMMENT '接入方id',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '0.删除 1.有效',
  `application_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `invoice_url` varchar(100) DEFAULT NULL COMMENT '发票链接',
  `invoice_code` varchar(64) DEFAULT NULL COMMENT '发票代码',
  `logistics_name` varchar(20) DEFAULT NULL COMMENT '物流公司',
  `logistics_no` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `invoice_create_time` timestamp NULL DEFAULT NULL COMMENT '开票时间',
  `send_mail` int(1) NOT NULL DEFAULT '0' COMMENT '邮件发送状态:[1.成功,0.失败]',
  `up_qiNiu` int(1) NOT NULL DEFAULT '0' COMMENT '上传七牛状态:[1.成功,0.失败]',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发票申请表';



# Dump of table t_logistics_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_logistics_order`;

CREATE TABLE `t_logistics_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '业务号',
  `order_no` varchar(45) NOT NULL DEFAULT '' COMMENT '订单表的订单号',
  `erp_order_no` varchar(45) NOT NULL DEFAULT '' COMMENT '传给顺丰的订单号',
  `logistics_name` varchar(45) DEFAULT '' COMMENT '快递公司名',
  `logistics_id` varchar(45) NOT NULL COMMENT '快递公司id',
  `receiver_country` varchar(45) NOT NULL COMMENT '收货人国家',
  `receiver_address` varchar(128) NOT NULL DEFAULT '' COMMENT '收货人地址',
  `receiver_company` varchar(45) DEFAULT NULL COMMENT '收货人公司',
  `receiver_name` varchar(45) NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `receiver_zip_code` varchar(10) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_mobile` varchar(16) DEFAULT NULL COMMENT '收货人手机号',
  `receiver_phone` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `shipment_id` varchar(128) DEFAULT NULL COMMENT '出库号',
  `result` varchar(45) DEFAULT NULL COMMENT '快递公司出库结果',
  `note` varchar(1024) DEFAULT NULL COMMENT '快递公司返回结果说明',
  `sf_order_status` varchar(1024) DEFAULT NULL COMMENT '快递公司运单状态',
  `status` int(11) NOT NULL COMMENT '物流订单状态：1待发起出库申请 2出库申请成功  3出库申请失败 4取消出库 5已发货 6已签收 7退回',
  `sf_status_desc` varchar(1024) DEFAULT NULL COMMENT '快递公司订单状态说明',
  `way_bill_no` varchar(128) DEFAULT NULL COMMENT '运单号',
  `return_tracking` varchar(128) DEFAULT NULL COMMENT '回单号',
  `state` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `event_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '最新事件时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_order` (`app_id`,`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_logistics_sku
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_logistics_sku`;

CREATE TABLE `t_logistics_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `erp_order_no` varchar(45) NOT NULL COMMENT '物流单号',
  `sku_no` varchar(45) NOT NULL COMMENT 'sku编码',
  `sku_count` int(11) NOT NULL COMMENT 'sku数量',
  `imie` varchar(1024) DEFAULT NULL COMMENT '序列号，数组',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `unique_key` varchar(45) DEFAULT '' COMMENT '唯一码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `erp_order_no_sku` (`erp_order_no`,`sku_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单与sku关系表';



# Dump of table t_promote_activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_promote_activity`;

CREATE TABLE `t_promote_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promote_name` varchar(45) DEFAULT NULL COMMENT '活动名',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `buy_limit` int(11) DEFAULT NULL COMMENT '购买上限',
  `status` int(11) NOT NULL COMMENT '-1 冻结 1未冻结[保留2 ,3]',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '0删除 1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时特价';



# Dump of table t_promote_sku
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_promote_sku`;

CREATE TABLE `t_promote_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sku_id` int(11) NOT NULL COMMENT 'sku id',
  `promote_id` int(11) NOT NULL COMMENT '限时活动id',
  `special_price` int(11) NOT NULL COMMENT '特价',
  `goods_id` int(11) NOT NULL,
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '0删除 1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `promoteSkuIdx` (`promote_id`,`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动sku关系表';



# Dump of table t_refund
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_refund`;

CREATE TABLE `t_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mount` int(10) unsigned NOT NULL COMMENT '金额',
  `status` int(11) NOT NULL COMMENT '4支付中 5支付失败 6支付成功',
  `card_no` varchar(128) NOT NULL COMMENT '账号',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `bank_phone` varchar(16) NOT NULL COMMENT '手机号',
  `acct_name` varchar(30) NOT NULL COMMENT '开户名',
  `bank_code` varchar(60) NOT NULL DEFAULT '' COMMENT '开户行编码',
  `id_no` varchar(128) NOT NULL,
  `id_type` varchar(20) NOT NULL DEFAULT '1' COMMENT '证件类型 1-身份证，2-护照',
  `try_count` int(11) NOT NULL DEFAULT '0' COMMENT '尝试次数',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `down_pay_id` int(11) NOT NULL COMMENT '首付款id',
  `withdraw_id` varchar(64) DEFAULT '' COMMENT '支付网关唯一取现ID',
  `close_time` timestamp NULL DEFAULT NULL COMMENT '成功或者失败时间',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `pay_result_desc` varchar(128) DEFAULT '' COMMENT '支付结果描述',
  `pay_result` int(11) DEFAULT NULL COMMENT '支付结果',
  `ip` varchar(128) NOT NULL,
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `down_pay_id_UNIQUE` (`down_pay_id`),
  UNIQUE KEY `order_no_UNIQUE` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退款';



# Dump of table t_refund_water
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_refund_water`;

CREATE TABLE `t_refund_water` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL COMMENT '4支付中 5支付失败 6支付成功 ',
  `pay_result_desc` varchar(128) DEFAULT '' COMMENT '支付结果描述',
  `pay_result` int(11) DEFAULT NULL COMMENT '支付结果',
  `water_no` varchar(64) NOT NULL DEFAULT '' COMMENT '流水号',
  `withdraw_id` varchar(64) DEFAULT '' COMMENT '支付网关唯一取现ID',
  `ip` varchar(128) NOT NULL,
  `refund_id` int(11) NOT NULL,
  `close_time` timestamp NULL DEFAULT NULL COMMENT '成功或者失败时间',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `water_no_UNIQUE` (`water_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退款';



# Dump of table t_seller
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_seller`;

CREATE TABLE `t_seller` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `app_id` int(11) unsigned NOT NULL COMMENT '接入方id',
  `seller_name` varchar(255) DEFAULT NULL,
  `seller_desc` varchar(255) DEFAULT '',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `goods_id` (`seller_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_sku
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sku`;

CREATE TABLE `t_sku` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_model_sn` char(64) DEFAULT NULL COMMENT '产品型号',
  `attr_value_text` varchar(255) NOT NULL DEFAULT '' COMMENT '属性值拼接串',
  `attr_value_json` varchar(1024) NOT NULL DEFAULT '' COMMENT '属性值集josn串',
  `sku_goods_sn` char(32) NOT NULL COMMENT 'sku编码',
  `postage` int(11) NOT NULL COMMENT '运费成本',
  `has_invoice` tinyint(1) NOT NULL COMMENT '是否开发票[1:是,2:否]',
  `suggest_price` int(11) NOT NULL COMMENT '建议价',
  `market_price` int(11) NOT NULL COMMENT '成本价',
  `sku_count` int(11) DEFAULT NULL COMMENT '库存',
  `logistics_id` varchar(45) NOT NULL COMMENT '快递公司 id ',
  `logistics_name` varchar(45) DEFAULT NULL COMMENT '快递公司名',
  `delivery_way` varchar(45) NOT NULL COMMENT '发货方式',
  `status` int(11) NOT NULL COMMENT 'sku状态 1启用 2禁用',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sku_goods_sn_UNIQUE` (`sku_goods_sn`),
  KEY `goods_id` (`goods_id`),
  KEY `sku_sn` (`goods_model_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_sku_app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sku_app`;

CREATE TABLE `t_sku_app` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `promote_price` int(11) DEFAULT NULL COMMENT '销售价',
  `default_instalment_count` int(11) DEFAULT NULL COMMENT '默认的分期数',
  `default_instalment_rate` decimal(15,12) DEFAULT NULL COMMENT '默认的分期利率',
  `instalment_rates` varchar(1024) DEFAULT NULL COMMENT '分期费率列表',
  `down_payment` int(11) DEFAULT NULL COMMENT '首付款',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `sku_id` int(11) NOT NULL COMMENT 'sku id',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `skuIdIndex` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sku与渠道关系表';



# Dump of table t_sku_snapshot
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sku_snapshot`;

CREATE TABLE `t_sku_snapshot` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `sku_id` int(11) NOT NULL COMMENT 'sku id',
  `brand_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '品牌id',
  `third_cat_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '三级类目id',
  `supplier_id` int(11) unsigned NOT NULL COMMENT '供应商id',
  `goods_sn` varchar(60) NOT NULL DEFAULT '' COMMENT '商品编码',
  `goods_name` varchar(120) NOT NULL DEFAULT '' COMMENT '商品名称',
  `cat_name` varchar(100) NOT NULL DEFAULT '' COMMENT '类目名称',
  `brand_name` varchar(20) NOT NULL DEFAULT '' COMMENT '品牌名称',
  `goods_number` int(11) DEFAULT '0' COMMENT '商品库存',
  `goods_img` varchar(2048) NOT NULL DEFAULT '' COMMENT '商品图片 多图用,分隔',
  `second_cat_id` int(10) NOT NULL DEFAULT '0' COMMENT '2级类目id',
  `first_cat_id` int(10) NOT NULL DEFAULT '0' COMMENT '1级类目id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供应商名',
  `unit` varchar(45) NOT NULL COMMENT '计量单位',
  `edit_time` timestamp NULL DEFAULT NULL COMMENT '编辑时间',
  `goods_title` varchar(255) NOT NULL DEFAULT '+' COMMENT '商品id',
  `sort_order` int(11) DEFAULT '100' COMMENT '排序权重',
  `is_promote` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐商品:1是,2否',
  `promote_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '推荐时间',
  `buy_limit` int(11) DEFAULT '0' COMMENT '限购数量',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签id',
  `tag_name` varchar(128) DEFAULT NULL COMMENT '标签名',
  `goods_status` int(2) NOT NULL COMMENT '商品状态[1:未上架  2:已上架 3:已下架 ]',
  `off_sell_time` timestamp NULL DEFAULT NULL COMMENT '下架时间',
  `on_sell_time` timestamp NULL DEFAULT NULL COMMENT '上架时间',
  `default_sku_id` int(11) NOT NULL COMMENT '商品列表中显示的默认sku',
  `show_way` int(11) DEFAULT NULL COMMENT '展示方式：1正常 2轮播 3爆品',
  `hot_img` varchar(256) DEFAULT NULL COMMENT '爆品图片',
  `goods_model_sn` char(64) NOT NULL DEFAULT '' COMMENT '产品型号',
  `attr_value_text` varchar(255) NOT NULL DEFAULT '' COMMENT '属性值拼接串',
  `attr_value_json` varchar(1024) NOT NULL DEFAULT '' COMMENT '属性值集josn串',
  `sku_goods_sn` char(32) NOT NULL COMMENT 'sku编码',
  `postage` int(11) NOT NULL COMMENT '运费成本',
  `has_invoice` tinyint(1) NOT NULL COMMENT '是否开发票[1:是,2:否]',
  `suggest_price` int(11) NOT NULL COMMENT '建议价',
  `market_price` int(11) NOT NULL COMMENT '成本价',
  `sku_count` int(11) DEFAULT NULL COMMENT '库存',
  `logistics_id` varchar(45) NOT NULL COMMENT '快递公司 id ',
  `logistics_name` varchar(45) DEFAULT NULL COMMENT '快递公司名',
  `delivery_way` varchar(45) NOT NULL COMMENT '发货方式',
  `sku_status` int(11) NOT NULL COMMENT 'sku状态 1启用 2禁用',
  `hide` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏',
  `promote_price` int(11) DEFAULT NULL COMMENT '销售价',
  `default_instalment_count` int(11) DEFAULT NULL COMMENT '默认的分期数',
  `default_instalment_rate` decimal(15,12) DEFAULT NULL COMMENT '默认的分期利率',
  `instalment_rates` varchar(1024) DEFAULT NULL COMMENT '分期费率列表',
  `down_payment` int(11) DEFAULT NULL COMMENT '首付款',
  `invoice_goods_code` varchar(45) DEFAULT NULL COMMENT '发票局商品编码',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `goods_sn_UNIQUE` (`goods_sn`),
  KEY `goods_sn` (`goods_sn`),
  KEY `cat_id` (`third_cat_id`),
  KEY `brand_id` (`brand_id`),
  KEY `goods_number` (`goods_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品快照表';



# Dump of table t_sms
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_sms`;

CREATE TABLE `t_sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `biz_no` varchar(10) DEFAULT NULL COMMENT '业务号',
  `sp_id` tinyint(4) DEFAULT NULL,
  `mobile` varchar(16) DEFAULT NULL,
  `content` varchar(256) DEFAULT NULL,
  `send_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1-成功 2-失败',
  `req_time` timestamp NULL DEFAULT NULL COMMENT '请求时间',
  `send_time` timestamp NULL DEFAULT NULL,
  `state` tinyint(4) NOT NULL COMMENT '1-正常 2-逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_supplier
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_supplier`;

CREATE TABLE `t_supplier` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) NOT NULL DEFAULT '' COMMENT '供应商名称',
  `supplier_desc` varchar(255) DEFAULT '' COMMENT '供应商描述',
  `state` int(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `supplier_name_UNIQUE` (`supplier_name`),
  KEY `suppliers_name` (`supplier_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商';



# Dump of table t_tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '标签名',
  `state` int(11) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';



# Dump of table t_topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_topic`;

CREATE TABLE `t_topic` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `topic_name` varchar(100) NOT NULL DEFAULT '' COMMENT '专题名称',
  `topic_title` varchar(32) NOT NULL DEFAULT '' COMMENT '专题页面标题',
  `pv_key` varchar(32) NOT NULL DEFAULT '' COMMENT '统计标识',
  `detail` longtext,
  `app_id` int(11) NOT NULL COMMENT '接入方id',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '0删除 1有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动专题表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
