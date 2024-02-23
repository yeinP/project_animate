package org.yein.animate.backend.serviceImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.yein.animate.backend.service.JwtService;

import javax.crypto.spec.SecretKeySpec;


import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {
    private String security = "asdfasdgbasbvzxcfsdfwreeagd";

    @Override
    public String getToken(String key, Object value) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 5);

        Key signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap).setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);
        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
        if(token != null && !"".equals(token)){
            try{
                Key signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                Claims claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
                return claims;
            } catch(ExpiredJwtException e) {

            } catch(JwtException e){

            }
        }
        return null;
    }
}
