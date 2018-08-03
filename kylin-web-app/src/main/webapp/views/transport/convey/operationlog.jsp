<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>操作异常日志报表</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/abnormal/operationlog/css/operationlog.css"/>
</head>
<body>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>操作异常日志报表</a>
	</span>
	
	<hr>
	
	<form class='layui-form check-form'>
		<div class='layui-form-item'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>操作时间:</label>
					<div class='layui-input-block date-select'>
						<input type='text' name='date' class='layui-input' id='startDate' placeholder="请输入">
					</div>
					<span class='date-select date-copy'>至</span>
					<div class='layui-input-block date-select'>
						<input type='text' name='date' class='layui-input' id='endDate' placeholder="请输入">
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='layui-input-block' style='margin-left: 10px;'>
					<select>
						<option value=''>全部</option>
						<option>撤销签收</option>
						<option>撤销装载</option>
						<option>运单作废</option>
						<option>运单修改</option>
						<option>财凭冲红</option>
						<option>修改装载成本</option>
						<option>取货派车修改</option>
						<option>送货派车修改</option>
					</select>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label waybillNum'>运单/派车单号:</label>
					<div class='layui-input-block date-select'>
						<input type='text' name='carNumber' id='waybillId' class='layui-input Vehicle-condition clear-input' placeholder='请输入'>
					</div>
				</div>
			</div>
			<button class='layui-btn toArrive' lay-filter='toCheck' lay-submit=''>查询</button>
		</div>
	</form>
	
	<div class='btn-group'>
		<button class='layui-btn layui-btn-warm toDelete' style='display: none;'>导出数据</button>
	</div>
	<hr>
	
	<table class='layui-table'>
		<colgroup>
			<col width='4%'>
			<col width='8%'>
			<col width='8%'>
			<col width='8%'>
			<col width='12%'>
			<col width='15%'>
			<col width='8%'>
			<col width='25%'>
		</colgroup>
		<thead>
			<tr>
				<td>序号</td>
				<td>操作人名称</td>
				<td>操作人账号</td>
				<td>操作人公司</td>
				<td>IP地址</td>
				<td>运单/派车单号</td>
				<td>操作模块</td>
				<td>操作内容</td>
				<td>操作时间</td>
			</tr>
		</thead>
		<tbody id="showLogList"></tbody>
	</table>
	<div class="pageWrap" style="text-align:center;">
		<div id="page" style="display:inline-block;"></div>
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
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/jsrender-min.js"></script>
    <script type="text/x-jsrender" id='tableList'>
		<tr>
			<td>
				<span>{{:#index+1}}</span>
			</td>
			<td>
				<span>{{:operatorName}}</span>
			</td>
			<td>
				<span>{{:operatorAccount}}</span>
			</td>
			<td>
				<span>{{:operatorCompany}}</span>
			</td>
			<td>
				<span>{{:ipAddress}}</span>
			</td>
			{{if ydbhid != null}}
				<td>
					<span>{{:ydbhid}}-运单编号/派车单号</span>
				</td>
			{{else}}
				<td>
					<span>{{:cwpzhbh}}-财凭号</span>
				</td>
			{{/if}}
			<td>
				<span>{{:operatingMenu}}</span>
			</td>
			<td class='operation-content'>
				<span>{{:operatingContent}}</span>
			</td>
			<td>
				<span>{{:operatingTime}}</span>
			</td>
		</tr>
	</script>
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form', 'laypage', 'laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;
    		var laypage = layui.laypage;
    		laydate.render({
    			elem: '#startDate'
    			,theme: '#393D49'
    		});
    		laydate.render({
    			elem: '#endDate'
    			/* ,type: 'datetime' */
    			,theme: '#393D49'
    		});
    	})
    </script>
    <script src="${ctx_static}/transport/arrive/js/public.js"></script>
    <script src="${ctx_static}/transport/abnormal/operationlog/js/operationlog.js?"></script>
</body>
</html>