package top.b0x0.admin.service.InitRunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 初始化
 * 可以接收启动参数
 * eg: --developer=TANG --email=123@163.com --blog=b0x0.top
 * <pre>
 *     --getOptionNames()方法可以得到 [developer,email,blog] 这样的key的集合。
 *     --getOptionValues(String name)方法可以得到 [TANG] 这样的集合的value。
 * </pre>
 *
 * @author musui
 * @date 2021/04/17
 */
@Component
@Order(3)
public class ApplicationRunner1 implements ApplicationRunner {

    /**
     * 当前项目执行的命令参数
     *
     * @param args eg: --developer=TANG --email=123@163.com --blog=b0x0.top
     */
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("\n==== The ApplicationRunner1 start to initialize ...");
        System.out.println(Arrays.asList(args.getSourceArgs()));
        System.out.println("===getOptionNames========\n " + args.getOptionNames());
        System.out.println(" developer = " + args.getOptionValues("developer"));
        System.out.println(" email = " + args.getOptionValues("email"));
        System.out.println(" blog = " + args.getOptionValues("blog"));
    }
}