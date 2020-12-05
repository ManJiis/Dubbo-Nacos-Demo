package cn.tlh.ex.provide.example;

import cn.tlh.ex.service.ProvideService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author Ling
 */
@Service(version = "${service.version}")
@Component
public class ProvideImpl implements ProvideService {

    @Override
    public String sayHello(String word) {
        return word;
    }
}
