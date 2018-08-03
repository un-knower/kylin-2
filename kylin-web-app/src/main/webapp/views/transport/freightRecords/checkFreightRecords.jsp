<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>&nbsp;</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/freightRecords/css/freightRecords.css"/>
</head>
<body>
	<div id='rrapp' style="padding-top:30px;" v-cloak>
		<form class="layui-form">
		    <table class='layui-table freight-table check-table'>
				<caption>货物异常记录</caption>
				<colgroup>
					<col width='6%'>
					<col width='8%'>
					<col width='6%'>
					<col width='8%'>
					<col width='6%'>
					<col width='8%'>
					<col width='6%'>
					<col width='8%'>
					<col width='7%'>
					<col width='8%'>
					<col width='6%'>
					<col width='8%'>
					<col width='7%'>
					<col width='8%'>
				</colgroup>
				<tbody>
					<tr>
						<td class="check-fill">车牌号</td>
						<td>{{infoList.chc}}</td>
						<td>装车日期</td>
						<td>{{infoList.fchrq}}</td>
						<td>装车发站</td>
						<td>{{infoList.fazhan}}</td>
						<td>装车到站</td>
						<td>{{infoList.daozhan}}</td>
						<td>供应商名称</td>
						<td>{{infoList.wxName}}</td>
						<td>录入人</td>
						<td>{{infoList.lrr}}</td>
						<td>录入公司</td>
						<td>{{infoList.gs}}</td>
					</tr>
					<tr>
						<td class="check-fill">异常环节</td>
						<td>{{infoList.abnormalType}}</td>
						<td>发生地点</td>
						<td>{{infoList.happenedAdress}}</td>
						<td>发生日期</td>
						<td>{{infoList.happenedTime}}</td>
						<td>到货日期</td>
						<td>{{infoList.arrivalTime}}</td>
						<td>责任方</td>
						<td class="fixed-height"><input type="text" id="responsibility" :disabled="respDisabled" v-model="ideaData.responsibility" dataname="责任方"></td>
						<td>通知公司</td>
						<td class="fixed-height"><input type="text" id="noticeCompany" lay-verify="noticeCompany" dataname="通知公司" :disabled="informDisabled" v-model="ideaData.noticeCompany"></td>
						<td>通知公司处理意见</td>
						<td class="fixed-height"><input type="text" id="noticeCompanyOpinion" lay-verify="lengthLimit" dataname="通知公司处理意见" :disabled="informOpinionDisabled" v-model="ideaData.noticeCompanyOpinion"></td>
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
				<thead>
					<tr>
						<th>运单编号</td>
						<th>品名</td>
						<th>件数（件）</th>
						<th>短少（件）</th>
						<th>破损（件）</th>
						<th>湿损（件）</th>
						<th>污染（件）</th>
						<th>异常数量（件）</th>
						<th>预估损失（元）</th>
						<th>事故原因描述</th>
					</tr>
				</thead>
				<tbody id="showLogList">
					<tr v-for="item in detailList">
						<td class="check-fill">{{item.ydbhid}}</td>
						<td>{{item.pinming}}</td>
						<td>{{item.jianshu}}</td>
						<td>{{item.ds}}</td>
						<td>{{item.bjps}}</td>
						<td>{{item.ssh}}</td>
						<td>{{item.wr}}</td>
						<td>{{item.exceptionCount}}</td>
						<td>{{item.estimatedLoss}}</td>
						<td>{{item.ychzht}}</td>
					</tr>
				</tbody>
			</table>
			<table class='layui-table freight-table resp-opinion'>
				<colgroup>
					<col width='20%'>
					<col width='10%'>
					<col width='10%'>
					<col width='15%'>
					<col width='10%'>
					<col width='15%'>
				</colgroup>
				<tbody>
					<tr class="daozhan-wrap">
						<td class="label-brand">到站单位负责人处理意见</td>
						<td class="fixed-height"><input type="text" lay-verify="mustFill|lengthLimit" v-model="ideaData.dzdwOpinion" dataname="到站单位负责人处理意见"></td>
						<td class="label-brand">负责人</td>
						<td>{{ideaData.dzdwName}}</td>
						<td class="label-brand">处理时间</td>
						<td class="dzdwTime">{{ideaData.dzdwTime}}</td>
					</tr>
					<tr v-show="isFazhanShow" class="fazhan-wrap">
						<td class="label-brand">发站单位负责人处理意见</td>
						<td class="fixed-height"><input type="text" v-model="ideaData.fzdwOpinion" dataname="发站单位负责人处理意见"></td>
						<td class="label-brand">负责人</td>
						<td>{{ideaData.fzdwName}}</td>
						<td class="label-brand">处理时间</td>
						<td class="fzdwTime">{{ideaData.fzdwTime}}</td>
					</tr>
				</tbody>
			</table>  
			<div class="layui-form-item layui-form-save" style="text-align:center">
				<button class="layui-btn" id='checkBtn' lay-submit lay-filter="checkBtn">提交审核</button>
			</div>
		</form>
	</div>
	<%@ include file="/views/transport/include/floor.jsp"%><script>
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
    <script src="${ctx_static}/transport/freightRecords/js/checkFreightRecords.js"></script>
</body>
</html>