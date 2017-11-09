package com.blysin.leshop;

import com.blysin.leshop.shop.domain.Product;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author Blysin
 * @since 2017/10/29
 */
public class TestDemo {
    @Test
    public void tesetSha1(){
        String name = "SHA-1";
        String userName = "123123";
        ByteSource salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;

        Object result = new SimpleHash(name,userName,salt,hashIterations);
        System.out.println(result);
    }

    @Test
    public void testSwap(){
        System.out.println(RandomStringUtils.random(6,true,false));
        for (int i = 0; i <20 ; i++) {
            System.out.println(Product.builder().ProductId(i).ProductName("商品_"+ RandomStringUtils.random(6,true,false)).DefaultPrice(BigDecimal.valueOf(RandomUtils.nextDouble()*100).setScale(2,BigDecimal.ROUND_HALF_UP)).build());
        }
    }
}
