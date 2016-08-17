<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"
>
<html>
<head>
<TITLE>授权</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="<%=path%>/common/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path%>/common/js/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/ztree/js/jquery.ztree.core-3.5.js"></script>

<script type="text/javascript" src="<%=path%>/common/js/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	
<script type="text/javascript">



function isValid(){

		var arrayMenu = [];
	  	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	    var nodes = treeObj.getCheckedNodes(true);
	    var checkedIds="";
		if(nodes.length>0){
			 for(var i=0;i<nodes.length;i++){
				 arrayMenu.push(nodes[i].id);
				}
		}
		$("#privilegeIdS").val(arrayMenu);
	return true;
	
}

function getForm(){
	return $("#eform");
}


</script>
	<SCRIPT type="text/javascript">
	var setting = {
			check: {
				enable: true,
				chkboxType : { "Y" : "ps", "N" : "ps" }
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: 121
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
			
		};
		function zTreeOnClick(event, treeId, treeNode) {
		   // alert(treeNode.id + ", " + treeNode.name+","+treeNode.pId);
		    
		    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		    var nodes = treeObj.getCheckedNodes(true);
		    var checkedIds="";
			if(nodes.length>0){
				 
				 for(var i=0;i<nodes.length;i++){
					 checkedIds+=nodes[i].id+",";
					}
			}
			alert(checkedIds);
		   
		};

		var zNodes =<ww:property value="#request.menuListStr"/>;
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
	</SCRIPT>
</head>
<BODY>

 <form id="eform" action="<%=path %>/group/groupAddPrivilege.action">
 <input type="hidden" name="group.groupId" value="<ww:property value='group.groupId'/>">
  <input type="hidden" name="privilegeIdS" id="privilegeIdS">
 </form>

<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	
</div>
</BODY>
</HTML>