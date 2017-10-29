package com.blysin.leshop.shiro.dao;

import org.apache.shiro.cache.CacheManagerAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Blysin
 * @since 2017/10/29
 */
@Component
public class SessionDao extends EnterpriseCacheSessionDAO implements CacheManagerAware {

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        //TODO 将session对象序列化到数据库或者缓存中并返回id
        return sessionId;
    }



    @Override
    protected Session doReadSession(Serializable sessionId) {
        //TODO 从数据库或者缓存中读书session数据并反序列化为session对象
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        //TODO 更新数据库或者缓存中的session对象数据
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        //TODO 删除操作
        super.doDelete(session);
    }
}