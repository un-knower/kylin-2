<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>运单批量导入</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/excell.css?12"/>
</head>
<body>
	<div class='layui-process layui-process-big' lay-showpercent='true' lay-filter='processbar'>
		<div class='layui-process-bar layui-bg-red' layui-percent='0%'></div>
	</div>
    <fieldset class="layui-elem-field layui-field-title info-title" style="margin-top: 20px;">
        <legend>信息选择</legend> 
    </fieldset>
    <div style='float: left;'>
	    <div class="loading-model">
	        <span class="load-name">模板：</span>
	        <span class="load-model">&nbsp;&nbsp;运单批量导入标准模板下载</span>
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
    		<li>4.对于解析后的数据结果请查看导入信息显示标签栏数据。</li>
    	</ol>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;clear: both;">
        <legend>信息显示</legend>
    </fieldset>
    <div class='layui-tab'>
	    <ul class="informatin-return layui-tab-title">
	        <li class="layui-this">导入数据<span class="layui-badge import-data-number all-data">0</span></li>
	        <li>成功<span class="layui-badge success-data all-data">0</span></li>
	        <li>解析失败<span class="layui-badge fail-data all-data">0</span></li>
	        <button class="layui-btn toSubmit do-btn layui-btn-disabled">提交</button>
	        <!-- <button class="layui-btn layui-btn-warm toReturn do-btn">返回</button>  -->
	    </ul>
	    <div class='layui-tab-content'>
	    	<div class='layui-tab-item layui-show' style='position: relative;'>
	    		<button class="layui-btn toCheck" style='display:none'>校验</button>
	    		<div class='loading'><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop" style='font-size: 50px;color: #FF5722;'>&#xe63d;</i></div>
		    	<div class="ifImportNodata nodata" style="text-align: center; margin-top: 20%;">
			        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
			        <span class="no-data-copy">暂无数据</span>
			    </div>
	    		<table class="layui-table table-show" id="demo" lay-filter="excell-demo"></table>
	    	</div>
	    	<div class='layui-tab-item'>
	    		<button class="layui-btn toExport-success" style='display:none'>导出</button>
	    		<div class="if-successNodata nodata" style="text-align: center; margin-top: 20%;">
			        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
			        <span class="no-data-copy">暂无数据</span>
			    </div>
	    		<table class="layui-table table-show" id="success-list" lay-filter="success-import"></table>
	    	</div>
	    	<div class='layui-tab-item'>
	    		<button class="layui-btn toExport" style='display:none'>导出</button>
	    		<div class="if-failNodata nodata" style="text-align: center; margin-top: 20%;">
			        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
			        <span class="no-data-copy">暂无数据</span>
			    </div>
	    		<table class="layui-table table-show" id="false-list" lay-filter="fail-import"></table>
	    	</div>
	    </div>
    </div>
    <div class='bar-process'>
    	<div class='wrap-process'>
		    <p><img class='loading_pic' alt="" src="${ctx_static}/transport/excelRead/images/loading.gif"></p>
	    	<div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter="demo">
				<div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>
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
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/excell.js?12"></script>
    <script type="text/javascript">
    	layui.use('element', function(){
    		var element = layui.element;
    	})
    </script>
</body>
</html>