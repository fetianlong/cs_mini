package com.dearho.cs.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class HtmlInterceptor extends AroundInterceptor {
	protected void after(ActionInvocation dispatcher, String result)
			throws Exception {
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void before(ActionInvocation invocation) throws Exception {
		if (!(invocation.getAction() instanceof HtmlParameter)) {
			Map parameters = ActionContext.getContext().getParameters();
			if (this.log.isDebugEnabled()) {
				this.log.debug("Setting params " + parameters);
			}
			Map tmpMap = new HashMap();
			ActionContext invocationContext = invocation.getInvocationContext();
			try {
				invocationContext.put("report.conversion.errors", Boolean.TRUE);
				if (parameters != null) {
					Map fieldMap = HtmlTagParse.getInstance().getFieldNameMap();
					for (Iterator iterator = parameters.keySet().iterator(); iterator
							.hasNext();) {
						String key = (String) iterator.next();
						String[] value = (String[]) parameters.get(key);
						for (int i = 0; i < value.length; i++) {
							if ((fieldMap.size() <= 0)
									|| (fieldMap.get(key) != null))
								continue;
							value[i] = tagReplace(value[i].trim());
						}

						tmpMap.put(key, value);
					}
					ActionContext.getContext().setParameters(tmpMap);
				}
			} finally {
				invocationContext
						.put("report.conversion.errors", Boolean.FALSE);
			}
		}
	}

	public static String tagReplace(String sSource) {
		if ((sSource == null) || (sSource.equals("")))
			return "";
		Pattern p = null;
		Matcher m = null;
		String s = null;
		String sTmp = null;
		StringBuffer sb = null;
		p = Pattern.compile("(\\n|\\r|<|>)", 2);
		m = p.matcher(sSource);
		sb = new StringBuffer();
		while (m.find()) {
			sTmp = m.group().toLowerCase();
			if (sTmp.equals("\n"))
				s = "<br/>";
			else if (sTmp.equals("\r"))
				s = "";
			else if (sTmp.equals("<"))
				s = "&lt;";
			else if (sTmp.equals(">"))
				s = "&gt;";
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		return sb.toString();
	}
}