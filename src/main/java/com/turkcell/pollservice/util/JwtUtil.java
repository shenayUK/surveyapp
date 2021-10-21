package com.turkcell.pollservice.util;


import com.turkcell.pollservice.auth.UserPrincipal;
import com.turkcell.pollservice.model.PollUser;
import com.turkcell.pollservice.model.response.TokenResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "lifebox";
    public static final String CLAIM_DELIMETER = "##";
    private static final long   EXPIRED_TIME =  5 * 60 * 60 * 1000;

    public static String getJwtFromRequest(HttpServletRequest request) {
        Optional<String> header = Optional.ofNullable(( request).getHeader("Authorization"));
        return header.isPresent() ? header
                .filter(auth -> auth.startsWith("Bearer "))
                .map(auth -> auth.substring(7))
                .orElse(null) : null ;
    }

    public static UserPrincipal getUserPrincipalFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return new UserPrincipal(claims);
    }
    public String generateToken(PollUser user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRED_TIME);

        Map<String,Object> claims = new LinkedHashMap<>();
        claims.put("id", user.getId()+"");
        claims.put("username", user.getUsername());
        claims.put("name", user.getName());
        if(!Objects.isNull(user.getAuthorities())){
            claims.put("authorities",String.join(CLAIM_DELIMETER,user.getAuthorities()));
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public TokenResponse validateTokenWithAuth(String authToken) {
        TokenResponse builder = TokenResponse.builder().success(true).build();
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
        } catch (Throwable ex) {
            builder.setSuccess(false);
            log.error(ex.getMessage());
        }
        return builder;
    }

    public static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }


}