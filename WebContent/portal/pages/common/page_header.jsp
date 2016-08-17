<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dearho.cs.util.DateUtil" language="java" %>
<%@ page import="com.dearho.cs.subscriber.pojo.Subscriber" language="java" %>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path_page_head = request.getContextPath();
%>
<%
	Object userObj = request.getSession().getAttribute("subscriber");
	Subscriber subs = null;
	String name = null;
	if(userObj != null){
		subs = (Subscriber)userObj;
		name = subs.getName();
		if(name == null || name.trim().equals("")){
			name = subs.getPhoneNo();
		}
	}
%>
<div class="Header">
	<!--顶部栏-->
        <div class="TopNavBar">
        	<div class="container">
                <div class="row" id="TopNavBarContent">
                	<div class="col-xs-3 visible-xs-block"><a href="<%=path_page_head %>" target="_blank"><img class="img-responsive SmallLogoImg" src="<%=path_page_head %>/portal/common/images/main/Logo.png"></a></div>
                	<div class="col-sm-8 col-xs-5">
                    	<p class="navbar-text"><i class="fa fa-phone"></i>010-5786 2214</p>
                    </div>
                    <div class="col-xs-4">
                    <% if(name == null || name.equals("")) {%>
                    	<a class="btn navbar-btn RegButton pull-right" href="<%=path_page_head %>/portal/subscriber/register.action" role="button">注册</a>
                        <a class="btn btn-link navbar-btn pull-right" href="<%=path_page_head %>/portal/login.action" role="button">登录</a>
                     <%} else { %>
					 	<a class="btn navbar-btn RegButton pull-right" href="<%=path_page_head %>/portal/logout.action" role="button">退出</a>
						<a class="btn btn-warning navbar-btn pull-right MyCenter" href="<%=path_page_head %>/portal/subscriber/showBaseInfo.action" role="button">个人中心</a>
						<p class="navbar-text pull-right WelcomeLabel hidden-xs">欢迎您！<%=name %></p>
                     	
                     <%} %>
                    	
                    </div>
                </div>
            </div>
        </div>
        <div class="NavBarBlock">
            <div class="container">
                <div class="row HeaderBlock">
                    <div class="col-sm-6 hidden-xs">
                        <a href="<%=path_page_head %>"><img class="LogoImg" src="<%=path_page_head %>/portal/common/images/main/Logo.png"></a>
                    </div>
                    <div class="col-sm-6">
                        <ul class="nav nav-justified MainNavBar">
                            <li role="presentation"  <ww:if test="#mainNavIndex=='index'">class="active"</ww:if>><a href="<%=path_page_head %>">首页</a></li>
                            <li role="presentation" <ww:if test="#mainNavIndex=='book'">class="active"</ww:if>><a href="<%=path_page_head%>/portal/orders/toBookCar.action">在线订车</a></li> 
                            <li role="presentation" <ww:if test="#mainNavIndex=='down'">class="active"</ww:if> ><a href="<%=path_page_head %>/portal/gotoAppDown.action">客户端下载</a></li>
                            <li role="presentation" <ww:if test="#mainNavIndex=='about'">class="active"</ww:if>><a href="<%=path_page_head %>/portal/pages/about.jsp">华泰介绍</a></li> 
                        </ul>
                    </div>
                </div>
            </div>
        </div>
</div>
