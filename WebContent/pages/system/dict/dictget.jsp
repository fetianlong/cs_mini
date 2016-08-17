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
		$("#eform").attr('action','<%=path%>/dict/dictAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/dict/dictUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"rEntity.code": {
				required: true,
				maxlength : 60,
				rEntityCodeSc:true
			},
			"rEntity.cnName": {
				required: true,
				maxlength : 60,
				rEntityCnNameSc:true
			},
			"rEntity.groupId": {
				required: true
			},
			"rEntity.remark":{
				rEntityRemarkSc:true
			}
		},
		messages: {
			"rEntity.code": {
				required: "请输入字典编码！",
				maxlength: "字典编码过长，最大为60个字符！"
			},
			"rEntity.cnName": {
				required: "请输入中文名！",
				maxlength: "中文名过长，最大为60个字符！"
			},
			"rEntity.groupId": {
				required: "请选择字典组！"
			}
		}
	});
	val_check_SpecialChar("rEntityCodeSc,rEntityCnNameSc,rEntityRemarkSc");
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
		    <td class="zuo"><span class="xx red">*</span><span>字典组</span>:
		    </td>
		    <td>
		      <div class="btt1 fl">
		        <select name="rEntity.groupId" id="rEntity.groupId" >
		        	<option value="" >请选择</option>
					<ww:iterator value="dictGroups" id="data" status="rl">
						<option value="<ww:property value="id" />"  <ww:if test="rEntity.groupId==id">selected=true</ww:if> ><ww:property value="remark" /></option>	
					</ww:iterator>
				</select>
			  </div>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>字典编码</span>:
		    </td>
		    <td class="you"><input type="text" name="rEntity.code" id="rEntity.code" maxlength="60" class="input_size fl" 
		    value="<ww:property value="rEntity.code" />"/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>中文名称</span>:
		    </td>
		    <td class="you"><input type="text" name="rEntity.cnName" id="rEntity.cnName" maxlength="60"  class="input_size fl" 
		    value="<ww:property value="rEntity.cnName" />"/>
		    </td>   
		  </tr> 
		  
		  <tr>
		    <td class="zuo"><span>是否启用</span>:
		    </td>
		    <td>
		      <div class="btt1 fl">
		        <select name="rEntity.isUsed" style="top:12px;height:26px;">
					<option value="1" <ww:if test="rEntity.isUsed==1">selected=true</ww:if> >启用</option>	
					<option value="0" <ww:if test="rEntity.isUsed==0">selected=true</ww:if>>未启用</option>	
				</select>
			  </div>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span>是否系统预制</span>:
		    </td>
		    <td>
		    <input type="hidden" name="rEntity.isSystem" value="<ww:property value="rEntity.isSystem"/>" />
		      <div class="btt1 fl">
		      <ww:if test="id != null && id != ''">
		      	<label>
		      		<ww:if test="rEntity.isSystem==1">是</ww:if>
		      		<ww:if test="rEntity.isSystem==0">否</ww:if>
		      	</label>
		      </ww:if>
		      <ww:else>
		      	<select name="rEntity.isSystem" >
					<option value="0" <ww:if test="rEntity.isSystem==0">selected=true</ww:if>>否</option>	
				</select>
		      </ww:else>
			  </div>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span>颜色值</span>:
		    </td>
		    <td>
		      <div class="btt1 fl">
                 <select name="rEntity.color" id="rEntity.color" >
				  <ww:iterator value="#dictUtil.getDictSelectsByGroupCode('dictOptionColor',3)" id="data" status="rl">
					<option value="<ww:property value="cnName" />"  
					<ww:if test="rEntity.color==cnName">selected=true</ww:if> ><ww:property value="remark" /></option>	
				  </ww:iterator>
			    </select>
			  </div>
		    </td>   
		  </tr>
		  <tr>
		    <td class="zuo"><span>字典备注</span>:
		    </td>
		    <td class="you"><input type="text" name="rEntity.remark" id="rEntity.remark" maxlength="60" class="input_size fl" 
		    value="<ww:property value="rEntity.remark" />"/>
		    </td>   
		  </tr> 
		</table>
	</form>
	</div> 
</body>
</html>