package com.wan.web.controller.system;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.wan.web.controller.common.BaseController;

/**
 * 登录
 * @author wmj
 *
 */
@Controller
public class SysLoginController extends BaseController {
	
	@Autowired
	private Producer captchaProducer;
	
	@GetMapping("/login")
	public String login(ModelMap map) {
		getConfig(map);
		return "login";
	}
	
	@GetMapping("/images/captcha.png")
	public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//用字节数组存储
		byte[] captchaChallengeAsPng = null;
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		ServletOutputStream responseOutputStream = response.getOutputStream();
		final HttpSession httpSession = request.getSession();
		
		try {
			
			//生产验证码字符串并保存到session中
			String createText = captchaProducer.createText();
			httpSession.setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
			
			//使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = captchaProducer.createImage(createText);
			ImageIO.write(challenge, "png", pngOutputStream);
			captchaChallengeAsPng = pngOutputStream.toByteArray();
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/png");
			
            //定义response输出类型为image/png类型，使用response输出流输出图片的byte数组
			responseOutputStream.write(captchaChallengeAsPng);
            responseOutputStream.flush();
            
 		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}finally {
			responseOutputStream.close();
		}
	}
	
}
