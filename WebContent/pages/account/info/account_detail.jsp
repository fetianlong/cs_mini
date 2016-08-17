<%@page import="java.util.Date"%>
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
<title>Insert title here</title>
<base href="<%=basePath%>">

<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
$().ready(function (){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"subscriber.idCard": {
				required: true,
				rangelength:[15,18]
			},
			"subscriber.drivingLicense":{
				required: true,
				rangelength:[15,20]
			}
		},
		messages: {
			"subscriber.idCard": {
				required: "请输入身份证号称！",
				rangelength: "请输入正确的身份证号"
			},
			"subscriber.drivingLicense":{
				required: "请输入驾驶证号！",
				rangelength: "请输入正确的驾驶证号"
			}
		}
	});
});
function getForm(){
	return $("#eform");
}
function isValid(){
	var result = $("#result").val();
	if(result == "pass"  && $("#eform").valid()){
		return true;
	}else if(result == "nopass"){
		var failReason= $("#failReason").val();
		if(failReason==null || failReason==""){
			document.getElementById("failReasonLable").style.display="";
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
	

}


function checkResult() {
	var result = $("#result").val();
	document.getElementById("failReasonLable").style.display=false;
	/*if(result == "nopass") {
		document.getElementById("failReason").style.display=true;
	}
	else if(result == "pass") {
		document.getElementById("failReason").style.display=false;
	}*/
}


</script>
</head>

<body style="height:160%">
	<div class="table_con" style="margin-right:50px; ">
	<form name="eform" id="eform" method="post" action="subscriber/doCheck.action">
				<input type="hidden" name="subscriber.id" id="memebr.id"value="<ww:property value="subscriber.id" />">
		<table class="t1" width="95%"  border="0" cellpadding="0" cellspacing="0" >
		   <tr class="trr">
		    <th width="20%"><span style="position:relative; top:-10px;">用户姓名:</span>
		    </th>
		    <td>
		    <input type="text" name="subscriber.name" id="subscriber.name" disabled="disabled"  class="input_size" style=" position:relative;" value="<ww:property value="subscriber.name" />"/>
		    </td>   
		  </tr> 
		  <tr class="trr">
		    <th width="20%"><span>手机号:</span>
		    </th>
		    <td>
		    	<input type="text" name="subscriber.phoneNo" id="subscriber.phoneNo" disabled="disabled" class="input_size" style=" position:relative;" value="<ww:property value="subscriber.phoneNo" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  
		   <tr class="trr">
		    <th width="20%"><span>身份证号:</span>
		    </th>
		    <td>
		    	<input type="text" name="subscriber.idCard" id="subscriber.idCard"  class="input_size" style=" position:relative;" value="<ww:property value="subscriber.idCard" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  
		  <tr>
		   <th ></th>
		  	<td >
		  		<ww:if test="subscriber.drivingLicenseImg == null">
		  			<img alt="身份证图片" width="348px" height="195px"  src="">
		  		</ww:if>
		  		<ww:else>
		  		<img alt="身份证图片" width="348px" height="195px"  src="<ww:property value="subscriber.idCardImg.substring(0, subscriber.idCardImg.indexOf('.'))+'_cut.png'"/>">
		  		<a href="subscriber/showOriginalImage.action?imageType=idCard&subscriber.idCardImg=<ww:property value='subscriber.idCardImg'/>" target="_blank">原图</a>
		  		</ww:else>
		  		
		  	</td>
		  </tr>
		  
		  
		  <tr class="trr">
		    <th width="20%"><span>驾驶证号:</span>
		    </th>
		    <td>
		    	<input type="text" name="subscriber.drivingLicense" id="subscriber.drivingLicense"  class="input_size" style=" position:relative;" value="<ww:property value="subscriber.drivingLicense" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  
		  
		   <tr>
		     <th ></th>
		  	<td >
		  		<ww:if test="subscriber.drivingLicenseImg == null">
		  			<img alt="驾照图片" width="580px" height="232px"  src="">
		  		</ww:if>
		  		<ww:else>
		  			<img alt="驾照图片" width="580px" height="232px"  src="<ww:property value="subscriber.drivingLicenseImg.substring(0, subscriber.drivingLicenseImg.indexOf('.'))+'_cut.png'"/>">
		  			<a href="subscriber/showOriginalImage.action?imageType=drivingCard&subscriber.drivingLicenseImg=<ww:property value='subscriber.drivingLicenseImg'/>" target="_blank">原图</a>
		  		</ww:else>
		  		
		  	</td>
		  </tr>
		  
		  
		 <tr class="trr">
		    <th width="20%"><span>审核结果:</span>
		    </th>
		    <td>
		    	 <select name="result" id="result"  onchange="checkResult()" >
						<option value="pass">审核通过</option>
						<option value="nopass">审核不通过</option>
				</select>
		    	
		    	
		    </td>   
		  </tr> 
		  
		  <tr>
		  	 <th width="20%">
		    </th>
		    <td>
		    	<textarea rows="3" id="failReason" name="failReason"    cols="38"></textarea>
		    	<label for="failReason" id="failReasonLable"   style="display:none;" class="error">请输入审核失败原因！</label>
		    </td>
		  </tr>
		  
		</table>
	</form>
	</div> 
</body>


</html>