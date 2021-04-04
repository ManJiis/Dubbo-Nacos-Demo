package top.b0x0.admin.consumer.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.b0x0.admin.common.pojo.system.SysUser;
import top.b0x0.admin.common.vo.JuheResponse;
import top.b0x0.admin.service.JuheApiService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author musui
 * @description 调用聚合api
 */
@RestController
@RequestMapping("juhe")
@Validated
@Api(tags = "聚合数据：第三方api调用")
public class JuheApiController {

    @Reference(version = "${service.version}", check = false)
    JuheApiService juheApiService;

    @GetMapping("todayOnhistory/queryEvent")
    @ApiOperation("历史上的今天")
    public JuheResponse getTodayInHistory() {
        return juheApiService.getTodayInHistory();
    }

    @GetMapping("mobile/phoneAttribution")
    @ApiOperation("手机号归属地查询")
    public JuheResponse getPhoneAttribution(@NotBlank(message = "手机号不能为空") @RequestParam("phone") String phone) {
        return juheApiService.getPhoneAttribution(phone);
    }

    @GetMapping("xzqh/query")
    @ApiOperation("行政区划查询")
    public JuheResponse getAdministrativeDivisions(String id) {
        return juheApiService.getAdministrativeDivisions(id);
    }

    @GetMapping("simpleWeather/query")
    @ApiOperation("天气查询")
    public JuheResponse getSimpleWeather(String city) {
        return juheApiService.getSimpleWeather(city);
    }

    @GetMapping("export")
    @ApiOperation("hutool Excel导出")
    public void export(HttpServletResponse response) throws UnsupportedEncodingException {
        List<SysUser> list = new ArrayList<>();
        list.add(SysUser.builder().username("zhangsan1").phone("17688888888").enabled(1).build());
        list.add(SysUser.builder().username("zhangsan2").phone("17688888886").enabled(1).build());
        list.add(SysUser.builder().username("zhangsan3").phone("17688888885").enabled(1).build());
        list.add(SysUser.builder().username("zhangsan4").phone("17688888884").enabled(1).build());
        list.add(SysUser.builder().username("zhangsan5").phone("17688888883").enabled(1).build());
        list.add(SysUser.builder().username("zhangsan5").phone("17688888882").enabled(0).build());
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("username", "姓名");
        writer.addHeaderAlias("phone", "手机号");
        writer.addHeaderAlias("enabled", "是否禁用");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(3, "用户信息");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String fileName = "userInfo" + System.currentTimeMillis();
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
        }
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
}
