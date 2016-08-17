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

<title>车辆管理</title>

<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/car/carAdd.action";
		}else{
			url="<%=path%>/car/carUpdate.action";	
		}
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"car.vehiclePlateId": {
					required: true,
					validatecar:true
				},
				"car.vin":{
					required: true,
					rangelength:[17,17],
					validateVin:true,
					carVinSc:true
				},
				"modelId":{
					required: true
				},
				"car.color":{
					maxlength:15,
					carColorSc:true
				},
				"car.bizState":{
					required: true
				},
				"branchDotId":{
					required: true
				}
			},
			messages: {
				"car.vehiclePlateId": {
					required: "请输入车牌号！"
				},
				"car.vin":{
					required: "请输入车架号！",
					rangelength:"车架号为17位！"
				},
				"car.modelId":{
					required: "请选择车辆型号！"
				},
				"modelId":{
					required: "请选择车型！"
				},
				"car.color":{
					maxlength:"颜色最长为15个字符！"
				},
				"car.bizState":{
					required: "请选择车辆状态！"
				},
				"branchDotId":{
					required: "请选择所属网点！"
				}
			}
		});
		
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
		
		val_check_Plate("validatecar");
		val_check_MustNumOrStr("validateVin");
		val_check_SpecialChar("carVinSc,carColorSc");
	});
	
	
	
	function r_saveCar(data){
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/car/carSearch.action";
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
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/car/carAdd.action";
		}else{
			url="<%=path%>/car/carUpdate.action";	
		}
		var re=isValid();
		if(re){
			$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
		}
	}
	function queryModel(id){
		if(id!=""){
			var strs=id.split(",");
			$("#modelId").val(strs[0]);
			$("#level").val(strs[1]);
			$("#engineType").val(strs[2]);
			$("#seatingNum").val(strs[3]);
			$("#luggageNum").val(strs[4]);
			$("#casesNum").val(strs[5]);
			$("#gearboxType").val(strs[6]);
		}else{
			$("#modelId").val("");
			$("#level").val("");
			$("#engineType").val("");
			$("#seatingNum").val("");
			$("#luggageNum").val("");
			$("#casesNum").val("");
			$("#gearboxType").val("");
		}
	}
	//取消按钮的事件
	function cancel(){
		window.location.href="<%=path%>/car/carSearch.action";
	}
	var api = frameElement.api, W = api.opener;
	function selectDot(){
		$.dialog({
			id:'searchDotDialog',
		    title:'查询网点',
			content : 'url:<%=path%>/place/branchDotSearch.action?state=page',
			fixed:true,
			width:740,
			height:450,
			resize:false,
			max: false,
		    min: false,
		    lock: true,
		    parent:api,
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
		<form action="" method="post" id="eform" name="eform">
        	 <input type="hidden" name="car.id" value="<ww:property value="car.id" />" />
        	 <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
                 <tr>
                    <td class="zuo"><span class="xx red">*</span><span>车牌号</span>:</td>
                    <td class="you">
                      <input class="input_size fl" type="text"  name="car.vehiclePlateId" maxlength="7" id="car.vehiclePlateId"  value="<ww:property value="car.vehiclePlateId" />" />
                    </td>  
                    <td class="zuo1"><span class="xx red">*</span><span>车辆型号 </span>:</td>
                    <td class="you1">
                      <div class="btt1 fl">
                      	 <select id="car.modelId" name="modelId" onchange="queryModel(this.options[this.options.selectedIndex].value);" style="top:17px">
							<option value="">请选择</option>
							<ww:iterator value="carVehicleModels">
								<option  value="<ww:property value="toString4CarAdd()"/>" <ww:if test="car.modelId.equals(id)">selected=true</ww:if>>
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',brand)"/>-<ww:property value="name"/>
								</option>
							</ww:iterator>
						 </select>
						 <input type="hidden" name="car.modelId" id="modelId" value="<ww:property value="car.modelId" />" />
                      </div>
                    </td>   
                 </tr>
                 <tr>
                      <td class="zuo"><span class="xx red">*</span><span>车架号</span>:</td>
                      <td class="you">
                        <input class="input_size fl" maxlength="17"    type="text" name="car.vin" id="car.vin"  value="<ww:property value="car.vin" />" />
                      </td>  
                      <td class="zuo1"><span>车辆等级</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="level" disabled="disabled" 
                        value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('1',car.carVehicleModel.level)" />" />
                      </td>   
                   </tr>
                  <tr>
<!--                       <td class="zuo"><span>昵称</span>:</td> -->
<!--                       <td class="you"> -->
<%--                         <input class="input_size fl" maxlength="30"   type="text" name="car.nickName" value="<ww:property value="car.nickName"/>" /> --%>
<!--                       </td>   -->
                      <td class="zuo"><span>发动机号</span>:</td>
                      <td class="you">
                        <input class="input_size fl" maxlength="30"   type="text" name="car.engineNumber" value="<ww:property value="car.engineNumber"/>" />
                      </td>  
                      <td class="zuo1"><span>动力类型</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="engineType"  disabled="disabled" 
                        value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('2',car.carVehicleModel.engineType)" />" />
                      </td>   
                   </tr>
                   <tr>
                      <td class="zuo"><span>车辆颜色 </span>:</td>
                      <td class="you">
                        <input class="input_size fl" maxlength="15"   type="text" name="car.color" id="car.color" value="<ww:property value="car.color" />"/>
                      </td>  
                      <td class="zuo1"><span>座位数</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="seatingNum" disabled="disabled" value="<ww:property value="car.carVehicleModel.seatingNum" />" />
                      </td>   
                   </tr>
                   <tr>
<!--                       <td class="zuo"><span class="xx red">*</span><span>车辆状态 </span>:</td> -->
<!--                       <td class="you"> -->
<!--                          <div class="btt1 fl"> -->
<!-- 	                         <select class="kd"  style="top:17px"id="car.bizState" name="car.bizState"> -->
<%-- 								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carBizState',2)" id="data" status="rl"> --%>
<%-- 									<option value="<ww:property value="id" />"  <ww:if test="car.bizState==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	 --%>
<%-- 								</ww:iterator> --%>
<!-- 							</select> -->
<!-- 	                      </div> -->
<!--                       </td>   -->
					  <td class="zuo"><span>行驶证号</span>:</td>
                      <td class="you">
                        <input class="input_size fl" maxlength="30"   type="text" name="car.drivingLicenseNumber" value="<ww:property value="car.drivingLicenseNumber"/>" />
                      </td>  
                      <td class="zuo1"><span>行李数</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="luggageNum" disabled="disabled" value="<ww:property value="car.carVehicleModel.luggageNum"/>" />
                      </td>   
                   </tr>
                   <tr>
                      <td class="zuo"><span>是否是广告车 </span>:</td>
                      <td class="you">
                        <div class="btt1 fl">
                        	<select name="car.isAd">
								<option value="1" <ww:if test="car.isAd==1">selected="selected"</ww:if>>是 </option>
								<option value="0" <ww:if test="car.isAd==0">selected="selected"</ww:if>>否</option>
							</select>
                        </div>
                      </td>  
                      <td class="zuo1"><span>档类别</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="gearboxType" disabled="disabled" 
                        value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('3',car.carVehicleModel.gearboxType)" />" />
                      </td>   
                   </tr>
                   <tr>
                   		<td class="zuo"><span>购置时间</span>:</td>
                      <td class="you">
                        <input name="car.buyTime" value="<ww:property value="transDate10String(car.buyTime)"/>"
		    			type="text" class="input_size fl timeselect"  />
                      </td>  
                      
                   </tr>
                   <tr>
                       <td class="zuo1"><span>购置地址</span>:</td>
                       <td class="you1">
                           <input class="input_size fl" maxlength="255"  type="text" name="car.buyPlace" value="<ww:property value="car.buyPlace"/>" />
                       </td>   
                   </tr>
                    <tr>
                        <td class="zuo"><span class="xx red">*</span><span>所属网点</span>:</td>
		                <td class="you">
		                  	<input type="hidden" name="belongDotId" id="branchDotId" value="<ww:property value="belongDotId" />"/>
		                    <input class="InlineInput" name="belongDotName" id="branchDotName" type="text" readonly 
		                  	    class="input_size fl"
		                  		value="<ww:property value="belongDotName" />" />
							<input onclick="selectDot();" type="button" value="选择" class="searchinputbut" />
		                </td>  
                   </tr>
                    <tr>
                       <td class="zuo1"><span>车辆位置</span>:</td>
                       <td class="you1">
                          	 楼层：<input class="input_size" maxlength="50"  type="text" name="carfoor" value="<ww:property value="carfoor" />" />
                           	车位号：<input class="input_size" maxlength="50"  type="text" name="carnumber" value="<ww:property value="carnumber" />" />
                       </td>   
                   </tr>
                   <tr>
                        <td colspan="4">
                            <div class="btt">
                               <div class="sbtn fl" onclick="sub();">提&nbsp;&nbsp;交</div>
                               <div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消</div>
                            </div>
                        </td>
                   </tr>
             </table>
             <%-- 
                   <div class="panel_add_left fl" style="margin-left:50px;position:absolute">
                        <div class="xxgl fl">
                            <span class="zjh">车牌号:</span>
                            <input name="car.vehiclePlateId" id="car.vehiclePlateId"  class="input_size2"  value="<ww:property value="car.vehiclePlateId" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjh">车架号:</span>
                            <input name="car.vin" id="car.vin"  class="input_size2" value="<ww:property value="car.vin" />" />
                        </div>
                        <div class="xxgl fl">
                            <span>昵<span class="jx">称</span>:</span>
                            <input type="text" name="car.nickName"  class="input_size2" value="<ww:property value="car.nickName"/>" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjzp">车辆颜色 :</span>
                            <input name="car.color" id="car.color"  class="input_size2" value="<ww:property value="car.color" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjzp">运营城市 :</span>
                            <input name="car.operationCity" id="car.operationCity"  class="input_size2" value="<ww:property value="car.operationCity" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjzp">是否是广告车 :</span>
                            <select name="car.isAd">
<select class="kd"  	<option value="1" <ww:if test="car.isAd==1">selected="selected"</ww:if>>是 </option>
								<option value="0" <ww:if test="car.isAd==0">selected="selected"</ww:if>>否</option>
							</select>
                        </div>
                        --%>
                        <%-- <div class="sbtn fr" onclick="sub();">提&nbsp;&nbsp;交</div> --%>
                   <%-- </div>
                   <!--panel_add_left 左侧结束-->
                   <div class="panel_add_left fr" style="margin-right:50px;position:absolute;left:50%">
                        <div class="xxgl fl">
                            <span class="zjzp">车辆型号 :</span>
                            <select id="car.modelId" name="modelId" onchange="queryModel(this.options[this.options.selectedIndex].value);">
								<option value="">请选择</option>
								<ww:iterator value="carVehicleModels">
									<option  value="<ww:property value="id"/>,<ww:property value="level"/>,<ww:property value="engineType"/>,<ww:property value="seatingNum"/>,<ww:property value="luggageNum"/>,<ww:property value="casesNum"/>,<ww:property value="gearboxType"/>" <ww:if test="car.modelId.equals(id)">selected=true</ww:if>>
										<ww:property value="brand"/>-<ww:property value="name"/>
									</option>
								</ww:iterator>
							</select>
							<input type="hidden" name="car.modelId" id="modelId" value="<ww:property value="car.modelId" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjzp">车辆等级&nbsp;:</span>
                            <input id="level" class="input_size2" disabled="disabled" value="<ww:property value="car.carVehicleModel.level" />" />
                        </div>
                       <!--   <div class="xxgl fl">
                            <span class="zjzp">动力类型:</span>
                            <span id="engineType"><ww:property value="car.carVehicleModel.engineType" /></span>
                        </div>-->
                        <div class="xxgl fl">
                            <span class="zjh">座位数:</span>
                             <input id="seatingNum" class="input_size2" disabled="disabled" value="<ww:property value="car.carVehicleModel.seatingNum" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjh">行李数:</span>
                            <input id="luggageNum" class="input_size2" disabled="disabled" value="<ww:property value="car.carVehicleModel.luggageNum" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjzp">车辆厢数 :</span>
                            <input id="casesNum" class="input_size2" disabled="disabled" value="<ww:property value="car.carVehicleModel.casesNum" />" />
                        </div>
                        <div class="xxgl fl">
                            <span class="zjh">档类别:</span>
                             <input id="gearboxType" class="input_size2" disabled="disabled" value="<ww:property value="car.carVehicleModel.gearboxType" />" />
                        </div>
                        --%>
                         <%-- <div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消</div>--%>
                  <%-- </div> <!--panel_add_left 右侧结束-->
                   --%>
     			</form>
     	</div>
</body>

</html>