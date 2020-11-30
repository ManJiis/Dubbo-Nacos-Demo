package cn.tlh.ex.controller.example;

import cn.tlh.ex.common.exception.BusinessMsgEnum;
import cn.tlh.ex.common.vo.req.SayHelloVO;
import cn.tlh.ex.common.vo.resp.ResultInfo;
import cn.tlh.ex.service.ProvideService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Ling
 */
@RestController
@RequestMapping("/test")
@Api(tags = "Dubbo-消费者示例")
@Validated  // 单个参数校验,需要在类上加次参数
public class ConsumerController {

    @Reference(version = "${service.version}", check = false)
    ProvideService provideService;

    @GetMapping("/hello")
    public ResultInfo sayHello(@Validated SayHelloVO vo) {
        String sayHello = provideService.sayHello("消费者 " + vo.getName() + " 访问了....");
        return new ResultInfo(sayHello);
    }

    @GetMapping("/hello1")
    public ResultInfo sayHello1(@NotNull(message = "name不能为空") String name) {
        String sayHello = provideService.sayHello("消费者 " + name + " 访问了....");
        return new ResultInfo(sayHello);
    }

    @GetMapping("/error")
    public ResultInfo error() {
        return new ResultInfo(BusinessMsgEnum.PARMETER_EXCEPTION);
    }
}
