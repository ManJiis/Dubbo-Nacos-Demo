package cn.tlh;

import cn.tlh.admin.common.util.spring.SpringContextHolder;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author TANG
 */
@SpringBootApplication
@MapperScan("cn.tlh.admin.dao")
@EnableDubbo
@EnableAsync
public class ProvideApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProvideApplication.class);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
