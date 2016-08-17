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

<title>车辆保单</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carInsuranceAdd.action";
		}else{
			url="<%=path%>/carservice/carInsuranceUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"vehiclePlateId":{
					required: true
				}
			},
			messages: {
				"vehiclePlateId":{
					required: "请选择参保车辆！"
				}
			}
		});
		
		$('.timeselect').datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView: 2,
			autoclose: true,
			format:'yyyy-mm-dd'
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
		window.location.href="<%=path%>/carservice/carInsuranceSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carInsuranceAdd.action";
		}else{
			url="<%=path%>/carservice/carInsuranceUpdate.action";	
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
					window.location.href="<%=path%>/carservice/carInsuranceSearch.action";
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
</script>

</head>
<body style="overflow-y: auto;" class="bxglPage">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="carInsurance.id" id="carInsurance.id"
						value="<ww:property value="carInsurance.id" />">
			<input type="hidden" name="carInsurance.isDiscard" 
						value="<ww:property value="carInsurance.isDiscard" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	<tr>
                <td class="zuo"><span class="xx red">*</span><span>保单编号</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text"  maxlength="30" name="carInsurance.code" id="carInsurance.code"  
                  value="<ww:property value="carInsurance.code" />" />
                </td>  
             </tr>
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>参保车辆</span>:</td>
                <td class="you">
                  <input type="hidden" name="carInsurance.carId" id="carId" value="<ww:property value="carInsurance.carId" />"/>
                  <input name="carInsurance.plateNumber"  id="vehiclePlateId" type="text" readonly 
                  	class="input_size fl" 
                  		value="<ww:property value="carInsurance.plateNumber" />" />
					<input onclick="selectCar();" type="button" value="选择" class="searchinputbut" />
                </td>  
                <td class="zuo1"><span>车型</span>:</td>
                <td class="you1">
                  <input class="input_size fl" type="text" readonly name="carInsurance.vehicleModel" id="modelId"  
                  value="<ww:property value="carInsurance.vehicleModel" />" />
                </td>  
             </tr>
             <tr>
             	<td class="zuo"><span>颜色</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" readonly name="carInsurance.carColor" id="color"  
                  value="<ww:property value="carInsurance.carColor" />" />
                </td> 
                <td class="zuo1"><span>发动机号</span>:</td>
                <td class="you1">
                  <input class="input_size fl" type="text" readonly name="carInsurance.carEngineNumber" id="engineNumber"  
                  value="<ww:property value="carInsurance.carEngineNumber" />" />
                </td> 
             </tr>
             <tr>
                  <td class="zuo"><span class="xx red">*</span><span>投保人</span>:</td>
                  <td class="you">
                    <input class="input_size fl"  maxlength="30" type="text" name="carInsurance.policyHolder"  
                  		value="<ww:property value="carInsurance.policyHolder" />" />
                  </td>   
              </tr>
              <tr>
                  <td class="zuo"><span>投保人联系方式</span>:</td>
                  <td class="you">
                    <input class="input_size fl" type="text"  maxlength="30" name="carInsurance.phPhone"  
                  		value="<ww:property value="carInsurance.phPhone" />" />
                  </td>   
              </tr>
              <tr>
                  <td class="zuo"><span>保险公司出单员</span>:</td>
                  <td class="you">
                    <input class="input_size fl" type="text"  maxlength="30" name="carInsurance.billCreator"  
                  		value="<ww:property value="carInsurance.billCreator" />" />
                  </td>   
                  <td class="zuo1"><span>出单员联系方式</span>:</td>
	              <td class="you1">
	                 <input class="input_size fl"   type="text" maxlength="30" name="carInsurance.billCreatorPhone"  
                  		value="<ww:property value="carInsurance.billCreatorPhone" />" />
	              </td>   
              </tr>
              <tr>
                  <td class="zuo"><span>行驶证注册日期</span>:</td>
                  <td class="you">
                    <input name="carInsurance.firstRegTime" value="<ww:property value="transDate10String(carInsurance.firstRegTime)"/>"
		    			type="text" class="input_size fl timeselect"   />
                  </td> 
                  <td class="zuo"><span>车辆投保日期</span>:</td>
                  <td class="you">
                    <input name="carInsurance.insuranceTime" value="<ww:property value="transDate10String(carInsurance.insuranceTime)"/>"
		    			type="text" class="input_size fl timeselect"   />
                  </td>   
                  
              </tr>
              <tr>
                  <td class="zuo"><span>车辆使用性质</span>:</td>
                  <td class="you">
                    <div class="btt1 fl">
                          <select name="carInsurance.carUseType" id="carInsurance.carUseType" style="top:12px;height:26px;">
							  <ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carUseType',2)" id="data" status="rl">
								  <option value="<ww:property value="id" />"  
								  <ww:if test="carInsurance.carUseType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							  </ww:iterator>
						  </select>
					  </div>
                  </td>   
              </tr>
              <tr>
                  <td class="zuo1"><span>车辆损失险</span>:</td>
                  <td class="you">
                  	<input type="checkbox" name="carInsurance.carLose" 
                  	<ww:if test="carInsurance.carLose=='on'">checked="checked"</ww:if>  />
                  </td>   
                  <td class="zuo1"><span>第三者责任险</span>:</td>
                  <td class="you1">
                  	<input type="checkbox" name="carInsurance.thirdPerson" 
                  	<ww:if test="carInsurance.thirdPerson=='on'">checked="checked"</ww:if>  />
                  </td>     
              </tr>
              <tr>
                  <td class="zuo"><span>车上人员责任险</span>:</td>
                  <td class="you">
                  	<input type="checkbox" name="carInsurance.peopleIncar" 
                  	<ww:if test="carInsurance.peopleIncar=='on'">checked="checked"</ww:if>  />
                  </td>   
              </tr>
              <tr>
                  <td class="zuo"><span>车上货物险</span>:</td>
                  <td class="you">
                  	<input type="checkbox" name="carInsurance.goodsIncar" 
                  	<ww:if test="carInsurance.goodsIncar=='on'">checked="checked"</ww:if>  />
                  </td>   
                  <td class="zuo1"><span>盗抢险</span>:</td>
                  <td class="you1">
                  	<input type="checkbox" name="carInsurance.stolenRescue" 
                  	<ww:if test="carInsurance.stolenRescue=='on'">checked="checked"</ww:if>  />
                  </td>     
              </tr>
              <tr>
                  <td class="zuo"><span>玻璃破碎险</span>:</td>
                  <td class="you">
                  	<input type="checkbox" name="carInsurance.glassBroken" 
                  	<ww:if test="carInsurance.glassBroken=='on'">checked="checked"</ww:if>  />
                  </td>   
                  <td class="zuo1"><span>玻璃类型</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                          <select name="carInsurance.glassType" id="carInsurance.glassType" style="top:12px;height:26px;">
							  <ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carInsuranceGlassType',2)" id="data" status="rl">
								  <option value="<ww:property value="id" />"  
								  <ww:if test="carInsurance.glassType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							  </ww:iterator>
						  </select>
					  </div>
                  </td>     
              </tr>
              <tr>
                  <td class="zuo1"><span>车身划痕险</span>:</td>
                  <td class="you1">
                  	<input type="checkbox" name="carInsurance.scratch" 
                  	<ww:if test="carInsurance.scratch=='on'">checked="checked"</ww:if>  />
                  </td>     
              </tr>
              <tr>
                  <td class="zuo1"><span>不计免赔</span>:</td>
                  <td class="you1">
                  	<input type="checkbox" name="carInsurance.exclDeducSpecial" 
                  	<ww:if test="carInsurance.exclDeducSpecial=='on'">checked="checked"</ww:if>  />
                  </td>   
                  <td class="zuo1"><span>发动机涉水险</span>:</td>
                  <td class="you1">
                  	<input type="checkbox" name="carInsurance.engineWater" 
                  	<ww:if test="carInsurance.engineWater=='on'">checked="checked"</ww:if>  />
                  </td>      
                  
              </tr>
              <tr>
                  <td class="zuo"><span>交强险开始日期</span>:</td>
                  <td class="you">
                  	<input name="carInsurance.trfInsStartDate" value="<ww:property value="transDate10String(carInsurance.trfInsStartDate)"/>"
		    			type="text" class="input_size fl timeselect" id="trfInsStartDate"  />
                  </td>
                  <td class="zuo1"><span>交强险结束日期</span>:</td>
                  <td class="you1">
                  	<input name="carInsurance.trfInsEndDate" value="<ww:property value="transDate10String(carInsurance.trfInsEndDate)"/>"
		    			type="text" class="input_size fl timeselect" id="trfInsEndDate"   />
                  </td> 
              </tr>
              <tr>
                  <td class="zuo"><span>商业保险开始日期</span>:</td>
                  <td class="you">
                  	<input name="carInsurance.commInsStartDate" value="<ww:property value="transDate10String(carInsurance.commInsStartDate)"/>"
		    			type="text" class="input_size fl timeselect" id="commInsStartDate"   />
                  </td>
                  <td class="zuo1"><span>商业保险结束日期</span>:</td>
                  <td class="you1">
                  	<input name="carInsurance.commInsEndDate" value="<ww:property value="transDate10String(carInsurance.commInsEndDate)"/>"
		    			type="text" class="input_size fl timeselect"  id="commInsEndDate"  />
                  </td> 
              </tr>
              
              <tr style="height: 170px">
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carInsurance.remark" style="width: 91%" class="textarea_size"><ww:property value="carInsurance.remark"/></textarea>
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