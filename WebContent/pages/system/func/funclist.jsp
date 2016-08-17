<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function editEntity(id){
		var dialoguser = $.dialog({
		    id:'useredit', 
		    title:(id == "")?"新添功能":"编辑功能",
			content : 'url:<%=path%>/functions/functionGet.action?id='+id,
			fixed:true,
			width:420,
			height:380,
			resize:false,
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
		    okVal:isnull(id)?'添加':'保存',
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
				alertok("保存成功！", function(){
					$('#sform').submit();	
			    });
				break;
			case 1:
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
		return false;
}
	function deleteEntity(){
		var ob = document.getElementsByName("checkdel");
		var check = false;
		for (var i = 0; i < ob.length; i++) {
			if (ob[i].checked) {
				check = true;
				break;
			}
		}
		if (!check) {
			alertinfo("请选择要删除的数据！");
			return false;
		}
		alertconfirm("确认删除选中的数据吗？",function (){
			showLoading();
			$.post('functionDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
		});	
	}
	function r_delete(data){
		hideLoading();
		switch(data.result){
			case 0:
				alertok("删除成功！", function(){
			    	$('#sform').submit();		
			    });
				break;
			case 1:
				restoreInfo();
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
	}
</script>
</head>
<body>
	<h1>功能管理</h1>
	<form name="sform" id="sform" method="post" action="<%=path%>/functions/functionSearch.action">
		<input type="hidden" name="page.orderFlag" id="page.orderFlag"
				value="<ww:property value="page.orderFlag"/>">
		<input type="hidden" name="page.orderString" id="page.orderString"
				value="<ww:property value="page.orderString"/>">
		<table style="min-width: 880px;">
			<tr>
				<td>
					功能名：<input name="sEntity.name" id="sEntity.name" size=10 value="<ww:property value="sEntity.name"/>">
					<input class="csbutton" type="button" onclick="searchEntity();" value="查询">
					<input class="csbutton" type="button" onclick="editEntity('');" value="添加">
					<input class="csbutton" type="button" onclick="deleteEntity();" value="删除">
				</td>
			</tr>
		</table>
		<table style="min-width: 880px;margin-top: 5px;">
			<tr>
				<th align="center" nowrap height="28" width="40">
					<input type="checkbox" name="checkdelcheckall"
										onclick="funCheck('','checkdel')">
				</th>
				<th align="center" nowrap width=90>
					操作
				</th>
				<th align="center" nowrap width="100">
					<a href="javascript:SetOrder('name')">功能名</a>
				</th>
				<th align="center" nowrap width="80">
					<a href="javascript:SetOrder('password')">描述</a>
				</th>
			</tr>
	
			<ww:iterator value="page.results" id="data" status="rl">
				<tr
					
					 <ww:if test="#rl.even"> style="background-color: #F2F2F2;"</ww:if> >
					<td align="center">
						<input type="checkbox" name="checkdel" 
							value="<ww:property value="id"/>">
					</td>
					<td align="center">
						<a href="javascript:editEntity('<ww:property value="id"/>');">编辑</a>&nbsp&nbsp
					</td>
					<td align="left">
						<ww:property value="name" />
					</td>
					<td align="left">
						<ww:property value="funDesc" />
					</td>
					<td align="center">
						
					</td>
				</tr>
			</ww:iterator>
			<tr style="background-color: #fff;height: 30px;">
				<td align="center" colspan="5">
					<ww:property value="page.pageSplit" />	
				</td>
			</tr>
		</table>
	</form>
</body>
</html>