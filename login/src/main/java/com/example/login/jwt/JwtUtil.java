package com.example.login.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    //有效期为一个小时
    public static final Long JWT_TTL = 3600000L;
    //设置密钥明文
    public static final String JWY_KEY = "itcast";

    public static String createJWT(String id, String subject, Long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null){
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("admin")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm,secretKey)
                .setExpiration(expDate);
        return builder.compact();
    }

    //生成加密后的密钥secretKey
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWY_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

}
