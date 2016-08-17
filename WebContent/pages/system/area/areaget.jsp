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

<title>Insert title here</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
function saveEntity(id){
	
}

function r_savedata(data){

}
$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/area/areaAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/area/areaUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"area.name": {
				required: true,
				maxlength : 60,
				areaNameSc:true
			},
			"area.code": {
				required: true,
				maxlength : 60,
				areaCodeSc:true
			},
			"area.parentCode": {
				maxlength : 60,
				areaParentCodeSc:true
			}
		},
		messages: {
			"area.name": {
				required: "请输入区划名称！",
				maxlength: "区划名称过长，最大为60个字符！"
			},
			"area.code": {
				required: "请输入区划编码！",
				maxlength: "区划编码过长，最大为60个字符！"
			},
			"area.parentCode": {
				maxlength: "父级区划编码过长，最大为60个字符！",
			}
		}
	});
	
	val_check_SpecialChar("areaNameSc,areaCodeSc,areaParentCodeSc");
});

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
</script>
</head>
<body>
  <div class="tc tanchuang">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="area.id" id="area.id"value="<ww:property value="area.id" />">
		<table class="xxgl" >
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>行政区划编码</span>:
		    </td>
		    <td class="you"><input type="text" name="area.code" id="area.code"  maxlength="60" class="input_size fl" 
		    value="<ww:property value="area.code" />"/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>行政区划名称</span>:</td>
		    <td class="you"><input type="text" name="area.name" id="area.name" maxlength="60"  class="input_size fl"  
		    value="<ww:property value="area.name" />"/>
		    </td>   
		  </tr> 
		  
		  <tr>
		    <td class="zuo"><span>父级区划编码</span>:
		    </td>
		    <td class="you"><input type="text" name="area.parentCode" id="area.parentCode"  maxlength="60" class="input_size fl" 
		    value="<ww:property value="area.parentCode" />"/>
		    </td>   
		  </tr> 
		</table>
	</form>
	</div> 
</body>
</html>