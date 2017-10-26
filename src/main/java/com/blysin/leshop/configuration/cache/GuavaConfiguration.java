package com.blysin.leshop.configuration.cache;

import com.blysin.leshop.common.cache.guava.GuavaCacheManager;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Blysin
 * @since 2017/8/24
 */
@Configuration
public class GuavaConfiguration {

    @Bean
    public CacheManager getCompositeCacheManager() {
        CompositeCacheManager cacheManager = new CompositeCacheManager();
        List<CacheManager> cacheManagers = new ArrayList<>();
        cacheManagers.add(getGuavaCacheManager());
        cacheManager.setCacheManagers(cacheManagers);
        cacheManager.setFallbackToNoOpCache(true);
        return cacheManager;
    }

    public GuavaCacheManager getGuavaCacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        Map<String, CacheBuilder> configMap = new HashMap<>();
        configMap.put("guavaCache", CacheBuilder.from("maximumSize=500, expireAfterWrite=1h"));
        configMap.put("variedCache", CacheBuilder.from("maximumSize=500, expireAfterWrite=30m"));
        guavaCacheManager.setConfigMap(configMap);
        return guavaCacheManager;
    }
}
