<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>扣款记录列表</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	
	

$(document).ready(function(){ 
	
	$("#fromDateTmp").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 4,
		autoclose: true,
		minuteStep: 5,
		format: "yyyy-mm-dd"
	});
	$("#toDateTmp").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 4,
		autoclose: true,
		minuteStep: 5,
		format: "yyyy-mm-dd"
	});
});

	
	//添加车辆弹框
	
	function showEntity(id){
		var dialoguser = $.dialog({
		    id:'editRechargeCarList', 
		    title:"扣款详情",
			content :'url:<%=path%>/account/showCutPaymentRecordDetail.action?rechargeRecord.id='+id,
			fixed:true,
			width:800,
			height:550,
			resize:true,
	 		max: false,
		    min: false,
		    lock: true,
		    ok: false,
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
	
	
	
	function searchEntity(){
		$("#sform").attr("action","<%=path %>/account/showCutPaymentRecordList.action");
		$("#sform").submit();
	}

	
	
	
	function downloadEntity(){
		if($("#fromDateTmp").val()=="" || $("#toDateTmp").val()==""){
			alert("请选择查询时间");
			return;
		}
		$("#sform").attr("action","<%=path %>/account/downloadCutPaymentRecord.action");
		$("#sform").submit();
	};
	
</script>
</head>
<body  class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/account/showCutPaymentRecordList.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				
				<div class="ControlBlock">
				<div class="row SelectBlock">
							<div class="col-xs-4">
							<div class="form-group">
								<label for="fromDateTmp" class="col-xs-4 control-label">起始日期：</label>
								<div class="col-xs-7">
									<input class="form-control TimeSelect" name="fromDate" id="fromDateTmp"   type="text" value='<ww:property value="transDate10String(fromDate)"/>' >
								</div>
							</div>
							
							<div class="form-group">
						 	<label for="rechargeCard.name" class="col-xs-4 control-label">会员手机号：</label>
						 	<div class="col-xs-8">
						 		<input type="text" class="kd form-control" name="rechargeRecord.subscriberPhoneNo" value='<ww:property value="rechargeRecord.subscriberPhoneNo" />' />
						 	</div>
						 </div>
						 
						</div>
						
						
						<div class="col-xs-4">
							<div class="form-group">
								<label for="fromDateTmp" class="col-xs-4 control-label">终止日期：</label>
								<div class="col-xs-7">
									<input class="form-control TimeSelect" name="toDate" id="toDateTmp"   type="text" value='<ww:property value="transDate10String(toDate)"/>' >
								</div>
							</div>
							
							
						<div class="form-group">
						 	<label for="rechargeCard.name" class="col-xs-4 control-label">会员姓名：</label>
						 	<div class="col-xs-8">
						 		<input type="text" class="kd form-control" name="rechargeRecord.subscriberName" value='<ww:property value="rechargeRecord.subscriberName" />' />
						 	</div>
						 </div>
							
							
						</div>
						
					
					 
					
					 
					  <div class="col-sm-4 col-xs-6">
						 <div class="form-group">
						 	<label for="rechargeCard.name" class="col-xs-4 control-label">操作人姓名：</label>
						 	<div class="col-xs-8">
						 		<input type="text" class="kd form-control" name="rechargeRecord.operatorName" value='<ww:property value="rechargeRecord.operatorName" />' />
						 	</div>
						 </div>
						
					 </div>
					
					 
				 </div>
				 	
				 <div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					
					<!--<ww:if test="hasPrivilegeUrl('/account/downloadCutPaymentRecord.action')">
					<div class="col-sm-2 col-xs-6"><a class="btn btn-block Button2" onclick="downloadEntity();" target="_blank"><i class="fa fa-floppy-o"></i>导出表格</a></div>
					</ww:if>-->
					
  				 </div>
			</div>
				
			
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						
						<td  >
							会员名称
						</td>
						<td  >
							会员手机号
						</td>
						<td  >
							金额
						</td>
						<td  >
							操作时间
						</td>
						<td  >
							操作人
						</td>
					
						<td >
							操作
						</td>
					</tr>
			
					<ww:iterator value="page.Results" id="data" status="rl">
						<tr
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
						
							<td >
								<a href="javascript:showSubscriberDetailForDialog('<ww:property value="subscriberId" />','<%=path%>')">
								<ww:property value="subscriberName" />
								</a>
							</td>
							
							<td >
								<ww:property value="subscriberPhoneNo" />
							</td>
							
							<td>
								<ww:property value="rechargeAmount" />
							</td>
							
							<td >
								<ww:property value="transDateString(createTime)" />
							</td>
							<td >
								<ww:property value="operatorName" />
							</td>
							
							<td >
								
								<ww:if test="hasPrivilegeUrl('/account/showCutPaymentRecordDetail.action')">
									<div class="pan_btn3"  onclick="showEntity('<ww:property value="id"/>');">详情</div>
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr >
						<td align="center" colspan="12">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
			</form>
			</div>
</body>
</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                