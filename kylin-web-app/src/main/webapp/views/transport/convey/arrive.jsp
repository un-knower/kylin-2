<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>到货</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/arrive/css/arrive.css"/>
</head>
<body>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>到货</a>
	</span>
	
	<hr>
	
	<form class='layui-form check-form'>
		<div class='layui-form-item' style="min-width: 712px;">
			<div class='layui-input-inline radio-div'>
				<input type = 'radio'  name='typical' value = '整车' title = '整车' checked='' id='Vehicle' lay-filter='Vehicle-status'>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>发车日期</label>
					<div class='layui-input-block'>
						<input type='text' name='date' class='layui-input Vehicle-condition clear-input' id='carDate' placeholder="请输入">
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>车牌号</label>
					<div class='layui-input-block'>
						<input type='text' name='carNumber' id='carId' class='layui-input Vehicle-condition clear-input' placeholder='请输入'>
					</div>
				</div>
			</div>
		</div>
		<div class='layui-form-item' style='margin-bottom: 0px'>
			<div class='layui-input-inline radio-div'>
				<input type = 'radio' name='typical' value = '单票' title = '单票' lay-filter='single-status'>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>运单编号</label>
					<div class='layui-input-block'>
						<input type='text' name='date' class='layui-input clear-input' id='waybillId' placeholder='未选择单票时不能输入' disabled=''>
					</div>
				</div>
			</div>
		</div>
	</form>
	
	<div class='btn-group'>
		<button class='layui-btn toArrive'>查询到货</button>
		<!-- <button class='layui-btn layui-btn-normal toSave'>确认收货</button> -->
		<button class='layui-btn layui-btn-warm toDelete'>清空数据</button>
		
		<button class='layui-btn toArrive00' onclick="batchGoodArrive()" >批量到货</button>
		<!-- 
		<button class='layui-btn toArrive11' onclick="batchRepealGoodArrive()" >批量撤销到货</button>
		 -->
	</div>
	
	<form class='layui-form select-condition'  lay-filter='arriv-time' >
			<div class='layui-form-item'>
				<label class="layui-form-label">到货仓库</label>
				<div class='layui-input-block'>
					<select  placeholder='请先查询' class='check-arrive' id='select-storeHouse'>
						  <option value="">直接选择或搜索选择</option>
					</select>
				</div>
			</div>
			<div class='layui-form-item'>
				<label class="layui-form-label">到货时间</label>
				<div class='layui-input-block'>
					<input type='text' name='date' class='layui-input clear-input check-arrive' id='arriveDate' disabled='' placeholder='请先查询'>
				</div>
			</div>
	</form>
	
	<hr>
	
	<table class='layui-table layui-form' lay-filter='table-data'>
		<colgroup>
		    <col width='3%'>
			<col width='7%'>
			<col width='7%'>
			<col width='6%'>
			<col width='6%'>
			<col width='5%'>
			<col width='5%'>
			<col width='5%'>
			<col width='6%'>
			<col width='8%'>
			<col width='8%'>
			<col width='6%'>
			<col width='6%'>
			<col width='5%'>
			<col width='5%'>	
			<col width='8%'>			
		</colgroup>
		<thead>
			<tr>
			    <td class="tdCheck"><input name="checkAll"  id="checkAll" type="checkbox" lay-skin="primary"></td>
				<td>车牌号</td>
				<td>运单号</td>
				<td>序号</td>
				<td>品名</td>
				<td>件数</td>
				<td>重量</td>
				<td>体积</td>
				<td>始发站</td>
				<td>目的站</td>
				<td>到货时间</td>
				<td>装车发站</td>
				<td>装车到站</td>
				<td>状态</td>
				<td>提干配</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="showcarInformationList"></tbody>
	</table>
	<input type="hidden" value="" id="startDate">
	<input type="hidden" value="" id="s_carId">
	<input type="hidden" value="" id="orderId">
	<input type="hidden" value="" id="searchType">
	<input type="hidden" value="" id="xuhao">
	<div class="save-success" style="text-align: center; margin-top: 9%; display: none;">
       <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
       <span class="no-data-copy">确认收货成功！</span>
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
               <div class="layui-form-item">
                   <input name="checks" lay-skin="primary" type="checkbox">
               </div>
            </td>
			<td rowspan = {{:conveyLen}} class='carBrand'>
				<span>{{:chxh}}</span>
			</td>
			{{for conveys}}
				{{if #getIndex() == 0}}
					<td rowspan = {{:detail.length}}>
						<span class='ydbihd'>{{:ydbhid}}</span>
					</td>

					{{for detail}}
						{{if  #getIndex() == 0 }}
							<td>
								<span class="xuhao">{{:xuhao}}<span>
							</td>
							<td>
								<span>{{:pinming}}<span>
							</td>
							<td>
								<span>{{:jianshu}}<span>
							</td>
							<td>
								<span>{{:zhl}}<span>
							</td>
							<td>
								<span>{{:tiji}}<span>
							</td>
						{{/if}}
					{{/for}}
					<td rowspan = {{:detail.length}}>
						<span>{{:beginPlace}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:endPlace}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:dhsj}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:fazhan}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:daozhan}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:isArrived}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:iType==0?'干线':(iType==1?'配送':(iType==2?'提货':''))}}
							{{:transferFlag=='中转'?'中转':(transferFlag!=null?transferFlag:'')}}
						</span>
					</td>
                   {{if  isArrived == "未到站" }}
                       <td rowspan = {{:detail.length}}>
					    	<button class='layui-btn layui-btn-normal' onclick='arrival(this)'>确认到货</button>
				    </td>
                    {{/if}}
                    {{if  isArrived == "已到站" }}
                       <td rowspan = {{:detail.length}}>
					    	<button class='layui-btn layui-btn-normal' onclick="revoke(this,'{{:dhsj}}')">撤销到货</button>
				        </td>
                    {{/if}}
				{{/if}}
			{{/for}}
		</tr>
		{{for conveys}}
			{{if #getIndex() > 0}}
				<tr>
                    <td><input name="checks" lay-skin="primary" type="checkbox"></td>
					<td rowspan = {{:detail.length}}>
						<span class='ydbihd'>{{:ydbhid}}</span>
					</td>
					{{for detail}}
						{{if  #getIndex() == 0 }}
							<td>
								<span class="xuhao">{{:xuhao}}<span>
							</td>
							<td>
								<span>{{:pinming}}<span>
							</td>
							<td>
								<span>{{:jianshu}}<span>
							</td>
							<td>
								<span>{{:zhl}}<span>
							</td>
							<td>
								<span>{{:tiji}}<span>
							</td>
						{{/if}}
					{{/for}}
					<td rowspan = {{:detail.length}}>
						<span>{{:beginPlace}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:endPlace}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:dhsj}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:fazhan}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:daozhan}}</span>
					</td>
					<td rowspan = {{:detail.length}}>
						<span>{{:isArrived}}</span>
					</td>
                   {{if  isArrived == "未到站" }}
                       <td rowspan = {{:detail.length}}>
					    	<button class='layui-btn layui-btn-normal' onclick='arrival(this)'>确认到货</button>
				    </td>
                    {{/if}}
                    {{if  isArrived == "已到站" }}
                       <td rowspan = {{:detail.length}}>
					    	<button class='layui-btn layui-btn-normal' onclick="revoke(this,'{{:dhsj}}')">撤销到货</button>
				        </td>
                    {{/if}}
				</tr>
			{{/if}}

			{{if detail.length > 0}}
				{{for detail}}
					{{if  #getIndex() > 0 }}
						<tr>
                           <td>
              				 <div class="layui-form-item">
                               <input name="checks" lay-skin="primary"  type="checkbox">
                             </div>
                            </td>
							<td>
								<span class="xuhao">{{:xuhao}}<span>
							</td>
							<td>
								<span>{{:pinming}}<span>
							</td>
							<td>
								<span>{{:jianshu}}<span>
							</td>
							<td>
								<span>{{:zhl}}<span>
							</td>
							<td>
								<span>{{:tiji}}<span>
							</td>
						</tr>
					{{/if}}
				{{/for}}
			{{/if}}
				
		{{/for}}
	</script>
	<script type="text/x-jsrender" id='optionsList'>
		<option>{{:ckmc}}</option>
	</script>
    <script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
    <script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;
    		laydate.render({
    			elem: '#carDate'
    			,theme: '#393D49'
    		});
    		laydate.render({
    			elem: '#arriveDate'
    			,type: 'datetime'
    			,theme: '#393D49'
    		});
    	})
    </script>
    <script src="${ctx_static}/transport/excelRead/js/table.js"></script>
    <script src="${ctx_static}/transport/arrive/js/public.js"></script>
    <script src="${ctx_static}/transport/arrive/js/arrive.js?v=2521"></script>
</body>
</html>