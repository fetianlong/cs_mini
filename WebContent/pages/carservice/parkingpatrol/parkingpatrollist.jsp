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
<title>网点巡检管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function editEntity(id){
		window.location.href="<%=path%>/carservice/parkingPatrolGet.action?id="+id;
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
		alertconfirm("确认删除选中的【网点巡检信息】吗？",function (){
			showLoading();
			$.post('parkingPatrolDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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
	function deleteOneEntity(id,name){
		var pars={
				"id":id
			};
		alertconfirm("确认删除【"+name+"】吗？",function (){
			showLoading();
			$.post('parkingPatrolDelete.action',pars,r_delete,'json').error(requestError);
		});	
	}
	function changeDiscard(id,isDiscard){
		if(isDiscard == '0'){
			alertconfirm("确认废弃吗？",function (){
				showLoading();
				var par = {'id':id,'isDiscard':isDiscard};
				$.post('parkingPatrolChangeDiscard.action',par,function(data){
					hideLoading();
					switch(data.result){
						case 0:
							alertok("废弃成功！", function(){
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
				},'json').error(requestError);
			});	
		}
		else{
			var par = {'id':id,'isDiscard':isDiscard};
			showLoading();
			$.post('parkingPatrolChangeDiscard.action',par,function(data){
				hideLoading();
				switch(data.result){
					case 0:
						alertok("找回成功！", function(){
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
			},'json').error(requestError);
		}
		
	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carservice/parkingPatrolSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						<div class="form-group">
							<label for="parkingPatrol.code" class="col-xs-5 control-label">编号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="parkingPatrol.code" id="parkingPatrol.code" type="text" value="<ww:property value="parkingPatrol.code"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="yycs" class="col-xs-5 control-label">运营城市：</label>
							<div class="col-xs-7">
					    		 <select class="form-control" id="yycs" name="parkingPatrol.cityId" >
									 <option value="">全部</option>
									 <ww:iterator value="getCitys()" id="data" status="rl">
										 <option value="<ww:property value="id" />"  <ww:if test="parkingPatrol.cityId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
									 </ww:iterator>
								 </select>
					    	</div>
					    </div>
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label for="parkingPatrol.parkingName" class="col-xs-5 control-label">网点简称：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="parkingPatrol.parkingName" id="parkingPatrol.parkingName" type="text" value="<ww:property value="parkingPatrol.parkingName"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="sffq" class="col-xs-5 control-label">是否废弃：</label>
							<div class="col-xs-7">
					    		 <select class="form-control" id="sffq" name="parkingPatrol.isDiscard" >
									<option value="">全部</option>
									<option value="1" <ww:if test="parkingPatrol.isDiscard==1">selected=true</ww:if> >废弃</option>	
									<option value="0" <ww:if test="parkingPatrol.isDiscard==0">selected=true</ww:if>>启用</option>	
								</select>
					    	</div>
					    </div>
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label for="parkingPatrol.userName" class="col-xs-5 control-label">巡检人：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" name="parkingPatrol.userName" id="parkingPatrol.userName" type="text" value="<ww:property value="parkingPatrol.userName"/>">
					    	</div>
					    </div>
					</div>
				</div>
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					<div class="col-sm-2 col-xs-4"><a class="btn btn-block Button2" onclick="editEntity();" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
	  				<div class="col-sm-2 col-xs-4"><a class="btn btn-block Button3" onclick="deleteEntity();" target="_blank"><i class="fa fa-trash-o"></i>删除</a></div>
	  			</div>
	  		</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td  height="28" width="40"  >
							<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
						</td>
						<td>
							<a href="javascript:SetOrder('code')">编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td>网点</td>
						<td>巡检人</td>
						<td>巡检时间</td>
						<td>备注</td>
						<td width="157">
							操作
						</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<input type="checkbox" name="checkdel" value="<ww:property value='id' />" />
							</td>
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<ww:property value="dotName" />
							</td>
							<td align="left">
								<ww:property value="userName" />
							</td>
							<td align="center">
								<ww:property value="transDate10String(patrolTime)" />
							</td>
							<td>
								<ww:property value="remark" />
							</td>
							
							<td align="center">
								<!-- <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a> -->
								<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
								<div class="pan_btn4"  onclick="javascript:changeDiscard('<ww:property value="id"/>','<ww:property value="isDiscard"/>');"><ww:if test="isDiscard == 0">废弃</ww:if><ww:else>找回</ww:else></div>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="11">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>