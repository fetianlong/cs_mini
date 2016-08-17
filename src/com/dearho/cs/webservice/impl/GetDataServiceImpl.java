package com.dearho.cs.webservice.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.dearho.cs.webservice.GetDataService;
/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
@Component("GetData")
@WebService(endpointInterface="com.dearho.cs.webservice.GetDataService")//, serviceName="GetDataService"
public class GetDataServiceImpl implements GetDataService{

	@Override
	public String getData(String dataMark) {
		return "new Data["+dataMark+"]";
	}

}
