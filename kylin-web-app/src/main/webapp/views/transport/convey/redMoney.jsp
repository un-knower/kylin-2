<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>财凭冲红</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/redMoney/css/redMoney.css?v=1"/>
</head>
<body style='overflow: hidden;'>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>异常操作维护</a>
		<a href='javaScript:void(0);'>财凭冲红</a>
	</span>
	
	<form class='layui-form check-form'>
		<div class='layui-form-item check-waybill'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>公司：</label>
					<c:choose>
					<c:when test = "${CURR_COMPANY_NAME eq '总公司'}">
					<select  class='' >
						<option value="YC">总公司</option>
						<c:if test="${COMPANY_LIST != NULL}">
							<c:forEach items="${COMPANY_LIST }" var="i" varStatus="status">
								<option value="${i.companyCode}">${i.companyName}</option>
							</c:forEach>
						</c:if>
					</select> 
					</c:when>
					<c:otherwise>
						<div class='layui-input-block'>
							<input id='companyN' data-value='${CURR_COMPANY_CODE}' value="${CURR_COMPANY_NAME}" type='text' class='layui-input' readonly>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label label-div'>年度：</label>
					<div class='layui-input-block'>
						<input type='text' class='layui-input clear-data change-status' id='select-year'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label label-div'>运单号：</label>
					<div class='layui-input-block'>
						<input type='text' id='transportCode' name='carNumber' class='layui-input' placeholder='请输入'>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>财凭号：</label>
					<div class='layui-input-block'>
						<input type='text' id='waybillId' class='clear-data change-status layui-input' placeholder='非必填'>
					</div>
				</div>
			</div>
			<shiro:hasPermission name="undo:finance:search:noright"> 
				  <div class='layui-inline'><button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button></div>
			 	  <input name="permissLevel" id="permissLevel" type="hidden" value="1"/>
			 	</shiro:hasPermission>
			 	<shiro:hasPermission name="undo:finance:search:common"> 
				  <div class='layui-inline'><button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button></div>
			 	  <input name="permissLevel" id="permissLevel" type="hidden" value="2"/>
			 	</shiro:hasPermission>
			 	<shiro:hasPermission name="undo:finance:search:grouper"> 
				  <div class='layui-inline'><button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button></div>
			 		<input name="permissLevel" id="permissLevel" type="hidden" value="3"/>
			 	</shiro:hasPermission>
			 	<shiro:hasPermission name="undo:finance:search:manager"> 
				  <div class='layui-inline'><button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button></div>
			 		<input name="permissLevel" id="permissLevel" type="hidden" value="4"/>
			 	</shiro:hasPermission>
			
		</div>
		<!-- <div class='btn-operation'> 
		    <button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
		    <button class="layui-btn layui-btn-warm toCheck" lay-submit  lay-filter='toCancel'>撤销签收</button>
		</div> -->
	</form>
	
	
	<fieldset class="layui-elem-field layui-field-title">
		<legend>查询信息</legend>
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
					<th>财凭号</th>
					<th>红票财凭号</th>
					<th>运单号</th>
					<th>录入时间</th>
					<th>叉车费</th>
					<th>装卸费（轻）</th>
					<th>保险费</th>
					<th>装卸费（重）</th>
					<th>办单费</th>
					<th>上门取货费</th>
					<th>送货上门费</th>
					<th>代收货款</th>
					<th>其他费用</th>
					<th>合计费用</th>
				</tr>
			</thead>
			<tbody id='showdetailList'></tbody>
		</table>
	</div>
	
	<form class='layui-form layui-from-pane select-condition'>
			<div class="layui-form-item layui-form-text toTips">
		    	<label class="layui-form-label beizhu"><span class="must-item"></span>&nbsp;冲红原因：</label>
		    	<div class="layui-input-block textarea-div">
		      		<textarea id='tips' data-value='comm' placeholder="暂无数据" class="clear-data layui-textarea Vehicle-condition waybill-info" readonly></textarea>
		    	</div>
		    </div>
	</form>
	<div class='btn-div' style='text-align: center;'>
		<button class="layui-btn layui-btn-disabled toRed" lay-submit id='toRed'>冲红</button>
		<button class="layui-btn toClear" lay-submit  id='toClear'>清空数据</button>
		<!-- <button class="layui-btn" type='button'  id='print'>打印运输受理单</button> -->
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
    <script type="text/x-jsrender" id='tableList'>
		<tr>
			<td>
				<span>{{:wealthCode}}</span>
			</td>
			<td>
				<span>{{:redWealthCode}}</span>
			</td>
			<td>
				<span>{{:transportCode}}</span>
			</td>
			<td>
				<span>{{:createTime}}</span>
			</td>
			<td>
				<span>{{:forkliftFee}}</span>
			</td>
			<td>
				<span>{{:lightLoadingFee}}</span>
			</td>
			<td>
				<span>{{:insuranceExpense}}</span>
			</td>
			<td>
				<span>{{:heavyLoadingFee}}</span>
			</td>
			<td>
				<span>{{:handleBillFee}}</span>
			</td>
			<td>
				<span>{{:pickupFee}}</span>
			</td>
			<td>
				<span>{{:deliveryFee}}</span>
			</td>
			<td>
				<span>{{:agencyCharge}}</span>
			</td>
			<td>
				<span>{{:otherFee}}</span>
			</td>
			<td>
				<span>{{:totalFee}}</span>
			</td>
		</tr>
	</script>
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;
    		 laydate.render({
    			elem: '#select-year'
    			,type: 'year'
    			,theme: '#393D49'
    		});
    	})
    </script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/redMoney/js/redMoney.js?t=10"></script>
    
</body>
</html>