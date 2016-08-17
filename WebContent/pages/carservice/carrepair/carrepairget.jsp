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

<title>车辆维修</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carRepairAdd.action";
		}else{
			url="<%=path%>/carservice/carRepairUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"vehiclePlateId":{
					required: true
				},
				"carRepair.memberName":{
					required: true
				},
				"carRepair.repairAddress":{
					required: true
				},
				"carRepair.repairOrderCode":{
					repairOrderCodeSc: true
				},
				"carRepair.repairFee":{
					number: true
				},
				"carRepair.status":{
					required: true
				}
			},
			messages: {
				"vehiclePlateId":{
					required: "请选择维修车辆！"
				},
				"carRepair.memberName":{
					required: "请输入送修人！"
				},
				"carRepair.repairAddress":{
					required: "请选择维修地点！"
				},
				"carRepair.repairFee":{
					number: "请输入数字！"
				},
				"carRepair.status":{
					required: "请选择维修状态！"
				}
			}
			
		});
		
		val_check_SpecialChar("repairCodeSc,repairOrderCodeSc");
		
		var isSettled = '<ww:property value="carRepair.isSettled" />';
		if(isSettled == '0' || isSettled == 0){
			$('#settledTimeTrId').hide();
		}
		else if(isSettled == '1' || isSettled == 1){
			$('#settledTimeTrId').show();
		}
		
		$('.timeselect').datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView: 2,
			autoclose: true,
			format:'yyyy-mm-dd',
			endDate:new Date()
		});
		$('.timeselect').attr('readonly','readonly');
	});
	function showSettleTime(settleFlag){
		if(settleFlag == '0'){
			$('#settledTimeTrId').hide();
		}
		else if(settleFlag == '1'){
			$('#settledTimeTrId').show();
		}
	}
	
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	//取消按钮
	function cancel(){
		window.location.href="<%=path%>/carservice/carRepairSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carRepairAdd.action";
		}else{
			url="<%=path%>/carservice/carRepairUpdate.action";	
		}
		var re=isValid();
		if(re){
			$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
		}
	}
	function r_saveCar(data){
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/carservice/carRepairSearch.action";
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
function selectCar(){
	$.dialog({
		id:'searchCarDia',
	    title:'车辆查询',
		content : 'url:<%=path%>/car/carSearch.action?state=page&query=repair',
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
function selectOrder(){
	$.dialog({
		id:'searchOrderDia',
	    title:'订单查询',
		content : 'url:<%=path%>/orders/ordersSearch.action?state=page&query=accident',
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
function selectUser(){
	$.dialog({
		id:'searchUserDia',
	    title:'送修人查询',
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
</script>

</head>
<body>
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="carRepair.id" id="carRepair.id"
						value="<ww:property value="carRepair.id" />">
			<input type="hidden" name="carRepair.isDiscard" 
						value="<ww:property value="carRepair.isDiscard" />">
			<input type="hidden" name="carRepair.code" 
						value="<ww:property value="carRepair.code" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	 <tr>
		  	 	<td class="zuo"><span>事故订单</span>:</td>
                <td class="you">
                  <input type="hidden" name="carRepair.orderId" id="orderId" value="<ww:property value="carRepair.orderId" />"/>
                  
                  <input name="orderCode" id="orderCode" type="text"
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carRepair.orderCode" />" />
						<input onclick="selectOrder();" type="button" value="选择" style="margin-right: 60px" class="searchinputbut" />
                </td> 
                <td class="zuo1"><span class="xx red">*</span><span>维修车辆</span>:</td>
                <td class="you1">
                  <input type="hidden" name="carRepair.carId" id="carId" value="<ww:property value="carRepair.carId" />"/>
                  <input name="vehiclePlateId"  id="vehiclePlateId" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carRepair.plateNumber" />" />
					<input onclick="selectCar();" type="button" value="选择" style="margin-right: 84px" class="searchinputbut" />
                </td>  
             </tr>
             <tr>
		  	 	<td class="zuo"><span class="xx red">*</span><span>送修人</span>:</td>
                <td class="you">
<%--                       <input type="hidden" name="carRepair.memberId" id="userId" value="<ww:property value="carRepair.memberId" />"/> --%>
<%-- 	                  <input id="memberIdSearchInp" onclick="selectUser();" type="button" value="" class="searchinputbut" /> --%>
<!-- 	                  <input name="userName" id="userName" type="text" readonly="readonly"  -->
<!-- 	                  	class="input_size fl" style="top: 0;left: 0px;position:relative;" -->
<%-- 	                  		value="<ww:property value="carRepair.memberName" />" /> --%>
					<input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" 
                  		id="carRepair.memberName" name="carRepair.memberName" 
                 		value="<ww:property value="carRepair.memberName" />" />
                </td> 
                <td class="zuo1"><span class="xx red">*</span><span>维修地点</span>:</td>
                <td class="you1">
               		<div class="btt1 fl">
                      <select name="carRepair.repairAddress" id="carRepair.repairAddress" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('repairAddress',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carRepair.repairAddress==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>  
             </tr>
             <tr>
                  <td class="zuo1"><span>送厂日期</span>:</td>
                  <td class="you1">
                     <input name="carRepair.sendTime" value="<ww:property value="transDate10String(carRepair.sendTime)"/>"
			    		type="text" class="input_size fl timeselect" />
                  </td>   

                   <td class="zuo1"><span>出厂日期</span>:</td>
	               <td class="you1">
	                 <input name="carRepair.outTime" value="<ww:property value="transDate10String(carRepair.outTime)"/>"
			    		type="text" class="input_size fl timeselect"  />
	               </td>   
              </tr>
              <tr>
                <td class="zuo"><span>维修单号</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" 
                  id="carRepair.repairOrderCode" name="carRepair.repairOrderCode" 
                  value="<ww:property value="carRepair.repairOrderCode" />" />
                </td>  
                <td class="zuo1"><span>维修费用</span>:</td>
                  <td class="you1">
                    <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30"
                    id="carRepair.repairFee" name="carRepair.repairFee" 
                    value="<ww:property value="carRepair.repairFee" />" />
                  </td>   
             </tr>
             <tr style="height: 150px">
              	<td class="zuo1"><span>维修描述</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carRepair.repairDesc" style="width: 91%" class="textarea_size"><ww:property value="carRepair.repairDesc"/></textarea>
                  </td>  
                 
              </tr>
             <tr>
                <td class="zuo"><span>是否结算</span>:</td>
                <td class="you">
                  <div class="btt1 fl">
                      <select name="carRepair.isSettled" onchange="showSettleTime(this.value);"  style="top:12px;height:26px;">
                        <option value="0" <ww:if test="carRepair.isSettled==0">selected="selected"</ww:if>>否</option>
                       	<option value="1" <ww:if test="carRepair.isSettled==1">selected="selected"</ww:if>>是</option>
					  </select>
				  </div>
                </td>  
                 
              </tr>
              <tr id="settledTimeTrId">
              	<td class="zuo"><span>结算日期</span>:</td>
                  <td class="you">
                    <input name="carRepair.settleTime" value="<ww:property value="transDate10String(carRepair.settleTime)"/>"
			    		type="text" class="input_size fl timeselect"   />
                  </td>  
              </tr>
             <tr>
                <td class="zuo"><span class="xx red">*</span><span>维修状态</span>:</td>
                <td class="you">
                  <div class="btt1 fl">
                      <select name="carRepair.status" id="carRepair.status" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('repairStatus',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carRepair.status==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>  
             </tr>
              <tr>
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carRepair.remark" style="width: 91%;margin-top:12px " class="textarea_size"><ww:property value="carRepair.remark"/></textarea>
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