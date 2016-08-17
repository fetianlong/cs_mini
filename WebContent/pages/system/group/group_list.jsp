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
	    title:(id == "")?"新添角色":"编辑角色",
		content : 'url:<%=path%>/group/gotoGroupUpdate.action?group.groupId='+id,
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

function editPrivilege(id){
	var dialoguser = $.dialog({
	    id:'privilegeEdit', 
	    title:"授权",
		content : 'url:<%=path%>/group/groupGetPrivilege.action?group.groupId='+id,
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
	    okVal:'保存',
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
		$.post('<%=path %>/group/groupDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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
<body class="SubPage">
<div class="container-fluid">
		<form class="form-horizontal"  name="sform" id="sform" method="post" action="<%=path %>/group/groupSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
					
				<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label for="sEntity.name" class="col-xs-4 control-label">角色名</label>
					<div class="col-xs-8">
						<input class="form-control" name="sEntity.name" id="sEntity.name" type="text" value="<ww:property value="sEntity.name"/>" />
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-3 col-xs-4">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			<ww:if test="hasPrivilegeUrl('/group/groupAdd.action')">
			<div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button2"  onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
			</div>
			</ww:if>
			<ww:if test="hasPrivilegeUrl('/group/groupDelete.action')">
			<div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button3"  onclick="deleteEntity('');" target="_blank"><i class="fa fa-trash-o"></i>删除</a>
			</div>
			</ww:if>
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
								<a href="javascript:SetOrder('name')"> 角色<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('password')">备注<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.results"   status="rl">
					<tr style="font-size:12px;">
							<td width="3%" style="text-align: center;"><input  name="checkdel" value='<ww:property value="groupId"/>' type="checkbox"></td>
							<td width="10%" style="text-align: center;"><ww:property value="groupName"/></td>
							<td width="20%" style="text-align: center;"><ww:property value="groupRemark"/></td>
							<td width="20%" style="text-align: center;">
							
							<ww:if test="hasPrivilegeUrl('/group/groupUpdate.action')">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="groupId"/>');">编辑</div>
							</ww:if>
							<ww:if test="hasPrivilegeUrl('/group/groupAddPrivilege.action')">
									<div class="pan_btn4"  onclick="javascript:editPrivilege('<ww:property value="groupId"/>');">授权</div>
							</ww:if>
							</td>
							
							
		
					</tr>
		
		
		</ww:iterator>
						<tr>
							<td align="center" colspan="4">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
 			</div>
		</form>
</div>
</body>
</html>