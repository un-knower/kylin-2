<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>送货签收单查询</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/receitCheck.css?12"/>
</head>
<body>
<div id='arrivellodiing'>
    <span class='layui-breadcrumb breadcrumb no-print'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>送货签收单查询</a>
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
			<div class='layui-inline'>
				<company-select ref='companys'></company-select>
			</div>
		    <div class="layui-inline radio-all ziti-control">
		      <input type="radio" lay-filter='all' name="isyipai" checked='' value="全部"  title="全部">
		      <input type="radio" lay-filter='yiti' name="isyipai" value="已派"  title="已派">
		      <input type="radio" lay-filter='weiti' name="isyipai" value="未派" title="未派"> 
		    </div>
		    <button class="layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
		</div>
		<div class="layui-form-item" style='margin-left: 15px;'>
		    <button class="layui-btn" type='button' @click='_pachiModify'>调度派车</button>
		    <button class="layui-btn" type='button' @click='_importData'>导出</button>
		    <button class="layui-btn" type='button' @click='_cancelReceipt'>撤销送货</button>
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
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/sendSignCheck.js?t=5"></script>
</body>
</html>