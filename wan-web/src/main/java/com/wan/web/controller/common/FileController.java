package com.wan.web.controller.common;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wan.common.domain.Result;
import com.wan.common.util.FileUtil;
import com.wan.common.util.ResultUtil;
import com.wan.system.service.SysConfigService;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	@Autowired
	private SysConfigService configService;

	@PostMapping("/upload")
	@ResponseBody
	public Result upload(MultipartFile file) throws FileNotFoundException {
		if (file.isEmpty()) {
			return ResultUtil.error("上传失败，请选择文件");
		}
		
		String fileName = FileUtil.getRandomFileName() + "-" + file.getOriginalFilename();
		String userFloder = "1/files/";
		String filePath = FileUtil.getUploadPath() + "/" + userFloder;
		File dest = new File(filePath + fileName);
		
		try {
		
			// 创建用户文件夹
			if(!dest.getParentFile().getParentFile().exists()){
				dest.getParentFile().getParentFile().mkdir();
			}
			// 创建files文件夹
			if(!dest.getParentFile().exists()){
				dest.getParentFile().mkdir();
	        }
		
			file.transferTo(dest);
			String rs = userFloder + fileName;
			return ResultUtil.success(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			dest.delete();
			return ResultUtil.error("上传失败！");
		}
	}
	
	@PostMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) throws FileNotFoundException {
		if (file.isEmpty()) {
			return ResultUtil.error("上传失败，请选择文件");
		}
		
		String oFileName = file.getOriginalFilename();
		String upload_image_ext = configService.getConfigValue("upload_image_ext");
		String suffix = oFileName.substring(oFileName.lastIndexOf(".") + 1);
		if (upload_image_ext.indexOf(suffix) < 0) {
			return ResultUtil.error("您上传图片的类型不符合(" + upload_image_ext + ")");
		}
		
		String fileName = FileUtil.getRandomFileName() + "-" + oFileName;
		String userFloder = "1/images/";
		String filePath = FileUtil.getUploadPath() + "/" + userFloder;
		File dest = new File(filePath + fileName);
		
		try {
			
			// 创建用户文件夹
			if(!dest.getParentFile().getParentFile().exists()){
				dest.getParentFile().getParentFile().mkdir();
			}
			// 创建images文件夹
			if(!dest.getParentFile().exists()){
				dest.getParentFile().mkdir();
	        }
		
			file.transferTo(dest);
			
			if (!FileUtil.isImage(dest)) {
				dest.delete();
				return ResultUtil.error("上传失败，文件不是图片");
			}
			
			String rs = userFloder + fileName;
			return ResultUtil.success(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			dest.delete();
			return ResultUtil.error("上传失败！");
		}
	}
	
}
