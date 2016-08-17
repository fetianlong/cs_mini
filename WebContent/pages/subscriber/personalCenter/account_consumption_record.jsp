<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消费记录</title>
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.7.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/common/portal/css/style.css"/>
<script type="text/javascript" src="<%=path%>/common/js/Calendar/WdatePicker.js"></script>

<script type="text/javascript" src="<%=path%>/common/js/popup_layer.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>


<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}

</style>

<script type="text/javascript">
$(document).ready(function(){
	

	//示例9，综合效果
	var t9 = new PopupLayer({
		trigger:"#ele9",
		popupBlk:"#blk9",
		closeBtn:"#close9",
		useOverlay:true,
		useFx:true,
		offsets:{
			x:120,
			y:-41
		}
	});
	t9.doEffects = function(way){
		if(way == "open"){
			this.popupLayer.css({opacity:0.3}).show(300,function(){
				this.popupLayer.animate({
					left:($(document).width() - this.popupLayer.width())/2,
					top:(document.documentElement.clientHeight - this.popupLayer.height())/4 + $(document).scrollTop(),
					opacity:0.8
				},300,function(){this.popupLayer.css("opacity",1)}.binding(this));
			}.binding(this));
		}
		else{
			this.popupLayer.animate({
				left:this.trigger.offset().left,
				top:this.trigger.offset().top,
				opacity:0.1
			},{duration:200,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
		}
	}
	
	
});
</script>

<script type="text/javascript">
$(document).ready(function(){


	$("a[name='dateOption']").click(function(){

		$("a[name='dateOption']").each(function(){
			$(this).removeClass("sl");
		});
		$(this).addClass("sl");
	     
	});
});

function dateChange(){
	var fromDate=document.getElementById('fromDateTmp').value;
	var toDateTmp=document.getElementById('toDateTmp').value;
	
	searchRecord(fromDate,toDateTmp,'SS');
}

function searchRecord(start,end,dateType){
	document.getElementById('fromDate').value=start;
	document.getElementById('toDate').value=end;
	document.getElementById('dateType').value=dateType;
	
	document.sform.submit();
}
function searchBusinessRecord(type){
	$("#businessType").val(type);
	document.sform.submit();
}
function skipToPage(page)
{
	document.getElementById('page.currentPage').value=page;
	document.sform.submit();
}


function recordDecal(id){
	$.ajax({
		type: "POST",
	    url:"<%=path%>/account/showTradingRecord.action?accountTradeRecord.id="+id,
	    dataType:'json',
	    data:$('#refundForm').serialize(),
	    success: function(data) {
			if(0==data.result){
				
				$("#xqType").html(data.type);
				$("#xqAmount").html(data.amount);
				$("#xqPayType").html(data.payType);
				$("#xqAccount").html(data.alipayAccount);
				$("#xqTime").html(data.tradeTime);
				$("#orderId").html(data.orderId);
				$("#autoClear").html(data.orderAutoClear);
				
				$("#ele9").click();
		    }else{
		    	window.parent.alertMsg("提示",data.msg);
			   // alert(data.msg);
	    	
		    }
	    }
	});
	
	
}
</script>

<script type="text/javascript">
	function showEntity(id){
		var dialoguser = $.dialog({
		    id:'carBindedit', 
		    title:'消费详情',
			content : 'url:<%=path%>/account/gotoTradingRecord.action?accountTradeRecord.id='+id,
			fixed:true,
			width:350,
			height:400,
			resize:false,
	 		max: false,
		    min: false,
		    lock: true,
		    ok: false,
		    cancelVal: '关闭',
		    cancel: true,
		    close: function () {
		        this.hide();
		       // restoreInfo('hospitalinfo');
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
	</script>

</head>

<body>

<form name="sform" id="sform" action="<%=path%>/account/queryTradingRecord.action"  method="post">
<input id="fromDate"  name="fromDate" type="hidden" value="<ww:property value="fromDate"/>" />
<input id="toDate"  name="toDate"  type="hidden"  value="<ww:property value="toDate"/>" />
<input type="hidden" id="dateType" name="dateType" value="<ww:property value="#request.dateType"/>" />
<input type="hidden" id="businessType" name="accountTradeRecord.type" value="<ww:property value="accountTradeRecord.type"/>" />
<input type="hidden" id="page.currentPage" name="page.currentPage" value='<ww:property value="page.currentPage"/>'/>
<input type="hidden" value='<ww:property value="page.pageSize"/>' id="page.pageSize" name="page.pageSize" />
</form>	
	    			
	 <div id="ele9" style="display:none;" >元素</div>      		    			
	

 <!--消费记录 开始-->
        <div class="zhgl">
             <div class="xyk_tit" style="border-bottom:1px solid #b6eddb; ">
                <span class="xf fr">可用余额：
                <span class="sz"><ww:property  value="formatAmount(account.amount)"/> </span>
                <span class="yy">元</span>|
                <li class="qcz"><a  href="<%=path%>/account/gotoRecharge.action">去充值</a></li>
                </span>
                消费记录
             </div>
             <div class="box_x">
               <table class="riqi" width="619" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <th align="right" width="100">起止日期：</th>
                    <td align="left" width="208">
                    	<input class="input_s" id="fromDateTmp" value='<ww:property value="transDate10String(fromDate)"/>' class="Wdate" type="text" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>-<input class="input_s" id="toDateTmp"  value='<ww:property value="transDate10String(toDate)"/>' class="Wdate" type="text" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
                    </td>
                    <td> <div class="zh_btn_cz" style="cursor:pointer;margin-left:10px;" onclick="dateChange()">查询</div>  </td>
                    <td><a id="J-today" name="dateOption" <ww:if test="'oneD'.equals(#request.dateType)">class="sl"</ww:if> href="javascript:searchRecord('<ww:property  value="transDate10String(#request.today)"/>','<ww:property  value="transDate10String(#request.today)"/>','oneD')" >今天</a></td>
                    <td><a id="J-one-month" name="dateOption" <ww:if test="'oneM'.equals(#request.dateType)">class="sl"</ww:if> href="javascript:searchRecord('<ww:property  value="transDate10String(#request.lateMonth)"/>','<ww:property  value="transDate10String(#request.today)"/>','oneM')" >最近1个月</a></td>
                    <td><a id="J-three-month" name="dateOption" <ww:if test="'threeM'.equals(#request.dateType)">class="sl"</ww:if> href="javascript:searchRecord('<ww:property  value="transDate10String(#request.lastThreeMonth)"/>','<ww:property  value="transDate10String(#request.today)"/>','threeM')" >3个月</a></td>
                    <td><a id="J-six-year" name="dateOption" <ww:if test="'sixM'.equals(#request.dateType)">class="sl"</ww:if> href="javascript:searchRecord('<ww:property  value="transDate10String(#request.lastSixMonth)"/>','<ww:property  value="transDate10String(#request.today)"/>','sixM')">半年</a></td>

                    
                  </tr>
              </table> 
             </div>
              <div class="box_x">
               <table class="riqi" width="328" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <th align="right" width="100">交易类型：</th>              
                    <td><a  name="businessType" <ww:if test="accountTradeRecord.type==null">class="sl"</ww:if>  href="javascript:searchBusinessRecord('')" >全&nbsp;&nbsp;部</a></td>           
                    <td><a  name="businessType" <ww:if test="'1'.toString().equals(accountTradeRecord.type.toString())">class="sl"</ww:if>  href="javascript:searchBusinessRecord('1')" >充&nbsp;&nbsp;值</a></td>
                    <td><a  name="businessType" <ww:if test="'2'.toString().equals(accountTradeRecord.type.toString())">class="sl"</ww:if> href="javascript:searchBusinessRecord('2')" >退&nbsp;&nbsp;款</a></td>
                    <td><a  name="businessType" <ww:if test="'5'.toString().equals(accountTradeRecord.type.toString())">class="sl"</ww:if> href="javascript:searchBusinessRecord('5')">租车</a></td>
                   
                    <td></td>
                  </tr>
              </table> 
             </div>
             <table width="956" class="jylx" border="0" cellpadding="0" cellspacing="1">
                  <tr>
                    <th>序号 </th>
                    <th>交易类型</th>
                    <th>交易金额</th>
                    <th>交易时间</th>
                    <th>操作</th>
                  </tr>
                
	               <%
		               Integer index=1;
		               try{
		            	   index=(Integer)request.getAttribute("pageIndex");
		               }catch(Exception e){}
	               %>
                  <ww:iterator value="page.mResults"   status="st">
					<tr>
						<td><%=index++ %> </td>
						<td>
							<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type"><span class="green">充值</span></ww:if>
							<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type"><span class="tk">退款</span></ww:elseif>
							<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type"><span class="tk">租车</span></ww:elseif>
							<ww:else>其他</ww:else>
						</td>
						
						<td>
							<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type"><span class="green"><ww:property  value="formatAmount(amount)"/></span></ww:if>
							<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type"><span class="tk"><ww:property  value="formatAmount(amount)"/></span></ww:elseif>
							<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type"><span class="tk"><ww:property  value="formatAmount(amount)"/></span></ww:elseif>
							<ww:else><ww:property  value="formatAmount(amount)"/></ww:else>
						</td>
						<td><ww:property value="transDateString(tradeTime)"/></td>
						
						
						<td>
						<!--  	<input class="ck" style="cursor:pointer;" type="button" onclick="recordDecal('<ww:property value="id"/>','<ww:property value="type"/>')" value="查看详情" />-->
							<input class="ck" style="cursor:pointer;" type="button" onclick="showEntity('<ww:property value="id"/>')" value="查看详情" />
							
						</td>
					</tr>
				</ww:iterator>
                 
            </table>
            
            <div class="fy">
            <a href="javascript:skipToPage(<ww:if test="page.currentPage-1>0"><ww:property value="page.currentPage-1"/></ww:if><ww:else><ww:property value="page.currentPage"/></ww:else>)" class="syy">上一页</a><span><ww:property value="1+(page.pageSize)*(page.currentPage-1)"/>-<ww:property value="(page.pageSize * page.currentPage)>page.totalRows?page.totalRows:(page.pageSize * page.currentPage)"/>条，共<ww:property value="page.totalRows"/>条记录</span><a class="xyy" href="javascript:skipToPage(<ww:if test="page.currentPage+1>page.totalPages"><ww:property value="page.currentPage"/></ww:if><ww:else><ww:property value="page.currentPage+1"/></ww:else>)">下一页</a></div>
          
            
           
            
                    
        </div>
        <!--消费记录 开始-->
        
        
        
	<!-- 弹框 start -->
	
        
        <div id="blk9" class="blk">
            
            <div class="main">
                <h2>消费详情</h2>
                <a href="javascript:void(0)" id="close9" class="closeBtn">&times;</a> 
                <table class="tabb" width="300" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="100">交易类型：</td>
                        <td style="text-align:left"><span id="xqType"></span></td>
                      </tr>
                      <tr>
                        <td>交易金额：</td>
                        <td style="text-align:left"><span  id="xqAmount"></span></td>
                      </tr>
                      <tr>
                        <td>支付方式：</td>
                        <td  style="text-align:left" ><span  id="xqPayType"></span></td>
                      </tr>
                      <tr>
                        <td>支付账户：</td>
                        <td style="text-align:left"><span   id="xqAccount"></span></td>
                      </tr>
                      <tr>
                        <td>交易时间：</td>
                        <td style="text-align:left"><span   id="xqTime"></span></td>
                      </tr>
                       <tr>
                        <td>订单ID：</td>
                        <td style="text-align:left"><span   id="orderId"></span></td>
                      </tr>
                       <tr>
                        <td>自动取消：</td>
                        <td style="text-align:left"><span   id="autoClear"></span></td>
                      </tr>
                    </table>

                
            </div>      
        </div>
<!-- 弹框 end -->
        
        
</body>
</html>
