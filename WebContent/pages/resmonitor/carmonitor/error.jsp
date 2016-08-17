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
		无数据！
		<div style="padding-top:5px;">
	</div>
	</div>
	</div>
</body>
</HTML>

