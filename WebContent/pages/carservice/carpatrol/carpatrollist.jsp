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

<title>车辆巡检管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function editEntity(id){
		window.location.href="<%=path%>/carservice/carPatrolGet.action?id="+id;
	}
	function searchEntity(){
		$("#sform").submit();
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
		alertconfirm("确认删除选中的【车辆巡检信息】吗？",function (){
			showLoading();
			$.post('carPatrolDelete.action',$('#sform').serialize(),r_delete,'json').error(requestError);
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
	function deleteOneEntity(id,name){
		var pars={
				"id":id
			};
		alertconfirm("确认删除【"+name+"】的车辆巡检记录吗？",function (){
			showLoading();
			$.post('carPatrolDelete.action',pars,r_delete,'json').error(requestError);
		});	
	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carservice/carPatrolSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-md-4">
						<div class="form-group">
							<label for="carPatrol.code" class="col-sm-5 control-label">编号：</label>
							<div class="col-sm-7">
					    		 <input class="form-control" name="carPatrol.code" id="carPatrol.code" type="text" value="<ww:property value="carPatrol.code"/>">
					    	</div>
					    </div>
					    <div class="form-group">
							<label for="carPatrol.parkingPatrolCode" class="col-sm-5 control-label">网点巡检单：</label>
							<div class="col-sm-7">
					    		 <input class="form-control" name="carPatrol.parkingPatrolCode" id="carPatrol.parkingPatrolCode" type="text" value="<ww:property value="carPatrol.parkingPatrolCode"/>">
					    	</div>
					    </div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="carPatrol.plateNumber" class="col-sm-5 control-label">巡检车辆牌号：</label>
							<div class="col-sm-7">
					    		 <input class="form-control" name="carPatrol.plateNumber" id="carPatrol.plateNumber" type="text" value="<ww:property value="carPatrol.plateNumber"/>">
					    	</div>
					    </div>
					</div>
					<div class="col-md-4">
					    <div class="form-group">
							<label for="tcc" class="col-sm-4 control-label">网点：</label>
							<div class="col-sm-8">
					    		 <select class="form-control" id="tcc" name="carPatrol.parkingId" >
									<option value="">全部</option>
									<ww:iterator value="getParkings()" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="carPatrol.parkingId==id">selected=true</ww:if> ><ww:property value="nickName" /></option>	
									</ww:iterator>
								</select>
					    	</div>
					    </div>
					</div>
				</div>			
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-3"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					<div class="col-sm-2"><a class="btn btn-block Button2" onclick="editEntity();" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
	  				<div class="col-sm-2"><a class="btn btn-block Button3" onclick="deleteEntity();" target="_blank"><i class="fa fa-trash-o"></i>删除</a></div>
	  			</div>
			</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths">
						<td  height="28" width="40"  >
							<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
						</td>
						<td>
							<a href="javascript:SetOrder('code')">编号</a>
						</td>
						<td>车牌号</td>
						<td>外漆是否完好</td>
						<td>玻璃是否完好</td>
						<td>胎压</td>
						<td>网点</td>
						<td>网点巡检单号</td>
						<td>备注</td>
						<td width="157">
							操作
						</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  <ww:if test="#rl.even"> class="trs"</ww:if> >
							<td align="center">
								<input type="checkbox" name="checkdel" value="<ww:property value='id' />" />
							</td>
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<a href="javascript:showCarDetailForDialog('<ww:property value="carId" />','<%=path%>')"><ww:property value="plateNumber" /></a>
							</td>
							<td align="center">
								<ww:if test="paint==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="center">
								<ww:if test="glass==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="left">
								<ww:property value="tirePressure" />
							</td>
							<td align="left">
								<ww:property value="dotName" />
							</td>
							<td align="left">
								<ww:property value="parkingPatrolCode" />
							</td>
							<td align="left">
								<ww:property value="remark" />
							</td>
							
							<td align="center">
								<!-- <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a> -->
								<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>');">编辑</div>
								<div class="pan_btn4"  onclick="javascript:deleteOneEntity('<ww:property value="id"/>','<ww:property value="plateNumber"/>');">删除</div>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="10">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>