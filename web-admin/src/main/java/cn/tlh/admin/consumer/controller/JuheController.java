package cn.tlh.admin.consumer.controller;

import cn.tlh.admin.common.base.common.JuheResponse;
import cn.tlh.admin.service.JuheApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author musui
 * @description 调用聚合api
 */
@RestController
@RequestMapping("juhe")
@Api(tags = "聚合数据：第三方api调用")
public class JuheController {
    @Reference(version = "${service.version}", check = false)
    JuheApiService juheApiService;

    @GetMapping("todayInHistory")
    @ApiOperation("历史上的今天")
    public JuheResponse getTodayInHistory() {
        return juheApiService.getTodayInHistory();
    }
}
