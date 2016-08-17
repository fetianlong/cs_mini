<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">消费详情</h4>
      </div>
      <div class="modal-body">
        <table>
			<tbody>
			
			<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==accountTradeRecord.type">
							<tr>
								<th>流水号</th>
								<td><ww:property value="accountTradeRecord.tradeOrderNo"/></td>
							</tr>
							<tr>
								<th>时间</th>
								<td><ww:property value="transDateString(accountTradeRecord.tradeTime)"/></td>
							</tr>
							<tr>
								<th>交易金额</th>
								<td><i class="fa fa-jpy"></i><ww:property value="formatAmount(accountTradeRecord.amount)"/></td>
							</tr>
							
							<tr>
								<th>交易类型</th>
								<td>充值</td>
							</tr>
							<tr>
								<th>支付方式</th>
								<td><ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(accountTradeRecord.payType)"/></td>
							</tr>
							<tr>
								<th>交易来源</th>
								<td><ww:property value="@com.dearho.cs.account.pojo.Account@getPayChannel(accountTradeRecord.payChannel)"/></td>
							</tr>
							
				</ww:if>
				<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==accountTradeRecord.type">
					<tr>
								<th>流水号</th>
								<td><ww:property value="accountTradeRecord.tradeOrderNo"/></td>
							</tr>
							<tr>
								<th>时间</th>
								<td><ww:property value="transDateString(accountTradeRecord.tradeTime)"/></td>
							</tr>
							<tr>
								<th>交易金额</th>
								<td ><i class="fa fa-jpy"></i><ww:property value="formatAmount(accountTradeRecord.amount)"/></td>
							</tr>
							
							
							<tr>
								<th>交易类型</th>
								<td>退款</td>
							</tr>
							
							<tr>
								<th>支付方式</th>
								<td><ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(accountTradeRecord.payType)"/></td>
							</tr>
				</ww:elseif>
				<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==accountTradeRecord.type">
							<tr>
								<th>流水号</th>
								<td><ww:property value="accountTradeRecord.tradeOrderNo"/></td>
							</tr>
							<tr>
								<th>订单编号</th>
								<td><ww:property value="accountTradeRecord.bizId"/></td>
							</tr>
							<tr>
								<th>时间</th>
								<td><ww:property value="transDateString(accountTradeRecord.tradeTime)"/></td>
							</tr>
							<tr>
								<th>交易金额</th>
								<td ><i class="fa fa-jpy"></i><ww:property value="formatAmount(accountTradeRecord.amount)"/></td>
							</tr>
							
							
							<tr>
								<th>交易类型</th>
								<td>租车</td>
							</tr>
							<tr>
								<th>支付方式</th>
								<td><ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(accountTradeRecord.payType)"/></td>
							</tr>
							<tr>
								<th>交易来源</th>
								<td><ww:property value="@com.dearho.cs.account.pojo.Account@getPayChannel(accountTradeRecord.payChannel)"/></td>
							</tr>
							
				</ww:elseif>
				<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_CUT_PAYMENT==accountTradeRecord.type">
					<tr>
								<th>流水号</th>
								<td><ww:property value="accountTradeRecord.tradeOrderNo"/></td>
							</tr>
							<tr>
								<th>时间</th>
								<td><ww:property value="transDateString(accountTradeRecord.tradeTime)"/></td>
							</tr>
							<tr>
								<th>交易金额</th>
								<td ><i class="fa fa-jpy"></i><ww:property value="formatAmount(accountTradeRecord.amount)"/></td>
							</tr>
							
							
							<tr>
								<th>交易类型</th>
								<td>扣款</td>
							</tr>
							
				</ww:elseif>
			
			
				
			</tbody>
		</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
