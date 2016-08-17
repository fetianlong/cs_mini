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
                    		<th>交易金额</th>
                   		 	<th>交易时间</th>
                   		 	<th>支付渠道</th>
                   		 	<th>支付方式</th>
                   		 	<th>订单ID</th>
                   		 	
						</tr>
						<ww:iterator value="page.mResults" id="data"  status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
								<td align="left">
									<ww:property value="@com.dearho.cs.account.pojo.Account@getTradeType(type)"/>
										
								</td>
								
								<td align="right">
									<ww:property  value="formatAmount(amount)"/>
								</td>
								<td align="center">
									<ww:property value="transDateString(tradeTime)"/>
								</td>
								<td>
									<ww:property value="@com.dearho.cs.account.pojo.Account@getPayChannel(payChannel)"/>
								</td>
								<td>
										<ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(payType)"/>

								</td>
								
	
									<td>
										<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type"><ww:property value="tradeOrderNo"/></ww:if>
										<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type"><ww:property value="tradeOrderNo"/></ww:elseif>
										<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type"><ww:property value="bizId"/></ww:elseif>
										<ww:else>
										<ww:property value="tradeOrderNo"/>
										</ww:else>
								
									
				
								</td>
							
								
							
							</tr>
						</ww:iterator>
						<tr >
							<td align="center" colspan="8">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
					
					</form>
