package com.dqcer.integration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dongqin
 * @description i18n属性
 * @date 2021/10/10
 */
@ConfigurationProperties(I18nProperties.PREFIX)
public class I18nProperties {
	
	public static final String PREFIX = "dxp.i18n";

	public static final String DEFAULT_FILE = "classpath:i18n/SysMessages,classpath:i18n/BusinessMessages,classpath:i18n/ValidationMessages";

	/**
	 * basename
	 */
	private String basename;

	/**
	 * 默认的语言
	 */
	private String defaultLanguage = "zh-CN";

	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
}
