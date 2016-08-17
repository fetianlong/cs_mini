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
function searchEntity(){
	$('#sform').submit();		
}
function editEntity(id){
	window.location.href="<%=path%>/company/companyGet.action?id="+id;
}
function editEntity1(id){
	var dialoguser = $.dialog({
	    id:'companyedit', 
	    title:(id == "")?"新增公司企业":"编辑公司企业",
		content : 'url:<%=path%>/company/companyGet.action?id='+id,
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
	alertconfirm("确认删除选中的【公司企业】吗？",function (){
		showLoading();
		$.post('companyDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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
function deleteOneEntity(id,name){
	var pars={
			"id":id
		};
	alertconfirm("确认删除选中的【"+name+"】吗？",function (){
		showLoading();
		$.post('companyDelete.action',pars,r_delete,'json').error(requestError);
	});	
}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/company/companySearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
					<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-xs-4">
				<div class="form-group">
					<label for="company.code" class="col-xs-4 control-label">公司编码</label>
					<div class="col-xs-8">
						<input class="form-control" name="company.code" id="company.code" type="text" value="<ww:property value="company.code"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="company.name" class="col-xs-4 control-label">公司名称</label>
					<div class="col-xs-8">
						 <input class="form-control" name="company.name" id="company.name" type="text" value="<ww:property value="company.name"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="company.bizType" class="col-xs-4 control-label">公司类型</label>
					<div class="col-xs-8">
						<select class="form-control" name="company.bizType" id="company.bizType">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('companyBizType',1)" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="company.bizType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-3 col-xs-4">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			<ww:if test="hasPrivilegeUrl('/company/companyAdd.action')"><div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button2"  onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
			</div></ww:if>
			<ww:if test="hasPrivilegeUrl('/company/companyDelete.action')"><div class="col-sm-2 col-xs-4">
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
							<td >
								<a href="javascript:SetOrder('code')">编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								<a href="javascript:SetOrder('name')">名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
						<!--		<td >
								地址
							</td>
					  	<td >
								经营范围
							</td>
							-->
							<td >
								负责人
							</td>
							<td >
								公司电话
							</td>
							<td >
								联系人姓名
							</td>
							<td >
								联系人电话
							</td>
							<td >
								服务开始日期
							</td>
							<td >
								服务结束日期
							</td>
							<td >
								类型
							</td>
							<td  width="157">
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td>
									<input type="checkbox" name="checkdel" 
										value="<ww:property value="id"/>">
								</td>
								<td>
									<ww:property value="code" />
								</td>
								<td>
									<ww:property value="name" />
								</td>
							<!--	<td>
									<ww:property value="address" />
								</td>
								  
								<td>
									<ww:property value="bizScope" />
								</td>
								-->
								<td>
									<ww:property value="chairman" />
								</td>
								<td>
									<ww:property value="telephone" />
								</td>
								<td>
									<ww:property value="linkman" />
								</td>
								<td>
									<ww:property value="linkmanTelephone" />
								</td>
								<td>
									<ww:property value="transDate10String(serviceStart)" />
								</td>
								<td>
									<ww:property value="transDate10String(serviceEnd)" />
								</td>
								<td>
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('companyBizType',bizType)" />
								</td>
								
								<td>
									<ww:if test="hasPrivilegeUrl('/company/companyUpdate.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/company/companyDelete.action')">
										<div class="pan_btn4"  onclick="javascript:deleteOneEntity('<ww:property value="id"/>','<ww:property value="name" />');">删除</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr>
							<td colspan="13">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
</div>
</body>
</html>