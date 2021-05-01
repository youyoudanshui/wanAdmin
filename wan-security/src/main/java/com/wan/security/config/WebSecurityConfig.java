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
	 * BCryptPasswordEncoder密码加密，PasswordEncoder不加密
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		captchaFilter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);
		
		http.authorizeRequests()
			// 如果有允许匿名的url，填在下面
			.anyRequest().authenticated()
			.and()
			// 将验证码过滤器配置到UsernamePasswordAuthenticationFilter前面
			.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
			//登录设置
			.formLogin()
			// 设置登陆页
			.loginPage("/login")
			// 登录处理接口
			.loginProcessingUrl("/doLogin")
			// 设置登陆成功页
			.defaultSuccessUrl("/")
			// 自定义登录成功处理
			.successHandler(myAuthenctiationSuccessHandler)
			// 自定义登录失败处理
			.failureHandler(myAuthenctiationFailureHandler)
			.and()
			// 配置被拦截时的处理  这个位置很关键
			.exceptionHandling()
			//添加无权限时的处理
			.accessDeniedHandler(myAccessDeniedHandler)
			.and()
			.logout()
			.logoutUrl("/logout")
			.deleteCookies("JSESSIONID")
			// 自定义退出成功处理器
			.logoutSuccessHandler(myLogoutSuccessHandler)
			// 禁止重复登录
			.and()
			.sessionManagement()
			.invalidSessionUrl("/login?timeout=1")
			.maximumSessions(1)
			.sessionRegistry(sessionRegistry())
			.expiredUrl("/login?expired=1")
			;
		
		// 关闭CSRF跨域
		http.csrf().disable();
		// 解决iframe打不开问题
		http.headers().frameOptions().sameOrigin();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置拦截忽略文件夹，可以对静态资源放行
		web.ignoring().antMatchers("/css/**", "/js/**", "/wanAdmin/**", "/fonts/**", "/images/**", "/favicon.ico", "/login");
	}
	
}
