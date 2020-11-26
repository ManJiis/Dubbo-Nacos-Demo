package cn.tlh.ex.service.impl.common;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ling
 * @description:
 * @date: 2020-11-26
 */
@Service
public class RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * set redis: hash类型
     *
     * @param key      key
     * @param filedKey filedkey
     * @param value    value
     */
    public void setHash(String key, String filedKey, String value) {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, filedKey, value);
    }

    /**
     * get redis: hash类型
     *
     * @param key      key
     * @param filedkey filedkey
     * @return
     */
    public String getHash(String key, String filedkey) {
        return (String) stringRedisTemplate.opsForHash().get(key, filedkey);
    }

    /**
     * set redis:list类型
     *
     * @param key   key
     * @param value value
     * @return
     */
    public long setList(String key, String value) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        return listOperations.leftPush(key, value);
    }

    /**
     * get redis:list类型
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return
     */
    public List<String> getList(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }
}
