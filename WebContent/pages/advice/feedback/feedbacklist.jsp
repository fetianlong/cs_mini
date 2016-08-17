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
	window.location.href="<%=path%>/feedback/getFeedbackById.action?id="+id;
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
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/feedback/queryFeedbacks.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
					<div class="ControlBlock">
		<div class="row SelectBlock">
		<div class="col-xs-4">
				<div class="form-group">
					<label for="feedback.subscriberId" class="col-xs-4 control-label">会员ID</label>
					<div class="col-xs-8">
						<input class="form-control" name="feedback.subscriberId" id="feedback.subscriberId" type="text" value="<ww:property value="feedback.subscriberId"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="feedback.name" class="col-xs-4 control-label">会员姓名</label>
					<div class="col-xs-8">
						<input class="form-control" name="feedback.name" id="feedback.name" type="text" value="<ww:property value="feedback.name"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="feedback.contactType" class="col-xs-4 control-label">联系方式</label>
					<div class="col-xs-8">
						 <input class="form-control" name="feedback.contactType" id="feedback.contactType" type="text" value="<ww:property value="feedback.contactType"/>">
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label for="feedback.state" class="col-xs-4 control-label">投诉状态</label>
					<div class="col-xs-8">
						<select class="form-control" name="feedback.state" id="feedback.state">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('dealState',1)" id="data" status="rl">
								<option value="<ww:property value="code" />"  <ww:if test="feedback.state==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
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
								会员ID
							</td>
							<td >
								会员姓名
							</td>
							<td >
								联系方式
							</td>
							<td >
								反馈意见
							</td>
							<td >
								投诉时间
							</td>
							<td >
								投诉状态
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
									<ww:property value="subscriberId" />
								</td>
								<td>
									<ww:property value="name" />
								</td>
								<td>
									<ww:property value="contactType" />
								</td>
								<td>
									<ww:property value="feedbackDesc" />
								</td>
								<td>
									<ww:property value="transDateString(ts)" />
								</td>
								<td>
									<ww:property value=" #dictUtil.getDictMapByGroupCode('dealState').get(state)" />
								</td>
								
								<td>
									<ww:if test="hasPrivilegeUrl('/feedback/updateFeedbackState.action')">
										<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
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