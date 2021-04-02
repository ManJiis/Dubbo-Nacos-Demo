package top.b0x0.admin.consumer.controller.system;

import top.b0x0.admin.common.vo.BusinessResponse;
import top.b0x0.admin.common.vo.req.EmailVo;
import top.b0x0.admin.common.util.enums.CodeBiEnum;
import top.b0x0.admin.common.util.enums.CodeEnum;
import top.b0x0.admin.service.system.EmailService;
import top.b0x0.admin.service.system.VerifyService;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.Objects;

/**
 * @author TANG
 * @date 2020-12-26
 */
@Api(tags = "系统：验证码管理")
@RestController
@RequestMapping("system/code")
@Validated
public class VerifyController {

    @Reference(version = "${service.version}", check = false)
    VerifyService verificationCodeService;
    @Reference(version = "${service.version}", check = false)
    EmailService emailService;

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public BusinessResponse resetEmail(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey());
        emailService.send(emailVo, emailService.find());
        return BusinessResponse.ok();
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public BusinessResponse resetPass(@ApiParam() @RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_PWD_CODE.getKey());
        emailService.send(emailVo, emailService.find());
        return BusinessResponse.ok();
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "邮箱", name = "email"),
            @ApiImplicitParam(value = "验证码", name = "code"),
            @ApiImplicitParam(value = "修改类型(1:旧邮箱修改邮箱 2:通过邮箱修改密码)", name = "codeBi")
    })
    public BusinessResponse validated(@RequestParam @Email String email, @RequestParam String code, @RequestParam Integer codeBi) {
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)) {
            // 旧邮箱修改邮箱
            case ONE:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + email, code);
                break;
            // 通过邮箱修改密码
            case TWO:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_PWD_CODE.getKey() + email, code);
                break;
            default:
                break;
        }
        return BusinessResponse.ok();
    }
}
