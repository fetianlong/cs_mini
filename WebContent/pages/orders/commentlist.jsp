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
<title>评论管理</title>
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
	
	function deleteComment(id){
		var pars={
				"id":id
			};
		alertconfirm("确定要删除这条评论吗？",function (){
			showLoading();
			$.post('<%=path%>/orders/ordersCommentDelete.action',pars,r_cancel,'json').error(requestError);
		});	
	}
	
	function r_cancel(data){
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
	function openAudit(id,comment){
		$.dialog({
		    id: 'auditComment',
		    content: '评论内容：'+comment,
		    title:'评论审核',
		    button: [
		        {
		            name: '通过',
		            callback: function(){
		            	showLoading();
		            	var pars={
		        				"id":id,
		        				"auditType":'ok'
		        			};
		            	$.post('<%=path%>/orders/ordersCommentAudit.action',pars,function(data){
		            		hideLoading();
		            		if(data.result == 0 ){$("#sform").submit();}
		            		else{
		            			alerterror(data.msg);
		            		}
		            		
		            	},'json').error(requestError);
		            },
		            focus: true
		        },
		        {
		            name: '不通过',
		            callback: function(){
		            	showLoading();
		            	var pars={
		        				"id":id,
		        				"auditType":'faile'
		        			};
		            	$.post('<%=path%>/orders/ordersCommentAudit.action',pars,function(data){
		            		hideLoading();
		            		if(data.result == 0 ){$("#sform").submit();}
		            		else{
		            			alerterror(data.msg);
		            		}
		            	},'json').error(requestError);
		            }
		        },
		        {
		            name: '取消'
		        }
		    ]
		});


	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form class="form-horizontal" name="sform" id="sform" method="post"
			action="<%=path%>/orders/ordersCommentSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
				value="<ww:property value="page.orderFlag"/>"> <input
				type="hidden" name="page.orderString" id="page.orderString"
				value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						<div class="form-group">
							<label for="startTime" class="col-xs-4 control-label">开始时间</label>
							<div class="col-xs-8">
		    					<input type="text" class="form-control TimeSelect" name="startTime" id="startTime" value="<ww:property value="startTime"/>">
							</div>
						</div>
						<div class="form-group">
							<label for="ordersComment.commentPersonName" class="col-xs-4 control-label">评论人</label>
							<div class="col-xs-8">
								<input class="form-control" name="ordersComment.commentPersonName"
									id="ordersComment.commentPersonName" type="text"
									value="<ww:property value="ordersComment.commentPersonName"/>">
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
							<label for="ordersComment.auditState" class="col-xs-4 control-label">审核状态</label>
							<div class="col-xs-8">
								<select class="form-control"   name="ordersComment.auditState" id="ordersComment.auditState">
									<option value="" >全部</option>	
				 					<option value="0"  <ww:if test="ordersComment.auditState.equals(\"0\")">selected=true</ww:if> >未审核</option>	
									<option value="1"  <ww:if test="ordersComment.auditState.equals(\"1\")">selected=true</ww:if> >审核通过</option>	
									<option value="2"  <ww:if test="ordersComment.auditState.equals(\"2\")">selected=true</ww:if> >审核未通过</option>	
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
						<td><a href="javascript:SetOrder('ordersNo')">订单编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td><a href="javascript:SetOrder('auditState')">评论状态<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td><a href="javascript:SetOrder('commentTime')">评论时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td>评论内容</td>
						<td>评论人</td>
						<td>操作</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center"><ww:property value="ordersNo" /></td>
							<td align="center">
								<ww:if test="auditState.equals(\"0\")">未审核</ww:if>
								<ww:if test="auditState.equals(\"1\")">审核通过</ww:if>
								<ww:if test="auditState.equals(\"2\")">审核未通过</ww:if>
							</td>
							<td align="center"><ww:property value="transDate12String(commentTime)" /></td>
							<td align="center"><ww:property value="commentContent" /></td>
							<td align="center"><ww:property value="commentPersonName" /></td>
							<td align="center">
								<ww:if test="auditState.equals(\"0\")">
									<div class="pan_btn1"  onclick="javascript:openAudit('<ww:property value="id"/>','<ww:property value="commentContent" />');">审核 </div>	
								</ww:if>	
								<div class="pan_btn4"  onclick="javascript:deleteComment('<ww:property value="id"/>');">删除 </div>
							</td>
						</tr>
					</ww:iterator>
					<tr>
						<td align="right" colspan="6"><ww:property
								value="page.pageSplit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>