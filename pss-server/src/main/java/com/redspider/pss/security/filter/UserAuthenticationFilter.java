package com.redspider.pss.security.filter;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.redspider.pss.security.handler.UserAuthenticationFailureHandler;
import com.redspider.pss.security.handler.UserAuthenticationSuccessHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

/** @author dylan */
public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public UserAuthenticationFilter(
      AuthenticationManager authenticationManager,
      UserAuthenticationSuccessHandler successHandler,
      UserAuthenticationFailureHandler failureHandler) {
    super(new AntPathRequestMatcher("/user/session", "POST"));
    setAuthenticationManager(authenticationManager);
    setAuthenticationSuccessHandler(successHandler);
    setAuthenticationFailureHandler(failureHandler);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        getUsernamePasswordAuthenticationToken(request);
    // provider 进行验证
    return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
  }

  private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(
      HttpServletRequest request) throws IOException {
    Map<String, String> body =
        JSON.parseObject(
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8),
            HashMap.class);
    // 目前是手机号+验证码验证，后续会增加密码验证
    // todo 七叔 感觉这个filter不是很有存在的必要呀。
    return new UsernamePasswordAuthenticationToken(
        MapUtil.getStr(body, "phone", null), MapUtil.getStr(body, "verificationCode", null));
  }
}
