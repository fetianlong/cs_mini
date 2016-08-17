package com.dearho.cs.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class HtmlTagParse {
	private static final Log log = LogFactory.getLog(HtmlTagParse.class);
	private static HtmlTagParse instance;
	
	@SuppressWarnings("rawtypes")
	private static Map fieldNameMap = new HashMap();

	public static synchronized HtmlTagParse getInstance() {
		try {
			if (instance == null) {
				instance = new HtmlTagParse();
				readXmlFile("/htmltag.xml");
			} else {
				return instance;
			}
		} catch (Exception e) {
			log.error("HtmlTagParse::getInstance:" + e.getMessage());
			instance = null;
		}
		return instance;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void readXmlFile(String fileName) throws Exception {
		fieldNameMap.clear();
		SAXReader sax = new SAXReader();
		InputStream cfgIn = getConfigurationInputStream(fileName);
		if (cfgIn == null)
			return;
		Document doc = sax.read(cfgIn);
		Iterator iter = doc.getRootElement().elementIterator("field");
		while (iter.hasNext()) {
			String fieldName = ((Element) iter.next()).attribute("name")
					.getText();
			fieldNameMap.put(fieldName, fieldName);
		}
	}

	@SuppressWarnings("rawtypes")
	public Map getFieldNameMap() {
		return fieldNameMap;
	}

	protected static InputStream getConfigurationInputStream(String resource) {
		log.debug("Configuration resource: " + resource);
		InputStream stream = HtmlTagParse.class.getResourceAsStream(resource);
		if (stream == null)
			stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(resource);
		return stream;
	}
}