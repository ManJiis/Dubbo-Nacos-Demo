package cn.tlh.admin.consumer.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author TANG
 * @description 自定义 sessionId 的生成器
 * @date 2020-12-30
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return "sdns-" + UUID.randomUUID().toString();
    }
}