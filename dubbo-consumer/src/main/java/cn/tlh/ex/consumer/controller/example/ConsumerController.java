package cn.tlh.ex.consumer.controller.example;

import cn.tlh.ex.common.exception.BusinessMsgEnum;
import cn.tlh.ex.common.vo.req.SayHelloVO;
import cn.tlh.ex.common.vo.resp.Response;
import cn.tlh.ex.service.ProvideService;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author Ling
 */
@RestController
@RequestMapping("/test")
@Api(tags = "Dubbo-消费者示例")
@Validated  // 单个参数校验,需要在类上加此参数
public class ConsumerController {

    @Reference(version = "${service.version}", check = false)
    ProvideService provideService;

    @GetMapping("/hello")
    public Response sayHello(@Validated SayHelloVO vo) {
        String sayHello = provideService.sayHello("消费者 " + vo.getName() + " 访问了....");
        return Response.ok(sayHello);
    }

    @GetMapping("/hello1")
    public Response sayHello1(@NotNull(message = "name不能为空") String name) {
        String sayHello = provideService.sayHello("消费者 " + name + " 访问了....");
        return Response.ok(sayHello);
    }

    @GetMapping("/error")
    public Response error() {
        return Response.fail(BusinessMsgEnum.PARMETER_EXCEPTION);
    }
}
