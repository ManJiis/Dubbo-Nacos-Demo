package top.b0x0.admin.consumer.controller.example;

import top.b0x0.admin.common.vo.R;
import top.b0x0.admin.common.vo.req.SayHelloVo;
import top.b0x0.admin.common.util.enums.BusinessMsgEnum;
import top.b0x0.admin.service.ProvideService;
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
    public R sayHello(@Validated SayHelloVo vo) {
        String sayHello = provideService.sayHello("消费者 " + vo.getName() + " 访问了....");
        return R.ok(sayHello);
    }

    @GetMapping("/hello1")
    public R sayHello1(@NotNull(message = "name不能为空") String name) {
        String sayHello = provideService.sayHello("消费者 " + name + " 访问了....");
        return R.ok(sayHello);
    }

    @GetMapping("/error")
    public R error() {
        return R.fail(BusinessMsgEnum.PARMETER_EXCEPTION);
    }
}
