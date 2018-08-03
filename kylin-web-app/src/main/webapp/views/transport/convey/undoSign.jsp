<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>撤销签收</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/abnormal/undoSign/css/undoSign.css"/>
</head>
<body style='overflow-x: hidden;'>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>撤销签收</a>
	</span>
	
	<!-- <hr class='layui-bg-cyan'> -->
	
	<form class='layui-form check-form'>
		<div class='layui-form-item check-waybill'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号：</label>
					<div class='layui-input-block'>
						<input lay-verify='required' type='text' id='waybillId' class='layui-input'>
					</div>
				</div>
			</div>
		</div>
		
		<div class='btn-operation'> 
			<shiro:hasPermission name="undo:sign:search"> 
				  <button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
			 </shiro:hasPermission>
		</div>
	</form>
	
	
	<fieldset class="layui-elem-field layui-field-title">
		<legend>运单信息</legend>
	</fieldset>
	<hr>
	
	<div class='layui-form'>
		<table class='layui-table table-div'>
			<colgroup>
				<col width='10%'>
				<col width='8%'>
				<col width='8%'>
				<col width='15%'>
				<col width='15%'>
				<col width='30%'>			
			</colgroup>
			<thead>
				<tr>
					<th>运单编号</th>
					<th>发站</th>
					<th>到站</th>
					<th>收货人</th>
					<th>收货人电话</th>
					<th>发货人</th>
					<th>发货人电话</th>
				</tr>
			</thead>
			<tbody id='shoeTableList'></tbody>
		</table>
	</div>
			<div class='layui-form-item isVIP'>
				<p style='color: #FF5722'>
					(&nbsp;<i class='layui-icon' style='color: darkgreen'>&#xe735;</i>
					此发货人为绿色通道用户。)
				</p>
			</div>
	
	<fieldset class="layui-elem-field layui-field-title" style='margin-top: 3%;'>
		<legend>运单签收详情</legend>
	</fieldset>
	<hr>
	
	<div class='layui-form'>
		<table class='layui-table table-div'>
			<colgroup>
				<!-- <col width='10%'>
				<col width='8%'>
				<col width='8%'>
				<col width='15%'>
				<col width='15%'>
				<col width='30%'>	 -->		
			</colgroup>
			<thead>
				<tr>
					<th>破损件数</th>
					<th>短少件数</th>
					<th>录入人</th>
					<th>录入时间</th>
					<th>签收人</th>
					<th>签收人电话</th>
					<th>签收状态</th>
					<th>签收时间</th>
				</tr>
			</thead>
			<tbody id='showdetailList'></tbody>
		</table>
	</div>
	
	<form class='layui-form layui-from-pane select-condition'>
			<div class="layui-form-item layui-form-text toTips">
		    	<label class="layui-form-label beizhu">备注：</label>
		    	<div class="layui-input-block textarea-div">
		      		<textarea id='tips' data-value='comm' placeholder="暂无数据" class="layui-textarea Vehicle-condition waybill-info" readonly></textarea>
		    	</div>
		    </div>
		    <shiro:hasPermission name="undo:sign:save"> 
				    <button class="layui-btn layui-btn-disabled toCancel" lay-submit  lay-filter='toCancel'>撤销签收</button>
			 </shiro:hasPermission>
		  
	</form>
	
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
    <script type="text/x-jsrender" id='tableList'>
		<tr>
			<td id='numberID'>
				<span>{{:ydbhid}}</span>
			</td>
			<td>
				<span>{{:fazhan}}</span>
			</td>
			<td>
				<span>{{:daozhan}}</span>
			</td>
			<td>
				<span>{{:shhrmch}}</span>
			</td>
			<td>
				<span>{{:shhrlxdh}}</span>
			</td>
			<td>
				<span>{{:fhdwmch}}</span>
			</td>
			<td>
				<span>{{:fhdwlxdh}}</span>
			</td>
		</tr>
	</script>
	<script type="text/x-jsrender" id='detailList'>
		<tr>
			<td>
				<span>{{:psjianshu}}</span>
			</td>
			<td>
				<span>{{:dsjianshu}}</span>
			</td>
			<td>
				<span>{{:lrr}}</span>
			</td>
			<td>
				<span>{{:lrTime}}</span>
			</td>
			<td>
				<span>{{:qsr}}</span>
			</td>
			<td>
				<span>{{:qsrphone}}</span>
			</td>
			<td>
				{{if qszt == 0}}
					<span>未签收</span>
				{{else qszt == 11}}
					<span>完好签收</span>
				{{else qszt == 12}}
					<span>延误签收</span>
				{{else qszt == 1}}
					<span>破损签收</span>
				{{else qszt == 2}}
					<span>短少签收</span>
				{{else qszt == 3}}
					<span>综合事故</span>
				{{else qszt == 21}}
					<span>客户要求延时</span>
				{{else qszt == 20}}
					<span>其他</span>
				{{else qszt == 221}}
					<span>客户拒收-破损</span>
				{{else qszt == 222}}
					<span>客户拒收-短少</span>
				{{else qszt == 223}}
					<span>客户拒收-延时</span>
				{{else qszt == 224}}
					<span>客户拒收-串货</span>
				{{else qszt == 225}}
					<span>客户拒收-其它</span>
				{{else qszt == 224}}
					<span>客户拒收-串货</span>
				{{else qszt == 22}}
					<span>客户拒收</span>
				{{/if}}
				
			</td>
			<td>
				<span>{{:qsTime}}</span>
			</td>
		</tr>
	</script>
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate', 'layedit'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;
    		var layedit = layui.layedit;
    	}) 
    </script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/abnormal/undoSign/js/undoSign.js"></script>
    
</body>
</html>