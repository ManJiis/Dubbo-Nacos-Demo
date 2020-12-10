# springboot-dubbo-nacos
基于springboot搭建dubbo,使用nacos作为注册中心
1. 统一返回对象
2. 全局异常处理
3. aop日志记录
4. 集成Redis
5. 集成Rabbitmq
6. 邮箱注册与登录
## 生产者 8029  暴露端口20873
## 消费者 8030

swagger文档地址：http://localhost:8030/doc.html

### 数据库文件
```sql
-- ----------------------------
-- Table structure for broker_message_log
-- ----------------------------
DROP TABLE IF EXISTS `broker_message_log`;
CREATE TABLE `broker_message_log` (
  `message_id` varchar(255) NOT NULL COMMENT '消息唯一ID',
  `message` varchar(4000) NOT NULL COMMENT '消息内容',
  `try_count` int(4) DEFAULT '0' COMMENT '重试次数',
  `status` varchar(10) DEFAULT '' COMMENT '消息投递状态 0投递中，1投递成功，2投递失败',
  `next_retry` timestamp NOT NULL  DEFAULT CURRENT_TIMESTAMP  COMMENT '下一次重试时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


