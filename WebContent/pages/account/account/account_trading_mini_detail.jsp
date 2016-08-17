<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>

					<table class="table table-striped table-bordered table-condensed" >
						<tr class="ths" id="tab_bg_cl">
							<td>手机号</td>
							<td>姓名</td>
							<td>交易类型</td>
                    		<td>交易金额</td>
                   		 	<td>交易时间</td>
                   		 	<td>支付渠道</td>
                   		 	<td>支付方式</td>
                   		 	
                   		 	<td>订单ID</td>
                   		 	<td>操作</td>
						</tr>
						<ww:iterator value="page.mResults" id="data"  status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
							<td><ww:property value="subscriber.phoneNo"/></td>
							<td>
								<a href="javascript:showSubscriberDetailForDialog('<ww:property value="subscriber.id"/>','<%=path%>')"><ww:property value="subscriber.name"/></a>
							</td>
								<td >
									<ww:property value="@com.dearho.cs.account.pojo.Account@getTradeType(type)"/>	
								</td>
								
								<td >
											<ww:property  value="formatAmount(amount)"/>
								</td>
								<td >
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
										<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type"><a href="javascript:showOrderDetailByNoForDialog('<ww:property value="bizId"/>','<%=path%>')"><ww:property value="bizId"/></a></ww:elseif>
										<ww:else>
										<ww:property value="tradeOrderNo"/>
										</ww:else>
								
									
								</td>
								
								<td>
									<ww:if test="hasPrivilegeUrl('account/showAccountTradingDetail.action')">
										<div class="pan_btn3"  onclick="showEntity('<ww:property value="id"/>');">详情</div>
									</ww:if>
								</td>
							
							</tr>
						</ww:iterator>
						<tr >
							<td align="center" colspan="9">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
