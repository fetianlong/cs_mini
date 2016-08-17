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
<%@ include file="/pages/common/common_head.jsp"%>

<style type="text/css">
.error{position:inherit;top:0px;}
</style>
<script type="text/javascript">
$().ready(function (){
	
	$('#eform').validate({   
        errorClass : 'text-danger',  
		rules: {
			"subscriberConfirm.resultDesc":{
				required: true
			}
		},
		messages: {
			"subscriberConfirm.resultDesc":{
				required: "请填写审核失败原因！"
			}
		}
	});

	
});


jQuery.validator.addMethod("isIdCard", function(value, element) {   
    var idCard = /^\d{15,18}(\d{2}[A-Za-z0-9])?$/;
    return this.optional(element) || (idCard.test(value));
}, "请输入正确的身份证号");

function getForm(){
	return $("#eform");
}
function isValid(){
	var result = $("#result").val();
	if(result == 1 ){
		return true;
	}else if(result == 0  && $("#eform").valid()){
	 	return true;
	}else{
		return false;
	}
	
}
function changeResult(){
	var result = $("#result").val();

	if(result=='1'){
		$("#resultDesc").val("同意");
	}else{
		$("#resultDesc").val("");
	}
}

</script>
</head>

<body >
	<div class="table_con tanchuang" >
	<form name="eform" id="eform" method="post" action="account/doApplyRefund.action">
				<input type="hidden" name="subscriberConfirm.id"  value="<ww:property value="subscriberConfirm.id" />">
		<table class="xxgl"  >
		   <tr >
		      <td class="zuo" >
			 	<ww:if test="subscriberConfirm.result ==null">
			 	 	<span class="xx red"></span>
			 	</ww:if>
			    <span>用户姓名</span>:</td>
		   
		    <td class="you">
		   <input type="text"   name="subscriberConfirm.subscriber.name" id="subscriberConfirm.subscriber.name" disabled="disabled" class="input_size"  value="<ww:property value="subscriberConfirm.subscriber.name" />"/>
		    </td>   
		  </tr> 
		  <tr >
		  	<td class="zuo">
		  			<span class="xx red"></span>
		  	<span>手机号</span>:</td>
		    <td class="you">
		    	<input type="text"   name="subscriberConfirm.subscriber.phoneNo" id="subscriberConfirm.subscriber.phoneNo" disabled="disabled" class="input_size" value="<ww:property value="subscriberConfirm.subscriber.phoneNo" />"/>
		    	
		    </td>   
		  </tr> 
		    <tr >
		  	<td class="zuo">
		  		<span class="xx red"></span>
		  	<span>退款金额</span>:</td>
		    <td class="you">
		    	<input type="text"    disabled="disabled" class="input_size"  value="<ww:property value="subscriberConfirm.attribute_2" />"/>
		    	
		    </td>   
		  </tr> 
		  
		     <tr >
		  	<td class="zuo"><span class="xx red"></span>
		  	<span>退款方式</span>:</td>
		    <td class="you">
		    	<input type="text"    disabled="disabled" class="input_size"  value="<ww:if test="subscriberConfirm.attribute_1==null">原路返回</ww:if><ww:else><ww:property value="subscriberConfirm.attribute_4"/></ww:else>	"/>
		    	
		    </td>   
		  </tr> 
		  
		  
		 <tr>
			 <td class="zuo"><span class="xx red">*</span><span>审核结果</span>:</td>
		 	<td class="you">  
		 		<select name="subscriberConfirm.result" id="result" class="input_size"   onchange="changeResult()" >
						<option value="1"  <ww:if test="subscriberConfirm.result==1">selected="selected"</ww:if>>审核通过</option>
						<option value="0"  <ww:if test="subscriberConfirm.result==0">selected="selected"</ww:if>>审核不通过</option>
				</select>
			</td>
		 </tr>
		 <tr>
		 	<td class="zuo"><span>审核意见：</span>
		  	</td>
		    <td class="you">
		    	<textarea rows="3" class="textarea_size "  id="resultDesc"  name="subscriberConfirm.resultDesc" cols="30"><ww:if test="subscriberConfirm.result==0"><ww:property value="subscriberConfirm.resultDesc"/></ww:if><ww:else>同意</ww:else></textarea>
		    </td>   
		 </tr>
		  <tr>
		 	<td class="zuo"><span>备注：</span>
		  	</td>
		    <td class="you">
		    	<textarea rows="3" class="textarea_size "    name="subscriberConfirm.remark" cols="30"><ww:property value="subscriberConfirm.remark"/></textarea>
		    </td>   
		 </tr>
	
		<tr>
		</tr>
		</table>
	</form>
	</div> 
</body>


</html>