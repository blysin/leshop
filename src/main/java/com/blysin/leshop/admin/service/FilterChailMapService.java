package com.blysin.leshop.admin.service;

import com.blysin.leshop.admin.domain.FilterChailMap;
import com.blysin.leshop.common.mybatis.BaseService;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 代码生成器
 * @since 2017-10-29
 */
public interface FilterChailMapService extends BaseService<FilterChailMap> {
	LinkedList<FilterChailMap> findMapping();
}
