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
<title>操作日志管理</title>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript">
$(function(){
	/*时间选择*/
	$("#sform .TimeSelect").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 4,
		autoclose: true,
		minuteStep: 5,
		format: "yyyy-mm-dd"
	});
});
function searchEntity(){
	$("#sform").submit();
}

function editBill(id){
	var dialoguser = $.dialog({
	    id:'editOrderBill', 
	    title:"编辑发票管理信息",
		content :'url:<%=path%>/orders/queryOrdersBillDetail.action?ordersBillId='+id,
		fixed:true,
		width:500,
		height:550,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    ok: function(){
	    	//var valid = this.content.isValid();
	    	//if (valid){
	    		var form = this.content.getForm();
	    		showLoading(parent);
	    		$.post(form.attr("action"),form.serialize(),r_savedata,'json').error(requestError);
	    	//}
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
				alertok(data.msg, function(){
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
<body class="SubPage czrzglPage">
	<div class="container-fluid">
		<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/systemutil/sysOperateLogRecord.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"value="<ww:property value="page.orderFlag"/>"> 
			<input type="hidden" name="page.orderString" id="page.orderString" value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						<div class="form-group">
							<label for="startTime" class="col-xs-4 control-label">开始时间</label>
							<div class="col-xs-8">
		    					<input type="text" class="form-control TimeSelect" name="startTime" id="startTime" value="<ww:property value="startTime"/>">
							</div>
						</div>
					</div>

					<div class="col-xs-4">
						<div class="form-group">
							<label for="endTime" class="col-xs-4 control-label">结束时间</label>
							<div class="col-xs-8">
								<input type="text" class="form-control TimeSelect" name="endTime" id="endTime" value="<ww:property value="endTime"/>">
							</div>
						</div>
					</div>

					<div class="col-xs-4">
						<div class="form-group">
							<label for="operateRemark" class="col-xs-4 control-label">操作类型</label>
							<div class="col-xs-8">
								<select class="form-control"  name="operateRemark" id="operateRemark">
									<option value=""  <ww:if test='operateRemark==""'>selected=true</ww:if> >全部</option>	
									<option value="add"  <ww:if test='operateRemark=="add"'>selected=true</ww:if> >新增操作</option>	
									<option value="delete"  <ww:if test='operateRemark=="delete"'>selected=true</ww:if> >删除操作</option>	
									<option value="update"  <ww:if test='operateRemark=="update"'>selected=true</ww:if> >修改操作</option>	
									<option value="login"  <ww:if test='operateRemark=="login"'>selected=true</ww:if> >登录操作</option>	
								</select>
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-5 col-xs-6 col-xs-offset-3">
						<a class="btn btn-block Button1" onclick="searchEntity();"
							target="_blank"><i class="fa fa-search"></i>查询</a>
					</div>
				</div>
			</div>

			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td class="xh">序号</td>
						<td class="czr">操作人</td>
						<td class="czmk">操作模块</td>
						<td class="czsj">操作对象</td>
						<td class="czsj">操作时间</td>
						<td class="czlx">操作类型</td>
						<td>操作内容</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr style="font-size:12px;">
							<td><ww:property value="#rl.index+1"/></td>
							<td><ww:property value="operatorName" /></td>
							<td><ww:property value="modelName" /></td>
							<td><ww:property value="keyword" /></td>
							<td><ww:property value="transDate12String(operateDate)" /></td>
							<td>
								<ww:if test='operateRemark=="add"'>添加</ww:if>
								<ww:if test='operateRemark=="delete"'>删除</ww:if>
								<ww:if test='operateRemark=="update"'>修改</ww:if>
								<ww:if test='operateRemark=="login"'>登录</ww:if>
							</td>
							<td  class="cznr" title='<ww:property value="operateContent" />'><span><ww:property value="operateContent" /></span></td>
						</tr>
					</ww:iterator>
					<tr>
						<td align="right" colspan="7"><ww:property value="page.pageSplit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>