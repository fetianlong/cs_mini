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

<title>网点巡检管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	
function searchEntity(){
	$("#sform").submit();
}
var api = frameElement.api, W = api.opener;
var parkingPatrolDoc = W;
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
		var str=$("input[name='parkingPatrolDoc']:checked").val();
		if(typeof(str)=="undefined"){
			alert("请选择车辆");
		}
		if(str!=""){
			var ppDoc=str.split(",");
		    parkingPatrolDoc.$("#parkingPatrolId").val(ppDoc[0]);
			parkingPatrolDoc.$("#parkingPatrolCode").val(ppDoc[1]);
		}
};

function confirmTr(tr){
	var str = $(tr).find("input[name='parkingPatrolDoc']").val();
	if(str!=""){
		var ppDoc=str.split(",");
	    parkingPatrolDoc.$("#parkingPatrolId").val(ppDoc[0]);
		parkingPatrolDoc.$("#parkingPatrolCode").val(ppDoc[1]);
	}else{
		return false;
	}
	api.close();
}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal"  id="sform" method="post" action="<%=path%>/carservice/parkingPatrolSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						<div class="form-group">
							<label for="parkingPatrol.code" class="col-xs-5 control-label">编号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="parkingPatrol.code" id="parkingPatrol.code" type="text" value="<ww:property value="parkingPatrol.code"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="yycs" class="col-xs-5 control-label">运营城市：</label>
							<div class="col-xs-7">
					    		 <select class="form-control" id="yycs" name="parkingPatrol.cityId" >
									 <option value="">全部</option>
									 <ww:iterator value="getCitys()" id="data" status="rl">
										 <option value="<ww:property value="id" />"  <ww:if test="parkingPatrol.cityId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
									 </ww:iterator>
								 </select>
					    	</div>
					    </div>
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label for="parkingPatrol.parkingName" class="col-xs-5 control-label">网点简称：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="parkingPatrol.parkingName" id="parkingPatrol.parkingName" type="text" value="<ww:property value="parkingPatrol.parkingName"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="sffq" class="col-xs-5 control-label">是否废弃：</label>
							<div class="col-xs-7">
					    		 <select class="form-control" id="sffq" name="parkingPatrol.isDiscard" >
									<option value="">全部</option>
									<option value="1" <ww:if test="parkingPatrol.isDiscard==1">selected=true</ww:if> >废弃</option>	
									<option value="0" <ww:if test="parkingPatrol.isDiscard==0">selected=true</ww:if>>启用</option>	
								</select>
					    	</div>
					    </div>
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label for="parkingPatrol.userName" class="col-xs-5 control-label">巡检人：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="parkingPatrol.userName" id="parkingPatrol.userName" type="text" value="<ww:property value="parkingPatrol.userName"/>">
					    	</div>
					    </div>
					</div>
				</div>
				<div class="row SubmitButtonBlock">
					<div class="col-xs-4 col-xs-offset-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
	  			</div>
	  		</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td height="28" width="40" align="center" nowrap></td>
						<td>
							<a href="javascript:SetOrder('code')">编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td>网点</td>
						<td>巡检人</td>
						<td>巡检时间</td>
						<td>备注</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr ondblclick="confirmTr(this);" 
							<ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<input type="radio" name="parkingPatrolDoc" value="<ww:property value="id" />,<ww:property value="code" />" /></td>
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<ww:property value="parkingName" />
							</td>
							<td align="left">
								<ww:property value="userName" />
							</td>
							<td align="center">
								<ww:property value="transDate10String(patrolTime)" />
							</td>
							<td>
								<ww:property value="remark" />
							</td>
							
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="11">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>