package com.redspider.pss.security.filter;

import com.redspider.pss.converter.UserConverter;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.logic.spi.UserLogic;
import com.redspider.pss.security.PssAuthenticationToken;
import com.redspider.pss.security.TokenHelper;
import com.redspider.pss.security.UserPayload;
import com.redspider.pss.security.handler.JWTAuthenticationFailureHandler;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dylan
 */
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTH_PREFIX = "Bearer";
    private static final String AUTHORIZATION = "Authorization";
    private static final RequestMatcher REQUEST_HEADER_REQUEST_MATCHER =
            new RequestHeaderRequestMatcher(AUTHORIZATION);
    private final JWTAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final UserLogic userLogic;
    // todo 七叔 这里不懂为什么不用自动注入 可以看下。
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(JWTAuthenticationFailureHandler jwtAuthenticationFailureHandler, UserLogic userLogic) {
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.userLogic = userLogic;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!REQUEST_HEADER_REQUEST_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authenticate =
                    authenticationManager.authenticate(buildJWTAuthenticationToken(request));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            log.info("认证失败", e);
            SecurityContextHolder.clearContext();
            jwtAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Authentication buildJWTAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String jwt = token.replace(AUTH_PREFIX, "");
        Claims claims = TokenHelper.resolve(jwt);
        Long userId = claims.get(UserPayload.USER_ID, Long.class);
        UserDTO userInfo = userLogic.getUserInfo(userId);
        UserPayload userPayload = UserConverter.INSTANCE.toPayload(userInfo);
        return new PssAuthenticationToken(jwt, userPayload);
    }

    public JWTAuthenticationFilter setAuthenticationManager(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        return this;
    }
}
