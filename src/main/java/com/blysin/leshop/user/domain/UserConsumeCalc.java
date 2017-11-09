package com.blysin.leshop.user.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 用户消费统计表
 * </p>
 *
 * @author 代码生成器
 * @since 2017-11-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserConsumeCalc {

    //用户id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    //下单次数
    private Integer consumeCnt;

    //本年度下单次数
    private Integer currentYearConsumeCnt;

    //消费总额
    private BigDecimal totalConsumeAmt;

    //本年度消费总额
    private BigDecimal currentYearTotalConsumeAmt;

    //发展会员总数
    private Integer developUserCnt;

    //账户余额
    private BigDecimal userBalance;

    //更新时间
    private Date lastUpdateTime;

}
