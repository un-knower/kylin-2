<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>提货签收单查询</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
</head>
<body>
<div id='arrivellodiing'>
    <span class='layui-breadcrumb breadcrumb no-print'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>提货签收单查询</a>
	</span>
    <form class='layui-form check-condition'>
		<div class='layui-form-item' style='max-width: 1400px;'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.ydbhid' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>提货单号：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.thqshdid' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<company-select ref='companys'></company-select>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>提货人：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.thrmch' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>收货人：</label>
					<div class='layui-input-block'>
						<input v-model='checkinfo.shhrmch' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
		    <button class="layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
		    <shiro:hasPermission name="bundleArrive:pickdelivery:cancel"> 
				<button class="layui-btn" type='button' @click='_cancelReceipt'>撤销提货</button>
			 </shiro:hasPermission>
		    
		    <!-- <button class="layui-btn" type='button' @click='_layerContent'>测试</button> -->
	   </div>
	</form>
    <hr/>
    <table class='layui-hide' id='tableList' lay-filter='table-data'></table>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <%@ include file="../../../static/publicFile/publicComponent.jsp"%>
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/receitCheck.js?t=2"></script>
</body>
</html>