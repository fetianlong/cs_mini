package com.dearho.cs.util;

import com.dearho.cs.sys.action.AbstractAction;

public class PinYinAction extends AbstractAction{

	private static final long serialVersionUID = 9080905236717219190L;
	
	private String font;
	
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}

	@Override
	public String process() {
		result = Ajax.JSONResult(0, "", Pinyin.getPinYinHeadChar(font));
		return SUCCESS;
	}

}
