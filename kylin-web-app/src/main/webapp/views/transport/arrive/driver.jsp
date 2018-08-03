<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>送货派车查询司机</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
</head>
<body>
<div id='arrivellodiing'>
    <span class='layui-breadcrumb breadcrumb no-print'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>送货派车查询司机</a>
	</span>
    <form class='layui-form check-condition'>
		<div class='layui-form-item' style='max-width: 1400px;'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.ydbhid' @input='_contentChange' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>派车单号：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.thqshdid' @input='_contentChange' type='text' id='carNumber' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>下单时间：</label>
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
		<div class='layui-form-item'>
		    <div class="layui-inline radio-all ziti-control">
		      <input type="radio" lay-filter='all' name="isyipai" checked='' value="全部"  title="全部">
		      <input type="radio" lay-filter='yiti' name="isyipai" value="已派"  title="已派">
		      <input type="radio" lay-filter='weiti' name="isyipai" value="未派" title="未派"> 
		    </div>
		    <button class="layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
			<button class="layui-btn" type='button' @click='_pachiModify'>派车修改</button>
		    <button class="layui-btn" type='button' @click='_importData'>导出</button>
		</div>
	</form>
    <hr/>
    <table class='layui-table' lay-data='{id:"demoList",cellMinWidth: 100}' lay-filter='table-data'>
    	<thead>
    		<tr>
    			<th lay-data="{checkbox:true, fixed:true}" rowspan="2"></th>
    			<th lay-data='{field: "gs"}' rowspan='2'>公司</th>
    			<th lay-data='{field: "id"}' rowspan='2'>派车单号</th>
    			<th lay-data='{field: "yshm",width: 180}' rowspan='2'>运输号码</th>
    			<th lay-data='{field: "ydbhid",width: 110}' rowspan='2'>运单编号</th>
    			<th colspan='3'>始发站</th>
    			<th lay-data='{field: "shhd"}' rowspan='2'>发站网点</th>
    			<th lay-data='{field: "ywlx"}' rowspan='2'>业务类型</th>
    			<th lay-data='{field: "fhdw",width: 365}' rowspan='2'>发货单位</th>
    			<th lay-data='{field: "fhdh",width: 130}' rowspan='2'>发货电话</th>
    			<th lay-data='{field: "shdw",width: 150}' rowspan='2'>收货单位</th>
    			<th lay-data='{field: "shlxr"}' rowspan='2'>收货人手机</th>
    			<th lay-data='{field: "shdz"}' rowspan='2'>收货地址</th>
    			<th lay-data='{field: "shdh",width: 180}' rowspan='2'>送货电话</th>
    			<th lay-data='{field: "JS"}' rowspan='2'>件数</th>
    			<th lay-data='{field: "ZL"}' rowspan='2'>重量</th>
    			<th lay-data='{field: "TJ"}' rowspan='2'>体积</th>
    			<th lay-data='{field: "dsk"}' rowspan='2'>代收款</th>
    			<th lay-data='{field: "hdfk"}' rowspan='2'>到付款</th>
    			<th lay-data='{field: "dzy"}' rowspan='2'>单证号</th>
    			<th lay-data='{field: "dzygrid"}' rowspan='2'>单证号账号</th>
    			<th lay-data='{field: "kdtime",width: 180}' rowspan='2'>开单时间</th>
    			<th lay-data='{field: "jhshtime",width: 180}' rowspan='2'>计划送货时间</th>
    			<th lay-data='{field: "pcdd"}' rowspan='2'>派车调度</th>
    			<th lay-data='{field: "pcddgrid"}' rowspan='2'>调度账号</th>
    			<th lay-data='{field: "pcyes"}' rowspan='2'>已派车</th>
    			<th lay-data='{field: "thtime",width: 180}' rowspan='2'>仓库提货时间</th>
    			<th lay-data='{field: "zxbz"}' rowspan='2'>装卸班组</th>
    			<th lay-data='{field: "cgyqm"}' rowspan='2'>仓管员</th>
    			<th lay-data='{field: "qsr"}' rowspan='2'>客户签收</th>
    			<th lay-data='{field: "qstime",width: 180}' rowspan='2'>签收时间</th>
    			<th lay-data='{field: "pcpctime",width: 180}' rowspan='2'>派车时间</th>
    			<th lay-data='{field: "pcqsd"}' rowspan='2'>起始地</th>
    			<th lay-data='{field: "pcshd"}' rowspan='2'>送货地</th>
    			<th lay-data='{field: "tjhsr"}' rowspan='2'>核算人</th>
    			<th lay-data='{field: "tjhsrgrid"}' rowspan='2'>核算人帐号</th>
    			<th lay-data='{field: "tjtime"}' rowspan='2'>结算时间</th>
    			<th lay-data='{field: "pszhsh"}' rowspan='2'>派送指示</th>
    			<th lay-data='{field: "ch"}' rowspan='2'>车号</th>
    			<th lay-data='{field: "sj"}' rowspan='2'>司机</th>
    			<th lay-data='{field: "hwmc"}' rowspan='2'>品名</th>
    			<th lay-data='{field: "pccomm"}' rowspan='2'>特别说明</th>
    		</tr>
    		<tr>
    			<th lay-data='{field: "ModeOfTransportation"}'>运输方式</th>
    			<th lay-data='{field: "ChargeableTotalVolume"}'>计费总体积</th>
    			<th lay-data='{field: "ChargeableTotalWeight"}'>计费总重量</th>
    		</tr>
    	</thead>
    	
    </table>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/driver.js?t=2"></script>
</body>
</html>