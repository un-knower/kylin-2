
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>装载清单查看</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
<link href="${ctx_static}/transport/bundle/css/expenditure.css" rel="stylesheet" />
</head>

<body>
	<span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>
		<a href='javaScript:void(0);'>装载清单查询</a>
   </span>
	<div class="wrap">
		<div id="content">
			<div class="con-main">
					<div class="part">
						<h2>装载清单查询</h2>
						<br />
						<div class="sub-part">
							<!-- 装载方式  -->
							<form class='layui-form'>
								<div class='layui-inline'>
								<label class="col-10 fontCopy layui-form-label">&nbsp;&nbsp;&nbsp;装载方式 :</label>
								<div class="form-con col-10 fontCopy loadmodearea">
									<c:if test="${receipt.iType==2}">
										<input name="zType" value="2" checked="checked" title="提货装载" id="transTh" class="transType radioStyle" type="radio" disabled>
									</c:if>
									<c:if test="${receipt.iType!=2}">
										<input name="zType" value="2" title="提货装载" id="transTh" class="transType radioStyle" type="radio" disabled>
									</c:if>
								</div>
								<div class="form-con col-10 fontCopy loadmodearea">
									<c:if test="${receipt.iType==0}">
										<input name="zType" value="0" title="干线中转" id="transGx" class="transType radioStyle" checked="" type="radio" disabled>
									</c:if>
									<c:if test="${receipt.iType!=0}">
										<input name="zType" value="0" title="干线中转" id="transGx" class="transType radioStyle" type="radio" disabled>
									</c:if>
								</div>
								<div class="form-con col-20 fontCopy loadmodearea">
									<c:if test="${receipt.iType==1}">
										<input name="zType" value="1" checked="checked" title="配送运输" id="transPs" class="radioStyle transType" type="radio" disabled>
									</c:if>
									<c:if test="${receipt.iType!=1}">
										<input name="zType" value="1" title="配送运输" id="transPs" class="radioStyle transType" type="radio" disabled>
									</c:if>
								</div>
								</div>
								<!-- 承运方式  -->
								<div class='layui-inline cyMehod' style='width: 420px'>
								<label class="col-10 fontCopy layui-form-label">&nbsp;&nbsp;&nbsp;承运方式 :</label>
								<div class="form-con col-10 fontCopy carrmodearea">
									<c:if test="${receipt.clOwner=='外线'}">
										<input type="radio" checked="checked" class="radioStyle" disabled="true" title='外线'/>
									</c:if>
									<c:if test="${receipt.clOwner!='外线'}">
										<input type="radio"class="radioStyle" disabled="true" title='外线'/>
									</c:if>
								</div>
								<div class="form-con col-10 fontCopy carrmodearea">
									<c:if test="${receipt.clOwner=='外车'}">
										<input type="radio" checked="checked" class="radioStyle" disabled="true" title='外车'/>
									</c:if>
									<c:if test="${receipt.clOwner!='外车'}">
										<input type="radio"class="radioStyle" disabled="true" title='外车'/>
									</c:if>
								</div>
								<div class="form-con col-20 fontCopy carrmodearea">
									<c:if test="${receipt.clOwner=='自有'}">
										<input type="radio" checked="checked" class="radioStyle" disabled="true" title='自有车'/>
									</c:if>
									<c:if test="${receipt.clOwner!='自有'}">
										<input type="radio"class="radioStyle" disabled="true" title='自有车'/>
									</c:if>
								</div>
								</div>
								<!-- 运输类型 -->
								<div class='layui-inline'>
								<label class="col-20 fontCopy layui-form-label">运输类型 :</label>
								
								<c:if test= "${receipt.yslx =='零担'}" >
									<div class="form-con col-20 fontCopy transtypearea">
										<input type="radio" name="yslx" value="整车" class='radioStyle' title='整车运输' disabled="disabled"/> 
									</div>
									<div class="form-con col-20 fontCopy transtypearea">
										<input type="radio" name="yslx" value="零担" checked="checked" class='radioStyle' title='零担运输' disabled="disabled"/> 
									</div>
								</c:if>
								<c:if test= "${receipt.yslx =='整车'}" >
									<div class="form-con col-20 fontCopy transtypearea">
										<input disabled="disabled" type="radio" name="yslx" value="整车" checked="checked" class='radioStyle' title='整车运输'/> 
									</div>
									<div class="form-con col-20 fontCopy transtypearea">
										<input disabled="disabled" type="radio" name="yslx" value="零担" class='radioStyle' title='零担运输'/> 
									</div>
								</c:if>
								</div>
							</div>
						</form>
						<br />
						<c:if test="${receipt.clOwner=='外线'}">
							<div class="sub-part" id="xiao">
							<h4>外线信息</h4>
							<div class="sub-part-form">
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l">外线名称 :</label> 
										<input type="text" class="txt"  value="${receipt.wxName}" disabled="disabled">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">联系人 :</label> 
										<input type="text" class="txt" value="${receipt.wxConName}" disabled="disabled">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">联系电话 :</label> 
										<input type="text" class="txt"  value="${receipt.wxTel}" disabled="disabled">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">外线单号 :</label> 
										<input type="text" class="txt" value="${receipt.wxItem}" disabled="disabled">
									</div>
								</div>
							</div>
						</div>
						</c:if>
							<c:if test= "${not empty receipt.chxh}" >
									<!--车辆信息-->
								<div class="sub-part" id="cheliang">
									<h4>车辆信息</h4>
									<div class="sub-part-form">
										<div class="clearfix">
											<div class="form-con col-25">
												<label class="labe-l">车牌号 :</label> 
												<input type="text" class="txt" 	value="${receipt.chxh}" disabled="disabled">
											</div>
											<div class="form-con col-25">
												<label class="labe-l">司机姓名 :</label> <input type="text" class="txt"  value="${receipt.driverName }" disabled="disabled">
											</div>
											<div class="form-con col-25">
												<label class="labe-l">司机电话 :</label> <input type="text" class="txt" value="${receipt.driverTel}" disabled="disabled">
											</div>
										</div>
									</div>
								</div>
							</c:if>		
							
					
								
						<!--运输信息-->
						<div class="sub-part">
							<h4>运输信息</h4>
							<div class="sub-part-form">
									<!-- 选择干线中转的装载方式：显示如下表单 -->
							<div class="clearfix">
								<c:if test="${receipt.iType==0}">
									<c:if test="${receipt.transferFlag =='中转'}">
									<div class="form-con col-25">
							          <label class="labe-l">起运网点：</label>
							          <input type="text" class="txt"  value = "${receipt.fzshhd }" disabled="disabled">
							        </div>		
										<div class="form-con col-25">
											<label class="labe-l">中转站：</label> 
											<input type="text" class="txt" value="${receipt.daozhan}" disabled="disabled">
										</div>
										<div class="form-con col-25">
											<label class="labe-l">中转网点：</label> 
											<input type="text" class="txt" value="${receipt.dzshhd}" disabled="disabled">
										</div>
									</c:if>
									<c:if test="${receipt.transferFlag !='中转'}">
									<div class="form-con col-25">
							          <label class="labe-l">起运网点：</label>
							          <input type="text" class="txt"  value = "${receipt.fzshhd }" disabled="disabled">
							        </div>	
										<div class="form-con col-25" >
										<label class="labe-l">发站 :</label> 
										<input type="text" class="txt" disabled="disabled" value="${receipt.fazhan}">
									</div>
									<div class="form-con col-25" >
										<label class="labe-l">到站:</label> 
										<input type="text" class="txt"  disabled="disabled" value="${receipt.daozhan}">
									</div>
									 <div class="form-con col-25">
								      <label class="labe-l">到达网点：</label>
								    	<input type="text" class="txt" disabled="disabled" value="${receipt.dzshhd}">
								    </div>
									</c:if>
									
								</c:if>
								<c:if test="${receipt.iType!=0}">
								<div class="form-con col-25">
							          <label class="labe-l">起运网点：</label>
							          <input type="text" class="txt"  value = "${receipt.fzshhd }" disabled="disabled">
							        </div>	
									<div class="form-con col-25" >
										<label class="labe-l">运输起始地 :</label> 
										<input type="text" class="txt" disabled="disabled" value="${receipt.fazhan}">
									</div>
									<div class="form-con col-25" >
										<label class="labe-l">运输目的地:</label> 
										<input type="text" class="txt"  disabled="disabled" value="${receipt.daozhan}">
									</div>
									 <div class="form-con col-25">
								      <label class="labe-l">到达网点：</label>
								    	<input type="text" class="txt" disabled="disabled" value="${receipt.dzshhd}">
								    </div>
								</c:if>
									
								</div>
								<div class="clearfix">
								    <div class="form-con col-25">
										<label class="labe-l">发车时间 :</label> 
										<input type="text" class="txt datetime-picker" disabled="disabled"
											value="<fmt:formatDate value="${receipt.fchrq}" type="both"/>">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">到达时间 :</label> 
										<input type="text" class="txt datetime-picker" disabled="disabled"
											value="<fmt:formatDate value="${receipt.yjddshj}" type="both"/>">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运输成本 :</label>
										<input type="text" class="txt" disabled="disabled" value="${receipt.qdCost}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">其他成本 :</label> 
										<input type="text" class="txt" disabled="disabled" value="${receipt.elseCost}">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l">运输方式:</label> 
										<input type="text" class="txt" disabled="disabled" value="${receipt.ysfs}">
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--关联运单-->
					<div class="sub-part">
						<div class="sub-part-form">
							<table>
								<thead>
									<tr>
										<th width="6%" style="text-align:center;">序号</th>
										<th width="12%" style="text-align:center;">运单编号</th>
										<th width="12%" style="text-align:center;">收货人省市区</th>
										<th width="20%" style="text-align:center;">客户名称</th>
										<th width="20%" style="text-align:center;">客户地址</th>
										<th width="10%" style="text-align:center;">提货方式</th>
										<th width="10%" style="text-align:center;">品名</th>
										<th width="10%" style="text-align:center;">装车重量（t）</th>
										<th width="10%" style="text-align:center;">装车件数</th>
										<th width="10%" style="text-align:center;">装车体积（m³）</th>
									</tr>
								</thead>
								<c:forEach items="${receipt.bundReceiptList}" var="o" varStatus="varStatus">
									<tbody id="tbody">
										<tr>
											<td>${varStatus.index + 1 }</td>
											<td>${o.ydbhid }</td>
											<td>${o.ydendplace }</td>
											<td>${o.fhdwmch }</td>
											<td>${o.khdh }</td>
											<td>${o.dhgrid }</td>
											<td>${o.pinming }</td>
											<td>${o.zhl }</td>
											<td>${o.jianshu }</td>
											<td>${o.tiji }</td>
										</tr>
									</tbody>
								</c:forEach>
							</table>
						</div>
					</div>
			</div>
		<div class="con-btn">
			<form role="form" id="queryForm_hidden" method="post">
				<input type="hidden" name="ydbhid" value="${searchModel.ydbhid}"/>
				<input type="hidden" name="fazhan" value="${searchModel.fazhan }">
				<input type="hidden" name="lrdate" value="${searchModel.lrdate }">
				<input type="hidden" name="chxh" value="${searchModel.chxh }">
				<input type="hidden" name="wxName" value="${searchModel.wxName }">
				<input type="hidden" name="fcdate" value="${searchModel.fcdate }">	
				<input type="hidden" name="daozhan" value="${searchModel.daozhan }">
				<input type="hidden" name="dddate" value="${searchModel.dddate }">
			</form>
			<!--<a style="margin-left: 10px;" id="btn-return" class="btn-save">返回</a>  -->
			<div style="margin-top: 20px;height:20px;">
			</div>
		</div>
		</div>
	</div>
	</div>
</body>
<%@ include file="/views/transport/include/floor.jsp"%>
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script type="text/javascript">
    	layui.use(['element', 'layer', 'form','laydate'], function(){
    		var element = layui.element;
    		var form = layui.form;
    		var laydate = layui.laydate;   		
      })
 </script>
<script type="text/javascript">
var base_url = '${base_url}';
$(document).ready(function(){

	//防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
    });
	
	$("#btn-return").on('click', function(){
		$("#queryForm_hidden").attr("action", base_url + "/transport/bundle/manage/query.do");
		$("#queryForm_hidden").submit();
	});
});
</script>
</body>
</html>