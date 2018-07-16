package com.wyk;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class picture {
	
	static String File_PATH = "C:\\Users\\Win7\\Desktop\\cap2.jpg";
	
	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageIO.read(new File(File_PATH));
		int width = img.getWidth();
		int height = img.getHeight();
		// 对图片进行二值化处理
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = img.getRGB(x, y);
				newImage.setRGB(x, y, rgb);
			}
		}
		ImageIO.write(newImage, "JPG", new File("C:\\Users\\Win7\\Desktop\\result.jpg"));
		System.out.println("产生图片成功！");
	}
}
