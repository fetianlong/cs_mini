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
			"subscriberConfirm.subscriber.drivingLicenseNo":{
				required: true,
				isIdCard:true
			}
		},
		messages: {
			"subscriberConfirm.subscriber.drivingLicenseNo":{
				required: "请输入驾驶证号！"
				
			}
		}
	});

	if('<ww:property value="#request.type"/>'=='show'){
		$("input[type=text]").attr("disabled","disabled");
		$("input[type=checkbox]").attr("disabled","disabled");
		$("select").attr("disabled","disabled");
		
	}
	
	
	<ww:if test="subscriberConfirm.result ==null || subscriberConfirm.result==1">$("#failResultTr").css("display","none");</ww:if>
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
	if(result == 1  && $("#eform").valid()){
		return true;
	}else if(result == 0){

		if($('input[name="subscriberConfirm.resultDesc"]:checked').length==0){
			document.getElementById("failReasonLable").style.display="";
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}

$(document).ready(function(){  
	  $('#result').change(function(){ 
		  var result = $("#result").val();
			document.getElementById("failReasonLable").style.display="none";
			document.getElementById("failResultTr").style.display="none";
			
			if(result == 0) {
				document.getElementById("failResultTr").style.display="";
			}
	});
});



</script>
</head>

<body style="overflow-y:auto;" class="hyshPage">


	<div class="table_con tanchuang" >
	<form name="eform" id="eform" method="post" action="subscriberConfirm/doCheck.action">
				<input type="hidden" name="subscriberConfirm.subscriberId" value="<ww:property value="subscriberConfirm.subscriberId" />">
				<input type="hidden" name="subscriberConfirm.id"  value="<ww:property value="subscriberConfirm.id" />">
		<table class="xxgl"  >
		   <tr >
		      <td class="zuo" style="width:14%">
			 	<ww:if test="subscriberConfirm.result ==null">
			 	 	<span class="xx red"></span>
			 	</ww:if>
			    <span>用&nbsp;户&nbsp;姓&nbsp;名</span>：</td>
		   
		    <td class="you">
		    <input type="text"   name="subscriberConfirm.subscriber.name" id="subscriberConfirm.subscriber.name" disabled="disabled"  class="input_size"  value="<ww:property value="subscriberConfirm.subscriber.name" />"/>
		    </td>   
		  </tr> 
		  <tr >
		  	<td class="zuo">
		  		<ww:if test="subscriberConfirm.result ==null">
		  			<span class="xx red"></span>
		  		</ww:if>
		  	<span>手&nbsp;&nbsp;机&nbsp;&nbsp;号&nbsp;&nbsp;</span>：</td>
		    <td class="you">
		    	<input type="text"    name="subscriberConfirm.subscriber.phoneNo" id="subscriberConfirm.subscriber.phoneNo" disabled="disabled" class="input_size"  value="<ww:property value="subscriberConfirm.subscriber.phoneNo" />"/>
		    </td>   
		  </tr> 
		  
		 
		   <tr >
		    <td class="zuo">
			    <ww:if test="subscriberConfirm.result ==null">
			    	 <span class="xx red">*</span>
			    </ww:if>
			   	<span>驾&nbsp;驶&nbsp;证&nbsp;号</span>：
		    </td>
		    <td class="you">
		    	<input type="text"    maxlength="18"  name="subscriberConfirm.subscriber.drivingLicenseNo" id="subscriberConfirm.subscriber.drivingLicenseNo"  class="input_size"  value="<ww:property value="subscriberConfirm.subscriber.drivingLicenseNo" />"/>
		    </td>   
		  </tr> 
		  
		  <tr >
		    <td class="zuo">
			    <ww:if test="subscriberConfirm.result ==null">
			    	 <span class="xx red">*</span>
			    </ww:if>
			   	<span>家庭住址</span>：
		    </td>
		    <td class="you">
			    <select id="s_province" name="subscriberConfirm.subscriber.province"></select>
				<select id="s_city" name="subscriberConfirm.subscriber.city" ></select>
				<select id="s_county" name="subscriberConfirm.subscriber.county"></select>
		    	<input type="text"  value="<ww:property value="subscriberConfirm.subscriber.address"/>" >
		    </td>   
		  </tr> 
		  
		  <tr>
		   	<td class="zuo"><span>身份证图片</span>：</td>
		   
		  	<td  class="you">
		  		<ww:if test="subscriberConfirm.subscriber.idCardImg == null">
		  			<img alt="身份证图片" class="img-responsive tcImg"  src="">
		  		</ww:if>
		  		<ww:else>
			  		 <a href="<%=path %>/subscriberConfirm/showOriginalImage.action?imageType=idCard&subscriberConfirm.subscriber.idCardImg=<ww:property value='subscriberConfirm.subscriber.idCardImg'/>" target="_blank">
							<img class="img-responsive IDImg img-thumbnail" alt="身份证缩略图"  src="<ww:property value="subscriberConfirm.subscriber.idCardImg"/>">
					</a>
		  		</ww:else>
		  		
		  		

		  	</td>
		  </tr>
		  
		  
		  
		  
		  
		   <tr>
		    
		     <td class="zuo"><span>驾驶证图片</span>：</td>
		  	 <td class="you">
		  		<ww:if test="subscriberConfirm.subscriber.drivingLicenseImg == null">
		  			<img alt="驾照图片" class="img-responsive tcImg" src="" >
		  		</ww:if>
		  	
		  		<ww:else>
		  			   <a href="<%=path %>/subscriberConfirm/showOriginalImage.action?imageType=drivingCard&subscriberConfirm.subscriber.drivingLicenseImg=<ww:property value='subscriberConfirm.subscriber.drivingLicenseImg'/>" target="_blank">
								      <img class="img-responsive IDImg img-thumbnail" alt="驾驶证缩略图"  src="<ww:property value="subscriberConfirm.subscriber.drivingLicenseImg"/>">
						</a>
								  
		  		</ww:else>
		  		
		  	</td>
		  </tr>
		  
		  
		<ww:if test="#request.type != 'show' || (#request.type == 'show'&& subscriberConfirm.result !=null )">
		  
		 <tr class="trr">
		 	<td class="zuo">
		 		<ww:if test="subscriberConfirm.result ==null">
		 			<span class="xx red">*</span>
		 		</ww:if>
		 		
		 		<span>审&nbsp;核&nbsp;结&nbsp;果</span>：</td>
		    <td class="you">
		    	 <select name="subscriberConfirm.result" id="result">
						<option value="1"  <ww:if test="subscriberConfirm.result==1">selected="selected"</ww:if>>审核通过</option>
						<option value="0"  <ww:if test="subscriberConfirm.result==0">selected="selected"</ww:if>>审核不通过</option>
				</select>
		    	
		    	
		    </td>   
		  </tr> 
		  
		 </ww:if>
		 <!--  style="display:none;"
		 <textarea rows="5" id="failReason" name="subscriberConfirm.resultDesc"    cols="60">欢迎使用华泰租车服务，您的资料审核未通过，原因：</textarea>
		 -->
		 
		  <tr id="failResultTr" <ww:if test="subscriberConfirm.result ==null || subscriberConfirm.result==1">style="display:none;"</ww:if> >
		  	 <td class="zuo">
		  	 	 <ww:if test="subscriberConfirm.result ==null">
		  	 	 	<span class="xx red">*</span>
		  	 	 </ww:if>
		  	 	<span>失&nbsp;败&nbsp;原&nbsp;因</span>：</td>
		     <td class="you">
		    	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('6')" >
					<div class="checkbox">
					  <label>
						<input class="tcCheckBox" type="checkbox" name="subscriberConfirm.resultDesc" <ww:if test="resultList!=null && resultList.contains(code)">checked="checked"</ww:if> value="<ww:property value="code" />" >
						<ww:property value="cnName" />
					  </label>
					</div>
				</ww:iterator>
		    	<label for="failReason" id="failReasonLable"   style="display:none;color: red;" class="help-block">请选择审核失败原因！</label>
		    </td>
		  </tr>
		  
		  <ww:if test="subscriberConfirm.result!=null">
		  	<tr >
		  	<td class="zuo"><span>审&nbsp;&nbsp;&nbsp;核&nbsp;&nbsp;&nbsp;人</span>：</td>
		    <td  class="you">
		    	<ww:property value="subscriberConfirm.businessFlow.reviewerId"/>
		    </td>
		  </tr>
		  
		  <tr >
		  	 <td class="zuo"><span>审&nbsp;核&nbsp;时&nbsp;间</span>：</td>
		     <td class="you">
		    	<ww:property value="transDate10String(subscriberConfirm.businessFlow.reviewTime)"/>
		    </td>
		  </tr>
		  </ww:if>
		  
		   
		  
		</table>
	</form>
	</div> 
	
	
</body>


</html>

<script class="resources library" src="<%=path %>/mobile/common/js/area.js" type="text/javascript"></script>
 <script type="text/javascript">
 _init_area();
$("#s_province option[value='<ww:property value="subscriberConfirm.subscriber.province"/>']").attr("selected",true);
$("#s_province").trigger("change");  
$("#s_city option[value='<ww:property value="subscriberConfirm.subscriber.city"/>']").attr("selected",true);
$("#s_city").trigger("change");  
$("#s_county option[value='<ww:property value="subscriberConfirm.subscriber.county"/>']").attr("selected",true);
$("#s_county").trigger("change");  
</script>