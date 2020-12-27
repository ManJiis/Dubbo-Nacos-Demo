package cn.tlh.admin.consumer.controller.system;

import cn.hutool.core.util.IdUtil;
import cn.tlh.admin.common.base.vo.BusinessResponse;
import cn.tlh.admin.common.base.vo.req.AuthUserVo;
import cn.tlh.admin.common.exception.customexception.BusinessErrorException;
import cn.tlh.admin.common.util.RedisCacheKey;
import cn.tlh.admin.common.util.RedisTemplateUtil;
import cn.tlh.admin.service.aop.annotaion.rest.AnonymousGetMapping;
import cn.tlh.admin.service.aop.annotaion.rest.AnonymousPostMapping;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登录
 *
 * @author TANG
 * @date 2020-12-21
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统：系统授权接口")
public class LoginController {
//    private final OnlineUserService onlineUserService;

    @Value("${loginCode.expiration}")
    private Integer expiration;

    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    private static Pattern PHONE_REX = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    /**
     * 只允许英文字母、数字、下划线、英文句号、以及中划线组成
     */
    private static Pattern EMAIL_REX = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    private String getVerifyKey(String codeType, String uuid) {
        return String.format(codeType, uuid);
    }

    @ApiOperation("登录")
    @AnonymousPostMapping(value = "/login")
    public BusinessResponse login(@Validated @RequestBody AuthUserVo userVo, HttpServletRequest request) {
        // 前端传过来公钥加密的密码 这里进行解密
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, userVo.getPassword());
        @NotBlank(message = "登录账号不能为空") String loginAccount = userVo.getLoginAccount();
        Matcher phoneMatcher = PHONE_REX.matcher(loginAccount);
        Matcher emailMatcher = EMAIL_REX.matcher(loginAccount);
        // TODO
        if (phoneMatcher.matches()) {
            System.out.println("登录方式: 手机号 " + loginAccount);
        } else if (emailMatcher.matches()) {
            System.out.println("登录方式: 邮箱 " + loginAccount);
        }
        // 验证码校验
        String code = (String) redisTemplateUtil.get(this.getVerifyKey(RedisCacheKey.CALCULATE_VERIFICATION_CODE, userVo.getUuid()));
        // 清除验证码
        redisTemplateUtil.delete(userVo.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BusinessErrorException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(userVo.getCode()) || !userVo.getCode().equalsIgnoreCase(code)) {
            throw new BusinessErrorException("验证码错误");
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginAccount, userVo.getPassword());
        // 由shiro校验
        subject.login(usernamePasswordToken);
        return BusinessResponse.ok("login success");
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public BusinessResponse logout() {
        SecurityUtils.getSubject().logout();
        return BusinessResponse.ok("loginout success");
    }
/*
    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }
*/

    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public BusinessResponse getCode(String uuid) {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        System.out.println("calculate result = " + result);
        if (StringUtils.isEmpty(uuid)) {
            uuid = IdUtil.simpleUUID();
        }
        String key = this.getVerifyKey(RedisCacheKey.CALCULATE_VERIFICATION_CODE, uuid);
        // 保存
        redisTemplateUtil.set2Minutes(key, result, expiration);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2);
        imgResult.put("img", captcha.toBase64());
        imgResult.put("uuid", uuid);
        return BusinessResponse.ok(imgResult);
    }

/*    @ApiOperation("退出登录")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

}
