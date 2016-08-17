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
	var id = '<ww:property value="group.groupId" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/group/groupAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/group/groupUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"group.groupName": {
				required: true,
				maxlength : 60
			}
		},
		messages: {
			"group.groupName": {
				required: "请输入角色名称！",
				maxlength: "用户名称过长，最大为60个字符！"
			}
		}
	});
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
<body >
	<div class="table_con tanchuang" >
	
			<!-- 添加-->
		<form   name="eform" id="eform" method="post" action="" >	
		
			<table class="t1" >
				<tr class="trr" >
					<th >
						<span >角色名称：</span>
					</th>
					<td>
						<input type="text"    value='<ww:property value="group.groupName"/>' name="group.groupName"  class="input_size"    /><span class="rr red">*</span>
						<input type="hidden" value='<ww:property value="group.groupId"/>' name="group.groupId"/>
		    		</td> 
				</tr>
				 <tr class="trr">
		   		     <th ><span >备注：</span>
					</th>
					<td>
					<input type="text"  maxlength="120" value='<ww:property value="group.groupRemark"/>' name="group.groupRemark" size="30" class="input_size"  />
		    </td> 
				</tr>
			</table>
		</form>
	</div>
</body>
</html>