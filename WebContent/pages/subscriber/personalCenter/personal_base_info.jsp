<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/common.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}


</style>

<script type="text/javascript">
var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
function emailOnBlur(){
	if($("#email").val()=="" || $("#email").val()==null){
		$("#emailSpan").html("");
	}else{
		if(!reg.test($("#email").val())){
			$("#email").focus();
			$("#emailSpan").html("邮箱格式不正确!");
			return;
		}else{
			$("#emailSpan").html("");
		}
	}
	
}


function formSubmit(){
	if($("#email").val()!="" ){
		if(!reg.test($("#email").val())){
			$("#email").focus();
			$("#emailSpan").html("邮箱格式不正确!");
			return;
		}else{
			$("#emailSpan").html("");
		}

	}
	if(formData==$('#subscriberform').serialize()){
		window.parent.parent.alertMsg("提示","基本资料未修改，无需保存!");
		return;
	}
	
	$.ajax({
		type: "POST",
	    url: "subscriber/updateBaseInfo.action",
	    dataType:'json',
	    data:$('#subscriberform').serialize(),
	    success: function(data) {
	     
			if(0==data.result){
				formData=$('#subscriberform').serialize();
				window.parent.parent.alertMsg("提示","保存成功!");
		    }else{
		    	window.parent.parent.alertMsg("提示",data.msg);
	    		
		    }
	    }
	});
}


//查询网点
function queryDot(val){
	$("#dot").text("");
	if(val!=""){
		var data={"dot.areaId":val};
		$.post('orders/ordersSearchDot.action',data,r_data,'json').error(requestError);
	}else{
		var str="<option value=''>请选择</option>";
		$("#dot").append(str);
	}
}

function r_data(data){
	$("#backSiteId").text("");
	if(data!=""){
		if(data.info!=""){
			var str="<option value=''>请选择</option>";
			for(var i=0;i<data.info.length;i++){
				str+="<option value="+data.info[i].id+">"+data.info[i].name+"</option>";
			}
			$("#dot").append(str);
			$("#backSiteId").append(str);
		}
	}
}


//查询城市
function queryCity(val){
	$("#area").empty();
	$("#dot").empty();
	$("#area").append("<option value=''>请选择</option>");
	$("#dot").append("<option value=''>请选择</option>");
	if(val!=""){
		var data={"dot.areaId":val};
		$.post('subscriber/searchCity.action',data,area_data,'json').error(requestError);
	}else{
		$("#dot").append(str);
	}
}

function area_data(data){
	if(data!=""){
		if(data.info!=""){
			var str="";
			for(var i=0;i<data.info.length;i++){
				str+="<option value="+data.info[i].id+">"+data.info[i].name+"</option>";
			}
			$("#area").append(str);

		}
	}
}
var formData;
$(document).ready(function () {
	formData=$('#subscriberform').serialize();
});
</script>
</head>

<body>
<div style="background-color:#fff; padding:0px; margin:0 auto;">
<div class="tis">以下信息仅用户本人可见，请用户放心填写。（应保证资料的真实、可靠性）</div>
 <form name="subscriberform" id="subscriberform" method="post" action="">
<table class="table2" width="800" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <th width="220">用户姓名：</th>
        <td width="250">  <input class="input_size" type="search" value="<ww:property value="subscriber.name"/>" disabled="disabled"   style="width:320px; height:25px;"></td>
        <td><span class="cw" ></span></td>
      </tr>
      <tr>
        <th>手&nbsp;机&nbsp;号&nbsp;：</th>
        <td><input class="input_size" type="search" disabled="disabled" value="<ww:property value="subscriber.phoneNo"/>"   style="width:320px; height:25px;"></td>
        <td></td>
      </tr>
      <tr>
        <th>状&nbsp;&nbsp;&nbsp;态&nbsp;&nbsp;&nbsp;：</th>
        <td>
        	
        		<ww:if test="subscriber.state==1"><span class="sh">资料未提交</span></ww:if>
        		<ww:elseif test="subscriber.state==2"><span class="sh">资料待审核</span></ww:elseif>
        		<ww:elseif test="subscriber.state==4"><span class="sh" style="color:red;">审核未通过</span></ww:elseif>
        		<ww:elseif test="subscriber.state==3"><span class="sh" style="color:black;">资料已审核</span></ww:elseif>
        		<ww:elseif test="subscriber.state==5"><span class="sh" style="color:black;">已发卡</span></ww:elseif>
        		<ww:elseif test="subscriber.state==6"><span class="sh" style="color:black;">已挂失</span></ww:elseif>
        		<ww:else>其他</ww:else>
        	</td>
        <td></td>
      </tr>
       <tr>
        <th>驾驶证号：</th>
        <td><input class="input_size" type="text" disabled="disabled" name="subscriber.drivingLicenseNo" value='<ww:property value="subscriber.drivingLicenseNo"/>'  id="drivingLicenseNo" style=" width:320px; height:25px;"></td>
        <td>
	        <ww:if test="subscriber.state==1  || subscriber.state==4">
	        	<a href="subscriber/registerNextStep.action" target="_blank">修改驾驶证资料</a> 
	        </ww:if>	
        </td>
      </tr>
      <tr>
        <th>支付宝帐号：</th>
        <td><input class="input_size" type="text" name="subscriber.alipayAccount" maxlength="40"value='<ww:property value="subscriber.alipayAccount"/>' id="alipayAccount" style=" width:320px; height:25px;"></td>
        <td><span class="cw" id="alipayAccountSpan"></span></td>
      </tr>
     
     <!--  
      <tr>
        <th>微信帐号：</th>
        <td><input class="input_size" type="text" name="subscriber.webchatNo" maxlength="40" value='<ww:property value="subscriber.webchatNo"/>' id="webchatNo" style=" width:320px; height:25px;"></td>
        <td><span class="cw" id="webchatNoSpan"></span></td>
      </tr>
      -->
      <tr>
        <td height="50"><span style="margin-left:62px; color:#4bd3a5;">完善个人用车信息</span></td>
        <td></td>
        <td></td>
      </tr>
      
       <tr>
        <th>性&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;：</th>
        <td>
        	  <select  class="input_size "  style="width:100px;"  name="subscriber.sex" id="subscriber.sex">
							   		<option>请选择</option>
									<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_MAN"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_MAN.equals(subscriber.sex)">selected=true</ww:if> >男</option>
									<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_WOMAN"/>' <ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_WOMAN.equals(subscriber.sex)">selected=true</ww:if> >女</option>
					</select>
        </td>
        <td></td>
      </tr>
      
      <tr>
        <th>常用城市：</th>
        <td>
          
           <select class="input_size" name="subscriber.city"  style="width:100px;" onchange="queryCity(this.value)">
		                <option value="">请选择</option>
						<ww:iterator value="citys" >
							<option value='<ww:property value="id"/>' <ww:if test="id.equals(subscriber.city)">selected</ww:if> >
								<ww:property value="name"/>
							</option>
						</ww:iterator>
	        </select>
        </td>
        <td><span class="cw" id="citySpan"></span></td>
      </tr>
      
      <tr>
      	<th>常用网点：</th>
      	<td>
      		 	<select class="input_size" style="width:100px;" onchange="queryDot(this.value);" id="area" name="subscriber.defaultArea">
		                <option value="">请选择</option>
						<ww:iterator value="areas" >
							<option value='<ww:property value="id"/>' <ww:if test="id.equals(subscriber.defaultArea)">selected</ww:if> >
								<ww:property value="name"/>
							</option>
						</ww:iterator>
	                </select>
	                <select class="input_size" style="width:120px;" id="dot" name="subscriber.defaultSite">
	                	<option value="">请选择</option>
						<ww:iterator value="dots">
							<option value='<ww:property value="id"/>'  <ww:if test="id.equals(subscriber.defaultSite)">selected</ww:if>   >
								<ww:property value="name"/>
							</option>
						</ww:iterator>
	                </select>
      	</td>
      </tr>
      <tr>
        <th>索取发票地址：</th>
        <td>
        	<textarea  class="input_size" name="subscriber.postAddress" maxlength="400" style=" width:320px;height:37px;resize: none;"id="postAddress"   rows="2" cols="46"><ww:property value="subscriber.postAddress"/></textarea>
        </td>
        <td><span class="cw" id="postAddressSpan"></span></td>
      </tr>
      <tr>
        <th>邮&nbsp;&nbsp;&nbsp;箱&nbsp;&nbsp;&nbsp;：</th>
        <td><input class="input_size" type="text" name="subscriber.email" id="email" maxlength="40" onBlur="emailOnBlur();" maxlength="30" value='<ww:property value="subscriber.email"/>' style=" width:320px; height:25px;"></td>
        <td><span class="cw" id="emailSpan"></span></td>
      </tr>
      <tr>
        <th>紧急联系人：</th>
        <td><input class="input_size" type="text" name="subscriber.emergencyContact" maxlength="20" id="emergencyContact" value='<ww:property value="subscriber.emergencyContact"/>' style=" width:320px; height:25px;"></td>
        <td><span class="cw" id="emergencyContactSpan"></span></td>
      </tr>
      <tr>
        <th>紧急联系人号码：</th>
        <td><input class="input_size" type="text" name="subscriber.emergencyContactPhone" maxlength="20" id="emergencyContactPhone" value='<ww:property value="subscriber.emergencyContactPhone"/>' style=" width:320px; height:25px;"></td>
        <td><span class="cw" id="emergencyContactPhoneSpan"></span></td>
      </tr>
      <tr>
        <th>紧急联系人地址：</th>
        <td>
        	<textarea  class="input_size" name="subscriber.emergencyContactAddress" id="emergencyContactAddress" maxlength="400" style=" width:320px;height:37px;resize: none;"id="postAddress"   rows="2" cols="46"><ww:property value="subscriber.emergencyContactAddress"/></textarea>
        	
        </td>
        <td><span class="cw" id="emergencyContactAddressSpan"></span></td>
      </tr>
      <tr ><td height="30" colspan="3"></td> </tr>
      <tr>
        <th></th>
        <td><input class="input_sub" type="button" onclick="formSubmit()"  value="保  存" style=" width:282px; height:39px; color:#fff; font-size:16px; font-weight:bold; line-height:39px;background:url(common/portal/images/text_btn.png) no-repeat; border:none; margin-left:20px;cursor:pointer;" /></td>
        <td><span class="cw" id="formSpan"></span></td>
      </tr>
</table>  
</form>
</div>
</body>
</html>
