package com.redspider.pss.security.handler;

import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.security.exception.ParamAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author dylan */
@Component
@Slf4j
public class UserAuthenticationFailureHandler extends AbstractHandler
    implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    log.info("登录失败", e);

    // 返回具体的失败提示
    ResponseCode errorCode = ResponseCode.LOGIN_OR_REGISTER_FAIL;

    if (e instanceof ParamAuthenticationException) {
      errorCode = ResponseCode.findByCode(((ParamAuthenticationException) e).getCode());
    }
    failureResponse(response, errorCode);
  }
}
