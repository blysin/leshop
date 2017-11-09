package com.blysin.leshop.shop.domain;

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
 * 商品
 * </p>
 *
 * @author 代码生成器
 * @since 2017-11-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 7247714662080613254L;
    //商品ID
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer ProductId;

    //产品名称
    private String ProductName;

    //创建时间
    private Date CreateTime;

    //产品状态（包含 上架:1，下架:2, 删除:-2）
    private Integer ProductStatusCd;

    //子商品SKU的KEY值Json串
    private String SkuKeyJsonStr;

    //主商品的SKU的Json数据
    private String SkuCommonJsonStr;

    //实际销售价
    private BigDecimal DefaultPrice;

    //标签价
    private BigDecimal TagPrice;

    //真实库存
    private Integer RealStock;

    //虚拟销量
    private Integer VirtualSaleCnt;

    //品牌ID
    private Integer BrandId;

    //系统分类ID
    private Integer CategoryId;

    //主产品ID
    private Integer MasterProductId;

    //最近修改时间
    private Date LastUpdateTime;

    //最后一个修改人ID
    private Integer LastUpdateUserId;

    //是否主商品
    private String MasterProductFlag;

}
