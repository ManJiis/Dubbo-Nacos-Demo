package top.b0x0.admin.consumer.controller.system;

import top.b0x0.admin.common.vo.R;
import top.b0x0.admin.common.vo.req.LogReqVo;
import top.b0x0.admin.consumer.annotaion.Log;
import top.b0x0.admin.service.module.system.LogService;
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
@RequestMapping("/system/log")
@Api(tags = "系统：日志管理")
public class LogController {

    @Reference(version = "${service.version}", check = false)
    private LogService logService;

    @Log(description = "导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    // @RequiresPermissions("@el.check()")
    public void download(HttpServletResponse response, LogReqVo logReqVo) throws IOException {
        logReqVo.setLogType("INFO");
//        logService.download(logService.selectList(logReq), response);
    }

    @Log(description = "导出错误数据")
    @ApiOperation("导出错误数据")
    @GetMapping(value = "/error/download")
    // @RequiresPermissions("@el.check()")
    public void downloadErrorLog(HttpServletResponse response, LogReqVo logReqVo) throws IOException {
        logReqVo.setLogType("ERROR");
        logService.download(logService.selectList(logReqVo), response);
    }

    @GetMapping("/get")
    @ApiOperation("日志查询")
    // @RequiresPermissions("@el.check()")
    public R query(LogReqVo logReqVo) {
        logReqVo.setLogType("INFO");
        return R.ok(logService.selectList(logReqVo));
    }

    @GetMapping(value = "/getUserLog")
    @ApiOperation("用户日志查询")
    public R queryUserLog(LogReqVo logReqVo) {
        logReqVo.setLogType("INFO");
//        String currentUsername = SecurityUtils.getCurrentUsername();
        logReqVo.setUsername("currentUsername");
        return R.ok(logService.queryAllByUser(logReqVo));
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    // @RequiresPermissions("@el.check()")
    public R queryErrorLogs(@PathVariable Long id) {
        return R.ok(logService.findByErrDetail(id));
    }

    @DeleteMapping(value = "/del/error")
    @Log(description = "删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    // @RequiresPermissions("@el.check()")
    public R delAllErrorLog() {
        logService.delAllByError();
        return R.ok();
    }

    @DeleteMapping(value = "/del/info")
    @Log(description = "删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    // @RequiresPermissions("@el.check()")
    public R delAllInfoLog() {
        logService.delAllByInfo();
        return R.ok();
    }
}
