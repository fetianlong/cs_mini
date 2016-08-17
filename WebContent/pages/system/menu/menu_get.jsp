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
	var id = '<ww:property value="menu.menuId" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/menu/menuAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/menu/menuUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"menu.menuPid": {
				required: true
			},
			"menu.menuName":{
				required: true
			},
			"menu.menuType":{
				required: true
			}
		},
		messages: {
			"menu.menuPid": {
				required: "请输入父节点ID"
			},
			"menu.menuPid": {
				required: "请输入菜单名称"
			},
			"menu.menuType":{
				required: "请选择菜单类型"
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
	
		<form   name="eform" id="eform" method="post" action="" >	
		
	
			<input type="hidden" value='<ww:property value="menu.menuId"/>' name="menu.menuId"/>
			
			<table class="t1" >
			
			<tr class="trr">
		   		     <th ><span >父节点ID：</span>
					</th>
					<td>
					
					
					<select name="menu.menuPid" class="input_size"  > 
					<option></option>
						<ww:iterator value="getAllParentMenu()" id="data" status="rl" >
							<option value="<ww:property value="menuId" />"  <ww:if test="menu.menuPid==menuId">selected=true</ww:if> ><ww:property value="menuName" /></option>	
						</ww:iterator>
					</select>
		    		</td> 
				</tr>
			
				<tr class="trr" >
					<th >
						<span ><span class="rr red">*</span>名称：</span>
					</th>
					<td>
						<input type="text"    value='<ww:property value="menu.menuName"/>' name="menu.menuName"  class="input_size"    />
						
		    		</td> 
				</tr>
				 <tr class="trr">
		   		     <th width="100"><span >URL：</span>
					</th>
					<td>
					<input type="text"  maxlength="120" value='<ww:property value="menu.menuUrl"/>' name="menu.menuUrl"  class="input_size" />
		    		</td> 
				</tr>
				
				 <tr class="trr">
		   		     <th width="100"><span >类型：</span>
					</th>
					<td>
						<select  name="menu.menuType" class="input_size"  >
							<option value="M" <ww:if test='"M".equals(menu.menuType)'>selected="selected"</ww:if> >菜单 </option>
							<option value="F" <ww:if test='"F".equals(menu.menuType)'>selected="selected"</ww:if> >功能 </option>
						</select>
		    		</td> 
				</tr>
				
				
				<tr class="trr">
		   		     <th width="100"><span >顺序：</span>
					</th>
					<td>
					<input type="text"  maxlength="120" value='<ww:property value="menu.menuOrder"/>' name="menu.menuOrder" size="30" class="input_size"  />
		    		</td> 
				</tr>
				
				
				
			</table>
		</form>
	</div>
</body>
</html>