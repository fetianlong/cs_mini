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
	
	
	//添加RFID弹框
	function editEntity(id){
		var dialoguser = $.dialog({
		    id:'carList', 
		    title:"交易记录",
			content : 'url:<%=path%>/account/showAccountTrading.action?type=show&accountTradeRecord.subscriberId='+id,
			fixed:true,
			width:720,
			height:500,
			resize:false,
	 		max: false,
		    min: false,
		    lock: true,
		    ok: false,
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
	

	function searchEntity(){
		$("#sform").submit();
	}



	function editEntitySkip(id){
		window.location.href="<%=path%>/account/showAccountTrading.action?type=show&accountTradeRecord.subscriberId="+id;
	}
	
	

</script>
</head>
<body  class="SubPage">
<div class="container-fluid">

			<form class="form-horizontal" name="sform"  id="sform" method="post" action="<%=path%>/account/showAccountList.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
						
					<div class="ControlBlock">
					<div class="row SelectBlock">
						<div class="col-md-4">
							<div class="form-group">
								<label for="subscriber.phoneNo" class="col-sm-4 control-label">手机号</label>
								<div class="col-sm-8">
									<input class="form-control" name="subscriber.phoneNo" id="subscriber.phoneNo"  maxlength="11" type="text" value="<ww:property value="subscriber.phoneNo"/>">
								</div>
							</div>
						</div>
					</div>
					<div class="row SubmitButtonBlock">
						<div class="col-sm-2 col-sm-offset-5">
						<ww:if test="hasPrivilegeUrl('/account/showAccountList.action')">
							<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询1</a>
						</ww:if>
						</div>
						
					</div>
				</div>
				
				
				<div class="row TableBlock">
					<table class="table table-striped table-bordered table-condensed" >
						<tr class="ths" id="tab_bg_cl">
							<td >
								<a href="javascript:SetOrder('s.phoneNo')">手机号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								姓名
							</td>
							<td >
								状态
							</td>
							<td >
								账户金额
							</td>
							
							
							<td >
								最新交易金额
							</td>
							<td >
								最新交易时间
							</td>
							<td >
								最新交易类型
							</td>
							
							
							
							<td width="140">
								操作
							</td>
						</tr>
						<ww:iterator value="page.results" id="data"  status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
								<td align="center">
									<ww:property value="phoneNo" />
								</td>
								
								<td align="left"><a href="javascript:showSubscriberDetailForDialog('<ww:property value="id" />','<%=path%>')"><ww:property value="name" /></a></td>
								<td align="left"><ww:property  value="@com.dearho.cs.subscriber.pojo.Subscriber@getState(state)"/></td>
								<td align="right">
									<ww:property value="formatAmount(account.amount)" />
								</td>
								
								<td align="right"> <ww:property value="formatAmount(account.lastOperateAmount)"/></td>
								<td align="center"><ww:property value="transDateString(account.lastOperateTime)"/> </td>
								<td align="left">
									<ww:property value="@com.dearho.cs.account.pojo.Account@getTradeType(account.lastOperateType)"/>
								</td>
								<td>
									<ww:if test="hasPrivilegeUrl('/account/showAccountTrading.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">交易记录</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr >
							<td align="right"  colspan="10">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
			
</div>
</body>
</html>