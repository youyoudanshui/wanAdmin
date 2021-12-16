package com.wan.web.controller.common;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wan.common.domain.Result;
import com.wan.common.exception.FileException;
import com.wan.common.util.FileUtil;
import com.wan.common.util.ResultUtil;
import com.wan.security.util.SecurityUtil;
import com.wan.system.service.SysConfigService;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private SysConfigService configService;
	
	@Value("${wan.upload-path}")
	private String UPLOAD_FOLDER;
	
	@Value("${wan.file-folder}")
	private String FILE_FOLDER;
	
	@Value("${wan.image-folder}")
	private String IMAGE_FOLDER;
	
	private final Logger log = LoggerFactory.getLogger(FileController.class);
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public Result upload(MultipartFile file) {
		File dest = null;
		
		try {
			
			if (file.isEmpty()) {
				throw new FileException("上传失败，请选择文件");
			}
			
			String oFileName = file.getOriginalFilename();
			
			if (FileUtil.checkSpecialChar(oFileName)) {
				throw new FileException("上传失败，文件名不能包含" + FileUtil.specialChars);
			}
			
			String upload_file_ext = configService.getConfig().getUpload_file_ext();
			if (StringUtils.isNotBlank(upload_file_ext) && !FileUtil.checkFileSuffix(oFileName, upload_file_ext)) {
				throw new FileException("上传失败，不支持该文件类型");
			}
			
			String fileName = FileUtil.getRandomFileName() + "-" + oFileName;
			String userFolder = SecurityUtil.getAuthUser().getId().toString();
			String filePath = UPLOAD_FOLDER + File.separator + userFolder + File.separator + FILE_FOLDER + File.separator;
			dest = new File(filePath + fileName);
			
			FileUtil.createDirectory(filePath);
			file.transferTo(dest);
			String rs = userFolder + "/" + FILE_FOLDER + "/" + fileName;
			return ResultUtil.success(rs);
			
		} catch (Exception e) {
			
			if (dest != null && dest.exists()) {
				dest.delete();
			}
			
			if (e instanceof FileException) {
				return ResultUtil.error(e.getMessage());
			}
			
			log.error("异常信息：", e);			
			return ResultUtil.error("上传失败！");
			
		}
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		File dest = null;
		
		try {
			
			if (file.isEmpty()) {
				throw new FileException("上传失败，请选择文件");
			}
			
			String oFileName = file.getOriginalFilename();
			
			if (FileUtil.checkSpecialChar(oFileName)) {
				throw new FileException("上传失败，文件名不能包含" + FileUtil.specialChars);
			}
			
			String upload_image_ext = configService.getConfig().getUpload_image_ext();
			if (StringUtils.isNotBlank(upload_image_ext) && !FileUtil.checkFileSuffix(oFileName, upload_image_ext)) {
				throw new FileException("上传失败，上传图片的类型不符合(" + upload_image_ext + ")");
			}
			
			String fileName = FileUtil.getRandomFileName() + "-" + oFileName;
			String userFolder = SecurityUtil.getAuthUser().getId().toString();
			String filePath = UPLOAD_FOLDER + File.separator + userFolder + File.separator + IMAGE_FOLDER + File.separator;
			dest = new File(filePath + fileName);
			
			FileUtil.createDirectory(filePath);
			file.transferTo(dest);
			
			if (!FileUtil.isImage(dest)) {
				throw new FileException("上传失败，文件不是图片");
			}
			
			String rs = userFolder + "/" + IMAGE_FOLDER + "/" + fileName;
			return ResultUtil.success(rs);
			
		} catch (Exception e) {
			
			if (dest != null && dest.exists()) {
				dest.delete();
			}
			
			if (e instanceof FileException) {
				return ResultUtil.error(e.getMessage());
			}
			
			log.error("异常信息：", e);
			return ResultUtil.error("上传失败！");
		}
	}
	
	/**
	 * 上传头像
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadAvatar")
	@ResponseBody
	public Result uploadAvatar(MultipartFile file) {
		File dest = null;
		
		try {
			
			if (file.isEmpty()) {
				throw new FileException("上传失败，请选择文件");
			}
			
			String oFileName = file.getOriginalFilename();
			
			if (FileUtil.checkSpecialChar(oFileName)) {
				throw new FileException("上传失败，文件名不能包含" + FileUtil.specialChars);
			}
			
			String upload_image_ext = configService.getConfig().getUpload_image_ext();
			if (StringUtils.isNotBlank(upload_image_ext) && !FileUtil.checkFileSuffix(oFileName, upload_image_ext)) {
				throw new FileException("上传失败，上传图片的类型不符合(" + upload_image_ext + ")");
			}
			
			String fileName = FileUtil.getRandomFileName() + "-" + oFileName;
			String userFolder = SecurityUtil.getAuthUser().getId().toString();
			String filePath = UPLOAD_FOLDER + File.separator + userFolder + File.separator + IMAGE_FOLDER + File.separator;
			dest = new File(filePath + fileName);
			
			FileUtil.createDirectory(filePath);
			file.transferTo(dest);
			
			if (!FileUtil.isImage(dest)) {
				throw new FileException("上传失败，文件不是图片");
			}
			
			// 裁剪头像为68x68
			String cutFileName = FileUtil.cutImage(dest, 68, 68);
			
			String rs = userFolder + "/" + IMAGE_FOLDER + "/" + (cutFileName == null ? fileName : cutFileName);
			return ResultUtil.success(rs);
			
		} catch (Exception e) {
			
			if (dest != null && dest.exists()) {
				dest.delete();
			}
			
			if (e instanceof FileException) {
				return ResultUtil.error(e.getMessage());
			}
			
			log.error("异常信息：", e);
			return ResultUtil.error("上传失败！");
			
		}
	}
	
}
