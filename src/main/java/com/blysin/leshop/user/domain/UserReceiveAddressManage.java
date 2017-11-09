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
 * 用户收货地址管理
 * </p>
 *
 * @author 代码生成器
 * @since 2017-11-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReceiveAddressManage {

    //自增id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer addrId;

    //用户id
    private Integer userId;

    //收货人姓名
    private String receiverName;
    private String receiverTel;

    //省
    private Long receiverProvinceId;

    //市
    private Long receiverCityId;

    //区
    private Long receiverCountyId;

    //镇区ID
    private Long receiveTownId;

    //村ID
    private Long receiveVillageId;

    //街道地址
    private String receiverAddr;

    //创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;

    //是否默认收货地址
    private Integer isDefaultAddr;

}
