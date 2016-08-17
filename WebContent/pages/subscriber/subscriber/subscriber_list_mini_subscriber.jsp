<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
			
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
							
							<td class="td_bt" >
								<a href="javascript:SetOrder('phoneNo')">手机号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  class="td_bt" >
								<a href="javascript:SetOrder('name')">姓名<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  class="td_bt" >
								状态
							</td>
							<td class="td_bt"  >
								驾驶证号
							</td>
							<td class="td_bt"  >
								<a href="javascript:SetOrder('createDate')">创建时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							
							<td class="td_bt" >
								锁定状态
							</td>
							
							<td class="td_bt">
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.mResults" id="data" status="rl">
							<tr
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								
								<td  align="center">
									<ww:property value="phoneNo" />
								</td>
								<td align="left">
									<a href="javascript:showSubscriberDetailForDialog('<ww:property value="id" />','<%=path%>')"><ww:property value="name" /></a>
									
								</td>
								<td align="left">
									
									<ww:if test="@com.dearho.cs.subscriber.pojo.Subscriber@STATE_UNCONFIRMED ==state">
										<span class="label label-default">资料未提交</span>
									</ww:if>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.Subscriber@STATE_WAIT_CONFIRMED ==state">
										<span class="label label-primary">资料待审核</span>
									</ww:elseif>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.Subscriber@STATE_NO_CONFIRMED ==state">
										<span class="label label-warning">审核未通过</span>
									</ww:elseif>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.Subscriber@STATE_NORMAL ==state">
										<span class="label label-success">资料已审核</span>
									</ww:elseif>
									<ww:elseif test="@com.dearho.cs.subscriber.pojo.Subscriber@STATE_NORMAL ==state">
										<span class="label label-danger">未知</span>
									</ww:elseif>
									
								</td>
								<td align="center">
									<ww:property value="drivingLicenseNo" />
								</td>
								
								
								
							<td align="center">
									<ww:property value="transDateString(ts)" />
								</td>
								
								<td align="center">
									<ww:if test="4==eventState">半锁</ww:if>
									<ww:elseif test="5==eventState">全锁</ww:elseif>
								</td>
								
								<td align="center">
								<ww:if test="hasPrivilegeUrl('/subscriber/showSubscriberDetail2.action')">
									<div class="pan_btn1"  onclick="javascript:editEntitySkip('<ww:property value="id"/>');">编辑</div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">									
									<div class="pan_btn2" onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path%>');">记录</div>
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
	