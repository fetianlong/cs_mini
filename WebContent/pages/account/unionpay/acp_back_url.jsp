<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<form id="pay_form"
	action="https://101.231.204.80:5000/gateway/api/frontTransReq.do"
	method="post"><input type="hidden" name="txnType" id="txnType"
	value="01" /><input type="hidden" name="frontUrl" id="frontUrl"
	value="http://localhost:8080/account/acpFrontUrl.action" /><input
	type="hidden" name="currencyCode" id="currencyCode" value="156" /><input
	type="hidden" name="channelType" id="channelType" value="08" /><input
	type="hidden" name="merId" id="merId" value="777290058114322" /><input
	type="hidden" name="txnSubType" id="txnSubType" value="01" /><input
	type="hidden" name="txnAmt" id="txnAmt" value="1" /><input
	type="hidden" name="version" id="version" value="5.0.0" /><input
	type="hidden" name="signMethod" id="signMethod" value="01" /><input
	type="hidden" name="backUrl" id="backUrl"
	value="http://222.222.222.222:8080/ACPTest/acp_back_url.do" /><input
	type="hidden" name="certId" id="certId"
	value="124876885185794726986301355951670452718" /><input type="hidden"
	name="encoding" id="encoding" value="UTF-8" /><input type="hidden"
	name="bizType" id="bizType" value="000201" /><input type="hidden"
	name="signature" id="signature"
	value="Ybjam1oBpQcve7bZdPjaaW4Zd000f79v/XYMwjGQBf1OGJCiazgKJfnc0hgI/biw5/9GyvfgncBgLYsQo7GP9xpKwDRNYyEEIL8vo8aGR24EEmOYCxSsvMhFEnRUdnwieb+3xq1M/F/XHj6NudVYVchiiIjRLB4b77pOhVHQWIM=" /><input
	type="hidden" name="orderId" id="orderId" value="20150602112810" /><input
	type="hidden" name="txnTime" id="txnTime" value="20150602112810" /><input
	type="hidden" name="accessType" id="accessType" value="0" /></form>
</body>
<script type="text/javascript">document.all.pay_form.submit();</script>
</html>
