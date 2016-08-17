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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base href="<%=basePath%>">
<link href="common/css/common.css" rel="stylesheet" type="text/css">

<link href="common/css/main/xenon-core.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="common/js/common.js"></script>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>
<script type="text/javascript">
$().ready(function (){
	
	$('#eform').validate({
		rules: {
			"member.idCard": {
				required: true,
				rangelength:[15,18]
			},
			"member.drivingLicense":{
				required: true,
				rangelength:[15,20]
			}
		},
		messages: {
			"member.idCard": {
				required: "请输入身份证号称！",
				rangelength: "请输入正确的身份证号"
			},
			"member.drivingLicense":{
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
	var result = $("#result").attr("value");
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
	var result = $("#result").attr("value");
	document.getElementById("failReasonLable").style.display="none";
	if(result == "nopass") {
		document.getElementById("failReason").style.display="";
	}
	else if(result == "pass") {
		document.getElementById("failReason").style.display="none";
	}
}


</script>
</head>

<body style="height:160%">
	<div class="table_con" style="margin-right:50px; ">
	<form name="eform" id="eform" method="post" action="member/doCheck.action">
				<input type="hidden" name="member.id" id="memebr.id"value="<ww:property value="member.id" />">
		<table class="t1" width="95%"  border="0" cellpadding="0" cellspacing="0" >
		   <tr class="trr">
		    <th width="20%"><span style="position:relative; top:-10px;">用户姓名:</span>
		    </th>
		    <td>
		    <input type="text" name="member.name" id="member.name" disabled="disabled"  class="input_size" style=" position:relative;" value="<ww:property value="member.name" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  <tr class="trr">
		    <th width="20%"><span>手机号:</span>
		    </th>
		    <td>
		    	<input type="text" name="member.phoneNo" id="member.phoneNo" disabled="disabled" class="input_size" style=" position:relative;" value="<ww:property value="member.phoneNo" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  
		   <tr class="trr">
		    <th width="20%"><span>身份证号:</span>
		    </th>
		    <td>
		    	<input type="text" name="member.idCard" id="member.idCard"  class="input_size" style=" position:relative;" value="<ww:property value="member.idCard" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  
		  <tr>
		   <th ></th>
		  	<td >
		  		<ww:if test="member.drivingLicenseImg == null">
		  			<img alt="身份证图片" width="348px" height="195px"  src="">
		  		</ww:if>
		  		<ww:else>
		  		<img alt="身份证图片" width="348px" height="195px"  src="<ww:property value="member.idCardImg.substring(0, member.idCardImg.indexOf('.'))+'_cut.png'"/>">
		  		<a href="member/showOriginalImage.action?imageType=idCard&member.idCardImg=<ww:property value='member.idCardImg'/>" target="_blank">原图</a>
		  		</ww:else>
		  		
		  	</td>
		  </tr>
		  
		  
		  <tr class="trr">
		    <th width="20%"><span>驾驶证号:</span>
		    </th>
		    <td>
		    	<input type="text" name="member.drivingLicense" id="member.drivingLicense"  class="input_size" style=" position:relative;" value="<ww:property value="member.drivingLicense" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  
		  
		   <tr>
		     <th ></th>
		  	<td >
		  		<ww:if test="member.drivingLicenseImg == null">
		  			<img alt="驾照图片" width="580px" height="232px"  src="">
		  		</ww:if>
		  		<ww:else>
		  			<img alt="驾照图片" width="580px" height="232px"  src="<ww:property value="member.drivingLicenseImg.substring(0, member.drivingLicenseImg.indexOf('.'))+'_cut.png'"/>">
		  			<a href="member/showOriginalImage.action?imageType=drivingCard&member.drivingLicenseImg=<ww:property value='member.drivingLicenseImg'/>" target="_blank">原图</a>
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
		    	<textarea rows="3" id="failReason" name="failReason"  style="display:none;"  cols="30"></textarea>
		    	<label for="failReason" id="failReasonLable"   style="display:none;" class="error">请输入审核失败原因！</label>
		    </td>   
		  </tr> 
		  
		</table>
	</form>
	</div> 
</body>


</html>