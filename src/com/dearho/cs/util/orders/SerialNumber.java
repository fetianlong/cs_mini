/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file SerialNumber.java creation date: [2015年6月4日 下午2:37:52] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.util.orders;

/**
 * @author Carsharing03
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月4日
 */
public abstract class SerialNumber {
	public synchronized String getSerialNumber() {  
        return process();  
    }  
    protected abstract String process(); 
}