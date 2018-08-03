<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>送货(派车)签收单</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?2"/>
   <style type="text/css">
   </style>
</head>
<body>
<style type="text/css">
   [v-cloak] {display: none;}
</style>
<div id='arrivellodiing'  v-cloak>
   <div class='print-page'>
			<div  class='list-top'>
				<h2 class='head-middle head-tag title send-title'>送货（派车）签收单</h2>
			</div>
			<div class='tihuoNum'>
				<p class='head-tag list-info' style='margin-left: 28%;'>公司：{{carOutInfo.gs}}</p>
				<p class='head-tag list-info'>NO.{{carOutInfo.id}}</p>
			</div>
			<input type="hidden" id="id" v-model="carOutInfo.id">
			<input type="hidden" id="gs" v-model="carOutInfo.gs">
			<div style='padding: 20px;'>
				<table class='list-table send-table'>
					<thead></thead>
					<tbody>
						<tr>
							<td :rowspan='rowspan' class='danzh'>单证号保存栏</td>
							<td>运输号码</th>
							<td colspan='3'>{{carOutInfo.yshm}}</td>
							<td>业务类型</td>	
							<td colspan='2'>{{carOutInfo.ywlx}}</td>
							<td style='width: 150px;'>单证员</td>
						</tr>
						<tr>
							<td>发货单位</td>
							<td colspan='3'>
								<!-- <input type='text' class='borderNone layui-input'/> -->
								{{carOutInfo.fhdw}}
							</td>
							<td>发货电话</td>	
							<td colspan='2'>
								{{carOutInfo.fhlxr}}
							</td>
							<td>{{carOutInfo.dzy}}</td>
						</tr>
						<tr>
							<td>收货单位</td>
							<td colspan='3'>
								{{carOutInfo.shdw}}
							</td>
							<td>收货人手机</td>	
							<td colspan='2'>
								{{carOutInfo.shlxr}}
							</td>
							<td>开单时间</td>
						</tr>
						<tr>
							<td>收货地址</td>
							<td colspan='3'>
								{{carOutInfo.shdz}}
							</td>
							<td>收货人电话</td>	
							<td colspan='2'>
								{{carOutInfo.shdh}}
							</td>
							<td>
								{{carOutInfo.kdTime}}
							</td>
						</tr>
						<tr>
							<td colspan='7' rowspan='6' style='position: relative;padding: 0;'>
								<table style='position: absolute;top: 0;border:none; width: 100%;'>
									<tbody>
										<tr>
											<td colspan='2' class="goodsTd1">货物名称</td>
											<td class="goodsTd">车牌号</td>
											<td class="goodsTd">件数</td>	
											<td class="goodsTd">重量</td>
											<td class="goodsTd">体积</td>
											<td class="goodsTd6">返单</td>
										</tr>
										<tr class='goodsDetailTr' v-for="item in goodsDetailInfo">
											<td colspan='2' class="goodsTdInfo1">{{item.hwmc}}</td>
											<td>{{item.ch}}</td>
											<td>{{item.js}}</td>	
											<td>{{item.zl}}</td>
											<td>{{item.tj}}</td>
											<td v-if="fd" class="goodsTdInfo2">是</td>
											<td v-if="!fd" class="goodsTdInfo2">否</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>计划送货时间</td>
						</tr>
						<tr>
							<td class="jhshTime">{{carOutInfo.jhshTime}}</td>
						</tr>
						<tr>
							<td>仓库提货时间</td>
						</tr>
						<tr>
							<td>{{carOutInfo.thTime}}</td>
						</tr>
						<tr>
							<td>装卸班组</td>
						</tr>
						<tr>
							<td>{{carOutInfo.zxbz}}</td>
						</tr>
						<tr>
							<td rowspan='2'>备注</td>
							<td rowspan='2' colspan='6'>{{carOutInfo.comm}}</td>
							<td>仓管员签名</td>
						</tr>
						<tr>
							<td>{{carOutInfo.cgyqm}}</td>
						</tr>
						<tr>
							<th rowspan='9'>调度员填写</th>
							<td>车辆性质</td>
							<td>车号</td>
							<td>车型</td>
							<td>司机</td>                
							<td>派车调度</td>
							<td>{{carOutInfo.pcdd}}</td>
							<td>派车时间</td>
							<td id="pcpcTime">{{carOutInfo.pcpcTime}}</td>
						</tr>
						<tr>
							<td>
								<p class='print'>{{vehicleinfofrist.clxz}}</p>
								<select @change='_isAllowPrint' id="fristclxz" name="vehicleinfofrist.clxz" class="noprint txt layui-input" v-model="vehicleinfofrist.clxz">
						    		 <option value="">请选择</option>
   									 <option value="自有">自有</option>
   									 <option value="租赁">租赁</option>
   									 <option value="合作">合作</option>
								</select>
							</td>
							<td><p class='print'>{{vehicleinfofrist.ch}}</p><input @input='_isAllowPrint' id="fristch" type='text' class='noprint borderNone layui-input' v-model="vehicleinfofrist.ch"/></td>
							<td><p class='print'>{{vehicleinfofrist.cx}}</p><input id="fristcx" type='text' class='noprint borderNone layui-input' v-model="vehicleinfofrist.cx"/></td>
							<td style="position: static;">
								<p class='print'>{{vehicleinfofrist.sj}}</p>
							   <input type="text" @input='_isAllowPrint' id="fristsj"  class="noprint selectInput" v-model="vehicleinfofrist.sj" lazy/>
							     <ul class="selectUl">
							        <li v-for="obj in driver_options" v-bind:value="obj.xm" v-bind:data="obj.grid">{{obj.xm}}</li>
							     </ul>
							<!-- 	<select id="fristsj" name="vehicleinfofrist.sj" class="txt layui-input" v-model="vehicleinfofrist.sj">
						    		 <option value="0">请选择</option>
   									 <option v-for="obj in driver_options" v-bind:value="obj.grid">{{obj.xm}}</option>
								 </select> -->
							</td>
							<td>起始地</td>
							<td>
								<p class='print'>{{carOutInfo.pcqsd}}</p>
								<!-- <select @change='_isAllowPrint' name="carOutInfo.pcqsd" id="pcqsd" class="noprint txt layui-input" v-model="carOutInfo.pcqsd">						    		
   									 <option v-for="obj in pcqsd_options" :data="obj.lc" v-bind:value="obj.value">{{obj.text}}</option>
								</select> -->
								 <input type="text" @input='_isAllowPrint' name="carOutInfo.pcqsd" id="pcqsd"  class="noprint selectInput" v-model="carOutInfo.pcqsd" lazy/>
							     <ul class="selectUl">
							        <li v-for="obj in pcqsd_options" v-bind:value="obj.value" v-bind:data="obj.lc">{{obj.text}}</li>
							     </ul>
								
							</td>
							<td>送货地</td>
							<td>
								<p class='print'>{{carOutInfo.pcshd}}</p>
								<select @change='_isAllowPrint' name="carOutInfo.pcshd" id="pcshd" class="noprint txt layui-input" v-model="carOutInfo.pcshd">						    		
   									 <option v-for="obj in pcqsd_options" :data="obj.lc"  v-bind:value="obj.value">{{obj.text}}</option>
								</select>
							</td>
						</tr>
						<tr>
 						   <td>
 						   		<p class='print'>{{vehicleinfosecond.clxz}}</p>
								<select @change='_isAllowPrint' id="secondclxz" name="vehicleinfosecond.clxz" class="noprint txt layui-input" v-model="vehicleinfosecond.clxz">
						    		 <option value="">请选择</option>
   									 <option value="自有">自有</option>
   									 <option value="租赁">租赁</option>
   									 <option value="合作">合作</option>
								</select>
							</td>
							<td><p class='print'>{{vehicleinfosecond.ch}}</p><input @input='_isAllowPrint' id="secondch" type='text' class='noprint borderNone layui-input' v-model="vehicleinfosecond.ch"/></td>
							<td><p class='print'>{{vehicleinfosecond.cx}}</p><input @input='_isAllowPrint' id="secondcx" type='text' class='noprint borderNone layui-input' v-model="vehicleinfosecond.cx"/></td>
							<td>
								<p class='print'>{{vehicleinfosecond.sj}}</p>
							    <input @input='_isAllowPrint'  type="text" id="secondsj" class="noprint selectInput" v-model="vehicleinfosecond.sj"  lazy/>
							     <ul class="selectUl">
							        <li v-for="obj in driver_options" v-bind:value="obj.xm" v-bind:data="obj.grid">{{obj.xm}}</li>
							     </ul>
								<!-- <select id="secondsj" name="vehicleinfosecond.sj" class="txt layui-input" v-model="vehicleinfosecond.sj">
						    		 <option value="0">请选择</option>
   									 <option v-for="obj in driver_options" v-bind:value="obj.grid">{{obj.xm}}</option>
								</select> -->
							</td> 
							<td>送货里程</td>
							<td colspan='3'>
								<p class='print'>{{carOutInfo.pcshlc}}</p>
								<select @change='_isAllowPrint'  name="carOutInfo.pcshlc" id="pcshlc" class="noprint txt layui-input" v-model="carOutInfo.pcshlc">						    		
   									 <option v-for="obj in pcshlc_options" :shlc="obj.shlc" v-bind:value='obj.lc' :data-min='obj.min' :data-max='obj.max'>{{obj.lc}}</option>
								</select>						
							</td>
						</tr>
						<tr>
 							<td>
 								<p class='print'>{{vehicleinfothird.clxz}}</p>
								<select @change='_isAllowPrint'  id="thirdclxz" name="vehicleinfothird.clxz" class="noprint txt layui-input" v-model="vehicleinfothird.clxz">
						    		 <option value="">请选择</option>
   									 <option value="自有">自有</option>
   									 <option value="租赁">租赁</option>
   									 <option value="合作">合作</option>
								</select>
							</td>
							<td><p class='print'>{{vehicleinfothird.ch}}</p><input @input='_isAllowPrint' id="thirdch" type='text' class='noprint borderNone layui-input' v-model="vehicleinfothird.ch"/></td>
							<td><p class='print'>{{vehicleinfothird.cx}}</p><input @input='_isAllowPrint' id="thirdcx" type='text' class='noprint borderNone layui-input' v-model="vehicleinfothird.cx"/></td>
							<td>
								<p class='print'>{{vehicleinfothird.sj}}</p>
							    <input @input='_isAllowPrint' type="text" id="thirdsj" class="noprint selectInput" v-model="vehicleinfothird.sj"/>
							     <ul class="selectUl">
							        <li v-for="obj in driver_options" v-bind:value="obj.xm" v-bind:data="obj.grid">{{obj.xm}}</li>
							     </ul>
						<!-- 		<select id="thirdsj" name="vehicleinfothird.sj" class="txt layui-input" v-model="vehicleinfothird.sj">
						    		 <option value="0">请选择</option>
   									 <option v-for="obj in driver_options" v-bind:value="obj.grid">{{obj.xm}}</option>
								</select> -->
							</td> 
							<td rowspan='2'>特别说明</td>
							<td rowspan='2' colspan='3'>
								<p class='print'>{{carOutInfo.pcComm}}</p>
								<input  @input='_isAllowPrint' type='text' id="pcComm" class='noprint inputborder borderNone layui-input' v-model="carOutInfo.pcComm"/>
							</td>
						</tr>
						<tr>
							<td>
								<p class='print'>{{vehicleinfofourth.clxz}}</p>
								<select @change='_isAllowPrint' id="fourthclxz" name="vehicleinfofourth.clxz" class="noprint txt layui-input" v-model="vehicleinfofourth.clxz">
						    		 <option value="">请选择</option>
   									 <option value="自有">自有</option>
   									 <option value="租赁">租赁</option>
   									 <option value="合作">合作</option>
								</select>
							</td>
							<td><p class='print'>{{vehicleinfofourth.ch}}</p><input @input='_isAllowPrint'  id="fourthch" type='text' class='noprint borderNone layui-input' v-model="vehicleinfofourth.ch"/></td>
							<td><p class='print'>{{vehicleinfofourth.cx}}</p><input @input='_isAllowPrint'  id="fourthcx" type='text' class='noprint borderNone layui-input' v-model="vehicleinfofourth.cx"/></td>
							<td>
								 <p class='print'>{{vehicleinfofourth.sj}}</p>
							     <input @input='_isAllowPrint'  type="text" id="fourthsj" class="noprint selectInput" v-model="vehicleinfofourth.sj"/>
							     <ul class="selectUl">
							        <li v-for="obj in driver_options" v-bind:value="obj.xm" v-bind:data="obj.grid">{{obj.xm}}</li>
							     </ul>
							<!-- 	<select id="fourthsj" name="vehicleinfofourth.sj" class="txt layui-input" v-model="vehicleinfofourth.sj">
						    		 <option value="0">请选择</option>
   									 <option v-for="obj in driver_options" v-bind:value="obj.grid">{{obj.xm}}</option>
								</select> -->
							</td>
						</tr>
						<tr>
							<td>考核对象</td>
							<td>
								<p class='print'>{{carOutInfo.khObject}}</p>
 							 	<select @change='_isAllowPrint'  name="carOutInfo.khObject" id="khObject" class="noprint txt layui-input" v-model="carOutInfo.khObject"> 						    		 
   									 <option v-for="obj in khObject_options" v-bind:value="obj.value">{{obj.text}}</option>
								</select>
							</td>
							<td>提成趟次</td>
							<td>
								<p class='print'>{{carOutInfo.tangci}}</p>
								<select @change='_isAllowPrint'  name="carOutInfo.tangci" id="tangci" class="noprint txt layui-input" v-model="carOutInfo.tangci"> 						    		
   									 <option v-for="obj in tangci_options" v-bind:value="obj.value">{{obj.text}}</option>
								</select>
							</td>
							<td>提成送货地</td>
							<td colspan='3'><p class='print'>{{carOutInfo.pcmdd}}</p><input  type='text' id="pcmdd" class='noprint inputborder borderNone layui-input' v-model="carOutInfo.pcmdd"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class='btn-operation list-top list-footer no-print'>
				<button v-show='!isSave' class='layui-btn layui-btn-warm no-print' @click='_saveHandler'>保存</button>
				<button v-show='isSave' class='layui-btn layui-btn-disabled no-print'>保存</button> 
				<!-- <button v-show='!isSave' class='layui-btn layui-btn-disabled no-print'>打印</button> -->
				<button class='layui-btn no-print' @click='_printHandler'>打印</button>
			</div>
		</div>
	<iframe id="printf" src="" width="0" height="0" frameborder="0"></iframe>
	<!-- <print-page :stylecss='stylecss' direction='H' ref='printFunction'></print-page> -->
</div>

    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <%@ include file="../../../static/publicFile/publicComponent.jsp"%> 
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/sendSign.js?v=3"></script>
</body>
</html>