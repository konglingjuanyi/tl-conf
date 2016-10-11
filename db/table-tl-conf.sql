CREATE TABLE `tl_conf_group` (
  `group_key` varchar(100) NOT NULL,
  `group_name` varchar(100) NOT NULL COMMENT '描述',
  `isdelete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`group_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tl_conf_group` VALUES ('default', '默认分组', '0');

-- ----------------------------
-- Table structure for tl_conf_node
-- ----------------------------
CREATE TABLE `tl_conf_node` (
  `node_group` varchar(100) NOT NULL COMMENT '分组',
  `node_key` varchar(100) NOT NULL COMMENT '配置Key',
  `node_value` varchar(512) DEFAULT NULL COMMENT '配置Value',
  `node_desc` varchar(100) DEFAULT NULL COMMENT '配置简介',
  `isdelete` tinyint(1) DEFAULT NULL,
  UNIQUE KEY `u_group_key` (`node_group`,`node_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tl_conf_node
-- ----------------------------
INSERT INTO `tl_conf_node` VALUES ('default', 'key01', '168', '测试配置01', '0');
INSERT INTO `tl_conf_node` VALUES ('default', 'key02', '127.0.0.1:3307', '测试配置02', '0');
