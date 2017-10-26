package com.blysin.leshop.admin.controller;

import com.blysin.leshop.admin.domain.Admin;
import com.blysin.leshop.admin.service.AdminService;
import com.blysin.leshop.common.constant.CommonConstants;
import com.blysin.leshop.common.domain.RestResult;
import com.blysin.leshop.common.util.web.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Blysin
 * @since 2017/10/19
 */
@RestController
@RequestMapping("/admin/sa/api")
public class AdminApiController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object Login(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam String loginName, @RequestParam String password, @RequestParam String code) {
        if (!code.equals(session.getAttribute(CommonConstants.SESSION_KEY_VERIFY_CODE))) {
            return RestResult.newConflictError(response, "验证码错误");
        }
        Admin admin = Admin.builder().userName(loginName).password(password).build();
        admin = adminService.selectOne(admin);
        if (admin == null || admin.getAdminId() == null) {
            return RestResult.newConflictError(response, "用户名或密码错误");
        }
        admin.setLastLoginIp(HttpUtils.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        adminService.updateByPrimaryKeySelective(admin);
        session.setAttribute(CommonConstants.SESSION_ADMIN_LOGIN, admin);
        return "success";
    }
}
