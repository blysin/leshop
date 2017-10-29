package com.blysin.leshop.admin.service.impl;

import com.blysin.leshop.admin.domain.FilterChailMap;
import com.blysin.leshop.admin.dao.FilterChailMapDao;
import com.blysin.leshop.admin.service.FilterChailMapService;
import com.blysin.leshop.common.mybatis.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
@Service
public class FilterChailMapServiceImpl extends BaseServiceImpl<FilterChailMapDao, FilterChailMap> implements FilterChailMapService {
    @Autowired
    private FilterChailMapDao filterChailMapDao;
    @Override
    public LinkedList<FilterChailMap> findMapping() {
        return filterChailMapDao.findMapping();
    }
}
