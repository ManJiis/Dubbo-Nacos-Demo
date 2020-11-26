package cn.tlh;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ling
 */
@SpringBootApplication
@MapperScan("cn.tlh.ex.dao")
@EnableDubbo
public class DubboProvideApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboProvideApplication.class);
    }
}
