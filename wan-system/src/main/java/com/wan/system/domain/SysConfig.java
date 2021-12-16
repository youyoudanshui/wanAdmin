package com.wan.system.domain;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 网站配置
 * @author wmj
 *
 */
public class SysConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String web_site_title;
	
	String web_site_logo_type;
	
	String web_site_logo_text;
	
	String web_site_logo_image;
	
	String web_site_keywords;
	
	String web_site_description;
	
	String web_site_copyright;
	
	String upload_image_ext;
	
	String login_page_bg_image;
	
	String login_page_template_type;
	
	String multitabs_cache;
	
	String upload_file_ext;
	
	public SysConfig() {
		
	}
	
	public SysConfig(Map<String, String> configMap) {
		
		web_site_title = configMap.get("web_site_title");
		web_site_logo_type = configMap.get("web_site_logo_type");
		web_site_logo_text = configMap.get("web_site_logo_text");
		web_site_logo_image = configMap.get("web_site_logo_image");
		web_site_keywords = configMap.get("web_site_keywords");
		web_site_description = configMap.get("web_site_description");
		web_site_copyright = configMap.get("web_site_copyright");
		upload_image_ext = configMap.get("upload_image_ext");
		login_page_bg_image = configMap.get("login_page_bg_image");
		login_page_template_type = configMap.get("login_page_template_type");
		multitabs_cache = configMap.get("multitabs_cache");
		upload_file_ext = configMap.get("upload_file_ext");
		
	}

	public String getWeb_site_title() {
		return web_site_title;
	}

	public void setWeb_site_title(String web_site_title) {
		this.web_site_title = web_site_title;
	}

	public String getWeb_site_logo_type() {
		return web_site_logo_type;
	}

	public void setWeb_site_logo_type(String web_site_logo_type) {
		this.web_site_logo_type = web_site_logo_type;
	}

	public String getWeb_site_logo_text() {
		return web_site_logo_text;
	}

	public void setWeb_site_logo_text(String web_site_logo_text) {
		this.web_site_logo_text = web_site_logo_text;
	}

	public String getWeb_site_logo_image() {
		return web_site_logo_image;
	}

	public void setWeb_site_logo_image(String web_site_logo_image) {
		this.web_site_logo_image = web_site_logo_image;
	}

	public String getWeb_site_keywords() {
		return web_site_keywords;
	}

	public void setWeb_site_keywords(String web_site_keywords) {
		this.web_site_keywords = web_site_keywords;
	}

	public String getWeb_site_description() {
		return web_site_description;
	}

	public void setWeb_site_description(String web_site_description) {
		this.web_site_description = web_site_description;
	}

	public String getWeb_site_copyright() {
		return web_site_copyright;
	}

	public void setWeb_site_copyright(String web_site_copyright) {
		this.web_site_copyright = web_site_copyright;
	}

	public String getUpload_image_ext() {
		return upload_image_ext;
	}

	public void setUpload_image_ext(String upload_image_ext) {
		this.upload_image_ext = upload_image_ext;
	}

	public String getLogin_page_bg_image() {
		return login_page_bg_image;
	}

	public void setLogin_page_bg_image(String login_page_bg_image) {
		this.login_page_bg_image = login_page_bg_image;
	}

	public String getLogin_page_template_type() {
		if (StringUtils.isBlank(login_page_template_type)) {
			return "1";
		}
		return login_page_template_type;
	}

	public void setLogin_page_template_type(String login_page_template_type) {
		this.login_page_template_type = login_page_template_type;
	}

	public String getMultitabs_cache() {
		return multitabs_cache;
	}

	public void setMultitabs_cache(String multitabs_cache) {
		this.multitabs_cache = multitabs_cache;
	}

	public String getUpload_file_ext() {
		return upload_file_ext;
	}

	public void setUpload_file_ext(String upload_file_ext) {
		this.upload_file_ext = upload_file_ext;
	}
	
}
