package cn.tlh;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableDubbo
@SpringBootApplication
public class ConsumerApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }



}
