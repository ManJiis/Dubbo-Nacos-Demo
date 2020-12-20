package cn.tlh.admin.service.serviceImpl.system;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.tlh.admin.common.exception.customexception.BusinessErrorException;
import cn.tlh.admin.common.pojo.system.EmailConfig;
//import cn.tlh.common.utils.EncryptUtils;
import cn.tlh.admin.common.base.vo.req.EmailVo;
import cn.tlh.admin.dao.EmailConfigDao;
import cn.tlh.admin.service.system.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author TANG
 * @date 2020-12-26
 */
@Service(version = "${service.version}")
@Component
//// @RequiredArgsConstructor
//@CacheConfig(cacheNames = "email")
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    EmailConfigDao emailConfigDao;

    @Override
//    @CachePut(key = "'config'")
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig config(EmailConfig emailConfig, EmailConfig old) throws Exception {
        emailConfig.setId(1L);
        if (!emailConfig.getPass().equals(old.getPass())) {
            // 对称加密
//            emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            emailConfig.setPass(emailConfig.getPass());
        }
        return emailConfigDao.insert(emailConfig);
    }

    @Override
    // @Cacheable(key = "'config'")
    public EmailConfig find() {
        EmailConfig config = emailConfigDao.queryById(1L);
        Optional<EmailConfig> emailConfig = Optional.of(config);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if (emailConfig.getId() == null) {
            throw new BusinessErrorException("请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setUser(emailConfig.getUser());
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
//            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
            account.setPass(emailConfig.getPass());
        } catch (Exception e) {
            throw new BusinessErrorException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        // 使用STARTTLS安全连接
        account.setStarttlsEnable(true);
        String content = emailVo.getContent();
        // 发送
        try {
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new BusinessErrorException(e.getMessage());
        }
    }
}
