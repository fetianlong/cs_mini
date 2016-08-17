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
		$("#eform").attr('action','<%=path%>/config/configAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/config/configUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"rEntity.code": {
				required: true,
				maxlength : 30,
				rEntityCode:true
			},
			"rEntity.name": {
				required: true,
				maxlength : 30,
				rEntityName:true
			},
			"rEntity.value": {
				required: true,
				maxlength : 30,
				rEntityValue:true
			},
			"rEntity.group": {
				required: true
			},
			"rEntity.remark":{
				maxlength : 60
			}
		},
		messages: {
			"rEntity.code": {
				required: "请输入参数编码！",
				maxlength: "参数编码过长，最大为30个字符！"
			},
			"rEntity.name": {
				required: "请输入参数名！",
				maxlength: "参数名过长，最大为30个字符！"
			},
			"rEntity.value": {
				required: "请输入参数值！",
				maxlength: "参数值过长，最大为30个字符！"
			},
			"rEntity.group": {
				required: "请选择参数组！",
			}
		}
	});
	val_check_SpecialChar("rEntityCode,rEntityName,rEntityValue,rEntityRemark");
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
				 <td class="zuo"><span class="xx red">*</span><span>参数编码</span>:</td>
			    <td class="you"><input type="text" name="rEntity.code" id="rEntity.code" maxlength="30" class="input_size fl"  
			    value="<ww:property value="rEntity.code" />"/>
			    </td>
			</tr>
		   <tr>
	   	   
		    <td class="zuo"><span class="xx red">*</span><span>参数名称</span>:</td>
		    <td class="you"><input type="text" name="rEntity.name" id="rEntity.name" maxlength="30" class="input_size fl" 
		    value="<ww:property value="rEntity.name" />"/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>参数值</span>:
		    </td>
		    <td class="you"><input type="text" name="rEntity.value" id="rEntity.value"  maxlength="30" class="input_size fl" 
		    value="<ww:property value="rEntity.value" />"/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>参数组</span>:
		    </td>
		    <td class="you">
		      <div class="btt1 fl">
			    <select name="rEntity.group" id="rEntity.group" ">
					<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('4',2)" id="data" status="rl">
						<option value="<ww:property value="id" />"  
						<ww:if test="rEntity.group==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
					</ww:iterator>
				</select>
			   </div>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span>备注</span>:
		    </td>
		    <td class="you">
		    	<textarea name="rEntity.remark" class="textarea_size" id="rEntity.remark" ><ww:property value="rEntity.remark" /></textarea>
		    </td>   
		  </tr> 
		</table>
	</form>
	</div> 
</body>
</html>