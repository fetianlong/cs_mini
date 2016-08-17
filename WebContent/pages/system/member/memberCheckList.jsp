<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>会员信息审核</title>
<link href="<%=path%>/common/css/common.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="common/css/main/fonts/linecons/css/linecons.css">
<link rel="stylesheet" href="common/css/main/font-awesome.min.css">
<link rel="stylesheet" href="common/css/main/bootstrap.css">
<link rel="stylesheet" href="common/css/main/xenon-core.css">
<link rel="stylesheet" href="common/css/main/xenon-forms.css">
<link rel="stylesheet" href="common/css/main/xenon-components.css">
<link rel="stylesheet" href="common/css/main/xenon-skins.css">

<script type="text/javascript" src="common/js/common.js"></script>
<script type="text/javascript" src="common/js/page.js"></script>
<script type="text/javascript" src="common/js/checkbox.js"></script>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>

<!-- Bottom Scripts -->
<script src="common/js/main/bootstrap.min.js"></script>
<script src="common/js/main/TweenMax.min.js"></script>
<script src="common/js/main/resizeable.js"></script>
<script src="common/js/main/joinable.js"></script>
<script src="common/js/main/xenon-api.js"></script>
<script src="common/js/main/xenon-toggles.js"></script>


<!-- Imported scripts on this page -->
<script src="common/js/main/globalize.min.js"></script>



<!-- JavaScripts initializations and stuff -->
<script src="common/js/main/xenon-custom.js"></script>
<script type="text/javascript">
	function editEntity(id){
		var dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'审核用户信息',
			content : 'url:member/showCheckDetail.action?member.id='+id,
			fixed:true,
			width:800,
			height:600,
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
				alertok(data.msg, function(){
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
			$.post('carBindDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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
	function searchEntity(){
		$("#sform").submit();
	}
</script>
</head>
<body style="width:100%">
	<div >
		<div class="col-sm-12">
			<div class="panel">
				<div class="panel-heading">
					<h3 class="panel-title">会员信息审核管理</h3>							
		        </div>
		    </div>
		   
		   <form style="margin-left: 10px" name="sform" id="sform" method="post" action="member/checkList.action">
				
				<div class="chazhao">
				   <div class="nr fl"><span>手机号:</span>
				   <input class="kd" name="member.phoneNo"  type="text" value="<ww:property value="member.phoneNo"/>"></div>
				   <a class="find_btn"  onclick="searchEntity();" target="_blank">查&nbsp;&nbsp;询</a>
				</div>
			
				<div class="panel_content" >
					<table width="100%" class="biaoge" border="0" cellspacing="0">
						<tr class="ths">
							
							
							<td  >
								<a href="javascript:SetOrder('')">姓名</a>
							</td>
							<td  >
								<a href="javascript:SetOrder('')">手机号</a>
							</td>
							<td  >
								<a href="javascript:SetOrder('')">创建时间</a>
							</td>
							<td  width="157">
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.mResults" id="data" status="rl">
							<tr
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> >
								
								
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="phoneNo" />
								</td>
								<td align="left">
									<ww:property value="transDate10String(ts)" />
								</td>
								<td align="center">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
								</td>
							</tr>
						</ww:iterator>
						<tr style="background-color: #fff;height: 30px;">
							<td align="center" colspan="4">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	
</body>
</html>