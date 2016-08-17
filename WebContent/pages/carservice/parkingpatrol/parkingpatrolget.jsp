<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>网点巡检</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		parkingPatrolId = id;
		var url="";
		if (parkingPatrolId == "" || parkingPatrolId== "undefined"){
			url="<%=path%>/carservice/parkingPatrolAdd.action";
		}else{
			url="<%=path%>/carservice/parkingPatrolUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"parkingPatrol.code":{
					required: true,
					parkingPatrolCodeSc:true
				},
				"parkingPatrol.city": {
					required: true
				},
				"parkingName":{
					required: true
				},
				"userName":{
					required: true
				},
				"parkingPatrol.patrolTime":{
					required: true
				}
			},
			messages: {
				"parkingPatrol.code":{
					required: "请输入编码！"
				},
				"parkingPatrol.city": {
					required: "请选择城市！"
				},
				"parkingName":{
					required: "请选择网点！"
				},
				"userName":{
					required: "请选择巡检人！"
				},
				"parkingPatrol.patrolTime":{
					required: "请选择巡检时间！"
				}
			}
			
		});
		
		val_check_SpecialChar("parkingPatrolCodeSc");
		
		$('#xjrq').datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView:2,
			autoclose: true,
			format:'yyyy-mm-dd',
			endDate:new Date()
		});
	});
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	//取消按钮
	function cancel(){
		window.location.href="<%=path%>/carservice/parkingPatrolSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var url="";
		if (parkingPatrolId == "" || parkingPatrolId == "undefined"){
			url="<%=path%>/carservice/parkingPatrolAdd.action";
		}else{
			url="<%=path%>/carservice/parkingPatrolUpdate.action";	
		}
		var re=isValid();
		if(re){
			$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
		}
	}
	function r_saveCar(data){
		switch(data.result){
			case 0:
				alertok("保存成功！", function(){
					window.location.href="<%=path%>/carservice/parkingPatrolSearch.action";
			    });
				break;
			case 1:
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
	}
function selectParking(){
	$.dialog({
		id:'searchParkingDia',
	    title:'网点查询',
		content : 'url:<%=path%>/place/branchDotSearch.action?state=page',
		fixed:true,
		width:940,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
function selectUser(){
	$.dialog({
		id:'searchUserDia',
	    title:'巡检人查询',
		content : 'url:<%=path%>/user/userSearch.action?state=page',
		fixed:true,
		width:740,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
var parkingPatrolId;
function addCarPatorl(){
	if(parkingPatrolId == null || parkingPatrolId == ""|| parkingPatrolId == "undefined"){
		alertconfirm("车辆巡检前需要先保存网点巡检单，是否保存？",function (){
			
			url="<%=path%>/carservice/parkingPatrolAdd.action";
			var re=isValid();
			if(re){
				showLoading();
				$.post(url,$("#eform").serialize(),function(data){
					switch(data.result){
					case 0:
						hideLoading();
						parkingPatrolId = data.msg;
						window.location.href="<%=path%>/carservice/carPatrolGet.action?parkingPatrolId="+parkingPatrolId;
						break;
					case 1:
						alerterror(data.msg);
						break;
					case 9:
						document.location = "doError.action";
						break;
				}
				},'json').error(requestError);
			}
			
		});
	}
	else{
		window.location.href="<%=path%>/carservice/carPatrolGet.action?parkingPatrolId="+parkingPatrolId;
	}
}
</script>

</head>
<body style="overflow-y:auto;">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="parkingPatrol.id" id="parkingPatrol.id"
						value="<ww:property value="parkingPatrol.id" />">
			<input type="hidden" name="parkingPatrol.isDiscard" 
						value="<ww:property value="parkingPatrol.isDiscard" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	<tr>
                <td class="zuo"><span class="xx red">*</span><span>编号</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="parkingPatrol.code" id="parkingPatrol.code"  
                  value="<ww:property value="parkingPatrol.code" />" />
                </td>  
                <td class="zuo1"><span class="xx red">*</span><span>城市</span>:</td>
                  <td class="you1">
                  	<div class="btt1 fl">
	                      <select name="parkingPatrol.city" id="parkingPatrol.city" style="top:12px;height:26px;">
								<option value="">请选择</option>
								<ww:iterator value="getCitys()" id="data" status="rl">
									<option value="<ww:property value="id" />"  <ww:if test="parkingPatrol.city==id">selected=true</ww:if> ><ww:property value="name" /></option>	
								</ww:iterator>
							</select>
						</div>
                  </td>   
             </tr>
		  	 <tr>
		  	 	<td class="zuo"><span class="xx red">*</span><span>网点</span>:</td>
                <td class="you">
                  <input type="hidden" name="parkingPatrol.dotId" id="branchDotId" value="<ww:property value="parkingPatrol.dotId" />"/>
                  <input name="branchDotName" id="branchDotName" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="parkingPatrol.dotName" />" />
					<input onclick="selectParking();" type="button" value="选择" class="searchinputbut" />
                </td> 
                <td class="zuo1"><span class="xx red">*</span><span>巡检人</span>:</td>
                <td class="you1">
                  <input type="hidden" name="parkingPatrol.userId" id="userId" value="<ww:property value="parkingPatrol.userId" />"/>
                  <input name="userName"  id="userName" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="parkingPatrol.userName" />" />
					<input onclick="selectUser();" type="button" value="选择" class="searchinputbut" />
                </td>  
             </tr>
             <tr>
                  <td class="zuo1"><span class="xx red">*</span><span>巡检日期</span>:</td>
                  <td class="you1">
                     <input name="parkingPatrol.patrolTime" id="xjrq" value="<ww:property value="transDate10String(parkingPatrol.patrolTime)"/>"
			    		type="text" class="input_size fl" readonly="readonly"  />
                  </td>   
              </tr>
          </table>
          <div class="nra"><a class="btn btn-default add_btn" onclick="addCarPatorl();" target="_blank">车辆巡检</a></div>
          <div>
         	<iframe style="width:100%;border: 0" src="<%=path%>/carservice/carPatrolSearch.action?state=inparking&carPatrol.parkingPatrolId=<ww:property value="parkingPatrol.id" />"></iframe>
          </div>
           <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
              <tr style="height:170px">
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="parkingPatrol.remark" style="width: 91%" class="textarea_size"><ww:property value="parkingPatrol.remark"/></textarea>
                  </td>  
                 
              </tr>
              <tr></tr>
		  	<tr>
                  <td colspan="4">
                      <div class="btt">
                         <div class="sbtn fl" onclick="sub();">提&nbsp;&nbsp;交</div>
                         <div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消</div>
                      </div>
                  </td>
             </tr>
			</table>
		</form>
	</div>
</body>
</html>