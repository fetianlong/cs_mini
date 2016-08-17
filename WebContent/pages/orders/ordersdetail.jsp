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

<title>订单详情</title>

<%@ include file="/pages/common/common_head.jsp"%>



<script type="text/javascript">
function cancel(){
	window.location.href="<%=path %>/orders/ordersSearch.action";
}
</script>

</head>
<body style="overflow-y:auto;" class="sgglPage">
      <div class="tc">
		<form>
			<input type="hidden" name="carAccident.id" id="carAccident.id"
						value="<ww:property value="carAccident.id" />">
		     <table class="table table-bordered table-condensed">
			 	<tbody>
                <tr>
	                <td class="CaptionTd"><span>订单编号</span>:</td>
	                <td class="ContentTd"> <ww:property value="orders.ordersNo" /></td>  
	                <td class="CaptionTd"><span>订单状态</span>:</td>
	                <td><ww:property value="@com.dearho.cs.sys.util.DictUtil@getCnNameByCode('14',orders.state)" /></td>   
             </tr>
             
              <tr>
	                <td><span>车辆型号</span>:</td>
	                <td><ww:property value="orders.vehicleModelName" /></td>  
	                <td><span>车牌号码</span>:</td>
	                <td><ww:property value="orders.plateNumber" /> </td>   
             </tr>
              <tr>
	                <td><span>承租人</span>:</td>
	                <td><ww:property value="orders.memberName" /></td>   
	                <td><span>实际还车地点</span>:</td>
	                <td><ww:property value="orders.endSiteId" /> </td>  
             </tr>
             
             <tr>
	                <td><span>取车时间</span>:</td>
	                <td><ww:property value="transDateString(orders.beginTime)" /></td>  
	                <td><span>还车时间</span>:</td>
	                <td><ww:property value="transDateString(orders.endTime)" /> </td>   
             </tr>
               <tr>
	                <td><span>是否需要发票</span>:</td>
	                <td><ww:if test="orders.isBill==1">需要</ww:if><ww:else>不需要</ww:else> </td>   
	                <td><span>总费用</span>:</td>
	                <td><ww:property value="formatAmount(orders.totalFee)" /> </td>   
             </tr>
			 </tbody>
			 </table>

             <ww:iterator value="orders.ordersDetail" id="data" status="rl">
			 <table class="table table-bordered table-condensed SubTable">
			<tbody>
             <tr>
             		<td class="CaptionTd"><span>子订单号</span>:</td>
	                <td class="ContentTd"><ww:property value="ordersDetailNo" /> </td>   
	                
	                <td class="CaptionTd"><span>租赁类型</span>:</td>
	                <td> 
	                	<ww:if test='timeoutRemark=="1"'>超时订单</ww:if>
	                	<ww:else></ww:else><ww:property value="typeName" /> 
	                </td>   
             </tr>
             
             <tr>
             		<td><span>开始时间</span>:</td>
	                <td><ww:property value="transDateString(beginTime)" /></td>  
	                
	                <td><span>结束时间</span>:</td>
	                <td><ww:property value="transDateString(endTime)" /> </td>   
             </tr>
             
             <ww:if test='isPrePay=="0"'>
             	<tr class="Border">
					<td><span>使用时长</span>:</td>
	                <td><ww:property value="useTimeStr" /> </td>  
					<td><span>计时费用</span>:</td>
	                <td><ww:if test='timeFee!=null && timeFee!=0'><ww:property value="timeFee" /> 元 </ww:if></td>  
             	</tr>
             
              	<tr class="Border">
					<td><span>使用里程</span>:</td>
	                <td><ww:if test='mileage!=null && mileage!=0'><ww:property value="mileage" /> 公里 </ww:if></td>  
					<td><span>里程费用</span>:</td>
	                <td><ww:if test='mileFee!=null && mileFee!=0'><ww:property value="mileFee" /> 元 </ww:if></td>  
            	</tr>
             </ww:if>
             
             <tr class="Border">
	                <td><span>订单状态</span>:</td>
	                <td>
	                	<ww:if test='isRunning=="1"'>进行中</ww:if>
						<ww:if test='isTimeout=="1" && isOver=="1"'>该订单已超时！</ww:if>
						<ww:if test='isPaid=="1" && isOver=="1"'>已结束</ww:if>
	                </td>  
					<td><span>费用</span>:</td>
	                <td>
	                <ww:if test='ticketsFee==null'><ww:property value="totalFee"/> 元 </ww:if>
	                <ww:else><ww:property value="totalFee+ticketsFee"/> 元  （含罚金：<ww:property value="ticketsFee" /> 元 ）</ww:else>
	                </td>  
             </tr>
             
             <ww:if test='accountTradeRecord.accountTradeRecordDetail.tradeNo!=null'>进行中
             <tr class="Border">
	                <td><span>微信支付流水</span>:</td>
	                <td><ww:property value="accountTradeRecord.accountTradeRecordDetail.tradeNo" /> 
             </tr></ww:if>
			  </tbody>
			 </table>
             </ww:iterator>
			
	
			<div class="row">
				<div class="col-xs-4 col-xs-offset-4">
				<div class="btt">
                            <div class="qzbtn fl" onclick="cancel();">返&nbsp;&nbsp;回</div>
                         </div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>