package top.b0x0.admin.service.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.b0x0.admin.common.util.StringUtils;


/**
 * 分布式锁配置
 *
 * @author musui
 * @date 2021-02-12
 */
@Configuration
public class RedissonConfig {
    private static final Logger log = LoggerFactory.getLogger(RedissonConfig.class);

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
        config.useSingleServer().
                setAddress("redis://" + redisHost + ":" + redisPort);
        log.info("redis password:{}", redisPassword);
        if (StringUtils.isNotBlank(redisPassword)) {
            config.useSingleServer().setPassword(redisPassword);
        }
        //使用json序列化方式
        Codec codec = new JsonJacksonCodec();
        config.setCodec(codec);
//        config.setCodec(new StringCodec());
        return Redisson.create(config);
    }

//    @Bean
//    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
//        return new RedissonConnectionFactory(redisson);
//    }
}


