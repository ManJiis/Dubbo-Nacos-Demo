package top.b0x0.admin.service.commandLineRunner;

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
@Order(1)
public class InitRunner1 implements CommandLineRunner {

    /**
     * @param args 当前项目执行的命令参数
     * @throws Exception /
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("============================================ The InitRunner1 start to initialize ...");
        System.out.println(" InitRunner1  Arrays.asList(args) = " + Arrays.asList(args));
        // do init something
    }
}