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
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<title>车辆管理</title>

<script type="text/javascript">
var deleteMenuURL = "<%=path%>/common/menuDelete.action";
//添加用户回调函数
function addMenudialogAjaxDone(data){
	if(data.result == 0 ){
		$.pdialog.close("addOrUpdateMenuDialog"); 
		alertMsg.correct('您的数据提交成功！');
		navTabPageBreak();
	}else{
		alertMsg.error(data.msg);
	}
}
//删除车辆
function deleteMenu(){
	var menuIds = [];
	$("input[name='menuIds']:checked").each(function(i) {
		menuIds.push($(this).val());
	});
		if(menuIds==null || menuIds.length<1){
			alertMsg.info('请选择要删除的数据！');
		}else{
			$.ajax({
				type: "POST",
				url : deleteMenuURL,
				data : $('#sform').serialize(),
				dataType : "json",
				success : function(data){
		    		if(data.result == 0){
		    			alertMsg.correct('菜单删除成功');
		    			navTabPageBreak();
		    		}
		    		else{
		    			alertMsg.error(data.msg);
		    		}
				}
			});
		}
}
</script>
</head>
<body>
<div class="navTab-panel tabsPageContent layoutBox" style="height: 645px;">
					<div layouth="80" class="pageFormContent" style="background: transparent url(&quot;images/bg_admin_main.png&quot;) no-repeat scroll center center; margin-right: auto; display: none; height: 565px; overflow: auto;"></div>
				<div class="page unitBox" style="display: block;">


<div class="pageHeader">
		<form method="post" action="<%=path%>/common/menuSearch.action" onsubmit="return navTabSearch(this);" rel="pagerForm" id="pagerForm">
			<input type="hidden" name="pageNum" value="<ww:property value="page.currentPage" />" /> 
			<input type="hidden" name="numPerPage"	value="<ww:property value="page.pageSize" />" />
				<div class="searchBar">
		<table class="searchContent">
			<tbody><tr>
				<td>
					父级节点id:
					<select class="status" name="menu.menuPid" >
						<option value="">全部</option>
						<ww:iterator value="getAllParentMenu()" id="data" status="rl">
							<option value="<ww:property value="menuId" />"  <ww:if test="menu.menuPid==menuId">selected=true</ww:if> ><ww:property value="menuName" /></option>	
						</ww:iterator>
					</select>
				</td>
				</tr>
		</tbody></table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
		</form>
	</div>
<div class="pageContent j-resizeGrid">
<div class="panelBar">
		<ul class="toolBar">
					<li><a title="添加" rel="addOrUpdateMenuDialog" target="dialog" height="580" mask="true" href="<%=path%>/common/goMenuAdd.action?state=get" class="add"><span>添加</span></a></li>
					<li><a title="删除" href="javaScript:deleteMenu();" class="delete" ><span>删除</span></a></li>
		</ul>
	</div>
	<form class="grid" id="sform">
		<div class="gridHeader"><div class="gridThead"><table style="width:1203px;"><thead>
			<tr>
				<th style="text-align: center; "><div title="" class="gridCol"><input type="checkbox" class="checkboxCtrl" group="menuIds"></div></th>
				<th style="text-align: center; "><div title="菜单id" class="gridCol">菜单id</div></th>
				<th style="text-align: center;"><div title="菜单名" class="gridCol">菜单名</div></th>
				<th style="text-align: center;"><div title="菜单url" class="gridCol">菜单url</div></th>
				<th style="text-align: center; "><div title="菜单类型" class="gridCol">菜单类型</div></th>
				<th style="text-align: center;"><div title="父级菜单id" class="gridCol">父级菜单id</div></th>
				<th style="text-align: center;"><div title="父级节点" class="gridCol">父级节点</div></th>
				<th style="text-align: center;"><div title="操作" class="gridCol">操作</div></th>
			</tr>
		</thead></table></div></div>
		<div style="width: 1223px; height: 507px; overflow: auto;" layouth="138" class="gridScroller"><div class="gridTbody"><table style="width:1203px;"><tbody id="groupListData">
			
				<ww:iterator value="page.results" id="data" status="rl">
				
					<tr class="">
						<td width="3%" style="text-align: center; "><div><input type="checkbox" value="<ww:property value="menuId" />" name="menuIds"></div></td>
						<td width="8%" style="text-align: center; "><div><ww:property value="menuId" /></div></td>
						<td width="35%" style="text-align: center;"><div><ww:property value="menuName" /></div></td>
						<td width="40%" style="text-align: left; "><div><ww:property value="menuUrl" /></div></td>
						<td width="3%" style="text-align: center; "><div><ww:property value="menuType" /></div></td>
						<td width="8%" style="text-align: center; "><div><ww:property value="menuPid" /></div></td>
						<td width="15%" style="text-align: center; "><div>
								<ww:property value="getMenuNameById(menuPid)"/>
						</div></td>
						
						<td width="6%" style="text-align: center; "><div>
						<div style="background:#FFFFFF" class="panelBar">
		               <ul class="toolBar">
								<a title="修改" rel="addOrUpdateMenuDialog" target="dialog" height="580" mask="true" href="<%=path %>/common/goMenuUpdate.action?id=<ww:property value="menuId" />&state=get" class="edit"><span>修改</span></a>
						</ul>
						</div>
						</div></td>
					</tr>
				 </ww:iterator>
<!-- 				<tr style="background-color: #fff;height: 30px;"> -->
<!-- 					<td align="center" colspan="10"> -->
<%-- 						<ww:property value="page.pageSplit" />	 --%>
<!-- 					</td> -->
<!-- 				</tr> -->
			
		</tbody></table>
		
		
		</div></div>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select id="identPageSizeChange" class="combox" rel="identManagerNav" name="numPerPage"

					onchange="navTabPageBreak({numPerPage:this.value})">
					
						<option value="10" <ww:if test="numPerPage == 10">selected=selected</ww:if>>10</option>
					
						<option value="20" <ww:if test="numPerPage == 20">selected=selected</ww:if>>20</option>
					
						<option value="50" <ww:if test="numPerPage == 50">selected=selected</ww:if>>50</option>
					
						<option value="100" <ww:if test="numPerPage == 100">selected=selected</ww:if>>100</option>
					
						<option value="200" <ww:if test="numPerPage == 200">selected=selected</ww:if>>200</option>
					
				</select> <span>条，共<ww:property value="page.totalRows" />条</span>
			</div>

			<div class="pagination" targetType="navTab" totalCount="<ww:property value="page.totalRows" />" numPerPage="<ww:property value="page.pageSize" />"
				pageNumShown="<ww:property value="page.pageSize" />" currentPage="<ww:property value="page.currentPage" />"></div>

		</div>
	</form>
	
</div>
</div>
</div>
</body>
</html>