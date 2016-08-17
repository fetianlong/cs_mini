package com.dearho.cs.core.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dearho.cs.appinterface.sys.pojo.AppToken;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CryptoTools;
import com.dearho.cs.util.TokenUtils;


public class AppParamFilter implements Filter {

	private String[] escapeUrls = {
			"/app/account/acountLogin.action",
			"/app/account/isnotoken.action",
			"/app/account/mobileCodeLogin.action",
			"/app/account/sendCode.action"
			};
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest r, ServletResponse o, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) r;
		HttpServletResponse response = (HttpServletResponse) o;
//		Map<String, String[]> params = request.getParameterMap();
//		CryptoTools des = null;
//		try {
//			des = new CryptoTools(Constants.APP_DES_KEY.getBytes(), Constants.APP_DES_IV.getBytes());
//			if(params != null && params.size() > 0){
//				for(Entry<String, String[]> entry : params.entrySet()){
//					entry.setValue(new String[]{des.decode(entry.getValue()[0])});
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			String url = request.getRequestURI();
			String path = request.getContextPath();
			if (url.startsWith(path)
					&& (url.endsWith(".action") || url.endsWith(".jsp"))) {
				url = url.replaceFirst(path, "");
			}
			if(!check(url,escapeUrls)){
				String data = request.getParameter("data");
				if(data!=null){
					if(TokenUtils.getToken(data) == null){
						response.sendRedirect(request.getContextPath()+"/app/account/isnotoken.action");
						return;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}


	private boolean check(String element, String[] elements) {
		for (String e : elements) {
			if (element.endsWith(e)) {
				return true;
			}
		}
		return false;
	}
}
