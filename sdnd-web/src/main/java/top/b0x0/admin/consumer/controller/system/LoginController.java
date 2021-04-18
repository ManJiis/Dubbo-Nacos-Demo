package top.b0x0.admin.consumer.controller.system;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.b0x0.admin.common.exception.BusinessErrorException;
import top.b0x0.admin.common.pojo.system.SysUser;
import top.b0x0.admin.common.util.RsaUtils;
import top.b0x0.admin.common.util.constants.CommonConstants;
import top.b0x0.admin.common.util.jwt.JwtUtil;
import top.b0x0.admin.common.util.jwt.Token;
import top.b0x0.admin.common.util.constants.RedisCacheKey;
import top.b0x0.admin.common.vo.R;
import top.b0x0.admin.common.vo.req.AuthUserReq;
import top.b0x0.admin.consumer.annotaion.rest.AnonymousGetMapping;
import top.b0x0.admin.consumer.shiro.ShiroUtils;
import top.b0x0.admin.consumer.util.RedisUtils;
import top.b0x0.admin.service.module.system.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * 登录
 *
 * @author TANG
 * @date 2020-12-21
 */
@Slf4j
@RestController
@RequestMapping("system/auth")
@Api(tags = "系统：系统授权接口")
public class LoginController {
//    private final OnlineUserService onlineUserService;

    @Value("${loginCode.expiration}")
    private Integer expiration;

    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${rsa.public_key}")
    private String publicKey;

    @Reference(version = "${service.version}", check = false)
    UserService userService;
    @Autowired(required = false)
    RedisUtils redisUtils;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public R login(@Validated @RequestBody AuthUserReq userVo, HttpServletRequest request) throws Exception {
        // 前端传过来公钥加密的密码 这里进行解密
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, userVo.getPassword());
        String password = RsaUtils.decryptByPrivateKey(privateKey, userVo.getPassword());
        log.info("login account::{}", userVo.toString());
        String loginAccount = userVo.getUsername();
        Matcher phoneMatcher = CommonConstants.REX_PHONE.matcher(loginAccount);
        Matcher emailMatcher = CommonConstants.REX_EMAIL.matcher(loginAccount);

        if (phoneMatcher.matches()) {
            System.out.println("登录方式: 手机号 " + loginAccount);
        } else if (emailMatcher.matches()) {
            System.out.println("登录方式: 邮箱 " + loginAccount);
        } else {
            System.out.println("登录方式: 用户名 " + loginAccount);
        }
        // 验证码校验
        String code = (String) redisUtils.get(this.getVerifyKey(RedisCacheKey.CALCULATE_VERIFICATION_CODE, userVo.getUuid()));
        // 清除验证码
        redisUtils.del(userVo.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BusinessErrorException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(userVo.getCode()) || !userVo.getCode().equalsIgnoreCase(code)) {
            throw new BusinessErrorException("验证码错误");
        }
        // 获取shiro Subject对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginAccount, password);
        // 由shiro校验
        subject.login(usernamePasswordToken);
        final SysUser byName = userService.findByName(loginAccount);
        Token token = new Token();
        BeanUtils.copyProperties(byName, token);
        String signature = JwtUtil.sign(token, byName.getSalt());
        Map<String, Object> returnMap = new HashMap<String, Object>(2) {{
            put("token", signature);
            put("user", byName);
        }};
        System.out.println("subject.getSessionId = " + subject.getSession().getId());
        return R.ok(returnMap);
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public R logout() {
        SecurityUtils.getSubject().logout();
        return R.ok("loginout success");
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping(value = "/currentUserInfo")
    public R getUserInfo() {
        return R.ok(ShiroUtils.getUserEntity());
    }

    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public R getCode(String uuid) {
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
        redisUtils.set(key, result, expiration, TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2);
        imgResult.put("img", captcha.toBase64());
        imgResult.put("uuid", uuid);
        return R.ok(imgResult);
    }

    private String getVerifyKey(String codeType, String uuid) {
        return String.format(codeType, uuid);
    }


}
