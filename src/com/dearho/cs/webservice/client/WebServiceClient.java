package com.dearho.cs.webservice.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dearho.cs.webservice.GetDataService;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class WebServiceClient {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-client.xml");  
        GetDataService getDataService = (GetDataService) context.getBean("client");  
        String response = getDataService.getData("wangyx");  
        System.out.println(response);  
	}
}
