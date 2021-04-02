package top.b0x0.admin.consumer.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 分布式锁配置
 *
 * @author musui
 * @since 2021-02-12
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        //单机模式  依次设置redis地址和密码
//        config.useSingleServer()
//                .setAddress("redis://" + redisHost + ":" + redisPort)
//                .setPassword(redisPassword);
        config.useSingleServer()
                .setAddress("redis://" + "127.0.0.1" + ":" + "6379");
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


