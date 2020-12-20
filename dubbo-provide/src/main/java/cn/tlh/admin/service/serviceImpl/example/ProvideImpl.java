package cn.tlh.admin.service.serviceImpl.example;

import cn.tlh.admin.service.ProvideService;
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
