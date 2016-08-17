package com.dearho.cs.sys.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.sys.entity.DictSingleClz;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictService;

public class DictUtil {
	
	static DictSingleClz dictSingleClz = DictSingleClz.getDictSingleClz();
	public static WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
	
	private static DictService dictService;
	
	private static void check(){
		dictService = (DictService)wac.getBean("dictService");
		if(dictSingleClz.getDicts() == null || dictSingleClz.getDictGroups() == null){
			dictService.reloadDictEntity();
		}
	}
	public static List<Dict> getDictsByGroupId(String groupId){
		check();
		List<Dict> resultDicts = new ArrayList<Dict>();
		for (Dict dict : dictSingleClz.getDicts()) {
			if(dict.getGroupId().equals(groupId)){
				resultDicts.add(dict);
			}
		}
		return resultDicts;
	}
	
	public static List<Dict> getDictsByGroupCode(String groupCode){
		check();
		List<Dict> resultDicts = new ArrayList<Dict>();
		String groupId = null;
		for (DictGroup dictGroup : dictSingleClz.getDictGroups()) {
			if(dictGroup.getCode().equals(groupCode)){
				groupId = dictGroup.getId();
			}
		}
		for (Dict dict : dictSingleClz.getDicts()) {
			if(dict.getGroupId().equals(groupId)){
				resultDicts.add(dict);
			}
		}
		return resultDicts;
	}
	
	public static Map<String,String> getDictMapByGroupCode(String groupCode){
		check();
		Map<String,String> resultDictMap = new HashMap<String,String>();
		String groupId = null;
		for (DictGroup dictGroup : dictSingleClz.getDictGroups()) {
			if(dictGroup.getCode().equals(groupCode)){
				groupId = dictGroup.getId();
			}
		}
		for (Dict dict : dictSingleClz.getDicts()) {
			if(dict.getGroupId().equals(groupId)){
				resultDictMap.put(dict.getCode(), dict.getCnName());
			}
		}
		return resultDictMap;
	}
	public static List<Dict> getDictSelectsByGroupCode(String groupCode,int type){
		check();
		String groupId = null;
		for (DictGroup dictGroup : dictSingleClz.getDictGroups()) {
			if(dictGroup.getCode().equals(groupCode)){
				groupId = dictGroup.getId();
			}
		}
		List<Dict> resultDicts = new ArrayList<Dict>();
		Dict d = new Dict();
		switch (type) {
		case 1:
			d.setCnName("全部");
			resultDicts.add(d);
			break;
		case 2:
			d.setCnName("请选择");
			resultDicts.add(d);
			break;
		default:
			break;
		}
		
		for (Dict dict : dictSingleClz.getDicts()) {
			if(dict.getGroupId().equals(groupId)){
				resultDicts.add(dict);
			}
		}
		return resultDicts;
	}
	
	public static Dict getDictById(String id){
		check();
		for (Dict dict : dictSingleClz.getDicts()) {
			if(dict.getId().equals(id)){
				return dict;
			}
		}
		return null;
	}
	
	public static String getCnNameByCode(String groupCode,String dictCode){
		List<Dict> dicts = getDictsByGroupCode(groupCode);
		for (Dict dict : dicts) {
			if(dict.getCode().equals(dictCode)){
				return dict.getCnName();
			}
		}
		return "";
	}

	public static String getCnNameByGroupCodeAndDictId(String groupCode,String dictId){
		List<Dict> dicts = getDictsByGroupCode(groupCode);
		for (Dict dict : dicts) {
			if(dict.getId().equals(dictId)){
				return dict.getCnName();
			}
		}
		return "";
	}
	
	public static Dict getDictByCodes(String groupCode,String dictCode){
		List<Dict> dicts = getDictsByGroupCode(groupCode);
		for (Dict dict : dicts) {
			if(dict.getCode().equals(dictCode)){
				return dict;
			}
		}
		return null;
	}

}
