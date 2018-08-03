<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
	<head>
		<title>运单管理</title>
		<%@ include file="/views/transport/include/head.jsp"%>
		<link rel="stylesheet" href="${ctx_static}/transport/convey/css/waybill.css"/>
		<style type="text/css">
			.ydbhid-a{ color: 358FF0;cursor:pointer;}
			hr{height: 1px;margin: 10px 0;border: 0;background-color: #e2e2e2;clear: both;}
			.reciept-btn{padding: 0 10px;height: 30px;line-height: 30px;}
			.check-one{cursor:pointer;display:inline-block;width:18px;height:18px;line-height:18px;border:1px solid #bbb;border-radius:2px;font-weight:900;font-family:黑体;}
		</style>
	</head>
<body>
		<div id="content">
				<div class="con-main">
					<div class="part">
						<h2>运单管理</h2>
						<!--运输信息-->
						<div class="sub-part"> 
							<!--<h4>运输信息</h4>-->
							<div class="sub-part-form">
								<form  class="form-inline" id='form' role="form" action="" method="">
									<div class="clearfix">
										<div class="form-con col-25">
											<label class="labe-l">运单编号 :</label>
											<input type="text" class="txt ajax-submit" name="ydbhid" id="orderId" value="${searchModel.ydbhid }">
										</div>
<%-- 										<div class="form-con col-25"> 
											<label class="labe-l">运输起始地 :</label> 
 											<input type="text" class="txt" name="beginPlacename" value="${searchModel.beginPlacename }"> 
 										</div>					--%>
										<div class="form-con col-25">
											<label class="labe-l">到站:</label>
											<input id="daozhan" type="text" class="txt ajax-submit" name="daozhan" value="${searchModel.daozhan }">
										</div>
<!-- 										<div class="form-con col-25"> -->
<!-- 											<label class="labe-l">运输号码 :</label> -->
<%-- 											<input type="text" class="txt ajax-submit" name="yshhm" value="${searchModel.yshhm }"> --%>
<!-- 										</div>	 -->
										<div class="form-con col-25">
											<label class="labe-l">客户单号:</label>
											<input id="khdh" type="text" class="txt ajax-submit" name="khdh" value="${searchModel.khdh }">
										</div>
										<div class="form-con col-25">
											<label class="labe-l">客户编码:</label>
											<input id="khbm" type="text" class="txt ajax-submit" name="khbm" value="${searchModel.khbm }">
										</div>
									</div>
									<div class="clearfix"> 
										<div class="form-con col-25">
											<label class="labe-l">托运日期 :</label>
											<input id="searchdate" type="text" class="ajax-submit txt datetime-picker" name="searchdate" value="${searchModel.searchdate }">
										</div>
										<div class="form-con col-25">
											<label class="labe-l">录单员工号 :</label>
											<input id="zhipiao" type="text" class="ajax-submit txt" name="zhipiao" value="${searchModel.zhipiao }">
										</div>
										<%-- <div class="form-con col-25">
											<label class="labe-l">装载状态 :</label>
											<select name="status" class="txt" value="${searchModel.status }">
												<option value="0" <c:if test="${'0' eq searchModel.status}">selected</c:if> >所有</option>
												<option value="1" <c:if test="${'1' eq searchModel.status}">selected</c:if> >已装载</option>
												<option value="2" <c:if test="${'2' eq searchModel.status}">selected</c:if> >未装载</option>
											</select>
										</div> --%>
										<div class="form-con col-25">
											<label class="labe-l">客户名称:</label>
											<input id="fhdwmch" type="text" class="txt ajax-submit" name="fhdwmch" value="${searchModel.fhdwmch }">
										</div>
										<div class="form-con col-25">
											<label class="labe-l">收货人名称:</label>
											<input id="shhrmch" type="text" class="txt ajax-submit" name="shhrmch" value="${searchModel.shhrmch }">
										</div>
									</div>
									<div class="clearfix">
										<div class="form-con col-25">
											<label class="labe-l">有无财凭 :</label>
											<select id="isFinanceCertify" name="isFinanceCertify" class="txt ajax-submit" value="${searchModel.isFinanceCertify }">
												<option value="2" <c:if test="${'2' eq searchModel.isFinanceCertify}">selected</c:if> >所有</option>
												<option value="1" <c:if test="${'1' eq searchModel.isFinanceCertify}">selected</c:if> >已做财凭</option>
												<option value="0" <c:if test="${'0' eq searchModel.isFinanceCertify}">selected</c:if> >未做财凭</option>
											</select>
										</div>
										<div class="form-con col-25">
											<label class="labe-l">回单状态 :</label>
											<select id="hasReceipt" name="hasReceipt" class="txt ajax-submit" value="${searchModel.hasReceipt }">
												<option value="" <c:if test="${'2' eq searchModel.hasReceipt}">selected</c:if> >全部</option>
												<option value="1" <c:if test="${'1' eq searchModel.hasReceipt}">selected</c:if> >已上传</option>
												<option value="0" <c:if test="${'0' eq searchModel.hasReceipt}">selected</c:if> >未上传</option>
											</select>
										</div>
										<div class="form-con col-25">
											<input type="hidden" name="num" value="${searchModel.num }" />
											<a class="search-3" id="search-3">搜索</a>
										</div>
										<div class="form-con col-25">
											<!--  <a class="search-3" id="export">导出</a>-->
										</div>
									</div>
								</form>
								<form  class="form-inline" id='form_hidden' role="form" method="post">
									<input type="hidden"  name="ydbhid" value="${searchModel.ydbhid }">
									<input type="hidden"  name="beginPlacename" value="${searchModel.beginPlacename }">
									<input type="hidden"  name="daozhan" value="${searchModel.daozhan }">
									<input type="hidden"  name="yshhm" value="${searchModel.yshhm }">
									<input type="hidden"  name="searchdate" value="${searchModel.searchdate }">
									<input type="hidden"  name="status" value="${searchModel.status }">
									<input type="hidden" id="ydbhId" name="ydbhId">
									<input type="hidden" id="ydbhidList" name="ydbhidList">
								</form>
								</div>
							</div>
						</div>
						<hr/>
					    <div class='info-detail check-box'>
					    	<div class='layui-form-item'>
					    		<button class='layui-btn' id="songhuodan" onclick='print()'>打印（套打）</button>
					    		<button class='layui-btn' onclick='printA4()'>打印（A4）</button>
					    		<button class='layui-btn' onclick='printDetail()'>打印货物明细</button>
					    		<button class='layui-btn' onclick='printLabel()'>打印70*60标签</button>
					    		<button class='layui-btn' onclick='printLabelBig()'>打印70*90标签</button> 
					    		<button class='layui-btn' id="tihuodan" onclick='look()'>在途查看</button>
					    	</div>
					    </div>
					    <hr/>
					</div>
			<!--表格-->	
	   	  	 <div class="content-con">
	   	  	 	<div class="quan-select">
<!--              	    <input type="button" id="batch" class="batch" value="批量装载"/>
             	    <a href="${base_url}/transport/convey/insert" id="add-num">运单录入</a> -->
                </div>
	   	  	 	<div class="biaoge" id="biaoge">
					<table class="form">
					   <colgroup>
					   	  <col width="4%">
					      <col width="10%">
					      <col width="5%">
					      <col width="8%">
					      <col width="7%">
					      <col width="5%">
					      <col width="12%">
					      <col width="7%">
					      <col width="12%">
					      <col width="6%">
					      <col width="5%">
					      <col width="6%">
					    </colgroup>
						<thead>
							<tr class="con-header">
								 <th class="center"><span class="check-one select-all" data-num="0" onclick="selectAll(this)"></span></th>
					   	  	 	 <th class="center"><span class="hed">运单编号</span></th>
					   	  	 	 <th class="center"><span class="hed">财凭号</span></th>
				   	  	         <th class="center"><span class="hed">客户名称</span></th>
				   	  	         <th class="center"><span class="hed">货量</span></th>
				   	  	         <th class="center"><span class="hed">数量</span></th>
				   	  	         <th class="center"><span class="hed">收货方</span></th>
				   	  	         <th class="center"><span class="hed">运输方式</span></th>
				   	  	         <th class="center"><span class="hed">托运时间</span></th>
				   	  	         <th class="center"><span class="hed">服务方式</span></th>
				   	  	         <th class="center"><span class="hed">收款合计(元)</span></th>
				   	  	        <!--  <th class="center"><span class="hed">操作</span></th> -->
				   	  	         <th class="center"><span class="hed">回单状态</span></th>
							</tr>	
						</thead>
						<tbody id='table-tbody'>
						<%-- <c:forEach items="${page.collection}" var="i" varStatus="s">
							<tr class="aaddress">
							    <td class="center"><a style="word-break: break-all;" class="ydbhid-a" onclick="check('${i.ydbhid}');">${i.ydbhid}</a></td>
							    <td class="center">
									<span style="text-align: center;">${i.cwpzhbh}</span>
								</td>
								<td class="center">
									<span style="text-align: center;">${i.fhdwmch}</span>
								</td>
								<td class="center">
									<c:if test="${i.zhl != NULL && i.zhl != 0}">
										<span>${i.zhl}吨</span><br />
									</c:if>
									<c:if test="${i.tiji != NULL && i.tiji != 0}">
										<span>${i.tiji}立方米</span>
									</c:if>
								</td>
								<td class="center">${i.jianshu}</td>
								<td class="center">${i.shhrmch}</td>
								<td class="center">${i.ysfs}</td>
								<td class="center">
									<c:if test="${i.fwfs == 0}">仓到站</c:if>
									<c:if test="${i.fwfs == 1}">仓到仓</c:if>
									<c:if test="${i.fwfs == 2}">站到仓</c:if>
									<c:if test="${i.fwfs == 3}">站到站</c:if>
								</td>
								<td class="center"><fmt:formatNumber value="${i.hjje}" type="currency" pattern="0.00"/></td>
								<td class="center">
									<c:if test="${i.bundel != NULL &&  i.bundel == 0}">
									<a><input type="button" value="装载" class="foll bundel" id="${i.ydbhid}"/></a>
										<a><input type="button" value="未装载 " style="cursor:auto;" class="foll" id="${i.ydbhid}"/></a>
									</c:if>
									<c:if test="${i.bundel != NULL && i.bundel == 1}">
										<a><input type="button" value="已装载" style="cursor:auto;" class="foll1"/></a>
									</c:if>
								</td>
							</tr>
			    		</c:forEach> --%>
						</tbody>
				    </table>
	   	  	 	</div>
			<div id="layui_div_page" class="col-sm-12" >
				<ul class="pagination">
					<li class="disabled"><span><span aria-hidden="true">共<span class='page-total'></span>条,每页<span class='page-size'></span>条</span></span></li>
					
					<li><a href="javascript:;" num="1" class='isFirstPage' style='display: none'>首页</a></li>
					<li><a href="javascript:;" num="" class='page-prePage isFirstPage' style='display: none'>&laquo;</a></li>
						
					<li class='insert-li'><a href="javascript:;" num="" class='isLastPage nextPage' style='display: none'>&raquo;</a></li>
					<li><a href="javascript:;" num="" class='isLastPage lastPage' style='display: none'>尾页</a></li>
					
					<li class="disabled last-li"><span><span>共<span class='page-pages'></span>页</span></span></li>
				</ul>
			</div>
	   	  	   </div>
		  </div>	
			</div>
	</body>
<%@ include file="/views/transport/include/floor.jsp"%>
<script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
<script src="${ctx_static}/transport/excelRead/js/jsrender-min.js"></script>
<script type="text/x-jsrender" id='table-list'>
	<tr class="aaddress">
		<td class="center"><span class="check-one select-one" waybillNum='{{:ydbhid}}' data-num="0" onclick="selectOne(this,'{{:ydbhid}}');"></span></td>
		<td class="center"><a style="word-break: break-all;" class="ydbhid-a" onclick="check('{{:ydbhid}}');">{{:ydbhid}}</a></td>
		<td class="center">
			<span style="text-align: center;">{{:cwpzhbh}}</span>
		</td>
		<td class="center">
			<span style="text-align: center;">{{:fhdwmch}}</span>
		</td>
		<td class="center">
			{{if zhl != NULL && zhl != 0}}
				<span>{{:zhl}}吨</span><br />
			{{/if}}
			{{if tiji != NULL && tiji != 0}}
				<span>{{:tiji}}立方米</span><br />
			{{/if}}
		</td>
		<td class="center">{{:jianshu}}</td>
		<td class="center">{{:shhrmch}}</td>
		<td class="center">{{:ysfs}}</td>
		<td class="center">{{:fhshj}}</td>
		<td class="center">
			{{if fwfs == 0}}
				仓到站
			{{else fwfs == 1}}
				仓到仓
			{{else fwfs == 2}}
				站到仓
			{{else fwfs == 3}}
				站到站
			{{/if}}
		</td>
		<td class="center">{{:hjje}}</td>
		<td class="center">
			{{if hasReceipt == NULL ||  hasReceipt == 0}}
				<button class="layui-btn layui-btn-disabled reciept-btn" style="color:#999;">未上传<button>
			{{else}}
				<button class="layui-btn reciept-btn" onclick="recieptState('{{:ydbhid}}');">查看<button>
			{{/if}}		
		</td>
		{{!--<td class="center">
			{{if bundel != NULL &&  bundel == 0}}
				<a><input type="button" value="装载" class="foll bundel" id="{{:ydbhid}}"/></a>
				<a><input type="button" value="未装载 " style="cursor:auto;" class="foll" id="{{:ydbhid}}"/></a>
			{{else bundel != NULL &&  bundel == 1}}
				<a><input type="button" value="已装载" style="cursor:auto;" class="foll1"/></a>
			{{/if}}		
		</td>--}}
	</tr>
</script>
<script type="text/javascript">
var base_url = '${base_url}', static_url = '${ctx_static}';
var layer;
$(document).ready(function(){
	layui.use('layer', function(){
		layer = layui.layer;
	})
	//日期选择空间jquery-1.12.2.minjquery-1.12.2.min
	if(!$('.datetime-picker').val()){
		$('.datetime-picker').val(moment().subtract(7, 'days').format('YYYY-MM-DD') +' 至 '+ moment().format('YYYY-MM-DD'));
	}
	//日期选择空间
	$('.datetime-picker').each(function(i,val) { 
		$(this).flatpickr({ mode: "range" ,minDate: "1970-01-01"});
	});
	
	/**
	 * 功能描述：点击保存提交表单
	 * 作者：yanxf 
	 * 时间：2017-05-05
	 */
	 /*获取ajax查询字段*/
	function ajaxSubmit(num){
		num = num || 1;
		var dataArr = {};
		dataArr['num'] = num;
		$('.ajax-submit').each(function(index,elem){
			var name = $(elem).attr('name');
			var value = $(elem).val();
			dataArr[name] = value;
		})
		dataArr = JSON.stringify(dataArr);
		$.ajax({
			url: base_url + '/transport/convey/manage/search',
			type: 'post',
			dataType: 'JSON',
			contentType: 'application/json',
			data: dataArr,
			beforeSend: function(){
				loading = layer.load(0,{
					shade: [0.5,'#fff']
				})
			},
			success: function(data){
				if(data.resultCode != 200){
					layer.open({title:"提示信息",content:data.reason});
				}else{
					//切换页面时，清空全选框
					$(".select-all").attr("data-num","0").html("");
					if($('.page-num')){
						$('.page-num').remove();
					}
					layer.close(loading);
					$('.page-total').html(data.page.total);
					$('.page-size').html(data.page.size);
					$('.page-pages').html(data.page.pages);
					$.each(data.page.collection, function(i,k){
						k.fhshj = formatDate(new Date(k.fhshj),'年月日时分');
					});
					$('#table-tbody').html($('#table-list').render(data.page.collection));
					if(data.page.pages > 1){
						if(!data.page.isFirstPage){
							$('.isFirstPage').show();
							$('.page-prePage').attr('num',data.page.prePage);
						}else{
							$('.isFirstPage').hide();
						}
						$.each(data.page.navigatepageNums, function(i,e){
							if(e == data.page.num){
								$('.insert-li').before('<li class="active page-num"><a href="javascript:;" num='+e+'>'+e+'</a></li>');
							}else{
								$('.insert-li').before('<li class="page-num"><a href="javascript:;" num='+e+'>'+e+'</a></li>');
							}
							
						})
						if(!data.page.isLastPage){
							$('.isLastPage').show();
							$('.nextPage').attr('num',data.page.nextPage);
							$('.lastPage').attr('num',data.page.pages);
						}else{
							$('.isLastPage').hide();
						}
					}else{
						$('.isLastPage').hide();
					}
					eachPage();
				}
				
			},
			error: function(data){
				layer.close(loading);
			}
		})
	}
	/*新增 当输入运单编号时  日期清空  by wyp*/
 	$("#orderId").on("input propertychange",function(){  
		if($.trim($(this).val())!=""){
			$(".datetime-picker").val("");
		}else{
			$('.datetime-picker').val(moment().subtract(7, 'days').format('YYYY-MM-DD') +' 至 '+ moment().format('YYYY-MM-DD'));
		}
	}); 
	
	
	$("#search-3").on("click", function() {
		var searchdate = $('.datetime-picker').val();
		//if(searchdate != null && searchdate != undefined && searchdate != ''){
			$("form input[name='num']").val("");
			ajaxSubmit();
			/* $("#form").submit(); */
		//}else{
		//	layer.msg('请选择托运日期',{anim: 5, icon: 6})
			/* $.util.warning('操作提示', '请选择托运日期'); */
		//}		
	});
	function eachPage(){
		$(".pagination a").each(function(i){	
			$(this).unbind('click').on('click', function(){
				var searchdate = $('.datetime-picker').val();
			//	if(searchdate != null && searchdate != undefined && searchdate != ''){
					$("form input[name='num']").val($(this).attr("num"));
					ajaxSubmit($(this).attr("num"));
				    /* $("#form").submit();  */
			//	}else{
				//	layer.msg('请选择托运日期',{anim: 5, icon: 6})
					/* $.util.warning('操作提示', '请选择托运日期');  */
				// }
			});   
		});
	}
	
	/**
	 * 导出
	 * 作者：yanxf 
	 * 时间：2018-03-22
	 */
	$("#export").click(function(){/*下载模板*/
		//获取页面查询参数
		var ydbhid = $("#ydbhid").val();
		if(ydbhid == "" || ydbhid == null || ydbhid == undefined){
			ydbhid = "";
		}
		var daozhan = $("#daozhan").val();
		var khdh = $("#khdh").val();
		var khbm = $("#khbm").val();searchdate
		var searchdate = $("#searchdate").val();
		var zhipiao = $("#zhipiao").val();
		var fhdwmch = $("#fhdwmch").val();
		var shhrmch = $("#shhrmch").val();
		var isFinanceCertify = $("#isFinanceCertify").val();
		window.location.href = base_url + "/transport/convey/exportTransportOrder?ydbhid=" + ydbhid + "&daozhan=" + daozhan + "&khdh=" + khdh + 
				"&khbm=" + khbm + "&searchdate=" + searchdate + "&zhipiao=" + zhipiao + "&fhdwmch=" + fhdwmch + "&shhrmch=" + shhrmch +
				"&isFinanceCertify=" + isFinanceCertify;
	})
});

	
	
	

	function check(ydbhid){
		var width=$(document).width();
		$("body").css ("overflow-y","hidden");
		$("body").width(width);
		layer.open({
			  type: 2,
			  title: "查看与修改页面（运单号:"+ydbhid+"）",
			  closeBtn: 1, //显示关闭按钮
			  shade: [0],
			  area: ['90%', '90%'],
			  //offset: 'rb', //右下角弹出
			 // time: 2000, //2秒后自动关闭
			  anim: 2,
			  content: [base_url + "/transport/convey/edit/manage/"+ydbhid, 'yes'], //iframe的url，no代表不显示滚动条
			  end: function () {
				  $("body").css ('overflow-y','auto');
	            }
			});
		//$("#ydbhId").val(ydbhid);
		//window.location.href = base_url + "/transport/convey/edit/manage/"+ydbhid;
		//$("#form_hidden").attr("action", base_url + "/transport/convey/check.do");
		//$("#form_hidden").attr("action", base_url + "/transport/convey/edit/manage.do");
		//$("#form_hidden").submit();
	}
	//回单状态
	function recieptState(ydbhid){
		var width=$(document).width();
		$("body").css ("overflow-y","hidden");
		$("body").width(width);
		/* var img = '<div style="width:100%;height:100%;line-height:500px;text-align:center;">'
				+'<img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="max-width:880px;max-height:480px;"/></div>';  */
		layer.open({
			  type: 2,
			  title: "回单查看（运单号:"+ydbhid+"）",
			  closeBtn: 1, //显示关闭按钮
			  shade: [0],
			  area: ['90%', '90%'],
			  anim: 2,
			  content: [base_url + "/transport/convey/toRecieptState?ydbhid="+ydbhid, 'yes'], //iframe的url，no代表不显示滚动条
			  end: function () {
				  $("body").css ('overflow-y','auto');
	            }
			});
	}
	function selectAll(dom){
		if($(dom).attr('data-num') == "0"){
			/* $(".check-one").attr("data-num","0").html("");
			$(".check-one").removeAttr("waybillNum"); */
			$(dom).attr("data-num","1").html("√");
			$(".select-one").attr("data-num","1").html("√");
		}else if($(dom).attr('data-num') == "1"){
			$(dom).attr("data-num","0").html("");
			$(".select-one").attr("data-num","0").html("");
		}
	}
	//选择多条数据数据
	function selectOne(dom,num){
		if($(dom).attr('data-num') == "0"){
			$(dom).attr("data-num","1").html("√");
		}else if($(dom).attr('data-num') == "1"){
			$(dom).attr("data-num","0").html("");
		}
		var selectNum = 0;
		var singleLength = $(".select-one").length;
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				selectNum += 1;
			}
		})
		if(selectNum == singleLength){
			$(".select-all").attr("data-num","1").html("√");
		}else{
			$(".select-all").attr("data-num","0").html("");
		}
	}
	
	//打印（套打）
	function print(){
		var ydbhidArr = [];
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				ydbhidArr.push($(this).attr("waybillNum"));
			}
		});
		if(ydbhidArr.length > 0){
			var str = "";
			for(var i in ydbhidArr){
				str += ydbhidArr[i] + ',';
			}
			str = str.substring(0,str.length - 1);
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "货物运单",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [base_url + "/transport/convey/print?ydbhid="+str, 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		}else{
			layer.msg('请至少选择一条数据！');
		}
	}
	//打印（A4）
	function printA4(){
		var ydbhidArr = [];
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				ydbhidArr.push($(this).attr("waybillNum"));
			}
		});
		if(ydbhidArr.length > 0){
			var str = "";
			for(var i in ydbhidArr){
				str += ydbhidArr[i] + ',';
			}
			str = str.substring(0,str.length - 1);
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "货物运单",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [base_url + "/transport/convey/printA4?ydbhid="+str, 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		}else{
			layer.msg('请至少选择一条数据！');
		}
	}
	//打印货物明细
	function printDetail(){
		var ydbhidArr = [];
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				ydbhidArr.push($(this).attr("waybillNum"));
			}
		});
		if(ydbhidArr.length == 1){
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "货物明细单（运单号:"+ydbhidArr[0]+"）",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [base_url + "/transport/convey/printDetail?ydbhid="+ydbhidArr[0], 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		}else{
			layer.msg('请务必选择一条数据！');
		}
	}
	
	//打印70*60标签
	function printLabel(){
		var ydbhidArr = [];
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				ydbhidArr.push($(this).attr("waybillNum"));
			}
		});
		if(ydbhidArr.length == 1){
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "打印70*60客户标签（运单号:"+ydbhidArr[0]+"）",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [base_url + "/transport/convey/printLabel?ydbhid="+ydbhidArr[0], 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css('overflow-y','auto');
		            }
				});
		}else{
			layer.msg('请务必选择一条数据！');
		}
		
	}
	//打印70*90标签
	function printLabelBig(){
		var ydbhidArr = [];
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				ydbhidArr.push($(this).attr("waybillNum"));
			}
		});
		if(ydbhidArr.length == 1){
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "打印70*90客户标签（运单号:"+ydbhidArr[0]+"）",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [base_url + "/transport/convey/printLabelBig?ydbhid="+ydbhidArr[0], 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		}else{
			layer.msg('请务必一条数据！');
		}
	}

	//在途查看
	function look(){
		var ydbhidArr = [];
		$(".select-one").each(function(){
			if($(this).attr("data-num") == "1"){
				ydbhidArr.push($(this).attr("waybillNum"));
			}
		});
		if(ydbhidArr.length == 1){
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			sessionStorage.setItem("transId",ydbhidArr[0]);
			layer.open({
				  type: 2,
				  title: "货物在途跟踪查看（运单号:"+ydbhidArr[0]+"）",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '80%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [base_url + "/transport/trace/transit", 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				}); 
		}else{
			layer.msg('请务必选择一条数据！');
		}
		
	}
</script>
</html>