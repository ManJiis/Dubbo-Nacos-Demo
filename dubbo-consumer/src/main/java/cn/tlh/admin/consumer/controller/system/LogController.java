package cn.tlh.admin.consumer.controller.system;

import cn.tlh.admin.common.base.dto.LogReq;
import cn.tlh.admin.common.base.vo.resp.Response;
import cn.tlh.admin.service.aop.annotaion.Log;
import cn.tlh.admin.service.system.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author TANG
 * @date 2020-12-18
 */
@RestController
@RequestMapping("/api/log")
@Api(tags = "系统：日志管理")
public class LogController {

    @Reference(version = "${service.version}", check = false)
    private LogService logService;

    @Log(description = "导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    // @PreAuthorize("@el.check()")
    public void download(HttpServletResponse response, LogReq logReq) throws IOException {
        logReq.setLogType("INFO");
//        logService.download(logService.selectList(logReq), response);
    }

    @Log(description = "导出错误数据")
    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    // @PreAuthorize("@el.check()")
    public void downloadErrorLog(HttpServletResponse response, LogReq logReq) throws IOException {
        logReq.setLogType("ERROR");
        logService.download(logService.selectList(logReq), response);
    }

    @GetMapping
    @ApiOperation("日志查询")
    // @PreAuthorize("@el.check()")
    public Response query(LogReq logReq) {
        logReq.setLogType("INFO");
        return Response.ok(logService.selectList(logReq));
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public Response queryUserLog(LogReq logReq) {
        logReq.setLogType("INFO");
//        String currentUsername = SecurityUtils.getCurrentUsername();
        logReq.setUsername("currentUsername");
        return Response.ok(logService.queryAllByUser(logReq));
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    // @PreAuthorize("@el.check()")
    public Response queryErrorLogs(@PathVariable Long id) {
        return Response.ok(logService.findByErrDetail(id));
    }

    @DeleteMapping(value = "/del/error")
    @Log(description = "删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    // @PreAuthorize("@el.check()")
    public Response delAllErrorLog() {
        logService.delAllByError();
        return Response.ok();
    }

    @DeleteMapping(value = "/del/info")
    @Log(description = "删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    // @PreAuthorize("@el.check()")
    public Response delAllInfoLog() {
        logService.delAllByInfo();
        return Response.ok();
    }
}
