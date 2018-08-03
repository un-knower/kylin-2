<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>签收</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/receivesign/css/receivesign.css?t=1"/>
</head>
<body style='overflow-x: hidden;'>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>签收</a>
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
		    <button class="layui-btn layui-btn toCheck" lay-submit lay-filter='toCheck' id='check'>查询</button>
		    <!-- <button class="layui-btn layui-btn-warm toSave" lay-submit  lay-filter='save'>保存</button> -->
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
				<col width='10%'>
				<col width='8%'>
				<col width='8%'>
				<col width='10%'>
				<col width='20%'>
				<col width='10%'>
				<col width='8%'>			
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
					<th>撤销次数</th>
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
	
	<form class='layui-form layui-from-pane select-condition'>
			<div class='layui-form-item'>
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'>破损件数：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='psjianshu' id='damageNum' lay-verify='num' class='layui-input Vehicle-condition waybill-info' placeholder='请先查询运单编号' readonly>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'>短少件数：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='dsjianshu' lay-verify='num' id='shortNum' class='layui-input Vehicle-condition waybill-info' placeholder='请先查询运单编号' readonly>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'>录入人：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='lrr' lay-verify='entryPerson' id='lururen' class='waybill-info layui-input' placeholder='暂无数据' readonly>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'>录入时间：</label>
						<div class='layui-input-block'>
							<input type='text' id='lrTime' data-value='lrTime'  class='layui-input waybill-info' placeholder='暂无数据' readonly>
						</div>
					</div>
				</div>
			</div>
			<div class='layui-form-item'>
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'><span class="must-item"></span>&nbsp;签收人：</label>
						<div class='layui-input-block'>
							<input type='text' data-value='qsr' id='signPeople' name='carNumber' lay-verify='required|signPerson' class='layui-input Vehicle-condition waybill-info' placeholder='请先查询运单编号' readonly>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'>签收人电话：</label>
						<div class='layui-input-block'>
							 <input type="tel" data-value='qsrphone' id='signPhone' name="phone" lay-verify="phoneNumber" autocomplete="off" class="layui-input Vehicle-condition waybill-info" placeholder='请先查询运单编号' readonly>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'><span class="must-item"></span>&nbsp;签收状态：</label>
						<div class='layui-input-block'>
							 <input type="tel"  data-value='qszt'  id='sign-state' class="layui-input check-arrive Vehicle-condition waybill-info">
							<select  placeholder='请先查询' id='sign-select' data-value='qszt0' class='check-arrive Vehicle-condition waybill-info' id='' placeholder='请先查询运单编号' readonly>
								<option value="">直接选择</option>
								<c:if test="${qsztList != NULL}">
									<c:forEach items="${qsztList }" var="i" varStatus="status">
										<option value="${i.number}">${i.zt}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				</div>
				
				<div class='layui-inline short-div'>
					<div class='input-div'>
						<label class='layui-form-label'><span class="must-item"></span>&nbsp;签收时间：</label>
						<div class='layui-input-block'>
							<input type='text' style='display: none;' data-value='qsTime' id='cancel-signTime' lay-verify='required' class='qsTime layui-input Vehicle-condition waybill-info' placeholder='请先查询运单编号' readonly>
							<input type='text' data-value='qsTime' id='signTime' lay-verify='required' class='qsTime layui-input Vehicle-condition waybill-info' placeholder='请先查询运单编号' readonly>
						</div>
					</div>
				</div>
			</div>
			
			<div class="layui-form-item layui-form-text toTips">
		    	<label class="layui-form-label beizhu">备注：</label>
		    	<div class="layui-input-block textarea-div">
		      		<textarea id='tips' data-value='comm' placeholder="请先查询运单编号" class="layui-textarea Vehicle-condition waybill-info" readonly></textarea>
		    	</div>
		    </div>
		    <button class="layui-btn layui-btn-warm toSave" lay-submit  lay-filter='save'>保存</button>
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
			<td>
				<span>{{:cxNumber}}</span>
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
    		 laydate.render({
    			elem: '#signTime'
    			,format: 'yyyy-MM-dd HH:mm' 
    			,type: 'datetime'
    			,theme: '#393D49'
    		});
    		 var editIndex = layedit.build('LAY_demo_editor');
    		 form.verify({
    		 	signPerson: [/^[\u4e00-\u9fa5]/, '签收人只能填写中文汉字且不能为空']
    		 	,phoneNumber: function(value){
    		 		var regexp = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
    		 		var phoneReg = /^1\d{10}$/;
    		 		if(value != ''){
    		 			if(!regexp.test(value)){
        		 			if(!phoneReg.test(value)){
        		 				return '只能输入手机号码或电话号码';
        		 			}
        		 		}
    		 		}
    		 	}
    		 	,num: function(value){
    		 		var Dregexp =  new RegExp("^[0-9]*$");
    		 		if(value != ''){
    		 			if(!Dregexp.test(value)){
    		 				return '只能填写数字'
    		 			}
    		 		}
    		 	}
    	     	
    		});
    	}) 
    </script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/receivesign/js/receivesign.js"></script>
    
</body>
</html>