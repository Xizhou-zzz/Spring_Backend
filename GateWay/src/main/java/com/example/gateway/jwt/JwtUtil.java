package com.example.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class JwtUtil {
    //有效期为1个小时
    public static final Long JWT_TTL = 3600000L;
    //设置密钥明文
    public static final String JWT_KEY = "itcast";
    //生成加密后的密钥 secretKey
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
        return key;
    }
    //解析
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
