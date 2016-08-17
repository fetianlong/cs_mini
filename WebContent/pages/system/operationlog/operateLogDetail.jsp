<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>操作记录</title>

<%@ include file="/pages/common/common_head.jsp"%>

</head>
<body style="overflow-y:auto;" class="czjlPage">
      <div class="tc">

<ww:if test="logList == null">
当前数据无操作记录！
</ww:if>
<ww:else>
             <ww:iterator value="logList" id="data" status="rl">
			 <table class="table table-bordered table-condensed">
			<tbody>
             <tr>
             		<th><span>操作人</span>:</th>
	                <td><ww:property value="operatorName" /> </td>   
	                
	                <th><span>操作时间</span>:</th>
	                <td><ww:property value="transDateString(operateDate)" /> </td>
                    
                    <th><span>操作类型</span>:</th>
	                <td>
	                	<ww:if test="operateRemark == 'update'">更新操作</ww:if>
						<ww:if test="operateRemark == 'delete'">删除操作</ww:if>
						<ww:if test="operateRemark == 'add'">添加操作</ww:if>
	                </td>   
             </tr>
              <tr>
             		<th><span>操作内容</span>:</th>
	                <td colspan="5"><ww:property value="operateContent" /> </td>
             </tr>
			  </tbody>
			 </table>
             </ww:iterator>
             
             
             </ww:else>
	</div>
</body>
</html>