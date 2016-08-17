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
	    id:'useredit', 
	    title:(id == "")?"新添用户":"编辑用户",
		content : 'url:<%=path%>/user/userGet.action?id='+id,
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
		$.post('userDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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

function resetPassword(id){
	alertconfirm("确认要重置密？",function (){
		showLoading();
		$.post('<%=path%>/user/resetPassword.action?id='+id,$('#sform').serialize(),r_reset_password,'json').error(requestError);
	});	
}

function r_reset_password(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok("重置成功！");
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
<body class="SubPage">
<div class="container-fluid">
		<form class="form-horizontal"  name="sform" id="sform" method="post" action="<%=path%>/user/userSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
					
					<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label for="sEntity.name" class="col-xs-4 control-label">真实名称</label>
					<div class="col-xs-8">
						<input class="form-control"name="sEntity.name" id="sEntity.name" type="text" value="<ww:property value="sEntity.name"/>" />
					</div>
				</div>
				
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label for="sEntity.name" class="col-xs-4 control-label"> 登录名</label>
					<div class="col-xs-8">
						<input class="form-control"name="sEntity.loginName" id="sEntity.loginName" type="text" value="<ww:property value="sEntity.loginName"/>" />
					</div>
				</div>
				
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-3 col-xs-4">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			<ww:if test="hasPrivilegeUrl('/user/userGet.action')"><div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button2"  onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
			</div></ww:if>
			<ww:if test="hasPrivilegeUrl('/user/userDelete.action')"><div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button3"  onclick="deleteEntity('');" target="_blank"><i class="fa fa-trash-o"></i>删除</a>
			</div></ww:if>
			
		</div>
	</div>
					
					
					
					
					
					
					
			
<div class="row TableBlock">
 				<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td>
								<input type="checkbox" name="checkdelcheckall"
													onclick="funCheck('','checkdel')">
							</td>
							
							<td>
								<a href="javascript:SetOrder('loginName')">登录名<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('name')">真实姓名<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('phoneNo')">手机号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
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
									<ww:property value="loginName" />
								</td>
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="phoneNo" />
								</td>
								<td align="center">
									<ww:if test="hasPrivilegeUrl('/user/userUpdate.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/user/resetPassword.action')">
										<div class="pan_btn2"  onclick="javascript:resetPassword('<ww:property value="id"/>');">重置密码</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr style="background-color: #fff;height: 30px;">
							<td align="center" colspan="5">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
 			</div>
		</form>
</div>
</body>
</html>