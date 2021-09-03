package com.dqcer.integration.slider.model;

import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author dongqin
 * @description 图片对象
 * @date 2021/09/03 21:09:66
 */
public class ImageRead {

	/**
	 * 图片
	 */
	private BufferedImage bufferedImage;

	/**
	 * 文件扩展名
	 */
	private String suffix;

	/**
	 * 输入流
	 */
	private InputStream inputStream;


	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}