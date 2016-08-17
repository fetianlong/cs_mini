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

function skipToPage(page, func){
	document.getElementById('page.currentPage').value=page;
	if (typeof func == 'function'){
		func();
	}else{
		searchEntity();
	};
}
function searchEntity(){
	
	var pars = $("#sform").serialize();
	$.ajax({
	  url: '<%=path%>/subscriber/showSubscriberList.action',
    data: pars,
    type: 'POST',
	  error: requestError,
	  dataType: 'html',
	  success: function(data){
		
	 		$("#showdiv").html(data);
	  }
	});
}

function SetOrder(str)
{
	var orderFlag=0;
	document.getElementById('page.orderString').value=str;
	if(!isnull(document.getElementById('page.orderFlag').value)) 
		orderFlag=document.getElementById('page.orderFlag').value;
	document.getElementById('page.orderFlag').value=1 - orderFlag;
	searchEntity();
}


	var dialoguser;
	var dialogbinding;
	function editEntity(id){
		dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'会员信息',
			content : 'url:<%=path%>/subscriber/showSubscriberDetail.action?subscriber.id='+id,
			fixed:true,
			width:500,
			height:550,
			resize:false,
	 		max: false,
		    min: false,
		    lock: true,
		    ok: function(){
		    	var valid = this.content.isValid();
		    	if (valid){
		    		var form = this.content.getForm();
		    		showLoading(parent);
		    		$.post(form.attr("action"),form.serialize(),r_savedata,'json').error(requestError);
		    	}
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
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
	}
	
/*	function searchEntity(){
		$("#sform").submit();
	}
*/



	function editEntitySkip(id){
		window.location.href="<%=path%>/subscriber/showSubscriberDetail2.action?subscriber.id="+id;
	}


	function bindingEntity(id){
		dialogbinding = $.dialog({
		    id:'carBindedit', 
		    title:'会员卡绑定',
			content : 'url:<%=path%>/subscriber/gotoBindingRFIDCard.action?subscriber.id='+id,
			fixed:true,
			width:500,
			height:550,
			resize:false,
	 		max: false,
		    min: false,
		    lock: true,
		    ok: function(){
		    	var valid = this.content.isValid();
		    	if (valid){
		    		var form = this.content.getForm();
		    		showLoading(parent);
		    		$.post(form.attr("action"),form.serialize(),r_savedataBinding,'json').error(requestError);
		    	}
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

	
	function r_savedataBinding(data){
		hideLoading();
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					dialogbinding.close();
					searchEntity();
		    			
		    	});
				break;
			case 1:
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
	}
	
</script>
</head>
<body  class="SubPage">
<div class="container-fluid">

	<form class="form-horizontal"  name="sform" id="sform" method="post" action="<%=path %>/subscriber/showSubscriberList.action">
				
	
	<div class="ControlBlock">
		<div class="row SelectBlock" >
			<div class="col-xs-4">
				<div class="form-group">
					<label for="subscriber.phoneNo" class="col-xs-4 control-label">手机号</label>
					<div class="col-xs-8">
						<input class="form-control" name="subscriber.phoneNo" id="subscriber.phoneNo" maxlength="11"   type="text" value="<ww:property value="subscriber.phoneNo"/>">
					</div>
				</div>
				<div class="form-group">
					<label for="subscriber.state" class="col-xs-4 control-label">会员状态</label>
					<div class="col-xs-8">
						 <select  class="form-control" name="subscriber.state" id="subscriber.state">
						   		<option>全部</option>
								<option value="1" <ww:if test="1==subscriber.state">selected</ww:if> >资料未提交</option>
								<option value="2" <ww:if test="2==subscriber.state">selected</ww:if> >资料待审核</option>
								<option value="3" <ww:if test="3==subscriber.state">selected</ww:if> >资料已审核</option>
								<option value="4" <ww:if test="4==subscriber.state">selected</ww:if> >审核未通过</option>
							</select>
			
					</div>
				</div>
			</div>
			
			<div class="col-xs-4">
				<div class="form-group">
					<label for="subscriber.name" class="col-xs-4 control-label">姓名</label>
					<div class="col-xs-8">
						<input class="form-control" name="subscriber.name" id="subscriber.name"    type="text" value="<ww:property value="subscriber.name"/>">
							
					</div>
				</div>
				<div class="form-group">
					<label for="subscriber.sex" class="col-xs-4 control-label">性别</label>
					<div class="col-xs-8">
						<select  class="form-control" name="subscriber.sex" id="subscriber.sex">
						   		<option value="">全部</option>
								<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_MAN"/>'  <ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_MAN.equals(subscriber.sex)">selected=true</ww:if> >男</option>
								<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_WOMAN"/>' <ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@SEX_WOMAN.equals(subscriber.sex)">selected=true</ww:if> >女</option>
							</select>
					</div>
				</div>
			</div>
			
			<input type="hidden" name="searchCondition" value="subscriber">
			
			
			 <div class="col-xs-4">
				<div class="form-group">
					<label for="subscriber.eventState" class="col-xs-5 control-label">锁定状态</label>
					<div class="col-xs-7">
						<select  class="form-control" name="subscriber.eventState" id="subscriber.eventState">
						   		<option>全部</option>
								<option value="4"  <ww:if test="4==subscriber.eventState">selected=true</ww:if> >半锁</option>
								<option value="5"  <ww:if test="5==subscriber.eventState">selected=true</ww:if> >全锁</option>
						</select>
							
					</div>
				</div>
				
			</div>
			
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-5 col-xs-6 col-xs-offset-3">
				<ww:if test="hasPrivilegeUrl('/charge/searchChargeStation.action')">
					<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
				</ww:if>
			</div>
			
		</div>
	</div>
				
				
				
			<div id="showdiv" class="row TableBlock" >
			
			</div>
		</form>
</div>
</body>
</html>