package com.dqcer.integration.slider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongqin
 * @description 项目配置 用作自动装配
 * @date 2021/09/03 21:09:52
 */
@Configuration
public class SliderAutoConfiguration {

	/**
	 * 滑块处理
	 *
	 * @return {@link SliderCode}
	 */
	@Bean
	public SliderCode sliderCode() {
		return new SliderCode();
	}
}
