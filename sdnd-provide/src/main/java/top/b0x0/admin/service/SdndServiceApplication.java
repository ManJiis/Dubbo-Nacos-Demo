package top.b0x0.admin.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import top.b0x0.admin.common.util.spring.SpringContextHolder;

/**
 * @author TANG
 */
@SpringBootApplication
@MapperScan("top.b0x0.admin.dao")
@EnableAsync
public class SdndServiceApplication {

    public static void main(String[] args) {
        System.out.println("====================== 服务开始启动.... ====================== ");
//        SpringApplication.run(SdndServiceApplication.class, "hello", "world");
        SpringApplication.run(SdndServiceApplication.class, args);
        System.out.println("====================== 服务启动结束 ====================== ");
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
