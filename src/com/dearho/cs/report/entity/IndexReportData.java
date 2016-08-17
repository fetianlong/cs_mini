package com.dearho.cs.report.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @author GaoYunpeng
 * @Description 主页数据展示
 * @version 1.0 2015年7月6日 下午2:25:57
 */
public class IndexReportData implements Serializable{

	private static final long serialVersionUID = 1675138327614725015L;

	private List<List<Object>> operateSurvey;
	private String dataStatisticTitle;
	private List<String> xaxis;
	private List<Integer> regMemberCount;
	private List<Integer> orderCount;
	
	private Map<String,Integer> dataSurveyMap;


	public List<List<Object>> getOperateSurvey() {
		return operateSurvey;
	}

	public void setOperateSurvey(List<List<Object>> operateSurvey) {
		this.operateSurvey = operateSurvey;
	}

	public String getDataStatisticTitle() {
		return dataStatisticTitle;
	}

	public void setDataStatisticTitle(String dataStatisticTitle) {
		this.dataStatisticTitle = dataStatisticTitle;
	}


	public List<String> getXaxis() {
		return xaxis;
	}

	public void setXaxis(List<String> xaxis) {
		this.xaxis = xaxis;
	}

	public List<Integer> getRegMemberCount() {
		return regMemberCount;
	}

	public void setRegMemberCount(List<Integer> regMemberCount) {
		this.regMemberCount = regMemberCount;
	}

	public List<Integer> getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(List<Integer> orderCount) {
		this.orderCount = orderCount;
	}

	public Map<String, Integer> getDataSurveyMap() {
		return dataSurveyMap;
	}

	public void setDataSurveyMap(Map<String, Integer> dataSurveyMap) {
		this.dataSurveyMap = dataSurveyMap;
	}
	
	
	
}
