package com.wan.common.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;

public class FileUtil {

	public static String getRandomFileName() {
		
		Date current = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = fmt.format(current);
		return fileName;
		
	}
	
	public static boolean isImage(File file) throws IOException {
		
		String suffix = getFileSuffix(file);
		
		String[] fileAccaccept = ImageIO.getReaderFormatNames();
		if (!Arrays.asList(fileAccaccept).contains(suffix)) {
			return false;
		}
		
		Image img = ImageIO.read(file); //imgFile为图片文件
		if (img == null) {
		    return false; //不是图片
		}
		return true;
		
	}
	
	public static void createDirectory(String filePath) {
		
		String paths[] = filePath.split("\\\\");
		String dir = paths[0];
		for (int i = 1; i < paths.length; i ++) {
			dir = dir + "/" + paths[i];
			File dirFile = new File(dir);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
		}
		
	}
	
	public static String cutImage(File imagePath, int width, int height) throws IOException {
		
		Image img;
		ImageFilter cropFilter;
		// 读取源图像
		BufferedImage bi = ImageIO.read(imagePath);
		int srcWidth = bi.getWidth(); // 源图宽度
		int srcHeight = bi.getHeight(); // 源图高度
		// 若原图大小大于切片大小，设置目标图片大小
		if (width > srcWidth) {
			width = srcWidth;
		}
		if (height > srcHeight) {
			height = srcHeight;
		}
		// 进行切割
		if (srcWidth >= width && srcHeight >= height) {
			int x = srcWidth == width ? 0 : (int) Math.floor((srcWidth - width) / 2);
			int y = srcHeight == height ? 0 : (int) Math.floor((srcHeight - height) / 2);
			
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			cropFilter = new CropImageFilter(x, y, width, height);
			img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
			g.dispose();
			// 获取源文件路径
			String path = imagePath.getPath();
			// 拼接新的输出文件的路径
			String[] split = path.split("\\.");
			String newPath = split[0] + "_cut." + split[1];
			// 创建文件
			File wj = new File(newPath);
			// 输出为文件
			ImageIO.write(tag, getFileSuffix(imagePath), wj);
			return wj.getName();
		}
		return null;
		
	}
	
	public static String getFileSuffix(File file) {
		
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return suffix;
		
	}
	
}
