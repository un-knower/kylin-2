<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
	<head>
		<title>录入运单数据</title>
		<%@ include file="/views/transport/include/head.jsp"%>
		<style type="text/css">
			#btn-cansel{ 
			   cursor:pointer;
		    }
		</style>
	</head>
	
	<body>
			<div id="content">
				<div class="con-main">
					<div class="part">
						<h2>运单录入信息</h2>
						<div class="sub-part">  
							<div class="sub-part-form">
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l must">运单编号 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.ydbhid}">
									</div>
									<div class="form-con  col-25">
										<label  class="labe-l">托运时间 :</label>
										<input type="text" class="txt datetime-picker" readonly="readonly" value="<fmt:formatDate value='${convey.order.fhshj}' type='both' pattern='yyyy-MM-dd'/>">
									</div>
									<div class="form-con  col-25">
										<label  class="labe-l">录单员 :</label>
										<input type="text" readonly="readonly" class="txt" value="${employee.emplyeeName }">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-50">
										<label  class="labe-l must">始发站 :</label>
										<input type="text" value="${convey.order.fazhan}" readonly="readonly" class="txt">
									</div>
									<div class="form-con  col-50">
										<label  class="labe-l must">到站 :</label>
										<select class="txt" id="daozhan" value="${convey.order.daozhan}" disabled="disabled">
											<c:forEach items="${daozhanList}" var="i" varStatus="status">
												<option value="${i.companyName }">${i.companyName }</option>
											</c:forEach>
										</select>
									</div>
									
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label  class="labe-l">到站网点 :</label>
										<input type="text" value="${convey.order.dzshhd}" readonly="readonly" class="txt">
									</div>
								</div>
							<!-- 	<div class="clearfix">
									<div class="form-con  col-50">
										<label class="labe-l must">始发地 :</label>
										<input class="txt city-picker" type="text" readonly="readonly" value="${convey.order.beginPlacename}">
									</div>
									<div class="form-con  col-50">
										<label class="labe-l must">目的地 :</label>
										<input class="txt city-picker" type="text" readonly="readonly" value="${convey.order.endPlacename}">
									</div>
								</div>
								 -->
							</div>
						</div>
						<!--客户信息-->
						<div class="sub-part"> 
							<h4>客户信息</h4>
							<div class="sub-part-form">
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label for="fhdwmch" class="labe-l must">客户名称 :</label>
										<input class="txt" type="text" id="fhdwmch" readonly="readonly" value="${convey.order.fhdwmch}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">客户编码 :</label>
										<input class="txt" type="text" id="khbm" readonly="readonly" value="${convey.order.khbm}">
									</div>
									<div class="form-con col-50">
										<label class="labe-l must">客户地址 :</label>
										<input type="text" id="fhdwdzh" class="txt" readonly="readonly" value="${convey.order.fhdwdzh}">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l">座机 :</label>
										<input type="text" id="fhdwlxdh" class="txt" readonly="readonly" value="${convey.order.fhdwlxdh}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">手机号 :</label>
										<input type="text" id="fhdwyb" class="txt" readonly="readonly" value="${convey.order.fhdwyb}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">行业类别 :</label>
										<select class="txt" id="fhkhhy" value="${convey.order.fhkhhy }" disabled="disabled">
											<c:forEach items="${fhkhhyList}" var="i" varStatus="status">
												<option value="${i.industryName }">${i.industryName }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<!--收货人信息-->
						<div class="sub-part"> 
							<h4>收货人信息</h4>
							<div class="sub-part-form">
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l must">名称 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.shhrmch }">
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">手机 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.shhryb }">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-50">
										<label class="labe-l must">省市区 :</label>
										<input class="txt city-picker" readonly="readonly" type="text" id="shrProvinces" value="${convey.shrProvinces }">
									</div>
									<div class="form-con col-50">
										<label class="labe-l must">地址 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.shhrdzh }">
									</div>
								</div>
							</div>
						</div>
						<!--发运信息-->
						<div class="sub-part"> 
							<h4>发运信息</h4>
							<div class="sub-part-form">
								<div class="clearfix">
									<div class="form-con col-50">
										<label class="labe-l must">服务方式 :</label>
										<div class="txt radioBox">
	       									<div>
		       									<input id="order.fwfs" disabled="disabled" name='order.fwfs' type="radio" value='0' real="${convey.order.fwfs }"/><label>仓到站</label>
												<input name='order.fwfs' disabled="disabled" type="radio" value='1' real="${convey.order.fwfs }"/><label>仓到仓</label>
												<input name='order.fwfs' disabled="disabled" type="radio" value='2' real="${convey.order.fwfs }"/><label>站到仓</label>
												<input name='order.fwfs' disabled="disabled" type="radio" value='3' real="${convey.order.fwfs }"/><label>站到站</label>
											</div>
										</div>
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">运输方式 :</label>
										<select class="txt" id="ysfs" value="${convey.order.ysfs }" disabled="disabled">
											<c:forEach items="${ysfsList}" var="i" varStatus="status">
												<option value="${i.wayName }">${i.wayName }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运输天数 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.daodatianshu }">
									</div>
									
								</div>
								<div class="clearfix"> 
									<div class="form-con col-50">
										<label class="labe-l must">是否返单 :</label>
										<div class="txt radioBox">
											<div>
												<input name='order.isfd' disabled="disabled" type="radio" value='1' real="${convey.order.isfd }"/><label>普通返单</label>
												<input name='order.isfd' disabled="disabled" type="radio" value='2' real="${convey.order.isfd }"/><label>电子返单</label>
												<input name='order.isfd' disabled="disabled" type="radio" value='0' real="${convey.order.isfd }"/><label>无</label>					
											</div>
										</div>
									</div>
									<div class="form-con col-50">
										<label class="labe-l">返单要求 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.fdyq }">
									</div>
									<!-- <div class="form-con col-25">
										<label class="labe-l must">作业方式 :</label>
										<select class="txt" id="zyfs" value="${convey.order.zyfs }" disabled="disabled">
											<option value="0">人工</option>
											<option value="1">机械</option>
										</select>
									</div> -->	
								</div>
								<div class="clearfix" > 
									<div class="form-con col-25">
										<label class="labe-l">运输号码 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.yshhm }">
									</div>
									<div class="form-con col-50">
										<label class="labe-l">特别说明 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.tiebieshuoming }">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l must">付费方式 :</label>
										 <div class='txt checkBox'>
											<div>
												<input type="checkbox" disabled="disabled" value='1' name="order.fzfk" real="${convey.order.fzfk }"/><label class="hCheckBox">发付</label>
												<input type="checkbox" disabled="disabled" value='1' name="order.dzfk" real="${convey.order.dzfk }"/><label class="hCheckBox">到付</label>
											</div>
										</div> 
									</div>
									<div class="form-con col-25">
										<label class="labe-l">承运人签名 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.hyy}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">托运人签名 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.tyrqm}">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">客户单号 :</label>
										<input type="text" class="txt" readonly="readonly" value="${convey.order.khdh}">
									</div>
									<div class="form-con col-25">
	       								<div>
	       									<input type="checkbox" name="isReleaseWaiting" value='1' real="${convey.releaseWaiting }" disabled="disabled"/><label class="hCheckBox"><font color="red">等托运人指令发货</font></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       									<input type="checkbox" name="certify.receiveMoneyStatus" <c:if test="${convey.certify.receiveMoneyStatus==1}">checked="checked"</c:if> value='1' real="${certify.receiveMoneyStatus}" disabled="disabled"/><label class="hCheckBox"><font color="red">款未付等通知</font></label>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<!--货物信息-->
						<div class="sub-part"> 
							<h4>货物信息</h4>
							<div class="sub-part-form wtable">
								<table>
									<thead>
										<tr>
											<th width="5%" style="text-align:center;">序号</th>
											<th style="text-align:center;">品名</th>
											<th style="text-align:center;">型号</th>
											<th width="6%" style="text-align:center;">件数</th>
											<th width="6%" style="text-align:center;">包装</th>
											<th width="6%" style="text-align:center;">重量(吨)</th>
											<th width="6%" style="text-align:center;">体积(立方)</th>
											<th width="6%" style="text-align:center;">保价金额</th>
											<th width="6%" style="text-align:center;">重货叉车量</th>
											<th width="6%" style="text-align:center;">重货装卸量</th>
											<th width="6%" style="text-align:center;">轻货叉车量</th> 
											<th width="6%" style="text-align:center;">轻货装卸量</th> 
											<th width="7%" style="text-align:center;">计费方式</th>
											<th width="8%" style="text-align:center;">运价</th>
										</tr>
									</thead>
									<tbody id="details-tbody">
										<c:forEach items="${convey.details}" var="d" varStatus="s">
											<tr>											
												<td>${s.index + 1}<input type="hidden" value="${s.index + 1}"/></td>
												<td><input type="text" readonly="readonly" class="edit-input" value="${d.pinming}"/></td>
												<td><input type="text" readonly="readonly" class="edit-input" value="${d.xh}"/></td>
												<td><input type="text" readonly="readonly" name="details[${s.index}].jianshu" class="edit-input" value="${d.jianshu}"/></td>
												<td><input type="text" readonly="readonly" class="edit-input" value="${d.bzh}"/></td>
												<td><input type="text" readonly="readonly" name="details[${s.index}].zhl" class="edit-input" value='${d.zhl}'/></td>
												<td><input type="text" readonly="readonly" name="details[${s.index}].tiji" class="edit-input" value='${d.tiji}'/></td>
												<td><input type="text" readonly="readonly" class="edit-input" value='<fmt:formatNumber value="${d.tbje}" type="currency" pattern="0.00"/>'/></td>
												<td><input type="text" readonly="readonly" class="edit-input" name="details[${s.index}].zchl" value="${d.zchl}"/></td><!-- 重货叉车量 -->
												<td><input type="text" readonly="readonly" class="edit-input" name="details[${s.index}].zzxl" value="${d.zzxl}"/></td><!-- 重货装卸量 -->
												<td><input type="text" readonly="readonly" class="edit-input" name="details[${s.index}].qchl" value="${d.qchl}"/></td><!-- 轻货叉车量 -->
												<td><input type="text" readonly="readonly" class="edit-input" name="details[${s.index}].qzxl" value="${d.qzxl}"/></td><!-- 轻货装卸量 -->
												<td>
													<select class="edit-input" value="${d.jffs}" disabled="disabled">
														<option value="0">重货</option>
														<option value="1">轻货</option>
														<option value="2">按件</option>
													</select>
												</td>
												<td><input type="text" readonly="readonly" class="edit-input" value='<fmt:formatNumber value="${d.yunjia}" type="currency" pattern="0.00"/>'/></td> 													
											</tr>
										</c:forEach>		
									</tbody>  
									<tbody>
										<tr>											
											<td colspan="3">合计</td>
											<td><input type="text" id="jianshuHj" class="edit-input" readonly="readonly"/></td>
											<td></td>
											<td><input type="text" id="zhlHj" class="edit-input" readonly="readonly"/></td>
											<td><input type="text" id="tijiHj" class="edit-input" readonly="readonly"/></td>
											<td><input type="text" id="tbjeHj" class="edit-input" readonly="readonly"/></td>
											<td><input type="text" id="zchlHj" class="edit-input" readonly="readonly"/></td>
											<td><input type="text" id="zzxlHj" class="edit-input" readonly="readonly"/></td>
											<td><input type="text" id="qchlHj" class="edit-input" readonly="readonly"/></td>
											<td><input type="text" id="qzxlHj" class="edit-input" readonly="readonly"/></td>
											<td colspan="2"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="part">
						<h2>财凭录入信息</h2>
						<div class="sub-part"> 
							<div class="sub-part-form">
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">保率:</label>
										<input type="text" name="price.premiumRate" readonly="readonly" class="txt" value="${convey.certify.premiumRate}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">叉车费单价:</label>
										<input type="text" name="price.zhjxzyf" readonly="readonly" class="txt" value="${convey.certify.forkliftPrice}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">轻货装卸单价:</label>
										<input type="text" name="price.qhzhxfdj" readonly="readonly" class="txt" value="${convey.certify.lightHandlingPrice}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">重货装卸单价:</label>
										<input type="text" name="price.zhzhxfdj" readonly="readonly" class="txt" value="${convey.certify.heavyHandlingPrice}">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">保险费 :</label>
										<input type="text" class="txt" readonly="readonly" value='<fmt:formatNumber value="${convey.order.baoxianfei }" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">叉车费 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.forkliftFee" value="${transportOrderEntry.certify.forkliftFee}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">装卸费 :</label>
										<input type="text" class="txt" readonly="readonly" value='<fmt:formatNumber value="${convey.order.zhuangxiefei }" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运费合计 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.transportTotalFee" value="${transportOrderEntry.certify.transportTotalFee }">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">重货运价 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.weightprice" value='<fmt:formatNumber value="${convey.certify.weightprice}" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">轻货运价 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.lightprice" value='<fmt:formatNumber value="${convey.certify.lightprice}" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">按件运价 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.piecework" value='<fmt:formatNumber value="${convey.certify.piecework}" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">办单费 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.invoice" value='<fmt:formatNumber value="${convey.certify.invoice}" type="currency" pattern="0.00"/>'>
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l must">费用名称 :</label>
										<input type="text" class="txt" name="certify.otherFeeName" id="otherFee" value="${transportOrderEntry.certify.otherFeeName==null?'其他费用':transportOrderEntry.certify.otherFeeName}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">代收货款 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.receipt" value='<fmt:formatNumber value="${convey.certify.receipt}" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">上门取货费 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.tohome" value='<fmt:formatNumber value="${convey.certify.tohome}" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">送货上门费 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.delivery" value='<fmt:formatNumber value="${convey.certify.delivery}" type="currency" pattern="0.00"/>'>
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l">其他费用 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.other" value='<fmt:formatNumber value="${convey.certify.other}" type="currency" pattern="0.00"/>'>
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">合计费用 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.cost" value='<fmt:formatNumber value="${convey.certify.cost}" type="currency" pattern="0.00"/>'>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="con-btn">
					<form  class="form-inline" id='form_hidden' role="form" method="post">
						<input type="hidden"  name="ydbhid" value="${searchModel.ydbhid }">
						<input type="hidden"  name="beginPlacename" value="${searchModel.beginPlacename }">
						<input type="hidden"  name="daozhan" value="${searchModel.daozhan }">
						<input type="hidden"  name="yshhm" value="${searchModel.yshhm }">
						<input type="hidden"  name="searchdate" value="${searchModel.searchdate }">
						<input type="hidden"  name="status" value="${searchModel.status }">
					</form>
					<a id="btn-cansel" class="btn-cansel">返回</a>
				</div>
			</div>
<%@ include file="/views/transport/include/floor.jsp"%>
<script type="text/javascript">
var base_url = '${base_url}';
$(document).ready(function(){
	
	//防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
    });
	
	/*
	$("#daozhan").val($("#daozhan_hidden").val());
	$("#fhkhhy").val($("#fhkhhy_hidden").val());
	$("#ysfs").val($("#ysfs_hidden").val());
	$("#zyfs").val($("#zyfs_hidden").val());
	
	$("input[name='order.fwfs'][value="+$("#fwfs").val()+"]").attr("checked",true); 
	$("input[name='order.isfd'][value="+$("#isfd").val()+"]").attr("checked",true); 
	
	
	if($("#fzfk").val() != null && $("#fzfk").val() == 1){
		$("input[name='order.fzfk']").attr("checked",true); 
	}
	
	if($("#dzfk").val() != null && $("#dzfk").val() == 1){
		$("input[name='order.dzfk']").attr("checked",true); 
	}
	
	if($("#isReleaseWaiting").val() != null && $("#isReleaseWaiting").val() == 1){
		$("input[name='isReleaseWaiting']").attr("checked",true); 
	}
	
	var shrProvinces = $("#dhShengfen").val() + $("#dhChengsi").val() + $("#dhAddr").val();
	$("#shrProvinces").val(shrProvinces);

	*/
	
	//设置所有的select选中值
	$("select").each(function(i){
		if($(this).attr("value")){
			$(this).val($(this).attr("value"));
		}
	});
	//设置所有的单选多选选中值
	$("input:radio,input:checkbox").each(function(i){
		if($(this).val() == $(this).attr("real")){
			$(this).attr("checked",'checked');
		}
	});
	
	bindEvent();
	
	$("#btn-cansel").on('click', function(){
		$("#form_hidden").attr("action", base_url + "/transport/convey/manage/search.do");
		$("#form_hidden").submit();
	});
});

function bindEvent(){
	var gegExp = new RegExp('');
	var item = { jianshu : 0, zhl : 0, tiji : 0 ,qzxl :0,zchl :0,tbje:0,zzxl:0,qchl:0};
	$("#details-tbody tr").each(function(){
		$(this).find("input").each(function(){
			var _nameValue = $(this).attr("name");
			if(gegExp.compile('details\\[\\d+\\]\\.jianshu').test(_nameValue)){
				item.jianshu += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.zhl').test(_nameValue)){
				item.zhl += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.tiji').test(_nameValue)){
				item.tiji += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.tbje').test(_nameValue)){
				item.tbje += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.zchl').test(_nameValue)){
				item.zchl += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.qchl').test(_nameValue)){
				item.qchl += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.zzxl').test(_nameValue)){
				item.zzxl += Number($(this).val());
			}
			if(gegExp.compile('details\\[\\d+\\]\\.qzxl').test(_nameValue)){
				item.qzxl += Number($(this).val());
			}
		});
	});
	
	if(item.jianshu){
		$("#jianshuHj").val(item.jianshu);
	}
	if(item.tiji){
		$("#tijiHj").val(item.tiji);
	}
	if(item.zhl){
		$("#zhlHj").val(item.zhl);
	}
	if(item.tbje){
		$("#tbjeHj").val(item.tbje);
	}
	if(item.zchl){
		$("#zchlHj").val(item.zchl);
	}
	if(item.qchl){
		$("#qchlHj").val(item.qchl);
	}
	if(item.zzxl){
		$("#zzxlHj").val(item.zzxl);
	}
	if(item.qzxl){
		$("#qzxlHj").val(item.qzxl);
	}
}
</script>
	</body>
</html>