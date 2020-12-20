package cn.tlh.admin.consumer.controller.system;

import cn.tlh.admin.common.base.vo.resp.Response;
import cn.tlh.admin.service.system.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TANG
 * @date 2020-05-02
 */
@RestController
//// @RequiredArgsConstructor
@Api(tags = "系统-服务监控管理")
@RequestMapping("/api/monitor")
public class MonitorController {

    //    private final MonitorService serverService;
    @Reference(version = "${service.version}", check = false)
    MonitorService serverService;

    @GetMapping
    @ApiOperation("查询服务监控")
    // // @PreAuthorize("@el.check('monitor:list')")
    public Response query() {
        return Response.ok(serverService.getServers());
    }
}
