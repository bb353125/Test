package com.keeko.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * Token 相关
 *
 * @author chensq
 */
@Repository
public class JwtUtils {

    private String id = "jwt";

    private String secret = "6686df7fc3a34e26a61c034d5ec82488";

    public JwtUtils() {
    }

    /**
     * 存活时间（毫秒）
     */
    private long ttlMillis = 86400000;

    public JwtUtils(String id, String secret, long ttlMillis) {
        this.id = id;
        this.secret = secret;
        this.ttlMillis = ttlMillis;
    }

    /**
     * 生成加密key
     */
    private SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 创建jwt
     */
    public String createJWT(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm,
                key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     */
    private Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    /**
     * 从jwt中获取subject信息
     */
    public String getSubjectFromJwt(String jwt, String key) {
        Claims claims = parseJWT(jwt);
        String subject = claims.getSubject();
        if (key != null && !"".equals(key.trim())) {
            JSONObject json = JSONObject.parseObject(subject);
            return json.getString(key);
        } else {
            return subject;
        }
    }

}
