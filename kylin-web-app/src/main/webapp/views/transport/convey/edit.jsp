<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
<head>
<script src="${ctx_static}/common/jquery/citypicker/jquery.js"></script>
		<title>录入运单数据</title>
		<%@ include file="/views/transport/include/head.jsp"%>
		<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
		<style type="text/css">
			#btn-cansel{ 
			   cursor:pointer;
		    }
			#vsuccess{
				-moz-box-sizing: border-box;  
                -webkit-box-sizing: border-box; 
                -o-box-sizing: border-box; 
                -ms-box-sizing: border-box; 
                box-sizing: border-box;
                border:2px solid #cccccc;
			}
		  	.verror{
		       -moz-box-sizing: border-box;  
                -webkit-box-sizing: border-box; 
                -o-box-sizing: border-box; 
                -ms-box-sizing: border-box; 
                box-sizing: border-box;
                border:2px solid #FF7378;
                border-bottom: 2px solid #FF7378;
		  	}
		  	.btn-new,.btn-save{ 
			 	cursor:pointer;
		  	}
		  	.loading{
				display: none;
			    width: 100%;
			    height: 100%;
			    position: fixed;
			    left: 0;
			    top: 0;
			    background-color: rgba(0,0,0,.3);
			    z-index: 999;
			}
			.loading img{
			    width: 100px;
			    height: 100px;
			    position: absolute;
			    left: 50%;
			    top: 50%;
			    margin-left: -50px;
			    margin-top: -50px;
			}
			#details-tbody .easy-autocomplete{
				width: 100% !important;
				position: relative;
			}	
			#details-tbody .easy-autocomplete input{
				border: none !important;
				width: 100% !important;
			}
			.handle-edit{
				width: auto;
    			text-align: center;
			    display: inline-block;
			    padding: 5;
			    border-radius: 4px;
			    font-size: 12px;
			    text-align: center;
			    color: #fff;
			    margin-top: 8px;
    			margin-left: 12px;
			}
			.handle-edit {
			    background: #38c76e no-repeat 6px center;
			}
			.title-name{float: left; border: none !important;}
			.title-line{border-bottom: 2px solid #ebeff5;clear:both;}
			.table-select{width: 100% !important;}
			#content>.con-btn>a {
			  	width: 80px;
			  	padding: 7px 0;
			  	font-size: 14px;
			}
			.dropdown-display, .dropdown-display-label{font-size: 12px !important;}
			.txt.checkBox div{
					width: 300px;
					/* padding-top: 0px; */
				}
				
	.selectUl,com_select {
		margin-top:5px;
	    display: none;
	    z-index: 1;
	    background-color: #fff;
	    border: 1px solid #eee;
	    display: none;
	    width: 100%;
	    clear: both;
	    overflow: auto;
	    position: absolute;
	    max-height: 250px;
	}
.selectUl li,com_select li {
    padding:8px 0 8px 8px;
    cursor: pointer;
}
.selectUl li:hover{background:#EBEBEB;}
			@media screen and (max-width: 1400px){
				.txt.checkBox div{
					width: 230px !important;
					/* padding-top: 0px; */
				}
				font{
					font-size: 12px !important;
				}
			}
		</style>
	</head>
	
	<body>
	<div class="loading" id="loading" style='display:none;'>
		<img src="${ctx_static}/common/images/loading.gif">
	</div>
			<div id="content">
			<form  class="form-inline" id='form' role="form" action="${base_url}/transport/convey/edit/save" method="post">
				<div class="con-main">
					<div class="part">
						<h2 class='title-name'>运单信息</h2>
						<a href="javascript:;" data-message='${transportOrderEditPermissions.message}' data-status='${transportOrderEditPermissions.isEdit}' class="handle-edit" id='editOrder'>修改运单信息</a>
						<hr class='title-line'>
						<div class="sub-part">  
							<div class="sub-part-form">
								<div class="clearfix">
									<!-- <div class="col-25" style='opacity: ;'> -->
										<input value="0" type="hidden" name="modifyOrderIdentification" id="modify_order"/>
									<!-- </div> -->
									<div class="form-con col-25">
										<label class="labe-l must">运单编号 :</label>
										<input type="text" name="order.ydbhid" readonly="readonly" class="txt waybillId" 
											 placeholder="请输入10或12位运单编号" value="${transportOrderEntry.order.ydbhid}"/>
									</div>
									<div class="form-con  col-25" style="display: none">
										<label  class="labe-l">录单员 :</label>
										<input type="text" readonly="readonly" class="txt modify-info" value="${user.userName }">
										<input type="hidden" name="order.zhipiao" value="${user.account }">
									</div>
									<div class="form-con col-25">
										<label  class="labe-l must">始发站 :</label>
										<input id="verror" type="text" name="order.fazhan" value="${transportOrderEntry.order.fazhan }" readonly="readonly" class="txt modify-info">
									</div>
									<div class="form-con col-25" style="display:table; width:23.5%; ">
										<label  class="labe-l" style="display: table-cell;vertical-align: middle;">发站网点 :</label>
										 <input name="order.shhd" id='shhd'  type="text"  class="txt selectInput" readonly="readonly" value="${transportOrderEntry.order.shhd}">
							    	       <ul class="selectUl shhd"   style="max-width:80%;postion:absolute;top:20px">
							    	       </ul>									 
								
									</div>
									<div class="form-con  col-25" style="display:table; width:23.5%;">
										<label  class="labe-l must" style="display: table-cell;vertical-align: middle;">到站 :</label>
										 <div id="daozhanSelect" style="display:table-cell;">
											<select id='daozhan' name="order.daozhan" disabled='' class="txt modify-info select-box" value="${transportOrderEntry.order.daozhan}">
												<c:forEach items="${daozhanList}" var="i" varStatus="status">
													<option value="${i.companyName}">${i.companyName}</option>
												</c:forEach>
											</select>
										</div> 
									</div>

								</div>
								<div class="clearfix" >
								    <div class="form-con col-25 dzshhdBox" style='display: table;'>
										<label  class="labe-l" style="display:table-cell;">到站网点 :</label>
										<div id="dzshhdSelect" style="display:table-cell;">
										    <input type="text"  id='dzshhd' name="order.dzshhd"  class="txt modify-info select-box selectInput" value="${transportOrderEntry.order.dzshhd}">
											<ul class="selectUl dzshhd"   style="max-width:70%;postion:absolute;top:20px">
							    	        </ul>
											<%-- <select id='dzshhd' name="order.dzshhd" class="txt modify-info select-box" value="${transportOrderEntry.order.dzshhd}">
												<option value="${transportOrderEntry.order.dzshhd}">${transportOrderEntry.order.dzshhd}</option>
											</select> --%>
										</div> 
									</div>
									<div class="form-con  col-25">
										<label class="labe-l must">始发地 :</label>
										<input class="txt  modify-info" type="text" name="order.beginPlacename" value="${transportOrderEntry.order.beginPlacename}">
									</div>
									<div class="form-con  col-25">
										<label class="labe-l must">目的地 :</label>
										<input class="txt modify-info" type="text" name="order.endPlacename" value="${transportOrderEntry.order.endPlacename}">
									</div>
								</div>
							</div>
						</div>
						<!--客户信息-->
				<div class="sub-part"> 
							<h4>客户信息</h4>
							<div class="sub-part-form">
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label for="fhdwmch" class="labe-l must">客户名称 :</label>
										<input class="txt modify-info" type="text" id="fhdwmch" name="order.fhdwmch" value="${transportOrderEntry.order.fhdwmch}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">客户编码 :</label>
										<input class="txt modify-info" type="text" id="khbm" name="order.khbm" value="${transportOrderEntry.order.khbm}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">行业类别 :</label>
										<select name="order.fhkhhy" class="modify-info txt select-box" disabled='' value="${transportOrderEntry.order.fhkhhy}">
											<c:forEach items="${fhkhhyList}" var="i" varStatus="status">
												<option value="${i.industryName }">${i.industryName }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-50">
										<label class="labe-l must">客户地址 :</label>
										<input type="text" id="fhdwdzh" name="order.fhdwdzh" class="txt  modify-info" value="${transportOrderEntry.order.fhdwdzh}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">座机 :</label>
										<input type="text" id="fhdwlxdh" name="order.fhdwlxdh" class="txt modify-info" value="${transportOrderEntry.order.fhdwlxdh}" maxlength="20">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">手机号 :</label>
										<input type="text" id="fhdwyb" name="order.fhdwyb" class="txt modify-info" value="${transportOrderEntry.order.fhdwyb}">
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
										<input type="text" name="order.shhrmch" class="modify-info txt" value="${transportOrderEntry.order.shhrmch}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">手机号:</label>
										<input type="text" name="order.shhryb" class="modify-info txt" value="${transportOrderEntry.order.shhryb}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">座机:</label>
										<input type="text" name="order.shhrlxdh"  value="${transportOrderEntry.order.shhrlxdh}" class="modify-info txt">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-50">
										<label class="labe-l must">省市区 :</label>
										<input type="text" name="shrProvinces" value="${transportOrderEntry.shrProvinces}" class="txt modify-info">
									</div>
									<div class="form-con col-50">
										<label class="labe-l must">地址 :</label>
										<input type="text" name="order.shhrdzh" class="modify-info txt" value="${transportOrderEntry.order.shhrdzh}">
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
										<div class="txt radioBox modify-info">
	       									<div class='radio-input' id='radioValue'>
	       									<label><input class='modify-info select-box cdz' disabled='' id="order.fwfs" name='order.fwfs' type="radio" value='0' real="${transportOrderEntry.order.fwfs }"/>仓到站</label>
											<label><input class='modify-info select-box cdc' disabled='' name='order.fwfs' type="radio" value='1' real="${transportOrderEntry.order.fwfs }"/>仓到仓</label>
											<label><input class='modify-info select-box zdc' disabled='' name='order.fwfs' type="radio" value='2' real="${transportOrderEntry.order.fwfs }"/>站到仓</label>
											<label><input class='modify-info select-box zdz' disabled='' name='order.fwfs' type="radio" value='3' real="${transportOrderEntry.order.fwfs }"/>站到站</label>
											</div>
										</div>
									</div>
									<div class="form-con col-25" style='display: table;'>
										<label class="labe-l must" style='display: table-cell;'>运输方式 :</label>
										<div id="conveyMethod" style="display:table-cell;">
											<select name="order.ysfs" class="txt modify-info select-box" disabled=''" value="${transportOrderEntry.order.ysfs}">
												<c:forEach items="${ysfsList}" var="i" varStatus="status">
													<option value="${i.wayName }">${i.wayName }</option>
												</c:forEach>
											</select>
										</div> 
										<%-- <select name="order.ysfs" class="txt modify-info" value="${transportOrderEntry.order.ysfs}">
											<c:forEach items="${ysfsList}" var="i" varStatus="status">
												<option value="${i.wayName }">${i.wayName }</option>
											</c:forEach>
										</select> --%>
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运输天数 :</label>
										<input type="text" name="order.daodatianshu" class="modify-info txt" value="${transportOrderEntry.order.daodatianshu}">
									</div>	
								</div>
								<div class="clearfix"> 
<!-- 									<div class="form-con col-25"> -->
<!-- 										<label class="labe-l">运输号码 :</label> -->
<%-- 										<input type="text" name="order.yshhm" class="txt" value="${transportOrderEntry.order.yshhm}"> --%>
<!-- 									</div> -->
									<div class="form-con col-50">
										<label class="labe-l must">是否返单 :</label>
										<div class="txt radioBox modify-info" id="radioBox_sffd">
											<div>
												<input class='modify-info select-box' disabled='' name='order.isfd' type="radio" value='1' real="${transportOrderEntry.order.isfd}"/><label>普通返单</label>
												<input class='modify-info select-box' disabled='' name='order.isfd' type="radio" value='2' real="${transportOrderEntry.order.isfd}"/><label>电子返单</label>
												<input class='modify-info select-box' disabled='' name='order.isfd' type="radio" value='0' real="${transportOrderEntry.order.isfd}"/><label>无</label>					
											</div>
										</div>
									</div>
									<div class="form-con col-50">
										<label class="labe-l">返单要求 :</label>
										<input type="text" name="order.fdyq" class="modify-info txt" value="${transportOrderEntry.order.fdyq}">
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
										 <div class='txt checkBox modify-info'>
											<div>
											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.fzfk" real="${transportOrderEntry.order.fzfk}"/><label class="hCheckBox">发付</label>
											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.dzfk" real="${transportOrderEntry.order.dzfk}"/><label class="hCheckBox">到付</label>
											</div>
										</div> 
									</div>
									<div class="form-con col-25">
										<label class="labe-l">代收款 :</label>
											<div>
											<input type="text" name="order.dashoukuan" id="daishou" class="txt modify-info" value="${transportOrderEntry.order.dashoukuan}"/>
											</div>
									</div>
									
									<div class="form-con" style="margin-left: 0px;">
										<label class="labe-l " style="width: 70px">代客包装:</label>

											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.daikebaozhuang" real="${transportOrderEntry.order.daikebaozhuang}"/>

									</div>
									
									<div class="form-con" style="margin-left: 0px;">
										<label class="labe-l " style="width: 70px">纸箱:</label>

											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.dkbzCarton" real="${transportOrderEntry.order.dkbzCarton}"/>

									</div>
									
									<div class="form-con" style="margin-left: 0px;">
										<label class="labe-l" style="width: 70px">木箱:</label>

											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.dkbzWooden" real="${transportOrderEntry.order.dkbzWooden}"/>

									</div>
									
									<div class="form-con" style="margin-left: 0px;">
										<label class="labe-l" style="width: 70px">代客装卸:</label>

											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.daikezhuangxie" real="${transportOrderEntry.order.daikezhuangxie}"/>
			
									</div>
									
									<div class="form-con" style="margin-left: 0px;">
										<label class="labe-l" style="width: 70px">送货上楼:</label>

											<input class='modify-info select-box' disabled='' type="checkbox" value='1'  name="order.songhuoshanglou" real="${transportOrderEntry.order.songhuoshanglou}"/>

									</div>
								</div>
								
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">承运人签名 :</label>
										<input type="text" name="order.hyy" class="txt modify-info" value="${transportOrderEntry.order.hyy}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">托运人签名 :</label>
										<input type="text" name="order.tyrqm" class="txt modify-info" value="${transportOrderEntry.order.tyrqm}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">客户单号 :</label>
										<input type="text" name="order.khdh" class="txt modify-info" value="${transportOrderEntry.order.khdh}">
									</div>
									<div class="form-con col-25">
										<div class='txt checkBox noBorder'>
	       									<div>
	       									<input style="cursor: not-allowed;vertical-align: -3px;background-color: rgb(235, 235, 228);" class='select-box' disabled='' type="checkbox" readonly="readonly" value='1' name="releaseWaiting" <c:if test="${transportOrderEntry.releaseWaiting==1}">checked="checked"</c:if> real="${transportOrderEntry.releaseWaiting}"/>
	       									<label class="hCheckBox"><font color="red" style="font-size:14px;">等托运人指令发货</font></label>
	       									<input style='vertical-align: -3px;' class='edit-hidden select-box' disabled='' type="checkbox" <c:if test="${transportOrderEntry.certify.receiveMoneyStatus==1}">checked="checked"</c:if> value='${transportOrderEntry.certify.receiveMoneyStatus}' readonly="readonly"/><label class="hCheckBox edit-hidden"><font color="red" style="font-size:14px;">款未付等通知</font></label>
											</div>
										</div>
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-50">
										<label class="labe-l">特别说明 :</label>
										<input type="text" name="order.tiebieshuoming" class="modify-info txt" value="${transportOrderEntry.order.tiebieshuoming}">
									</div>
									<div class="form-con  col-25">
										<label  class="labe-l">托运时间 :</label>
										<input type="text" class="txt datetime-picker modify-info" name="order.fhshj" value="<fmt:formatDate value='${transportOrderEntry.order.fhshj}' type='both' pattern='yyyy-MM-dd HH:mm'/>">
									</div>
								</div>
							</div>
						</div>
						
						<!--货物信息-->
						<div class="sub-part"> 
							<h4>货物信息</h4>
							<div class="sub-part-form wtable">
								<div class="handle">
									<a href="javascript:;" class="handle-add operation-btn" style='display:none;'>添加</a>
									<a href="javascript:;" class="handle-delete operation-btn" style='display:none;'>删除</a>
								</div>
								<table>
									<thead>
										<tr>
											<th width="5%" style="text-align:center;">序号</th>
											<th  style="text-align:center;">品名</th>
											<th  style="text-align:center;">型号</th>
											<th width="6%"  style="text-align:center;">件数</th>
											<th width="6%"  style="text-align:center;">包装</th>
											<th width="6%"  style="text-align:center;">重量(吨)</th>
											<th width="6%"  style="text-align:center;">体积(立方)</th>
											<th width="6%"  style="text-align:center;">保价金额</th>
											<th width="6%"  style="text-align:center;">重货叉车量</th>
											<th width="6%"  style="text-align:center;">重货装卸量</th>
											<th width="6%"  style="text-align:center;">轻货叉车量</th> 
											<th width="6%"  style="text-align:center;">轻货装卸量</th> 
											<th width="7%"  style="text-align:center;">计费方式</th>
											<th width="8%"  style="text-align:center;">运价</th>
											<th width="6%" class="noBorder operation-btn"  style="text-align:center;display: none"><a href="javascript:;" class="addTr"></a></th>
										</tr>
									</thead>
									<tbody id="details-tbody">
										<c:if test="${transportOrderEntry.details!= null && fn:length(transportOrderEntry.details) > 0}">
											<c:forEach items="${transportOrderEntry.details}" var="d" varStatus="s">
												<tr>											
													<td><input class='xizeId ydxzh' type="text" readonly="readonly" value="${d.ydxzh}" name="details[${s.index}].ydxzh"/></td>
													<td><input type="text" class="edit-input pinming modify-info" style="text-align:center;" name="details[${s.index}].pinming" value="${d.pinming}"/></td>
													<td><input type="text" class="edit-input modify-info xh" style="text-align:center;" name="details[${s.index}].xh" value="${d.xh}"/></td>
													<td><input type="text" class="edit-input modify-info jianshu" name="details[${s.index}].jianshu" value="${d.jianshu}"/></td>
													<td><input type="text" class="edit-input modify-info bzh" name="details[${s.index}].bzh" value="${d.bzh}"/></td>
													<td><input type="text" class="edit-input modify-info zhl" name="details[${s.index}].zhl" value="${d.zhl}"/></td>
													<td><input type="text" class="edit-input modify-info tiji" name="details[${s.index}].tiji" value="${d.tiji}"/></td>
													<td><input type="text" class="edit-input modify-info tbje" name="details[${s.index}].tbje" value="${d.tbje}"/></td>
													<td><input type="text" class="edit-input modify-info zchl" name="details[${s.index}].zchl" value="${d.zchl}"/></td><!-- 重货叉车量 -->
													<td><input type="text" class="edit-input modify-info zzxl" name="details[${s.index}].zzxl" value="${d.zzxl}"/></td><!-- 重货装卸量 -->
													<td><input type="text" class="edit-input modify-info qchl" name="details[${s.index}].qchl" value="${d.qchl}"/></td><!-- 轻货叉车量 -->
													<td><input type="text" class="edit-input modify-info qzxl" name="details[${s.index}].qzxl" value="${d.qzxl}"/></td><!-- 轻货装卸量 -->
													<td>
														<select class="edit-input modify-info table-select jffs"  name="details[${s.index}].jffs" value="${d.jffs}">
															<option value="0">重货</option>
															<option value="1">轻货</option>
															<option value="2">按件</option>
														</select>
													</td>
													<td><input type="text" class="edit-input modify-info yunjia" name="details[${s.index}].yunjia" value="${d.yunjia}"/></td>
													<td class="noBorder operation-btn" style='display:none;'><a href="javascript:;" class="removeTr"></a></td>
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
					<div class="part">
						<h2 class='title-name'>财凭信息</h2>
						<a href="javascript:;" data-message='${inputtingFinanceCertifyPermissions.message}' data-status='${inputtingFinanceCertifyPermissions.isEdit}' class="handle-edit" id='editCertify'>补录财凭信息</a>
						<hr class='title-line'>
						<div class="sub-part"> 
							<div class="sub-part-form">
								<div class="clearfix"> 
										<input value='0' type="hidden" id="modifyCertify" name="modifyCertifyIdentification" class="M-modify-status"/>
									<div class="form-con col-25">
										<label class="labe-l">保率:</label>
										<input type="text" name="price.premiumRate" class="txt modify-money" value="${transportOrderEntry.price.premiumRate}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">叉车费单价:</label>
										<input type="text" name="price.zhjxzyf" class="txt modify-money" value="${transportOrderEntry.price.zhjxzyf}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">轻货装卸单价:</label>
										<input type="text" name="price.qhzhxfdj" class="txt modify-money" value="${transportOrderEntry.price.qhzhxfdj}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">重货装卸单价:</label>
										<input type="text" name="price.zhzhxfdj" class="txt modify-money" value="${transportOrderEntry.price.zhzhxfdj}">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">保险费 :</label>
										<input type="text" name="certify.premiumFee" style='background-color: rgb(235, 235, 228);' readonly="readonly" class="txt" value="${transportOrderEntry.certify.premiumFee}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">叉车费 :</label>
										<input type="text" class="txt" style='background-color: rgb(235, 235, 228);' readonly="readonly" name="certify.forkliftFee" value="${transportOrderEntry.certify.forkliftFee}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">装卸费 :</label>
										<input type="text" style='background-color: rgb(235, 235, 228);' name="certify.zhuangxiefei" class="txt" readonly="readonly" value="${transportOrderEntry.order.zhuangxiefei}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运费合计 :</label>
										<input type="text" style='background-color: rgb(235, 235, 228);' class="txt" readonly="readonly" name="certify.transportTotalFee" value="${transportOrderEntry.certify.transportTotalFee }">
									</div>
								</div>
								<div class="clearfix"> 
									<div class="form-con col-25">
										<label class="labe-l">重货运价 :</label>
										<input type="text" class="txt modify-money" name="certify.weightprice" value="${transportOrderEntry.certify.weightprice}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">轻货运价 :</label>
										<input type="text" class="txt modify-money" name="certify.lightprice" value="${transportOrderEntry.certify.lightprice}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">按件运价 :</label>
										<input type="text" class="txt modify-money" name="certify.piecework" value="${transportOrderEntry.certify.piecework}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">办单费 :</label>
										<input type="text" class="txt modify-money" name="certify.invoice" value="${transportOrderEntry.certify.invoice}">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l must">费用名称 :</label>
										<input type="text" class="txt modify-money" name="certify.otherFeeName" id="otherFee" value="${transportOrderEntry.certify.otherFeeName==null?'其他费用':transportOrderEntry.certify.otherFeeName}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">代收货款 :</label>
										<input id='daishou-hidden' class="txt modify-money" name="certify.receipt" value="${transportOrderEntry.certify!=null?transportOrderEntry.certify.receipt:transportOrderEntry.order.dashoukuan}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">上门取货费 :</label>
										<input type="text" class="txt modify-money catch-goods" placeholder="站到仓，站到站 上门收货费为0" name="certify.tohome" value="${transportOrderEntry.certify==null?0:transportOrderEntry.certify.tohome}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">送货上门费 :</label>
										<input type="text" class="txt modify-money send-goods" placeholder="仓到站，站到站送货上门费为0" name="certify.delivery" value="${transportOrderEntry.certify==null?0:transportOrderEntry.certify.delivery}">
									</div>
								
								</div>
								<div class="clearfix">
									<div class="form-con col-25 must">
										<label class="labe-l" id="s_fee">${transportOrderEntry.certify.otherFeeName==null?'其他费用':transportOrderEntry.certify.otherFeeName} :</label>
										<input type="text" class="txt modify-money" name="certify.other" value="${transportOrderEntry.certify.other}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">是否取整</label>
										<div class="txt radioBox">
	       									<div>
	       									<c:if test="${transportOrderEntry.order.jshhj==1}">
	       										<input name='order.jshhj' type="radio" value='1' checked="checked"/><label>不取整</label>
												<input name='order.jshhj' type="radio" value='0'/><label>取整</label>
	       									</c:if>
	       									<c:if test="${transportOrderEntry.order.jshhj==0}">
	       										<input name='order.jshhj' type="radio" value='1'/><label>不取整</label>
												<input name='order.jshhj' type="radio" value='0' checked="checked"/><label>取整</label>
	       									</c:if>
											
											</div>
										</div>
									</div>
									<div class="form-con col-25">
										<label class="labe-l must">合计费用 :</label>
										<input type="text" class="txt" readonly="readonly" name="certify.cost" value="${transportOrderEntry.certify.cost }">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			
				<div class="con-btn">
<%-- 					<input type="hidden"  name="ydbhid" value="${searchModel.ydbhid }">
						<input type="hidden"  name="beginPlacename" value="${searchModel.beginPlacename }"> 
						<input type="hidden"  name="daozhan" value="${searchModel.daozhan }"> 
 						<input type="hidden"  name="yshhm" value="${searchModel.yshhm }"> 
						<input type="hidden"  name="searchdate" value="${searchModel.searchdate }"> 
						<input type="hidden"  name="status" value="${searchModel.status }"> --%>
					<a href="javascript:;" id="btn-save" class="btn-save btn-submit" value='1' style='display: none;'>保存</a>
					<!-- <a style="margin-left: 10px;" id="btn-return" class="btn-save">返回</a> -->
				</div>
			</form>
			</div>
		
<%@ include file="/views/transport/include/floor.jsp"%>
<script type="text/javascript">
var selectFazhan= new Array();
var base_url = '${base_url}';
var pinmingList = '${pinmingList}';
var bb = [];
var dzshhdList = new Array();
var clickNum = 0, monneyClick = 0;
var dzshhdList = '${dzshhdList}';
bb = dzshhdList.split(',');
var daozhanValue = $("#daozhan").val();
var hejijine = '${transportOrderEntry.certify.cost}';//合计金额
var pointValue = '${transportOrderEntry.order.dzshhd}';
var companyName = '${user.companyString}';

$(document).ready(function(){
	getPoint();
});
function stopPropagation(e) {//把事件对象传入
	if (e.stopPropagation){ //支持W3C标准 doc
		e.stopPropagation();
	}else{ //IE8及以下浏览器
		e.cancelBubble = true;
	}
}
function selectInput(obj){
	 obj.find("li").unbind("click").click(function(){
		var _target = $(this).parent("ul").siblings("input");
		var _this=$(this);
		_target.val(_this.text());
	 $(this).parent("ul").hide();		
	}); 
	 
	$(document).click(function(){
		obj.hide();
	});
}
function getPoint(){
	 $(".dzshhd").html(""); 
	$.ajax({
		url:base_url + "/transport/adjunct/arriveNetWork?daozhan="+encodeURI(encodeURI($("#daozhan").val())),
		type:'post',
		dataType:"json",
		success:function(data){	
			var html="";
			for (var i = 0; i < data.arriveNetWork.length; i++){
				if(pointValue && pointValue!='' && data.arriveNetWork[i]==pointValue){
					html+="<li selected='selected' value='"+data.arriveNetWork[i]+"'>"+data.arriveNetWork[i]+"</li>";
				}else{
					html+="<li value='"+data.arriveNetWork[i]+"'>"+data.arriveNetWork[i]+"</li>";
				}
			}
			  $(".dzshhd").empty().append(html);
			  dzshhdList=data.arriveNetWork;
			  selectList();
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
           if(XMLHttpRequest.readyState == 0) {
           //here request not initialization, do nothing
           } else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
               layer.msg("服务器忙，请重试!");
           } else {
               layer.msg("系统异常，请联系系统管理员!");
           }
       }
  });
}	

//输入匹配下拉框
function selectList(){
	$(".selectInput").each(function(){
		$(this).bind("focus click",function(e){
			$(this).siblings("ul").hide();
			stopPropagation(e);
			if($(this).siblings("ul").find("li").length>0){
		    	$(this).siblings("ul").show();
			    $(this).siblings("ul").find("li").show();
			}
			var obj=$(this).siblings("ul");
			selectInput(obj);
			
		});
		$(this).on("input propertychange",function(){
			var _this=$(this);
			var obj=_this.siblings("ul");
			var searchName =_this.val(); 
		    if (searchName == "") {
			    	obj.find("li").show();
			    } else {
			    	obj.find("li").each(function() {
			    		var s_name=""; 
			    		if(obj.hasClass("sjUl")){
			    		    s_name = $(this).attr("data"); 
			    		}else{
			    			s_name = $(this).attr("value");
			    		}    
			            if (s_name.indexOf(searchName) != -1) {
			              $(this).show();
			            }else {
			              $(this).hide();
			            }
			          });
			    }
		});	
		$(this).blur(function(){
			var _name=$(this).attr("name");
			var _text=$(this).val();
			if(_name=="order.daozhan"&& _text!=""){
				if($.inArray(_text, selectType)==-1){
					$.util.warning("到站不存在！");
					$(this).val("");
			  }
			};
	/* 		if(_name=="order.shhd"&& _text!=""){
				if($.inArray(_text, selectFazhan)==-1){
					$.util.warning("发站网点不存在！");
					$(this).val("");
			  }
			}; */
			
		})
	});	
}

//发站网点
function getPoints(){
	var daozhan=encodeURI(encodeURI($("#verror").val()));
	EasyAjax.ajax_Post_Json({
	  dataType: 'json',
	  url:base_url+'/transport/adjunct/latticePoint/'+daozhan,
	  errorReason: true,
	},function(res){
		//渲染数据
		var html="";
		for (var i = 0; i < res.latticePoint.length; i++){
	        html+="<li class='fzli' value='"+res.latticePoint[i]+"'>"+res.latticePoint[i]+"</li>";
	        selectFazhan[i]=res.latticePoint[i];
		};
	    //zhzhuanWdArr=res.latticePoint;
		$(".selectUl:eq(0)").empty().append(html);	
		
		
	});
}
	$("#fhdwmch").easyAutocomplete({
		minCharNumber: 1,//至少需要1个字符
		url: function(phrase) {
			return base_url + "/transport/adjunct/customer?something=" + encodeURI(encodeURI(phrase));
		},
		getValue: function(element) {
			return element.customerName;
		},
		//listLocation: "objects",
		list: {
			onSelectItemEvent : function(){
	        	var item = $("#fhdwmch").getSelectedItemData();
	        	$("#fhdwmch").val(item.customerName);
				$('#khbm').val(item.customerKey);
				$("#fhdwdzh").val(item.address);
				$("#fhdwlxdh").val(item.phone);
				$("#fhdwyb").val(item.mobile);
	        },
	        onLoadEvent:function(){
	        	$('#eac-container-fhdwmch').css('width', $("#fhdwmch").outerWidth());
	        }
	    },
		requestDelay : 500
	});
	$(".pinming").easyAutocomplete({
		minCharNumber: 1,//至少需要1个字符
		url: function(phrase) {
			return base_url + "/transport/adjunct/pinming?something=" + encodeURI(encodeURI(phrase));
		},
		getValue: function(element) {
			return element;
		},
	}); 
	$("#khbm").easyAutocomplete({
		minCharNumber: 1,//至少需要1个字符
		url: function(phrase) {
			return base_url + "/transport/adjunct/customer?something=" + encodeURI(phrase);
		},
		getValue: function(element) {
			return element.customerKey;
		},
		//listLocation: "objects",
		list: {
			onSelectItemEvent : function(){
	        	var item = $("#khbm").getSelectedItemData();
	        	$("#khbm").val(item.customerKey);
				$('#fhdwmch').val(item.customerName);
				$("#fhdwdzh").val(item.address);
				$("#fhdwlxdh").val(item.phone);
				$("#fhdwyb").val(item.mobile);
	        },onLoadEvent:function(){
	        	$('#eac-container-khbm').css('width', $("#khbm").outerWidth());
	        }
	    },
		requestDelay : 500
	});
	
$(document).ready(function(){
	getPoints();
	//selectList();
	//防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
    });
    
    $("#otherFee").on("input propertychange",function(){
		$("#s_fee").text($(this).val());
	})
	
	//设置所有的单选多选选中值
	$("input:radio,input:checkbox").each(function(i){
		if($(this).val() == $(this).attr("real")){
			$(this).attr("checked",'checked');
		}
	});
	
	/* bindEvent(); */
	
	$("#btn-return").on('click', function(){
		window.location.href = base_url + "/transport/convey/edit/return.do";
	});
	
	 //日期选择空间
	$('.datetime-picker').each(function(i,val) { 
		$(this).flatpickr({ maxDate: new Date(), minDate:'1970-01-01' ,enableTime: true});
	}); 
});

function bindEvent(){
	var gegExp = new RegExp('');
	var item = { jianshu : 0, zhl : 0, tiji : 0 ,qzxl :0,zchl :0,tbje:0,zzxl:0,qchl:0};
	$("#details-tbody tr").each(function(){
		console.log(gegExp.compile());
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
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script src="${ctx_static}/common/jquery/plugins/jquery.validate.min.js"></script>
<script src="${ctx_static}/transport/convey/js/cost.js?t=1111"></script>
<script src="${ctx_static}/transport/convey/js/modifyWaybill.js?t=22"></script>
<script src="${ctx_static}/transport/convey/js/edit.js?t=3331"></script>
</body>
</html>