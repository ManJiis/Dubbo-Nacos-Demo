package cn.tlh.admin.consumer.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 自定义 sessionId 的生成器
 *
 * @author TANG
 * @date 2020-12-30
 */
public class MySessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return "sdns-" + UUID.randomUUID().toString();
    }
}