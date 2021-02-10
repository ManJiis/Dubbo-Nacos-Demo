package cn.tlh.admin.service.serviceImpl.system;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.tlh.admin.common.base.vo.req.EmailVo;
import cn.tlh.admin.common.exception.customexception.BusinessErrorException;
import cn.tlh.admin.common.util.redis.RedisTemplateUtil;
import cn.tlh.admin.service.system.VerifyService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @author TANG
 * @date 2020-12-26
 */
@Service(version = "${service.version}")
@Component
// @RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    //    @Value("${code.expiration}")
    private Integer expiration = 300;

    @Autowired(required = false)
    RedisTemplateUtil redisTemplateUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailVo sendEmail(String email, String key) {
        EmailVo emailVo;
        String content;
        String redisKey = key + email;
        // 如果不存在有效的验证码，就创建一个新的
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("/email/email.ftl");
        Object oldCode = redisTemplateUtil.get(redisKey);
        if (oldCode == null) {
            String code = RandomUtil.randomNumbers(6);
            // 存入缓存
            if (!redisTemplateUtil.setValueSec(redisKey, code, expiration)) {
                throw new BusinessErrorException("服务异常，请联系网站负责人");
            }
            content = template.render(Dict.create().set("code", code));
            emailVo = new EmailVo(Collections.singletonList(email), "ADMIN后台管理系统", content);
            // 存在就再次发送原来的验证码
        } else {
            content = template.render(Dict.create().set("code", oldCode));
            emailVo = new EmailVo(Collections.singletonList(email), "ADMIN后台管理系统", content);
        }
        return emailVo;
    }

    @Override
    public void validated(String key, String code) {
        Object value = redisTemplateUtil.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new BusinessErrorException("无效验证码");
        } else {
            redisTemplateUtil.delete(key);
        }
    }
}
