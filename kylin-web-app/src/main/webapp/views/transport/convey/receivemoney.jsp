<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>月结收钱</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/money/css/money.css"/>
</head>
<body>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>月结收钱</a>
	</span>
	
	<!-- <hr class='layui-bg-cyan'> -->
	
	<form class='layui-form check-form'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label label-div'>公司编码：</label>
					<%-- <div class='layui-input-block'>
						<input type='text' name='carNumber' value="${companyCode}" data="" id='companyId' class='layui-input' readonly>
					</div> --%>
				    <c:choose>
					<c:when test = "${CURR_COMPANY_NAME eq '总公司'}">
					 <div class='layui-input-block'>
						<select  class='' >
							<option value="YC">YC</option>
							<c:if test="${COMPANY_LIST != NULL}">
								<c:forEach items="${COMPANY_LIST }" var="i" varStatus="status">
									<option value="${i.companyCode}" data="${i.companyName}">${i.companyCode}</option>
								</c:forEach>
							</c:if>
						</select> 
					</div>
					</c:when>
					<c:otherwise>
						<div class='layui-input-block'>
							<input id='companyN' data-value='${CURR_COMPANY_NAME}' value="${CURR_COMPANY_CODE}" type='text' class='layui-input' readonly>
						</div>
					</c:otherwise>
					</c:choose>
					
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label label-div'>年度：</label>
					<div class='layui-input-block'>
						<input type='text' class='layui-input' id='select-year'>
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
					<label class='layui-form-label label-div'>财凭号：</label>
					<div class='layui-input-block'>
						<input type='text' id='wealthId' name='carNumber' class='layui-input' placeholder='非必填'>
					</div>
				</div>
			</div>
			<div class='layui-inline check-btn'>
				<button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
			</div>
		</div>
	</form>
	
	<hr>
	
	<form class='layui-form select-condition'>
	  <div class="layui-form-item" style="width:300px">
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>款未付：</label>
					<div class='layui-input-block'>
						<input type='text' id='unWealth' style='color:red' name='carNumber' class='layui-input' placeholder='暂无数据' readonly>
					</div>
				</div>
			</div>	    
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>现金收入：</label>
					<div class='layui-input-block'>
						<input type='text' id='CashIncome' name='carNumber' class='layui-input Vehicle-condition' placeholder='暂时不可输入数据' readonly>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>货到付款：</label>
					<div class='layui-input-block'>
						<input type='text' id='CashDelivery' name='carNumber' class='layui-input Vehicle-condition' placeholder='暂时不可输入数据' readonly>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item" style="width:300px">	
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>银行收入：</label>
					<div class='layui-input-block'>
						<input type='text' id='BankIncome' name='carNumber' class='layui-input Vehicle-condition' placeholder='暂时不可输入数据' readonly>
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>月结：</label>
					<div class='layui-input-block'>
						<input type='text' id='Monthly' name='carNumber' class='layui-input Vehicle-condition' placeholder='暂时不可输入数据' readonly>
					</div>
				</div>
			</div>
			
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>代收款：</label>
					<div class='layui-input-block'>
						<input type='text' id='Collection' name='carNumber' class='layui-input' placeholder='暂无数据' readonly>
					</div>
				</div>
			</div>
			
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>合计金额：</label>
					<div class='layui-input-block'>
						<input type='text' id='totalMoney' name='totalMoney' class='layui-input' readonly>
					</div>
				</div>
			</div>
		</div>
	</form>
	
	<div class='btn-operation'> 
	    <button class="layui-btn layui-btn-disabled toSave">保存</button>
	    <button class="layui-btn layui-btn-disabled" type='button' id='print'>打印运输受理单（套打）</button>
	    <button class="layui-btn layui-btn-disabled" type='button' id='printA4'>打印运输受理单（A4）</button> 
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
    <script src="${ctx_static}/transport/money/js/money.js?t=1"></script>
    
</body>
</html>