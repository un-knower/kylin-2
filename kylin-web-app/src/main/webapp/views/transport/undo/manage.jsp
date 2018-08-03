<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>撤销装载</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/undoManage/css/manage.css?v=3"/>
</head>
<body>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>撤销装载</a>
	</span>
	<hr>
	<div class="box_con">
		<form class='layui-form'>
			<div class='layui-form-item'>
			   <div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>运单编号：</label>
						<div class='layui-input-inline'>
							<input type='text' name='orderId' lay-verify="required" class='layui-input' id='waybillId' autocomplete="off" placeholder='请输入运单编号' >
						</div>
					</div>					
				</div>
				<div class='layui-inline'>
				     <button class="layui-btn"  lay-submit=""  lay-filter="searchBtn" id="searchBtn">查询</button>
				</div>
			 </div>
		</form>
	</div>
	<form class='layui-form'>	
	 	<table class='layui-table'>
			<thead>
				<tr>
				    <th style="width:2%;" class="tdCheck"><input name="checkAll"  lay-skin="primary" type="checkbox" id="checkAll"></th>
					<th style="width:12%;">运单号</th>
					<th style="width:8%;">车牌号</th>
					<th style="width:8%;">发车时间</th>
					<th style="width:8%;">到站状态</th>
					<th style="width:8%;">装车发站</th>
					<th style="width:8%;">装车到站</th>
					<th style="width:8%;">始发站</th>
					<th style="width:8%;">目的站</th>
					<th style="width:8%;">提干配</th>
					<th style="width:8%;">品名</th>
					<th style="width:8%;">件数（件）</th>
					<th style="width:8%;">重量（吨）</th>
					<th style="width:8%;">体积（立方）</th>
				</tr>
			</thead>
			<tbody id="showcarInformationList"></tbody>
		</table> 
		<input type="hidden" val="" id="ydbhid">
		<div class="layui-form-item" style="text-align:center">
			<shiro:hasPermission name="undo:loading:delete"> 
				   <button class="layui-btn layui-btn-normal"  lay-submit=""  lay-filter="btn_sub" id="btn_sub">撤销装载</button>
			 </shiro:hasPermission>
		    
	    </div>
	</form>
    <%@ include file="/views/transport/include/floor.jsp"%>   
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/jsrender-min.js"></script>
    <script type="text/x-jsrender" id='tableList'>
		<tr class="tabTr">
            <td>
               <div class="layui-form-item">
                      <input name="checks" lay-skin="primary"  type="checkbox">
               </div>
            </td>
            <td dataId="{{:xuhao}}">{{:ydbhid}}</td>
            <td>{{:chxh}}</td>
            <td class="timeBox">{{:fchrq}}</td>
            <td>{{:ydzh==1?'已到站':'未到站'}}</td>
            <td>{{:fazhan}}</td>
            <td>{{:daozhan}}</td>
            <td>{{:ydbeginplace}}</td>
            <td>{{:ydendplace}}</td>
			<td>
				{{:iType==0?'干线':(iType==1?'配送':(iType==2?'提货':''))}}{{:transferFlag=='中转'?'中转':(transferFlag!=null?transferFlag:'')}}
			</td>
            <td>{{:pinming}}</td>
            <td>{{:jianshu}}</td>
            <td>{{:zhl}}</td>
            <td>{{:tiji}}</td>
       </tr>
	</script>
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    	}) 
    </script>
    <script src="${ctx_static}/transport/undoManage/js/manage.js?v=4"></script> 
</body>
</html>