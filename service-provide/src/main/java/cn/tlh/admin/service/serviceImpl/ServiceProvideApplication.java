package cn.tlh.admin.service.serviceImpl;

import cn.tlh.admin.common.util.spring.SpringContextHolder;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * @author TANG
 */
@SpringBootApplication
@MapperScan("cn.tlh.admin.dao")
@EnableDubbo
@EnableAsync
public class ServiceProvideApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProvideApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServiceProvideApplication.class);
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
