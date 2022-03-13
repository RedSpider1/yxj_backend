package com.redspider.pss.security.handler;

import com.redspider.pss.response.ResponseCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 *
 * @author dylan
 */
@Component
public class AuthenticationEntryPointHandler extends AbstractHandler
    implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    failureResponse(response, ResponseCode.ACCESS_ILLEGAL);
  }
}
