<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>录入装载清单数据</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/bundle/css/expenditure.css?v=1"/>
</head>
<body>
   <span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>&gt;
		<a href='javaScript:void(0);'>装载清单录入</a>
   </span>
   <div class="com_box clearfix">
	  <form class='layui-form'>
	    <!-- part1 -->
	     <div class="part_box">
		      <div class="layui-input-inline radioBox">
			      <label class="layui-form-label">装载方式：</label>
			      <div class="layui-input-inline radio-div">
			          <input name="zType" value="2" title="提货装载" id="transTh" class="transType" type="radio" lay-filter='th-status'>
			          <input name="zType" value="0" title="干线中转" id="transGx" class="transType" checked="" type="radio" lay-filter='gx-status'>
			          <input name="zType" value="1" title="配送运输" id="transPs" class="transType" type="radio" lay-filter='ps-status'>
			      </div>
			  </div>
			  <div class="layui-input-inline  radioBox" >
			      <label class="layui-form-label">承运方式 ：</label>
			      <div class="layui-input-inline radio-div">
			          <input name="cType" value="外线" title="外线" checked="" type="radio" lay-filter='outline-status'>
			          <input name="cType" value="外车" title="外车" type="radio" lay-filter='waiche-status'>
			          <input name="cType" value="自有" title="自有车" type="radio" lay-filter='ziyou-status'>
			      </div>
			  </div>
			  <div class="layui-input-inline  radioBox">
			      <label class="layui-form-label font16">运输类型 ：</label>
			      <div class="layui-input-inline radio-div">
			          <input name="dType" class="transStatus" value="整车" title="整车运输" checked="" type="radio" lay-filter='full-status'>
			          <input name="dType" class="transStatus" value="零担" title="零担运输" type="radio"  lay-filter='less-status'>
			      </div>
			  </div>
		  </div>
		  <!-- part2 -->
	      <div class="part_box">
			  <h4>外线信息</h4>
		      <div class="layui-form-item">
			    <div class="layui-inline ">
			      <label class="layui-form-label"><em>*</em>外线名称：</label>
			      <div class="layui-input-inline selectBox">
			        <input name="outlineName" id="outlineName" lay-verify="required" autocomplete="off" class="layui-input selectInput" type="text">
			         <ul class="selectUl">
			        </ul>
			      </div>
			    </div>
			    <div class="layui-inline">
			      <label class="layui-form-label"><em>*</em>联系人： </label>
			      <div class="layui-input-inline">
			        <input name="userName" lay-verify="required" id="userName" autocomplete="off" class="layui-input" type="text">
			      </div>
			    </div>
			    <div class="layui-inline">
			      <label class="layui-form-label"><em>*</em>联系电话： </label>
			      <div class="layui-input-inline">
			        <input name="userPhone" id="userPhone" lay-verify="required" autocomplete="off" class="layui-input" type="text">
			      </div>
			    </div>
			    <div class="layui-inline">
			      <label class="layui-form-label">外线单号： </label>
			      <div class="layui-input-inline">
			        <input name="outlineOrder" id="outlineOrder"  autocomplete="off" class="layui-input" type="text">
			      </div>
			    </div>
		     </div>
          </div>
	  	  <!-- part3 -->
	      <div class="part_box">
			  <h4>车辆信息</h4>
		      <div class="layui-form-item">
			    <div class="layui-inline">
			      <label class="layui-form-label"><em>*</em>车牌号：</label>
			      <div class="layui-input-inline selectBox">
			        <input name="carId"  id="carId" lay-verify="required" autocomplete="off" class="layui-input selectInput" type="text">
			        <ul class="selectUl">
			        </ul>
			      </div>
			    </div>
			    <div class="layui-inline">
			      <label class="layui-form-label">司机姓名： </label>
			      <div class="layui-input-inline">
			        <input name="driverName" id="driverName"  autocomplete="off" class="layui-input" type="text">
			      </div>
			    </div>
			    <div class="layui-inline">
			      <label class="layui-form-label">司机电话： </label>
			      <div class="layui-input-inline">
			        <input name="driverPhone" id="driverPhone" lay-verify="driverPhone"  autocomplete="off" class="layui-input" type="text">
			      </div>
			    </div>
		     </div>
          </div>
	      <!-- part4 -->
	      <div class="part_box">
			  <h4>运输信息</h4>
		      <div class="layui-form-item">
		        <!-- spart1 -->
		        <div class="sPart" >
		           <div class="layui-input-inline" style="width:295px">
				       <label class="layui-form-label"><em>*</em>是否中转：</label>
				       <div class="layui-input-inline radio-div">
				          <input name="transfer" value="中转" title="中转" id="transfer" checked="" type="radio" lay-filter="transfer">
				          <input name="transfer" value="不中转" title="不中转" id="direct"  type="radio" lay-filter="direct">
				       </div>
			        </div>
			        <div class="subPart">
			           <div class="layui-inline">
				          <label class="layui-form-label"><span class="fromStation">起运网点：</span></label>
				          <div class="layui-input-inline">
				             <input name="fromStation" id="fromStation" lay-verify="fromStation"  autocomplete="off"  class="layui-input selectInput pointStation" type="text"  value = "">
				              <ul class="selectUl qywdUl">
						       </ul>
				          </div>
				        </div>				   
				        <div class="layui-inline">
					        <label class="layui-form-label"><em>*</em>中转站 ： </label>
					        <div class="layui-input-inline selectBox">
					           <input name="station" id="station" lay-verify="station" autocomplete="off" class="layui-input selectInput" type="text">
					           <ul class="selectUl">
						       </ul>
					        </div>
				        </div>
				        <div class="layui-inline">
					        <label class="layui-form-label">中转网点 ： </label>
					        <div class="layui-input-inline selectBox layui-form-select">
					           <input name="getPoint" id="getPoint" lay-verify="getPoint"  autocomplete="off" class="layui-input selectInput zzStatin" type="text">
					           <i class="layui-edge tips_point"></i>
					           <ul class="selectUl zzwdUl">
						       </ul>
					        </div>
				        </div>
			         </div>
		        </div>
		        	
			    <!-- spart2 -->
		        <div class="sPart" style="display:none">
		            <div class="layui-inline">
				      <label class="layui-form-label"><span class="qyStation">起运网点：</span></label>
				      <div class="layui-input-inline">
				         <input name="qyStation" id="qyStation"  autocomplete="off" lay-verify="fromStation" class="layui-input selectInput pointStation" type="text"  value = "">
				          <ul class="selectUl qywdUl">
						  </ul>
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label"><em>*</em><span class="fromAddress">发站：</span></label>
				      <div class="layui-input-inline">
				         <input name="fromAddress" id="fromAddress" lay-verify="" autocomplete="off" readonly class="layui-input" type="text"  value = "${company}">
				        <!-- <input name="s_fromAddress" id="s_fromAddress" lay-verify="" autocomplete="off" class="layui-input" type="text"  value ="" style="display:none"> -->
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label"><em>*</em><span class="toAddress">到站：</span> </label>
				      <div class="layui-input-inline">
				         <input name="toAddress" id="toAddress" lay-verify="" autocomplete="off" class="layui-input" type="text">
				        <!--  <input name="s_toAddress" id="s_toAddress" lay-verify="" autocomplete="off" class="layui-input " type="text" style="display:none"> -->
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label"><span class="toStation">到达网点：</span></label>
				      <div class="layui-input-inline">
				         <input name="toStation" id="toStation" autocomplete="off"  lay-verify="getPoint" class="layui-input selectInput zzStatin" type="text"  value = "">
				         <i class="layui-edge"></i>
			             <ul class="selectUl zzwdUl">
				         </ul>
				      </div>
				    </div>
			    </div>
			    <div class="layui-inline">
			       <label class="layui-form-label"><em>*</em>发车时间 ： </label>
			       <div class="layui-input-inline">
			          <input name="startDate" id="startDate" lay-verify="required"  readonly autocomplete="off" class="layui-input" type="text">
			       </div>
			    </div>
			    <div class="layui-inline">
			       <label class="layui-form-label"><em>*</em>到达时间 ： </label>
			       <div class="layui-input-inline">
			          <input name="endDate" id="endDate" lay-verify="required"  readonly autocomplete="off" class="layui-input" type="text">
			       </div>
			    </div>
			    <div class="layui-inline">
			       <label class="layui-form-label"><em>*</em>运输成本： </label>
			       <div class="layui-input-inline">
			          <input name="qdCost" id="qdCost" lay-verify="number" autocomplete="off" class="layui-input" type="text">
			       </div>
			    </div>
			    <div class="layui-inline">
			       <label class="layui-form-label">其他成本 ： </label>
			       <div class="layui-input-inline">
			          <input name="otherCost" id="otherCost"  autocomplete="off" class="layui-input" type="text">
			       </div>
			    </div>
			    <div class="layui-inline transTatus" >
			        <label class="layui-form-label"><em>*</em>运输方式：</label>
			        <div class="layui-input-inline selectBox layui-form-select">
			          <!--  <select name="modules" id="selectBox" lay-verify="required" value="汽运" lay-search="">
			           </select> -->
			           <input type="text" name="selectBox" id="selectBox" lay-verify="selectBox" autocomplete="off"  class="layui-input selectInput"  />
			           <i class="layui-edge tips_type"></i>
			           <ul class="selectUl ysUl"></ul>
			        </div>
			    </div>
		     </div> 
	     	 <div class="layui-inline">
		        <label class="layui-form-label"><em></em>运单编号： </label> 
		        <div class="layui-input-inline">
		           <input name="orderId" id="orderId"  autocomplete="off" class="layui-input" type="text">
		        </div>
		     </div>
		     <div class="layui-inline">
		        <a class="layui-btn layui-btn-normal" href="javascript:void(0)" id="appendTab">+添加</a>
		     </div>
		     
		      <!-- <div class="layui-inline">
		        <a class="layui-btn layui-btn-normal" href="javascript:void(0)" id="generateCost">生成成本</a>
		     </div> -->
		 </div>
		 <table class='layui-table tabBox'>
			<thead>
				<tr>
					<th style="width:3%;text-align:center">序号</th>
					<th style="width:7%;text-align:center">运单编号</th>
					<th style="width:7%;text-align:center">收货人省市区</th>
					<th style="width:7%;text-align:center">客户名称</th>
					<th style="width:7%;text-align:center">客户地址</th>
					<th style="width:4%;text-align:center">提货方式</th>
					<th style="width:3%;text-align:center">序号</th>
					<th style="width:4%;text-align:center">装载编号</th>
					<th style="width:5%;text-align:center">品名</th>
					<th style="width:5%;text-align:center">重量(t)</th>
					<th style="width:5%;text-align:center">装车重量(t)</th>
					<th style="width:3%;text-align:center">件数</th>
					<th style="width:5%;text-align:center">装车件数</th>
					<th style="width:5%;text-align:center">体积(m³)</th>
					<th style="width:6%;text-align:center">装车体积(m³)</th>
					<th style="width:4%;text-align:center">操作</th>
				</tr>
			</thead>
		    <tbody id="showInfoList"></tbody>
	    </table> 
	     <!--表格部分开始-->
        <table id="DataGrid" class="DataTab layui-table" cellspacing="0" style="width: 100%; ">
          <tbody class="operationListApend"></tbody>
          <tbody class="operationList" style="display: none;">
            <tr class="tabTr">
                <td style="width:3%;text-align:center"></td>
                <td style="width:7%;text-align:center">_orderId</td>
				<td style="width:7%;text-align:center">_address</td>
				<td style="width:7%;text-align:center">_company</td>
				<td style="width:7%;text-align:center">_userAddress</td>
				<td style="width:4%;text-align:center">_tpye</td>
				<td style="width:3%;text-align:center"><ul class="subNum">_num</ul></td>
				<td style="width:4%;text-align:center"><ul class="subXuhao">_xuhao</ul></td>
				<td style="width:5%;text-align:center"><ul class="subGoods">_goods</ul></td>
				<td style="width:5%;text-align:center"><ul class="subWt">_weight</ul></td>
				<td style="width:5%;text-align:center"><ul class="subWts">_carWeight</ul></td>
				<td style="width:3%;text-align:center"><ul class="subCount">_count</ul></td>
				<td style="width:5%;text-align:center"><ul class="subCounts">_carCount</ul></td>
				<td style="width:5%;text-align:center"><ul class="subVolume">_volume</ul></td>
				<td style="width:6%;text-align:center"><ul class="subVolumes">_carVolume</ul></td>
				<td style="width:4%;text-align:center">_img</td>
            </tr>
          </tbody>
        </table>
        <div class="layui-form-item" style="text-align:center">
	        <button class="layui-btn layui-btn-normal" lay-submit=""id="btn_sub"  lay-filter="save">保存</button>
	        <button class="layui-btn layui-btn-primary toDelete" type="reset"  lay-filter="">清空数据</button>
         </div>
	  </form>
	  
	</div> 


<script type="text/javascript">
	var base_url = '${base_url}';
	
</script>
<%@ include file="/views/transport/include/floor.jsp"%>
<script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script>
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script type="text/javascript">
	var allDaozhan = '${ALL_DAOZHAN_BY_COMPANY}';
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;  
    		//自定义验证规则
  		  form.verify({
  			  driverPhone: function(value){
  		      if(value.length > 0){ 
  		    	  var re = /^1(3|4|5|7|8)\d{9}$/;
  		    	  if(!(re.test(value))){ 
  		            return "手机号格式错误，请重新输入"; 
  		        } 
  		      }
  		    }
  		  });    		
      })
 </script>
<script src="${ctx_static}/transport/bundle/js/insert.js?v=2"></script>
<script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
</body>
</html>