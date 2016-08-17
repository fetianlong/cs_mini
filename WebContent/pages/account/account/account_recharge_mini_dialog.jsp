<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>

<form class="form-horizontal" name="sform" id="sform" method="post"	>
<input type="hidden" name="accountTradeRecord.subscriberId" value="<ww:property value="accountTradeRecord.subscriberId" />">
					<table class="table table-striped table-bordered table-condensed" >
						<tr class="ths" id="tab_bg_cl">
							<th>交易类型</th>
                    		<th>金额</th>
                    		
                   		 	<th>交易时间</th>
                   		 	<th>充值渠道</th>
                   		 	<th>支付方式</th>
                   		 	<th>描述</th>
						</tr>
						<ww:iterator value="page.mResults" id="data"  status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
								<td align="center">
										<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type"><span class="green">充值</span></ww:if>
										<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type"><span class="tk">退款</span></ww:elseif>
										<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type"><span class="tk">租车</span></ww:elseif>
										<ww:else>其他</ww:else>
								</td>
								
								<td align="center">
									<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type"><span class="green"><ww:property  value="formatAmount(amount)"/></span></ww:if>
									<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type"><span class="tk"><ww:property  value="formatAmount(amount)"/></span></ww:elseif>
									<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type"><span class="tk"><ww:property  value="formatAmount(amount)"/></span></ww:elseif>
									<ww:else><ww:property  value="formatAmount(amount)"/></ww:else>
								</td>
								
								<td align="center">
									<ww:property value="transDateString(tradeTime)"/>
								</td>
								<td align="center">
									<ww:property value="@com.dearho.cs.account.pojo.Account@getPayChannel(payChannel)"/>
									
								</td>
								<td align="center">
									<ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(payType)"/>
								</td>
								<td>
									<ww:property value="description"/>
								</td>
							</tr>
						</ww:iterator>
						<tr >
							<td align="center" colspan="7">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
</form>