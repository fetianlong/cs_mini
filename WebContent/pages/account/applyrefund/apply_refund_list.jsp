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
<title>退款申请审核</title>
<%@ include file="/pages/common/common_head.jsp"%>




<script type="text/javascript" src="<%=path%>/common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>



<script type="text/javascript">
	function editEntity(id){
	
		var dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'退款申请信息',
			content : 'url:<%=path%>/account/showApplyRefundEditDetail.action?subscriberConfirm.id='+id,
			fixed:true,
			width:600,
			height:500,
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
	function deleteEntity(){
		var ob = document.getElementsByName("checkdel");
		var check = false;
		for (var i = 0; i < ob.length; i++) {
			if (ob[i].checked) {
				check = true;
				break;
			}
		}
		if (!check) {
			alertinfo("请选择要删除的数据！");
			return false;
		}
		alertconfirm("确认删除选中的数据吗？",function (){
			showLoading();
			$.post('carBindDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
		});	
	}
	
	function r_delete(data){
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
	function searchEntity(){
		$("#sform").submit();
	}



	function showEntity(id){
		var dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'申请退款详情',
		    content : 'url:<%=path%>/account/showApplyRefundDetail.action?subscriberConfirm.id='+id,
			fixed:true,
			width:600,
			height:500,
			resize:false,
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


		 <form  class="form-horizontal"   name="sform" id="sform" method="post" action="<%=path %>/account/showApplyRefundList.action">
				

				<div class="ControlBlock">
					<div class="row SelectBlock">
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
								<label for="subscriberConfirm.subscriber.phoneNo" class="col-sm-4 control-label">手机号</label>
								<div class="col-sm-8">
									<input class="form-control" name="subscriberConfirm.subscriber.phoneNo" id="subscriberConfirm.subscriber.phoneNo"  maxlength="11" type="text" value="<ww:property value="subscriberConfirm.subscriber.phoneNo"/>">
								</div>
							</div>
						</div>
						
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
								<label for="subscriberConfirm.isComplete" class="col-xs-4 control-label">审核状态</label>
								<div class="col-xs-8">
									<select class="form-control"   name="subscriberConfirm.isComplete" id="subscriberConfirm.isComplete">
										<option value="0"  <ww:if test="subscriberConfirm.isComplete==0">selected=true</ww:if>>未审核</option>
										<option value="1" <ww:if test="subscriberConfirm.isComplete==1">selected=true</ww:if> >已审核</option>
										<option value="2" <ww:if test="subscriberConfirm.isComplete==2">selected=true</ww:if> >审核异常</option>
									</select>
								</div>
							</div>
						</div>
						
					</div>
					<div class="row SubmitButtonBlock">
						<div class="col-sm-2 col-sm-offset-5">
							<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
						</div>
						
					</div>
				</div>
				
				
				
			<div class="row TableBlock">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
						
				<table class="table table-striped table-bordered table-condensed" >
					<tr class="ths" id="tab_bg_cl">
							
							<td class="td_bt"  >
								手机号
							</td>
							<td class="td_bt"  >
								姓名
							</td>
							<td  >
								状态
							</td>
							<td class="td_bt"  >
								<a href="javascript:SetOrder('b.applyTime')">申请时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td class="td_bt"  >
								退款金额
							</td>
							
							<td class="td_bt">
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								
								<td align="center">
									<ww:property value="subscriber.phoneNo" />
								</td>
								<td align="left">
									<a href="javascript:showSubscriberDetailForDialog('<ww:property value="subscriber.id" />','<%=path%>')"><ww:property value="subscriber.name" /></a>
								</td>
								<td align="left">
									<ww:property  value="@com.dearho.cs.subscriber.pojo.Subscriber@getState(subscriber.state)"/>
								</td>
								<td align="center">
									<ww:property value="transDateString(businessFlow.applyTime)" />
								</td>
								<td align="right">
									<ww:property value="attribute_2" />
								</td>
								
								<td align="center">
									
									
									<ww:if test="hasPrivilegeUrl('/account/showApplyRefundDetail.action')">
									<div     onclick="javascript:showEntity('<ww:property value="id"/>');" class="pan_btn1" >详情</div>
									</ww:if>
									<ww:if test="isComplete==0 || isComplete==2">
										<ww:if test="hasPrivilegeUrl('/account/showApplyRefundEditDetail.action')">
										<div  onclick="javascript:editEntity('<ww:property value="id"/>');"   class="pan_btn1" >审核</div>
										</ww:if>	
								    </ww:if>
								    
								</td>
							</tr>
						</ww:iterator>
						<tr >
							<td align="center" colspan="7">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
				</table>
			</div>
		</form>
</div>
</body>
</html>