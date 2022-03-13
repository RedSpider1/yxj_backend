package com.redspider.pss.security.handler;

import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.security.exception.JWTExpiredAuthException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dylan
 */
@Component
public class JWTAuthenticationFailureHandler extends AbstractHandler
        implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        ResponseCode errorCode = ResponseCode.ACCESS_ILLEGAL;
        if (e instanceof JWTExpiredAuthException) {
            errorCode = ResponseCode.ACCESS_EXPIRED_ERROR;
        }
        failureResponse(response, errorCode);
    }
}
