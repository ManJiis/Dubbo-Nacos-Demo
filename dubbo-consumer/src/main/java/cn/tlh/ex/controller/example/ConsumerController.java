package cn.tlh.ex.controller.example;

import cn.tlh.ex.common.exception.BusinessMsgEnum;
import cn.tlh.ex.common.vo.resp.JsonResult;
import cn.tlh.ex.service.ProvideService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ling
 */
@RestController
@RequestMapping("/test")
public class ConsumerController {

    @Reference(version = "${service.version}",check = false)
    ProvideService provideService;

    @RequestMapping("/hello")
    public String sayHello() {
        return provideService.sayHello("Dubbo消费者访问了....");
    }

    @RequestMapping("/error")
    public JsonResult error() {
        return new JsonResult(BusinessMsgEnum.PARMETER_EXCEPTION);
    }
}
