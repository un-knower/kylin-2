<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>物流运输单号跟踪录入</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignSettle.css?v=2"/>
</head>
<body style="overflow-x: hidden;">
<style type="text/css">
   [v-cloak] {display: none;}
</style>
<div id="rrapp" v-cloak>
    <span class='layui-breadcrumb breadcrumb no-print'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>物流运输单号跟踪录入</a>
	</span>
    <form class='layui-form '>
		<div class='layui-form-item'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号：</label>
					<div class='layui-input-block'>
						<input lay-verify="required" type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
			<div class="layui-inline">
		       <button class="layui-btn toCheck" lay-submit lay-filter='searchBtn' id='searchBtn'>查询</button>
		    </div>   
		</div>
	</form>
    <hr/>
    <!-- part 1 -->
    <fieldset class="layui-elem-field layui-field-title">
		<legend>送货信息</legend>
			<table class='layui-table' style="width:98%;margin:10px auto 0 auto;">
			   <tr>
					<td style="width:10%;">送货单位</td>
					<td style="width:10%;">{{caseinfo.shdw}}</td>
					<td style="width:10%;">联系人</td>
					<td style="width:10%;">{{caseinfo.shlxr}}</td>
					<td style="width:10%;">电话</td>
					<td style="width:10%;">{{caseinfo.shdh}}</td>
					<td style="width:10%;">送货地址</td>
					<td style="text-align:left" colspan="3" >{{caseinfo.shdz}}</td>	
				</tr>
				<tr>
				  <td style="width:10%;">仓库提货时间</td>
				  <td style="width:10%;"><input class="layui-input date" id="thtime" placeholder="" type="text" :data="caseinfo.thTime" ></td>
		          <td >客户签收</td>
		          <td><input name="" id="khqs"  v-model="caseinfo.khqs" class="layui-input" type="text"></td>
			      <td>装卸班组</td>
			      <td><input name="" id="zxbz" v-model="caseinfo.zxbz" class="layui-input" type="text"></td>
			      <td>客户签收时间</td>
			      <td><input name="" id="khqstime"  :data="caseinfo.khqsTime"  class="layui-input date" type="text"></td>
			      <td>仓库员签名</td>
			      <td><input name="" id="cgyqm"  v-model="caseinfo.cgyqm" class="layui-input" type="text"></td>
				</tr>
				<tr class="">
				  <td style="width:15%;">运单号码</td>
				  <td colspan="9">
				     <ul><li class="orderLi" v-for="obj in orderList">{{obj.ydhm}}</li></ul>
				  </td>
				</tr>
				<tr>
				   <td>货物名称</td>
				   <td colspan="9">
				     <ul><li class="orderLi" v-for="s_obj in orderList">{{s_obj.hwmc}}</li></ul>
				  </td>
				</tr>
			</table>		
	  </fieldset>
	  <!-- part 2 -->
      <form class='layui-form'>
	     <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
		    <legend>司机填写信息</legend>
		 </fieldset>	
	 	 <table class='layui-table' style="width:98%;margin:0 auto">
			<thead>
				<tr>
					<th style="width:10%;"  rowspan='2'>车号</th>
					<th style="width:10%;"  rowspan='2'>司机</th>
					<th style="width:20%;"  colspan='2'>仓库出发</th>
					<th style="width:20%;"  colspan='2'>到达客户处</th>
					<th style="width:15%;"  colspan='3'>途中发生费用</th>
					<th style="width:15%;"  colspan='3'>途中加油</th>
					<th style="">操作</th>
				</tr>
				<tr class="trBox">
				   <th>时间</th>
				   <th>路码表</th>
				   <th>时间</th>
				   <th>路码表</th>
				   <th>路桥费</th>
				   <th>罚款</th>
				   <th>维修费</th>
				   <th>路码表</th>
				   <th>升</th>
				   <th>金额</th>
				   <th><a class="layui-btn layui-btn-sm addTabList"  @click="addHandler">新增一行</a>  
				   </th>
				</tr>
			</thead>
			<tbody class="addTbody">
			   <tr class="addTr"  v-for="item in tabinfo">
			      <td><input type="text" value="" v-model="item.ch" lay-verify="required"/></td>
			      <td><input type="text" value="" v-model="item.sj" lay-verify="required"/></td>
			      <td><input type="text" class="date cftime" :data="item.cftime" lay-verify="required" value=""/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.cflmb"/></td>
			      <td><input type="text" class="date ddtime" :data="item.ddtime" lay-verify="required" value="" /></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.ddlmb"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.tzlqf"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.tzfk"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.tzwxf"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.jylmb"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.jyss"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="item.jyje"/></td>
			      <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delite(this)">删除</a></td>
			   </tr>
			   <tr class="updateTr"><td>特殊说明：</td><td colspan="12" ><input type="text" style="text-align:left" id="sjComm" v-model="caseinfo.sjComm"/></td></tr>			   
			</tbody>
		</table>
		<!-- part 3 -->
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
		    <legend>统计员结算信息</legend>
		</fieldset>	
	 	<table class='layui-table tabBox' style="width:98%;margin:0 auto 20px auto">
			<thead>
				<tr>
					<th style="width:8%;">车辆性质</th>
					<th style="width:10%;">车号</th>
					<th style="width:10%;">运费标准</th>
					<th style="width:10%;">运费结算</th>
					<th style="width:10%;">提成</th>
					<th style="width:15%;">签单返回时间</th>
					<th style="width:15%;">
				      <a class="layui-btn layui-btn-sm addTabList" @click="addAccountHandler">新增一行</a>
				    </th>
					<th style="width:10%;">核算人</th>
				</tr>
			</thead>
			<tbody>
			   <tr v-for="tem in tjinfo" class="s_addTr">
			      <td>
 			       <select name="modules" lay-verify="required"  v-model="tem.clxz" style="display:block;width:100%; border: none;">
			          <option  value="">请选择</option>
			          <option value="自有">自有</option>
			          <option value="租赁">租赁</option>
			          <option value="合作">合作</option>
			       </select>
                  </td>
			      <td><input type="text" value="" v-model="tem.ch" lay-verify="required"/></td>
			      <td><input type="text" value="" v-model="tem.yfbz"/></td>
			      <td><input type="text" value="" lay-verify="number"v-model="tem.yfjs"/></td>
			      <td><input type="text" value="" lay-verify="number" v-model="tem.tc"/></td>
			      <td><input type="text" class="date qdfhtime" :data="tem.qdfhtime" lay-verify="required" value="" /></td>
			      <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delHandler(this)">删除</a></td>
			      <td class="rowsHsr" rowspan=""><input type="text" value="" id="tjhsr" v-model="caseinfo.tjhsr" style="text-align:center"/></td>
			   </tr>
			   <tr class="s_updateTr" ><td>备注：</td><td style="text-align:left" colspan="7"><input type="text" style="text-align: left;" id="tjComm" v-model="caseinfo.tjComm"/></td></tr>			   
			</tbody>
		</table>  
		<input type="hidden" val="" id="ydbhid">
		<div class="layui-form-item" style="text-align:center">
		     <button  class='layui-btn'  lay-submit  lay-filter='save' >保存</button>
	    </div>
	</form>
	<input type="hidden" id="orderId" value="">
	<!-- 新增 -->
	<table class="">
	 <tr class="newTr"  style="display:none">
	      <td><input type="text" value="" lay-verify="required"/></td>
	      <td><input type="text" value="" lay-verify="required"/></td>
	      <td><input type="text" class="date" lay-verify="required" value="" /></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" class="date" lay-verify="required" value="" /></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><input type="text" value="" lay-verify="number"/></td>
	      <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delite(this)">删除</a></td>
	  </tr>
	  <tr class="newTr"  style="display:none">
		   <td>
		       <select name="modules" lay-verify="required" style="display:block">
		          <option value="">请选择</option>
		          <option value="自有">自有</option>
		          <option value="租赁">租赁</option>
		          <option value="合作">合作</option>
		       </select>
		   </td>
		   <td><input type="text" value="" lay-verify="required"/></td>
		   <td><input type="text" value="" lay-verify="required"/></td>
		   <td><input type="text" value="" lay-verify="number"/></td>
		   <td><input type="text" value="" lay-verify="number"/></td>
		   <td><input type="text"  class="date" lay-verify="required" value=""/></td>
		   <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delHandler(this)">删除</a></td>
		   <td></td>
	   </tr>
	  </table>		
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    $(".toReturn").on("click",function(){
			window.location.href = base_url + "/transport/convey/manage";
		});
	    layui.use(['element','laydate','form'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;
    		lay('.date').each(function(){
    			laydate.render({
    				elem: this,
    				theme: '#009688',
    				type: 'datetime',
    				trigger: 'click',
    				format: 'yyyy-MM-dd HH:mm' //可任意组合
    			});
    		});	
 	  
    	})
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <script type="text/javascript" src="${ctx_static}/transport/arrivalsendSignCheck/js/sendSignSettle.js?v=03"></script>
</body>
</html>