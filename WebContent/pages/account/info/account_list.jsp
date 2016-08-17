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

<base href="<%=basePath%>">
<title>会员信息审核</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function editEntity(id){
		var dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'审核用户信息',
			content : 'subscriberConfirm/showCheckDetail.action',//?subscriberConfirm.id='+id,
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
	
	function searchEntity(){
		$("#sform").submit();
	}
</script>
</head>
<body style="width:100%">
	
		
		   
		   <form style="margin-left: 10px" name="sform" id="sform" method="post" action="subscriber/checkList.action">
				
				<div class="chazhao">
				   <div class="nr fl"><span>手机号:</span>
				   <input class="kd" name="subscriber.phoneNo"  type="text" value="<ww:property value="subscriber.phoneNo"/>"></div>
				   <a class="find_btn"  onclick="searchEntity();" target="_blank">查&nbsp;&nbsp;询</a>
				</div>
			
				<div class="panel_content" >
					<table width="100%" class="biaoge" border="0" cellspacing="0">
						<tr class="ths" id="tab_bg_cl">
							
							
							<td  >
								<a href="javascript:SetOrder('')">姓名<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('')">手机号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('')">驾驶证号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('')">创建时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  >
								<a href="javascript:SetOrder('')">状态<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td  width="157">
								操作
							</td>
						</tr>
				
						<ww:iterator value="page.mResults" id="data" status="rl">
							<tr
								onmouseover="currentcolor=this.style.backgroundColor;this.style.backgroundColor='#ccccff';this.style.cursor='hand';"
								 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
								
								
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="phoneNo" />
								</td>
								<td align="left">
									<ww:property value="drivingLicense" />
								</td>
								<td align="left">
									<ww:property value="transDate10String(ts)" />
								</td>
								<td align="left">
									<ww:property  value="@com.dearho.cs.subscriber.pojo.subscriber@getsubscriberState(state)"/>
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
	
</body>
</html>