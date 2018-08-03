<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>取货派车处理查询</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/pickgoods.css?12"/>
</head>
<body>
<div id='arrivellodiing'>
    <form class='layui-form check-condition'>
		<div class='layui-form-item' style='max-width: 1400px;'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>下单时间：</label>
					<div class='layui-input-block'>
						<input type='text' id='xdtimeStart' class='layui-input'>
					</div>
					<span class='middle-copy'>至</span>
					<div class='layui-input-block'>
						<input type='text' id='xdtimeEnd' class='layui-input'>
					</div>
				</div>
			</div>
			 <div class="layui-inline">
		      <input type="radio" name="pcyes" class="pcyes_all" checked="checked" title="全部">
		      <input type="radio" name="pcyes" class="pcyes_yes" value="1" title="已派">
		      <input type="radio" name="pcyes" class="pcyes_no" value="0" title="未派"> 
		    </div>
			<div class='layui-inline'>
				<company-select ref='companys' style="magin-top:20px;"></company-select>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>派车单号：</label>
					<div class='layui-input-block'>
						<input type='text' id='carNum' class='layui-input' maxlength="15">
					</div>					
				</div>
			</div>
		</div>
		<div class="layui-form-item" style='margin-left: 15px;'>
			<button class="layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>调度派车查询</button>
			<!--  <button class="layui-btn toCheck" >调度派车司机查询</button>-->
			<!--  
			<button class="layui-btn layui-btn" @click="_dispatch" type='button'>调度派车</button>
		    <button class="layui-btn layui-btn" type='button'>签收核算</button>
			-->
		    <button class="layui-btn layui-btn-normal" style='margin-left: 15px;' type='button' @click='_layerContentPage'>录入上门取货指令</button>
		    <a class="layui-btn layui-btn-primary toCheck" style="position:relative;float:right" @click='_expt'>导出</a>
		    <!-- 
		    <button class="layui-btn layui-btn-primary" style="position:relative;float:right" @click='_printHandler' type='button'>打印派车单</button>
		    <button class="layui-btn layui-btn-primary" style="position:relative;float:right" type='button'>打印派车单司机</button>
		     -->
	   </div>
	</form>
    <hr/>
    <table class='layui-hide' id='tableList' lay-filter='table-data'></table>
    <script type="text/html" id="dispatchAndCalc">
		<a class="layui-btn layui-btn-warn layui-btn-xs" lay-event="pickgoods">取货修改</a>
  		<a class="layui-btn layui-btn-warn layui-btn-xs" lay-event="dispatch">派车</a>
  		<a class="layui-btn layui-btn-warn layui-btn-xs" lay-event="calc">结算</a>
	</script>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <%@ include file="../../../static/publicFile/publicComponent.jsp"%>
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/pickgoods.js?t=6"></script>
</body>
</html>