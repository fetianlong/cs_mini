<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<% String path_left_menu = request.getContextPath(); %>

<script type="text/javascript">
function alertMsg(msg){
	$('#myModal').modal('show');
	$("#modelMsg").html(msg);
};

$(function(){
	Height = $(".CenterBlock ul").height();
	if ($(".ContentBlock").height() > Height){
		$(".CenterBlock ul").height($(".ContentBlock").height());
	} else {
		$(".CenterBlock ul li:last").css("border-bottom", "none");
	};
});
</script>

<!--个人中心公共控制-->
 <div class="col-sm-3 CenterBlock">
 	<div class="Border">
     	<ul>
         	<li>我的华泰</li>
             <li  <ww:if test="#pageindexpage=='userinfo'">class="active"</ww:if> ><i class="fa fa-caret-right"></i><a href="<%=path_left_menu %>/portal/subscriber/showBaseInfo.action">个人资料</a></li>
             <li  <ww:if test="#pageindexpage=='myorder'">class="active"</ww:if> ><i class="fa fa-caret-right"></i><a href="<%=path_left_menu%>/portal/orders/portalOrdersSearch.action">我的订单</a></li>
             <li  <ww:if test="#pageindexpage=='records'">class="active"</ww:if>><i class="fa fa-caret-right"></i><a href="<%=path_left_menu%>/portal/account/showTradingList.action">消费记录</a></li>
             <li  <ww:if test="#pageindexpage=='credit'">class="active"</ww:if>><i class="fa fa-caret-right"></i><a href="<%=path_left_menu%>/portal/account/accountCreditCardManage.action">信用卡管理</a></li>
             <li  <ww:if test="#pageindexpage=='account'">class="active"</ww:if>><i class="fa fa-caret-right"></i><a href="<%=path_left_menu %>/portal/account/index.action">个人账户</a></li>
             <li  <ww:if test="#pageindexpage=='changepwd'">class="active"</ww:if>><i class="fa fa-caret-right"></i><a href="<%=path_left_menu %>/portal/subscriber/portalChangePassword.action">修改密码</a></li>
             <li  <ww:if test="#pageindexpage=='changephone'">class="active"</ww:if>><i class="fa fa-caret-right"></i><a href="<%=path_left_menu%>/portal/pages/subscriber/changephone.jsp">修改手机号</a></li>
         </ul>
     </div>
 </div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">提示 </h4>
         </div>
         <div class="modal-body" id="modelMsg"></div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>

