package com.dqcer.integration.configuration;

import com.dqcer.integration.properties.I18nProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author dongqin
 * @description I18n 配置
 * @date 17:11 2020/11/11
 */
@Configuration
@EnableConfigurationProperties(I18nProperties.class)
public class AutoConfiguration {
	
	@Bean
	public MessageSource messageSource(I18nProperties properties) {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		String basename = properties.getBasename();
		if (null == basename || basename.trim().length() == 0) {
			basename = I18nProperties.DEFAULT_FILE;
		}
		String[] base = basename.split(",");
		messageBundle.setBasenames(base);
		return messageBundle;
	}
}
