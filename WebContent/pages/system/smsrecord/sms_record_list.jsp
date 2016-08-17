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
function searchEntity(){
	$('#sform').submit();		
}

function showEntity(id){
	var dialoguser = $.dialog({
	    id:'SMSRecorddialog', 
	    title:'详情',
		content : 'url:<%=path%>/smsRecord/smsRecordGet.action?smsRecord.id='+id,
		resize:false,
		fixed:true,
		width:500,
		height:400,
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
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
		<form class="form-horizontal"  name="sform" id="sform" method="post" action="<%=path %>/smsRecord/smsRecordSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
					
				<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label for="sEntity.name" class="col-xs-4 control-label">手机号</label>
					<div class="col-xs-8">
						<input class="form-control" name="smsRecord.phoneNo" id="smsRecord.phoneNo" type="text" value="<ww:property value="smsRecord.phoneNo"/>" />
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label for="sEntity.name" class="col-xs-4 control-label">类型</label>
					<div class="col-xs-8">
						
						<select class="form-control"   name="smsRecord.type" >
							<option value="">全部</option>
							<option value="1" <ww:if test="1==smsRecord.type">selected</ww:if>>注册</option>
							<option value="2" <ww:if test="2==smsRecord.type">selected</ww:if>>审核</option>
							<option value="3" <ww:if test="3==smsRecord.type">selected</ww:if>>订单</option>
							<option value="4" <ww:if test="4==smsRecord.type">selected</ww:if>>违章通知</option>
							<option value="5" <ww:if test="5==smsRecord.type">selected</ww:if>>验证码</option>
							<option value="6" <ww:if test="6==smsRecord.type">selected</ww:if>>退款</option>
							<option value="7" <ww:if test="7==smsRecord.type">selected</ww:if>>修改密码</option>
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
							<td>手机号</td>
							<td  >类别</td>
							<td  >内容</td>
							<td  >发送时间</td>
							<td>操作</td>
						</tr>
				
						<ww:iterator value="page.results"   status="rl">
						<tr style="font-size:12px;">
							<td><ww:property value="phoneNo"/></td>
							<td ><ww:property value="@com.dearho.cs.sys.pojo.SMSRecord@getType(type)"/></td>
							<td  >
								<ww:if test="content!=null && content.length()>40"><ww:property value="content.substring(0,40)"/>...</ww:if><ww:else><ww:property value="content"/></ww:else>
							</td>
							<td ><ww:property value="transDateString(ts)"/></td>
							<td>
								<ww:if test="hasPrivilegeUrl('/smsRecord/smsRecordGet.action')"><div class="pan_btn3"  onclick="javascript:showEntity('<ww:property value="id"/>');">详情</div></ww:if>
							</td>
							
							
		
					</tr>
		
		
			</ww:iterator>
						<tr>
							<td align="center" colspan="5">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
 			</div>
		</form>
</div>
</body>
</html>