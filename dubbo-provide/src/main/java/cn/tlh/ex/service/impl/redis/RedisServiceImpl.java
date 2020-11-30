package cn.tlh.ex.service.impl.redis;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import cn.tlh.ex.service.RedisService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Ling
 * @description: redis service
 * @date: 2020-11-26
 */
@SuppressWarnings("unchecked")
@Service(version = "${service.version}")
public class RedisServiceImpl implements RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 判断key是否存在
     */
    @Override
    public boolean exitst(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 以增量的方式将long值存储在变量中，每次设置该key，值都会根据第二个参数增加。
     *
     * @param key key
     * @return String
     */
    @Override
    public String increment(String key) {
        if (StringUtils.isBlank(key) && !key.matches("^[0-9]*$")) {
            return null;
        }
        return String.valueOf(redisTemplate.opsForValue().increment(key, 1L));
    }

    @Override
    public boolean setString(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, value);
            LOGGER.info("存入redis成功，key：{}，value：{}", key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("存入redis失败，key：{}，value：{}", key, value);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setString(String key, Object value, int seconds) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            LOGGER.info("存入redis成功，key：{}，value：{}", key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("存入redis失败，key：{}，value：{}", key, value);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object getString(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * set redis: hash类型
     *
     * @param key      key
     * @param filedKey filedkey
     * @param value    value
     */
    @Override
    public void setHash(String key, String filedKey, String value) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, filedKey, value);
    }

    /**
     * get redis: hash类型
     *
     * @param key      key
     * @param filedkey filedkey
     * @return
     */
    @Override
    public String getHash(String key, String filedkey) {
        return (String) redisTemplate.opsForHash().get(key, filedkey);
    }

    /**
     * set redis:list类型
     *
     * @param key   key
     * @param value value
     * @return
     */
    @Override
    public long setList2LeftPush(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.leftPush(key, value);
    }

    /**
     * set redis:list类型
     *
     * @param key   key
     * @param value value
     * @return
     */
    @Override
    public long setList2RightPush(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.rightPush(key, value);
    }

    /**
     * get redis:list类型
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return
     */
    @Override
    public List<String> getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public void expireMinutes(String key, int minutes) {
        stringRedisTemplate.expire(key, minutes, TimeUnit.MINUTES);
    }

    @Override
    public void expireSeconds(String key, int seconds) {
        stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Boolean setNX(byte[] key, byte[] value) {
        return redisTemplate.getConnectionFactory().getConnection().setNX(key, value);
    }

    /**
     * 验证码校验 限制 ip 时间
     * 0:正常  1:ip不合法  2:超过次数  3:验证码未过期
     *
     * @param phoneNumbeKey 设置手机号次数
     * @param phoneKey      设置验证码过期时间
     * @param ipNumberKey   设置ip次数
     * @param verCode       验证码
     * @return 0:正常  1:ip不合法  2:超过次数  3:验证码未过期
     */
    @Override
    public int verCodeCheck(String phoneNumbeKey, String phoneKey, String ipNumberKey, String verCode) {
        //查询ip是否已记录次数
        String ipKeyNumber = stringRedisTemplate.opsForValue().get(ipNumberKey);
        if (ipKeyNumber != null && Integer.valueOf(ipKeyNumber) >= 5) {
            return 1;
        }
        //校验手机号次数
        String phoneKeyNumber = stringRedisTemplate.opsForValue().get(phoneNumbeKey);
        if (phoneKeyNumber != null && Integer.valueOf(phoneKeyNumber) >= 5) {
            return 2;
        }
        //校验验证码是否过期
        Boolean flag = setNX(phoneKey.getBytes(), verCode.getBytes());
        if (!flag) {
            return 3;
        }
        //设置ip次数 +1 和 过期时间
        increment(ipNumberKey);
        stringRedisTemplate.expire(ipNumberKey, 86400, TimeUnit.SECONDS);
        //设置手机号次数 +1 和 过期时间
        increment(phoneNumbeKey);
        stringRedisTemplate.expire(phoneNumbeKey, 86400, TimeUnit.SECONDS);
        //设置验证码过期时间
        stringRedisTemplate.expire(phoneKey, 300, TimeUnit.SECONDS);
        return 0;
    }


}