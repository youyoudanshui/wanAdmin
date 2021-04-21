package com.wan.common.util;

import java.awt.Image;
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
		
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		
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
	
}
