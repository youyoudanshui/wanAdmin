package com.wan.security.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.wan.security.service.impl.TextCreator;

@Configuration
public class KaptchaConfig {

	@Bean(name="captchaProducer")
	public DefaultKaptcha getDefaultKaptcha() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "no");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "99,51,81");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "120");
        // 图片高
        properties.setProperty("kaptcha.image.height", "36");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 背景颜色渐进
        properties.setProperty("kaptcha.background.clear.from", "247,255,255");
        // 背景颜色渐进
        properties.setProperty("kaptcha.background.clear.to", "247,255,255");
        // 噪点颜色
        properties.setProperty("kaptcha.noise.color", "134,128,130");
        // 随机文本生成器
        properties.setProperty("kaptcha.textproducer.impl", TextCreator.class.getName());
 
 
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
 
        return defaultKaptcha;
	}
	
}
