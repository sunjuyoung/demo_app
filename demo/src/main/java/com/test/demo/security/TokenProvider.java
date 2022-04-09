package com.test.demo.security;

import com.test.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY="ergoij4350R";

    public String create(UserEntity userEntity){
        //기한 1일
        Date expireDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));


        return Jwts.builder()
                //Header에 들어갈 내용 및 서명을 하기위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                //payload
                .setSubject(userEntity.getId()) //sub
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date()) //iat
                .setExpiration(expireDate) //exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token) //Base64로 디코딩 및 파싱
                .getBody();

        //사용자 아이디 리턴
        return claims.getSubject();
    }
}
