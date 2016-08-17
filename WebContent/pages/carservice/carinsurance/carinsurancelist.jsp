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
<title>车辆保单管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function editEntity(id){
		window.location.href="<%=path%>/carservice/carInsuranceGet.action?id="+id;
	}
	function searchEntity(){
		$("#sform").submit();
	}
	function changeDiscard(id,isDiscard){
		if(isDiscard == '0'){
			alertconfirm("确认废弃吗？",function (){
				showLoading();
				var par = {'id':id,'isDiscard':isDiscard};
				$.post('carInsuranceChangeDiscard.action',par,function(data){
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
			$.post('carInsuranceChangeDiscard.action',par,function(data){
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
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carservice/carInsuranceSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						<div class="form-group">
							<label for="carInsurance.code" class="col-xs-4 control-label">保单编号：</label>
							<div class="col-xs-8">
					    		 <input class="form-control" name="carInsurance.code" id="carInsurance.code" type="text" value="<ww:property value="carInsurance.code"/>">
					    	</div>
					    </div>
					    					    <div class="form-group">
							<label for="sffq" class="col-xs-4 control-label">是否有效：</label>
							<div class="col-xs-8">
					    		   <select class="form-control" id="sffq" name="carInsurance.isDiscard" >
									 <option value="">全部</option>
									 <option value="1" <ww:if test="carInsurance.isDiscard==1">selected=true</ww:if> >无效</option>	
									 <option value="0" <ww:if test="carInsurance.isDiscard==0">selected=true</ww:if> >有效</option>	
								 </select>
					    	</div>
					    </div>
				    </div>
				    <div class="col-xs-4">
						<div class="form-group">
							<label for="carInsurance.plateNumber" class="col-xs-5 control-label">参保车辆牌号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="carInsurance.plateNumber" id="carInsurance.plateNumber" type="text" value="<ww:property value="carInsurance.plateNumber"/>">
					    	</div>
					    </div>
				    </div>
				    <div class="col-xs-4">
						<div class="form-group">
							<label for="carInsurance.policyHolder" class="col-xs-4 control-label">投保人：</label>
							<div class="col-xs-8">
					    		 <input class="form-control" name="carInsurance.policyHolder" id="carInsurance.policyHolder" type="text" value="<ww:property value="carInsurance.policyHolder"/>">
					    	</div>
					    </div>

				    </div>
				</div>
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					<ww:if test="hasPrivilegeUrl('/carservice/carInsuranceAdd.action')">
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
						<td>车牌号</td>
						<td>投保人</td>
						<td>行驶证注册日期</td>
						<td>车辆投保时间</td>
						<td>备注</td>
						<td width="157">
							操作
						</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<input type="checkbox" name="checkdel" value="<ww:property value='id' />" />
							</td>
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<a href="javascript:showCarDetailForDialog('<ww:property value="carId" />','<%=path%>')"><ww:property value="plateNumber" /></a>
							</td>
							<td align="left">
								<ww:property value="policyHolder" />
							</td>
							<td align="center">
								<ww:property value="transDate10String(firstRegTime)" />
							</td>
							<td align="right">
								<ww:property value="transDate10String(insuranceTime)" />
							</td>
							<td align="left">
								<ww:property value="remark" />
							</td>
							
							<td align="center">
								<!-- <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a> -->
								<ww:if test="hasPrivilegeUrl('/carservice/carInsuranceGet.action')">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/carservice/carInsuranceChangeDiscard.action')">
									<div class="pan_btn4"  onclick="javascript:changeDiscard('<ww:property value="id"/>','<ww:property value="isDiscard"/>');"><ww:if test="isDiscard == 0">废弃</ww:if><ww:else>找回</ww:else></div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
									<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="10">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>