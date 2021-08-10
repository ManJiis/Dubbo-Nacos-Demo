# springboot-dubbo-nacos-demo
基于springboot搭建dubbo，使用nacos作为注册中心, shiro_redis做权限.
## 项目结构

sdnd  
├── admin_docs -- 文档,SQL文件等  
├── sdnd-api -- 接口定义层  
├── sdnd-common -- 工具类及通用代码  
├── sdnd-common-mapper -- 数据持久层  
├── sdnd-provide -- 8031 生产者  
├── sdnd-web -- 8032 消费者  
├── sdnd-web-test -- 测试模块  
└── xxl-job-admin -- 8080 XXL-JOB分布式任务调度平台 2.3.0版本  

-------------------------------------------------------------
1. 统一返回对象
2. 全局异常处理
3. AOP日志记录
4. 集成Redis
5. 集成RabbitMQ
- [x] 消息可靠性投递
- [x] 处理消息重复消费
<details>
<summary>消息可靠总结</summary>

1. 持久化
- exchange要持久化
- queue要持久化
- message要持久化
2. 消息确认
- 启动消费返回（@ReturnList注解，生产者就可以知道哪些消息没有发出去）
- 生产者和Server（broker）之间的消息确认
- 消费者和Server（broker）之间的消息确认
</details>

6. 邮件发送
7. 集成分布式任务调度平台XXL-JOB 2.3.0版本
8. 添加分布式锁[Redission](https://github.com/redisson/redisson )(sdnd-provide模块测试类RedissionTest)
9. 添加接口限流功能(@AccessLimit注解)
10. 使用screw导出数据库文档(web-test模块下测试类ScrewApplicationTests)
11. [数据+模板生成PDF](https://github.com/tanglinghan/pdf-demo)
12. - [ ] CompletableFuture异步编程


本地swagger文档地址：http://localhost:8032/doc.html



