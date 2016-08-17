package com.dearho.cs.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.JsonDateValueProcessor;
import com.opensymphony.webwork.ServletActionContext;

/**
 * 导出excel表格通用工具类
 * @author wangjing
 *
 */
public class ExcelUtil {

	/**
	 * 导出表格
	 */
	@SuppressWarnings({ "resource"})
	public static void exportExcel(List<Object> objList, String fileName){
        HSSFWorkbook wb = new HSSFWorkbook();  // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFSheet sheet = wb.createSheet(fileName);  // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFRow row = sheet.createRow((int) 0); // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFCellStyle style = wb.createCellStyle(); // 第四步，创建单元格，并设置值表头 设置表头居中   
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中样式  
        
        writeExcelData(sheet, row, style, objList);
        
		try {
			 HttpServletResponse response = ServletActionContext.getResponse();  
			 response.setContentType("octets/stream");  
			 
		     //转码防止乱码  
		     response.addHeader("Content-Disposition", "attachment;filename="+new String( fileName.getBytes("gb2312"), "ISO8859-1" )+".xls"); 
		     OutputStream out = response.getOutputStream();
			 wb.write(out); 
			 out.close();
		} catch (IOException e) {
			 e.printStackTrace();
		}  
	}

	/**
	 * 往表格中写入数据
	 * @param sheet
	 * @param row
	 * @param style
	 * @param objList 
	 */
	@SuppressWarnings({"deprecation"})
	private static void writeExcelData(HSSFSheet sheet, HSSFRow row, HSSFCellStyle style, List<Object> objList) {
		if(objList != null && objList.size()!=0){
			//先给标题赋值
			Object object = objList.get(0);
			Field[] fields = object.getClass().getDeclaredFields();  
			int colCount = 0;
			List<String> fieldsList = new ArrayList<String>();
			
			for(int i=0; i<fields.length; i++){
				boolean isPresent = fields[i].isAnnotationPresent(FieldComment.class); 
				if(isPresent){
					FieldComment comment = fields[i].getAnnotation(FieldComment.class);// 取注解中的文字说明
					HSSFCell cell = row.createCell((short) colCount);
			        cell.setCellValue(comment.value());  //列头赋值 注解信息
			        cell.setCellStyle(style);  
					colCount++;
					
					fieldsList.add(fields[i].getName());
				}
			}
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			for (int i = 0; i < objList.size(); i++) {  
				Object obj = objList.get(i);
				JSONObject jsonObj = JSONObject.fromObject(obj,jsonConfig);
				row = sheet.createRow((int) i + 1);  
		         
				for(int j = 0; j < fieldsList.size(); j++){
					String fieldName = fieldsList.get(j);
					Object value1 = jsonObj.get(fieldName);
					row.createCell((short) j).setCellValue(value1.toString());   
				}
	        }  
			
		}
	}
	
	
}
