package com.blysin.leshop.configuration.web;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.blysin.leshop.configuration.filter.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 用于SpringMVC的一些配置
 *
 * @author Blysin
 * @since 2017/10/19
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");//添加一个拦截器，并配置拦截uri
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/sa/**").excludePathPatterns("/admin/sa/login", "/admin/sa/api/**");
        super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*配置SpringMVC使用fastjson*/
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConverter);
        super.configureMessageConverters(converters);
    }
}
