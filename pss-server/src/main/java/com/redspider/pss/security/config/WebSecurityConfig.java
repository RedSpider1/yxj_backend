package com.redspider.pss.security.config;

import com.redspider.pss.security.filter.AdminAuthenticationFilter;
import com.redspider.pss.security.filter.JWTAuthenticationFilter;
import com.redspider.pss.security.filter.UserAuthenticationFilter;
import com.redspider.pss.security.handler.AuthAccessDeniedHandler;
import com.redspider.pss.security.handler.AuthenticationEntryPointHandler;
import com.redspider.pss.security.handler.UserAuthenticationFailureHandler;
import com.redspider.pss.security.handler.UserAuthenticationSuccessHandler;
import com.redspider.pss.security.provider.AdminAuthenticationProvider;
import com.redspider.pss.security.provider.JWTAuthenticationProvider;
import com.redspider.pss.security.provider.UserAuthenticationProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @apiNote spring security的配置类，主要包含：web和wx两块
 * @author dylan
 * @author CoolerWu
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final AuthenticationEntryPointHandler authenticationEntryPointHandler;
	private final AuthAccessDeniedHandler authAccessDeniedHandler;
	private final UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
	private final UserAuthenticationFailureHandler userAuthenticationFailureHandler;
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final JWTAuthenticationProvider jwtAuthenticationProvider;
	private final UserAuthenticationProvider userAuthenticationProvider;
	private final AdminAuthenticationProvider adminAuthenticationProvider;

	public WebSecurityConfig(
		AuthenticationEntryPointHandler authenticationEntryPointHandler,
		AuthAccessDeniedHandler authAccessDeniedHandler,
		UserAuthenticationSuccessHandler userAuthenticationSuccessHandler,
		UserAuthenticationFailureHandler userAuthenticationFailureHandler,
		JWTAuthenticationFilter jwtAuthenticationFilter,
		JWTAuthenticationProvider jwtAuthenticationProvider,
		UserAuthenticationProvider userAuthenticationProvider,
		AdminAuthenticationProvider adminAuthenticationProvider) {
		this.authenticationEntryPointHandler = authenticationEntryPointHandler;
		this.authAccessDeniedHandler = authAccessDeniedHandler;
		this.userAuthenticationSuccessHandler = userAuthenticationSuccessHandler;
		this.userAuthenticationFailureHandler = userAuthenticationFailureHandler;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
		this.userAuthenticationProvider = userAuthenticationProvider;
		this.adminAuthenticationProvider = adminAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(userAuthenticationProvider)
			.authenticationProvider(jwtAuthenticationProvider)
			.authenticationProvider(adminAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 调试完请及时删除不具备超级权限的接口
		String[] superPermit = {
			"/swagger-ui.html",
			"/wx/**",
			"/user/getUserInfo",
			"/webjars/**",
			"/v2/**",
			"/swagger-resources/**",
			"/user/signSMS/*",
			"/groupTeam/query/list",
			"/user/session",
			"/groupTeam/query/*",
			"/redis",
			"/home",
			"/groupTeam/search/*",
			"/groupTeam/groupTeamDetails/**",
			"/user/testCode/**",
			"/label/*",
			"/user/getUserInfoById",
			"/groupTeam/query/queryUsersByGroupId",
			"/pss/query/queryUsersByGroupId",
			"/pss/**",
		};

		// 接口权限校验
		http.cors()
			.and()
			// 由于使用的是JWT，我们这里不需要csrf
			.csrf()
			.disable()
			// 基于token，所以不需要session
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			// 可以匿名访问的链接
			.antMatchers(
				HttpMethod.GET,
				"/ping",
				"/redis",
				"/groupTeam/query/list",
				"/workcommon/apicodes",
				"/workcommon/apiEnums",
				"/groupTeam/query/{id}")
			.permitAll()
			.antMatchers(superPermit)
			.permitAll()
			// 其他所有请求需要身份认证
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.authenticationEntryPoint(authenticationEntryPointHandler)
			.and()
			.exceptionHandling()
			.accessDeniedHandler(authAccessDeniedHandler);

		// 解析请求头参数用于用户登录态的校验
		{
			jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
			http.addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class);
		}

		// 设置在JWT解析之前，进行权限的登录认证并返回
		{
			http.addFilterBefore(adminAuthenticationFilter(), JWTAuthenticationFilter.class);
			http.addFilterBefore(usernamePasswordAuthenticationFilter(), JWTAuthenticationFilter.class);
		}

	}

	private UserAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
		return new UserAuthenticationFilter(
			authenticationManager(),
			userAuthenticationSuccessHandler,
			userAuthenticationFailureHandler);
	}

	private AdminAuthenticationFilter adminAuthenticationFilter() throws Exception {
		return new AdminAuthenticationFilter(
				authenticationManager(),
				userAuthenticationSuccessHandler,
				userAuthenticationFailureHandler);
	}
}
