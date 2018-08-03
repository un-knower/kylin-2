<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title></title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/receipts/css/receipt.css?v=11"/>
</head>
<body>
<div id="rrapp" v-cloak>
     <div class="lookDiv" style="padding-top:2%">
		<form class='layui-form searchForm'>
		   <div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号：</label>
					<div class='layui-input-inline'>
						<input type='text' name='orderId' readonly value="" class='layui-input' id='waybillId'  >
					</div>
				</div>					
			</div>
		</form>
		<hr>
    </div>
	<!-- 页面编辑部分 -->
	<form class='layui-form box_con'>
	  <div class="lookDiv">     
		 	<table width="100%">
		 	   <tr>
		 	     <td width="25%"></td>
		 	     <td width="50%" align="center">
		 	        <p class="company">远成集团</p>
		 	        <p class="title">收据</p>
		 	        <p class="dateTime">{{tabinfo.jksj}}</p>
		 	     </td>
		 	     <td width="25%">
		 	         <p><label>公司：</label>{{tabinfo.gs}}</p>
		 	         <p><label>NO.</label><span class="companyId">{{tabinfo.id}}</span></p>
		 	     </td>
		 	   </tr>
		 	</table>
		 	<div><label class="padL5">交款人:</label><span class="jkdw">{{tabinfo.jkdw}}</span></div>
		 	<table class='inpTab' id="getInf">
				<thead>
					<tr>
					    <th style="width:10%;" class="tdCheck">收费项目</th>
						<th style="width:6%;">应收金额</th>
						<th style="width:5%;">类型</th>
						<th style="width:10%;">运输号码</th>
						<th style="width:10%;">备注</th>
						<th style="width:6%;">实收金额</th>
						<th style="width:10%;">收款备注</th>
						<th style="width:8%;">客户编码</th>
						<th style="width:8%;">客户名称</th>
					</tr>
				</thead>
				<tbody>
				  <tr v-for="item in caseinfo" class="oldList trList">
				    <td><input type="text" class="courseId" value="" v-model="item.shfxm"  readonly></td>
				    <td align="right" class="getMoney"><input type="text" value="" v-model="item.shfje"  readonly></td>
				    <td align="center" class="layui-form-select pos_rel"> 
						<input type="text" name="selectBox"  v-model="item.shftype"  lay-verify="required" autocomplete="off"  class="layui-input s_selectInput"  value=""/>
			            <i class="layui-edge tips_type"></i>
			            <ul class="s_selectUl">
			                <li value="现金">现金</li>
						    <li value="银行">银行</li>
						    <li value="转账">转账</li>
			            </ul>
						 
				    </td>
				    <td>{{item.yshm}}</td>
				    <td><input type="text" value="测试"  readonly  v-model="item.beizhu" ></td>
				    <td align="right"  class="payMoney">
				        <span v-if="flag">0</span>
				        <span v-if="!flag">{{item.shsje}}</span> 
				    </td>
				    <td>{{item.sqbz}}</td>
				    <td class="layui-form-select">{{item.khbm}}</td>
				    <td class="userName">{{item.khmc}}</td>
				  </tr>
 
				  <tr class="totals">
				    <td align="center" class="bold">合计：</td>
				    <td colspan="6" align="right"  id="totals"></td>
				    <td colspan="2" align="right"  id="re_totals"></td>
				  </tr>
				  <tr>
				    <td align="center" class="bold">合计大写</td>
				    <td colspan="6" align="right" class="chineseMoney"></td>
				    <td colspan="2" align="right" class="re_chineseMoney"></td>
				  </tr>
				  <tr>
				    <td align="center" class="bold">备注</td>
				    <td colspan="8">
				       <input type="text" v-if="!flag" v-model="tabinfo.beiZhu" readonly class="beizhu">
				       <input type="text" v-if="flag"   class="beizhu" >
				    </td>
				  </tr>
				</tbody>
			</table>
			<div class="looktabFooter">
				<span>审核</span><span>会计</span><span>出纳 {{tabinfo.chuNa}}</span><span>制单 {{tabinfo.zhiPiao}}</span>
			 </div>
		    <div class="layui-form-item marT20 btnBox" style="text-align:center">
		       
		       
		       <a class="layui-btn"   lay-filter="" id="" v-if="!flag"  @click="collect">收钱</a>
		       <a class="layui-btn layui-btn-disabled"  v-if="flag" >收钱</a>
		        
		       <a class="layui-btn"   lay-filter=""   v-if="flag"   @click="show && add()">增加</a>
		       <a class="layui-btn layui-btn-disabled"  v-if="!flag" >增加</a>
		       
		       <a class="layui-btn"   lay-filter=""  v-if="flag"  @click="show && deleteHandler()" >删除</a>
		       <a class="layui-btn layui-btn-disabled"   v-if="!flag"  >删除</a>
		       
		       <shiro:hasPermission name="bundleArrive:payMoney:save"> 
					<button class="layui-btn" lay-submit=""  v-if="flag"  lay-filter="save" id="saveBtn">保存</button>
		       		<button class="layui-btn layui-btn-disabled"  v-if="!flag" >保存</button>
				</shiro:hasPermission>
		        
		        <a class="layui-btn"    v-if="!flag" @click="print">打印</a>
		        <a class="layui-btn layui-btn-disabled"    v-if="flag" >打印</a>			
		        
		        
		        <shiro:hasPermission name="bundleArrive:pickdelivery:create"> 
					<a class="layui-btn" onclick="tihuodan()">生成提货单</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="bundleArrive:senddelivery:create"> 
					<a class="layui-btn" onclick="songhuodan()">生成送货单</a>
				</shiro:hasPermission>
	        </div>
	        
		</div>
		<input type="hidden" v-model="tabinfo.fiwtCwpzhbh" id="fiwtCwpzhbh"> 
		<input type="hidden" v-model="tabinfo.fiwtYdbhid" id="fiwtYdbhid"> 
		<input type="hidden" v-model="tabinfo.type" id="s_type">
		<input type="hidden" v-model="tabinfo.ver" id="ver">
	 </form>
	 
	<!-- 添加部分 -->
	<form class="layui-form" >
		<table style="display:none" class="addTabBox" >
			<tr class="tableList" style="display:none">
		    <td align="center"  class="layui-form-select pos_rel">
		  <!--      <input type="hidden" class="courseId" value="货到付款" readonly> -->
		         <input type="text" name="feeType" lay-verify="required" autocomplete="off"  class="layui-input s_selectInput courseId"  value=""/>
		           <i class="layui-edge tips_type"></i>
		           <ul class="s_selectUl">
		                <li value="货到付款">货到付款</li>
					    <li value="仓储费">仓储费</li>
					    <li value="办单费">办单费</li>
					    <li value="罚款">罚款</li>
					    <li value="私话费">私话费</li>
					    <li value="送货费">送货费</li>
		            </ul>  
		    </td>
		    <td align="center">
		        <input type="text" value="0" class="money"  lay-verify="money">
		    </td>
		    <td align="center" class="layui-form-select">
			    <input type="text" name="selectBox"  lay-verify="required" autocomplete="off"  class="layui-input s_selectInput"  value=""/>
	            <i class="layui-edge tips_type"></i>
	            <ul class="s_selectUl">
	                <li value="现金">现金</li>
				    <li value="银行">银行</li>
				    <li value="转账">转账</li>
	            </ul>  
			</td>
		    <td class="driverCode"></td>
		    <td align="center"><input type="text" /></td>
		    <td align="right"></td>
		    <td></td>
		    <td class="userCode">
		        <!--  <input type="text" name="selectBox"   autocomplete="off"  class="layui-input selectInput"  value=""/>
	             <i class="layui-edge tips_type"></i>
	             <ul class="selectUl"></ul> -->
			</td>
		    <td class="userName userCompany"></td>
		  </tr>
		</table> 
	</form>
	

	<!-- 打印部分 -->
	<div class='printDiv'>
	    <table width="100%">
	 	   <tr>
	 	     <td width="20%" style="vertical-align: top;">
	 	        <img src="/kylin/static/common/images/logo_s.png"  style="width:150px">   
	 	     </td>
	 	     <td width="45%" align="center">
	 	        <p class="title">收据</p>
	 	        <p class="date marT10">{{printMaster.jksj}}</p>
	 	     </td>
	 	     <td width="13%">
	 	         <p class="padT20"><label>公司：</label>{{printMaster.gs}}</p>
	 	         <p class=""><label>NO.</label>{{printMaster.id}}</p>
	 	     </td>
	 	     <td width="22%"></td>
	 	   </tr>
	 	   <tr>
	 	     <td colspan="3" class="pad10"><p>交款单位：{{printMaster.jkdw}}</p> </td>
	 	     <td  class="pad10"><p>到站网点:</p></td>
	 	   </tr>

	 	</table>		
		<table class='printDiv printTab'>
			<thead>
			<tr>
			    <th colspan="2" style="width:20%;" class="tdCheck">收费项目</th>
				<th style="width:12%;">金额</th>
				<th style="width:8%;">类型</th>
				<th style="width:20%;">运输号码</th>
				<th style="width:">备注</th>
				</tr>				
			</thead>
			<tbody >
			   <tr  v-for="inf in printList">
			      <td  colspan="2">{{inf.shfxm}}</td>
			      <td align="right" class="printMoney">{{inf.shfje}}</td>
			      <td align="center">{{inf.shftype}}</td>
			      <td align="center">{{inf.yshm}}</td>
			      <td>{{inf.beizhu}}</td>
			   </tr>
			   <tr>
			      <td  colspan="2" align="center" class="bold">合计：</td>
			      <td colspan="4" align="right" class="printTotal"></td>
			   </tr>
			   <tr>
			      <td  colspan="2"  align="center" class="bold">合计大写：</td>
			      <td colspan="4"  align="right" class="printChinese"></td>
			   </tr>
			   <tr>
			      <td  align="center" class="bold">备注</td>
			      <td colspan="5">{{printMaster.beiZhu}}</td>
			   </tr>
			</tbody>
		</table>
		<div class="tabFooter">
			<span>审核</span><span>会计</span><span>出纳  {{printMaster.chuNa}}</span><span>制单  {{printMaster.zhiPiao}}</span><span>客户</span>
		 </div>
		 <div class="footerDiv marT20">
		       <p>第一联&nbsp; 财务记账&nbsp;  第二联&nbsp; 客户留存&nbsp; 第三联 &nbsp;司机/单证留存</p>
		 </div>	
       </div>
		<!-- <input type="hidden" val="" id="ydbhid"> -->
		
</div>	
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    
	    layui.use(['element','layer','form'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var layer = layui.layer;
    	}); 
	   
    </script>
    <script src="${ctx_static}/transport/receipts/js/receipt.js?v=12"></script> 
</body>
</html>