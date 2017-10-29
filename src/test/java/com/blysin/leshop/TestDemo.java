package com.blysin.leshop;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

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
}
