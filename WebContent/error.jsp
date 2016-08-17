<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="ww" uri="webwork" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<HTML>
<HEAD>
<link href="<%=path%>/common/css/editcommon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/json2.js"></script>
<script type="text/javascript">
function isError(){
	return true;
}
var errorObject = eval(<ww:property value="result"/>);
</script>
</HEAD>
<body>
	<div id=errordiv>
	<div id="funcTitle">系统信息</div>
	<div style="padding-top:5px;"></div>
	<div id=errorTable>
		<table>
	    	<tr>
	      		<td style="padding:5px;line-height: 20px;" id="msgcontent">
			 	</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$("#msgcontent").html(errorObject.msg);
	</script>
	<div style="padding-top:5px;">
		<table style="width: 100%">
			<tr>
				<td id="submitId">
					<ww:if test="timeOut == true">
						<input type="button" class="ydbutton" value="重新登录"  onclick="top.location='<%=path%>/'" />
					</ww:if>
					<ww:if test="showBack == true">
						<input class="ydbutton" type="button" value="返  回" onclick="javascript:history.go(-1);"/>
					</ww:if>
				</td>
			</tr>
		</table>
		<div style="padding-top:5px;">
	</div>
	</div>
	</div>
</body>
</HTML>

