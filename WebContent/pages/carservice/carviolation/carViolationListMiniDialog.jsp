<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var dialoguser343;
function sendMessage(id){

	dialoguser343 = $.dialog({
		    id:"ttttt",
		    title:'短信通知',
		    content : "url:<%=path%>/dialog/gotoSendMessage.action?subscriberId="+id,
			resize:false,
		    fixed:true,
			width:500,
			height:300,
			zIndex:1000,
			stack:true,
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
		    cancel: true

		   
		}); 
	
 // alert(dialoguser343.get("subscriberdailogid334"));
}

function r_savedata(data){
		hideLoading();
		switch(data.result){
			case 0:
				alertok(data.msg);
				dialoguser343.close();
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
<form class="form-horizontal" name="sform" id="sform" method="post"	>
<input type="hidden" name="carViolation.memberId" value="<ww:property value="carViolation.memberId" />">
<input type="hidden" name="carViolation.carId" value="<ww:property value="carViolation.carId" />">
<input type="hidden" name="carViolation.orderId" value="<ww:property value="carViolation.orderId" />">


<table class="table table-striped table-bordered table-condensed">
	<tr class="ths" id="tab_bg_cl">
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
		<td>操作</td>
	</tr>

	<ww:iterator value="page.results" id="data" status="rl">
		<tr
			
			 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
			
			<td align="left">
				<ww:property value="code" />
			</td>
			<td align="left">
				<ww:property value="orderCode" />
			</td>
			<td align="left">
				<ww:property value="memberName" />
			</td>
			<td align="left">
			<ww:property value="plateNumber" />
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
			<td><a  href="javascript:sendMessage('<ww:property value="memberId" />')" >发送短信</a></td>
		</tr>
	</ww:iterator>
	<tr >
		<td align="center" colspan="13">
			<ww:property value="page.pageSplit" />	
		</td>
	</tr>
</table>
</form>
