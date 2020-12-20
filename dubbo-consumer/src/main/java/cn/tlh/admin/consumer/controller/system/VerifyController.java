package cn.tlh.admin.consumer.controller.system;

import cn.tlh.admin.common.util.enums.CodeBiEnum;
import cn.tlh.admin.common.util.enums.CodeEnum;
import cn.tlh.admin.common.base.vo.req.EmailVo;
import cn.tlh.admin.common.base.vo.resp.Response;
import cn.tlh.admin.service.system.EmailService;
import cn.tlh.admin.service.system.VerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author TANG
 * @date 2020-12-26
 */
@RestController
//// @RequiredArgsConstructor
@RequestMapping("/api/code")
@Api(tags = "系统：验证码管理")
public class VerifyController {

    //    private final VerifyService verificationCodeService;
//    private final EmailService emailService;
    @Reference(version = "${service.version}", check = false)
    VerifyService verificationCodeService;
    @Reference(version = "${service.version}", check = false)
    EmailService emailService;

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public Response resetEmail(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey());
        emailService.send(emailVo, emailService.find());
        return Response.ok();
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public Response resetPass(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_PWD_CODE.getKey());
        emailService.send(emailVo, emailService.find());
        return Response.ok();
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public Response validated(@RequestParam String email, @RequestParam String code, @RequestParam Integer codeBi) {
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)) {
            case ONE:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + email, code);
                break;
            case TWO:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_PWD_CODE.getKey() + email, code);
                break;
            default:
                break;
        }
        return Response.ok();
    }
}
