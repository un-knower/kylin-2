<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>运单作废</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/abnormal/cancel/css/cancel.css"/>
</head>
<body style='overflow-x: hidden;'>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>运单作废</a>
	</span>
	
	<!-- <hr class='layui-bg-cyan'> -->
	<div class='major-content'>
	<form class='layui-form check-form'>
		<div class='layui-form-item check-waybill'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label' style='padding: 9px 15px 9px 0px;'>公司：</label>
					<c:choose>
					<c:when test = "${CURR_COMPANY_NAME eq '总公司'}">
					<select  class='' >
						<option value="">所有公司</option>
						<c:if test="${COMPANY_LIST != NULL}">
							<c:forEach items="${COMPANY_LIST }" var="i" varStatus="status">
								<option value="${i.companyCode}">${i.companyName}</option>
							</c:forEach>
						</c:if>
					</select> 
					</c:when>
					<c:otherwise>
						<div class='layui-input-block'>
							<input id='companyN' value="${CURR_COMPANY_NAME}" placeholder="${CURR_COMPANY_NAME}" type='text' class='layui-input' readonly>
						</div>
					</c:otherwise>
					</c:choose>
				</div>
			</div>
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
		    <button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
		</div>
	</form>
	
	<fieldset class="layui-elem-field layui-field-title">
		<legend>运单信息</legend>
	</fieldset>
	<hr>
	
	<form class='layui-form layui-from-pane select-condition'>
			<div class='layui-form-item waybills-info'>
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label check-information'>运单编号：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='psjianshu' id='damageNum' lay-verify='num' class='layui-input Vehicle-condition waybill-info' placeholder='暂无数据' readonly>
						</div>
					</div>
				</div>
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label check-information'>发站：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='dsjianshu' lay-verify='num' id='shortNum' class='layui-input Vehicle-condition waybill-info' placeholder='暂无数据' readonly>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div check-input'>
						<label class='layui-form-label check-information'>状态：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='lrr' lay-verify='entryPerson' id='waybillStatus' class='waybill-info layui-input' placeholder='暂无数据' readonly>
						</div>
					</div>
				</div>
				
			</div>
			<div class="layui-form-item layui-form-text toTips">
		    	<label class="layui-form-label beizhu">备注：</label>
		    	<div class="textarea-div">
		      		<textarea id='tips' data-value='comm' placeholder="暂无数据" class="layui-textarea waybill-info textarea-content" readonly></textarea>
		    	</div>
		    </div>
	</form>
		<div class='btn-div' style='text-align: center;'>
			<button class="layui-btn layui-btn-disabled toCancel" lay-submit  id='toVoid'>作废</button>
			<button class="layui-btn layui-btn-disabled toCancel" lay-submit  id='toCancel' style='margin-left: 100px;'>取消作废</button>
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
    <script type="text/javascript">
    	layui.use(['form','element'],function(){
    		var form = layui.form,
    			element = layui.element;
    	})
    </script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/abnormal/cancel/js/cancel.js"></script> 
    
</body>
</html>