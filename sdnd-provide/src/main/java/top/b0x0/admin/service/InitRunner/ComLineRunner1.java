package top.b0x0.admin.service.InitRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 初始化
 * 可以接收启动参数
 * eg: aa,bb,cc
 *
 * @author musui
 */
@Component
@Order(1)
public class ComLineRunner1 implements CommandLineRunner {

    /**
     * @param args 当前项目执行的命令参数
     */
    @Override
    public void run(String... args) {
        System.out.println("==== The ComLineRunner1 start to initialize ...");
        System.out.println(" ComLineRunner1  args = " + Arrays.asList(args));
        // do init something
    }
}