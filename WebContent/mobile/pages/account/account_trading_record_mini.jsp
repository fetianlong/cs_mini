
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>



<ww:property  value="page.currentPage"/>
<ww:property  value="page.pageSize"/>


 <ww:iterator value="page.mResults"   status="st">
 	<tr id='<ww:property value="id"/>'>
		<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type">
			<th><ww:property value="tradeOrderNo"/></th>
		<%-- 	<td><ww:property value="transDateString(tradeTime)"/></td> --%>
			<td class="Color1">+<ww:property  value="formatAmount(amount)"/></td>
			<td class="Color1">充值</td>
		</ww:if>
		<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type">
			<th><ww:property value="tradeOrderNo"/></th>
			<%-- <td><ww:property value="transDateString(tradeTime)"/></td> --%>
			<td class="Color3">-<ww:property  value="formatAmount(amount)"/></td>
			<td class="Color3">退款</td>
		</ww:elseif>
		<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type">
			<th><ww:property value="tradeOrderNo"/></th>
		<%-- 	<td><ww:property value="transDateString(tradeTime)"/></td> --%>
			<td class="Color2">-<ww:property  value="formatAmount(amount)"/></td>
			<td class="Color2">租车</td>
		</ww:elseif>
		<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_CUT_PAYMENT==type">
			<th><ww:property value="tradeOrderNo"/></th>
			<%-- <td><ww:property value="transDateString(tradeTime)"/></td> --%>
			<td class="Color3"><ww:property  value="formatAmount(amount)"/></td>
			<td class="Color3">扣款</td>
		</ww:elseif>
	</tr>
</ww:iterator>