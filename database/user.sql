-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(20) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL COMMENT 'male:"0" female:"1"',
  `phone` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `enabled` char(1) NOT NULL DEFAULT '0' COMMENT 'yes:"0" no:"1"',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
