package top.b0x0.admin.service.InitRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 初始化
 *
 * @author musui
 */
@Component
@Order(2)
public class ComLineRunner2 implements CommandLineRunner {

    /**
     * @param args 当前项目执行的命令参数 eg:
     */
    @Override
    public void run(String... args) {
        // do init something
        System.out.println("\n==== The ComLineRunner2 start to initialize ...");
        System.out.println(" ComLineRunner2  args = " + Arrays.asList(args));
    }
}