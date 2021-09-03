package com.dqcer.integration.slider;

import com.dqcer.integration.slider.model.ImageRead;
import com.dqcer.integration.slider.model.SlideCodePlace;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author dongqin
 * @description 滑动验证码生成工具 可配置 <b>spring.slidecode.path.image</b> 指向项目下资源
 * @date 2021/09/03 21:09:39
 */

public class SliderCode {

	/**
	 * 项目目录
	 */
	private final static String CLASS_PATH = "classpath";

	/**
	 * 抠图模板图宽度
	 */
	private final static int CUT_WIDTH = 50;

	/**
	 * 抠图模板高度
	 */
	private final static int CUT_HEIGHT = 50;

	/**
	 * 抠图凸起圆心 *
	 */
	private final static int CENTER_CIRCLE_INDEX = 5;

	/**
	 * 抠图内部矩形填充大小
	 */
	private final static int RECTANGLE_PADDING = 8;

	/**
	 * 抠图的边框宽度
	 */
	private final static int SLIDER_IMG_OUT_PADDING = 1;

	/**
	 * 资源路径
	 */
	private final static String RESOURCE_PATH = "classpath*:images";

	/**
	 * 获取一个滑动验证码
	 *
	 * @return {@link SlideCodePlace}
	 * @throws IOException ioexception
	 */
	public SlideCodePlace slideCode() throws IOException {
		// 随机获取一张图片作为源图层
		ImageRead img = randomImage();
		// 随机计算抠图位置二维数组
		int[][] data = getBlockData();
		// 处理抠图并返回数据
		return cutAndSave(img, data);
	}

	/**
	 * 随机获得一张图片
	 *
	 * @return {@link ImageRead}
	 * @throws IOException
	 */
	private ImageRead randomImage() throws IOException {
		ImageRead imageRead = null;
		Random random = new Random();
		if (RESOURCE_PATH.indexOf(CLASS_PATH) >= 0) {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(RESOURCE_PATH.concat("/*"));
			if (resources != null) {
				int i = random.nextInt(resources.length);
				imageRead = new ImageRead();
				imageRead.setBufferedImage(ImageIO.read(resources[i].getInputStream()));
				String extension = resources[i].getFilename().substring(resources[i].getFilename().lastIndexOf(".") + 1);
				imageRead.setInputStream(resources[i].getInputStream());
				imageRead.setSuffix(extension);
			}
		} else {
			File importImage = new File(RESOURCE_PATH);
			if (importImage != null) {
				File[] files = importImage.listFiles();
				int i = random.nextInt(files.length);
				imageRead = new ImageRead();
				imageRead.setBufferedImage(ImageIO.read(files[i]));
				String extension = files[i].getName().substring(files[i].getName().lastIndexOf(".") + 1);
				imageRead.setSuffix(extension);
				imageRead.setInputStream(new FileInputStream(files[i]));
			}
		}
		return imageRead;
	}

	/**
	 * 抠图
	 *
	 * @param bimage 背景图
	 * @param mimage 小图
	 * @param blocks 抠图二维数组
	 * @param x      X轴坐标
	 * @param y      Y轴坐标
	 */
	private void matting(BufferedImage bimage, BufferedImage mimage, int[][] blocks, int x, int y) {
		for (int i = 0; i < CUT_WIDTH; i++) {
			for (int j = 0; j < CUT_HEIGHT; j++) {
				int _x = x + i;
				int _y = y + j;
				int rgbFlg = blocks[i][j];
				int rgb_ori = bimage.getRGB(_x, _y);
				// 原图中对应位置变色处理
				if (rgbFlg == 1) {
					// 抠图上复制对应颜色值
					mimage.setRGB(i, j, rgb_ori);
					// 原图对应位置颜色变化
					bimage.setRGB(_x, _y, Color.WHITE.getRGB());
				} else if (rgbFlg == 2) {
					mimage.setRGB(i, j, Color.WHITE.getRGB());
					bimage.setRGB(_x, _y, Color.LIGHT_GRAY.getRGB());
				} else if (rgbFlg == 0) {
					mimage.setRGB(i, j, rgb_ori & 0x00FFFFFF);
				}
			}
		}
	}

	/**
	 * 处理抠图并返回数据
	 *
	 * @param img  原图
	 * @param data 抠图数组
	 * @return {@link SlideCodePlace}
	 * @throws IOException
	 */
	private SlideCodePlace cutAndSave(ImageRead img, int[][] data) throws IOException {
		BufferedImage bimage = img.getBufferedImage();
		/* 处理抠图 */
		if (bimage != null) {
			/* X轴位置 至少两个抠图位置 */
			int locationX = CUT_WIDTH * 2 + new Random().nextInt(bimage.getWidth() - (CUT_WIDTH * 3));
			/* Y轴位置 */
			int locationY = new Random().nextInt(bimage.getHeight() - CUT_HEIGHT);
			BufferedImage markImage = new BufferedImage(CUT_WIDTH, CUT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
			/* 抠出拼图 */
			matting(bimage, markImage, data, locationX, locationY);
			/* 转换图片为Base64 */
			return new SlideCodePlace(imageToBase64(bimage, img.getSuffix()), imageToBase64(markImage, img.getSuffix()), locationX, locationY);
		}
		return new SlideCodePlace();
	}

	/**
	 * 图片转Base64字符串
	 *
	 * @param image  图片
	 * @param suffix 后缀
	 * @return {@link String}
	 * @throws IOException
	 */
	private String imageToBase64(BufferedImage image, String suffix) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, suffix, baos);
		return "data:image/" + suffix + ";base64," + Base64.encodeBase64String(baos.toByteArray());
	}

	/**
	 * 随机生成抠图二维数组点阵
	 *
	 * @return int[][]
	 */
	private int[][] getBlockData() {
		int[][] data = new int[CUT_WIDTH][CUT_HEIGHT];
		Random random = new Random();
		// (x-a)²+(y-b)²=r²
		// x中心位置左右5像素随机
		double x1 = RECTANGLE_PADDING + (CUT_WIDTH - 2 * RECTANGLE_PADDING) / 2.0 - 5 + random.nextInt(10);
		double y1_top = RECTANGLE_PADDING - random.nextInt(3);
		double y1_bottom = CUT_HEIGHT - RECTANGLE_PADDING + random.nextInt(3);
		double y1 = random.nextInt(2) == 1 ? y1_top : y1_bottom;
		double x2_right = CUT_WIDTH - RECTANGLE_PADDING - CENTER_CIRCLE_INDEX + random.nextInt(2 * CENTER_CIRCLE_INDEX - 4);
		double x2_left = RECTANGLE_PADDING + CENTER_CIRCLE_INDEX - 2 - random.nextInt(2 * CENTER_CIRCLE_INDEX - 4);
		double x2 = random.nextInt(2) == 1 ? x2_right : x2_left;
		double y2 = RECTANGLE_PADDING + (CUT_HEIGHT - 2 * RECTANGLE_PADDING) / 2.0 - 4 + random.nextInt(10);
		double po = Math.pow(CENTER_CIRCLE_INDEX, 2);
		for (int i = 0; i < CUT_WIDTH; i++) {
			for (int j = 0; j < CUT_HEIGHT; j++) {
				// 矩形区域
				boolean fill;
				if ((i >= RECTANGLE_PADDING && i < CUT_WIDTH - RECTANGLE_PADDING) && (j >= RECTANGLE_PADDING && j < CUT_HEIGHT - RECTANGLE_PADDING)) {
					data[i][j] = 1;
					fill = true;
				} else {
					data[i][j] = 0;
					fill = false;
				}
				// 凸出区域
				double d3 = Math.pow(i - x1, 2) + Math.pow(j - y1, 2);
				if (d3 < po) {
					data[i][j] = 1;
				} else {
					if (!fill) {
						data[i][j] = 0;
					}
				}
				// 凹进区域
				double d4 = Math.pow(i - x2, 2) + Math.pow(j - y2, 2);
				if (d4 < po) {
					data[i][j] = 0;
				}
			}
		}
		// 边界阴影
		for (int i = 0; i < CUT_WIDTH; i++) {
			for (int j = 0; j < CUT_HEIGHT; j++) {
				// 四个正方形边角处理
				for (int k = 1; k <= SLIDER_IMG_OUT_PADDING; k++) {
					// 左上、右上
					if (i >= RECTANGLE_PADDING - k && i < RECTANGLE_PADDING && ((j >= RECTANGLE_PADDING - k && j < RECTANGLE_PADDING) || (j >= CUT_HEIGHT - RECTANGLE_PADDING - k && j < CUT_HEIGHT - RECTANGLE_PADDING + 1))) {
						data[i][j] = 2;
					}

					// 左下、右下
					if (i >= CUT_WIDTH - RECTANGLE_PADDING + k - 1 && i < CUT_WIDTH - RECTANGLE_PADDING + 1) {
						for (int n = 1; n <= SLIDER_IMG_OUT_PADDING; n++) {
							if (((j >= RECTANGLE_PADDING - n && j < RECTANGLE_PADDING) || (j >= CUT_HEIGHT - RECTANGLE_PADDING - n && j <= CUT_HEIGHT - RECTANGLE_PADDING))) {
								data[i][j] = 2;
							}
						}
					}
				}
				if (data[i][j] == 1 && j - SLIDER_IMG_OUT_PADDING > 0 && data[i][j - SLIDER_IMG_OUT_PADDING] == 0) {
					data[i][j - SLIDER_IMG_OUT_PADDING] = 2;
				}
				if (data[i][j] == 1 && j + SLIDER_IMG_OUT_PADDING > 0 && j + SLIDER_IMG_OUT_PADDING < CUT_HEIGHT && data[i][j + SLIDER_IMG_OUT_PADDING] == 0) {
					data[i][j + SLIDER_IMG_OUT_PADDING] = 2;
				}
				if (data[i][j] == 1 && i - SLIDER_IMG_OUT_PADDING > 0 && data[i - SLIDER_IMG_OUT_PADDING][j] == 0) {
					data[i - SLIDER_IMG_OUT_PADDING][j] = 2;
				}
				if (data[i][j] == 1 && i + SLIDER_IMG_OUT_PADDING > 0 && i + SLIDER_IMG_OUT_PADDING < CUT_WIDTH && data[i + SLIDER_IMG_OUT_PADDING][j] == 0) {
					data[i + SLIDER_IMG_OUT_PADDING][j] = 2;
				}
			}
		}
		return data;
	}
}
