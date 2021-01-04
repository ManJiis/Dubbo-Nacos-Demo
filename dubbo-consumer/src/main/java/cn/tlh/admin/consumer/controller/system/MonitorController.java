package cn.tlh.admin.consumer.controller.system;

import cn.tlh.admin.common.base.vo.BusinessResponse;
import cn.tlh.admin.service.system.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author TANG
 * @date 2020-05-02
 */
@RestController
@Api(tags = "系统-服务监控管理")
@RequestMapping("/system/monitor")
public class MonitorController {

    @Reference(version = "${service.version}", check = false)
    MonitorService serverService;

    @GetMapping("getSystemInfo")
    @ApiOperation("查询服务监控")
    // @RequiresPermissions("@el.check('monitor:list')")
    public BusinessResponse query() {
        return BusinessResponse.ok(serverService.getServers());
    }
}
