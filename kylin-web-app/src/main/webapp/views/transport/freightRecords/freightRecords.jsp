<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>货运记录查询</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/transport/freightRecords/css/freightRecords.css" />
</head>
<body style="overflow-y: scroll;">
	<span class='layui-breadcrumb breadcrumb'> 
		<a href='javaScript:void(0);'>当前位置 &gt;</a> <a href='javaScript:void(0);'>货运记录查询</a>
	</span>
	<div id="rrapp">
		<form class='layui-form '>
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label '>运单编号：</label>
						<div class='layui-input-block'>
							<input type='text' lay-verify="waybillNum" name='wayBillNum'
								dataname="运单编号" id='waybillNum' autocomplete="off"
								class='layui-input'>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label '>车牌号：</label>
						<div class='layui-input-block'>
							<input type='text' lay-verify="train" name='train' dataname="车牌号"
								id='train' autocomplete="off" class='layui-input'>
						</div>
					</div>
				</div>
				<!-- <div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>装车发站：</label>
						<div class='layui-input-block'>
							<input id='station' name="station" autocomplete="off" type='text'
								class='layui-input'>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label '>装车到站：</label>
						<div class='layui-input-block'>
							<input id='arriveStation' name="arriveStation" type='text'
								class='layui-input'>
						</div>
					</div>
				</div> -->
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>录入时间：</label>
						<div class='layui-input-block'>
							<input type='text' name='startTime' class='layui-input'
								id='startDate' placeholder="请输入">
						</div>
						<span class='date-select date-copy'>至</span>
						<div class='layui-input-block'>
							<input type='text' name='endTime' class='layui-input'
								id='endDate' placeholder="请输入">
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div input-label-box2'>
						<label class='layui-form-label'>审核：</label>
						<div class='layui-input-block'>
						    <select name="examine" id="isExamine">
	                             <option value="1">到站审核</option>
	                             <option value="0">发站审核</option>
	                             <option value="2">通知公司处理审核</option>
	                        </select>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>是否处理：</label>
						<div class='layui-input-block'>
						    <select name="isHandle" id="isHandle">
	                             <option value="0">未处理</option>
	                             <option value="1">已处理</option>
	                        </select>
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<shiro:hasPermission name="record:query:freightCheck"> 
				   		<button class="layui-btn toCheck" lay-submit lay-filter='toCheck'
						id='checkBtn' style="top: -3px">查询</button>
			 		</shiro:hasPermission>
				</div>
			</div>
		</form>
		<hr>
		<a class="layui-btn" href="${base_url}/transport/toFreightRecordsEdit">新增</a>
		<hr>
		<table class='layui-table'>
			<!-- <colgroup>
				<col width='8%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
				<col width='6%'>
			</colgroup> -->
			<thead>
				<tr>
					<td>运单编号</td>
					<td>录入公司</td>
					<td>车牌号</td>
					<td>装车发站</td>
					<td>装车到站</td>
					<td>发生日期</td>
					<td>到货日期</td>
					<td>发货人</td>
					<td>收货人</td>
					<td>供应商名称</td>
					<td>异常环节</td>
					<td width="10%">异常描述</td>
					<td>事故原因</td>
					<td>预估损失（元）</td>
					<td>责任方</td>
					<td>是否处理</td>
					<td>通知公司</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="showLogList" v-cloak>
				<tr v-for="item in dataList">
					<td>{{item.ydbhid}}</td>
					<td>{{item.gs}}</td>
					<td>{{item.chc}}</td>
					<td>{{item.fazhan}}</td>
					<td>{{item.daozhan}}</td>
					<td>{{item.happenedTime}}</td>
					<td>{{item.arrivalTime}}</td>
					<td>{{item.fhr}}</td>
					<td>{{item.shhrmch}}</td>
					<td>{{item.wxName}}</td>
					<td>{{item.abnormalTypeText}}</td>
					<td>{{item.abnormalNum}}</td>
					<td>{{item.ychzht}}</td>
					<td>{{item.estimatedLoss}}</td>
					<td>{{item.responsibility}}</td>
					<td>{{item.isHandleText}}</td>
					<td>{{item.noticeCompany}}</td>
					<td class="operator">
					<shiro:hasPermission name="record:comment:check"> 
				   		<button class="layui-btn" :disabled="item.disabled" @click="checkDetail(item.id,item.ydbhid)">审核</button>
			 		</shiro:hasPermission>
						<button class="layui-btn audit-btn" @click="printDetail(item.id,item.ydbhid)">打印</button>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="layui_div_page" class="col-sm-12">
			<ul class="pagination">
				<li class="disabled"><span><span aria-hidden="true">共<span
							class='page-total'></span>条,每页<span class='page-size'></span>条
					</span></span></li>

				<li><a href="javascript:;" num="1" class='isFirstPage'
					style='display: none'>首页</a></li>
				<li><a href="javascript:;" num=""
					class='page-prePage isFirstPage' style='display: none'>&laquo;</a></li>

				<li class='insert-li'><a href="javascript:;" num=""
					class='isLastPage nextPage' style='display: none'>&raquo;</a></li>
				<li><a href="javascript:;" num="" class='isLastPage lastPage'
					style='display: none'>尾页</a></li>

				<li class="disabled last-li"><span><span>共<span
							class='page-pages'></span>页
					</span></span></li>
			</ul>
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
	<script type="text/javascript"
		src="${ctx_static}/transport/freightRecords/js/freightRecords.js"></script>
</body>
</html>