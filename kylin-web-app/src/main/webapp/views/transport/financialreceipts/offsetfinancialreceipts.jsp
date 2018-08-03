<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>分理收据冲红</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/offsetfinancialreceipts/css/offsetfinancialreceipts.css?v=1"/>
</head>
<body style="overflow-x: hidden;">
<div id="rrapp" v-cloak>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>异常操作维护</a>
		<a href='javaScript:void(0);'>分理收据冲红</a>
	</span>
	<hr>
		<form class='layui-form searchForm' >
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
				<shiro:hasPermission name="undo:financeReceipt:search:noright"> 
					<input name="permissLevel" id="permissLevel" type="hidden" value="1"/>
				   <button class="layui-btn btnBox searchBtn"  lay-submit lay-filter="searchBtn" id="searchBtn">查询</button>		
			 	</shiro:hasPermission>
			 	<shiro:hasPermission name="undo:financeReceipt:search:common"> 
			 		<input name="permissLevel" id="permissLevel" type="hidden" value="2"/>
				   <button class="layui-btn btnBox searchBtn"  lay-submit lay-filter="searchBtn" id="searchBtn">查询</button>		
			 	</shiro:hasPermission>
			 	<shiro:hasPermission name="undo:financeReceipt:search:manager"> 
			 		<input name="permissLevel" id="permissLevel" type="hidden" value="3"/>
				   <button class="layui-btn btnBox searchBtn"  lay-submit lay-filter="searchBtn" id="searchBtn">查询</button>		
			 	</shiro:hasPermission>
		    </div>
		</form>
	<hr>
	
	<fieldset class="layui-elem-field layui-field-title">
		<legend>查询信息</legend>
		<div>
		   <ul class="comUl">
		      <li><label>交款单位：</label>{{tabinfo.jkdw}}</li>
		      <li><label>交款时间：</label>{{tabinfo.jksj}}</li>
		      <li><label>公司：</label>{{tabinfo.gs}}</li>
		      <li><label>收据号：</label><span id="sjid">{{tabinfo.id}}</span></li>
		      <li><label>备注：</label>{{tabinfo.beiZhu}}</li>
		      <li><label>出纳：</label>{{tabinfo.chuNa}}</li>
		      <li><label>制单：</label>{{tabinfo.zhiPiao}}</li>
		   </ul>
		  
		</div>
	</fieldset>
	<hr>
	<div class='layui-form' style="">
		<table class='layui-table table-div'>
			<thead>
				<tr>
					<th>收费项目</th>
					<th>应收金额</th>
					<th>运输号码</th>
					<th>备注</th>
					<th>现金</th>
					<th>银行</th>
					<th>转账</th>
					<th>实收金额</th>
					<th>收款备注</th>
				</tr>
			</thead>
			<tbody>
			    <tr v-for="item in caseinfo" class="trList">
			       <td align="left">{{item.shfxm}}</td>
			       <td class="getMoney">{{item.shfje}}</td>
			       <td>{{item.yshm}}</td>
			       <td>{{item.beizhu}}</td>
			       <td class="money cash">{{item.xjsr}}</td>
			       <td class="money bank">{{item.yhsr}}</td>
			       <td class="money transfer">{{item.zzsr}}</td>
			       <td class="amount">{{item.shsje}}</td>
			       <td>{{item.sqbz}}</td>
			    </tr>
			    <tr class="totals" align="center">
			    	<td class="bold">合计</td>
			    	<td colspan="3" id="totals" style="text-align:right;"></td>
			    	<td colspan="4" id="re_totals" style="text-align:right;"></td>
			    	<td></td>
			    </tr>
			    <tr>
			    <td class="bold" align="center">合计大写</td>
				    <td colspan="3" class="chineseMoney blue" style="text-align:right;"></td>
				    <td colspan="4" class="re_chineseMoney red" style="text-align:right;"></td>
				    <td></td>
			    </tr>
			</tbody>
		</table>
	</div>
	
	<form class='layui-form ' style="padding:0 2%">
			<div class="layui-form-item " style="width:80%; margin:0 auto">
		        <label><em class="red">*</em>冲红原因：</label>	
		    	<div class="marT10 textarea-div">
		      		<textarea id='tips' data-value='comm' placeholder="暂无数据" lay-verify="required" class="clear-data layui-textarea" ></textarea>
		    	</div>
		    </div>
		   <div class='marT20 btn-div' style='text-align: center;'>
		       <button class="layui-btn layui-btn-disabled" type='button' v-show='!istoRed'>冲红</button>
		       <button class="layui-btn" v-show='istoRed' lay-submit lay-filter="save">冲红</button>
		       <button class="layui-btn toClear"  id='toClear' type="reset" style='margin-left: 100px;'>清空数据</button>
	       </div>
	</form>
	<input type="hidden" val="" id="searchType">
</div>	
    <%@ include file="/views/transport/include/floor.jsp"%>
    
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    	})
    </script>
    <script src="${ctx_static}/transport/offsetfinancialreceipts/js/offsetfinancialreceipts.js?v=003"></script>   
    
</body>
</html>