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
$().ready(function (){
	
		if($("#subscriberCallLevel").val()=="0"){
			$("#subscriberCallLevel").css("color","#337ab7");
		}else if($("#subscriberCallLevel").val()=="1"){
			$("#subscriberCallLevel").css("color","#f0ad4e");
		}else if($("#subscriberCallLevel").val()=="2"){
			$("#subscriberCallLevel").css("color","#d9534f");
		}else{
			$("#subscriberCallLevel").css("color","black");
		}



		$("#subscriberCallLevel").change(function(){ 
			if($(this).val()=="0"){
				$(this).css("color","#337ab7");
			}else if($(this).val()=="1"){
				$(this).css("color","#f0ad4e");
			}else if($(this).val()=="2"){
				$(this).css("color","#d9534f");
			}else{
				$(this).css("color","black");
			}
			
		});

});


function addEntity(id){
	window.location.href='<%=path%>/subscriber/showCallDetailAdd.action';
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


	function showEntity(id){
		window.location.href='<%=path%>/subscriber/showCallDetail.action?subscriberCall.id='+id;
	}
	

	function handleEntity(id){
		window.location.href="<%=path%>/subscriber/showCallHandling.action?subscriberCall.id="+id;
	}
	
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/subscriber/showCallList.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
						
<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-4 control-label">来电号码</label>
					<div class="col-xs-8">
						<input type="text" class="form-control" name="subscriberCall.callPhoneNo" value='<ww:property value="subscriberCall.callPhoneNo" />' />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">类型</label>
					<div class="col-xs-8">
						<select class="form-control"   name="subscriberCall.type" >
							<option value="">全部</option>
							<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('17')" id="data" status="rl">
								<option value="<ww:property value="code" />"   <ww:if test="@java.lang.Integer@parseInt(code)==subscriberCall.type">selected</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-4 control-label">姓名</label>
					<div class="col-xs-8">
						 <input type="text" class="form-control" name="subscriberCall.callName" value='<ww:property value="subscriberCall.callName" />' />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">紧急程度</label>
					<div class="col-xs-8">
						 <select id="subscriberCallLevel"  class="form-control"  name="subscriberCall.level"  >
		      	 			<option value="" style="color:black">全部</option>
							<%-- <option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NOT_URGENT"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NOT_URGENT==subscriberCall.level">selected</ww:if> style="color:#337ab7">稍缓</option> --%>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL==subscriberCall.level">selected</ww:if> style="color:#f0ad4e">正常</option>	
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT==subscriberCall.level">selected</ww:if>  style="color:#d9534f">紧急</option>
					</select>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="company.bizType" class="col-xs-4 control-label">状态</label>
					<div class="col-xs-8">
						<select class="form-control" name="subscriberCall.state" >
							<option>全部</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING==subscriberCall.state">selected</ww:if>>待处理</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING==subscriberCall.state">selected</ww:if>>处理中</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED==subscriberCall.state">selected</ww:if>>已处理</option>
						</select>
					</div>
				</div>
			</div>
			
		</div>
		<div class="row SubmitButtonBlock">
		<ww:if test="hasPrivilegeUrl('/subscriber/showCallList.action')">
			<div class="col-sm-2 col-sm-offset-4 col-xs-6">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
		</ww:if>
		<ww:if test="hasPrivilegeUrl('/subscriber/showCallDetailAdd.action')">
			<div class="col-sm-2 col-xs-6">
				<a class="btn btn-block Button2"  onclick="addEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
			</div>
		</ww:if>
		</div>
	</div>	
						
<div class="row TableBlock">
 				<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td>
								<a href="javascript:SetOrder('a.callPhoneNo')">来电号码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								<a href="javascript:SetOrder('a.callName')">姓名<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							
							<td>
								<a href="javascript:SetOrder('a.type')">类型<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								<a href="javascript:SetOrder('a.level')">紧急程度<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								<a href="javascript:SetOrder('a.state')">状态<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								<a href="javascript:SetOrder('a.callTime')">呼入时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							
							<td>
								主题
							</td>
							
							
							<td>
								操作
							</td>
						</tr>
						<ww:iterator value="page.results" id="data"  status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								 <td>
									<ww:property value="callPhoneNo" />
								</td>
								<td>
									<ww:property value="callName" />
								</td>
								
								<td>
									<ww:property value="@com.dearho.cs.sys.util.DictUtil@getCnNameByCode('17',type)" />
								</td>
								<td>
									<ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NOT_URGENT==level">
										<span class="label label-primary">稍缓</span>
									</ww:if>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_NORMAL==level">
										<span class="label label-warning">正常</span>
									</ww:elseif>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.SubscriberCall@LEVEL_URGENT==level">
										<span class="label label-danger">紧急</span>
									</ww:elseif>
							
								</td>
								<td>
								 
									<ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING==state">待处理</ww:if>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING==state">处理中</ww:elseif>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLED==state">已处理</ww:elseif>
									<ww:else>未知</ww:else>
									
								</td>
								<td>
									<ww:property value="transDateString(callTime)" />
								</td>
								
								<td>
									
									<ww:if test="title.length()>19">
										<ww:property value="title.substring(0, 19)" />...
									</ww:if>
									<ww:else>
										<ww:property value="title" />
									</ww:else>
								</td>
								
								<td>
									<div class="pan_btn1"  onclick="javascript:showEntity('<ww:property value="id"/>','update');">详情</div>
									
									<ww:if test="hasPrivilegeUrl('/subscriber/showCallHandling.action')">
										<ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_WAIT_HANDLING==state">
											<div class="pan_btn3"  onclick="javascript:handleEntity('<ww:property value="id"/>','update');">处理</div>
										</ww:if>
									</ww:if>
									
									<ww:if test="hasPrivilegeUrl('/subscriber/showCallHandling.action')">
										<ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCall@STATE_HANDLING==state">
											<div class="pan_btn3"  onclick="javascript:handleEntity('<ww:property value="id"/>','update');">处理</div>
										</ww:if>
										
									</ww:if>
									
									<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">									
										<div class="pan_btn2" onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path%>');">记录</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr>
							<td colspan="10">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
	</div>
</body>
</html>