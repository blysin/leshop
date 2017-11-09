package com.blysin.leshop.user.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 会员基础表
 * </p>
 *
 * @author 代码生成器
 * @since 2017-11-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 7247714666012613254L;

    //会员ID
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    //登录账号
    private String loginName;

    //昵称
    private String nickName;

    //会员姓名
    private String userName;

    //登录密码
    private String password;

    //手机号码
    private String phone;

    //性别 0:男 1:女 2:不详
    private Integer sexCd;

    //用户头像
    private String headPortraitUrl;

    //生日
    private Date birthday;

    //身份证号
    private String identityId;

    //email
    private String emailAddress;

    //省
    private Long provinceId;

    //市
    private Long cityId;

    //区
    private Long countyId;

    //镇ID
    private Long townId;

    //村庄ID
    private Long villageId;

    //详细地址
    private String street;

    //用户状态(启用、禁用)
    private Integer statusCd;

    //注册平台
    private Integer registerPlatformCd;

    //上级会员Id
    private Integer parentUserId;

    //支付密码
    private String payPassword;

    //注册时间
    private Date registerTime;

    //会员等级id
    private Integer userLevelId;

    //会员二维码参数
    private String qrCodeParam;

    //最后一次登录时间
    private Date lastLoginTime;

    //最后一次登录的IP
    private String lastLoginIp;

    //用户最后一次信息修改时间
    private Date lastUpdateTime;

    //微信openId
    private String openid;

    //创建时间
    private Date createTime;

}
