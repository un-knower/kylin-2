<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>到货装载清单查询</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalloding/css/arrivalloding.css?12"/>
    <style>
    .selectUl,.fazhanUI,.daozhanUI,com_select {
	margin-top:5px;
    display: none;
    z-index: 1;
    background-color: #fff;
    border: 1px solid #eee;
    display: none;
    width: 100%;
    clear: both;
    overflow: auto;
    position: absolute;
    max-height: 250px;
}
.selectUl li,com_select li {
    padding:8px 0 8px 8px;
    cursor: pointer;
}
.fazhanUI li,com_select li {
    padding:8px 0 8px 8px;
    cursor: pointer;
}
.daozhanUI li,com_select li {
    padding:8px 0 8px 8px;
    cursor: pointer;
}
.selectUl li:hover{background:#EBEBEB;}
    
    </style>
</head>
<body>
<div id='arrivellodiing' @print-handler='_doComponentsPrint'>
    <!-- <span class='layui-breadcrumb breadcrumb no-print'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>到货装载清单查询</a>
	</span> -->
    <form class='layui-form check-condition'>
		<div class='layui-form-item'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.transportCode' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>车牌号：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.carNo' type='text' id='carNumber' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>装车日期：</label>
					<div class='layui-input-block'>
						<input type='text' id='cardatestart' class='layui-input'>
					</div>
					<span class='middle-copy'>至</span>
					<div class='layui-input-block'>
						<input type='text' id='cardateend' class='layui-input'>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>装车发站：</label>
					 <div class="layui-input-inline selectBox layui-form-select">
			           <input type="text" v-model='checkinfo.fazhan' name="selectBox" id="fazhan" lay-verify="selectBox" autocomplete="off"  class="layui-input selectInput_fazhan"  />
			           <i class="layui-edge tips_type_fazhan"></i>
			           <ul class="fazhanUI"></ul>
			        </div>
				</div>
			</div>
			<div class='layui-inline'>	
				<div class='input-div'>
					<label class='layui-form-label'>装车到站：</label>
					 <div class="layui-input-inline selectBox layui-form-select">
			           <input type="text" v-model='checkinfo.daozhan' name="selectBox" id="daozhan" lay-verify="selectBox" autocomplete="off"  class="layui-input selectInput_daozhan"  />
			           <i class="layui-edge tips_type_daozhan"></i>
			           <ul class="daozhanUI"></ul>
			        </div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运输方式：</label>
					 <div class="layui-input-inline selectBox layui-form-select">
			           <input type="text" v-model='checkinfo.ysfs' name="selectBox" id="ysfs" lay-verify="selectBox" autocomplete="off"  class="layui-input selectInput"  />
			           <i class="layui-edge tips_type"></i>
			           <ul class="selectUl"></ul>
			        </div>
				</div>
			</div>
		    <div class="layui-inline radio-all">
		      <input type="radio" lay-filter='all' name="method" checked='' value="全部"  title="全部">
		      <input type="radio" lay-filter='ziti' name="method" value="自提" title="自提">
		      <input type="radio" lay-filter='shsm' name="method" value="送货上门" title="送货上门">
		    </div>
		    <div class="layui-inline radio-all ziti-control" v-show='isziti'>
		      <input type="radio" lay-filter='yiti' name="isyiti" value="已提"  title="已提">
		      <input type="radio" lay-filter='weiti' name="isyiti" value="未提" title="未提"> 
		    </div>
		    <button class="layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
	   </div>
	</form>
	<hr/>
    <div class='info-detail check-box'>
    	<div class='layui-form-item'>
    		<shiro:hasPermission name="bundleArrive:pickdelivery:create"> 
				<button class='layui-btn' id="tihuodan" @click='_SignHandler(tihuoAjaxData)'>生成提货单</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="bundleArrive:senddelivery:create"> 
				<button class='layui-btn' id="songhuodan" @click='_SignHandler(songhuoAjaxData)'>生成送货单</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="bundleArrive:collectMoney:search"> 
				<button class='layui-btn' @click='_printReceipt'>生成分理数据</button>
			</shiro:hasPermission>
    	
    		<button class='layui-btn' @click='_printClick(".detailed-list")'>打印</button>
    		<button class='layui-btn' @click='_printLabel(".detailed-list")'>打印70*60标签</button>
    		<button class='layui-btn' @click='_printLabelBig(".detailed-list")'>打印70*90标签</button>
    		<button class="layui-btn" @click='printMoneyWithoutBorder(".detailed-list")' id='print'>打印运输受理单（套打）</button>
	    	<button class="layui-btn" @click='printMoneyWithBorder(".detailed-list")' id='printA4'>打印运输受理单（A4）</button> 
    		<button class='layui-btn layui-btn-warm' @click='_importData'>导出</button>
    	</div>
    </div>
    <hr/>
    <table class='layui-hide' id='tableList' lay-filter='table-data'></table>
    <div class='detailed-list' v-show='false'>
		<detailed-list :username='username' :tablelist='printData' :tabledata='printData' :curr_time='currTime' ref='printList' title="货车装载清单" identifier='YY05-003'></detailed-list>
	</div>
	<div class='receive-bill' v-show='false'>
		<receipt-bill :username='username' :curr_time='currTime' :xuhaos='tihuoListXuhao' :tablelist='tihuoListData'></receipt-bill>
	</div>
	<div class='send-bill' v-show='false'>
		<send-bill ref='sendbill' :sendinfo='sendinfo' :beizhu='beizhus' :compname='compname' :username='username' :tablelist='tihuoListData' :curr_time='currTime' :company='company' :xuhaos='tihuoListXuhao'></send-bill>
	</div>
	<iframe id="printf" src="" width="0" height="0" frameborder="0"></iframe>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    var layuiIndex = -1;
	    var companyName = '${CURR_COMPANY_NAME}';
	    var companyCode = '${CURR_COMPANY_CODE}';
	    $(".toReturn").on("click",function(){
			window.location.href = base_url + "/transport/convey/manage";
		});
	    layui.use(['element','form'], function(){
    		var element = layui.element;
    		var form = layui.form;
    	})
    	function tihuodan_search(){
	    	if(layuiIndex!=-1) layer.close(layuiIndex);
	    	document.getElementById("tihuodan").click();
	    }
    	function songhuodan_search(){
    		if(layuiIndex!=-1) layer.close(layuiIndex);
    		document.getElementById("songhuodan").click();
	    }
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <%@ include file="../../../static/publicFile/publicComponent.jsp"%> 
    <%@ include file="../../../static/transport/arrivalloding/js/vueComponent.jsp"%>
    <script src="${ctx_static}/transport/arrivalloding/js/arrivalloding.js?t=232"></script>
</body>
</html>