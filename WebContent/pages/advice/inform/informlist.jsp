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
	window.location.href="<%=path%>/feedback/getInformById.action?id="+id;
}
function editEntity1(id){
	var dialoguser = $.dialog({
	    id:'informedit', 
	    title:(id == "")?"新增公司企业":"编辑公司企业",
		content : 'url:<%=path%>/feedback/getInformById.action?id='+id,
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
	alertconfirm("确认删除选中的【系统消息】吗？",function (){
		showLoading();
		$.post('delInform.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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
		$.post('delInform.action',pars,r_delete,'json').error(requestError);
	});	
}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/feedback/queryInforms.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
					<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-xs-4">
				<div class="form-group">
					<label for="inform.informContent" class="col-xs-4 control-label">消息内容</label>
					<div class="col-xs-8">
						<input class="form-control" name="inform.informContent" id="inform.informContent" type="text" value="<ww:property value="inform.informContent"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="inform.informType" class="col-xs-4 control-label">消息类型</label>
					<div class="col-xs-8">
						 	<select class="form-control" name="inform.informType" id="inform.informType">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('informType',1)" id="data" status="rl">
								<option value="<ww:property value="code" />"  <ww:if test="(inform.informType+'')==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select> 
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="inform.informSendType" class="col-xs-4 control-label">发送类型</label>
					<div class="col-xs-8">
						<select class="form-control" name="inform.informSendType" id="inform.informSendType">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('informSendType',1)" id="data" status="rl">
								<option value="<ww:property value="code" />"  <ww:if test="(inform.informSendType+'')==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
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
			<ww:if test="hasPrivilegeUrl('/feedback/addInform.action')"><div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button2"  onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
			</div></ww:if>
			<%-- <ww:if test="hasPrivilegeUrl('/feedback/delInformByIds.action')"><div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button3"  onclick="deleteEntity('');" target="_blank"><i class="fa fa-trash-o"></i>删除</a>
			</div></ww:if> --%>
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
								消息内容
							</td>
							<td >
								消息类型
							</td>
							<td >
								发送类型
							</td>
							<td >
								添加时间
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
									<ww:property value="informContent" />
								</td>
								<td>
									<ww:property value="#dictUtil.getCnNameByCode('informType',informType)" />
								</td>
						
								<td>
									<ww:property value="#dictUtil.getCnNameByCode('informSendType',informSendType)" />
								</td>
								<td>
									<ww:property value="transDateString(ts)" />
								</td>
								
								<td>
									<ww:if test="hasPrivilegeUrl('/feedback/updateInform.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">查看</div>
									</ww:if>
									<%-- <ww:if test="hasPrivilegeUrl('/feedback/delInformById.action')">
										<div class="pan_btn4"  onclick="javascript:deleteOneEntity('<ww:property value="id"/>','<ww:property value="informContent" />');">删除</div>
									</ww:if> --%>
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