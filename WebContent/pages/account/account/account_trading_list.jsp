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
	
$(document).ready(function(){ 
	searchTrading();
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



	


	function skipToPage(page, func){
		document.getElementById('page.currentPage').value=page;
		if (typeof func == 'function'){
			func();
		}else{
			searchTrading();
		};
	}

	function searchTrading(){
		
		var pars = $("#sform").serialize();
		$.ajax({
		  url: '<%=path%>/account/showAccountTrading.action',
	    data: pars,
	    type: 'POST',
		  error: requestError,
		  dataType: 'html',
		  success: function(data){
		 		$("#showdiv").html(data);
		  }
		});
	}
	
	
	function showEntity(id){
		var dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'交易详情',
			content : 'url:<%=path%>/account/showAccountTradingDetail.action?accountTradeRecord.id='+id,
			fixed:true,
			width:600,
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
	
	
	function downloadEntity(){
		if($("#fromDateTmp").val()=="" || $("#toDateTmp").val()==""){
			alert("请选择查询时间");
			return;
		}
		$("#sform").attr("action","<%=path %>/account/downloadAccountTrading.action");
		$("#sform").submit();
	};
		

</script>
</head>
<body class="SubPage" >
<div class="container-fluid">
			<form  class="form-horizontal"  name="sform"  id="sform" method="post" action="<%=path%>/account/showAccountTrading.action">
				<input type="hidden" name="accountTradeRecord.subscriberId" value='<ww:property value="accountTradeRecord.subscriberId"/>'>
			
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
						
				
  				
  				
  				<div class="ControlBlock">
					<div class="row SelectBlock">
						<div class="col-xs-4">
							<div class="form-group">
								<label for="fromDateTmp" class="col-xs-5 control-label">起始日期</label>
								<div class="col-xs-7">
									<input class="form-control TimeSelect" name="fromDate" id="fromDateTmp"   type="text" value='<ww:property value="transDate10String(fromDate)"/>' >
								</div>
							</div>
						</div>
						<div class="col-xs-4">
							<div class="form-group">
								<label for="fromDateTmp" class="col-xs-5 control-label">终止日期</label>
								<div class="col-xs-7">
									<input class="form-control TimeSelect" name="toDate" id="toDateTmp"   type="text" value='<ww:property value="transDate10String(toDate)"/>' >
								</div>
							</div>
						</div>
						
						<div class="col-xs-4">
							<div class="form-group">
								<label for="accountTradeRecord.type" class="col-xs-5 control-label">交易类型</label>
								<div class="col-xs-7">
									<select class="form-control"   name="accountTradeRecord.type" id="accountTradeRecord.type">
										<option value="">全部</option>
										<option value="1">充值</option>
										<option value="2">退款</option>
										<option value="5">租车</option>
									</select>
									
								</div>
							</div>
						</div>
						
					</div>
					
					
					
					<div class="row SubmitButtonBlock">
						<ww:if test="hasPrivilegeUrl('/account/showAccountTrading.action')">
							<div class="col-sm-2  col-sm-offset-4 col-xs-6">
								<a class="btn btn-block Button1"  onclick="searchTrading();" target="_blank"><i class="fa fa-search"></i>查询</a>	
							</div>
						</ww:if>
					<!--  	<ww:if test="hasPrivilegeUrl('/account/downloadAccountTrading.action')">
							<div class="col-sm-2 col-xs-6"><a class="btn btn-block Button2" onclick="downloadEntity();" target="_blank"><i class="fa fa-floppy-o"></i>导出表格</a></div>
						</ww:if>-->
					</div>
				</div>
  				
  				
				<div id="showdiv" class="row TableBlock">
					
				</div>
			</form>
</div>
</body>
</html>