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
<title>发票管理</title>
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

function exportExcel(){
	$.ajax({
		async: false,
		type: "POST",
		url: "<%=path %>/orders/exportOrdersBill.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				startDate=new Date(Number(data.msg));
			}
		}
	});
}

function editBill(id){
	var dialoguser = $.dialog({
	    id:'editOrderBill', 
	    title:"编辑发票管理信息",
		content :'url:<%=path%>/orders/queryOrdersBillDetail.action?ordersBillId='+id,
		fixed:true,
		width:600,
		height:650,
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
		
		function exportExcel(){
			if($("#startTime").val()=="" || $("#endTime").val()==""){
				alert("请选择查询时间");
				return;
			}
			$("#sform").attr("action","<%=path %>/orders/exportOrdersBill.action");
			$("#sform").submit();
			$("#sform").attr("action","");
		}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/orders/invoiceList.action">
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
							<label for="state" class="col-xs-4 control-label">发票状态</label>
							<div class="col-xs-8">
								<select class="form-control"  name="state" id="state">
									<option value=""  <ww:if test='state==""'>selected=true</ww:if> >全部</option>	
									<option value="0"  <ww:if test='state=="0"'>selected=true</ww:if> >发票未开</option>	
									<option value="1"  <ww:if test='state=="1"'>selected=true</ww:if> >审核未通过</option>
									<option value="2"  <ww:if test='state=="2"'>selected=true</ww:if> >审核通过</option>	
									<option value="3"  <ww:if test='state=="3"'>selected=true</ww:if> >发票已开</option>		
								</select>
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-4 col-xs-6"><a class="btn btn-block Button1"   onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					<!-- <div class="col-sm-2 col-xs-6"><a class="btn btn-block Button2" onclick="exportExcel();" target="_blank"><i class="fa fa-floppy-o"></i>导出表格</a></div> -->
				</div>
				
			</div>

			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td>编号</td>
						<td>收件人</td>
						<td>申请时间</td>
						<td>发票金额</td>
						<td>发票编号</td>
						<td>发票抬头</td>
						<td>地址</td>
						<td>快递名称</td>
						<td>快递单号</td>
						<td>寄件时间</td>
						<td>发票状态</td>
						<td>操作人</td>
						<td>操作</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr style="font-size:12px;">
							<td align="center"><ww:property value="#rl.index+1"/></td>
							<td align="center"><ww:property value="recipients" /></td>
							<td align="center"><ww:property value="transDate12String(createDate)" /></td>
							<td align="center"><ww:property value="totalFee" /></td>
							<td align="center"><ww:property value="billNo" /></td>
							<td align="center"><ww:property value="title" /></td>
							<td align="center"><ww:property value="address" /></td>
							<td align="center"><ww:property value="expresName" /></td>
							<td align="center"><ww:property value="expresNo" /></td>
							<td align="center"><ww:property value="transDate12String(sendDate)" /></td>
							<td align="center">
								<ww:if test='state=="0"'>未处理</ww:if>
								<ww:if test='state=="1"'>已申请</ww:if>
								<ww:if test='state=="2"'>审核未通过</ww:if>
								<ww:if test='state=="3"'>审核通过</ww:if>
								<ww:if test='state=="4"'>发票已开</ww:if>
							</td>
							<td align="center"><ww:property value="operatorName" /></td>
							<td align="center">
								<ww:if test="hasPrivilegeUrl('/orders/queryOrdersBillDetail.action')">
									<div class="pan_btn1" onclick="editBill('<ww:property value="id" />');">编辑</div>
								</ww:if>	
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
									<div class="pan_btn2" onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
								</ww:if>	
							</td>
						</tr>
					</ww:iterator>
					<tr>
						<td align="right" colspan="13"><ww:property value="page.pageSplit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>