package com.dqcer.integration.slider.model;

import java.io.Serializable;
import java.util.Random;

/**
 * @author dongqin
 * @description 滑动验证码实体
 * @date 2021/09/03 21:09:22
 */
public class SlideCodePlace implements Serializable {

	private static final long serialVersionUID = 6357114818017799203L;

	/**
	 * 背景图片
	 */
	private String backgroundImage;

	/**
	 * 抠图图片
	 */
	private String mimage;

	/**
	 * X轴坐标位置 此属性一般需要传回后端进行验证
	 */
	private Integer xposition;

	/**
	 * Y轴坐标位置
	 */
	private Integer yposition;

	/**
	 * 误差值 用作业务判断
	 */
	private Integer deviation;
	private String num1;
	private String num2;

	/**
	 * 无参构造器
	 */
	public SlideCodePlace() {
		super();
	}

	/**
	 * 构造器
	 *
	 * @param backgroundImage    背景图
	 * @param mimage    抠图
	 * @param xposition X轴位置
	 * @param yposition Y轴位置
	 */
	public SlideCodePlace(String backgroundImage, String mimage, Integer xposition, Integer yposition) {
		this.backgroundImage = backgroundImage;
		this.mimage = mimage;
		this.xposition = xposition;
		this.yposition = yposition;
	}


	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getMimage() {
		return mimage;
	}

	public void setMimage(String mimage) {
		this.mimage = mimage;
	}

	public Integer getXposition() {
		return xposition;
	}

	public void setXposition(Integer xposition) {
		this.xposition = xposition;
	}

	public Integer getYposition() {
		return yposition;
	}

	public void setYposition(Integer yposition) {
		this.yposition = yposition;
	}

	public Integer getDeviation() {
		return deviation;
	}

	public void setDeviation(Integer deviation) {
		this.deviation = deviation;
	}

	public String getNum1() {
		return num1;
	}

	public void setNum1(String num1) {
		this.num1 = num1;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}

	/**
	 * @param num1 the num1 to set
	 */
	public void setNum1(Integer x) {
		if (x != null) {
			/* 生成随机Long */
			long min = 1000000000000000000L;
			long max = Long.MAX_VALUE;
			Long temp = min + (((long) (new Random().nextDouble() * (max - min))));
			StringBuffer sb = new StringBuffer(temp.toString());
			this.num1 = sb.replace(3, 6, x.toString()).toString();
		} else {
			this.num1 = null;
		}
	}

	/**
	 * @param num2 the num2 to set
	 */
	public void setNum2(Integer y) {
		if (y != null) {
			/* 生成随机Long */
			long min = 1000000000000000000L;
			long max = Long.MAX_VALUE;
			Long temp = min + (((long) (new Random().nextDouble() * (max - min))));
			StringBuffer sb = new StringBuffer(temp.toString());
			this.num2 = sb.replace(4, 7, y.toString()).toString();
		} else {
			this.num2 = null;
		}
	}

	public boolean valid(int newXPosition) {
//		int xPosition = xposition;
//		Integer minXPosition = xPosition - deviation;
//		// 最大允许值
//		Integer maxXPosition = xPosition + deviation;
//		// 判断误差值
//		if (this.xposition > minXPosition && this.xposition < maxXPosition) {
//			return true;
//		}

		if (Math.abs(newXPosition - xposition) < deviation) {
			return true;
		}
		return false;
	}

}
