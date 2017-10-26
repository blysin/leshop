package com.blysin.leshop.common.mybatis;


import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Blysin on 2017/4/21.
 */
public class BaseServiceImpl<M extends MyBaseMapper<T>,T> implements BaseService<T> {
    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected M dao;

    @Override
    public int insert(T record) {
        return dao.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return dao.insertSelective(record);
    }


    @Override
    public int updateByPrimaryKeySelective(T record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public int updateByCondition(T record, Object condition) {
        return dao.updateByCondition(record, condition);
    }

    @Override
    public int updateByConditionSelective(T record, Object condition) {
        return dao.updateByConditionSelective(record, condition);
    }

    @Override
    public int delete(T record) {
        return dao.delete(record);
    }

    @Override
    public int deleteByCondition(Object condition) {
        return dao.deleteByCondition(condition);
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        return dao.deleteByPrimaryKey(key);
    }

    @Override
    public List<T> select(T record) {
        return dao.select(record);
    }

    @Override
    public List<T> selectAll() {
        return dao.selectAll();
    }

    @Override
    public List<T> selectByCondition(Object condition) {
        return dao.selectByCondition(condition);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return dao.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public List<T> selectByIds(String ids) {
        return dao.selectByIds(ids);
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return dao.selectByPrimaryKey(key);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return dao.selectByRowBounds(record, rowBounds);
    }

    @Override
    public int selectCount(T record) {
        return dao.selectCount(record);
    }

    @Override
    public int selectCountByCondition(Object condition) {
        return dao.selectCountByCondition(condition);
    }

    @Override
    public T selectOne(T record) {
        return dao.selectOne(record);
    }
}
