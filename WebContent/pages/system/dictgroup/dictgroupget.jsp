<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
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
		$("#eform").attr('action','<%=path%>/dictgroup/dictGroupAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/dictgroup/dictGroupUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"rEntity.code": {
				required: true,
				maxlength : 60,
				rEntityCodeSc:true
			},
			"rEntity.remark":{
				required: true,
				maxlength:60,
				rEntityRemarkSc:true
			}
		},
		messages: {
			"rEntity.code": {
				required: "请输入字典组编码！",
				maxlength: "编码过长，最大为60个字符！"
			},
			"rEntity.remark":{
				required: "请输入备注！"
			}
		}
	});
	val_check_SpecialChar("rEntityCodeSc,rEntityRemarkSc");
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
				<input type="hidden" name="rEntity.id" id="rEntity.id"value="<ww:property value="rEntity.id" />">
		<table class="xxgl" >
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>字典组编码</span>:</td>
		    <td class="you">
		    <ww:if test="id!=''"><input type="hidden" name="rEntity.code" id="rEntity.code" 
		    	value="<ww:property value="rEntity.code" />"/><label><ww:property value="rEntity.code" /></label></ww:if>
		    <ww:else><input  type="text" name="rEntity.code" id="rEntity.code" maxlength="60" class="input_size fl" 
		    	value="<ww:property value="rEntity.code" />"/></ww:else>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>字典组备注</span>:
		    </td>
		    <td class="you"><input type="text" name="rEntity.remark" id="rEntity.remark" maxlength="60"  class="input_size fl" 
		       maxlength="64" value="<ww:property value="rEntity.remark" />"/>
		    </td>   
		  </tr> 
		</table>
	</form>
	</div> 
</body>
</html>