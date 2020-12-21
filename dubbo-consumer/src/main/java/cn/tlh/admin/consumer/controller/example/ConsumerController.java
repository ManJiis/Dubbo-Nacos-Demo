package cn.tlh.admin.consumer.controller.example;

import cn.tlh.admin.common.util.enums.BusinessMsgEnum;
import cn.tlh.admin.common.base.vo.req.SayHelloVo;
import cn.tlh.admin.common.base.vo.resp.BusinessResponse;
import cn.tlh.admin.service.ProvideService;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author TANG
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试：消费者示例",value = "消费者示例")
@Validated  // 单个参数校验,需要在类上加此参数
public class ConsumerController {

    @Reference(version = "${service.version}", check = false)
    ProvideService provideService;

    @GetMapping("/hello")
    public BusinessResponse sayHello(@Validated SayHelloVo vo) {
        String sayHello = provideService.sayHello("消费者 " + vo.getName() + " 访问了....");
        return BusinessResponse.ok(sayHello);
    }

    @GetMapping("/hello1")
    public BusinessResponse sayHello1(@NotNull(message = "name不能为空") String name) {
        String sayHello = provideService.sayHello("消费者 " + name + " 访问了....");
        return BusinessResponse.ok(sayHello);
    }

    @GetMapping("/error")
    public BusinessResponse error() {
        return BusinessResponse.fail(BusinessMsgEnum.PARMETER_EXCEPTION);
    }
}
