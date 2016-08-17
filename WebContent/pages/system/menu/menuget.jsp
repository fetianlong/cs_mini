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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加或者删除组</title>
<script type="text/javascript">
$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/common/menuAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/common/menuUpdate.action');	
	}
	
});

</script>
</head>
<body>
<div class="dialogContent layoutBox unitBox" style="height: 541px;">

	<div class="pageContent" style="width: 566px;">
		<form id="eform" onsubmit="return validateCallback(this, addMenudialogAjaxDone);" class="pageForm required-validate" action="" method="post" novalidate="novalidate">
			<div layouth="56" class="pageFormContent" style="height: 485px; overflow: auto;">
				<input type="hidden" value="<ww:property value="menu.menuId" />" name="menu.menuId">
				<p>
					<label>父级菜单：</label> 
					<select name="menu.menuPid">
						<ww:iterator value="getAllParentMenu()" id="data" status="rl">
							<option value="<ww:property value="menuId" />"  <ww:if test="menu.menuPid==menuId">selected=true</ww:if> ><ww:property value="menuName" /></option>	
						</ww:iterator>
					</select>
				</p>
				<p>
					<label>菜单名称：</label> <input type="text" class="textInput" size="25" value="<ww:property value="menu.menuName" />" name="menu.menuName">
				</p>
				<p>
					<label>url：</label> <input type="text" class="textInput" size="25" value="<ww:property value="menu.menuUrl" />" name="menu.menuUrl">
				</p>
				<p>
					<label>类型：</label> 
					<select name="menu.menuType" >
						<option value="F"  <ww:if test="menu.menuType==\"F\"">selected=true</ww:if> >按钮</option>	
						<option value="M"  <ww:if test="menu.menuType==\"M\"">selected=true</ww:if> >菜单</option>	
					</select>
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button class="close" type="button">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>

</div>
</body>
</html>