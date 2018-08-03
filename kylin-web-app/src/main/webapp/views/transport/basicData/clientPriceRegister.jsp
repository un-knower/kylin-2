<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>结款和基础设置</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/publicFile/css/public.css" /> 
<link rel="stylesheet" href="${ctx_static}/transport/basicData/css/basicData.css" />
</head>
<body style="overflow-y: scroll;">
	<span class='layui-breadcrumb breadcrumb'> 
		<a>当前位置 &gt;</a><a>客户价格登记</a>
	</span>
	<div id="rrapp" v-cloak>
		<form class='layui-form'>
			<!-- 查询条件 -->
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>客户编码：</label>
						<div class='layui-input-block'>
							<input type='text' v-model="queryData.khbm" autocomplete="off" id='khbmQuery' class='layui-input' placeholder="请输入客户编码">
						</div>
					</div>
				</div>
				<div class="layui-inline query-btn">
			       <button class="layui-btn query" id='query' @click.prevent="queryInfo">查询</button>
			    </div>
			</div>
			<!-- 客户全部信息信息 -->
			<div>
				<hr/>
				<div class="main-wrap">
					<!-- 客户基本信息 -->
					<div class="table-title">
						<h4>客户基本信息</h4>
						<span>（下表属于查看类表格，禁止修改）</span>
					</div>
					<table class='layui-table'>
						<tr>
							<td class="tag-name">发货人</td>
							<td colspan="5">{{queryResult.khmc}}</td>
							<td class="tag-name">客户区域</td>
							<td>{{queryResult.khchsh}}</td>
						</tr>
						<tr>
							<td class="tag-name">地址</td>
							<td colspan="5">{{queryResult.khtxdz}}</td>
							<td class="tag-name">联系人</td>
							<td>{{queryResult.khbm}}</td>
						</tr>
						<tr>
							<td class="tag-name">职务</td>
							<td>{{queryResult.khlxr}}</td>
							<td class="tag-name">电话</td>
							<td>{{queryResult.lxrzw}}</td>
							<td class="tag-name">货物名称</td>
							<td>{{queryResult.khdh}}</td>
							<td class="tag-name">客户编码</td>
							<td>{{queryResult.pinming}}</td>
						</tr>
						<tr>
							<td class="tag-name">税号</td>
							<td colspan="7">{{queryResult.khsj}}</td>
						</tr>
						<tr>
							<td class="tag-name">业务类型</td>
							<td colspan="7">
							    <div class="layui-input-block layui-input-checkbox">
							      <input type="checkbox" data-name="kuaijian" lay-skin="primary" title="铁路快件">
							      <input type="checkbox" data-name="qiyun" lay-skin="primary" title="汽运">
							      <input type="checkbox" data-name="xinbao" lay-skin="primary" title="行包">
							      <input type="checkbox" data-name="wuding" lay-skin="primary" title="五定">
							      <input type="checkbox" data-name="xinyou" lay-skin="primary" title="行邮">
							      <input type="checkbox" data-name="hangkong" lay-skin="primary" title="航空">
							      <input type="checkbox" data-name="jizhuangxiang" lay-skin="primary" title="集装箱">
							      <input type="checkbox" data-name="citysend" lay-skin="primary" title="城际配送">
							      <input type="checkbox" data-name="other" lay-skin="primary" title="其他">
							    </div>
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
						</tr>
					</table>
					<!-- 操作要求 -->
					<div class="table-title">
						<h4>操作要求</h4>
						<span>（下表内容均可修改）</span>
					</div>
					<table class='layui-table cycle-info'>
						<tr>
							<td class="tag-name">结款方式</td>
							<td colspan="4">
							    <div class="layui-input-block layui-input-checkbox">
							      <input type="checkbox" data-name="yw_ky" lay-skin="primary" title="现付">
							      <input type="checkbox" data-name="yw_xb" lay-skin="primary" title="到付">
							      <input type="checkbox" data-name="yw_wd" lay-skin="primary" title="返单">
							      <input type="checkbox" data-name="yw_xy" lay-skin="primary" title="周期">
							      <input type="checkbox" data-name="yw_hk" lay-skin="primary" title="其他">
							    </div>
							</td>
							<td class="tag-name">有无合同</td>
							<td colspan="2">
								<input type="radio" class="ywht_yes" value="1"  title="有">
			      				<input type="radio" class="ywht_no" value="0" title="无">
							</td>
						</tr>
						<tr>
							<td class="tag-name">发货人</td>
							<td colspan="5">{{queryResult.khmc}}</td>
							<td class="tag-name">客户区域</td>
							<td>{{queryResult.khchsh}}</td>
						</tr>
						<tr>
							<td class="tag-name" rowspan="2">返单要求</td>
							<td colspan="7">
								<div class="layui-input-block layui-input-checkbox">
							      <input type="checkbox" data-name="yw_ky" lay-skin="primary" title="重量">
							      <input type="checkbox" data-name="yw_xb" lay-skin="primary" title="体积">
							      <input type="checkbox" data-name="yw_wd" lay-skin="primary" title="其他">
							    </div>
							</td>
						</tr>
						<tr>
							<td class="tag-name">返单名称</td>
							<td colspan="3"><input type="text" name="bankaccountno" v-model="queryResult.bankaccountno"></td>
							<td class="tag-name">返单联次</td>
							<td colspan="2"><input type="text" name="businessregistrationno" v-model="queryResult.businessregistrationno"></td>
						</tr>
						<tr>
							<td class="tag-name">计费标准</td>
							<td colspan="4">
							    <div class="layui-input-block layui-input-checkbox">
							      <input type="checkbox" data-name="yw_ky" lay-skin="primary" title="重量">
							      <input type="checkbox" data-name="yw_xb" lay-skin="primary" title="体积">
							      <input type="checkbox" data-name="yw_wd" lay-skin="primary" title="其他">
							    </div>
							</td>
							<td class="tag-name">代收款</td>
							<td colspan="2">
								<input type="radio" class="ywht_yes" value="1"  title="有">
			      				<input type="radio" class="ywht_no" value="0" title="无">
							</td>
						</tr>
						<tr>
							<td class="tag-name">客户性质</td>
							<td colspan="7">
							    <input type="radio" class="ywht_yes" value="1"  title="新客户">
			      				<input type="radio" class="ywht_no" value="0" title="老客户发新线">
			      				<input type="radio" class="ywht_yes" value="1"  title="老客户调价">
			      				<input type="radio" class="ywht_no" value="0" title="流失客户">
							</td>
						</tr>
						<tr>
							<td class="tag-name">有效期</td>
							<td colspan="3">{{queryResult.khlxr}}</td>
							<td class="tag-name">操作标准</td>
							<td colspan="3">{{queryResult.lxrzw}}</td>
						</tr>
						<tr>
							<td class="tag-name">其他特殊操作说明</td>
							<td colspan="7"><input type="text" name="shippertel" v-model="queryResult.shippertel"></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<div>
			<!-- 收费标准 -->
			<div class="table-title">
				<h4>收费标准</h4>
				<span>（下表属于查看表格，禁止修改）</span>
			</div>
			<table class='layui-table'>
				<tr>
					<th rowspan="2" class="tag-name">到站</th>
					<th colspan="3" class="tag-name">单价</th>
					<th colspan="2" class="tag-name">装卸费</th>
					<th rowspan="2" class="tag-name">重货机械作业费</th>
					<th rowspan="2" class="tag-name">保率</th>
					<th rowspan="2" class="tag-name">办单费</th>
					<th rowspan="2" class="tag-name">服务方式</th>
					<th rowspan="2" class="tag-name">走货方式</th>
					<th rowspan="2" class="tag-name">业务接单号</th>
					<th rowspan="2" class="tag-name">执行开始时间</th>
					<th rowspan="2" class="tag-name">执行结束时间</th>
					<th rowspan="2" class="tag-name">备注</th>
					<th rowspan="2" class="tag-name">登记时间</th>
				</tr>
				<tr>
					<th class="tag-name">轻货</th>
					<th class="tag-name">重货</th>
					<th class="tag-name">按件</th>
					<th class="tag-name">轻货</th>
					<th class="tag-name">重货</th>
				</tr>
				<tr>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
					<td>2.00</td>
				</tr>
				
			</table>
			
			<div class="layui-inline save-wrap" v-show="isShowSave">
		       <button class="layui-btn saveBtn" lay-submit lay-filter="saveBtn" id='saveBtn'>保存修改</button>
		    </div>
		</div>
		
	</div>
	<%@ include file="/views/transport/include/floor.jsp"%>
	<script>
   		var base_url = '${base_url}';
   		var ctx_static = '${ctx_static}';
   		//获取当前登录的用户名 
		var userName = '${currentUser.userName}';
		//获取当前登录的公司 
		var userCompany = '${currentUser.company}';
		//获取当前登录的账号 
		var userAccount = '${currentUser.account}';
   </script>
   <script type="text/javascript" src="${ctx_static}/transport/basicData/js/clientPriceRegister.js"></script>
</body>
</html>