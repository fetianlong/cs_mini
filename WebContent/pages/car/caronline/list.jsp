<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%@ page import="com.dearho.cs.sys.util.DictUtil" language="java" %>
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
	$("#sform").submit();
}
function online(id){
	alertconfirm("确认上线吗？",function (){
		$.dialog.prompt('请输入上线原因',  function(val){
				if(val == null || val.trim() == ''){
					$.dialog.tips('请输入上线原因');
				}
				else{
					showLoading();
					$.post('carOnline.action',{'car.id':id,'reason':val},function(data){
						hideLoading();
						if(data.result == 0){
							alertok('上线成功');
							$("#sform").submit();
						}
						else{
							alerterror(data.msg);
						}
						
					},'json').error(requestError);
				}
			},
		    ''
		);
		
	});	
}
function unonline(id){
	
	alertconfirm("确认下线吗？",function (){
		$.dialog.prompt('请输入下线原因',
		    function(val){
				if(val == null || val.trim() == ''){
					$.dialog.tips('请输入下线原因');
				}
				else{
					showLoading();
					$.post('carUnOnline.action',{'car.id':id,'reason':val},function(data){
						hideLoading();
						if(data.result == 0){
							alertok(data.msg);
							$("#sform").submit();
						}
						else{
							alerterror(data.msg);
						}
						
					},'json').error(requestError);
				}
		    },
		    ''
		);
		
	});	
}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
			<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/car/carOnlineManageSearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				<input type="hidden" id="allModelInput" value="
					<ww:iterator value="getAllModel('null')" id="data" status="rl"><ww:property value="id" />:<ww:property value="name" />:<ww:property value="brand" />,</ww:iterator>"/>
				<div class="ControlBlock">
					<div class="row SelectBlock">
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
								<label for="cph" class="col-xs-4 control-label">车牌号：</label>
								<div class="col-xs-8">
									<input type="text" id="cph"  class="form-control" name="car.vehiclePlateId" value='<ww:property value="car.vehiclePlateId" />' />
								</div>
							</div>
						</div>
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
							 	<label for="clzt" class="col-xs-4 control-label">车辆状态：</label>
							 	<div class="col-xs-8">
								 	<select class="form-control" id="clzt" name="car.bizState">
										<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carBizState',1)" id="data" status="rl">
											<option value="<ww:property value="id" />"  <ww:if test="car.bizState==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
										</ww:iterator>
									</select>
								</div>
							 </div>
						</div>
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
								<label for="car.modelId" class="col-xs-4 control-label">车辆型号：</label>
								<div class="col-xs-8">
								 	<select  class="form-control" name="car.modelId" id="car.modelId">
								 		<option value="">全部</option>
										<ww:iterator value="getAllModel()" id="data" status="rl">
											<option value="<ww:property value="id" />"  <ww:if test="car.modelId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
										</ww:iterator>
									</select>
								</div>
							</div>
							
						</div>
					 </div>
					 <div class="row SubmitButtonBlock">
						<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1"   onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
  				     </div>
  				</div>
				<div class="row TableBlock">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td>
							</td>
							<td>
								<a href="javascript:SetOrder('vehiclePlateId')">车牌号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td>
								车辆品牌
							</td>
							<td>
								车辆型号
							</td>
							<td>
								车辆状态
							</td>
							<td width="157">
								操作
							</td>
						</tr>
						<ww:iterator value="page.results" id="data" status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								<td align="center">
									<ww:property value="#rl.index + 1" />
								</td>
								<td align="left">
									<ww:property value="vehiclePlateId" />
								</td>
								
								<td align="left">
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',carVehicleModel.brand)"/>
								</td>
								<td align="left">
									<ww:property value="carVehicleModel.name" />
								</td>
								<td align="center">
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('carBizState',bizState)" />
								</td>
								<td>
									<ww:if test="bizStateCode.equals(\"3\") && hasPrivilegeUrl('/car/carOnline.action')">
										<div class="pan_btn3"  onclick="javascript:online('<ww:property value="id"/>');">上线</div>
									</ww:if>
									<ww:if test="(bizStateCode.equals(\"0\") || bizStateCode.equals(\"1\")) && hasPrivilegeUrl('/car/carUnOnline.action')">
										<div class="pan_btn4"  onclick="javascript:unonline('<ww:property value="id"/>');">下线</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
										<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
									</ww:if>
									<!--  <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a>-->
									<!--  <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','get');">详细信息</a>-->
								</td>
							</tr>
						</ww:iterator>
						<tr style="background-color: #fff;height: 30px;">
							<td align="center" colspan="8">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
	</div>
</body>
</html>