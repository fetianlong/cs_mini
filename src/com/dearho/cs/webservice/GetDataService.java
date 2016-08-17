package com.dearho.cs.webservice;

import javax.jws.WebService;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
@WebService
public interface GetDataService {
	String getData(String dataMark);
}
