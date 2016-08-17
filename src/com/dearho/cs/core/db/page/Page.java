package com.dearho.cs.core.db.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.dearho.cs.core.configuration.Configuration;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 * @param <T>
 */
public class Page<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ORDER_DESC = 0;
	public static final int ORDER_ASC = 1;
	public static final String MAPPING_FILE = "/hibernate.cfg.xml";
	private int pageSize = Configuration.getInstance().getSysPageSize();
	private int currentPage = 1;
	private int totalRows;
	private int totalPages;
	private List<T> results;
	private List<Serializable> mResults;
	private int orderFlag;
	private String countField;
	private String orderString;
	private static int PAGESIZE = 10;
	
	public Page(){
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.pageSize);
		sb.append(",");
		sb.append(this.currentPage);
		sb.append(",");
		sb.append(this.orderFlag);
		sb.append(",");
		sb.append(this.orderString);
		sb.append(",");
		return sb.toString();
	}

	public void finalize() throws Throwable {
		gc();
		super.finalize();
	}

	public void gc() {
		if (this.results != null) {
			this.results.clear();
			this.results = null;
		}
		this.orderString = null;
	}

	public void initPage(int totalRows, int pageSize) {
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		initPageInfo();
	}

	public void initPage(Query query, Query queryRows) {
		if (this.pageSize == 0)
			this.pageSize = PAGESIZE;
		getQueryRows(queryRows);
		this.results = getQueryResult(query);
	}
	
	public void initMPage(Query query, Query queryRows) {
		if (this.pageSize == 0)
			this.pageSize = PAGESIZE;
		getQueryRows(queryRows);
		this.mResults = getMQueryResult(query);
	}

	@SuppressWarnings("unchecked")
	private List<T> getQueryResult(Query query) {
		List<T> listResult;
		if (this.pageSize == -1) {
			listResult = query.list();
			this.totalRows = listResult.size();
			this.totalPages = 1;
			this.currentPage = 1;
		} else {
			if (this.currentPage < 1)
				this.currentPage = 1;
			listResult = query
					.setFirstResult((this.currentPage - 1) * this.pageSize)
					.setMaxResults(this.pageSize).list();
		}
		return listResult;
	}
	
	@SuppressWarnings("unchecked")
	private List<Serializable> getMQueryResult(Query query) {
		List<Serializable> listResult;
		if (this.pageSize == -1) {
			listResult = query.list();
			this.totalRows = listResult.size();
			this.totalPages = 1;
			this.currentPage = 1;
		} else {
			if (this.currentPage < 1)
				this.currentPage = 1;
			listResult = query
					.setFirstResult((this.currentPage - 1) * this.pageSize)
					.setMaxResults(this.pageSize).list();
		}
		return listResult;
	}

	private void getQueryRows(Query query) {
		try {
			this.totalRows = Integer.parseInt(query.list().get(0).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			this.totalRows = 0;
		}
		initPageInfo();
	}

	public void initPageInfo() {
		this.totalPages = ((this.totalRows + this.pageSize - 1) / this.pageSize);
		this.totalPages = (this.totalPages < 1 ? 1 : this.totalPages);
		this.currentPage = (this.currentPage > this.totalPages ? this.totalPages
				: this.currentPage);
	}

	public boolean isNextPage() {
		return this.currentPage < this.totalPages;
	}

	public boolean isPreviousPage() {
		return this.currentPage > 1;
	}

	public String getCountField() {
		return this.countField;
	}

	public void setCountField(String countField) {
		this.countField = countField;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@SuppressWarnings("rawtypes")
	public List getResults() {
		return this.results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getOrderFlag() {
		return this.orderFlag;
	}

	public void setOrderFlag(int orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getOrderString() {
		return this.orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public String getOrderByString() {
		String sRet = "";
		if ((this.orderString != null) && (!this.orderString.equals("")))
			sRet = " order by " + this.orderString
					+ (this.orderFlag == 1 ? " " : " desc ");
		return sRet;
	}

	public String getOrderByString2() {
		String sRet = "";
		if ((this.orderString != null) && (!this.orderString.equals("")))
			sRet = ", " + this.orderString
					+ (this.orderFlag == 1 ? " " : " desc ");
		return sRet;
	}

	public String getPageSplit() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"a_style\">");
		if ((this.pageSize == -1) || (this.totalPages == 1)) {
			sb.append("<li>共1页 记录总数： <b>" + this.totalRows + " </b>条</li>");
		} else {
			sb.append("<li><a href='javascript:skipToPage(1)'>首页</a></li>　");
			if (isPreviousPage())
				sb.append("<li><a href=\"javascript:skipToPage("
						+ (this.currentPage - 1) + ")\">上一页</a></li>　");
			if (isNextPage())
				sb.append("<li><a href=\"javascript:skipToPage("
						+ (this.currentPage + 1) + ")\">下一页</a></li>　");
			sb.append("<li><a href=\"javascript:skipToPage(" + this.totalPages
					+ ")\">尾页</a></li>　");
			sb.append("<li style=\"margin-left:10px;\">到<input type=\"text\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page.currentPage\" id=\"page.currentPage\" value=\""
					+ this.currentPage + "\"/>");
			sb.append("页</li> <li><a class=\"qw\" href=\"#\" onclick=\"commonJump()\" name=\"goto\">前往</a></li><li> 共"
					+ this.totalPages + "页</li><li style=\"margin-left:10px;\"> 每页");
			sb.append("<input onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page.pageSize\" id=\"page.pageSize\" value=\""
					+ this.pageSize + "\"/>");
			sb.append("条</li> <li style=\"margin-left:10px;\">记录总数：<b>" + this.totalRows + "</b>条</li>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String getPageSplit(String func) {
		if (func == null || func.equals(""))
			func = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"a_style\">");
		if ((this.pageSize == -1) || (this.totalPages == 1)) {
			sb.append("<li>共1页 记录总数： <b>" + this.totalRows + " </b>条</li>");
		} else {
			sb.append("<li><a href=\"javascript:skipToPage(1"
					+ (func.equals("") ? "" : ("," + func)) + ")\">首页</a>　</li>");
			if (isPreviousPage())
				sb.append("<li><a href=\"javascript:skipToPage("
						+ (this.currentPage - 1)
						+ (func.equals("") ? "" : ("," + func))
						+ ")\">上一页</a></li>　");
			if (isNextPage())
				sb.append("<li><a href=\"javascript:skipToPage("
						+ (this.currentPage + 1)
						+ (func.equals("") ? "" : ("," + func))
						+ ")\">下一页</a></li>　");
			sb.append("<li><a href=\"javascript:skipToPage(" + this.totalPages
					+ (func.equals("") ? "" : ("," + func)) + ")\">尾页</a></li>　");
			sb.append("<li style=\"margin-left:10px;\">到<input onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page.currentPage\" id=\"page.currentPage\" value=\""
					+ this.currentPage + "\"/>");
			sb.append("页</li><li> <a class=\"qw\" href=\"#\" onclick=\"commonJump("+ (func.equals("") ? "" : func)
					+ ")\" name=\"goto\">前往</a></li> <li>共"
					+ this.totalPages + "页</li> <li style=\"margin-left:10px;\">每页");
			sb.append("<input onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page.pageSize\" id=\"page.pageSize\" value=\""
					+ this.pageSize + "\"/>");
			sb.append("条</li> <li style=\"margin-left:10px;\">记录总数：<b>" + this.totalRows + "</b>条</li>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String getPageSplit2(String func) {
		if (func == null || func.equals(""))
			func = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"a_style\">");
		if ((this.pageSize == -1) || (this.totalPages == 1)) {
			sb.append("<li>共1页 记录总数： <b>" + this.totalRows + " </b>条</li>");
		} else {
			sb.append("<li><a href=\"javascript:skipToPage2(1"
					+ (func.equals("") ? "" : ("," + func)) + ")\">首页</a></li>　");
			if (isPreviousPage())
				sb.append("<li><a href=\"javascript:skipToPage2("
						+ (this.currentPage - 1)
						+ (func.equals("") ? "" : ("," + func))
						+ ")\">上一页</a></li>　");
			if (isNextPage())
				sb.append("<li><a href=\"javascript:skipToPage2("
						+ (this.currentPage + 1)
						+ (func.equals("") ? "" : ("," + func))
						+ ")\">下一页</a></li>　");
			sb.append("<li><a href=\"javascript:skipToPage2(" + this.totalPages
					+ (func.equals("") ? "" : ("," + func)) + ")\">尾页</a></li>　");
			sb.append("<li style=\"margin-left:10px;\">到<input onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page2.currentPage\" id=\"page2.currentPage\" value=\""
					+ this.currentPage + "\"/>");
			sb.append("页</li><li><a class=\"qw\" href=\"#\" onclick=\"commonJump2("
					+ (func.equals("") ? "" : func)
					+ ")\" name=\"goto\">前往</a></li> <li>共"
					+ this.totalPages + "页</li> <li style=\"margin-left:10px;\">每页");
			sb.append("<input onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page2.pageSize\" id=\"page2.pageSize\" value=\""
					+ this.pageSize + "\"/>");
			sb.append("条</li> <li style=\"margin-left:10px;\"> 记录总数：<b>" + this.totalRows + "</b>条</li>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String getPageInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"a_style\">");
		if ((this.pageSize == -1) || (this.totalPages == 1)) {
			sb.append("<li>共1页 记录总数： <b>" + this.totalRows + " </b>条</li>");
		} else {
			sb.append("<li><a href='javascript:skipToPage(1)'>首页</a></li>　");
			if (isPreviousPage())
				sb.append("<li><a href=\"javascript:skipToPage("
						+ (this.currentPage - 1) + ")\">上一页</a></li>　");
			if (isNextPage())
				sb.append("<li><a href=\"javascript:skipToPage("
						+ (this.currentPage + 1) + ")\">下一页</a></li>　");
			sb.append("<li><a href=\"javascript:skipToPage(" + this.totalPages
					+ ")\">尾页</a></li>　");
			sb.append("<li style=\"margin-left:10px;\">到<input onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page.currentPage\" id=\"page.currentPage\" value=\""
					+ this.currentPage + "\"/>");
			sb.append("页</li> <li><a class=\"qw\" href=\"#\">前往</a></li> <li> 共" + this.totalPages
					+ "页</li> <li style=\"margin-left:10px;\">每页");
			sb.append("<input size=\"3\" maxlength=\"6\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\"");
			sb.append(" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\"");
			sb.append(" name=\"page.pageSize\" id=\"page.pageSize\" value=\""
					+ this.pageSize + "\"/>");
			sb.append("条</li> <li style=\"margin-left:10px;\">记录总数：<b>" + this.totalRows + "</b>条</li>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String getPageInfo(int type, String fun) {
		return getPageInfo(type, fun, "");
	}

	public static boolean isEmpty(String string) {
		return (string == null) || (string.length() == 0);
	}

	public String getPageInfo(int type, String fun, String objName) {
		if (isEmpty(fun))
			fun = "jump";
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=inner id=pageTop>");
		if (this.totalPages == 1) {
			sb.append("共1页　总计<b>" + this.totalRows + "</b>条");
		} else {
			if (this.currentPage > 1)
				sb.append("<a href=javascript:" + fun
						+ "ToPage(1)>首页</a>&nbsp;&nbsp;<a href=javascript:"
						+ fun + "ToPage(" + (this.currentPage - 1)
						+ ")>上一页</a>&nbsp;&nbsp;");
			if (this.currentPage < this.totalPages)
				sb.append("<a href=javascript:" + fun + "ToPage("
						+ (this.currentPage + 1)
						+ ")>下一页</a>&nbsp;&nbsp;<a href=javascript:" + fun
						+ "ToPage(" + this.totalPages + ")>尾页</a>　");
			sb.append("第&nbsp;&nbsp;<input size=3 maxlength=6 name=cp" + type
					+ " id=" + objName + "cp" + type + " value="
					+ this.currentPage + ">&nbsp;&nbsp;页　<a href=javascript:"
					+ fun + "ToPage(document.getElementById('" + objName + "cp"
					+ type + "').value) name=goto" + type + ">Go</a>");
			sb.append(" 共" + this.totalPages + "页 每页" + this.pageSize
					+ "条 总计<b>" + this.totalRows + "</b>条");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String getPageTop(String fun) {
		return getPageInfo(1, fun);
	}

	public String getPageBottom(String fun) {
		return getPageInfo(2, fun);
	}

	public String getObjPageTop(String fun, String objName) {
		return getPageInfo(1, fun, objName);
	}

	public String getObjPageBottom(String fun, String objName) {
		return getPageInfo(2, fun, objName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initPage(List<T> list) {
		if (this.pageSize == 0) {
			this.pageSize = Configuration.getInstance().getSysPageSize();
		}
		results = new ArrayList();
		this.totalRows = list.size();
		if (currentPage < 1)
			currentPage = 1;
		for (int i = 0; i < list.size(); i++) {
			if (i >= (currentPage - 1) * pageSize
					&& i <= currentPage * pageSize) {
				results.add(list.get(i));
			}
			if (i > currentPage * pageSize)
				break;
		}
		initPageInfo();
	}

	public List<Serializable> getmResults() {
		return mResults;
	}

	public void setmResults(List<Serializable> mResults) {
		this.mResults = mResults;
	}
	
}