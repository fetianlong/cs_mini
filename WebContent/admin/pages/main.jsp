<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>首页 - 华泰租车后台管理系统</title>

<%@ include file="/pages/common/common_head.jsp"%>


<link href="<%=path%>/common/js/cropper/dist/cropper.css" rel="stylesheet">
<script type="text/javascript" src="<%=path%>/common/js/cropper/dist/cropper.js"></script>

<link href="<%=path%>/common/js/cropper/examples/crop-avatar/css/main.css" rel="stylesheet">
<script type="text/javascript"   src="<%=path%>/common/js/cropper/examples/crop-avatar/js/admin_avatar.js" ></script>


<script>
$(function(){
	$(".MainMenuBlock .MainMenuUl>li").each(function(index, element) {
		if ($(element).find("ul>li").length == 0){
			$(element).hide();
		};
	});
});
</script>
</head>

<body>
<!--左侧主导航菜单-->
<div class="LeftBlock">
	<div class="UserInfoBlock text-center">
		<!--点击头像弹出模态框，修改data-target即可-->
		<button type="button" class="UserPhoto center-block" data-toggle="modal" data-target="#avatar-modal">
			
			<ww:if test="loginUser.avatar !=null">
				
				<img id="adminAvatarImage" class="center-block" src="<ww:property value="loginUser.avatar"/>" >
			</ww:if>
			<ww:else>
				<img id="adminAvatarImage" class="center-block" src="<%=path %>/admin/common/images/main/default.png" >
			</ww:else>
			
			<!-- <p>更换头像</p> -->
		</button>
		<p class="UserName"><span><ww:property value="loginUser.name"/></span></p>
		<p class="UserLastTime">上次登录：<span><ww:property value="transDate12String(loginUser.lastLoginTime)"/></span></p>
	</div>
	<div class="MainMenuBlock">
		<ul class="MainMenuUl">
        	<ww:if test="hasPrivilegeUrl('/pages/resmonitor/carmonitor/monitor.jsp')">
			<li><a class="MenuLink MenuImg6" href="<%=path %>/pages/resmonitor/carmonitor/monitor.jsp" id="monitorPlatformManage"><span class="MenuImg"></span><span class="LinkLabel">监控中心</span><i class="fa fa-caret-left fa-2x RightFa"></i></a>
				<ul>
					<li></li>
				</ul>
			</li>
            </ww:if>
  <!--  
			<li><a class="MenuLink MenuImg8" href="#"><span class="MenuImg"></span>客服中心<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
					<ww:if test="hasPrivilegeUrl('/subscriber/showCallList.action')">
						<li><a class="SubMenuLink"  id="subscriberCallManage" href="<%=path %>/subscriber/showCallList.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">会员来电管理</span></a></li>
					</ww:if>
				</ul>
			</li>
	-->
			
			<li><a class="MenuLink MenuImg6_1" href="#"><span class="MenuImg"></span>订单管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				 <ww:if test="hasPrivilegeUrl('/orders/ordersSearch.action')">
					<li><a class="SubMenuLink" title="订单管理" id="ordersManage"  href="<%=path %>/orders/ordersSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">订单管理</span></a></li>
				</ww:if>
				 <ww:if test="hasPrivilegeUrl('/statistics/orderStatisticsSearch.action')">
					<li><a class="SubMenuLink" title="订单统计" id="ordersStatistics" href="<%=path %>/statistics/orderStatisticsSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">订单统计</span></a></li>
					</ww:if>
				 <ww:if test="hasPrivilegeUrl('/orders/invoiceList.action')">
					<li><a class="SubMenuLink" title="发票管理" id="needInvoice" href="<%=path %>/orders/invoiceList.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">发票管理</span></a></li>
				</ww:if>
				</ul>
			</li>
			<li><a class="MenuLink MenuImg3" href="#"><span class="MenuImg"></span>会员管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				<ww:if test="hasPrivilegeUrl('/subscriber/showSubscriberList.action')">
					<li><a class="SubMenuLink" title="会员管理" id="subscriberManage"  href="<%=path %>/subscriber/showSubscriberList.action"   ><i class="fa fa-caret-right"></i><span class="LinkLabel">会员管理</span></a></li>
				</ww:if>
				
				 <ww:if test="hasPrivilegeUrl('/subscriberConfirm/checkList.action')">
					<li><a class="SubMenuLink"  title="会员审核管理" id="subscriberCheckManage"  href="<%=path %>/subscriberConfirm/checkList.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">会员审核管理</span></a></li>
				</ww:if>
				</ul>
			</li>
			<li><a class="MenuLink MenuImg7" href="#"><span class="MenuImg"></span>财务管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				<ww:if test="hasPrivilegeUrl('/account/showAccountTrading.action')">
					<li><a class="SubMenuLink" title="交易管理" id="accountManage" href="<%=path %>/account/showAccountTrading.action?type=show"><i class="fa fa-caret-right"></i><span class="LinkLabel">交易管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/account/showApplyRefundList.action')">
				    <li><a class="SubMenuLink" title="退款申请管理" id="applyRefundManage" href="<%=path %>/account/showApplyRefundList.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">退款申请管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/account/showCommissionRechargeCard.action')">
					<li><a class="SubMenuLink" title="代充管理" id="commissionCardManage" href="<%=path %>/account/showCommissionRechargeCard.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">代充管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/account/showCommissionCutPayment.action')">
					<li><a class="SubMenuLink" title="扣款管理" id="commissionCutPayment" href="<%=path %>/account/showCommissionCutPayment.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">扣款管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/account/showRechargeRecordList.action')">
					<li><a class="SubMenuLink" title="代充记录管理" id="rechargeRecordManage" href="<%=path %>/account/showRechargeRecordList.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">代充记录管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/account/showCutPaymentRecordList.action')">
					<li><a class="SubMenuLink" title="扣款记录管理" id="cutPaymentRecordManage" href="<%=path %>/account/showCutPaymentRecordList.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">扣款记录管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/account/showRechargeCardList.action')">
					<li><a class="SubMenuLink" title="充值卡管理" id="rechargeAccountManage" href="<%=path %>/account/showRechargeCardList.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">充值卡管理</span></a></li>
				</ww:if>
				
				</ul>
			</li>
			<li><a class="MenuLink MenuImg4" href="#"><span class="MenuImg"></span>车辆管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				 <ww:if test="hasPrivilegeUrl('/carVehicleModel/carVehicleModelSearch.action')">
					<li><a class="SubMenuLink" title="车型管理" id="carVehicleManage" href="<%=path %>/carVehicleModel/carVehicleModelSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车型管理</span></a></li>
				</ww:if>
				 <ww:if test="hasPrivilegeUrl('/car/carSearch.action')">
					<li><a class="SubMenuLink" title="车辆管理" id="carManage"  href="<%=path %>/car/carSearch.action"  ><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆管理</span></a></li>
				</ww:if>
				 <ww:if test="hasPrivilegeUrl('/device/deviceSearch.action')">
					<li><a class="SubMenuLink" title="车机管理" id="deviceManage" href="<%=path %>/device/deviceSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车机管理</span></a></li>
				</ww:if>
				 <ww:if test="hasPrivilegeUrl('/deviceBinding/deviceBindingSearch.action')">
					<li><a class="SubMenuLink" title="车机绑定管理"  id="deviceBindingManage" href="<%=path %>/deviceBinding/deviceBindingSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车机绑定管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/car/carOnlineManageSearch.action')">
					<li><a class="SubMenuLink" title="上下线管理" id="carOnlineManage" href="<%=path %>/car/carOnlineManageSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">上下线管理</span></a></li>
				</ww:if>
				</ul>
			</li>
			<li><a class="MenuLink MenuImg2" href="#"><span class="MenuImg"></span>场地管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				<ww:if test="hasPrivilegeUrl('/place/branchDotSearch.action')">
					<li><a class="SubMenuLink" title="自有网点管理" id="placeManage" href="<%=path %>/place/branchDotSearch.action?state=map" ><i class="fa fa-caret-right"></i><span class="LinkLabel">自有网点管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/charge/searchChargeStation.action')">
					<li><a class="SubMenuLink" title="社会充电站管理" id="chargeStationManage" href="<%=path %>/charge/searchChargeStation.action?state=map"><i class="fa fa-caret-right"></i><span class="LinkLabel">社会充电站管理</span></a></li>
				</ww:if>
				
				
				</ul>
			</li>
			
			
			
			
			
			<li><a class="MenuLink MenuImg5" href="#"><span class="MenuImg"></span>车务管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				<ww:if test="hasPrivilegeUrl('/carservice/carViolationSearch.action')">
					<li><a class="SubMenuLink" title="车辆违章管理" id="carViolationManage" href="<%=path %>/carservice/carViolationSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆违章管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/carservice/carAccidentSearch.action')">
					<li><a class="SubMenuLink" title="车辆事故管理"  id="carAccidentManage" href="<%=path %>/carservice/carAccidentSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆事故管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/carservice/carMaintenanceSearch.action')">
					<li><a class="SubMenuLink" title="车辆保养管理" id="carMaintenanceManage"  href="<%=path %>/carservice/carMaintenanceSearch.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆保养管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/carservice/carMaintenanceWarning.action')">
					<li><a class="SubMenuLink" title="车辆保养预警" id="carMaintenanceWarning"  href="<%=path %>/carservice/carMaintenanceWarning.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆保养预警</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/carservice/carRepairSearch.action')">
					<li><a class="SubMenuLink" title="车辆维修管理" id="carRepairManage" href="<%=path %>/carservice/carRepairSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆维修管理</span></a></li>
				</ww:if>
				 <ww:if test="hasPrivilegeUrl('/carservice/carInsuranceSearch.action')">
					<li><a class="SubMenuLink" title="车辆保单管理" id="carInsuranceManage"  href="<%=path %>/carservice/carInsuranceSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆保单管理</span></a></li>
				</ww:if>
				
				<ww:if test="hasPrivilegeUrl('/carservice/carbreak.action')">
					<li><a class="SubMenuLink" title="车辆损坏上报" id="carbreak"  href="<%=path %>/carservice/carbreak.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">车辆损坏上报</span></a></li>
				</ww:if>
				
				</ul>
			</li>
			
			<li><a class="MenuLink MenuImg7_1" href="#"><span class="MenuImg"></span>运营规则<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				 	<ww:if test="hasPrivilegeUrl('/feestrategy/strategyBase/searchStrategyBase.action')">
						<li><a class="SubMenuLink" title="基础计费规则" id="strategyBase" href="<%=path %>/feestrategy/strategyBase/searchStrategyBase.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">基础计费规则</span></a></li>
					</ww:if>
				</ul>
			</li>
			<li><a class="MenuLink MenuImg7_1" href="#"><span class="MenuImg"></span>投诉建议<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				 	<ww:if test="hasPrivilegeUrl('/feedback/strategyBase/searchStrategyBase.action')">
						<li><a class="SubMenuLink" title="一键投诉" id="strategyBase" href="<%=path %>/feedback/queryFeedbacks.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">一键投诉</span></a></li>
					</ww:if>
					
					<ww:if test="hasPrivilegeUrl('/feedback/hotDot.action')">
					<li><a class="SubMenuLink" title="催我建点" id="hotDotManage" href="<%=path %>/feedback/hotDot.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">催我建点</span></a></li>
					</ww:if>
					
				</ul>
			</li>
			<li><a class="MenuLink MenuImg9" href="#"><span class="MenuImg"></span>系统管理<i class="fa fa-caret-left fa-2x RightFa"></i><i class="fa fa-angle-left DirectionArrow"></i></a>
				<ul>
				<ww:if test="hasPrivilegeUrl('/config/configSearch.action')">
					<li><a class="SubMenuLink" title="参数管理" id="configManage" href="<%=path %>/config/configSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">参数管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/dictgroup/dictGroupSearch.action')">
					<li><a class="SubMenuLink" title="字典组"  id="dictGroupManage" href="<%=path %>/dictgroup/dictGroupSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">字典组</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/dict/dictSearch.action')">
					<li><a class="SubMenuLink" title="字典" id="dictManage" href="<%=path %>/dict/dictSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">字典</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/area/areaSearch.action')">
					<li><a class="SubMenuLink" title="行政区划" id="areaManage" href="<%=path %>/area/areaSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">行政区划</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/user/userSearch.action')">
					<li><a class="SubMenuLink" title="用户管理" id="userManage" href="<%=path %>/user/userSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">用户管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/group/groupSearch.action')">
					<li><a class="SubMenuLink" title="角色管理" id="groupManage" href="<%=path %>/group/groupSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">角色管理</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/menu/menuSearch.action')">
					<li><a class="SubMenuLink" title="菜单管理" id="menuManage" href="<%=path %>/menu/menuSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">菜单管理</span></a></li>
				</ww:if>
				
				<ww:if test="hasPrivilegeUrl('/company/companySearch.action')">
					<li><a class="SubMenuLink" title="合作企业" id="companyManage" href="<%=path %>/company/companySearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">合作企业</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/smsRecord/smsRecordSearch.action')">
					<li><a class="SubMenuLink" title="短信日志" id="SMSRecordManage" href="<%=path %>/smsRecord/smsRecordSearch.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">短信日志</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/systemutil/sysOperateLogRecord.action')">
					<li><a class="SubMenuLink" title="操作日志" id="operateLogManage" href="<%=path %>/systemutil/sysOperateLogRecord.action" ><i class="fa fa-caret-right"></i><span class="LinkLabel">操作日志</span></a></li>
				</ww:if>
				<ww:if test="hasPrivilegeUrl('/feedback/queryInforms.action')">
					<li><a class="SubMenuLink" title="系统通知" id="informManage" href="<%=path %>/feedback/queryInforms.action"><i class="fa fa-caret-right"></i><span class="LinkLabel">系统通知</span></a></li>
					</ww:if>
				</ul>
			</li>
		</ul>
	</div>
</div>

<!--右侧内容-->
<div class="RightBlock">
	<!--头部-->
	<div class="Header">
		<div class="pull-left HideMenuBlock">
			<button class="btn HideMenuButton"><i class="fa fa-align-justify fa-lg"></i></button>
		</div>
<!-- 		<div class="pull-left SearchBlock"> -->
<!-- 			<div class="Search"> -->
<!-- 				<form class="form-inline"> -->
<!-- 					<input type="text" placeholder="搜索……"> -->
<!-- 					<button type="submit" class="btn"><i class="fa fa-search"></i></button> -->
<!-- 				</form> -->
<!-- 			</div> -->
<!-- 		</div> -->
       
       <div class="pull-left NavBarBlock">
		<ul class="NavBarTabs" role="tablist">
			<li role="presentation" tags="tab_index" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">首页</a><i class="fa fa-times"></i></li>
		</ul>
	</div>
	
       
		<div class="pull-right ExitBlock">
			<button class="ExitButton" onclick='javascript:window.location.href="<%=path %>/common/logout.action"'><i class="fa fa-power-off fa-lg"></i></button>
		</div>
		<div class="pull-right PwdBlock">
			<button class="PwdButton" onclick='changePassword();'><i class="fa fa-key fa-lg"></i></button>
		</div>
		<div class="clearfix"></div>
	</div>
	
	<!--Tab切换页签-->
	<!--<div class="NavBarBlock">
		<ul class="NavBarTabs" role="tablist">
			<li role="presentation" tags="tab_index" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">首页</a><i class="fa fa-times"></i></li>
		</ul>
	</div>-->
	<ww:if test="hasPrivilegeUrl('/report/indexDataShow.action')">
	<!--内容页-->
	<div class="tab-content ContentBlock">
		<div role="tabpanel" id="home" class="tab-pane fade in active">
			<%
				String path2 = request.getContextPath();
			%>
			<script type="text/javascript" src="<%=path2 %>/common/js/Highcharts-4.1.7/js/highcharts.js"></script>
			<script type="text/javascript" src="<%=path2 %>/common/js/Highcharts-4.1.7/js/modules/exporting.js"></script>
			<div class="row HomeButton">
				<div class="col-sm-3">
					<a link="monitorPlatformManage" class="ShouyeButton">
						<i class="fa fa-car fa-4x"></i>
						<h3>车辆监控</h3>
						<p>车辆监控<i class="fa fa-arrow-circle-o-right pull-right"></i></p>
					</a>
				</div>
				<div class="col-sm-3">
					<a link="subscriberManage" class="ShezhiButton">
						<i class="fa fa-users fa-4x"></i>
						<h3>会员管理</h3>
						<p>会员管理<i class="fa fa-arrow-circle-o-right pull-right"></i></p>
					</a>
				</div>
				<div class="col-sm-3">
					<a link="ordersManage" class="CaiwuButton">
						<span class="fa-stack fa-2x">
						<i class="fa fa-circle fa-stack-2x"></i>
						<i class="fa fa-usd fa-stack-1x"></i>
						</span>
						<h3>订单管理</h3>
						<p>订单管理<i class="fa fa-arrow-circle-o-right pull-right"></i></p>
					</a>
				</div>
				<!--  <div class="col-sm-3">
					<a link="subscriberCallManage" class="ShujuButton">
						<i class="fa fa-phone fa-4x"></i>
						<h3>会员来电</h3>
						<p>会员来电<i class="fa fa-arrow-circle-o-right pull-right"></i></p>
					</a>
				</div>-->
			</div>
			
			
			<div class="row TubiaoBlock">
				<div class="col-xs-4">
					<div class="Border YunyingBlock">
						<div class="Caption">运营概况</div>
						<div class="TubiaoContent">
							<table class="table table-condensed table-hover table-striped" id="operations">
								<thead>
									<tr>
										<th>类型\时间</th>
										<th>昨天</th>
										<th>今天</th>
										<th>本周</th>
										<th>本月</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>用户登录次数</td>
										<td id="lastDayLogin"></td>
										<td id="todayDayLogin"></td>
										<td id="thisWeekLogin"></td>
										<td id="thisMonthLogin"></td>
									</tr>
									<tr>
										<td>新增注册人数</td>
										<td id="lastDayReg"></td>
										<td id="todayDayReg"></td>
										<td id="thisWeekReg"></td>
										<td id="thisMonthReg"></td>
									</tr>
									
									<tr>
										<td>新增充值金额</td>
										<td id="lastDayRecharge"></td>
										<td id="todayDayRecharge"></td>
										<td id="thisWeekRecharge"></td>
										<td id="thisMonthRecharge"></td>
									</tr>
									
									<tr>
										<td>新增订单数量</td>
										<td id="lastDayOrderNum"></td>
										<td id="todayDayOrderNum"></td>
										<td id="thisWeekOrderNum"></td>
										<td id="thisMonthOrderNum"></td>
									</tr>
									
									<tr>
										<td>新增营业收入</td>
										<td id="lastDayOrderMoney"></td>
										<td id="todayDayOrderMoney"></td>
										<td id="thisWeekOrderMoney"></td>
										<td id="thisMonthOrderMoney"></td>
									</tr>
									
									<tr>
										<td>新增用车时间</td>
										<td id="lastDayOrderHours"></td>
										<td id="todayDayOrderHours"></td>
										<td id="thisWeekOrderHours"></td>
										<td id="thisMonthOrderHours"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="col-xs-8">
					<div class="Border ShujuBlock">
						<div class="Caption" id="dataStatisticDiv">数据统计</div>
						<div class="TubiaoContent" id="chartdiv">
							<img class="img-responsive center-block LoadingImg" src="<%=path_common_head %>/admin/common/images/main/loading.gif">
						</div>
					</div>
				</div>
			</div>
			
			<div class="row TubiaoBlock">
				<div class="col-xs-12">
					<div class="Border GaikuangBlock">
						<div class="Caption">数据概况</div>
						<div class="TubiaoContent">
							<table class="table">
								<tbody>
									<tr>
										<th><i class="fa fa-users fa-lg visible-lg-inline-block"></i>平台会员情况</th>
										<td id="memberStateTd">当前共有<a class="blue" link="subscriberManage">-</a>个注册会员，其中审核通过的<a class="blue" link="subscriberManage">-</a>个，待审核的<a class="red" link="subscriberCheckManage">-</a>个。</td>
									</tr>
									<tr>
										<th><i class="fa fa-puzzle-piece fa-lg visible-lg-inline-block"></i>平台资源情况</th>
										<td id="portalResourceTd">当前共在<a class="blue" link="areaManage">-</a>个行政区域开展运营，共有<a class="blue" link="placeManage">-</a>个网点，<a class="blue" link="chargeStationManage">-</a>个公共充电站。</td>
									</tr>
									<tr>
										<th><i class="fa fa-car fa-lg visible-lg-inline-block"></i>平台车辆情况</th>
										<td id="portalCarId">当前共有<a class="blue" link="carManage">-</a>辆车，其中在线运营<a class="blue" link="monitorPlatformManage">-</a>辆（已租<a class="blue" link="carManage">-</a>辆，未租<a class="blue" link="carManage">-</a>辆），下线<a class="red" href="#">-</a>辆（维护中<a class="red" link="carRepairManage">-</a>辆，停用<a class="red" link="carManage">-</a>辆）。交强险7天内到期<a class="red" link="carInsuranceManage">-</a>辆。</td>
									</tr>
									<tr>
										<th><i class="fa fa-cart-arrow-down fa-lg visible-lg-inline-block"></i>平台订单情况</th>
										<td id="portalOrderId">当前共有<a class="blue" link="ordersManage">-</a>个进行中的订单，其中，时租<a class="blue" link="ordersManage">-</a>个，日租<a class="blue" link="ordersManage">-</a>个。今日已完成时租订单<a class="blue" link="ordersManage">-</a>个，日租订单<a class="blue" link="ordersManage">-</a>个。</td>
									</tr>
									<tr>
										<th><i class="fa fa-bar-chart fa-lg visible-lg-inline-block"></i>平台运维情况</th>
										<td id="portalOperationId">当前共有<a class="blue" link="carAccidentManage">-</a>个未处理事故，<a class="blue" link="carViolationManage">-</a>个未处理违章。</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
$(function () {
	$.post('<%=path2 %>/report/indexDataShow.action',null,function(data){
		if(data != null && data.info != null){
			var operateSurvey = data.info.operateSurvey;
			$.each(operateSurvey,function(index,operateSurveyInfo){
				switch(index){
					case 0:
						$('#lastDayLogin').text(operateSurveyInfo[0]);
						$('#todayDayLogin').text(operateSurveyInfo[1]);
						$('#thisWeekLogin').text(operateSurveyInfo[2]);
						$('#thisMonthLogin').text(operateSurveyInfo[3]);
						break;
					case 1:
						$('#lastDayReg').text(operateSurveyInfo[0]);
						$('#todayDayReg').text(operateSurveyInfo[1]);
						$('#thisWeekReg').text(operateSurveyInfo[2]);
						$('#thisMonthReg').text(operateSurveyInfo[3]);
						break;
					case 2:
						$('#lastDayRecharge').text(operateSurveyInfo[0]);
						$('#todayDayRecharge').text(operateSurveyInfo[1]);
						$('#thisWeekRecharge').text(operateSurveyInfo[2]);
						$('#thisMonthRecharge').text(operateSurveyInfo[3]);
						break;
					case 3:
						$('#lastDayOrderNum').text(operateSurveyInfo[0]);
						$('#todayDayOrderNum').text(operateSurveyInfo[1]);
						$('#thisWeekOrderNum').text(operateSurveyInfo[2]);
						$('#thisMonthOrderNum').text(operateSurveyInfo[3]);
						break;
					case 4:
						$('#lastDayOrderMoney').text(operateSurveyInfo[0]);
						$('#todayDayOrderMoney').text(operateSurveyInfo[1]);
						$('#thisWeekOrderMoney').text(operateSurveyInfo[2]);
						$('#thisMonthOrderMoney').text(operateSurveyInfo[3]);
						break;
					case 5:
						$('#lastDayOrderHours').text(operateSurveyInfo[0]);
						$('#todayDayOrderHours').text(operateSurveyInfo[1]);
						$('#thisWeekOrderHours').text(operateSurveyInfo[2]);
						$('#thisMonthOrderHours').text(operateSurveyInfo[3]);
						break;
					default:
						break;
				}
			});
			
			$('#dataStatisticDiv').text($('#dataStatisticDiv').text()+data.info.dataStatisticTitle);
			$('#chartdiv').highcharts({
				chart: {
					height: "217"
				},
		    	 title: {
		             text: ''
		         },
		        xAxis: {
		            categories: data.info.xaxis
		        },
				yAxis: {
		            title: {
		                text: ''
		            }
		        },
		        series: [{
		            type: 'column',
		            name: '新增会员数',
		            data: data.info.regMemberCount
		        }, {
		            type: 'column',
		            name: '新增订单数',
		            data: data.info.orderCount
		        }, {
		            type: 'spline',
		            name: '新增会员数',
		            data: data.info.regMemberCount,
		            marker: {
		                lineWidth: 1,
		                lineColor: Highcharts.getOptions().colors[3],
		                fillColor: 'white'
		            }
		        }, {
		            type: 'spline',
		            name: '新增订单数',
		            data: data.info.orderCount,
		            marker: {
		                lineWidth: 1,
		                lineColor: Highcharts.getOptions().colors[3],
		                fillColor: 'white'
		            }
		        }]
		    });
			
			var memberstates = $('#memberStateTd').find('a');
			$(memberstates[0]).text(data.info.dataSurveyMap.allRegMember);
			$(memberstates[1]).text(data.info.dataSurveyMap.auditMember);
			$(memberstates[2]).text(data.info.dataSurveyMap.noAuditMember);
			
			var portalResourses = $('#portalResourceTd').find('a');
			$(portalResourses[0]).text(data.info.dataSurveyMap.allareaDotCount);
			$(portalResourses[1]).text(data.info.dataSurveyMap.allDotCount);
			$(portalResourses[2]).text(data.info.dataSurveyMap.allChargeStationCount);
			
			var portalCars = $('#portalCarId').find('a');
			$(portalCars[0]).text(data.info.dataSurveyMap.allCarCount);
			$(portalCars[1]).text(data.info.dataSurveyMap.onlineCarCount);
			$(portalCars[2]).text(data.info.dataSurveyMap.weizujieCarCount);
			$(portalCars[3]).text(data.info.dataSurveyMap.yizujieCarCount);
			$(portalCars[4]).text(data.info.dataSurveyMap.xiaxianCarCount);
			$(portalCars[5]).text(data.info.dataSurveyMap.weixiuCarCount);
			$(portalCars[6]).text(data.info.dataSurveyMap.tzsyCarCount);
			$(portalCars[7]).text(data.info.dataSurveyMap.jqx7DayCarCount);
			
			var portalOrders = $('#portalOrderId').find('a');
			$(portalOrders[0]).text(data.info.dataSurveyMap.alljxzOrderCount);
			$(portalOrders[1]).text(data.info.dataSurveyMap.szjxzOrderCount);
			$(portalOrders[2]).text(data.info.dataSurveyMap.rzjxzOrderCount);
			$(portalOrders[3]).text(data.info.dataSurveyMap.szywjOrderCount);
			$(portalOrders[4]).text(data.info.dataSurveyMap.rzywjOrderCount);
			
			var portalOperations = $('#portalOperationId').find('a');
			$(portalOperations[0]).text(data.info.dataSurveyMap.accidentCount);
			$(portalOperations[1]).text(data.info.dataSurveyMap.violationCarCount);
		}
	},'json').error();
	
	$(".GaikuangBlock").on("click", "a", function(e) {
		$(".LeftBlock .MainMenuUl li").removeClass("active");
		$(".LeftBlock .MainMenuUl #" + $(this).attr("link")).click();
		$(".LeftBlock .MainMenuUl #" + $(this).attr("link")).closest("ul").slideDown(200);
	});
	
	$(".ContentBlock .HomeButton").on("click", "a", function(e) {
		$(".LeftBlock .MainMenuUl li").removeClass("active");
		$(".LeftBlock .MainMenuUl #" + $(this).attr("link")).click();
		$(".LeftBlock .MainMenuUl #" + $(this).attr("link")).closest("ul").slideDown(200);
	});
});
</script>
		</div>
		
		
	</div>
	
	</ww:if>
	<ww:else>
	<div class="tab-content ContentBlock">
		<div role="tabpanel" id="home" class="tab-pane fade in active WelcomePage">
			<div class="LabelBlock">
            	<h3>Welcome!</h3>
                <p>欢迎您进入华泰后台管理系统</p>
                <small>©北京分时共享科技有限公司</small>
            </div>
            <div class="BackgroundBlock"></div>
		</div>
	</div>
	</ww:else>
</div>




 <div class="container" id="crop-avatar">


   <!-- Cropping modal -->
		    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
		      <div class="modal-dialog modal-lg">
		        <div class="modal-content">
		          <form class="avatar-form" action="<%=path %>/common/uploadAvatar.action" enctype="multipart/form-data" method="post">
		          	<input type="hidden" name="imageData" id="imageData">
		            <div class="modal-header">
		              <button class="close" data-dismiss="modal" type="button">&times;</button>
		              <h4 class="modal-title" id="avatar-modal-label">更换头像</h4>
		            </div>
		            <div class="modal-body">
		              <div class="avatar-body">
		
		                <!-- Upload image and data -->
		                <div class="avatar-upload">
		                  <input class="avatar-src" name="avatar_src" type="hidden">
		                  <input class="avatar-data" name="avatar_data" type="hidden">
		                  <label for="avatarInput">上传路径</label>
		                  <input class="avatar-input" id="avatarInput" name="upload" type="file">
		                </div>
		
		                <!-- Crop and preview -->
		                <div class="row">
		                  <div class="col-md-9">
		                    <div class="avatar-wrapper"></div>
		                  </div>
		                  <div class="col-md-3">
		                    <div class="avatar-preview preview-lg"></div>
		                  <!--   <div class="avatar-preview preview-md"></div>
		                    <div class="avatar-preview preview-sm"></div> -->
		                  </div>
		                </div>
		
		                <div class="row avatar-btns">
		                  <div class="col-md-9">
		                    <div class="btn-group">
		                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees">左转</button>
		                      <button class="btn btn-primary" data-method="rotate" data-option="-15" type="button">-15度</button>
		                      <button class="btn btn-primary" data-method="rotate" data-option="-30" type="button">-30度</button>
		                      <button class="btn btn-primary" data-method="rotate" data-option="-45" type="button">-45度</button>
		                    </div>
		                    <div class="btn-group">
		                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees">右转</button>
		                      <button class="btn btn-primary" data-method="rotate" data-option="15" type="button">15度</button>
		                      <button class="btn btn-primary" data-method="rotate" data-option="30" type="button">30度</button>
		                      <button class="btn btn-primary" data-method="rotate" data-option="45" type="button">45度</button>
		                    </div>
		                  </div>
		                  <div class="col-md-3">
		                    <button class="btn btn-primary btn-block avatar-save" type="submit">完成</button>
		                  </div>
		                </div>
		              </div>
		            </div>
		            <!-- <div class="modal-footer">
		              <button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
		            </div> -->
		          </form>
		        </div>
		      </div>
		    </div><!-- /.modal -->
		
		    <!-- Loading state -->
		    <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
		  </div>
<!-- 头像  end -->
  
</body>
<script type="text/javascript">
var dialoguser;
function changePassword(id){
	 dialoguser = $.dialog({
	    id:'changepasswordDia', 
	    title:'修改密码',
		content : 'url:<%=path%>/user/gotoChangePassword.action',
		fixed:true,
		width:500,
		height:250,
		resize:true,
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
function r_savedata(data){
	hideLoading();
	switch(data.result){
		case 0:
			dialoguser.close();
			alertok(data.msg);
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
</html>
