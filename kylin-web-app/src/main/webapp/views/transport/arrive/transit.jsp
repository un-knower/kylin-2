<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>货物在途情况</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
</head>
<body>
<style type="text/css">
   [v-cloak] {display: none;}
</style>
<div id="rrapp" v-cloak>
   <form class='layui-form' style="margin: 20px auto">
	 	<table class='layui-table tabBox' style="width:98%;margin:0 auto 20px auto">
		<thead>
			<tr>
				<th style="width:8%;">运单号</th>
				<th style="width:15%;">时间</th>
				<th style="width:10%;">操作类型</th>
				<th style="width:15%;">地点</th>
				<th style="width:10%;">操作人</th>
				<th style="width:15%;">备注</th>
			</tr>
		</thead>
		<tbody>
		   <tr class="s_addTr" v-for="item in orderList">
		      <td>{{item.ydbhid}}</td>
		      <td>{{item.shijian}}</td>
		      <td>{{item.cztype}}</td>
		      <td>{{item.gs}}</td>
		      <td>{{item.grid}}</td>
		      <td>{{item.beizhu}}</td>
		   </tr>		   
		</tbody>
	</table>  
    </form>
	<input type="hidden" id="orderId" value="">		
	
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    var currTime="";
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <script type="text/javascript" src="${ctx_static}/transport/transit/js/transit.js?v=0"></script>

</body>
</html>