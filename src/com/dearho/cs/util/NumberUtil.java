package com.dearho.cs.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberUtil {

	public static double floor(double d,int fl){
		BigDecimal b = new BigDecimal(d); 
		//保留2位小数
		double result = b.setScale(fl,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return result;
	}
	public static void main(String[] args) {
		System.out.println(NumberUtil.floor(232.1231231, 0));
	}
	public static Integer double2Int(double d){
		return Integer.parseInt(new java.text.DecimalFormat("0").format(d));
	}
	
	/**
	 * 系统生成表单编号 公共方法
	 * @param headCode
	 * @return
	 */
	public static String createFormNo(String headCode, int existCount){
		StringBuffer formNo = new StringBuffer(headCode);
		DecimalFormat df = new DecimalFormat("0000");
		String countNoStr = "";
		if(existCount == 0){
			countNoStr = df.format(1);
		}else{
			countNoStr = df.format(existCount+1);
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = new Date();
		String dateString = sf.format(currentDate);
		formNo.append(dateString).append(countNoStr);
		return formNo.toString();
	}
}
