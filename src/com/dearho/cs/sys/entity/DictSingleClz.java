package com.dearho.cs.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.DictGroup;

public class DictSingleClz implements Serializable{

	private static final long serialVersionUID = -1874759535716261743L;
	
	private List<DictGroup> dictGroups;
	private List<Dict> dicts;
	
	private static DictSingleClz dictSingleClz;
	private DictSingleClz(){
	}
	public static DictSingleClz getDictSingleClz(){
		if(dictSingleClz == null){
			dictSingleClz = new DictSingleClz();
		}
		return dictSingleClz;
	}
	public List<DictGroup> getDictGroups() {
		return dictGroups;
	}
	public void setDictGroups(List<DictGroup> dictGroups) {
		this.dictGroups = dictGroups;
	}
	public List<Dict> getDicts() {
		return dicts;
	}
	public void setDicts(List<Dict> dicts) {
		this.dicts = dicts;
	}
	

	

	
	
}
