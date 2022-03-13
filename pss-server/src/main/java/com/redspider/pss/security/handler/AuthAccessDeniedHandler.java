package com.redspider.pss.security.handler;

import com.redspider.pss.response.ResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决认证过的用户访问无权限资源时的异常
 *
 * @author dylan
 */
@Component
public class AuthAccessDeniedHandler extends AbstractHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        failureResponse(response, ResponseCode.ACCESS_ROLE_ILLEGAL);
    }
}
