<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<ww:if test="subscriber==null">
 		 <div class="form-group">
		    <label for="subscriberId" class="col-sm-2 control-label">姓名</label>
		    <div class="col-sm-10">
		      <p class="form-control-static">--</p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">余额</label>
		    <div class="col-sm-10">
		      <p class="form-control-static">--</p>
		    </div>
		  </div>
		 
		  <div class="form-group">
		    <label class="col-sm-2 control-label">押金</label>
		    <div class="col-sm-10">
		      <p class="form-control-static">--</p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">可用余额</label>
		    <div class="col-sm-10">
		      <p class="form-control-static">--</p>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-5 col-sm-2">
		      <button type="button" disabled="disabled" id="submitBtn" class="btn btn-danger btn-block disabled">充值</button>
		    </div>
		  </div>
</ww:if>
<ww:else>
	  <div class="form-group">
	    <label for="subscriberId" class="col-sm-2 control-label">姓名</label>
	    <div class="col-sm-10">
	      <p class="form-control-static" id="subscriberName">
	      <a href="javascript:showSubscriberDetailForDialog('<ww:property value="subscriber.id" />','<%=path%>')"><ww:property value="subscriber.name" /></a>
	      </p>
		 <input type="hidden" id="subscriberId" name="subscriber.id"  value="<ww:property value="subscriber.id"/>">
	    </div>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">余额</label>
	    <div class="col-sm-10">
	      <p class="form-control-static" id="amountSpan"><ww:property value="formatAmount(subscriber.account.amount)"/></p>
	    </div>
	  </div>
	 
	  <div class="form-group">
	    <label class="col-sm-2 control-label">押金</label>
	    <div class="col-sm-10">
	      <p class="form-control-static" id="frozenAmountSpan"><ww:property value="formatAmount(subscriber.account.frozenAmount)"/></p>
	    </div>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-2 control-label">可用余额</label>
	    <div class="col-sm-10">
	      <p class="form-control-static"  id="usableAmountSpan"><ww:property value="formatAmount(subscriber.account.usableAmount)"/></p>
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-offset-5 col-sm-2">
	      <button type="button" onclick="submitForm()" id="submitBtn" class="btn btn-danger btn-block">充值</button>
	    </div>
	  </div>
</ww:else>
 