<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>货运记录录入</title>
	<%@ include file="/views/transport/include/head.jsp"%>
    <link rel="stylesheet" href="${ctx_static}/transport/freightRecords/css/freightRecords.css?v=2"/>
</head>
<body style="overflow-y: scroll;">
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置  &gt;</a>
		<a href='javaScript:void(0);'>货运记录录入</a>
   </span>
   <div id="rrapp">
   		<form class='layui-form'>
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>运单编号：</label>
						<div class='layui-input-block'>
							<input type='text' name='waybillNum' lay-verify="needFill|waybillNum" dataname="运单编号" id='waybillNum' class='layui-input' placeholder="请输入10或12位编号">
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div input-label-box'>
						<label class='layui-form-label'>异常：</label>
						<div class='layui-input-block'>
						    <select name="exceptionType" id="selectDate">
	                             <option value="2">提货</option>
	                             <option value="0">干线</option>
	                             <option value="1">配送</option>
	                        </select>
						</div>
					</div>
				</div>
				<div class="layui-inline">
			       <button class="layui-btn inquire" lay-submit  lay-filter='inquire' id='inquire' style="top:-3px" >查询</button>
			    </div> 
			    <hr>
			</div>
		
		
			<!-- 录入表格 -->
			<div v-if="isDetailShow" v-cloak>
			    <div class="layui-inline">
			       <button class="layui-btn" @click="addRecord" type="button"  style="top:-3px" >添加记录</button>
			       <button class="layui-btn" @click="delRecord" type="button"  style="top:-3px" >删除记录</button>
			    </div> 
				<table class='layui-table freight-table'>
					<caption class="freight-caption">货物异常记录</caption>
					<colgroup>
						<col width='7%'>
						<col width='10%'>
						<col width='7%'>
						<col width='10%'>
						<col width='7%'>
						<col width='10%'>
						<col width='7%'>
						<col width='10%'>
						<col width='7%'>
						<col width='9%'>
						<col width='7%'>
						<col width='9%'>
					</colgroup>
					<tbody>
						<tr>
							<td class="label-brand">车牌号</td>
							<td class="not-fill">{{dataList.chxh}}</td>
							<td class="label-brand">装车日期</td>
							<td class="not-fill">{{dataList.zhchrq}}</td>
							<td class="label-brand">装车发站</td>
							<td class="not-fill">{{dataList.fazhan}}</td>
							<td class="label-brand">装车到站</td>
							<td class="not-fill">{{dataList.daozhan}}</td>
							<td class="label-brand">录入人</td>
							<td class="not-fill">{{dataList.currUser}}</td>
							<td class="label-brand">录入公司</td>
							<td class="not-fill">{{dataList.company}}</td>
						</tr>
						<tr>
							<td class="label-brand">供应商名称</td>
							<td class="not-fill">{{dataList.wxName}}</td>
							<td class="label-brand">异常环节</td>
							<td class="not-fill">{{dataList.ycType}}</td>
							<td class="label-brand label-required">发生地点</td>
							<td><input type="text" class="mustLu" lay-verify="mustFill" name="happenedAdress" dataname="发生地点"></td>
							<td class="label-brand label-required">发生日期</td>
							<td><input type="text" class="mustLu" id="appendDate" name="happenedTime"  dataname="发生日期"></td>
							<td class="label-brand label-required">到货日期</td>
							<td><input type="text" class="mustLu" id="arriveDate" name="arrivalTime" dataname="到货时间"></td>
							<td class="label-brand label-required">责任方</td>
							<td><input type="text" disabled></td>
						</tr>
					</tbody>
				</table>
				<table class='layui-table freight-table'>
					<colgroup>
						<col width='10%'>
						<col width='8%'>
						<col width='8%'>
						<col width='8%'>
						<col width='8%'>
						<col width='8%'>
						<col width='8%'>
						<col width='8%'>
						<col width='8%'>
						<col width='26%'>
					</colgroup>
					<thead class="freight-table-th">
						<tr>
							<th class="label-required">运单编号</th>
							<th class="label-required">品名</th>
							<th class="label-required">件数（件）</th>
							<th>短少（件）</th>
							<th>破损（件）</th>
							<th>湿损（件）</th>
							<th>污染（件）</th>
							<th class="label-required">异常数量（件）</th>
							<th class="label-required">预估损失（元）</th>
							<th class="label-required">事故原因描述</th>
						</tr>
					</thead>
					<tbody id="showLogList">
						<tr class="inputRow" v-for="item in detailData">
							<td><input type="text" v-model="item.wayBillNum" lay-verify="mustFill|wayBillNum" name="wayBillNum" dataname="运单编号"></td>
							<td><input type="text" v-model="item.goodsName" lay-verify="mustFill" dataname="品名"></td>
							<td><input type="text" v-model="item.ticketsNum" class="caseNum" lay-verify="mustFill|caseFill" name="ticketsNumber" dataname="件数"></td>
							<td><input type="number" v-model="item.ds" lay-verify="brokenFill" name="ds"  dataname="短少"></td>
							<td><input type="number" v-model="item.bjps" lay-verify="brokenFill" name="bjps"  dataname="破损"></td>
							<td><input type="number" v-model="item.ss" lay-verify="brokenFill" name="ss"  dataname="湿损"></td>
							<td><input type="number" v-model="item.ws" lay-verify="brokenFill" name="ws"  dataname="污染"></td>
							<td><input type="number" v-model="item.ycsl" lay-verify="abnormalFill" dataname="异常数量" disabled></td>
							<td><input type="text" v-model="item.estimatedLoss" lay-verify="mustFill|estimateLoss" name="estimatedLoss"  dataname="预估损失"></td>
							<td><input type="text" v-model="item.remark" lay-verify="mustFill|reasonDescribe" name="remark"  dataname="事故原因描述"></td>
						</tr>
					</tbody>
				</table>
				<div class="layui-form-item layui-form-save" style="text-align:center">
					<button class="layui-btn saveBtn" lay-submit lay-filter="saveBtn" id='saveBtn'>保存</button>
					<a class="layui-btn layui-btn-normal" href="${base_url}/transport/toFreightRecord">返回</a>
		         </div>
			</div>
		</form>	
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
   <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
   <script type="text/javascript" src="${ctx_static}/transport/freightRecords/js/freightRecordsEdit.js"></script>
   
</body>
</html>