<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
	function showCarcommonDetailForDialog(id,path){
		var dialoguser = $.dialog({
		    id:'cardailogid1', 
		    title:'车辆评价详情',
		    content : "url:"+path+"/carservice/caCommonDetail.action?carCommon.id="+id,
			resize:false,
			fixed:true,
			width:600,
			height:600,
		    lock: true,
	 		max: false,
		    min: false,
			 
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
	
	function showDetail(id){
		window.location.href="<%=path%>/carservice/caCommonDetail.action?carCommon.id="+id;
	}
</script>

	<table class="table table-striped table-bordered table-condensed">
		<tr class="ths" id="tab_bg_cl">
			<td>订单编号</td>
			<td>评价会员</td>
			<td>是否顺利</td>
			<td>车辆外观</td>
			<td>车内卫生</td>
			<td>时间</td>
			<td>详情</td>
		</tr>
		<ww:iterator value="page.results" id="data" status="rl">
			<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
				<td align="center"><ww:property value="orderId" /></td>
				<td align="center"><ww:property value="subscriberName" /></td>
				<td align="center">
				<ww:if test="isOk==1">顺利</ww:if>
				<ww:if test="isOk==0">不顺利</ww:if>
				</td>
				<td align="center"><ww:property value="carFace" /></td>
				<td align="center"><ww:property value="carClean" /></td>
				<td align="center"><ww:property value="transDate12String(ts)" /></td>
				<td align="center">
				<a style="color: red;" href="javascript:showCarcommonDetailForDialog('<ww:property value="id" />','<%=path%>')">详细</a>
				<%-- <a style="color: red;" href="javascript:showDetail('<ww:property value="id" />')">详细</a> --%>
				</td>
			</tr>
		</ww:iterator>
		<tr>
			<td align="right" colspan="7"><ww:property
					value="page.pageSplit" /></td>
		</tr>
	</table>
