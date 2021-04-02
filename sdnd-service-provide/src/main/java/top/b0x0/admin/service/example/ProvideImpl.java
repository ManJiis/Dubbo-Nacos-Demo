package top.b0x0.admin.service.example;

import top.b0x0.admin.service.ProvideService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author TANG
 */
@Service(version = "${service.version}")
@Component
public class ProvideImpl implements ProvideService {

    @Override
    public String sayHello(String word) {
        return word;
    }
}
