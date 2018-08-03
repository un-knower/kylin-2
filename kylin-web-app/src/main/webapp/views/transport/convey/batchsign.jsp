<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>大客户批量签收</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/batchsign/css/batchsign.css?12"/>
</head>
<body>
	<!-- <span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>批量签收</a>
	</span> -->
	<div class='layui-process layui-process-big' lay-showpercent='true' lay-filter='processbar'>
		<div class='layui-process-bar layui-bg-red' layui-percent='0%'></div>
	</div>
    <fieldset class="layui-elem-field layui-field-title info-title" style="margin-top: 20px;">
        <legend>基本信息</legend> 
    </fieldset>
    <div style='float: left;'>
	    <div class="loading-model">
	        <span class="load-name">模板：</span>
	        <span class="load-model">&nbsp;&nbsp;批量签收标准模板下载</span>
	    </div>
	    <div class="loading-model">
	        <label>导入：</label>
	        <button class="layui-btn layui-btn-warm select-file info-btn">
	            选择文件
	            <form id="myForm">
	                <input type="file" id="excell-file">
	            </form>
	        </button>
	        <span id="file-name">未选择文件</span>
	    </div>
    </div>
    <div class='info-detail'>
    	<h1>导入提示：</h1>
    	<ol style='margin-top: 10px;'>
    		<li>1.导入文件行数不能超过100行。</li>
    		<li>2.模板文件第一行为标题，请勿删除。</li>
    		<li>3.导入数据中，以数据分类字段的值，作为检验重复项的标准。</li>
    		<li>4.已经撤销签收的运单，再次签收默认签收时间为第一次签收时间，不可更改。</li>
    	</ol>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;clear: both;">
        <legend>信息显示</legend>
    </fieldset>
    <div class='layui-tab'>
	    <ul class="informatin-return layui-tab-title">
	        <li class="layui-this">导入数据<span class="layui-badge import-data-number all-data">0</span></li>
	        <li>成功<span class="layui-badge success-data all-data clear-datanum">0</span></li>
	        <li>签收失败<span class="layui-badge fail-data all-data clear-datanum">0</span></li>
	        <button class="layui-btn toSubmit do-btn layui-btn-disabled">一键签收</button>
	    </ul>
	    <div class='layui-tab-content'>
	    	<div class='layui-tab-item layui-show' style='position: relative;'>
	    		<div class='loading'><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop" style='font-size: 50px;color: #FF5722;'>&#xe63d;</i></div>
		    	<div class="ifImportNodata nodata" style="text-align: center; margin-top: 20%;">
			        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
			        <span class="no-data-copy">暂无数据</span>
			    </div>
	    		<table class="layui-table table-show import-table" id="demo" lay-filter="excell-demo" style='display: none;'>
	    			<thead>
	    				<tr>
	    					<th>运单编号</th>
	    					<th>签收时间</th>
	    					<th>签收状态</th>
	    					<th>签收人</th>
	    				</tr>
	    			</thead>
	    			<tbody id='import-data'></tbody>
	    		</table>
	    	</div>
	    	<div class='layui-tab-item'>
	    		<button class="layui-btn toExport-success" style='display:none'>导出</button>
	    		<div class="if-successNodata nodata" style="text-align: center; margin-top: 20%;">
			        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
			        <span class="no-data-copy">暂无数据</span>
			    </div>
	    		<table class="layui-table table-show success-table" id="success-list" lay-filter="success-import" style='display: none;'>
	    			<thead>
	    				<tr>
	    					<th>运单编号</th>
	    					<th>签收时间</th>
	    					<th>签收状态</th>
	    					<th>签收人</th>
	    					<th>导入人</th>
	    					<th>导入人工号</th>
	    					<th>导入人公司</th>
	    				</tr>
	    			</thead>
	    			<tbody id='success-data' class='clear-data'></tbody>
	    		</table>
	    	</div>
	    	<div class='layui-tab-item'>
	    		<button class="layui-btn toExport" style='display:none'>导出</button>
	    		<div class="falseData nodata" style="text-align: center; margin-top: 20%;">
			        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
			        <span class="no-data-copy">暂无数据</span>
			    </div>
	    		<table class="layui-table table-show false-table" id="false-list" lay-filter="fail-import" style='display: none;'>
	    			<thead>
	    				<tr>
	    					<th>运单编号</th>
	    					<th>签收时间</th>
	    					<th>签收状态</th>
	    					<th>签收人</th>
	    					<th>导入人</th>
	    					<th>导入人工号</th>
	    					<th>导入人公司</th>
	    					<th>失败原因</th>
	    				</tr>
	    			</thead>
	    			<tbody id='false-data' class='clear-data'></tbody>
	    		</table>
	    	</div>
	    </div>
    </div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    $(".toReturn").on("click",function(){
			window.location.href = base_url + "/transport/convey/manage";
		});
    </script>
    <script src="${ctx_static}/transport/excelRead/js/jquery.min.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/xlsx.core.min.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/jsrender-min.js"></script>
    <script type="text/x-jsrender" id='import-tbody'>
		<tr class='batchSign-data'>
			<td>{{:ydbhid}}</td>
			<td>{{:qsTime}}</td>
			<td>{{:qszt}}</td>
			<td>{{:qsr}}</td>
		</tr>
	</script>
	<script type="text/x-jsrender" id='success-tbody'>
		<tr class='sign-success'>
			<td>{{:ydbhid}}</td>
			<td>{{:qsTime}}</td>
			<td>{{:qszt}}</td>
			<td>{{:qsr}}</td>
			<td>{{:lrr}}</td>
			<td>{{:lrrGrid}}</td>
			<td>{{:gs}}</td>
		</tr>
	</script>
	<script type="text/x-jsrender" id='false-tbody'>
		<tr class='sign-fail'>
			<td>{{:ydbhid}}</td>
			<td>{{:qsTime}}</td>
			<td>{{:qszt}}</td>
			<td>{{:qsr}}</td>
			<td>{{:lrr}}</td>
			<td>{{:lrrGrid}}</td>
			<td>{{:gs}}</td>
			<td>{{:errorMsg}}</td>
		</tr>
	</script>
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/batchsign/js/batchsign.js?321"></script>
    <script type="text/javascript">
    layui.use(['element', 'layer', 'form', 'laypage', 'laydate'], function(){
		var element = layui.element;
		var form = layui.form;
		var laydate = layui.laydate;
		var laypage = layui.laypage;
	})
    </script>
</body>
</html>