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

<title>车辆违章</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carViolationAdd.action";
		}else{
			url="<%=path%>/carservice/carViolationUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"orderId":{
					required: true
				},
				"memberId":{
					required: true
				},
				"vehiclePlateId":{
					required: true
				},
				"carViolation.happenTime":{
					required: true
				},
				"carViolation.money":{
					number: true
				},
				"carViolation.score":{
					number: true
				},
				"carViolation.desc":{
					violationDescSc: true
				}
			},
			messages: {
				"orderId":{
					required: "请选择违章订单!"
				},
				"memberId":{
					required: "请选择违章会员！"
				},
				"vehiclePlateId":{
					required: "请选择违章车辆！"
				},
				"carViolation.happenTime":{
					required: "请选择发生时间！"
				},
				"carViolation.money":{
					number: "请输入数字！"
				},
				"carViolation.score":{
					number: "请输入数字！"
				}
			}
			
		});
		
		val_check_SpecialChar("violationCodeSc,violationDescSc");
		
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
	
	
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	//取消按钮
	function cancel(){
		window.location.href="<%=path%>/carservice/carViolationSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carViolationAdd.action";
		}else{
			url="<%=path%>/carservice/carViolationUpdate.action";	
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
					window.location.href="<%=path%>/carservice/carViolationSearch.action";
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
function selectMember(){
	$.dialog({
		id:'searchMemberDia',
	    title:'会员查询',
		content : 'url:<%=path%>/subscriber/showSubscriberList.action?state=page',
		fixed:true,
		width:840,
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
function selectCar(){
	if($('#carViolation.city').val() == null || $('#carViolation.city').val() == ''){
		$.dialog.tips("请先选择城市！");
		return;
	}
	$.dialog({
		id:'searchCarDia',
	    title:'车辆查询',
		content : 'url:<%=path%>/car/carSearch.action?state=page&query=accident',
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
function showNoticeTime(noticeFlag){
	if(noticeFlag == '0'){
		$('#noticeTimeTrId').hide();
	}
	else if(noticeFlag == '1'){
		$('#noticeTimeTrId').show();
	}
}
</script>

</head>
<body style="overflow-y: auto;">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="carViolation.id" id="carViolation.id"
						value="<ww:property value="carViolation.id" />">
			<input type="hidden" name="carViolation.code" id="carViolation.code"
						value="<ww:property value="carViolation.code" />">			
			<input type="hidden" name="carViolation.isDiscard" 
						value="<ww:property value="carViolation.isDiscard" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>违章订单</span>:</td>
                <td class="you">
                  <input type="hidden" name="carViolation.orderId" id="orderId" value="<ww:property value="carViolation.orderId" />"/>
                  <input name="carViolation.orderCode" id="orderCode" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carViolation.orderCode" />" />
						<input onclick="selectOrder();" type="button" value="选择" class="searchinputbut" />
                </td>  
                <td class="zuo1"><span>违章会员</span>:</td>
                  <td class="you1">
                      <input type="hidden" name="carViolation.memberId" id="memberId" value="<ww:property value="carViolation.memberId" />"/>
<%-- 	                  <input onclick="selectMember();" type="button" value="" class="searchinputbut" /> --%>
	                  <input name="carViolation.memberName" id="memberName" type="text" readonly 
	                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
	                  		value="<ww:property value="carViolation.memberName" />" />
                  </td>   
             </tr>
		  	 <tr>
                <td class="zuo"><span>违章车辆</span>:</td>
                <td class="you">
                  <input type="hidden" name="carViolation.carId" id="carId" value="<ww:property value="carViolation.carId" />"/>
<%--                   <input onclick="selectCar();" type="button" value="" class="searchinputbut" /> --%>
                  <input name="vehiclePlateId"  id="vehiclePlateId" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carViolation.plateNumber" />" />
                </td>  
                <td class="zuo1"><span class="xx red">*</span><span>发生时间</span>:</td>
                  <td class="you1">
                     <input name="carViolation.happenTime" value="<ww:property value="transDate10String(carViolation.happenTime)"/>"
		    			type="text" class="input_size fl timeselect" id="onlineDate"  />
                  </td>   
             </tr>
             
             <tr>
                 <td class="zuo1"><span>违章罚款</span>:</td>
                 <td class="you1">
                   	<input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="10" name="carViolation.money" id="carViolation.money"  
                  	value="<ww:property value="carViolation.money" />" />
                 </td>
                 <td class="zuo1"><span>违章扣分</span>:</td>
	             <td class="you1">
	                <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="2" name="carViolation.score" id="carViolation.score"  
                 	 value="<ww:property value="carViolation.score" />" />
	              </td>   
              </tr>
              <tr>
                <td class="zuo"><span>通知会员时间</span>:</td>
                <td class="you">
                  <input name="carViolation.noticeTime" value="<ww:property value="transDate10String(carViolation.noticeTime)"/>"
		    			type="text" class="input_size fl timeselect"  id="onlineDate"/>
                </td>  
                <td class="zuo1"><span>最迟处理违章时间</span>:</td>
                <td class="you1">
                    <input name="carViolation.offTime" value="<ww:property value="transDate10String(carViolation.offTime)"/>"
		    			type="text" class="input_size fl timeselect" id="onlineDate" />
                </td>   
             </tr>
             <tr>
                  <td class="zuo"><span>处理方式</span>:</td>
                  <td class="you">
                    <div class="btt1 fl">
                      <select name="carViolation.handleType" id="carViolation.handleType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('violationHandleType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carViolation.handleType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>   
                  <td class="zuo1"><span>处理部门</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                      <select name="carViolation.handleDept" id="carViolation.handleDept" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('violationHandleDept',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carViolation.handleDept==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>      
              </tr>
              <tr>
                <td class="zuo"><span>处理时间</span>:</td>
                <td class="you">
                  <input name="carViolation.handleTime" value="<ww:property value="transDate10String(carViolation.handleTime)"/>"
		    			type="text" class="input_size fl timeselect" id="onlineDate" />
                </td>  
                <td class="zuo1"><span>寄送方式</span>:</td>
                <td class="you1">
                    <div class="btt1 fl">
                      <select name="carViolation.sendType" id="carViolation.sendType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('violationSendType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carViolation.sendType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>   
             </tr>
             <tr>
                <td class="zuo"><span>寄送地址</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="carViolation.sendAddress" id="carViolation.sendAddress"  
                  value="<ww:property value="carViolation.sendAddress" />" />
                </td>  
                <td class="zuo1"><span>受理人员</span>:</td>
                <td class="you1">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="carViolation.acceptMember" id="carViolation.acceptMember"  
                  value="<ww:property value="carViolation.acceptMember" />" />
                </td>  
             </tr>
             <tr>
                <td class="zuo"><span>受理标记</span>:</td>
                <td class="you">
                  <div class="btt1 fl">
                      <select name="carViolation.acceptFlag" id="carViolation.acceptFlag" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('violationAccetpType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carViolation.acceptFlag==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>  
                <td class="zuo1"><span>业务状态</span>:</td>
                <td class="you1">
                  <div class="btt1 fl">
                      <select name="carViolation.bizStatus" id="carViolation.bizStatus" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('violationBizStatus',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carViolation.bizStatus==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>  
             </tr>
             
             <tr>
              	<td class="zuo1"><span>违章描述</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carViolation.remark" style="width: 91%" class="textarea_size"><ww:property value="carViolation.remark"/></textarea>
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