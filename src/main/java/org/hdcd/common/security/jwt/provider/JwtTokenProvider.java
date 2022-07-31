package org.hdcd.common.security.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hdcd.common.security.domain.CustomUser;
import org.hdcd.common.security.jwt.constants.SecurityConstants;
import org.hdcd.domain.Member;
import org.hdcd.prop.ShopProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider<E> {

    private final ShopProperties shopProperties;

    public long getUserNo (String header) throws Exception {
        String token = header.substring(7);

        byte[] signingKey = getStringKey();

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token);

        String subject = parsedToken.getBody().getSubject();

        long userNo = Long.parseLong(subject);
        return userNo;
    }

    public String createToken(long userNo, String userId, List<String> roles) {
        byte[] signingKey = getStringKey();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("uno", + userNo)
                .claim("uid", userId)
                .claim("rol", roles)
                .compact();
        return token;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        if (isNotEmpty(tokenHeader)){
            try {
                byte[] signingKey = getStringKey();

                JwtParser jwtParser = Jwts.parser().setSigningKey(signingKey);
                Jws<Claims> parseToken = jwtParser.parseClaimsJws(tokenHeader.replace("Bearer", ""));

                Claims claims = parseToken.getBody();

                String userNo = (String)claims.get("uno");
                String userId = (String)claims.get("uid");

                List<?> list = ((List<?>)claims.get("rol"));
                Stream<E> stream = (Stream<E>) list.stream();
                List<SimpleGrantedAuthority> authorities = stream.map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                if(isNotEmpty(userId)){
                    Member member = new Member();
                    member.setUserNo(Long.parseLong(userNo));
                    member.setUserId(userId);
                    member.setUserPw("");

                    UserDetails userDetails = new CustomUser(member, authorities);

                    return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                }
            } catch(ExpiredJwtException exception){
                log.warn("Request to parse expired JWT : {} failed : {}", tokenHeader, exception.getMessage());
            } catch(UnsupportedJwtException exception){
                log.warn("Request to parse unsupported JWT : {} failed : {}", tokenHeader, exception.getMessage());
            } catch(MalformedJwtException exception){
                log.warn("Request to parse invalid JWT : {} failed : {}", tokenHeader, exception.getMessage());
            } catch(IllegalArgumentException exception){
                log.warn("Request to parse empty or null JWT : {} failed : {}", tokenHeader, exception.getMessage());
            }
        }
        return null;
    }

    private byte[] getStringKey() {
        byte [] signingKey = shopProperties.getSecretKey().getBytes();
        return signingKey;
    }

    private boolean isNotEmpty(final CharSequence cs){
        return !isEmpty(cs);
    }

    private boolean isEmpty(final CharSequence cs) {
        return (cs == null) || (cs.length() == 0);
    }

    public boolean validateToken(String jwtToken){
        try {
            String signingKey = shopProperties.getSecretKey();
            JwtParser jwtParser = Jwts.parser().setSigningKey(signingKey);
            Jws<Claims> claims = jwtParser.parseClaimsJws(jwtToken);
            Date expiration = claims.getBody().getExpiration();
            boolean result = expiration.before(new Date());
            return result;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
        } catch (JwtException exception) {
            log.error("Token Tampered");
        } catch (NullPointerException exception) {
            log.error("Token is null");
        } catch (Exception exception){
            log.error(exception.getMessage());
        }
        return false;
    }
}
