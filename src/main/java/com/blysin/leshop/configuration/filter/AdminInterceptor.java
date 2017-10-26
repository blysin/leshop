package com.blysin.leshop.configuration.filter;

import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.common.constant.CommonConstants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Blysin
 * @since 2017/10/20
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        Object admin =  request.getSession().getAttribute(CommonConstants.SESSION_ADMIN_LOGIN);
        if(modelAndView!=null){
            if(admin !=null){
                modelAndView.addObject("adminUserName",((Admin)admin).getRealName());
            }else{
                response.sendRedirect("/admin/sa/login");
            }
        }
    }
}
