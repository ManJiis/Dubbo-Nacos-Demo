package cn.tlh.admin.service.serviceImpl.init;

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
public class InitRunner2 implements CommandLineRunner {

    /**
     * @param args 当前项目执行的命令参数
     * @throws Exception /
     */
    @Override
    public void run(String... args) throws Exception {
        // do init something
        System.out.println("============================================ The InitRunner2 start to initialize ...");
        System.out.println(" InitRunner2  Arrays.asList(args) = " + Arrays.asList(args));
    }
}