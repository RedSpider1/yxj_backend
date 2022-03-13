package com.redspider.pss.security.filter;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.redspider.pss.security.AdminAuthenticationToken;
import com.redspider.pss.security.handler.UserAuthenticationFailureHandler;
import com.redspider.pss.security.handler.UserAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiushui
 */
public class AdminAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public AdminAuthenticationFilter(
      AuthenticationManager authenticationManager,
      UserAuthenticationSuccessHandler successHandler,
      UserAuthenticationFailureHandler failureHandler) {
    super(new AntPathRequestMatcher("/admin/login", "POST"));
    setAuthenticationManager(authenticationManager);
    setAuthenticationSuccessHandler(successHandler);
    setAuthenticationFailureHandler(failureHandler);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {
    AdminAuthenticationToken adminAuthenticationToken =
        getAdminAuthenticationToken(request);
    // provider 进行验证
    return getAuthenticationManager().authenticate(adminAuthenticationToken);
  }

  private AdminAuthenticationToken getAdminAuthenticationToken(
      HttpServletRequest request) throws IOException {
    Map<String, String> body =
        JSON.parseObject(
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8),
            HashMap.class);
    return new AdminAuthenticationToken(
        MapUtil.getStr(body, "username", null), MapUtil.getStr(body, "password", null));
  }

}
