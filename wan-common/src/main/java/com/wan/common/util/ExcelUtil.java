package com.wan.common.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.wan.common.annotation.Excel;

import jxl.CellView;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	
	/**
	 * 导出Excel
	 * 
	 * @param list
	 *            ：结果集合
	 * @param filePath
	 *            ：指定的路径名
	 * @param response.getOutputStream()
	 *            ：输出流对象 通过response.getOutputStream()传入
	 * @param headers
	 *            ：表头信息
	 * @param keySet
	 *            ：表头对应的数据字段
	 * @param sheetName
	 *            ：工作表名称
	 */
	public static void createExcel(List list, Class listClass, String filePath, HttpServletResponse response,
			String headers[], String keySet[], List<Map<String, String>> dicts, String fileName, String sheetName) {
		sheetName = sheetName != null && !sheetName.equals("") ? sheetName : "sheet1";
		WritableWorkbook wook = null;// 可写的工作薄对象
		ServletOutputStream out = null;
		try {
			if (filePath != null && !filePath.equals("")) {
				wook = Workbook.createWorkbook(new File(filePath));// 指定导出的目录和文件名
				// 如：D:\\test.xls
			} else {
				out = response.getOutputStream();
				response.setContentType("application/x-download");
				fileName = fileName + ".xls";
				// 设置下载文件名
				response.addHeader("Content-Disposition",
						"attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
				wook = Workbook.createWorkbook(out);// html页面导出用
			}

			// 设置头部字体格式
			WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			// 应用字体
			WritableCellFormat wcfh = new WritableCellFormat(font);
			// 设置其他样式
			wcfh.setAlignment(Alignment.CENTRE);// 水平对齐
			wcfh.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfh.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfh.setBackground(Colour.GRAY_25);// 背景色
			wcfh.setWrap(false);// 不自动换行

			WritableCellFormat wcfc = new WritableCellFormat();

			wcfc.setAlignment(Alignment.CENTRE);
			wcfc.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfc.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfc.setWrap(false);// 不自动换行

			// 创建工作表
			WritableSheet sheet = wook.createSheet(sheetName, 0);
			SheetSettings setting = sheet.getSettings();
			setting.setVerticalFreeze(1);// 冻结窗口头部
			
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			if (headers != null && keySet != null && headers.length == keySet.length) {
				// 开始导出表格头部
				for (int i = 0; i < headers.length; i++) {
					// 应用wcfh样式创建单元格
					sheet.setColumnView(i, cellView); // 根据内容自动设置列宽，内容为中文时无效
					sheet.addCell(new Label(i, 0, headers[i], wcfh));
				}

				if (list != null && list.size() > 0) {

					// 导出表格内容
					Map<String, Method> getMap = getAllMethod(listClass);// 获得对象所有的get方法;
					Method method = null;
					Object objClass = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						objClass = list.get(i);
						// 按保存的字段顺序导出内容
						for (int j = 0; j < keySet.length; j++) {
							// 根据key获取对应方法
							method = getMap.get("GET" + keySet[j].toString().toUpperCase());
							if (method != null) {
								// 从对应的get方法得到返回值
								String value = method.invoke(objClass, null) == null ? ""
										: String.valueOf(method.invoke(objClass, null));
								if (dicts.get(j) != null) {
									value = dicts.get(j).get(value);
								}
								// 应用wcfc样式创建单元格
								sheet.addCell(new Label(j, i + 1, value, wcfc));
							} else {
								// 如果没有对应的get方法，则默认将内容设为""
								sheet.addCell(new Label(j, i + 1, "", wcfc));
							}

						}
					}
				}
				wook.write();
			} else {
				throw new Exception("传入参数不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wook != null) {
					wook.close();
				}
				if (response != null && out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取类的所有get方法
	 * 
	 * @param cls
	 * @return
	 */
	public static HashMap<String, Method> getAllMethod(Class c) throws Exception {
		HashMap<String, Method> map = new HashMap<String, Method>();
		Method[] methods = c.getMethods();// 得到所有方法
		String methodName = "";
		for (int i = 0; i < methods.length; i++) {
			methodName = methods[i].getName().toUpperCase();// 方法名
			if (methodName.startsWith("GET")) {
				map.put(methodName, methods[i]);// 添加get方法至map中
			}
		}
		return map;
	}
	
	/**
	 * 获取类的所有导出字段
	 * 
	 * @param cls
	 * @return
	 */
	public static HashMap<String, Object> getExportFields(Class c) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> keySet = new ArrayList<String>();
		List<String> headers = new ArrayList<String>();
		List<Map<String, String>> dicts = new ArrayList<Map<String, String>>();
		
		keySet.add("id");
		headers.add("编号");
		dicts.add(null);
		
		Field[] fields = c.getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);
			String key = field.getName();
			Excel excel = field.getAnnotation(Excel.class);
			if (excel != null) {
				keySet.add(key);
				headers.add(excel.Name());
				
				String dictStr = excel.Dict();
				if (StringUtils.isNotBlank(dictStr)) {
					Map<String, String> dict = new HashMap<String, String>();
					String[] dictArr = dictStr.split(",");
					for (String s: dictArr) {
						dict.put(s.split("=")[0], s.split("=")[1]);
					}
					dicts.add(dict);
				} else {
					dicts.add(null);
				}
			}
		}
		
		keySet.add("gmtCreate");
		headers.add("时间");
		dicts.add(null);
		
		map.put("keySet", keySet.toArray(new String[keySet.size()]));
		map.put("headers", headers.toArray(new String[headers.size()]));
		map.put("dicts", dicts);
		return map;
	}
	
	/**
	 * 从Html页面导出Excel
	 * 
	 * @param list
	 * @param filePath
	 * @param response
	 * @param headers
	 * @param keySet
	 * @param sheetName
	 */
	public static void exportHtmlExcel(List list, Class listClass, HttpServletResponse response, String fileName, String sheetName) {
		HashMap<String, Object> map = getExportFields(listClass);
		String headers[] = (String[]) map.get("headers");
		String keySet[] = (String[]) map.get("keySet");
		List<Map<String, String>> dicts = (List<Map<String, String>>) map.get("dicts");
		createExcel(list, listClass, null, response, headers, keySet, dicts, fileName, sheetName);
	}

}
