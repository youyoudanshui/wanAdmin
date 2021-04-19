package com.wan.common.util;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;

public class FileUtil {
	
	public static String UPLOAD_FOLDER = "E://upload//";
	public static String PROJECT_FOLDER = ReqUtil.getRequest().getContextPath();
	
	public static String getUploadPath() throws FileNotFoundException {
        
		String uploadPath = UPLOAD_FOLDER + PROJECT_FOLDER;
		
        File folder = new File(uploadPath);
        if(!folder.getParentFile().exists()){
        	folder.getParentFile().mkdir();
        }
        if(!folder.exists()){
        	folder.mkdir();
        }
        
        return uploadPath;
	}

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
	
}
