package com.redspider.pss.security.handler;

import com.redspider.pss.security.TokenHelper;
import com.redspider.pss.security.UserPayload;

import com.redspider.pss.vo.user.LoginVO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author dylan
 */
@Component
public class UserAuthenticationSuccessHandler extends AbstractHandler
    implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    // todo 七叔 感觉不应该在登录成功的filter来做这件事，直接在登录接口返回更好。小程序不用这个了，我看admin端还在用，后续可以优化下。
    // 生成token返回
    UserPayload payload = (UserPayload) authentication.getPrincipal();
    String token = TokenHelper.generate(payload);

      LoginVO vo = new LoginVO();
      vo.setToken(token);
      vo.setNewUser(payload.getNewUser());
      vo.setLoginPlatform(payload.getLoginPlatform());
      vo.setHasPhone(payload.getHasPhone());
      successResponse(response, vo);
  }

}
