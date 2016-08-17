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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BtxULUWSmvG50D5GKe0ka9Yk"></script>
<script type="text/javascript" src="<%=path%>/common/js/richMarker_min.js"></script>


<script type="text/javascript">
function searchEntity(){
	$('#sform').submit();		
}
function editEntity(id,type){
	var dialoguser = $.dialog({
	    id:'parkingedit', 
	    title:(type == "add")?"添加车辆":"减少车辆",
		content : 'url:<%=path%>/place/carDotBinding/searchCars.action?dotId='+id + '&type=' + type,
		fixed:true,
		width:1200,
		height:570,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    ok: function(){
	    	var valid = this.content.isValid();
	    	if (valid){
	    		var form = this.content.getForm();
	    		showLoading();
	    		if((type == "delete")){
					$.post('<%=path%>/place/carDotBinding/deleteCarDotBinding.action',form.serialize(),r_delete,'json').error(requestError);
				}
				else if((type == "add")){
					$.post('<%=path%>/place/carDotBinding/addCarDotBinding.action',form.serialize(),r_savedata,'json').error(requestError);
				}
	    	}
	    	return false;
	    },
	    okVal:"确定",
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
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				
<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="branchDot.name" class="col-xs-4 control-label">网点名称</label>
					<div class="col-xs-8">
						<input class="form-control" name="branchDot.name" id="branchDot.name" type="text" value="<ww:property value="branchDot.name"/>">
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="sfqy" class="col-xs-4 control-label">是否启用</label>
					<div class="col-xs-8">
						<select class="form-control"   name="branchDot.isActive" id="sfqy">
							<option value="">全部</option>
							<option value="1" <ww:if test="branchDot.isActive==1">selected=true</ww:if> >启用</option>	
							<option value="0" <ww:if test="branchDot.isActive==0">selected=true</ww:if>>未启用</option>	
						</select>				
					</div>
				</div>
			</div>
			
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-xs-3 ">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
		</div>
	</div>

<div class="row TableBlock">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td ><a href="javascript:SetOrder('code')">编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
							<td ><a href="javascript:SetOrder('name')">名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
							<td >地址</td>
							<td >是否启用</td>
							<td >总停车位</td>
							<td >总配置车辆</td>
							<td >已使用车辆</td>
							<td >剩余车位</td>
							<td>操作</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td align="center"><ww:property value="code" /></td>
								<td align="center"><ww:property value="name" /></td>
								<td align="center"><ww:property value="address"/></td>
								<td align="center">
									<ww:if test="isActive==1">是</ww:if>
									<ww:if test="isActive==0">否</ww:if>
								</td>
								<td align="right"><ww:property value="totalParkingPlace" /></td>
								<td align="right"><ww:property value="carCount" /></td>
								<td align="right"><ww:property value="carCount-totalParkingPlace+remainderParkingPlace" /></td>
								<td align="right"><ww:property value="remainderParkingPlace" /></td>
								<td align="center">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>','add');">车辆调入</div>
									<div class="pan_btn4"  onclick="javascript:editEntity('<ww:property value="id"/>','delete');">车辆调出</div>
								</td>
							</tr>
						</ww:iterator>
						<tr>
							<td align="right" colspan="9">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
</div>
</body>

</html>