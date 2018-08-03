<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
		<link rel="stylesheet" href="${ctx_static}/transport/convey/css/insert.css"/>
		<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
				<link rel="stylesheet" href="${ctx_static}/transport/common/css/layout.css"/>
</head>
<title>录入运单数据</title>
		<%@ include file="/views/transport/include/head.jsp"%>
	</head>
	<body>
	<style>
		.need-fill::before{content:'*';color:red;}
	</style>
	<!-- loading层效果 -->
	<div class="loading" id="loading">
		<img src="${ctx_static}/common/images/loading.gif">
	</div>
		<form  class="form-inline" id='form' role="form" action="${base_url}/transport/convey/insert/save" method="post">
			<div id="content">
				<div class="con-main">
					<div class="part">
						<h2>运单录入</h2>
						<div class="sub-part">  
							<div class="sub-part-form">
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l must">运单编号 :</label>
										<input type="text" name="order.ydbhid" id="ydbhid" class="txt" 
											 placeholder="请输入10或12位运单编号" value="${transportOrderEntry.order.ydbhid}"/>
									</div>
									 <div class="form-con  col-25" style="display: none">
										<label  class="labe-l">录单员 :</label>
										<input type="text" readonly="readonly" class="txt" value="${user.userName }">
										<input type="hidden" name="order.zhipiao" value="${user.account }">
									</div> 
									<div class="form-con col-25">
										<label  class="labe-l must">始发站 :</label>
										<input id="verror" type="text" name="order.fazhan" value="${user.company }" readonly="readonly" class="txt">
									</div>
									<div class="form-con col-25 dzshhdBox" style="display:table; width:23.5%; ">
										<label  class="labe-l" style="display: table-cell;vertical-align: middle;">发站网点 :</label>
										 <input name="order.shhd" id='shhd'  type="text"  class="txt selectInput" value="${transportOrderEntry.order.shhd}">
							    	       <ul class="selectUl shhd"   style="max-width:80%;postion:absolute;top:20px">
							    	       </ul>									 
								
									</div>
									<div class="form-con  col-25" style="display:table; width:23.5%;">
									   <label class="labe-l must" style="display: table-cell;vertical-align: middle;">到站 :</label>
									   <input name="order.daozhan"  id='daozhan' type="text"   class="txt selectInput" value="${transportOrderEntry.order.daozhan}">
							    	   <ul class="selectUl" style="max-width:80%;postion:absolute;top:20px">
											 <c:forEach items="${daozhanList}" var="i" varStatus="status">
													<li class="dzLi" <c:if test="${transportOrderEntry.order.daozhan==i.companyName}">selected="selected" </c:if> value="${i.companyName}">${i.companyName}</li>
											 </c:forEach>
							    	   </ul>
									  
										<%-- <label class="labe-l must" style="display: table-cell;vertical-align: middle;">到站 :</label>
										 <div id="daozhanSelect" style="display:table-cell;">
											<select id='daozhan' name="order.daozhan" class="txt"  value="${transportOrderEntry.order.daozhan}">
												<option value=""></option>
												<c:forEach items="${daozhanList}" var="i" varStatus="status">
													<option <c:if test="${transportOrderEntry.order.daozhan==i.companyName}">selected="selected" </c:if> value="${i.companyName}">${i.companyName}</option>
												</c:forEach>
											</select>
										</div>  --%>
									</div>
									
								</div>
								<div class="clearfix" >
								    <div class="form-con col-25 dzshhdBox" style="display:table; width:23.5%; ">
										<label  class="labe-l" style="display: table-cell;vertical-align: middle;">到站网点 :</label>
										 <input name="order.dzshhd" id='dzshhd'  type="text"  class="txt selectInput" value="${transportOrderEntry.order.dzshhd}">
							    	       <ul class="selectUl dzwd"   style="max-width:80%;postion:absolute;top:20px">
							    	       </ul>
										 
										 <%-- <div id="dzshhdSelect" style="display:table-cell;">
											<select id='dzshhd' name="order.dzshhd" class="txt" value="${transportOrderEntry.order.dzshhd}">
											</select>
										</div>  --%>
										<%-- <input type="text" id="dzshhd" name="order.dzshhd" value="${transportOrderEntry.order.dzshhd}" class="txt"> --%>
									</div>
									<div class="form-con  col-25">
										<label class="labe-l must">始发地 :</label>
										<input class="txt" id="beginPlace" type="text" name="order.beginPlacename" value="${transportOrderEntry.order.beginPlacename}">
									</div>
									<div class="form-con  col-25">
										<label class="labe-l must">目的地 :</label>
										<input class="txt" type="text" name="order.endPlacename" value="${transportOrderEntry.order.endPlacename}">
									</div>
								</div>
							</div>
						</div>
						<!--客户信息-->
						<div class="sub-part"> 
							<h4>客户信息</h4>
							<div class="sub-part-form">
								<div class="clearfix"> 
									<div class="form-con col-50">
										<label for="fhdwmch" class="labe-l must">客户名称 :</label>
										<input class="txt" type="text" id="fhdwmch" name="order.fhdwmch" value="${transportOrderEntry.order.fhdwmch}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">客户编码 :</label>
										<input class="txt" type="text" id="khbm" name="order.khbm" value="${transportOrderEntry.order.khbm}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">行业类别 :</label>
										<select name="order.fhkhhy" class="txt" value="${transportOrderEntry.order.fhkhhy}">
											<c:forEach items="${fhkhhyList}" var="i" varStatus="status">
												<option value="${i.industryName }">${i.industryName }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-50">
										<label class="labe-l must">客户地址 :</label>
										<input type="text" id="fhdwdzh" name="order.fhdwdzh" class="txt" value="${transportOrderEntry.order.fhdwdzh}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">座机 :</label>
										<input type="text" id="fhdwlxdh" name="order.fhdwlxdh" class="txt" value="${transportOrderEntry.order.fhdwlxdh}" maxlength="20">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">手机号 :</label>
										<input type="text" id="fhdwyb" name="order.fhdwyb" class="txt" value="${transportOrderEntry.order.fhdwyb}">
									</div>
									
								</div>
							</div>
						</div>
						<!--收货人信息-->
						<div class="sub-part"> 
							<h4>收货人信息</h4>
							<div class="sub-part-form">
								<div class="clearfix">
									<div class="form-con col-50">
										<label class="labe-l must">名称 :</label>
										<input type="text" name="order.shhrmch" class="txt" value="${transportOrderEntry.order.shhrmch}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">手机号 :</label>
										<input type="text" name="order.shhryb" class="txt" value="${transportOrderEntry.order.shhryb}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">座机:</label>
										<input type="text" name="order.shhrlxdh"  value="${transportOrderEntry.order.shhrlxdh}" class="modify-info txt">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-50">
										<label class="labe-l must">省市区 :</label>
										<input type="text" name="shrProvinces" value="${transportOrderEntry.shrProvinces}" class="txt">
									</div>
									<div class="form-con col-50">
										<label class="labe-l must">地址 :</label>
										<input type="text" name="order.shhrdzh" class="txt" value="${transportOrderEntry.order.shhrdzh}">
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
	       									<input class='cdz' id="order.fwfs" name='order.fwfs' type="radio" value='0' real="${transportOrderEntry.order.fwfs}"/><label>仓到站</label>
											<input class='cdc' name='order.fwfs' type="radio" value='1' real="${transportOrderEntry.order.fwfs}"/><label>仓到仓</label>
											<input class='zdc' name='order.fwfs' type="radio" value='2' real="${transportOrderEntry.order.fwfs}"/><label>站到仓</label>
											<input class='zdz' name='order.fwfs' type="radio" value='3' real="${transportOrderEntry.order.fwfs}"/><label>站到站</label>
											</div>
										</div>
									</div>
									<div class="form-con col-25" style="display:table; width:23.5%;">
										<label class="labe-l must" style="display: table-cell;vertical-align: middle;">运输方式 :</label>
										 <div id="transSelect"   style="display:table-cell;">
										    <select name="order.ysfs" class="txt" value="${transportOrderEntry.order.ysfs}">
											   <c:forEach items="${ysfsList}" var="i" varStatus="status">
												   <option value="${i.wayName }">${i.wayName }</option>
											   </c:forEach>
										    </select> 
										</div>
									</div>	
									<div class="form-con col-25">
										<label class="labe-l">运输天数 :</label>
										<input type="text" name="order.daodatianshu" class="txt" value="${transportOrderEntry.order.daodatianshu}">
									</div>	
								</div>
								<div class="clearfix"> 
<!-- 									<div class="form-con col-25"> -->
<!-- 										<label class="labe-l">运输号码 :</label> -->
<%-- 										<input type="text" name="order.yshhm" class="txt" value="${transportOrderEntry.order.yshhm}"> --%>
<!-- 									</div> -->
									<div class="form-con col-50">
										<label class="labe-l must">是否返单 :</label>
										<div class="txt radioBox" id="radioBox_sffd">
											<div>
												<input name='order.isfd' type="radio" value='1' real="${transportOrderEntry.order.isfd}"/><label>普通返单</label>
												<input name='order.isfd' type="radio" value='2' real="${transportOrderEntry.order.isfd}"/><label>电子返单</label>
												<input name='order.isfd' type="radio" value='0' real="${transportOrderEntry.order.isfd}"/><label>无</label>					
											</div>
										</div>
									</div>
									<div class="form-con col-50">
										<label class="labe-l">返单要求 :</label>
										<input type="text" name="order.fdyq" class="txt" value="${transportOrderEntry.order.fdyq}">
									</div>
									<!-- <div class="form-con col-25">
										<label class="labe-l must">作业方式 :</label>
										<select name="order.zyfs" class="txt" value="${transportOrderEntry.order.zyfs}">
											<option value="0">人工</option>
											<option value="1">机械</option>
										</select>
									</div> -->
									
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l must">付费方式 :</label>
										 <div class='txt checkBox'>
											<div>
											<input type="checkbox" value='1'  name="order.fzfk" real="${transportOrderEntry.order.fzfk}"/><label class="hCheckBox">发付</label>
											<input type="checkbox" value='1'  name="order.dzfk" real="${transportOrderEntry.order.dzfk}"/><label class="hCheckBox">到付</label>
											</div>
										</div> 
									</div>
									<div class="form-con col-25" style="min-width:400px">	
										<div class="form-con"  style="margin-left: 0px;">
											<label class="labe-l" style="width: 70px">代客包装:</label>
												<input class='modify-info select-box'  type="checkbox" value='1'  name="order.daikebaozhuang" real="${transportOrderEntry.order.daikebaozhuang}"/>										
										</div>
										
										<div class="form-con"  style="margin-left: 0px;">
											<label class="labe-l" style="width: 45px">纸箱:</label>
												<input class='modify-info select-box' type="checkbox" value='1'  name="order.dkbzCarton" real="${transportOrderEntry.order.dkbzCarton}"/>
										</div>
										
										<div class="form-con"  style="margin-left: 0px;">
											<label class="labe-l" style="width: 45px">木箱:</label>
	
												<input class='modify-info select-box' type="checkbox" value='1'  name="order.dkbzWooden" real="${transportOrderEntry.order.dkbzWooden}"/>
	
										</div>
										
										<div class="form-con"  style="margin-left: 0px;">
											<label class="labe-l" style="width: 70px">代客装卸:</label>
	
												<input class='modify-info select-box' type="checkbox" value='1'  name="order.daikezhuangxie" real="${transportOrderEntry.order.daikezhuangxie}"/>
		
										</div>
										
										<div class="form-con"  style="margin-left: 0px;">
											<label class="labe-l" style="width: 70px">送货上楼:</label>
	
												<input class='modify-info select-box' type="checkbox" value='1'  name="order.songhuoshanglou" real="${transportOrderEntry.order.songhuoshanglou}"/>
			
										</div>
									</div>
									<div class="form-con col-25" style="">
										<label class="labe-l">代收款 :</label>
											<div>
											<input type="text" name="order.dashoukuan" id="waybill_dsk"  onblur="dskChange();" class="txt" value="${transportOrderEntry.order.dashoukuan}"/>
											</div>
									</div>
								</div>
									
									<%-- <div class="form-con col-25" style="display: none">
										<label class="labe-l">办单费 :</label>
										<input type="text" name="order.bandanfei" class="txt" value="${transportOrderEntry.order.bandanfei}">
									</div> --%>
								</div>		
								<div class="clearfix" style="margin-top: 15px;"> 
									<div class="form-con col-25">
										<label class="labe-l">承运人签名 :</label>
										<input type="text" name="order.hyy" class="txt" value="${transportOrderEntry.order.hyy}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">托运人签名 :</label>
										<input type="text" name="order.tyrqm" class="txt" value="${transportOrderEntry.order.tyrqm}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">客户单号 :</label>
										<input type="text" name="order.khdh" class="txt" value="${transportOrderEntry.order.khdh}">
									</div>
									<div class="form-con col-25">
										<div class='txt checkBox noBorder'>
	       									<div>
	       									<input type="checkbox" value='1' name="releaseWaiting" <c:if test="${transportOrderEntry.releaseWaiting==1}">checked="checked"</c:if> real="${transportOrderEntry.releaseWaiting}"/>
	       									<label class="hCheckBox"><font color="red" style="font-size:14px;">等托运人指令发货</font></label>
											</div>
										</div>
									</div>
								</div>
								<div class="clearfix" style="margin-top: 15px;"> 
									<!-- <div class="form-con col-50">
										<label class="labe-l">包装说明 :</label>
										<input type="text" name="order.hwzht" class="txt" value="${transportOrderEntry.order.hwzht}">
									</div> -->
									<div class="form-con col-50">
										<label class="labe-l">特别说明 :</label>
										<input type="text" name="order.tiebieshuoming" class="txt" value="${transportOrderEntry.order.tiebieshuoming}">
									</div>
									<div class="form-con  col-25">
										<label  class="labe-l">托运时间 :</label>
										<input type="text" id="fhshj" name="order.fhshj" class="autofhshj txt datetime-picker" value="${transportOrderEntry.fhshj}">
									</div>
								</div>
							</div>
						</div>
						
						<!--货物信息-->
						<div class="sub-part"> 
							<h4>货物信息</h4>
							<div class="sub-part-form wtable">
								<div class="handle">
									<a href="javascript:;" class="handle-add">添加</a>
									<a href="javascript:;" class="handle-delete">删除</a>
								</div>
								<table>
									<thead>
										<tr>
											<th width="5%" style="text-align:center;">序号</th>
											<th class="need-fill" style="text-align:center;">品名</th>
											<th  style="text-align:center;">型号</th>
											<th class="need-fill" width="6%"  style="text-align:center;">件数</th>
											<th width="6%"  style="text-align:center;">包装</th>
											<th class="need-fill" width="6%"  style="text-align:center;">重量(吨)</th>
											<th class="need-fill" width="6%"  style="text-align:center;">体积(立方)</th>
											<th width="6%"  style="text-align:center;">保价金额</th>
											<th width="6%"  style="text-align:center;">重货叉车量</th>
											<th width="6%"  style="text-align:center;">重货装卸量</th>
											<th width="6%"  style="text-align:center;">轻货叉车量</th> 
											<th width="6%"  style="text-align:center;">轻货装卸量</th> 
											<th class="need-fill" width="7%"  style="text-align:center;">计费方式</th>
											<th width="8%"  style="text-align:center;">运价</th>
											<th width="6%" class="noBorder"  style="text-align:center;"><a href="javascript:;" class="addTr"></a></th>
										</tr>
									</thead>
									<tbody id="details-tbody">
										<c:if test="${transportOrderEntry.details!= null && fn:length(transportOrderEntry.details) > 0}">
											<c:forEach items="${transportOrderEntry.details}" var="d" varStatus="s">
												<tr>											
													<td><input type="text" readonly="readonly" class="ydxzh" value="${d.ydxzh}" name="details[${s.index}].ydxzh"/></td>
													<td><input type="text" class="edit-input pinming" name="details[${s.index}].pinming" value="${d.pinming}"/></td>
													<td><input type="text" class="edit-input xh" name="details[${s.index}].xh" value="${d.xh}"/></td>
													<td><input type="text" class="edit-input jianshu" name="details[${s.index}].jianshu" value="${d.jianshu}"/></td>
													<td><input type="text" class="edit-input bzh" name="details[${s.index}].bzh" value="${d.bzh}"/></td>
													<td><input type="text" class="edit-input zhl" name="details[${s.index}].zhl" value="${d.zhl}"/></td>
													<td><input type="text" class="edit-input tiji" name="details[${s.index}].tiji" value="${d.tiji}"/></td>
													<td><input type="text" class="edit-input tbje" name="details[${s.index}].tbje" value="${d.tbje}"/></td>
													<td><input type="text" class="edit-input zchl" name="details[${s.index}].zchl" value="${d.zchl}"/></td><!-- 重货叉车量 -->
													<td><input type="text" class="edit-input zzxl" name="details[${s.index}].zzxl" value="${d.zzxl}"/></td><!-- 重货装卸量 -->
													<td><input type="text" class="edit-input qchl" name="details[${s.index}].qchl" value="${d.qchl}"/></td><!-- 轻货叉车量 -->
													<td><input type="text" class="edit-input qzxl" name="details[${s.index}].qzxl" value="${d.qzxl}"/></td><!-- 轻货装卸量 -->
													<td>
														<select class="edit-input jffs" name="details[${s.index}].jffs" value="${d.jffs}">
															<option value="0">重货</option>
															<option value="1">轻货</option>
															<option value="2">按件</option>
														</select>
													</td>
													<td><input type="text" class="edit-input yunjia" name="details[${s.index}].yunjia" value="${d.yunjia}"/></td>
													<td class="noBorder"><a href="javascript:;" class="removeTr"></a></td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${transportOrderEntry.details == null || fn:length(transportOrderEntry.details) <= 0}">
											<tr>											
												<td><input type="text" readonly="readonly" class="ydxzh" value="1" name="details[0].ydxzh"/></td>
												
												<c:if test="${not empty pinmingList }">
													<td><input type="text" class="edit-input pinming" name="details[0].pinming"/></td>
												</c:if>
												<c:if test="${empty pinmingList }">
													<td><input type="text" class="edit-input pinming" name="details[0].pinming"/></td>
												</c:if>
												
												<td><input type="text" class="edit-input xh" name="details[0].xh"/></td>
												<td><input type="text" class="edit-input jianshu" name="details[0].jianshu"/></td>
												<td><input type="text" class="edit-input bzh" name="details[0].bzh"/></td>
												<td><input type="text" class="edit-input zhl" name="details[0].zhl"/></td>
												<td><input type="text" class="edit-input tiji" name="details[0].tiji"/></td>
												<td><input type="text" class="edit-input tbje" name="details[0].tbje"/></td>
												<td><input type="text" class="edit-input zchl" name="details[0].zchl"/></td>
												<td><input type="text" class="edit-input zzxl" name="details[0].zzxl"/></td>
												<td><input type="text" class="edit-input qchl" name="details[0].qchl"/></td>
												<td><input type="text" class="edit-input qzxl" name="details[0].qzxl"/></td>
												<td>
													<select class="edit-input jffs" name="details[0].jffs">
														<option value="0">重货</option>
														<option value="1">轻货</option>
														<option value="2">按件</option>
													</select>
												</td>
												<td><input type="text" class="edit-input yunjia" name="details[0].yunjia"/></td>
												<td class="noBorder"><a href="javascript:;" class="removeTr"></a></td>
											</tr>
										</c:if>
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
					<div class="part" style="margin-top:20px;">
						<h2>财凭录入<input class='isedit' style='cursor:pointer;display: inline-block; padding: 5px; color: #fff; margin-left: 14px; background: #38c76e;;border-radius: 5px; font-size: 12px;' type='button' value='开启'></h2> 
						<input type="hidden" id="hasFinance" name="certify.hasFinance" class='aaaa'> 
						<div class="sub-part editCaiping" style='display:none;'> 
							<div class="sub-part-form">
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">保率:</label>
										<input type="text" name="price.premiumRate" class="txt" value="${transportOrderEntry.price.premiumRate}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">叉车费单价:</label>
										<input type="text" name="price.zhjxzyf" class="txt" value="${transportOrderEntry.price.zhjxzyf}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">轻货装卸单价:</label>
										<input type="text" name="price.qhzhxfdj" class="txt" value="${transportOrderEntry.price.qhzhxfdj}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">重货装卸单价:</label>
										<input type="text" name="price.zhzhxfdj" class="txt" value="${transportOrderEntry.price.zhzhxfdj}">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">保险费 :</label>
										<input type="text" class="txt" style="background:#F5F5F5" readonly="readonly" name="certify.premiumFee" placeholder="自动计算:保率*明细保价" value="${transportOrderEntry.certify.premiumFee}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">叉车费 :</label>
										<input type="text" class="txt" style="background:#F5F5F5" readonly="readonly" placeholder="自动计算" name="certify.forkliftFee" value="${transportOrderEntry.certify.forkliftFee}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">装卸费 :</label>
										<input type="text" style="background:#F5F5F5" name="certify.zhuangxiefei" class="txt" placeholder="自动计算:(轻+重)装卸单价*装卸量" readonly="readonly" value="${transportOrderEntry.certify.zhuangxiefei}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运费合计 :</label>
										<input type="text" style="background:#F5F5F5" class="txt" readonly="readonly" placeholder="自动计算" name="certify.transportTotalFee" value="${transportOrderEntry.certify.transportTotalFee }">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">重货运价 :</label>
										<input type="text" class="txt" name="certify.weightprice" value="${transportOrderEntry.certify.weightprice}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">轻货运价 :</label>
										<input type="text" class="txt" name="certify.lightprice" value="${transportOrderEntry.certify.lightprice}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">按件运价 :</label>
										<input type="text" class="txt" name="certify.piecework" value="${transportOrderEntry.certify.piecework}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">办单费 :</label>
										<input type="text" class="txt" name="certify.invoice" value="${transportOrderEntry.certify.invoice}">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l must">费用名称 :</label>
										<input type="text" class="txt" name="certify.otherFeeName" id="otherFee" value="${transportOrderEntry.certify.otherFeeName==null?'其他费用':transportOrderEntry.certify.otherFeeName}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">代收货款 :</label>
										<input type="text" class="txt" name="certify.receipt" id="finance_dsk" value="${transportOrderEntry.certify.receipt}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">上门取货费 :</label>
										<input type="text" class="txt catch-goods"  placeholder="站到仓，站到站 上门收货费为0" name="certify.tohome" value="${transportOrderEntry.certify.tohome}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">送货上门费 :</label>
										<input type="text" class="txt send-goods" placeholder="仓到站，站到站送货上门费为0" name="certify.delivery" value="${transportOrderEntry.certify.delivery}">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l" id="s_fee">${transportOrderEntry.certify.otherFeeName==null?'其他费用':transportOrderEntry.certify.otherFeeName}</label>
										<input type="text" class="txt" name="certify.other" value="${transportOrderEntry.certify.other}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">是否取整</label>
										<div class="txt radioBox">
	       									<div>
	       									<c:choose>
	       										<c:when test="${empty transportOrderEntry.order.ydbhid}">
	       											<input name='order.jshhj' type="radio" value='1' checked="checked"/><label>不取整</label>
													<input name='order.jshhj' type="radio" value='0'/><label>取整</label>
	       										</c:when>
	       										<c:otherwise>
	       											<c:if test="${transportOrderEntry.order.jshhj==1}">
	       												<input name='order.jshhj' type="radio" value='1' checked="checked"/><label>不取整</label>
														<input name='order.jshhj' type="radio" value='0'/><label>取整</label>
	       											</c:if>
	       											<c:if test="${transportOrderEntry.order.jshhj==0}">
	       												<input name='order.jshhj' type="radio" value='1'/><label>不取整</label>
														<input name='order.jshhj' type="radio" value='0' checked="checked"/><label>取整</label>
	       											</c:if>
	       										</c:otherwise>
	       									</c:choose>
											</div>
										</div>
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">合计费用 :</label>
										<input type="text" readonly="readonly" class="txt" name="certify.cost" value="${transportOrderEntry.certify.cost }">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="con-btn" id="con-btn" style="text-align:center;margin-top:20px;">
					<input type="hidden" name="saveLogo"/>
					<a href="javascript:;" id="btn-save" class="btn-save btn-submit" value='1'>保存</a>
					<a style="margin-left: 10px;" href="javascript:;" class="btnnn-cansel" id="clearalls">清空</a>
					<a style="margin-left: 10px;" id="btn-return" class="btn-save">返回</a>
				</div>
			</div>
		</form>
<%@ include file="/views/transport/include/floor.jsp"%>
<script type="text/javascript">
var companyName = '${user.companyString}';
</script>
<script src="${ctx_static}/common/jquery/plugins/jquery.validate.min.js"></script>
<script src="${ctx_static}/transport/convey/js/waybillselect.js?v=91"></script>
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script type="text/javascript">
layui.use(['form','layer'],function(){
	 var layer= layui.layer;
});
//EL表达式获取数据
var base_url = '${base_url}';
var pinmingList = '${pinmingList}';
var dzList='${daozhanList}';
var editflag = true;
var pointValue = '${transportOrderEntry.order.dzshhd}';
var hasFinance = '${transportOrderEntry.certify.hasFinance}';
var trIndex = -2;

function dskChange(){
	$("#finance_dsk").val($("#waybill_dsk").val());
}

if(hasFinance==1){
	$('.isedit').val('隐藏').removeClass('disabledSpan');
	editflag = true; 
	$('.editCaiping').show();
	$('.aaaa').val('1');
}else{
	$('.isedit').val('开启').removeClass('disabledSpan');
	editflag = false;
	$('.editCaiping').hide();
	$('.aaaa').val('0');
}
/*是否显示财凭录入*/
$('.isedit').click(function(){
	if(editflag){
		//开启——》隐藏
		$(this).val('开启').removeClass('disabledSpan');
		$('.editCaiping').slideUp();
		$('.aaaa').val('0');
		editflag = false;
	}else{
		//隐藏 -> 开启
		dskChange();
		$(this).val('隐藏').removeClass('disabledSpan');
		$('.editCaiping').slideDown();
		$('.aaaa').val('1');
		editflag = true;
	}
})
$(document).ready(function(){
	getPoints();
	selectList();	
	WaybillCheckData.initDataCheck();
	var daozhanValue = $("#daozhan").val();
	$("#beginPlace").focus(function(){
		$(".selectUl").hide();
	});
	$("#otherFee").on("input propertychange",function(){
		$("#s_fee").text($(this).val());
	})
});
</script>
<script src="${ctx_static}/transport/convey/js/waybillvalidator.js?t=11"></script>
<script src="${ctx_static}/transport/convey/js/waybillbutton.js?t=22"></script>
<script src="${ctx_static}/transport/convey/js/cost.js?t=33"></script>
<script src="${ctx_static}/transport/convey/js/waybillcalc.js?t=442"></script>
</body>
</html>