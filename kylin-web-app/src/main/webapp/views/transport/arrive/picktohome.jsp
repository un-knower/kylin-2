<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>
    	<c:if test="${empty pcid}">
					录入上门取货指令
				</c:if>
				<c:if test="${not empty pcid}">
					取货调度派车处理
				</c:if>
    </title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css"/>
</head>
<body>
<style>
.layui-form-select dl{top: 32px}
.layui-form select {
     display: block;
} 
tr.newTr td{border-left:none;border-top:none;}
.newTr td:first-child{}
</style>
<div id='newpicktohome'>
	<form id="picktohomeForm" method="post" class="layui-form"> 
   <div class='print-page'>
			<div class='list-top'>
				<h2 class='head-middle head-tag title send-title'>
				<c:if test="${empty pcid}">
					录入上门取货指令
				</c:if>
				<c:if test="${not empty pcid}">
					取货调度派车处理
				<!-- <input type="button" value="按钮" onclick="getEditInfo('${pcid}')"/> --> 
				</c:if>
				</h2>
			</div>
			<!-- 
			<div class='tihuoNum'>
				<p class='head-tag list-info' style='margin-left: 28%;'>公司：company</p>
				<p class='head-tag list-info'>NO.</p>
			</div> -->
			<div style='padding: 0 20px;'>
				<table class='list-table send-table'>
					<thead></thead>
					<tbody>
						<!--
						<tr>
    						<td colspan="11">录入上门取货指令</td>
						</tr>
						  -->
						<tr>
							<!--
						    <td>订单号：</td>
						    <td colspan="3"></td>
						 -->
						    <td>公司：</td>
						    <td colspan="4">	
						    	<input name="gs" readonly="readonly" class="txt layui-input orderEntity" v-model="orderEntity.gs"/>
						    </td>
						    <td>NO：</td>
						    <td colspan="5">
						    	<input name="id" readonly="readonly" class="txt layui-input orderEntity id" v-model="orderEntity.id"/>
						    </td>
						</tr>
						<tr>
						    <td rowspan="17">销售员下指令填写</td>
						    <td>销售员</td>
						    <td colspan="2">
						    	<input name="kfy" readonly="readonly" class="txt layui-input orderEntity" v-model="orderEntity.kfy"/>
						    	<input type="hidden" name="kfgrid" class="txt layui-input orderEntity" v-model="orderEntity.kfgrid"/>
						    </td>
						    <td rowspan="2">预计取货时间</td>
						    <td colspan="3" >
						    	<!-- :data="orderEntity.jhqhtime"  -->
						    	<input name="jhqhtime" id="date1" type='text' class="txt layui-input orderEntity" />
						    </td>						  
						    <td>派送指示</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select class="txt layui-input orderEntity" lay-ignore name="pszhsh" v-model="orderEntity.pszhsh">
   									 <option v-for="obj in pszhsh_options" v-bind:value="obj.value">{{obj.text}}</option>
								</select>
						    </td>
						</tr>
						<tr>
						    <td>客户编码</td>
						    <td  colspan="2">
						    	<input name="khbm" class="txt layui-input orderEntity" v-model="orderEntity.khbm"/>
						    </td>
						    <td>发票</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select name="isXuYaoFaPiao" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.isXuYaoFaPiao">
						    		 <option v-for="obj in isfd_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>	
						    <td> 
						    <input value=""/>
						    	税号
						    </td>
						    <td colspan="2">
						    	<input name="tuoyunrenshuihao" class="txt layui-input orderEntity" v-model="orderEntity.tuoyunrenshuihao"/>
						    </td>
						</tr>

						<tr>
						    <td>发运网点</td>
						    <td colspan="2">
						    	<input name="yywd" class="txt layui-input orderEntity" v-model="orderEntity.yywd"/>
						    </td>
						   
						    <td>发运路径</td>
						    <td colspan="3">
						    	<input name="fasonglujingBianhao" class="txt layui-input orderEntity" v-model="orderEntity.fasonglujingBianhao"/>
						    </td>
						    
						    <td>目的地</td>
						    <td colspan="2" style="overflow: inherit;position: relative">
						    	  <input name="hwdaozhan" onblur="changeDaozhan()"  class="txt layui-input orderEntity hwdaozhan selectInput" id="hwdaozhan" v-model="orderEntity.hwdaozhan">
					    	      <ul class="selectUl" style="max-width: none;">
					    	         <li  v-for="obj in destination_options" v-bind:value="obj.text">{{obj.text}}</li>
					    	      </ul>
						    </td>
						</tr>
						<tr>
						    <td>运输方式</td>
						    <td colspan="2" style="overflow: inherit;position: relative">
					    	       <input name="ysfs" id="transType" class="txt layui-input orderEntity selectInput"  v-model="orderEntity.ysfs" >
					    	       <ul class="selectUl" style="max-width: none;">
					    	          <li  v-for="obj in ysfs_options" v-bind:value="obj.text">{{obj.text}}</li>
					    	       </ul>
						    </td>
						    <td>业务类型</td>
						    <td colspan="3" style="overflow: inherit;position: relative">
						         <select name="ywlx"lay-ignore class="txt layui-input orderEntity ywlx">
   									<option v-for="obj in ywlx_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						    <td>到站网点</td>
						    <td colspan="2" style="overflow: inherit;position: relative">
						       <input name="hwdaozhanWangDian" id="hwdaozhanWangDian" v-model="orderEntity.hwdaozhanWangDian" class="txt layui-input orderEntity selectInput">
						        <ul  class="selectUl" style="max-width: none;">
						          <li v-for="obj in destination_point_options" v-bind:value="obj.value">{{obj.text}}</li>
						        </ul>
						    </td>
						</tr>
						<tr>
						    <td rowspan="8">托运人</td>
						    <td>名称</td>
						    <td colspan="3">
						    	<input name="fhr" class="txt layui-input orderEntity" v-model="orderEntity.fhr">
						    </td>
						    <td rowspan="8">收货人</td>
						    <td>名称</td>
						    <td colspan="3">
						    	<input name="shhrmch" class="txt layui-input orderEntity" v-model="orderEntity.shhrmch">
						    </td>
						  
						</tr>
						<tr>
						    <td>省市</td>
						    <td>
						    	<input name="fhShengFen" class="txt layui-input orderEntity" v-model="orderEntity.fhShengFen">
						    </td>
						    <td>市县区</td>
						    <td>
						    	<input name="fhChengShi" class="txt layui-input orderEntity" v-model="orderEntity.fhChengShi">
						    </td>
						    <td>省市</td>
						    <td>
						    	<input name="dhShengfen" value="黑龙江省" class="txt layui-input orderEntity" v-model="orderEntity.dhShengfen">
						    </td>
						    <td>市县区</td>
						    <td>
						    	<input name="dhChengsi" value="哈尔滨市" class="txt layui-input orderEntity" v-model="orderEntity.dhChengsi">
						    </td>
						</tr>
						<tr>
						    <td>取货地址</td>
						    <td colspan="3">
						    	<input name="qhadd" class="txt layui-input orderEntity" v-model="orderEntity.qhadd">
						    </td>
						    <td>街道城镇</td>
						    <td colspan="3">
						    	<input name="dhAddr" class="txt layui-input orderEntity" v-model="orderEntity.dhAddr">
						    </td>
						</tr>
						<tr>
						    <td>取货联系人</td>
						    <td colspan="3">&nbsp;
						    	<input name="lxr" class="txt layui-input orderEntity" v-model="orderEntity.lxr">
						    </td>
						   
						    <td rowspan="3">地址</td>
						    <td colspan="3" rowspan="3">
						    	<input name="shhrdzh" class="txt layui-input orderEntity" v-model="orderEntity.shhrdzh">
						    </td>
						</tr>
						<tr>
						    <td>取货电话</td>
						    <td colspan="3">
						    	<input name="lxdh" class="txt layui-input orderEntity" v-model="orderEntity.lxdh">
						    </td>
						</tr>
						<tr>
						    <td>托运地址</td>
						    <td colspan="3">
						    	<input name="fhdwdzh" class="txt layui-input orderEntity" v-model="orderEntity.fhdwdzh">
						    </td>
						</tr>
						<tr>
						    <td>托运座机电话</td>
						    <td colspan="3">
						    	<input name="fhdwlxdh" v-model='orderEntity.fhdwlxdh' class="txt layui-input orderEntity" v-model="orderEntity.fhdwlxdh">
						    </td>
						  
						    <td>座机电话</td>
						    <td colspan="3">
						    	<input name="shhrlxdh" class="txt layui-input orderEntity" v-model="orderEntity.shhrlxdh">
						    </td>
						</tr>
						<tr>
						    <td>托运手机号码</td>
						    <td colspan="3">
						    	<input name="fhdwlxdhShouji" class="txt layui-input orderEntity" v-model="orderEntity.fhdwlxdhShouji">
						    </td>
						    <td>手机号码</td>
						    <td colspan="3">
						    	<input name="shhryb" class="txt layui-input orderEntity" v-model="orderEntity.shhryb">
						    </td>
						</tr>
						<tr>
						    <td colspan="2">货物名称
						    <button class="layui-btn addgoods" @click='_addHandler' type='button' style="height: 20px; line-height: 20px;padding: 0px 5px;margin-left: 5px;font-size: 12px;">添加</button></td>
						    <td>包装</td>
						    <td>件数</td>
						    <td>重量</td>
						    <td>体积</td>
						    <td colspan="2">申明价格</td>
						    <td colspan="2">保价金额</td>
						</tr>
						
					  	<tr v-for='obj in goodsLists'>
						    <td colspan="2">
						    	<input name="hwmc" v-model='obj.hwmc' placeholder="货物名称" class="txt layui-input goodsList">
						    	<input type="hidden" name="detailId" v-model='obj.detailId' class="goodsList">
						    </td>
						    <td>
						    	<input name="bz" v-model='obj.bz' placeholder="包装" class="txt layui-input goodsList">
						    </td>
						    <td>
						    	<input name="jianshu" v-model='obj.jianshu' placeholder="件数" class="txt layui-input goodsList">
						    </td>
						    <td>
						    	<input name="zl" v-model='obj.zl' placeholder="重量" class="txt layui-input goodsList">
						    </td>
						    <td>
						    	<input name="tj" v-model='obj.tj' placeholder="体积" class="txt layui-input goodsList">
						    </td>
						    <td colspan="2">
						    	<input name="shenMingJiaZhi" v-model='obj.shenMingJiaZhi' placeholder="申明价格" class="txt layui-input goodsList">
						    </td>
						    <td colspan="2">
						    	<input name="baoJiaJinE" v-model='obj.baoJiaJinE' placeholder="保价金额"  class="txt layui-input goodsList">
						    </td>
						</tr>
						<tr>
						    <td>服务方式</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select name="fwfs" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.fwfs">
   									 <option v-for="obj in fwfs_options" v-bind:value="obj.value">{{obj.text}}</option>
								</select>
						    </td>
						    <td>付款方式</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select name="fuKuanFangShi" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.fuKuanFangShi">
   									<option v-for="obj in fukuanfangshi_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						    <td>操作要求</td>
						    <td colspan="3" style="overflow: inherit;">
						    	<select name="caozuoyaoqiu" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.caozuoyaoqiu">
   									<option v-for="obj in caozuoyaoqiu_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						</tr>
						<tr>
						    <td>是否返单</td>
						    <td style="overflow: inherit;">
						    	<select name="isfd" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.isfd">
   									<option v-for="obj in isfd_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						    <td>代客包装</td>
						    <td style="overflow: inherit;">
						    	<select name="daikebaozhuang" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.daikebaozhuang">
   									<option v-for="obj in daikebaozhuang_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						    <td>作业方式</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select name="zyfs" lay-ignore class="txt layui-input orderEntity" v-model="orderEntity.zyfs">
   									<option v-for="obj in zyfs_options" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						    <td colspan="3" rowspan="2">
 						    	<input type="checkbox" id="songhuoshanglou"  v-bind:value="orderEntity.songhuoshanglou" lay-ignore name="songhuoshanglou" class="orderEntity"/>送货上楼
						    	<input type="checkbox" id="daikezhuangxie"   v-bind:value="orderEntity.daikezhuangxie"  lay-ignore name="daikezhuangxie" class="orderEntity"/>代客装卸
						    </td>
						</tr>
						<tr>
						    <td colspan="7">
						    返单(名称<input name="fdnr" style="border-bottom:1px solid;text-align:center;" class="orderEntity" v-model="orderEntity.fdnr"/>
						    联次<input name="fdlc" style="border-bottom:1px solid;text-align:center;" class="orderEntity" v-model="orderEntity.fdlc"/>
						    份数<input name="fdsl" style="border-bottom:1px solid;text-align:center;" class="orderEntity" v-model="orderEntity.fdsl"/>)
						    代付款(<input name="dfk" style="border-bottom:1px solid;text-align:center;" class="orderEntity" v-model="orderEntity.dfk"/>元)</td>
						</tr>
						<tr>
						    <td>客服说明</td>
						    <td colspan="10">
						    	<input name="khsm" value="1"  class="txt layui-input orderEntity" v-model="orderEntity.khsm"/>
						    </td>
						</tr>
						<tr>
						    <td rowspan="5">调度派车单填写</td>
						    <td colspan="5" rowspan="5" style="position:relative;padding:0;width:100%;overflow: inherit;">
						        <table style="position:absolute;top: 0px">
						           <tr class="newTr">
									    <td style="width:20%">车辆性质</td>
									    <td style="width:25%">车号</td>
									    <td style="width:25%">车型</td>
									    <td style="width:25%">司机<button class="layui-btn adddispatch" @click='_addDispatch' type='button' style="height: 20px; line-height: 20px;padding: 0px 5px;margin-left: 5px;font-size: 12px;">添加</button></td>
									    <!-- <td style="width:12%"></td> -->
						           </tr>
						           <tr v-for='item in dispatchLists'>
						              <td style="border-top-color: white;border-left-color:white;overflow: inherit;">
						                 <select name="clxz" lay-ignore class="txt layui-input dispatchList dispatchRange" v-model="item.clxz"/>
   									         <option v-for="obj in clxz_options" v-bind:value="obj.valuestr">{{obj.text}}</option>
						    	         </select> 
						    	       </td>
						    	       <td style="border-top-color: white;border-left-color:white;">
						    	          <input name="ch" placeholder="车号"  class="txt layui-input dispatchList dispatchRange" v-model="item.ch"/>
						    	          <input name="detailid" type="hidden" class="txt layui-input dispatchList dispatchRange" v-model="item.detailid"/>
									    </td>
									    <td style="border-top-color: white;border-left-color:white;">
									    	<input name="cx" placeholder="车型"  class="txt layui-input dispatchList dispatchRange" v-model="item.cx"/>
									    </td>
									    <td style="border-top-color: white;border-left-color:white;border-right-color:white; overflow: inherit;">
									    	 <input name="sj" class="txt layui-input dispatchList dispatchRange sj selectInput" :data="11">
								    	      <ul class="selectUl sjUl" style="max-width: 130px;">
								    	         <li  v-for="obj in drivers_options" v-bind:value="obj.text" :data="obj.text" >{{obj.text}}-{{obj.value}}</li>
								    	      </ul>
									    </td>
						    	    </tr>
						        </table>
						    
						    </td>
						    <td>派车调度</td>
						    <td colspan="">
						    	<input name="ddpcdd" class="txt layui-input orderEntity dispatchRange ddpcdd" v-model="orderEntity.ddpcdd"/>
						    </td>
						   
						    <td>派车时间</td>
						    <td colspan="2">
						    	<input name="ddpctime" id="date2" :data="orderEntity.ddpctime" class="txt layui-input orderEntity dispatchRange ddpctime"/>
						    </td>
						</tr>
						<tr>
						    <td>起始地</td>
						    <td colspan="" style="overflow: inherit;">
						    	<!-- <select name="ddqsd" lay-ignore class="txt layui-input orderEntity dispatchRange ddqsd" v-model="orderEntity.ddqsd"/>
						    		<option v-for="obj in placeOptions" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select> -->
						    	<input name="ddqsd"  class="txt layui-input orderEntity dispatchRange selectInput ddqsd" id="ddqsd" v-model="orderEntity.ddqsd">
					    	    <ul class="selectUl" style="max-width: 120px;">
					    	        <li  v-for="obj in placeOptions" v-bind:value="obj.value">{{obj.text}}</li>
					    	    </ul>
						    </td>
						  
						    <td>取货地</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select name="ddqhd" onchange="changeMileage()" lay-ignore id="ddqhd" class="txt layui-input orderEntity dispatchRange ddqhd" v-model="orderEntity.ddqhd"/>
						    		<option v-for="obj in placeOptions" v-bind:value="obj.value">{{obj.text}}-{{obj.min}}-{{obj.max}}</option>
						    	</select>
						    </td>
						</tr>
						<tr>
						    <td>取货里程</td>
						    <td colspan="" style="overflow: inherit;">
						    	<select name="lc" lay-ignore class="txt layui-input orderEntity dispatchRange lc" v-model="orderEntity.lc"/>
						    		<option v-for="obj in mileageOptions" v-bind:value="obj.lc">{{obj.text}}-{{obj.value}}</option>
						    	</select>
						    	<input type="hidden" name="pcshlc" v-model="orderEntity.pcshlc" class="orderEntity_lc"/>
						    </td>
						 
						    <td>提成趟次</td>
						    <td colspan="2" style="overflow: inherit;">
						    	<select name="tangci" lay-ignore class="txt layui-input orderEntity dispatchRange tangci" v-model="orderEntity.tangci"/>
						    		<option v-for="obj in tctimesOptions" v-bind:value="obj.value">{{obj.text}}</option>
						    	</select>
						    </td>
						</tr>
						<tr>
						    <td>提成目的地</td>
						    <td colspan="4">
						    	<input name="pcmdd" class="txt layui-input orderEntity dispatchRange pcmdd" v-model="orderEntity.pcmdd"/>
						    </td>
						</tr>
						<tr>
						    <td>特别说明</td>
						    <td colspan="4">
						   		<input name="ddcomm" class="txt layui-input orderEntity dispatchRange ddcomm" v-model="orderEntity.ddcomm"/>
						    </td>
						</tr>
					</tbody>
				</table>
			</div>
			<div>
			<div style="text-align:center;margin-top:20px;margin-bottom: 30px;">
			    <button onclick="savePickFormHomeOrder()" class="layui-btn btn_save" type='button'>保存</button>
			    <button class='layui-btn no-print' type='button' @click='_print'>打印预览</button>
			    <!-- 
			    <button onclick="back()" class="layui-btn layui-btn-primary" type='button'>返回</button>
			     -->
			</div>
				
				<!--  <button v-show='isSave' class='layui-btn layui-btn-warm no-print' @click='_saveAjax'>保存</button>
				<button v-show='!isSave' class='layui-btn layui-btn-disabled no-print'>保存</button>
				<button v-show='isSave' class='layui-btn no-print' @click='_print'>打印</button>
				<button v-show='!isSave' class='layui-btn layui-btn-disabled no-print'>打印</button>
    			<button class='layui-btn layui-btn-primary no-print' @click='_outLayer'>返回</button> -->
			</div>
		</div>
	</form>
</div>

<company-select v-show='false' ref='companys' style="magin-top:20px;"></company-select>
<%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    function back(){
	    	window.location.href = base_url+'/transport/bundleArrive/pickGoods/search';
	    }
	    var pcid = '${pcid}';//页面默认加载会传入派车单号
	    var openTypeStr = '${openType}';
	    var openType = 0;
	    if(openTypeStr!='' && openTypeStr=='1') {
	    	openType = 1;
	    }else if(openTypeStr!='' && openTypeStr=='0'){
	    	openType = 0;
	    }
	    var companyName = '${CURR_COMPANY_NAME}';
	    var kfy = '${CURR_USER_NAME}';
	    var kfgrid = '${CURR_USER_GRID}';
	    var yywd = '${CURR_NET_POINT}';
    </script>
     <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>    
    <script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script> 
    <script src="${ctx_static}/transport/pickgoods/js/picktohome.js?t=221"></script>
</body>
</html>