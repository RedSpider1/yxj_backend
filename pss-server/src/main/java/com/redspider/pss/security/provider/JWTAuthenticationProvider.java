package com.redspider.pss.security.provider;

import com.redspider.pss.converter.UserConverter;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.logic.spi.UserLogic;
import com.redspider.pss.security.PssAuthenticationToken;
import com.redspider.pss.security.TokenHelper;
import com.redspider.pss.security.UserPayload;
import com.redspider.pss.security.exception.JWTExpiredAuthException;
import com.redspider.pss.security.exception.JWTVerifyFailedAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author dylan
 */
@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

    public static final String AUTH_PREFIX = "Bearer";
    @Autowired
    private UserLogic userLogic;

    @Override
    public boolean supports(Class<?> authentication) {
        return PssAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = getToken(authentication);
        UserPayload payload = buildPayload(token);

        return new PssAuthenticationToken(token, payload);
    }

    private UserPayload buildPayload(String token) {
        Claims claims = getClaims(token);
        Long userId = claims.get(UserPayload.USER_ID, Long.class);
        UserDTO userInfo = userLogic.getUserInfo(userId);
        return UserConverter.INSTANCE.toPayload(userInfo);
    }

    private Claims getClaims(String token) {
        try {
            return TokenHelper.resolve(token);
        } catch (ExpiredJwtException e) {
            throw new JWTExpiredAuthException("Authorization Has Expired");
        } catch (Exception e) {
            throw new JWTVerifyFailedAuthException("Authorization Verify Error");
        }
    }

    private String getToken(Authentication authentication) {
        return ((PssAuthenticationToken) authentication).getCredentials().replace(AUTH_PREFIX, "");
    }
}
