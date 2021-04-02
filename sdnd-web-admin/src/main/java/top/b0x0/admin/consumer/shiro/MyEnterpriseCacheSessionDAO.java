package top.b0x0.admin.consumer.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * shiro session 管理
 *
 * @author TANG
 * @date 2021-01-04
 */
public class MyEnterpriseCacheSessionDAO extends EnterpriseCacheSessionDAO {
    public MyEnterpriseCacheSessionDAO() {
        super();
    }

    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }
}
