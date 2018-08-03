<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>取货(派车)签收单</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
</head>
<body>
<div id='arrivellodiing'>
   <form class='layui-form print-page'>
			<div class='list-top'>
				<h2 class='head-middle head-tag title send-title'>取货（派车）签收单结算</h2>
			</div>
			<div style='padding: 0 20px;'>
				<table class='list-table send-table'>
					<thead></thead>
					<tbody>
						<tr>
							<td style="width:10%">司机填写</td>
						    <td colspan="5" style="padding: 0px;">
						       <table class="newTable driverTable">
						          	<tr>
									    <td >托运人</td>
									    <td colspan="3" style="width:35%">{{caseinfo.fhr}}</td>
									    <td style="width:10%">联系人</td>
									    <td colspan="3">{{caseinfo.lxr}}</td>
									    <td >下单时间</td>
									    <td colspan="4">{{caseinfo.xdtime}}</td>
									</tr>						           
						            <tr>
									    <td>车号</td>
									    <td>司机</td>
									    <td>出发地出发时间</td>
									    <td>路码表</td>
									    <td>到达地到达时间</td>
									    <td>路码表</td>
									    <td>途中路桥费</td>
									    <td>发生罚款</td>
									    <td>费用维修费</td>
									    <td>路码表</td>
									    <td>途中加油/L</td>
									    <td>金额</td>
									    <td><a class="layui-btn layui-btn-sm addTabList" @click="addHandler">添加</a></td>                       
								   </tr>
						           <tr v-for="item in driverList" class="newTr">
						              <td><input type="text" value="" v-model="item.ch" lay-verify="required"/></td>
						              <td><input type="text" value="" v-model="item.sj" lay-verify="required"/></td>
						              <td><input type="text" value="" class="date cftime" :data="item.cftime"/></td>
						              <td><input type="text" value="" v-model="item.cflmbs" /></td>
						              <td><input type="text" value="" class="date ddtime" :data="item.ddtime"/></td>
						              <td><input type="text" value="" v-model="item.ddlmbs" /></td>
						              <td><input type="text" value="" v-model="item.tzlqf" /></td>
						              <td><input type="text" value="" v-model="item.tzfk" /></td>
						              <td><input type="text" value="" v-model="item.tzwxf" /></td>
						              <td><input type="text" value="" v-model="item.tzlmbs" /></td>
						              <td><input type="text" value="" v-model="item.tzjyss" /></td>
						              <td><input type="text" value="" v-model="item.tzjyje" /></td>
						              <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delite(this)">删除</a></td> 
						           </tr>
						           <tr class="driverBox">
									  <td>取货记录</td>
									  <td colspan="12"><input type="text" id="sjcomm" class="layui-input" v-model="caseinfo.sjcomm"></td>
								  </tr>
						       </table>
						    </td>

						</tr>
						<tr>
						    <td >统计员结算填写</td>						    
						    <td colspan="5" style="padding: 0px;">
						       	<table class="newTable tjTable">
						       	   	<tr>
									    <td>车辆性质</td>
									    <td>车号</td>
									    <td >运费标准</td>						   
									    <td >运算结算</td>						  
									    <td>提成</td>						 
									    <td >签收单返还时间</td>
									    <td><a class="layui-btn layui-btn-sm addTabList" @click="s_addHandler">添加</a></td>						    
									</tr>
						           <tr v-for="item in tjList" class="tjTr">
						              <td>
						                  <select name="modules" lay-verify="required"  v-model="item.clxz" style="display:block;width:100%; border: none;">
									          <option  value="">请选择</option>
									          <option value="自有">自有</option>
									          <option value="租赁">租赁</option>
									          <option value="合作">合作</option>
									       </select>
						              </td>
						              <td><input type="text" value="" v-model="item.ch" /></td>
						              <td><input type="text" value="" v-model="item.yfbz" /></td>
						              <td><input type="text" value="" v-model="item.yfjs" /></td>
						              <td><input type="text" value="" v-model="item.tc" /></td>
						              <td><input type="text" class="date qsdfhtime" value="" :data="item.qsdfhtime" /></td>
						              <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delHandler(this)">删除</a></td>
						           </tr>
									<tr class="tjBox">
									    <td>统计员备注</td>
									    <td colspan="3"><input type="text" id="tjybz" class="layui-input" v-model="caseinfo.tjybz"></td>
									    <td>核算人</td>
									    <td colspan="2">${CURR_USER_NAME}</td>
									</tr>
						       </table>
						    </td>   
						</tr>
					</tbody>
				</table>
			</div>
			<div class='btn-operation list-top list-footer no-print'>
				<button v-show='flag' class='layui-btn layui-btn-warm no-print' lay-submit  lay-filter='save'>保存</button>
				<i v-show='!flag' class='layui-btn layui-btn-disabled' >保存</i> 
			</div>
		</form>
		<input type="hidden"  id="pcid" value="${pcid}">
		<input type="hidden"  id="tjyhsrgrid" value="${CURR_USER_GRID}">
		<input type="hidden"  id="tjyhsr" value="${CURR_USER_NAME}">
		<input type="hidden"  id="dateTime" />
	    <!-- 新增 -->
		<table class="">
	       <tr class="s_newTr" style="display:none">
	          <td><input type="text" value=""  lay-verify="required"/></td>
	          <td><input type="text" value="" lay-verify="required"/></td>
	          <td><input type="text" value="" class="date" /></td>
	          <td><input type="text" value=""/></td>
	          <td><input type="text" value="" class="date" /></td>
	          <td><input type="text" value=""  /></td>
	          <td><input type="text" value=""/></td>
	          <td><input type="text" value="" /></td>
	          <td><input type="text" value="" /></td>
	          <td><input type="text" value="" /></td>
	          <td><input type="text" value="" /></td>
	          <td><input type="text" value=""  /></td>
	          <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delite(this)">删除</a></td> 
	       </tr>
		  <tr class="s_newTr"  style="display:none">
			    <td >
	                <select name="modules" lay-verify="required"  style="display:block;width:100%;height: 20px; border: none;">
			          <option  value="">请选择</option>
			          <option value="自有">自有</option>
			          <option value="租赁">租赁</option>
			          <option value="合作">合作</option>
		           </select>
                </td>
	            <td ><input type="text" value=""/></td>
	            <td><input type="text" value=""/></td>
	            <td ><input type="text" value="" /></td>
	            <td ><input type="text" value=""/></td>
	            <td><input type="text" class="date" value=""/></td>
	            <td><a class="layui-btn layui-btn-warm layui-btn-sm" onclick="vm.delHandler(this)">删除</a></td>
		   </tr>
		  </table>
		
   </div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    layui.use(['element','form'], function(){
    		var element = layui.element;
    		var form = layui.form;
    	})
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/receiptSignSettle.js"></script>
</body>
</html>