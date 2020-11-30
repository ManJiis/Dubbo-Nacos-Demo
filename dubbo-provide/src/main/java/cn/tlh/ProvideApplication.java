package cn.tlh;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ling
 */
@SpringBootApplication
@MapperScan("cn.tlh.ex.dao")
@EnableDubbo
public class ProvideApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProvideApplication.class);
    }
}
