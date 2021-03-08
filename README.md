# springboot-dubbo-nacos-demo
基于springboot搭建dubbo，使用nacos作为注册中心
## 项目结构
```
sdnd
├── admin_docs -- 文档,SQL文件等
├── sdnd-common -- 工具类及通用代码
├── sdnd-common-mapper -- 数据持久层
├── sdnd-service-api -- 接口定义层(Service)
├── sdnd-service-apimanager -- 8030 第三方接口服务(ServiceImpl) 
├── sdnd-service-provide -- 8031 服务提供者(ServiceImpl) 
├── sdnd-web-admin -- 8090 消费者(Controller) 
├── sdnd-web-test -- 测试模块
└── xxl-job-admin -- 8080 XXL-JOB分布式任务调度平台 2.3.0版本 
```

1. 统一返回对象
2. 全局异常处理
3. aop日志记录
4. 集成Redis
5. 集成Rabbitmq
- 消息可靠性投递(待完善)
- 消息重复消费(待完善)
6. 邮件发送
7. 使用logback记录日志,控制台彩色打印
8. 集成分布式任务调度平台XXL-JOB 2.3.0版本
9. 添加分布式锁[Redission](https://github.com/redisson/redisson )(service-provide模块测试类RedissionTest)
10. 添加接口限流功能(@AccessLimit注解)
11. 使用screw导出数据库文档(web-test模块下测试类ScrewApplicationTests)
12. [数据+模板生成PDF](https://github.com/tanglinghan/pdf-demo)
13. CompletableFuture异步编程(待添加)


本地swagger文档地址：http://localhost:8090/doc.html



