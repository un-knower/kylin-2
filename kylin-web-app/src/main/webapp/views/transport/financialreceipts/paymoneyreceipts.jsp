<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>分理收据交钱</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/receipts/css/receipt.css?v=01"/>
</head>
<body>
<div id="rrapp" v-cloak>
     <div class="lookDiv">
		<span class='layui-breadcrumb breadcrumb'>
			<a href='javaScript:void(0);'>当前位置</a>
			<a href='javaScript:void(0);'>分理收据交钱</a>
		</span>
		<hr>
		<form class='layui-form searchForm'>
		   <div class='layui-inline'>
				<div class='layui-input-inline'>
					<input name="s_type" value="收据号" title="收据号" checked="" type="radio" class="sj_radio" lay-filter='sj_radio'>
				    <input name="s_type" value="运单编号" title="运单编号" type="radio" class="yd_radio" lay-filter='yd_radio'>
					<div class='layui-input-inline'>
						<input type='text' name='orderId' placeholder="收据号或运单编号" class='layui-input' lay-verify="number" id='waybillId' >
					</div>
				</div>					
			</div>
			<div class="layui-inline">
				<shiro:hasPermission name="bundleArrive:payMoney:search"> 
				   <button class="layui-btn btnBox searchBtn"  lay-submit lay-filter="searchBtn" id="searchBtn">查询</button>
			 	</shiro:hasPermission>
		    </div>
		</form>
		<hr>
    </div>
	<!-- 页面编辑部分 -->
	<form class='layui-form box_con' style="width:100%">
	  <div class="lookDiv">     
		 	<table width="100%">
		 	   <tr>
		 	   <td width="25%"></td>
		 	     <td width="50%" align="center">
		 	        <p class="company bold">远成集团</p>
		 	        <p class="title bold">收据</p>
		 	        <p class="date">{{tabinfo.jksj}}</p>
		 	     </td>
		 	     <td width="25%">
		 	         <p>&nbsp;</p>
		 	         <p><label>NO.</label><span class="companyId">{{tabinfo.id}}</span></p>
		 	     </td>
		 	   </tr>
		 	</table>
		 	<div><label class="padL5">交款单位：</label>{{tabinfo.jkdw}}</div>
		 	<table class='layui-table inpTab' id="getInf">
				<thead>
					<tr>
					    <th style="width:8%;" class="tdCheck">收费项目</th>
						<th style="width:7%;">应收金额</th>
						<th style="width:12%;">备注</th>
						<th style="width:6%;">现金</th>
						<th style="width:6%;">银行</th>
						<th style="width:6%;">转账</th>
						<th style="width:6%;">实收金额</th>
						<th style="width:8%;">交款责任人</th>
					</tr>
				</thead>
				<tbody>
				  <tr v-for="item in caseinfo" class="trList">
				    <td>{{item.shfxm}}</td>
				    <td align="center" class="getMoney">{{item.shfje}}</td>
				    <td align="center">{{item.beizhu}}</td>
				    <td>
				       <input type="text" class="money cash" lay-verify="money"  v-if="flag"  value="0" >
				       <input type="text"  class="money cash" v-if="!flag" v-model="item.xjsr"  readonly>
				    </td>
				    <td>
				        <input type="text" class="money bank" lay-verify="money"   v-if="flag"  value="0">
				        <input type="text"   class="money bank"  v-if="!flag" v-model="item.yhsr" readonly>			    
				    </td>
				    <td align="right">
				          <input type="text"  class="money transfer" lay-verify="money"  v-if="flag" value="0">
				          <input type="text"  class="money transfer" v-if="!flag" v-model="item.zzsr" readonly>
				     </td>
				    <td align="right" class="amount">{{item.shsje}}</td>
				    <td>
				       <span style="display:none">{{item.xzh}}</span>
				       <input type="text" v-if="flag" value="">
				       <input type="text"  v-if="!flag"  v-model="item.sqbz" readonly>
				    </td>
				  </tr> 
				  <tr class="totals">
				    <td align="center" class="bold">合计：</td>
				    <td colspan="2" align="right"  class="blue" id="totals" ></td>
				    <td colspan="4" align="right"  class="red" id="re_totals"></td>
				    <td rowspan="3"></td>
				  </tr>
				  <tr>
				    <td align="center" class="bold">合计大写</td>
				    <td colspan="2" align="right" class="chineseMoney blue"></td>
				    <td colspan="4" align="right" class="re_chineseMoney red"></td>
				  </tr>
				  <tr>
				    <td align="center" class="bold">备注</td>
				    <td colspan="6"> {{tabinfo.beiZhu}}
				         <!-- <input type="text" value="" id="otherInf"   v-if="flag">
				         <input type="text" value=""   v-if="!flag"  v-model="tabinfo.beiZhu"> -->
				     </td>
				  </tr>
				</tbody>
			</table>
			<div class="looktabFooter">
				<span>审核</span><span>会计</span><span>出纳 {{tabinfo.chuNa}}</span><span>制单 {{tabinfo.zhiPiao}}</span>
			 </div>
		    <div class="layui-form-item marT20" style="text-align:center">
		    	<shiro:hasPermission name="bundleArrive:payMoney:search"> 
				    <button class="layui-btn"   lay-submit="" v-if="flag" lay-filter="save">交钱</button>
		        	<button class="layui-btn layui-btn-disabled"   lay-submit="" lay-filter="save"  v-if="!flag" >交钱</button>
			 	</shiro:hasPermission>
		        <a href="${base_url}/transport/financialReceipts/collectMoney?ydbhid=${ydbhid}" class="layui-btn">返回</a>
	        </div>
		</div>
		<input type="hidden" v-model="tabinfo.fiwtYdbhid" id="fiwtYdbhid"> 
		 
	 </form>
		<input type="hidden" val="" id="ydbhid">
		<input type="hidden" val="" id="searchType">
		
</div>	
    <%@ include file="/views/transport/include/floor.jsp"%>   
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script>
    <script src="${ctx_static}/publicFile/js/vue.js"></script> 
    <script src="${ctx_static}/transport/receipts/js/payReceipt.js?v=203"></script>     
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    	}) 
    </script>
</body>
</html>