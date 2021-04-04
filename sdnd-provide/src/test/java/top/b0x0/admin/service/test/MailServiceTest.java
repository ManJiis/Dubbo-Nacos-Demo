package top.b0x0.admin.service.test;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import top.b0x0.admin.common.pojo.system.EmailConfig;
import top.b0x0.admin.dao.EmailConfigDao;
import top.b0x0.admin.service.ServiceProvideApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProvideApplication.class)
public class MailServiceTest {

    @Autowired(required = false)
    EmailConfigDao emailConfigDao;

    @Test
    public void test1() {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("/email/email.ftl");
        String content = template.render(Dict.create().set("code", RandomUtil.randomNumbers(6)));
        System.out.println("content = " + content);

    }

    @Test
    public void test2() {
        EmailConfig emailConfig = emailConfigDao.queryById(1L);
        System.out.println("emailConfig.toString() = " + emailConfig.toString());

    }


}




