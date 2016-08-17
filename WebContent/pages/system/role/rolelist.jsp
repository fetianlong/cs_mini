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
function searchEntity(){
	$('#sform').submit();		
}
function editEntity(id){
	var dialoguser = $.dialog({
	    id:'roleedit', 
	    title:(id == "")?"新添角色":"编辑角色",
		content : 'url:<%=path%>/role/roleGet.action?id='+id,
		fixed:true,
		width:550,
		height:300,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    ok: function(){
	    	var valid = this.content.isValid();
	    	if (valid){
	    		var form = this.content.getForm();
	    		showLoading(parent);
	    		$.post(form.attr("action"),form.serialize(),r_savedata,'json').error(requestError);
	    	}
	    	return false;
	    },
	    okVal:isnull(id)?'添加':'保存',
	    cancelVal: '关闭',
	    cancel: true,
	    close: function () {
	        this.hide();
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
	
}

function r_savedata(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok("保存成功！", function(){
				$('#sform').submit();	
		    });
			break;
		case 1:
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
	return false;
}
function deleteEntity(){
	var ob = document.getElementsByName("checkdel");
	var check = false;
	for (var i = 0; i < ob.length; i++) {
		if (ob[i].checked) {
			check = true;
			break;
		}
	}
	if (!check) {
		alertinfo("请选择要删除的数据！");
		return false;
	}
	alertconfirm("确认删除选中的数据吗？",function (){
		showLoading();
		$.post('roleDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
	});	
}
function r_delete(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok("删除成功！", function(){
		    	$('#sform').submit();		
		    });
			break;
		case 1:
			restoreInfo();
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
}
</script>
</head>
<body>
			<form style="margin-left: 10px" name="sform" id="sform" method="post" action="<%=path%>/role/roleSearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				<div class="chazhao">
				   <div class="nr fl"><span>角色名：</span>
				   <input class="kd" name="rEntity.name" id="rEntity.name" type="text" value="<ww:property value="rEntity.name"/>"></div>
				   <a class="find_btn"  onclick="searchEntity();" target="_blank">查&nbsp;&nbsp;询</a>
				   <ww:if test="hasPrivilegeUrl('/role/roleGet.action')"><a class="add_btn" onclick="editEntity('');" target="_blank">添&nbsp;&nbsp;加</a></ww:if>
  				   <ww:if test="hasPrivilegeUrl('/role/roleDelete.action')"><a class="del_btn" onclick="deleteEntity();" target="_blank">删&nbsp;&nbsp;除</a></ww:if>
				</div>
				<div  style="width:100%; float:left;">
					<table class="biaoge" border="0" cellspacing="0">
						<tr class="ths" id="tab_bg_cl">
							<td  width="68" height="50">
								<input type="checkbox" name="checkdelcheckall"
													onclick="funCheck('','checkdel')">
							</td>
							
							<td  width="50">
								<a href="javascript:SetOrder('name')">角色名<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('desc')">角色描述<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  width="157">
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td align="center">
									<input type="checkbox" name="checkdel" 
										value="<ww:property value="id"/>">
								</td>
								
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="desc" />
								</td>
								<td align="center">
									<ww:if test="hasPrivilegeUrl('/role/roleGet.action')">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div></ww:if>
									<div class="pan_btn4"  onclick="">授权</div>
								</td>
							</tr>
						</ww:iterator>
						<tr style="background-color: #fff;height: 30px;">
							<td align="center" colspan="4">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
</body>
</html>