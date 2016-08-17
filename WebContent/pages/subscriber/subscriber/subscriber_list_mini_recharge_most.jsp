<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>

				
					<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
					<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td >
								手机号
							</td>
							<td >
								姓名
							</td>
							<td >
								状态
							</td>
							<td >
								账户金额
							</td>
							<td >
								累计充值金额
							</td>
							
						
							
							<td >
								操作
							</td>
						</tr>
						<ww:iterator value="page.results" id="data"   status="rl">
							<tr
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
								<td align="center">
									<ww:property value="subscriber.phoneNo" />
								</td>
								
								<td align="left"><a href="javascript:showSubscriberDetailForDialog('<ww:property value="subscriber.id" />','<%=path%>')"><ww:property value="subscriber.name" /></a></td>
								<td align="left"><ww:property  value="@com.dearho.cs.subscriber.pojo.Subscriber@getState(subscriber.state)"/></td>
								<td align="right">
									<ww:property value="formatAmount(subscriber.account.amount)" />
								</td>
								<td align="right">
									<ww:property value="formatAmount(amount)" />
								</td>
								
							
								
								
								
								<td>
									<ww:if test="hasPrivilegeUrl('/subscriber/showSubscriberDetail2.action')">
										<div class="pan_btn1"  onclick="javascript:editEntitySkip('<ww:property value="subscriber.id"/>','update');">编辑</div>
									</ww:if>
									<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">									
										<div class="pan_btn2" onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path%>');">记录</div>
									</ww:if>
								</td>
							</tr>
						</ww:iterator>
						<tr >
							<td align="center" colspan="8">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
			