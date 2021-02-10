package cn.tlh.admin.service.test;

import cn.tlh.admin.common.base.common.JuheResponse;
import cn.tlh.admin.service.JuheApiService;
import cn.tlh.admin.service.serviceImpl.ProvideApplication;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProvideApplication.class)
public class JuheApiServiceImplTest {

    @Reference(version = "${service.version}", check = false)
    JuheApiService juheApiService;

    @Test
    public void test1() {
        // date=1%2F1&key=2526ec383d550d7d2c6807286137f0a6
        JuheResponse todayInHistory = (JuheResponse) juheApiService.getTodayInHistory();
        System.out.println("todayInHistory = " + todayInHistory);

    }
}
