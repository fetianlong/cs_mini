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

<title>车辆违章管理</title>

<%@ include file="/pages/common/common_head.jsp"%>


<!-- JavaScripts initializations and stuff -->
<script src="<%=path %>/common/js/main/xenon-custom.js"></script>

<script type="text/javascript">
	function editEntity(id){
		window.location.href="<%=path%>/carservice/carViolationGet.action?id="+id;
	}
	function searchEntity(){
		$("#sform").submit();
	}
	function changeDiscard(id,isDiscard){
		if(isDiscard == '0'){
			alertconfirm("确认废弃吗？",function (){
				showLoading();
				var par = {'id':id,'isDiscard':isDiscard};
				$.post('carViolationChangeDiscard.action',par,function(data){
					hideLoading();
					switch(data.result){
						case 0:
							alertok("废弃成功！", function(){
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
				},'json').error(requestError);
			});	
		}
		else{
			var par = {'id':id,'isDiscard':isDiscard};
			showLoading();
			$.post('carViolationChangeDiscard.action',par,function(data){
				hideLoading();
				switch(data.result){
					case 0:
						alertok("找回成功！", function(){
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
			},'json').error(requestError);
		}
		
	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carservice/carViolationSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="carViolation.code" class="col-xs-5 control-label">编号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="carViolation.code" id="carViolation.code" type="text" value="<ww:property value="carViolation.code"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="carViolation.plateNumber" class="col-xs-5 control-label">违章车辆牌号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="carViolation.plateNumber" id="carViolation.plateNumber" type="text" value="<ww:property value="carViolation.plateNumber"/>">
					    	</div>
					    </div>
					</div>
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="carViolation.orderCode" class="col-xs-5 control-label">违章订单编号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="carViolation.orderCode" id="carViolation.orderCode" type="text" value="<ww:property value="carViolation.orderCode"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="yycs" class="col-xs-5 control-label">运营城市：</label>
							<div class="col-xs-7">
					    		<select class="form-control" id="yycs" name="carViolation.cityId" >
									<option value="">全部</option>
									<ww:iterator value="getCitys()" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="carViolation.cityId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
									</ww:iterator>
								</select>
					    	</div>
					    </div>
					</div>
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="carViolation.memberName" class="col-xs-5 control-label">违章会员姓名：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="carViolation.memberName" id="carViolation.memberName" type="text" value="<ww:property value="carViolation.memberName"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="sffq" class="col-xs-5 control-label">是否有效：</label>
							<div class="col-xs-7">
					    		<select class="form-control" id="sffq" name="carViolation.isDiscard" >
									<option value="">全部</option>
									<option value="1" <ww:if test="carViolation.isDiscard==1">selected=true</ww:if> >无效</option>	
									<option value="0" <ww:if test="carViolation.isDiscard==0">selected=true</ww:if> >有效</option>	
								</select>
					    	</div>
					    </div>
					</div>
				</div>
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					<ww:if test="hasPrivilegeUrl('/carservice/carViolationAdd.action')">
						<div class="col-sm-2 col-xs-4"><a class="btn btn-block Button2" onclick="editEntity();" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
					</ww:if>
	  			</div>
	  		</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td  height="28" width="40"  >
							<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
						</td>
						<td>
							<a href="javascript:SetOrder('code')">编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td>违章订单</td>
						<td>违章会员</td>
						<td>违章车辆</td>
						<td>违章罚款</td>
						<td>违法扣分</td>
						<td>发生时间</td>
						<td>违章描述</td>
						<td>处理方式</td>
						<td>处理部门</td>
						<td>受理标记</td>
						<td>业务状态</td>
						<td width="157">
							操作
						</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<input type="checkbox" name="checkdel" value="<ww:property value='id' />" />
							</td>
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<a href="javascript:showOrderDetailForDialog('<ww:property value="orderId" />','<%=path%>')"><ww:property value="orderCode" /></a>
							</td>
							<td align="left">
								<a href="javascript:showSubscriberDetailForDialog('<ww:property value="memberId" />','<%=path%>')"><ww:property value="memberName" /></a>
							</td>
							<td align="left">
								<a href="javascript:showCarDetailForDialog('<ww:property value="carId" />','<%=path%>')"><ww:property value="plateNumber" /></a>
							</td>
							<td align="left">
								<ww:property value="money" />
							</td>
							<td align="left">
								<ww:property value="score" />
							</td>
							<td align="left">
								<ww:property value="happenTime" />
							</td>
							
							<td align="center">
								<ww:property value="desc" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('violationHandleType',handleType)" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('violationHandleDept',handleDept)" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('violationAccetpType',acceptFlag)" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('violationBizStatus',bizStatus)" />
							</td>
							
							<td align="center">
								<!-- <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a> -->
								<ww:if test="hasPrivilegeUrl('/carservice/carViolationGet.action')">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/carservice/carViolationChangeDiscard.action')">
									<div class="pan_btn4"  onclick="javascript:changeDiscard('<ww:property value="id"/>','<ww:property value="isDiscard"/>');"><ww:if test="isDiscard == 0">废弃</ww:if><ww:else>找回</ww:else></div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
									<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="14">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">

</script>
</html>