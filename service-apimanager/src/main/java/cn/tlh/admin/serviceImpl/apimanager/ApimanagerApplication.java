package cn.tlh.admin.serviceImpl.apimanager;

import top.b0x0.admin.common.util.spring.SpringContextHolder;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author musui
 */
@SpringBootApplication
@EnableDubbo
public class ApimanagerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApimanagerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApimanagerApplication.class);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
