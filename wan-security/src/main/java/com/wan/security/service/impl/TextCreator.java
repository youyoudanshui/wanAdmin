package com.wan.security.service.impl;

import java.security.SecureRandom;
import java.util.Random;

import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;

/**
 * 重写验证码文本生成器，使用SecureRandom函数代替Random生成CAPTCHA值
 * @author wmj
 *
 */
public class TextCreator extends Configurable implements TextProducer {

	/**
	 * @return the random text
	 */
	public String getText()
	{
		int length = getConfig().getTextProducerCharLength();
		char[] chars = getConfig().getTextProducerCharString();
		Random rand = new SecureRandom();
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < length; i++)
		{
			text.append(chars[rand.nextInt(chars.length)]);
		}

		return text.toString();
	}
	
}
