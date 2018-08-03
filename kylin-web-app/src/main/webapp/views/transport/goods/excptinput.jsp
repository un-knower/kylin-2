<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>货物异常录入</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/goods/css/excptinput.css?v=3"/>
</head>
<body>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>货物异常录入</a>
	</span>
	
	<hr>
	<div class="box_con clearfix">
		<form class='layui-form'>
		   <div class='layui-inline'>
				<label class='layui-form-label'>运单编号：</label>
				<div class='layui-input-inline'>
					<input type='text' name='orderId' lay-verify="orderId" class='layui-input' id='waybillId' placeholder='请输入运单编号' >
				</div>					
			</div>
			<div class="layui-inline">
			    <button class="layui-btn btnBox searchBtn"  lay-submit lay-filter="searchBtn" id="searchBtn">查询</button>			     
		    </div> 
		</form>
	</div>
	<form class='layui-form formContent'>
	   <div class="formBox">
		    <div class="layui-inline ">
			     <label class="layui-form-label">交接人：</label>
			     <div class="layui-input-inline">
			         <input name="handover" id="handover"  autocomplete="off" readonly class="layui-input" type="text">
			     </div>
		     </div>
		     <div class="layui-inline ">
			     <label class="layui-form-label">接收人：</label>
			     <div class="layui-input-inline">
			         <input name="sendee" id="sendee"   autocomplete="off" class="layui-input" type="text">
			     </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">发站：</label>
			     <div class="layui-input-inline">
			         <input name="fromStation" id="fromStation"  autocomplete="off" readonly  class="layui-input" type="text">
			     </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">到站：</label>
			     <div class="layui-input-inline">
			         <input name="toStation" id="toStation" autocomplete="off"  readonly  class="layui-input" type="text">
			     </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">客户名称：</label>
			     <div class="layui-input-inline">
			         <input name="customer" id="customer"  autocomplete="off" readonly  class="layui-input" type="text">
			     </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">客户单号：</label>
			     <div class="layui-input-inline">
			         <input name="customerId" id="customerId"  autocomplete="off" readonly  class="layui-input" type="text">
			     </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">车牌号：</label>
			     <div class="layui-input-inline layui-form-select">
		               <input type="text" name="carId" id="carId"   autocomplete="off" class="layui-input selectInput" value=""/>
			           <i class="layui-edge"></i>
			           <ul class="selectUl"></ul>
		         </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">司机姓名：</label>
			     <div class="layui-input-inline">
			         <input name="driverName" id="driverName" readonly lay-verify="" class="layui-input" type="text">
			     </div>
		     </div>
		      <div class="layui-inline ">
			     <label class="layui-form-label">联系方式：</label>
			     <div class="layui-input-inline">
			         <input name="driverPhone" id="driverPhone" readonly lay-verify="" class="layui-input" type="text">
			     </div>
		     </div>
		     <div class="layui-inline">
		         <label class="layui-form-label"><em>*</em>环节：</label>
		         <div class="layui-input-inline selectBox layui-form-select">
		            <select name="tache" id="tache" lay-verify="required" value="" >
		                <option value="">请选择</option>
		                <option value="5">出库</option>
		                <option value="1">取货</option>
		                <option value="2">收货</option>
		                <option value="6">送提货</option>
		                <option value="3">装车</option>
		            </select>
		         </div>
			 </div>
			 <div class="layui-inline">
		        <label class="layui-form-label"><em>*</em>发生地点：</label>
		        <div class="layui-input-inline manage-wrapper">
		            <input name="address" type="text" class="layui-input dizhi_city" readonly id="address" lay-verify="address" value="" >
		        </div>
		     </div>
		     <div class="layui-inline">
		         <label class="layui-form-label"><em>*</em>发生日期 ： </label>
		         <div class="layui-input-inline">
		              <input class="layui-input" name="startDate"  id="startDate" lay-verify="required" readonly type="text">
		         </div>
		    </div>
		</div>     
	    <table class='layui-table'>
		  <thead>
			<tr>
			    <th style="width:2%;text-align:center">编号</th>
				<th style="width:5%;text-align:center">品名</th>
				<th style="width:3%;text-align:center">包装</th>
				<th style="width:3%;text-align:center">重量 (吨)</th>
				<th style="width:4%;text-align:center">体积 (立方)</th>
				<th style="width:3%;text-align:center">件数 (件)</th>
				<th style="width:3%;text-align:center">短少 (件)</th>
				<th style="width:3%;text-align:center">破损 (件)</th>
				<th style="width:3%;text-align:center">湿损 (件)</th>
				<th style="width:3%;text-align:center">污染 (件)</th>
				<th style="width:3%;text-align:center">冻损 (件)</th>
				<th style="width:3%;text-align:center">变质 (件)</th>
				<th style="width:3%;text-align:center">延时 (件)</th>
				<th style="width:3%;text-align:center">串货 (件)</th>
				<th style="width:3%;text-align:center">其它 (件)</th>
				<th style="width:4%;text-align:center">异常数量 (件)</th>
				<th style="width:4%;text-align:center">残值数量 (件)</th>
				<th style="width:8%;text-align:center">异常描述</th>
				<th style="width:3%;text-align:center">操作</th>
			</tr>
		</thead>
		<tbody id="showcarInformationList"></tbody>
	  </table> 
	  <input type="hidden" value="" id="ydbhid">
      <div class="layui-form-item" style="text-align:center">
        <button class="layui-btn layui-btn-normal" lay-submit="" id="btn_sub" lay-filter="btn_sub">保存</button>
      </div>
	</form>
    <%@ include file="/views/transport/include/floor.jsp"%>   
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script src="${ctx_static}/transport/excelRead/js/jquery.min.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script>
    <script src="${ctx_static}/transport/excelRead/js/jsrender-min.js"></script>
    <script type="text/x-jsrender" id='tableList'>
		<tr>
			<td></td>
			<td>{{:pinMing}}</td>
            <td>{{:packing}}</td>
            <td>{{:weight}}</td>
            <td>{{:volume}}</td>
            <td>{{:piece}}</td>
            <td><input name="lack" class="lack inpNum" value="0" autocomplete="off" type="text" ></td>
            <td><input name="damage"  class="damage inpNum" value="0"  autocomplete="off" type="text"></td>
            <td><input name="wet"  class="wet inpNum" value="0"  autocomplete="off" type="text"></td>
            <td><input name="pollute"  class="pollute inpNum" value="0" autocomplete="off"  type="text"></td>
            <td><input name="freeze"  class="freeze inpNum" value="0" autocomplete="off" type="text"></td>
            <td><input name="deterioration"  class="deterioration inpNum" value="0" autocomplete="off"  type="text"></td>
            <td><input name="delayed "   class="delayed inpNum" value="0" autocomplete="off" type="text"></td>
            <td><input name="errorGoods"  class="errorGoods inpNum" value="0" autocomplete="off" type="text"></td>
            <td><input name="other"  value="0" class="other inpNum" autocomplete="off"  type="text"></td>
            <td><span class="abnormalNum">0</span></td>
            <td><input name="incomplete"  value="0" class="inpNum incomplete" autocomplete="off" type="text"></td>
            <td><textarea class="layui-textarea description" name="description "  value="" class="description" lay-verify="required" ></textarea></td>
            <td><img src="/kylin/static/transport/common/images/icon-remove.png" class="imgBox" onclick="del_table(this)"></td>
		</tr>
	</script>
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script src="${ctx_static}/transport/goods/js/area.js"></script> 
    <script src="${ctx_static}/transport/goods/js/provinceSelect.js"></script> 
    <script type="text/javascript">
       var timestamp = (new Date()).valueOf();  //获取当前时间戳
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;
    		laydate.render({
    			elem: '#startDate'
    			,type: 'datetime'
    			,theme: '#1E9FFF'
    			,max: timestamp
    		});
    		//自定义验证规则
    		  form.verify({
    			  orderId: function(value){
    		      if(value.length <= 0){ 		    	  
    		        return '运单编号不能为空';
    		      }
    		    }
    		  });    		
    	}) 
    </script>
    <script src="${ctx_static}/transport/goods/js/excptinput.js?v=4"></script> 
</body>
</html>