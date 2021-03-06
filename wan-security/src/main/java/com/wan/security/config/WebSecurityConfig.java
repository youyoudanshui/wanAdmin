package com.wan.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.wan.security.filter.CaptchaFilter;
import com.wan.security.handler.MyAccessDeniedHandler;
import com.wan.security.handler.MyAuthenctiationFailureHandler;
import com.wan.security.handler.MyAuthenctiationSuccessHandler;
import com.wan.security.handler.MyLogoutSuccessHandler;
import com.wan.security.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CaptchaFilter captchaFilter;
	
	@Autowired
	private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;
	
	@Autowired
	private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;
	
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	
	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;

	@Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SessionRegistry sessionRegistry(){
		return new SessionRegistryImpl();
    }
	
	@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
	
	/**
	 * BCryptPasswordEncoder???????????????PasswordEncoder?????????
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		captchaFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);
		
		http.authorizeRequests()
			// ????????????????????????url???????????????
			.anyRequest().authenticated()
			.and()
			// ??????????????????????????????UsernamePasswordAuthenticationFilter??????
			.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
			//????????????
			.formLogin()
			// ???????????????
			.loginPage("/login")
			// ??????????????????
			.loginProcessingUrl("/doLogin")
			// ?????????????????????
			.defaultSuccessUrl("/")
			// ???????????????????????????
			.successHandler(myAuthenctiationSuccessHandler)
			// ???????????????????????????
			.failureHandler(myAuthenctiationFailureHandler)
			.and()
			// ???????????????????????????  ?????????????????????
			.exceptionHandling()
			//???????????????????????????
			.accessDeniedHandler(myAccessDeniedHandler)
			.and()
			.logout()
			.logoutUrl("/logout")
			.deleteCookies("JSESSIONID")
			// ??????????????????????????????
			.logoutSuccessHandler(myLogoutSuccessHandler)
			// ??????????????????
			.and()
			.sessionManagement()
			.invalidSessionUrl("/login?timeout=1")
			.maximumSessions(1)
			.sessionRegistry(sessionRegistry())
			.expiredUrl("/login?expired=1")
			;
		
		// ??????CSRF??????
		http.csrf().disable();
		// ??????iframe???????????????
		http.headers().frameOptions().sameOrigin();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// ?????????????????????????????????????????????????????????
		web.ignoring().antMatchers("/css/**", "/js/**", "/wanAdmin/**", "/fonts/**", "/images/**", "/*/Image/**", "/favicon.ico", "/login");
	}
	
}
