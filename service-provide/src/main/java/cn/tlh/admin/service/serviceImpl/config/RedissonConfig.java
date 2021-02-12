package cn.tlh.admin.service.serviceImpl.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author musui
 * @description 分布式锁配置
 * @date 2021-02-12
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        //单机模式  依次设置redis地址和密码
        config.useSingleServer().
                setAddress("redis://localhost:6379");
        //使用json序列化方式
        Codec codec = new JsonJacksonCodec();
        config.setCodec(codec);
//        config.setCodec(new StringCodec());
        return Redisson.create(config);
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }
}


