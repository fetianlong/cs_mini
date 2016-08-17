<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="<%=basePath%>">
<title>会员信息审核</title>

<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">

$(document).ready(function(){ 
	searchEntity();
});

function searchEntity(){

	var pars = $("#sform").serialize();
	$.ajax({
	  url: '<%=path%>/subscriber/searchWindowSubscriberList.action',
    data: pars,
    type: 'POST',
	  error: requestError,
	  dataType: 'html',
	  success: function(data){
	 		$("#showdiv").html(data);
	  }
	});
}

var api = frameElement.api, W = api.opener;
var memberAccidentEditDoc = W;
api.button({
    id:'valueOk',
	name:'确定',
	callback:ok,
	focus: true
});
api.button({
    id:'valueCancel',
	name:'取消'
});

function ok()
{
	if($(':radio[name=memberDoc]:checked').length) {
		var str=$("input[name='memberDoc']:checked").val();
		if(typeof(str)=="undefined"){
			$("#warning").html("请选择会员");
		    $("#warning").removeClass("hidden");
		    return false;
		}
		if(str!=""){
			var memberDoc=str.split(",");
			memberAccidentEditDoc.$("#memberId").val(memberDoc[0]);
			memberAccidentEditDoc.$("#memberName").val(memberDoc[1]);
		}
	}else{
		$("#warning").html("请选择会员");
	    $("#warning").removeClass("hidden");
	    return false;
	}
};

function confirmTr(tr){
	var str = $(tr).find("input[name='memberDoc']").val();
	if(str!=""){
		var memberDoc=str.split(",");
		memberAccidentEditDoc.$("#memberId").val(memberDoc[0]);
		memberAccidentEditDoc.$("#memberName").val(memberDoc[1]);
		
	}else{
		$("#warning").html("请选择会员");
	    $("#warning").removeClass("hidden");
	    return false;
	}
	api.close();
}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">

		 <form class="form-horizontal"  name="sform" id="sform" method="post" action="subscriber/showSubscriberList.action">
				
				<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4">
				<div class="form-group">
					<label for="subscriber.phoneNo" class="col-xs-5 control-label">手机号</label>
					<div class="col-xs-7">
						<input class="form-control" name="subscriber.phoneNo" id="subscriber.phoneNo" maxlength="11"   type="text" value="<ww:property value="subscriber.phoneNo"/>">
					</div>
				</div>
				
			</div>
			<div class="col-sm-4">
				
				<div class="form-group">
					<label for="subscriber.name" class="col-xs-5 control-label">会员状态</label>
					<div class="col-xs-7">
						<input class="form-control" name="subscriber.name" id="subscriber.name"    type="text" value="<ww:property value="subscriber.name"/>">
			
					</div>
				</div>
			</div>
			
						   		
		</div>
		
		<input  type="hidden" name="searchCondition" id="searchCondition" value="subscriber">
			<input  type="hidden" name="Subscriber.state" id="Subscriber.state" value="3">
			
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-5">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			
		</div>
			
	</div>
	
  		<div class="alert alert-danger hidden" role="alert" id="warning"></div>
	
			<div id="showdiv" class="row TableBlock">
			
			</div>
		</form>
</div>
</body>
</html>